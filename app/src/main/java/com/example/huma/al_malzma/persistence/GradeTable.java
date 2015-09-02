package com.example.huma.al_malzma.persistence;

import android.provider.BaseColumns;


public interface GradeTable {
    //GradeTable Table
    String NAME = "GradeTable";

    String COLUMN_GRADE_ID = BaseColumns._ID;
    String COLUMN_FK_DEPARTMENT_ID = "DepartmentID";
    String COLUMN_GRADE_NAME = "Name";

    String CREATE_GRADE = "CREATE TABLE " + NAME + "(" +
            COLUMN_GRADE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "FOREIGN KEY(" + COLUMN_FK_DEPARTMENT_ID + ")" + "REFERENCES " + DepartmentTable.NAME + "(" + DepartmentTable.COLUMN_DEPARTMENT_ID + "), " +
            COLUMN_GRADE_NAME + " VARCHAR NOT NULL" +
            ")";
}
