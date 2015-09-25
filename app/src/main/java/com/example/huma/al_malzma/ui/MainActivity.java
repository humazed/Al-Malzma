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

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.helper.Utility;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.example.huma.al_malzma.persistence.SubjectDataSource;
import com.github.clans.fab.FloatingActionButton;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.subjects_listView) ListView mSubjectsListView;
    @Bind(R.id.empty_text_view) TextView mEmptyTextView;
    @Bind(R.id.fab) FloatingActionButton mFab;

    public static final String KEY_UNIVERSITY = "university";
    public static final String KEY_FACULTY = "faculty";
    public static final String KEY_DEPARTMENT = "department";
    public static final String KEY_GRADE = "grade";
    public static final String KEY_TERM = "term";
    public static final String KEY_SUBJECT_NAME = "subjectName";

    public static ParseUser mCurrentUser;

    SubjectDataSource mSubjectDataSource;

    String mUniversity, mFaculty, mDepartment, mGrade, mTerm;

    String[] mSubjects;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSubjectDataSource = new SubjectDataSource(this);

        mCurrentUser = ParseUser.getCurrentUser();
        if (mCurrentUser == null) {
            // show the signup or login screen
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        // do stuff with the user
        else {
            //get the user FacultyTable, DepartmentTable and GradeTable to show him the right subjects.
            mUniversity = mCurrentUser.getString(ParseConstants.KEY_UNIVERSITY);
            mFaculty = mCurrentUser.getString(ParseConstants.KEY_FACULTY);
            mDepartment = mCurrentUser.getString(ParseConstants.KEY_DEPARTMENT);
            mGrade = mCurrentUser.getString(ParseConstants.KEY_GRADE);
            mTerm = Utility.getCurrentTerm();

            Log.d(TAG, "onCreate " + mUniversity + mFaculty + mDepartment + mGrade + mTerm);

            mSubjects = mSubjectDataSource.getSubjects(mUniversity, mFaculty, mDepartment, mGrade, mTerm);

            mSubjectsListView.setEmptyView(mEmptyTextView);
            mSubjectsListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mSubjects));
            mSubjectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //pass the subject name and Grades to WeeksActivity to let the user choose week.
                    Intent intent = new Intent(MainActivity.this, WeeksActivity.class);
                    intent.putExtra(KEY_UNIVERSITY, mUniversity);
                    intent.putExtra(KEY_FACULTY, mFaculty);
                    intent.putExtra(KEY_DEPARTMENT, mDepartment);
                    intent.putExtra(KEY_GRADE, mGrade);
                    intent.putExtra(KEY_TERM, mTerm);
                    intent.putExtra(KEY_SUBJECT_NAME, mSubjects[position]);
                    startActivity(intent);
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
            ParseUser.logOut(); // FIXME: 9/23/2015 when parse SDK fixed make it logoutInBackground
            mCurrentUser = ParseUser.getCurrentUser(); // this will now be null
            startActivity(new Intent(this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}

