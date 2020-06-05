package com.example.mydiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DbOpenHelper {

    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(Databases.CreateDB.CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+Databases.CreateDB.TABLENAME);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }

    public long insertColumn(String title, String content, Date created, Date modified){
        ContentValues values = new ContentValues();

        values.put(Databases.CreateDB.TITLE, title);
        values.put(Databases.CreateDB.CONTENT, content);
        values.put(Databases.CreateDB.CREATED, String.valueOf(created));
        values.put(Databases.CreateDB.MODIFIED, String.valueOf(modified));
        return mDB.insert(Databases.CreateDB.TABLENAME, null, values);
    }

    // Update DB
    public boolean updateColumn(long id, String title, String content, Date created, Date modified){
        ContentValues values = new ContentValues();
        values.put(Databases.CreateDB.TITLE, title);
        values.put(Databases.CreateDB.CONTENT, content);
        values.put(Databases.CreateDB.CREATED, String.valueOf(created));
        values.put(Databases.CreateDB.MODIFIED, String.valueOf(modified));
        return mDB.update(Databases.CreateDB.TABLENAME, values, "_id=" + id, null) > 0;
    }

    // Delete All
    public void deleteAllColumns() {
        mDB.delete(Databases.CreateDB.TABLENAME, null, null);
    }

    // Delete DB
    public boolean deleteColumn(long id){
        return mDB.delete(Databases.CreateDB.TABLENAME, "_id="+id, null) > 0;
    }

    // Select DB
    public Cursor selectColumns(){
        return mDB.query(Databases.CreateDB.TABLENAME, null, null, null, null, null, null);
    }

    // sort by column
    public Cursor sortColumn(String sort){
        Cursor c = mDB.rawQuery( "SELECT * FROM Diary ORDER BY " + sort + ";", null);
        return c;
    }
}
