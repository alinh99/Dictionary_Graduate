package com.vnuk.tuhocandroid.dictionary.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelperAnhViet extends SQLiteAssetHelper {
    private static final String DATABASE_ANH_VIET = "anh_viet.db";
    private static final int DATABASE_VERSION = 1;


    public DatabaseOpenHelperAnhViet(Context context) {
        super(context, DATABASE_ANH_VIET, null, DATABASE_VERSION);
    }
}
