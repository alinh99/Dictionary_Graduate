package com.vnuk.tuhocandroid.dictionary.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataAccessAnhViet {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DataAccessAnhViet instance;

    public DataAccessAnhViet(Context context) {
        this.openHelper = new DatabaseOpenHelperAnhViet (context);
    }

    public DataAccessAnhViet() {

    }

    public static DataAccessAnhViet getInstance(Context context) {
        if (instance == null) {
            instance = new DataAccessAnhViet (context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */

    // mở dữ liệu để đọc
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */

    // đóng dữ liệu lại
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    // show ra tất cả các từ
    public List<String> getWords() {
        List<String> list = new ArrayList<> ();
        Cursor cursor = database.rawQuery("SELECT * FROM anh_viet", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    // random từ
    public ArrayList<String> getRandomWords() {
        ArrayList<String> list = new ArrayList<> ();
        Random r = new Random ();
        int start = r.nextInt(300 - 0) + 300;
        int end = start + 50;
        Cursor cursor = database.rawQuery("SELECT * FROM anh_viet limit "+ start +","+ end +"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    // lấy ra từ để random
    public ArrayList<String> getWords(int start, int end) {
        ArrayList<String> list = new ArrayList<> ();
        Cursor cursor = database.rawQuery("SELECT * FROM anh_viet limit "+ start +","+ end +"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


    public ArrayList<String> getWords(String filter) {
        ArrayList<String> list = new ArrayList<> ();
        Cursor cursor = database.rawQuery("SELECT * FROM anh_viet where word like '"+ filter +"%' limit 10", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

     // định nghĩa từ
    public String getDefinition(String word) {
        String definition = "";
        Cursor cursor = database.rawQuery("SELECT * FROM anh_viet where word='"+ word +"'", null);
        cursor.moveToFirst();
        definition  = cursor.getString(2);
        cursor.close();
        return definition;
    }

    public List<String> insertYourWord(String word){
        List<String> list = new ArrayList<> ();
        word = word.replaceAll ( " ' ", "\\'" );
        Cursor cursor = database.rawQuery ( "INSERT INTO your_word(word) VALUES ('"+ word +"')",null );
        while (cursor.moveToNext ()){
            cursor.getString ( cursor.getColumnIndexOrThrow ( "word" ) );
        }
        cursor.close ();
        return list;
    }

    public Cursor getYourWord(){
        Cursor cursor = database.rawQuery ( "SELECT DISTINCT word FROM your_word ORDER BY id DESC",null );
        return cursor;
    }

    public Cursor getMeaning(String text){
        Cursor cursor = database.rawQuery ( "SELECT word FROM anh_viet WHERE word='"+text+"'",null );
        return cursor;
    }

    public void clearHistoryYourWord(){
        database.execSQL ( "DELETE FROM your_word" );
    }
    
}
