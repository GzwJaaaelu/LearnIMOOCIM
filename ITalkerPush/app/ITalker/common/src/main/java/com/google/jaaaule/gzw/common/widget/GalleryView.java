package com.google.jaaaule.gzw.common.widget;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.jaaaule.gzw.common.R;
import com.google.jaaaule.gzw.common.widget.recycler.RecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GalleryView extends RecyclerView {
    private static final int GALLEY_LOADER_ID = 0x100;
    private static final long MIN_IMAGE_FILE_SIZE = 10 * 1024;
    private LoaderCallback mLoaderCallback = new LoaderCallback();
    private List<Image> mSelectImages = new LinkedList<>();     //  因为这里增删比较多，而查询少，所以用 LinkedList
    private Adapter mAdapter;
    private static final int MAX_SELECT_IMAGE_COUNT = 3;
    private SelectChangeListener mSelectChangeListener;

    public GalleryView(Context context) {
        super(context);
        init();
    }

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutManager(new GridLayoutManager(getContext(), 4));
        mAdapter = new Adapter();
        setAdapter(mAdapter);
        mAdapter.setAdapterListener(new RecyclerAdapter.AdapterListenerImpl<Image>() {

            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder<Image> viewHolder, Image data) {
                //  点击单个图片的操作，如果点击是允许的，那么更新对应的条目状态
                //  然后更新界面，同理：如果不允许单击（已经达到最大选中数量），那么久不刷新界面
                if (onItemSelectClick(data)) {
                    viewHolder.updateData(data);
                }
            }
        });
    }

    /**
     * 点击的具体逻辑
     *
     * @param image
     * @return True 表示进行了数据更改，需要刷新界面，反之不刷新
     */
    private boolean onItemSelectClick(Image image) {
        //  是否需要率性
        boolean notifyRefresh = false;
        if (mSelectImages.contains(image)) {
            //  如果已经存在了
            image.isSelect = false;
            mSelectImages.remove(image);
            notifyRefresh = true;
        } else {
            if (mSelectImages.size() >= MAX_SELECT_IMAGE_COUNT) {
                notifyRefresh = false;
                //  顺便 Toast 一个提示
                String hintStr = getResources().getString(R.string.label_gallery_select_max_size);
                Toast.makeText(getContext(), String.format(hintStr, MAX_SELECT_IMAGE_COUNT + ""), Toast.LENGTH_SHORT).show();
            } else {
                //  添加
                image.isSelect = true;
                mSelectImages.add(image);
                notifyRefresh = true;
            }
        }

        //  有数据改变时
        if (notifyRefresh) {
            notifySelectChanged();
        }
        return notifyRefresh;
    }

    private void notifySelectChanged() {
        if (mSelectChangeListener != null) {
            mSelectChangeListener.onSelectCountChange(mSelectImages.size());
        }
    }

    /**
     * 得到选中图片的全部地址
     *
     * @return 存地址的数组
     */
    public String[] getSelectImagePaths() {
        String[] paths = new String[mSelectImages.size()];
        int index = 0;
        for (Image image : mSelectImages) {
            paths[index++] = image.path;
        }
        return paths;
    }

    /**
     * 对选中图片进行清理
     */
    public void cleanPaths() {
        for (Image image : mSelectImages) {
            //  先重置状态
            image.isSelect = false;
        }
        mSelectImages.clear();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化方法
     * 开始一个图片加载任务
     *
     * @param loaderManager
     * @return 返回 Loader Id，外围可以根据 Id 销毁 Loader
     */
    public int setup(LoaderManager loaderManager, SelectChangeListener listener) {
        loaderManager.initLoader(GALLEY_LOADER_ID, null, mLoaderCallback);
        mSelectChangeListener = listener;
        return GALLEY_LOADER_ID;
    }

    /**
     * 内部的图片数据结构
     */
    public static class Image {
        private int id;                 //  数据 Id
        private String path;            //  图片路径
        private long date;              //  创建日期
        private boolean isSelect;       //  是否选中

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Image image = (Image) o;

            return path != null ? path.equals(image.path) : image.path == null;
        }

        @Override
        public int hashCode() {
            return path != null ? path.hashCode() : 0;
        }
    }

    private class Adapter extends RecyclerAdapter<Image> {

        @Override
        protected int getItemViewType(int position, Image data) {
            return R.layout.item_galley;
        }

        @Override
        protected ViewHolder<Image> onCreateViewHolder(View root, int viewType) {
            return new Holder(root);
        }
    }

    private class Holder extends RecyclerAdapter.ViewHolder<Image> {
        private ImageView mPic;
        private View mShade;
        private CheckBox mSelected;

        public Holder(View itemView) {
            super(itemView);

            mPic = (ImageView) itemView.findViewById(R.id.iv_image);
            mShade = itemView.findViewById(R.id.vi_shade);
            mSelected = (CheckBox) itemView.findViewById(R.id.cb_select);
        }

        @Override
        protected void onBind(Image data) {
            Glide.with(getContext())
                    .load(data.path)
                    //  不设置缓存
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //  如果图片加载慢，设置一个灰色显示
                    .placeholder(R.color.grey_200)
                    .centerCrop()
                    .into(mPic);
            mSelected.setChecked(data.isSelect);
            mShade.setVisibility(data.isSelect ? VISIBLE : GONE);
        }
    }

    /**
     * Loader 回调
     */
    private class LoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {
        private final String[] IMAGE_PROJECTION = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED
        };

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            //  当创建 Loader 时
            if (id == GALLEY_LOADER_ID) {
                return new CursorLoader(getContext(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION,
                        null,
                        null,
                        IMAGE_PROJECTION[2] + " DESC");
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            //  当 Loader 加载完成时
            List<Image> images = new ArrayList<>();
            if (data != null && data.getCount() > 0) {
                while (data.moveToNext()) {
                    File file = new File(data.getString(1));
                    if (!file.exists() || file.length() < MIN_IMAGE_FILE_SIZE) {
                        //  不存在图片和小于 10k 的图片都不需要
                        continue;
                    }
                    Image image = new Image();
                    image.id = data.getInt(0);
                    image.path = data.getString(1);
                    image.date = data.getLong(2);
                    images.add(image);
                }
                updateSource(images);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            //  当 Loader 销毁或重置时 进行界面清空操作
            updateSource(null);
        }
    }

    /**
     * 通知 Adapter 更放心数据
     *
     * @param images
     */
    private void updateSource(List<Image> images) {
        mAdapter.replace(images);
    }

    public interface SelectChangeListener {

        void onSelectCountChange(int count);
    }
}
