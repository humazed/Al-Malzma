package com.example.huma.al_malzma.persistence;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.model.data.JsonAttributes;
import com.example.huma.al_malzma.ui.SubjectActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DbActivity extends AppCompatActivity {
    public static final String TAG = DbActivity.class.getSimpleName();

    SubjectDataSource mSubjectDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        ButterKnife.bind(this);

        mSubjectDataSource = new SubjectDataSource(this);
    }

    @OnClick(R.id.save_button)
    void save() {
        String[] subjects = mSubjectDataSource.getSubjects(JsonAttributes.Universities.AlAzharCairo.UNIVERSITY_AL_AZHAR_CAIRO,
                JsonAttributes.Universities.AlAzharCairo.Faculties.Engineering.FACULTY_ENGINEERING,
                JsonAttributes.Universities.AlAzharCairo.Faculties.Engineering.Departments.DEPARTMENT_SYSTEMS_AND_COMPUTER_ENGINEERING,
                JsonAttributes.Grades.GRADE_1,
                JsonAttributes.TERM_1);

        for (String subject : subjects) {
            Log.d(TAG, "save " + subject);
        }

    }

    @OnClick(R.id.query_button)
    void query() {
//        String[] strings = mSubjectDataSource.getGrades(JsonAttributes.Universities.AlAzharCairo.UNIVERSITY_AL_AZHAR_CAIRO,
//                JsonAttributes.Universities.AlAzharCairo.Faculties.Engineering.FACULTY_ENGINEERING,
//                JsonAttributes.Universities.AlAzharCairo.Faculties.Engineering.Departments.DEPARTMENT_SYSTEMS_AND_COMPUTER_ENGINEERING
//        );
//
////        String[] string = mSubjectDataSource.getDepartments(JsonAttributes.Universities.AlAzharCairo.UNIVERSITY_AL_AZHAR_CAIRO,
////                JsonAttributes.Universities.AlAzharCairo.Faculties.Engineering.FACULTY_ENGINEERING
////        );
//
////        String[] strings = mSubjectDataSource.getFaculties(JsonAttributes.Universities.AlAzharCairo.UNIVERSITY_AL_AZHAR_CAIRO);
//
////        String[] strings = mSubjectDataSource.getUniversities();
//
//        for (String s : strings) {
//            Log.d(TAG, "query " + s);
//        }



    }
}
