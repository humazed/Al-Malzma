package com.example.huma.al_malzma.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.example.huma.al_malzma.persistence.SubjectDataSource;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.rey.material.widget.ProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignupActivity extends AppCompatActivity {
    public static final String TAG = SignupActivity.class.getSimpleName();

    @Bind(R.id.name_edit_text) EditText mNameEditText;
    @Bind(R.id.email_edit_text) EditText mEmailEditText;
    @Bind(R.id.password_edit_text) EditText mPasswordEditText;
    @Bind(R.id.password_confirm_edit_text) EditText mPasswordConfirmEditText;
    @Bind(R.id.progress_view) ProgressView mProgressView;
    @Bind(R.id.department_text_view) TextView mDepartmentTextView;

    @Bind(R.id.faculty_text_view) TextView mFacultyTextView;
    @Bind(R.id.grade_text_view) TextView mGradeTextView;

    @Bind(R.id.university_spinner) Spinner mUniversitySpinner;
    @Bind(R.id.faculty_spinner) Spinner mFacultySpinner;
    @Bind(R.id.department_spinner) Spinner mDepartmentSpinner;
    @Bind(R.id.grade_spinner) Spinner mGradeSpinner;

    String mName, mEmail, mPassword, mPasswordConfirm;

    String mSelectedUniversity, mSelectedFaculty, mSelectedDepartment, mSelectedGrade;
    String[] mFaculties, mDepartments, mGrades;

    private SubjectDataSource mSubjectDataSource;

    @OnClick(R.id.test_button)
    void test() {
        Log.d(TAG, "test " + mSelectedUniversity);
        Log.d(TAG, "test " + mSelectedFaculty);
        Log.d(TAG, "test " + mSelectedDepartment);
        Log.d(TAG, "test " + mSelectedGrade);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        mSubjectDataSource = new SubjectDataSource(this);

        /*
        *the construct of grade which passed to parse.com is as following
        * department_grade_  and then at main add the term
        * so the full grade will be department_grade_term
        * for prep I used "0"
        * and for the rest of mDepartments used the first letter of them.
        */

        ///////////////////////////////////////////////////////////////////////////
        // START: Handle spinners entries and visibly.
        ///////////////////////////////////////////////////////////////////////////
        final String[] universities = mSubjectDataSource.getUniversities();
        mUniversitySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, universities));
        mUniversitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedUniversity = universities[position];
                mFaculties = mSubjectDataSource.getFaculties(mSelectedUniversity);

                mFacultyTextView.setVisibility(View.VISIBLE);
                mFacultySpinner.setVisibility(View.VISIBLE);

                mFacultySpinner.setAdapter(new ArrayAdapter<>(SignupActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, mFaculties));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mFacultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedFaculty = mFaculties[position];
                mDepartments = mSubjectDataSource.getDepartments(mSelectedUniversity, mSelectedFaculty);

                mDepartmentTextView.setVisibility(View.VISIBLE);
                mDepartmentSpinner.setVisibility(View.VISIBLE);

                mDepartmentSpinner.setAdapter(new ArrayAdapter<>(SignupActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, mDepartments));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedDepartment = mDepartments[position];
                mGrades = mSubjectDataSource.getGrades(mSelectedUniversity, mSelectedFaculty, mSelectedDepartment);

                if (!mGrades[0].equals("0")) {
                    mGradeTextView.setVisibility(View.VISIBLE);
                    mGradeSpinner.setVisibility(View.VISIBLE);

                    mGradeSpinner.setAdapter(new ArrayAdapter<>(SignupActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, mGrades));
                } else {
                    mSelectedGrade = "0";
                    mGradeTextView.setVisibility(View.INVISIBLE);
                    mGradeSpinner.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mGradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedGrade = mGrades[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ///////////////////////////////////////////////////////////////////////////
        // END.
        ///////////////////////////////////////////////////////////////////////////
    }

    @OnClick(R.id.signup_button)
    void signup() {
        mName = mNameEditText.getText().toString().trim().toLowerCase();
        mEmail = mEmailEditText.getText().toString().trim().toLowerCase();
        mPassword = mPasswordEditText.getText().toString().trim().toLowerCase();
        mPasswordConfirm = mPasswordConfirmEditText.getText().toString().trim().toLowerCase();


        //if user leave any thing empty show him AlertDialog.
        if (mName.isEmpty() || mEmail.isEmpty() || mPassword.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
            builder.setTitle(getString(R.string.generic_error_title))
                    .setMessage(R.string.signup_error_message)
                    .setPositiveButton(android.R.string.ok, null)
                    .create().show();
        }
        //Create new ParseUser after confirming the password.
        else {
            //confirm password
            if (mPassword.equals(mPasswordConfirm)) {
                mProgressView.setVisibility(View.VISIBLE);

                //pass user data to parse.
                ParseUser user = new ParseUser();
                user.setUsername(mName);
                user.setPassword(mPassword);
                user.setEmail(mEmail);
                user.put("pass", mPassword);
                user.put(ParseConstants.KEY_UNIVERSITY, mSelectedUniversity);
                user.put(ParseConstants.KEY_FACULTY, mSelectedFaculty);
                user.put(ParseConstants.KEY_DEPARTMENT, mSelectedDepartment);
                user.put(ParseConstants.KEY_GRADE, mSelectedGrade);

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        mProgressView.setVisibility(View.INVISIBLE);

                        if (e == null) {
                            // Hooray! Let them use the app now.
                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            // Sign up didn't succeed. Look at the ParseException to figure out what went wrong.
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                            builder.setTitle(getString(R.string.generic_error_title))
                                    .setMessage(e.getMessage())
                                    .setPositiveButton(android.R.string.ok, null)
                                    .create().show();
                        }
                    }
                });
            } else {
                //password not matching >> show the user AlertDialog.
                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                builder.setTitle(getString(R.string.generic_error_title))
                        .setMessage(getString(R.string.password_not_match_error_message))
                        .setPositiveButton(android.R.string.ok, null)
                        .create().show();
            }
        }
    }

}
