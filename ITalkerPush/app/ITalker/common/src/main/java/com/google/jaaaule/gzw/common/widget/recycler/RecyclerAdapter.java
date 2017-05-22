package com.google.jaaaule.gzw.common.widget.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.jaaaule.gzw.common.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/5/21.
 */

public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<T>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<T> {
    private final List<T> mDataList;
    private AdapterListener<T> mAdapterListener;

    public RecyclerAdapter(List<T> dataList, AdapterListener<T> adapterListener) {
        mDataList = dataList;
        mAdapterListener = adapterListener;
    }

    public RecyclerAdapter(AdapterListener<T> adapterListener) {
        this(new ArrayList<T>(), adapterListener);
    }

    public RecyclerAdapter(List<T> dataList) {
        this(dataList, null);
    }

    public RecyclerAdapter() {
        this(new ArrayList<T>(), null);
    }

    /**
     * 创建 ViewHolder
     *
     * @param parent
     * @param viewType 约定为 XML 布局的 Id
     * @return
     */
    @Override
    public ViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        //  设置点击事件
        item.setOnClickListener(this);
        item.setOnLongClickListener(this);
        ViewHolder<T> holder = onCreateViewHolder(item, viewType);
        //  设置 View 的 Tag 为 ViewHolder，进行双向绑定
        item.setTag(R.id.tag_recycler_holder, holder);
        //  进行界面注解绑定
        holder.mUnbinder = ButterKnife.bind(holder, item);
        //  绑定回调
        holder.mCallback = this;

        return holder;
    }

    /**
     * 返回布局的类型
     *
     * @param position
     * @param data
     * @return XML 文件的 Id
     */
    protected abstract int getItemViewType(int position, T data);

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    protected abstract ViewHolder<T> onCreateViewHolder(View root, int viewType);

    @Override
    public void onBindViewHolder(ViewHolder<T> holder, int position) {
        holder.bind(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void add(T data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    public void add(T... dataArray) {
        if (dataArray != null && dataArray.length > 0) {
            Collections.addAll(mDataList, dataArray);
            notifyItemRangeInserted(mDataList.size(), dataArray.length);
        }
    }

    public void add(List<T> dataList) {
        if (dataList != null && dataList.size() > 0) {
            mDataList.addAll(dataList);
            notifyItemRangeInserted(mDataList.size(), dataList.size());
        }
    }

    public void clean(List<T> dataList) {
        mDataList.clear();
        notifyDataSetChanged();
    }

    public void replace(List<T> dataList) {
        mDataList.clear();
        if (dataList != null && dataList.size() > 0) {
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (mAdapterListener == null) {
            return;
        }
        ViewHolder<T> viewHolder = (ViewHolder<T>) v.getTag(R.id.tag_recycler_holder);
        mAdapterListener.onItemClick(viewHolder, mDataList.get(viewHolder.getAdapterPosition()));
    }

    @Override
    public boolean onLongClick(View v) {
        if (mAdapterListener == null) {
            return false;
        }
        ViewHolder<T> viewHolder = (ViewHolder<T>) v.getTag(R.id.tag_recycler_holder);
        mAdapterListener.onItemLongClick(viewHolder, mDataList.get(viewHolder.getAdapterPosition()));
        return true;
    }

    public void setAdapterListener(AdapterListener<T> adapterListener) {
        mAdapterListener = adapterListener;
    }

    /**
     * 自定义的条目侦听器
     *
     * @param <T>
     */
    public interface AdapterListener<T> {
        void onItemClick(ViewHolder<T> viewHolder, T data);

        void onItemLongClick(ViewHolder<T> viewHolder, T data);
    }

    public static abstract class ViewHolder<T> extends RecyclerView.ViewHolder {
        protected T mData;
        private Unbinder mUnbinder;
        private AdapterCallback<T> mCallback;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据
         *
         * @param data
         */
        void bind(T data) {
            mData = data;
            onBind(data);
        }

        /**
         * 当触发绑定数据的时候回调，必须要重写
         *
         * @param data
         */
        protected abstract void onBind(T data);

        /**
         * 用于更新数据
         *
         * @param data
         */
        public void updateData(T data) {
            if (mCallback != null) {
                mCallback.update(data, this);
            }
        }
    }
}
