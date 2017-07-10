package com.google.jaaaule.gzw.italker;

import com.google.jaaaule.gzw.common.app.ITalkerApplication;
import com.google.jaaaule.gzw.factory.Factory;
import com.igexin.sdk.PushManager;

/**
 * Created by admin on 2017/7/7.
 */

public class App extends ITalkerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //  Factory 初始化
        Factory.setUp();
        //  推送初始化
        PushManager.getInstance().initialize(this);
    }
}
