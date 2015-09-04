package com.example.huma.al_malzma.persistence;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    public String[] getSubjects(String university, String faculty, String department, int grade, String term) {
        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.query(SubjectsTable.NAME,
                new String[]{SubjectsTable.COLUMN_SUBJECT},
                SubjectsTable.COLUMN_UNIVERSITY + "=? AND " +
                        SubjectsTable.COLUMN_FACULTY + "=? AND " +
                        SubjectsTable.COLUMN_DEPARTMENT + "=? AND " +
                        SubjectsTable.COLUMN_GRADE + "=? AND " +
                        SubjectsTable.COLUMN_TERM + "=? ",
                new String[]{university, faculty, department, String.valueOf(grade), term},
                null,
                null,
                null
        );

        ArrayList<String> subjectsList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                subjectsList.add(getStringFromColumnName(cursor, SubjectsTable.COLUMN_SUBJECT));
            } while (cursor.moveToNext());
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);

        return subjectsList.toArray(new String[subjectsList.size()]);
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
