package com.google.jaaaule.gzw.factory.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

/**
 * Created by gzw10 on 2017/7/9.
 */

public class DBFlowExclusionStrategies implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        //  被跳过的字段
        //  只要属于 DBFlow 数据库就跳过
        return f.getDeclaredClass().equals(ModelAdapter.class);
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        //  被跳过的类
        return false;
    }
}

