package com.google.jaaaule.gzw.common.widget.recycler;

/**
 * Created by admin on 2017/5/21.
 */

public interface AdapterCallback<T> {
    void update(T data, RecyclerAdapter.ViewHolder<T> holder);
}
