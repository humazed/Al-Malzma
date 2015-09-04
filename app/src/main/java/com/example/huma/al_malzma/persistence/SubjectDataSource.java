package com.example.huma.al_malzma.persistence;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.huma.al_malzma.persistence.tables.SubjectsTable;

import java.util.ArrayList;

public class SubjectDataSource {
    public static final String TAG = SubjectDataSource.class.getSimpleName();


    Context mContext;
    SubjectsDataBaseHelper mSubjectsDataBaseHelper;

    public SubjectDataSource(Context context) {
        mContext = context;
        mSubjectsDataBaseHelper = new SubjectsDataBaseHelper(context);
        SQLiteDatabase db = mSubjectsDataBaseHelper.getReadableDatabase();
        db.close();
    }

    //open
    public SQLiteDatabase open() {
        return mSubjectsDataBaseHelper.getWritableDatabase();
    }

    //close
    public void close(SQLiteDatabase db) {
        db.close();
    }


    //return subjects[] according to passed prams
    public String[] getSubjects(String university, String faculty, String department, String grade, String term) {
        return getColumn(SubjectsTable.COLUMN_SUBJECT, university, faculty, department, grade, term);
    }

    public String[] getUniversities() {
        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.query(true,
                SubjectsTable.NAME,
                new String[]{SubjectsTable.COLUMN_UNIVERSITY},
                null, null, null, null, null, null
        );

        return getArrayFromCursor(db, cursor, SubjectsTable.COLUMN_UNIVERSITY);
    }

    public String[] getFaculties(String university) {
        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.query(true,
                SubjectsTable.NAME,
                new String[]{SubjectsTable.COLUMN_FACULTY},
                SubjectsTable.COLUMN_UNIVERSITY + "=? ",
                new String[]{university},
                null, null, null, null
        );

        return getArrayFromCursor(db, cursor, SubjectsTable.COLUMN_FACULTY);
    }

    public String[] getDepartments(String university, String faculty) {
        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.query(true,
                SubjectsTable.NAME,
                new String[]{SubjectsTable.COLUMN_DEPARTMENT},
                SubjectsTable.COLUMN_UNIVERSITY + "=? AND " +
                        SubjectsTable.COLUMN_FACULTY + "=? ",
                new String[]{university, faculty},
                null, null, null, null
        );

        return getArrayFromCursor(db, cursor, SubjectsTable.COLUMN_DEPARTMENT);
    }

    public String[] getGrades(String university, String faculty, String department) {
        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.query(true,
                SubjectsTable.NAME,
                new String[]{SubjectsTable.COLUMN_GRADE},
                SubjectsTable.COLUMN_UNIVERSITY + "=? AND " +
                        SubjectsTable.COLUMN_FACULTY + "=? AND " +
                        SubjectsTable.COLUMN_DEPARTMENT + "=? ",
                new String[]{university, faculty, department},
                null, null, null, null
        );

        return getArrayFromCursor(db, cursor, SubjectsTable.COLUMN_GRADE);
    }

    public String[] getTerms(String university, String faculty, String department) {
        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.query(true,
                SubjectsTable.NAME,
                new String[]{SubjectsTable.COLUMN_GRADE},
                SubjectsTable.COLUMN_UNIVERSITY + "=? AND " +
                        SubjectsTable.COLUMN_FACULTY + "=? AND " +
                        SubjectsTable.COLUMN_DEPARTMENT + "=? ",
                new String[]{university, faculty, department},
                null, null, null, null
        );

        return getArrayFromCursor(db, cursor, SubjectsTable.COLUMN_GRADE);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helper methods
    ///////////////////////////////////////////////////////////////////////////
    public String[] getColumn(String column, String university, String faculty, String department, String grade,
                              String term) {
        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.query(true,
                SubjectsTable.NAME,
                new String[]{column},
                SubjectsTable.COLUMN_UNIVERSITY + "=? AND " +
                        SubjectsTable.COLUMN_FACULTY + "=? AND " +
                        SubjectsTable.COLUMN_DEPARTMENT + "=? AND " +
                        SubjectsTable.COLUMN_GRADE + "=? AND " +
                        SubjectsTable.COLUMN_TERM + "=? ",
                new String[]{university, faculty, department, grade, term},
                null, null, null, null
        );

        return getArrayFromCursor(db, cursor, column);
    }

    @NonNull
    private String[] getArrayFromCursor(SQLiteDatabase db, Cursor cursor, String column) {
        ArrayList<String> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                list.add(getStringFromColumnName(cursor, column));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);

        return list.toArray(new String[list.size()]);
    }

    private int getIntFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }

    private String getStringFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }
}
