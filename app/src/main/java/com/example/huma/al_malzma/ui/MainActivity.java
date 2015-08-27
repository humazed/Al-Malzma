package com.example.huma.al_malzma.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.model.data.Faculties;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.github.clans.fab.FloatingActionButton;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.subjects_listView) ListView mSubjectsListView;
    @Bind(R.id.empty_text_view) TextView mEmptyTextView;
    @Bind(R.id.fab) FloatingActionButton mFab;


    public static ParseUser mCurrentUser;

    String mFaculty, mDepartment, mGrade, mTerm;

    String[] mSubjects;
    private int mPreviousVisibleItem;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mCurrentUser = ParseUser.getCurrentUser();
        if (mCurrentUser == null) {
            // show the signup or login screen
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        // do stuff with the user
        else {
            //get the user Faculty, Department and Grade to show him the right subjects.
            mFaculty = mCurrentUser.getString(ParseConstants.KEY_FACULTY);
            mDepartment = mCurrentUser.getString(ParseConstants.KEY_DEPARTMENT);
            mGrade = mCurrentUser.getString(ParseConstants.KEY_GRADE);
            //2 for the second term
            //TODO: replace it with dynamic code that know the current term using the data.
            mTerm = Faculties.TERM_2;

            Toast.makeText(this, mGrade, Toast.LENGTH_SHORT).show();
            Log.d("Grades: ", mGrade);

            mSubjects = getSubjectsArr(mFaculty, mDepartment, mGrade, mTerm);

            // Set the adapter with subjects.
            mSubjectsListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mSubjects));
            mSubjectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //pass the subject name and Grades to the SubjectActivity to use it making ParseObject.
//                    Intent intent = new Intent(MainActivity.this, SubjectActivity.class);
//                    intent.putExtra(Constants.KET_GRADE, mGrade);
//                    intent.putExtra(Constants.KEY_SUBJECT_NAME, mSubjects[position]);
//                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            //Logout and take the use to LoginActivity
            ParseUser.logOut();
            mCurrentUser = ParseUser.getCurrentUser(); // this will now be null
            startActivity(new Intent(this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    // Get Subjects array by search in: Faculties >> Departments >> Grades >> Terms. and then get it from xml.
    private String[] getSubjectsArr(String faculty, String department, String grade, String term) {
        String[] subjects = new String[0];
        //switch the user Faculty, Department and Grade to show him the right subjects.
        switch (faculty) {
            case Faculties.Engineering.FACULTY_ENGINEERING:
                switch (department) {
                    case Faculties.Engineering.Departments.DEPARTMENT_PREP:
                        switch (term) {
                            case Faculties.TERM_1:
                                break;
                            case Faculties.TERM_2:
                                subjects = this.getResources().getStringArray(R.array.subjects_p0_1_2);
                                break;
                        }
                        break;
                    case Faculties.Engineering.Departments.DEPARTMENT_SYSTEMS_AND_COMPUTER_ENGINEERING:
                        switch (grade) {
                            case Faculties.Engineering.Grades.GRADE_1:
                                switch (term) {
                                    case Faculties.TERM_1:
                                        break;
                                    case Faculties.TERM_2:
                                        subjects = this.getResources().getStringArray(R.array.subjects_n_1_2);
                                        break;
                                }
                                break;
                            case Faculties.Engineering.Grades.GRADE_2:
                                switch (term) {
                                    case Faculties.TERM_1:
                                        break;
                                    case Faculties.TERM_2:
                                        subjects = this.getResources().getStringArray(R.array.subjects_n_2_2);
                                        break;
                                }
                                break;
                            case Faculties.Engineering.Grades.GRADE_3:
                                switch (term) {
                                    case Faculties.TERM_1:
                                        break;
                                    case Faculties.TERM_2:
                                        subjects = this.getResources().getStringArray(R.array.subjects_n_3_2);
                                        break;
                                }
                                break;
                            case Faculties.Engineering.Grades.GRADE_4:
                                switch (term) {
                                    case Faculties.TERM_1:
                                        break;
                                    case Faculties.TERM_2:
                                        subjects = this.getResources().getStringArray(R.array.subjects_n_4_2);
                                        break;
                                }
                                break;
                        }
                        break;
                    case Faculties.Engineering.Departments.DEPARTMENT_PETROL_ENGINEERING:
                        break;
                    case Faculties.Engineering.Departments.DEPARTMENT_ELECTRICAL_ENGINEERING:
                        break;
                    case Faculties.Engineering.Departments.DEPARTMENT_ARCHITECTURE_ENGINEERING:
                        break;
                    case Faculties.Engineering.Departments.DEPARTMENT_CIVIL_ENGINEERING:
                        break;
                    case Faculties.Engineering.Departments.DEPARTMENT_MECHANICAL_ENGINEERING:
                        break;
                    case Faculties.Engineering.Departments.DEPARTMENT_URBAN_DESIGN_ENGINEERING:
                        break;
                }
                break;
        }
        return subjects;
    }


}

