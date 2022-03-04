package com.vnuk.tuhocandroid.dictionary.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

public class DataAccessPracticeSentence {
    private SQLiteOpenHelper openHelperSentence;
    private SQLiteDatabase databaseSentence;
    private static DataAccessPracticeSentence instance;

    public DataAccessPracticeSentence(Context context) {
        this.openHelperSentence = new PracticeSentenceOpenHelper (context);
    }

    public static DataAccessPracticeSentence getInstance(Context context) {
        if (instance == null) {
            instance = new DataAccessPracticeSentence (context);
        }
        return instance;
    }

    public void openSentenceDB(){
        this.databaseSentence=openHelperSentence.getWritableDatabase ();
    }

    public void close() {
        if (databaseSentence != null) {
            this.databaseSentence.close();
        }
    }

    public ArrayList<String> getRandomSentence() {
        ArrayList<String> list = new ArrayList<> ();
        Random r = new Random ();
        int start = r.nextInt (10-0)+0;
        int end = start+10;
        Cursor cursor = databaseSentence.rawQuery("SELECT * FROM practice_sentence limit "+ start +","+ end +"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
