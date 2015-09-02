package com.example.huma.al_malzma.persistence;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SubjectDataSource {

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

    //insert
    public void insert() {

    }
}
