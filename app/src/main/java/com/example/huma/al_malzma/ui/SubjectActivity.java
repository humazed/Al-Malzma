package com.example.huma.al_malzma.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.ui.subject_fragments.AnnouncementsFragment;
import com.example.huma.al_malzma.ui.subject_fragments.LecturesFragment;
import com.example.huma.al_malzma.ui.subject_fragments.SectionsFragment;

import java.util.Locale;

public class SubjectActivity extends AppCompatActivity {
    public static final String TAG = SubjectActivity.class.getSimpleName();

    public static String university;
    public static String faculty;
    public static String department;
    public static String grade;
    public static String term;
    public static String subjectName;
    public static String week;

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: 9/9/2015 I didn't use any of that things except subjectName an week. so  try delete them when you finish.
        Intent mainIntent = getIntent().getParcelableExtra(WeeksActivity.KEY_MAIN_INTENT);
        if (mainIntent != null && mainIntent.getStringExtra(MainActivity.KEY_SUBJECT_NAME) != null) {
            university = mainIntent.getStringExtra(MainActivity.KEY_UNIVERSITY);
            faculty = mainIntent.getStringExtra(MainActivity.KEY_FACULTY);
            department = mainIntent.getStringExtra(MainActivity.KEY_DEPARTMENT);
            grade = mainIntent.getStringExtra(MainActivity.KEY_GRADE);
            term = mainIntent.getStringExtra(MainActivity.KEY_TERM);
            subjectName = mainIntent.getStringExtra(MainActivity.KEY_SUBJECT_NAME);

        }
        if (getIntent().getStringExtra(WeeksActivity.KEY_WEEK) != null)
            week = getIntent().getStringExtra(WeeksActivity.KEY_WEEK);


        Log.d(TAG, "onResume " + university + faculty + department + grade + term + subjectName + week);

        setTitle(subjectName);
    }


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case 0:
                    return new LecturesFragment();
                case 1:
                    return new SectionsFragment();
                case 2:
                    return new AnnouncementsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }
}
