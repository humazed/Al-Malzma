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

    String mGrade;
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
            //get the user grade to show him the right subjects.
            mGrade = mCurrentUser.getString(ParseConstants.KEY_GRADE);
            //2 for the second term
            //TODO: replace it with dynamic code that know the current term using the data.
            mGrade += "2";

            Toast.makeText(this, mGrade, Toast.LENGTH_SHORT).show();
            Log.d("Grades: ", mGrade);

            //switch the user grade to show him the right subjects.
            switch (mGrade) {
                case "p0_1_2": //prep
                    mSubjects = this.getResources().getStringArray(R.array.subjects_p0_1_2);
                    break;
                case "n_1_2": //1
                    mSubjects = this.getResources().getStringArray(R.array.subjects_n_1_2);
                    break;
                case "n_2_2": //2
                    mSubjects = this.getResources().getStringArray(R.array.subjects_n_2_2);
                    break;
                case "n_3_2": //3
                    mSubjects = this.getResources().getStringArray(R.array.subjects_n_3_2);
                    break;
                case "n_4_2": //4
                    mSubjects = this.getResources().getStringArray(R.array.subjects_n_4_2);
                    break;
                default:
                    mSubjects = new String[]{};
                    mEmptyTextView.setText(getString(R.string.unexpected_error));
            }

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

    private String[] getSubjectsArr(String faculty, String department, String grade) {

        switch (faculty + department + grade) {
            case "p0_1_2": //prep
                mSubjects = this.getResources().getStringArray(R.array.subjects_p0_1_2);
                break;
            case "n_1_2": //1
                mSubjects = this.getResources().getStringArray(R.array.subjects_n_1_2);
                break;
            case "n_2_2": //2
                mSubjects = this.getResources().getStringArray(R.array.subjects_n_2_2);
                break;
            case "n_3_2": //3
                mSubjects = this.getResources().getStringArray(R.array.subjects_n_3_2);
                break;
            case "n_4_2": //4
                mSubjects = this.getResources().getStringArray(R.array.subjects_n_4_2);
                break;
            default:
                mSubjects = new String[]{};
                mEmptyTextView.setText(getString(R.string.unexpected_error));
        }

        return null;
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

