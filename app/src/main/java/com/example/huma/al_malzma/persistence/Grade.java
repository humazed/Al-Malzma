package com.example.huma.al_malzma.persistence;

import android.provider.BaseColumns;


public interface Grade {
    //Grade Table
    String TABLE_GRADE = "Grade";

    String COLUMN_GRADE_ID = BaseColumns._ID;
    String COLUMN_FK_DEPARTMENT_ID = "DepartmentID";
    String COLUMN_GRADE_NAME = "Name";

    String CREATE_GRADE = "CREATE TABLE " + TABLE_GRADE + "(" +
            COLUMN_GRADE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "FOREIGN KEY(" + COLUMN_FK_DEPARTMENT_ID + ")" + "REFERENCES " + Department.TABLE_DEPARTMENT + "(" + Department.COLUMN_DEPARTMENT_ID + "), " +
            COLUMN_GRADE_NAME + " VARCHAR NOT NULL" +
            ")";
}
