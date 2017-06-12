package com.google.jaaaule.gzw.common.app;

import android.app.Application;
import android.os.SystemClock;

import java.io.File;

/**
 * Created by admin on 2017/6/12.
 */

public class ITalkerApplication extends Application {
    private static Application sInstance;

//    private ITalkerApplication() {
//
//    }
//
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

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
