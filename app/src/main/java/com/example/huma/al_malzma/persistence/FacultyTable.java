package com.example.huma.al_malzma.persistence;

import android.provider.BaseColumns;


public interface FacultyTable {
    //FacultyTable Table
    String NAME = "FacultyTable";

    String COLUMN_FACULTY_ID = BaseColumns._ID;
    String COLUMN_FK_UNIVERSITY_ID = "UniversityID";
    String COLUMN_FACULTY_NAME = "Name";

    String CREATE_FACULTY = "CREATE TABLE " + NAME + "(" +
            COLUMN_FACULTY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "FOREIGN KEY(" + COLUMN_FK_UNIVERSITY_ID + ")" + "REFERENCES " + UniversityTable.NAME + "(" + UniversityTable.COLUMN_UNIVERSITY_ID + "), " +
            COLUMN_FACULTY_NAME + " VARCHAR NOT NULL" +
            ")";
}
