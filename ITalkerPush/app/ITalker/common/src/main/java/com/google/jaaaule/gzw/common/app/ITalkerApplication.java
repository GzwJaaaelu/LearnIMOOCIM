package com.google.jaaaule.gzw.common.app;

import android.app.Application;
import android.os.SystemClock;
import android.support.annotation.StringRes;
import android.widget.Toast;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.io.File;

/**
 * Created by admin on 2017/6/12.
 */

public class ITalkerApplication extends Application {
    public static Application getInstance() {
        return sInstance;
    }

    private static Application sInstance;

//    private ITalkerApplication() {
//
//    }

//    public static ITalkerApplication getInstance() {
//        if (sInstance == null) {
//            synchronized (ITalkerApplication.class) {
//                if (sInstance == null) {
//                    sInstance = new ITalkerApplication();
//                }
//            }
//        }
//        return (ITalkerApplication) sInstance;
//    }

    /**
     * 当前 App 的缓存地址
     *
     * @return
     */
    public static File getCacheDirFile() {
        return sInstance.getCacheDir();
    }

    /**
     * 获取头像的临时存储文件地址
     * @return
     */
    public static File getPortraitTmpFile() {
        //  得到头像目录的换存地址
        File dir = new File(getCacheDirFile(), "portrait");
        //  创建所有的文件夹
        dir.mkdirs();
        //  删除旧的缓存文件
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                file.delete();
            }
        }
        File file = new File(dir, SystemClock.currentThreadTimeMillis() + ".jpg");
        return file.getAbsoluteFile();
    }

    /**
     * 获取声音文件的本地地址
     * @param isTmp 是否缓存文件  如果是 True，则每次返回的文件地址是一样的
     * @return
     */
    public static File getAudioTmpFile(boolean isTmp) {
        File dir = new File(getCacheDirFile(), "audio");
        dir.mkdirs();
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File f : files) {
                f.delete();
            }
        }
        File file = new File(dir, isTmp ? "tmp.mp3" : SystemClock.currentThreadTimeMillis() + ".mp3");
        return file.getAbsoluteFile();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static void showToast(@StringRes int msgId) {
        showToast(sInstance.getString(msgId));
    }

    private static void showToast(final String msg) {
        //  只能在主线程进行操作
//        Toast.makeText(sInstance, msg, Toast.LENGTH_SHORT).show();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                Toast.makeText(sInstance, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
