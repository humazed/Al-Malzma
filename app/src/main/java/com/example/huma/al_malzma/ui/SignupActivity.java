package com.example.huma.al_malzma.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.rey.material.widget.ProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignupActivity extends AppCompatActivity {
    @Bind(R.id.name_edit_text) EditText mNameEditText;
    @Bind(R.id.email_edit_text) EditText mEmailEditText;
    @Bind(R.id.password_edit_text) EditText mPasswordEditText;
    @Bind(R.id.password_confirm_edit_text) EditText mPasswordConfirmEditText;
    @Bind(R.id.progress_view) ProgressView mProgressView;
    @Bind(R.id.department_text_view) TextView mDepartmentTextView;

    @Bind(R.id.grade_text_view) TextView mGradeTextView;
    @Bind(R.id.faculty_spinner) Spinner mFacultySpinner;
    @Bind(R.id.department_spinner) Spinner mDepartmentSpinner;
    @Bind(R.id.grade_spinner) Spinner mGradeSpinner;
    String mName, mEmail, mPassword, mPasswordConfirm;

    String mFaculty, mDepartment, mGrade;


    private static final String SYSTEMS_AND_COMPUTER_ENGINEERING = "systems and computer engineering";
    private static final String PETROL_ENGINEERING = "petrol engineering";
    private static final String ELECTRICAL_ENGINEERING = "electrical engineering";
    private static final String ARCHITECTURE_ENGINEERING = "architecture engineering";
    private static final String CIVIL_ENGINEERING = "civil engineering";
    private static final String MECHANICAL_ENGINEERING = "mechanical engineering";
    private static final String URBAN_DESIGN_ENGINEERING = "urban design engineering";
    private static final String ERROR = "error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        /*
        *the construct of grade which passed to parse.com is as following
        * department_grade_  and then at main add the term
        * so the full grade will be department_grade_term
        * for prep I used "0"
        * and for the rest of departments used the first letter of them.
        */

        mFacultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mFaculty = "engineering";
                        showDepartment();
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            private void showDepartment() {
                mDepartmentTextView.setVisibility(View.VISIBLE);
                mDepartmentSpinner.setVisibility(View.VISIBLE);
            }
        });

        //Let the user choose his Department.
        mDepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mDepartment = "prep"; //prep
                        hideGrade();
                        break;
                    case 1:
                        mDepartment = SYSTEMS_AND_COMPUTER_ENGINEERING;
                        showGrade();
                        break;
                    case 2:
                        mDepartment = PETROL_ENGINEERING;
                        showGrade();
                        Toast.makeText(SignupActivity.this, R.string.coming_soon_message, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        mDepartment = ELECTRICAL_ENGINEERING;
                        showGrade();
                        Toast.makeText(SignupActivity.this, R.string.coming_soon_message, Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        mDepartment = ARCHITECTURE_ENGINEERING;
                        showGrade();
                        Toast.makeText(SignupActivity.this, R.string.coming_soon_message, Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        mDepartment = CIVIL_ENGINEERING;
                        showGrade();
                        Toast.makeText(SignupActivity.this, R.string.coming_soon_message, Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        mDepartment = MECHANICAL_ENGINEERING;
                        showGrade();
                        Toast.makeText(SignupActivity.this, R.string.coming_soon_message, Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        mDepartment = URBAN_DESIGN_ENGINEERING;
                        showGrade();
                        Toast.makeText(SignupActivity.this, R.string.coming_soon_message, Toast.LENGTH_SHORT).show();
                        break;
                    default:    //error state case.
                        mDepartment = ERROR;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                builder.setTitle(getString(R.string.generic_error_title))
                        .setMessage(R.string.department_select_message)
                        .setPositiveButton(android.R.string.ok, null)
                        .create().show();
            }

            private void showGrade() {
                mGradeSpinner.setVisibility(View.VISIBLE);
                mGradeTextView.setVisibility(View.VISIBLE);
            }

            private void hideGrade() {
                mGradeSpinner.setVisibility(View.INVISIBLE);
                mGradeTextView.setVisibility(View.INVISIBLE);
            }

        });

        //let user choose the Grade.
        mGradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mGrade = "1";
                        break;
                    case 1:
                        mGrade = "2";
                        break;
                    case 2:
                        mGrade = "3";
                        break;
                    case 3:
                        mGrade = "4";
                        break;
                    default:
                        mGrade = "0"; //error.
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                builder.setTitle(getString(R.string.generic_error_title))
                        .setMessage(R.string.grade_select_message)
                        .setPositiveButton(android.R.string.ok, null)
                        .create().show();
            }
        });

    }

    @OnClick(R.id.signup_button)
    void signup() {
        mName = mNameEditText.getText().toString().trim().toLowerCase();
        mEmail = mEmailEditText.getText().toString().trim().toLowerCase();
        mPassword = mPasswordEditText.getText().toString().trim().toLowerCase();
        mPasswordConfirm = mPasswordConfirmEditText.getText().toString().trim().toLowerCase();

        if (mDepartment.equals("0")) mGrade = "0";

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
                user.put(ParseConstants.KEY_FACULTY, mFaculty);
                user.put(ParseConstants.KEY_DEPARTMENT, mDepartment);
                user.put(ParseConstants.KEY_GRADE, mGrade);

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
