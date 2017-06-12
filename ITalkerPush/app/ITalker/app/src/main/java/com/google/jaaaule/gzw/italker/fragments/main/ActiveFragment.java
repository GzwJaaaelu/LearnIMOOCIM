package com.google.jaaaule.gzw.italker.fragments.main;


import android.support.v4.app.Fragment;

import com.google.jaaaule.gzw.common.app.BaseFragment;
import com.google.jaaaule.gzw.common.widget.GalleryView;
import com.google.jaaaule.gzw.italker.R;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveFragment extends BaseFragment {
    @BindView(R.id.gv_gallery)
    GalleryView mGalleryView;
    Unbinder unbinder;

    public ActiveFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_active;
    }


    @Override
    protected void initData() {
        super.initData();
        int id = mGalleryView.setup(getLoaderManager(), new GalleryView.SelectChangeListener() {
            @Override
            public void onSelectCountChange(int count) {

            }
        });
    }
}
