package com.example.huma.al_malzma.persistence.tables;


import android.provider.BaseColumns;

public interface SubjectsTable {
    //SubjectsTable Table
    String NAME = "SubjectsTable";

    String COLUMN_SUBLET_ID = BaseColumns._ID;

    String COLUMN_UNIVERSITY = "University";
    String COLUMN_FACULTY = "Faculty";
    String COLUMN_DEPARTMENT = "Department";
    String COLUMN_GRADE = "Grade";
    String COLUMN_TERM = "Term";
    String COLUMN_SUBJECT = "Subject";


    String CREATE = "CREATE TABLE " + NAME + "(" +
            COLUMN_SUBLET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_UNIVERSITY + " VARCHAR NOT NULL, " +
            COLUMN_FACULTY + " VARCHAR NOT NULL, " +
            COLUMN_DEPARTMENT + " VARCHAR NOT NULL, " +
            COLUMN_GRADE + " VARCHAR NOT NULL, " +
            COLUMN_TERM + " VARCHAR NOT NULL, " +
            COLUMN_SUBJECT + " VARCHAR NOT NULL " +
            ");";
}
