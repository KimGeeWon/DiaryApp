package com.example.mydiary;

import android.provider.BaseColumns;

public class Databases {
    public static final class CreateDB implements BaseColumns {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String CONTENT = "content";
        public static final String CREATED = "created";
        public static final String MODIFIED = "modified";
        public static final String TABLENAME = "Diary";

        public static final String CREATE = "create table if not exists "+TABLENAME+"("
                +ID+" INTEGER primary key autoincrement, "
                +TITLE+" TEXT not null , "
                +CONTENT+" TEXT not null , "
                +CREATED+" DATE not null , "
                +MODIFIED+" DATE );";
    }
}
