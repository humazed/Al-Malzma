package com.example.huma.al_malzma.persistence;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SubjectsDataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "subjects";
    private static final String DB_SUFFIX = ".db";
    private static final int DB_VERSION = 1;


    public SubjectsDataBaseHelper(Context context) {
        super(context, DB_NAME + DB_SUFFIX, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UniversityTable.CREATE_UNIVERSITY);
        db.execSQL(FacultyTable.CREATE_FACULTY);
        db.execSQL(DepartmentTable.CREATE_DEPARTMENT);
        db.execSQL(GradeTable.CREATE_GRADE);
        db.execSQL(TermSubjectsTable.CREATE_TERM_SUBLETS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
