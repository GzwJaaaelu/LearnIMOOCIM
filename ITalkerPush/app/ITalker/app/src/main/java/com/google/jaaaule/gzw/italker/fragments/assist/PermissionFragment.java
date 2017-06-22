package com.google.jaaaule.gzw.italker.fragments.assist;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.jaaaule.gzw.common.app.ITalkerApplication;
import com.google.jaaaule.gzw.italker.R;
import com.google.jaaaule.gzw.italker.fragments.media.GalleyFragment;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class PermissionFragment extends BottomSheetDialogFragment implements EasyPermissions.PermissionCallbacks {
    //  权限回调表示
    private static final int RC = 0x0100;

    public PermissionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_permission, container, false);
        view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPerm();
            }
        });
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new GalleyFragment.TransStatusBottomSheetDialog(getContext());
    }

    /**
     * 刷新根布局中的图片的状态
     *
     * @param root
     */
    private void refreshState(View root) {
        if (root == null) {
            return;
        }
        root.findViewById(R.id.iv_state_permission_network)
                .setVisibility(haveNetworkPerm(getContext()) ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.iv_state_permission_read).
                setVisibility(haveReadPerm(getContext()) ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.iv_state_permission_write).
                setVisibility(haveWritePerm(getContext()) ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.iv_state_permission_record_audio).
                setVisibility(haveRecordAudioPerm(getContext()) ? View.VISIBLE : View.GONE);
    }

    /**
     * 检测是否有网络的权限
     *
     * @param context
     * @return True 有
     */
    private static boolean haveNetworkPerm(Context context) {
        String[] perms = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 检测是否有读权限的
     *
     * @param context
     * @return True 有
     */
    private static boolean haveReadPerm(Context context) {
        String[] perms = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 检测是否有写权限的
     *
     * @param context
     * @return True 有
     */
    private static boolean haveWritePerm(Context context) {
        String[] perms = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 检测是否有录音的权限
     *
     * @param context
     * @return True 有
     */
    private static boolean haveRecordAudioPerm(Context context) {
        String[] perms = new String[]{
                Manifest.permission.RECORD_AUDIO
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 私有的显示方法
     *
     * @param manager
     */
    private static void show(FragmentManager manager) {
        //  调用 BottomSheetDialogFragment 准备的好像是方法
        new PermissionFragment()
                .show(manager, PermissionFragment.class.getName());
    }

    /**
     * 检查是否具有所有最基本的权限
     *
     * @param context
     * @param manager
     * @return 是否具有权限
     */
    public static boolean haveAllPerm(Context context, FragmentManager manager) {
        boolean haveAllPerm = haveNetworkPerm(context)
                && haveReadPerm(context)
                && haveWritePerm(context)
                && haveRecordAudioPerm(context);
        if (!haveAllPerm) {
            //  没有全部权限则显示当前弹框
            show(manager);
        }
        return haveAllPerm;
    }

    /**
     * 申请全部权限
     */
    @AfterPermissionGranted(RC)
    private void requestPerm() {
        String[] perms = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            ITalkerApplication.showToast(R.string.label_permission_ok);
            //  Fragment 中 getView() 拿到根布局（前提是在 onCreateView() 之后）
            refreshState(getView());
        } else {
            EasyPermissions.requestPermissions(this,
                    getString(R.string.title_assist_permissions),
                    RC,
                    perms);
        }
    }

    /**
     * 刷新界面，防止用户在设置中东设置后界面未刷新
     */
    @Override
    public void onResume() {
        super.onResume();
        refreshState(getView());
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //  如果权限有部分没有申请成的，则弹出提示框，用户点击后去设计界面自己打开权限
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .build()
                    .show();
        }
    }

    /**
     * 权限成时候的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //  传递对应的参数，以及接收处理权限的处理者是 Fragment 自己
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
