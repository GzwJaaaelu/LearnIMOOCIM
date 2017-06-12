package com.google.jaaaule.gzw.italker.fragments.media;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.jaaaule.gzw.common.tools.UiTool;
import com.google.jaaaule.gzw.common.widget.GalleryView;
import com.google.jaaaule.gzw.italker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleyFragment extends BottomSheetDialogFragment implements GalleryView.SelectChangeListener {
    GalleryView mGalleryView;
    private OnSelectedListener mListener;

    public GalleyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_galley, container, false);
        mGalleryView = (GalleryView) view.findViewById(R.id.gv_gallery);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TransStatusBottomSheetDialog(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        int id = mGalleryView.setup(getLoaderManager(), this);
    }

    @Override
    public void onSelectCountChange(int count) {
        if (count > 0) {
            //  一张即可，选中即隐藏界面
            dismiss();
            if (mListener != null) {
                String[] path = mGalleryView.getSelectImagePaths();
                //  返回第一章
                mListener.onSelectedImage(path[0]);
                //  取消和唤起者之间的引用，加快内存回收
                mListener = null;
            }
        }
    }

    /**
     * 设置回调，返回自身
     *
     * @param listener
     * @return
     */
    public GalleyFragment setListener(OnSelectedListener listener) {
        mListener = listener;
        return this;
    }

    public interface OnSelectedListener {
        void onSelectedImage(String path);
    }

    private static class TransStatusBottomSheetDialog extends BottomSheetDialog {

        public TransStatusBottomSheetDialog(@NonNull Context context) {
            super(context);
        }

        public TransStatusBottomSheetDialog(@NonNull Context context, int theme) {
            super(context, theme);
        }

        protected TransStatusBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            final Window window = getWindow();
            if (window == null) {
                return;
            }
            //  得到屏幕高度
            int screenHeight = UiTool.getScreenHeight(getOwnerActivity());
            //  得到状态栏的高度
            int statusHeight = UiTool.getStatusBarHeight(getOwnerActivity());
            //  计算 Dialog 高度
            int dialogHeight = screenHeight - statusHeight;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight <= 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        }
    }
}
