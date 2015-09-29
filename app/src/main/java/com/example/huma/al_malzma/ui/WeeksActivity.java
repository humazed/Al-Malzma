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

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeeksActivity extends AppCompatActivity {
    public static final String TAG = WeeksActivity.class.getSimpleName();

    @Bind(R.id.empty_text_view) TextView mEmptyTextView;
    @Bind(R.id.weeks_list_view) ListView mWeeksListView;

    public static final String KEY_MAIN_INTENT = "MainIntent";
    public static final String KEY_WEEK = "week";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weeks);
        ButterKnife.bind(this);

        // TODO: 9/6/2015 try to make custom adapter and view that contain subTitle date of the week and big number in the left.

        mWeeksListView.setEmptyView(mEmptyTextView);

        final String[] weeks = getResources().getStringArray(R.array.weeks);
        mWeeksListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                weeks));

        mWeeksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //go to SubjectActivity carrying all information needed to saveToParse or retrieve write thing.
                Intent intent = new Intent(WeeksActivity.this, SubjectActivity.class);
                intent.putExtra(KEY_WEEK, weeks[position]);
                //pass intent that come from MainActivity that contain every thing.
                intent.putExtra(KEY_MAIN_INTENT, getIntent());
                startActivity(intent);

                Log.d(TAG, "onItemClick " + weeks[position]);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weeks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
