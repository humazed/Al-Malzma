package com.example.huma.al_malzma.model;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.model.data.JsonAttributes;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.example.huma.al_malzma.ui.SubjectActivity;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import java.util.Calendar;


public abstract class BaseDataItem extends ParseObject {
    public static final String TAG = BaseDataItem.class.getSimpleName();

    private Thread mThread;

    /**
     * in this class the member variables getters and setters are normal noe's because all of them
     * can be got without user direct input and save them to parse in the constructor.
     * <p>
     * but in the classes that inherit from him getters and setters will deal with parse directly
     * because as they say in there site this is the recommenced approach.
     * https://www.parse.com/docs/android/guide#objects-accessors-mutators-and-methods
     */

    private ParseUser creator = ParseUser.getCurrentUser();

    // Fields that identify the ParseObject and help when retrieving it
    private String university = getCreator().getString(ParseConstants.KEY_UNIVERSITY);
    private String faculty = getCreator().getString(ParseConstants.KEY_FACULTY);
    private String department = getCreator().getString(ParseConstants.KEY_DEPARTMENT);
    private String grade = getCreator().getString(ParseConstants.KEY_GRADE);

    private String term = getCurrentTerm();

    public String subject = SubjectActivity.subjectName;
    public String week = SubjectActivity.week;

    private String FragmentSource; //ie: lecture,section or announcement. get it from SourceTypes.java

    //put the identifiers that can be got easily.
    public BaseDataItem() {
        put(ParseConstants.KEY_CREATOR, creator);
        put(ParseConstants.KEY_UNIVERSITY, university);
        put(ParseConstants.KEY_FACULTY, faculty);
        put(ParseConstants.KEY_DEPARTMENT, department);
        put(ParseConstants.KEY_GRADE, grade);
        put(ParseConstants.KEY_TERM, term);
        put(ParseConstants.KEY_SUBJECT, subject);
        put(ParseConstants.KEY_WEEK, week);
    }


    public abstract void saveToParse(Context context);

    protected void saveInBackgroundWithAlertDialog(final Context context) {
        saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(context.getString(R.string.generic_error_title))
                            .setMessage(R.string.connection_error)
                            .setPositiveButton(android.R.string.ok, null)
                            .create().show();
                    Log.d(TAG, "Fail: ", e);
                } else {
                    Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "done ");
                }
            }
        });
    }


    MaterialDialog dialog = null;

    protected void saveInBackgroundWithAlertDialogAndProgressDialog(final Context context, ParseFile parseFile) {
        new MaterialDialog.Builder(context)
                .title(R.string.uploading)
                .content(R.string.please_wait)
                .contentGravity(GravityEnum.CENTER)
                .progress(false, 100, true)
                .cancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                    }
                })
                .showListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog = (MaterialDialog) dialogInterface;
                    }
                }).show();
        parseFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    //fail
                    Log.d(TAG, "fail ", e);
                } else {
                    //succeed
                    Log.d(TAG, "done ");
                    dialog.setContent(context.getString(R.string.done));
                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(final Integer percentDone) {
                // Update your progress spinner here. percentDone will be between 0 and 100.
                dialog.setProgress(percentDone);
            }
        });
    }

    public boolean add() {
        return true;
    }

    public void remove() {

    }

    public void edit() {

    }

    @NonNull
    public static String getCurrentTerm() {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        if (Calendar.SEPTEMBER <= currentMonth || currentMonth <= Calendar.FEBRUARY) {
            return JsonAttributes.TERM_1;
        } else {
            return JsonAttributes.TERM_2;
        }
    }


    public ParseUser getCreator() {
        return creator;
    }

    public void setCreator(ParseUser creator) {
        this.creator = creator;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getFragmentSource() {
        return FragmentSource;
    }

    public void setFragmentSource(String fragmentSource) {
        this.FragmentSource = fragmentSource;
    }
}
