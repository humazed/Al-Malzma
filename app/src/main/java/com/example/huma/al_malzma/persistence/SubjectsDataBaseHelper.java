package com.example.huma.al_malzma.persistence;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.huma.al_malzma.R;
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
        ContentValues values = new ContentValues(); // reduce, reuse
        JSONArray jsonArray = new JSONArray(readCategoriesFromResources());
        JSONObject university;
        for (int i = 0; i < jsonArray.length(); i++) {
            university = jsonArray.getJSONObject(i);
            final String universityName = university.getString(JsonAttributes.NAME);
            fillCategory(db, values, university, universityName);
            final JSONArray quizzes = university.getJSONArray(JsonAttributes.QUIZZES);
            fillQuizzesForCategory(db, values, quizzes, universityName);
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

    private void fillCategory(SQLiteDatabase db, ContentValues values, JSONObject category,
                              String categoryId) throws JSONException {
        values.clear();
        values.put(CategoryTable.COLUMN_ID, categoryId);
        values.put(CategoryTable.COLUMN_NAME, category.getString(JsonAttributes.NAME));
        values.put(CategoryTable.COLUMN_THEME, category.getString(JsonAttributes.THEME));
        values.put(CategoryTable.COLUMN_SOLVED, category.getString(JsonAttributes.SOLVED));
        values.put(CategoryTable.COLUMN_SCORES, category.getString(JsonAttributes.SCORES));
        db.insert(CategoryTable.NAME, null, values);
    }

    private void fillQuizzesForCategory(SQLiteDatabase db, ContentValues values, JSONArray quizzes,
                                        String categoryId) throws JSONException {
        JSONObject quiz;
        for (int i = 0; i < quizzes.length(); i++) {
            quiz = quizzes.getJSONObject(i);
            values.clear();
            values.put(QuizTable.FK_CATEGORY, categoryId);
            values.put(QuizTable.COLUMN_TYPE, quiz.getString(JsonAttributes.TYPE));
            values.put(QuizTable.COLUMN_QUESTION, quiz.getString(JsonAttributes.QUESTION));
            values.put(QuizTable.COLUMN_ANSWER, quiz.getString(JsonAttributes.ANSWER));
            db.insert(QuizTable.NAME, null, values);
        }
    }

    /**
     * Resets the contents of Topeka's database to it's initial state.
     *
     * @param context The context this is running in.
     */
    public static void reset(Context context) {
        SQLiteDatabase writableDatabase = getWritableDatabase(context);
        writableDatabase.delete(CategoryTable.NAME, null, null);
        writableDatabase.delete(QuizTable.NAME, null, null);
        getInstance(context).preFillDatabase(writableDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UniversityTable.CREATE_UNIVERSITY);
        db.execSQL(FacultyTable.CREATE_FACULTY);
        db.execSQL(DepartmentTable.CREATE_DEPARTMENT);
        db.execSQL(GradeTable.CREATE_GRADE);
        db.execSQL(TermSubjectsTable.CREATE_TERM_SUBLETS);

        preFillDatabase(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
