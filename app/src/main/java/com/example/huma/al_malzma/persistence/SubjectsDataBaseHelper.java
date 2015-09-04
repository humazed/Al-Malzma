package com.example.huma.al_malzma.persistence;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.helper.JsonHelper;
import com.example.huma.al_malzma.model.data.JsonAttributes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SubjectsDataBaseHelper extends SQLiteOpenHelper {
    public static final String TAG = SubjectsDataBaseHelper.class.getSimpleName();

    private static final String DB_NAME = "subjects";
    private static final String DB_SUFFIX = ".db";
    private static final int DB_VERSION = 1;

    private static SubjectsDataBaseHelper mInstance;
    private final Resources mResources;


    public SubjectsDataBaseHelper(Context context) {
        super(context, DB_NAME + DB_SUFFIX, null, DB_VERSION);
        mResources = context.getResources();
    }

    private static SubjectsDataBaseHelper getInstance(Context context) {
        if (null == mInstance) {
            mInstance = new SubjectsDataBaseHelper(context);
        }
        return mInstance;
    }

    private void preFillDatabase(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            try {
                fillAll(db);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, "preFillDatabase", e);
        }
    }

    private void fillAll(SQLiteDatabase db) throws JSONException, IOException {
        JSONArray universities = new JSONArray(readCategoriesFromResources());
        JSONObject university;
        for (int i = 0; i < universities.length(); i++) {
            university = universities.getJSONObject(i);
            String universityName = university.getString(JsonAttributes.NAME);
            JSONArray faculties = university.getJSONArray(JsonAttributes.FACULTIES);

            getFaculties(db, faculties, universityName);
        }
    }

    private String readCategoriesFromResources() throws IOException {
        StringBuilder categoriesJson = new StringBuilder();
        InputStream rawCategories = mResources.openRawResource(R.raw.subjects);
        BufferedReader reader = new BufferedReader(new InputStreamReader(rawCategories));
        String line;

        while ((line = reader.readLine()) != null) {
            categoriesJson.append(line);
        }
        return categoriesJson.toString();
    }

    private void getFaculties(SQLiteDatabase db, JSONArray faculties, String universityName) throws JSONException {
        JSONObject faculty;
        for (int i = 0; i < faculties.length(); i++) {
            faculty = faculties.getJSONObject(i);
            String facultyName = faculty.getString(JsonAttributes.NAME);
            JSONArray departments = faculty.getJSONArray(JsonAttributes.DEPARTMENTS);

            getDepartments(db, departments, universityName, facultyName);
        }
    }

    private void getDepartments(SQLiteDatabase db, JSONArray departments, String universityName,
                                String facultyName) throws JSONException {
        JSONObject department;
        for (int i = 0; i < departments.length(); i++) {
            department = departments.getJSONObject(i);
            String departmentName = department.getString(JsonAttributes.NAME);
            JSONArray grades = department.getJSONArray(JsonAttributes.GRADES);

            getGrades(db, grades, universityName, facultyName, departmentName);
        }

    }

    private void getGrades(SQLiteDatabase db, JSONArray grades, String universityName, String facultyName,
                           String departmentName) throws JSONException {
        JSONObject grade;
        for (int i = 0; i < grades.length(); i++) {
            grade = grades.getJSONObject(i);
            int gradeName = grade.getInt(JsonAttributes.NAME);

            JSONArray t0Subjects = grade.getJSONArray(JsonAttributes.TERM_0);
            JSONArray t1Subjects = grade.getJSONArray(JsonAttributes.TERM_1);
            JSONArray t2Subjects = grade.getJSONArray(JsonAttributes.TERM_2);

            getT0Subjects(db, t0Subjects, universityName, facultyName, departmentName, gradeName);
            getT1Subjects(db, t1Subjects, universityName, facultyName, departmentName, gradeName);
            getT2Subjects(db, t2Subjects, universityName, facultyName, departmentName, gradeName);
        }
    }

    private void getT0Subjects(SQLiteDatabase db, JSONArray t1Subjects, String university, String faculty,
                               String department, int grade) throws JSONException {
        String[] T0Subjects = JsonHelper.jsonArrayToStringArray(t1Subjects.toString());

        for (String subject : T0Subjects) {
            Log.d(TAG, "getT1Subjects " + university + faculty + department + grade + subject);

            ContentValues values = new ContentValues();
            values.put(SubjectsTable.COLUMN_UNIVERSITY, university);
            values.put(SubjectsTable.COLUMN_FACULTY, faculty);
            values.put(SubjectsTable.COLUMN_DEPARTMENT, department);
            values.put(SubjectsTable.COLUMN_GRADE, grade);
            values.put(SubjectsTable.COLUMN_TERM, 0);
            values.put(SubjectsTable.COLUMN_SUBJECT, subject);

            db.insert(SubjectsTable.NAME, null, values);
        }
    }

    private void getT1Subjects(SQLiteDatabase db, JSONArray t1Subjects, String university, String faculty,
                               String department, int grade) throws JSONException {
        String[] T1Subjects = JsonHelper.jsonArrayToStringArray(t1Subjects.toString());

        for (String subject : T1Subjects) {
            Log.d(TAG, "getT1Subjects " + university + faculty + department + grade + subject);

            ContentValues values = new ContentValues();
            values.put(SubjectsTable.COLUMN_UNIVERSITY, university);
            values.put(SubjectsTable.COLUMN_FACULTY, faculty);
            values.put(SubjectsTable.COLUMN_DEPARTMENT, department);
            values.put(SubjectsTable.COLUMN_GRADE, grade);
            values.put(SubjectsTable.COLUMN_TERM, 1);
            values.put(SubjectsTable.COLUMN_SUBJECT, subject);

            db.insert(SubjectsTable.NAME, null, values);
        }
    }

    private void getT2Subjects(SQLiteDatabase db, JSONArray t2Subjects, String university, String faculty,
                               String department, int grade) throws JSONException {
        String[] T2Subjects = JsonHelper.jsonArrayToStringArray(t2Subjects.toString());

        for (String subject : T2Subjects) {
            Log.d(TAG, "getT1Subjects " + university + faculty + department + grade + subject);

            ContentValues values = new ContentValues();
            values.put(SubjectsTable.COLUMN_UNIVERSITY, university);
            values.put(SubjectsTable.COLUMN_FACULTY, faculty);
            values.put(SubjectsTable.COLUMN_DEPARTMENT, department);
            values.put(SubjectsTable.COLUMN_GRADE, grade);
            values.put(SubjectsTable.COLUMN_TERM, 2);
            values.put(SubjectsTable.COLUMN_SUBJECT, subject);

            db.insert(SubjectsTable.NAME, null, values);
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SubjectsTable.CREATE);

        preFillDatabase(db); //this will be called once when Database is first created.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
