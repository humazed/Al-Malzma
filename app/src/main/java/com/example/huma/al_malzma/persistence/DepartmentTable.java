package com.example.huma.al_malzma.persistence;

import android.provider.BaseColumns;


public interface DepartmentTable {
    //DepartmentTable Table
    String NAME = "DepartmentTable";

    String COLUMN_DEPARTMENT_ID = BaseColumns._ID;
    String COLUMN_FK_FACULTY_ID = "FacultyID";
    String COLUMN_DEPARTMENT_NAME = "Name";

    String CREATE_DEPARTMENT = "CREATE TABLE " + NAME + "(" +
            COLUMN_DEPARTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "FOREIGN KEY(" + COLUMN_FK_FACULTY_ID + ")" + "REFERENCES " + FacultyTable.NAME + "(" + FacultyTable.COLUMN_FACULTY_NAME + "), " +
            COLUMN_DEPARTMENT_NAME + " VARCHAR NOT NULL" +
            ")";
}
