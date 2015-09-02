package com.example.huma.al_malzma.persistence;

import android.provider.BaseColumns;


public interface UniversityTable {
    //UniversityTable Table
    String NAME = "UniversityTable";

    String COLUMN_UNIVERSITY_ID = BaseColumns._ID;
    String COLUMN_UNIVERSITY_NAME = "Name";

    String CREATE_UNIVERSITY = "CREATE TABLE " + NAME + "(" +
            COLUMN_UNIVERSITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_UNIVERSITY_NAME + " VARCHAR NOT NULL" + ")";
}
