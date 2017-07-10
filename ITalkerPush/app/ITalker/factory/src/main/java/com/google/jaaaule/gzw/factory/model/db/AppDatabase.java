package com.google.jaaaule.gzw.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by gzw10 on 2017/7/9.
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String NAME = "APP_DATABASE";
    public static final int VERSION = 1;
}
