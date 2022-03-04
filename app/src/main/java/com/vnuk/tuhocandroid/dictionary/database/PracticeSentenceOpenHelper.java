package com.vnuk.tuhocandroid.dictionary.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

class PracticeSentenceOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_ANH_VIET_PRACTICE_SENTENCE = "practice_sentence.db";
    private static final int DATABASE_VERSION_PRACTICE = 1;

    public PracticeSentenceOpenHelper(Context context) {
        super(context, DATABASE_ANH_VIET_PRACTICE_SENTENCE, null, DATABASE_VERSION_PRACTICE);
    }
}
