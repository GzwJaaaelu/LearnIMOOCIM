package com.google.jaaaule.gzw.factory;

import android.app.Application;

import com.google.jaaaule.gzw.common.app.ITalkerApplication;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2017/6/14.
 */

public class Factory {
    private static final Factory sInstance;
    private final Executor mExecutor;

    static {
        sInstance = new Factory();
    }

    private Factory() {
        //  新建一个 4 个线程的线程池
        mExecutor = Executors.newFixedThreadPool(4);
    }

    public static Application getApplicationContext() {
        return ITalkerApplication.getInstance();
    }

    /**
     * 异步运行的方法
     */
    public static void runOnAsync(Runnable runnable) {
        sInstance.mExecutor.execute(runnable);
    }
}
