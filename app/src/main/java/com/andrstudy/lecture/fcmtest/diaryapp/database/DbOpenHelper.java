package com.andrstudy.lecture.fcmtest.diaryapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Diary.db";
    public static final int DB_VERSION = 2;
    private static DbOpenHelper sInstance;

    public static DbOpenHelper getsInstance(Context context) {
        if(sInstance == null) {
            sInstance = new DbOpenHelper(context);
        }
        return sInstance;
    }

    public DbOpenHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Databases.DBdata.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Databases.DBdata.DELETE);
        onCreate(db);
    }
}
