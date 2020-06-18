package com.example.mydiary;

import android.provider.BaseColumns;

import java.util.Date;

public class Databases {
    public static final class DBdata implements BaseColumns {
        public static final String TABLENAME = "Diary";

        public static final String TITLE = "title";
        public static final String CONTENT = "content";
        public static final String CREATED = "created";
        public static final String MODIFIED = "modified";

        public static final String CREATE = String.format(
          "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT not null, %s TEXT not null, " +
                  "%s TEXT not null DEFAULT (datetime('now', 'localtime')), " +
                  "%s TEXT)",
                TABLENAME,
                _ID,
                TITLE,
                CONTENT,
                CREATED,
                MODIFIED
        );

        public static final String DELETE = "DROP TABLE IF EXISTS " + TABLENAME;
    }
}
