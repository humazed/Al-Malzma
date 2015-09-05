package com.example.huma.al_malzma.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.huma.al_malzma.model.data.JsonAttributes;
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


    private int mPreviousVisibleItem;

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
            mTerm = getCurrentTerm();

            Log.d(TAG, "onCreate " + mUniversity + mFaculty + mDepartment + mGrade + mTerm);

            mSubjects = mSubjectDataSource.getSubjects(mUniversity, mFaculty, mDepartment, mGrade, mTerm);

            // Set the adapter with subjects.
            mSubjectsListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mSubjects));
            mSubjectsListView.setEmptyView(mEmptyTextView);
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

    @NonNull
    private String getCurrentTerm() {
        //TODO: replace it with dynamic code that know the current term using the data.
        return JsonAttributes.TERM_1;
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

}

