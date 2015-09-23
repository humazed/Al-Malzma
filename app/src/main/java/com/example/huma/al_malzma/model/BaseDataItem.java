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
import com.example.huma.al_malzma.helper.Utility;
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
    private static final String TAG = BaseDataItem.class.getSimpleName();


    private static ParseUser creator = ParseUser.getCurrentUser();

    // Fields that identify the ParseObject and help when retrieving it
    private static String university = getCreator().getString(ParseConstants.KEY_UNIVERSITY);
    private static String faculty = getCreator().getString(ParseConstants.KEY_FACULTY);
    private static String department = getCreator().getString(ParseConstants.KEY_DEPARTMENT);
    private static String grade = getCreator().getString(ParseConstants.KEY_GRADE);

    private static String term = getCurrentTerm();

    private static String subject;
    private static String week;

    private String FragmentSource; //ie: lecture, section or announcement. get it from ParseConstants.java
    private String DataType; //ie: PDF, Image or link. get it from ParseConstants.java


    //put the identifiers that can be got easily.
    public BaseDataItem() {
        setSubject(SubjectActivity.subjectName);
        setWeek(SubjectActivity.week);
    }

    public static void putIdentifiers(BaseDataItem parseObject) {
        parseObject.put(ParseConstants.KEY_CREATOR, getCreator());
        parseObject.put(ParseConstants.KEY_UNIVERSITY, getUniversity());
        parseObject.put(ParseConstants.KEY_FACULTY, getFaculty());
        parseObject.put(ParseConstants.KEY_DEPARTMENT, getDepartment());
        parseObject.put(ParseConstants.KEY_GRADE, getGrade());
        parseObject.put(ParseConstants.KEY_TERM, getTerm());
        parseObject.put(ParseConstants.KEY_SUBJECT, getSubject());
        parseObject.put(ParseConstants.KEY_WEEK, getWeek());
    }

    /**
     * in this class the member variables getters and setters are normal noe's because all of them
     * can be got without user direct input and save them to parse in the constructor.
     * <p>
     * but in the classes that inherit from him getters and setters will deal with parse directly
     * because as they say in there site this is the recommenced approach.
     * https://www.parse.com/docs/android/guide#objects-accessors-mutators-and-methods
     */


    public abstract void saveToParse(Context context);

    protected void saveInBackgroundWithAlertDialog(final Context context) {
        if (Utility.isNetworkAvailableWithToast(context)) {
            saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle(context.getString(R.string.generic_error_title))
                                .setMessage(R.string.connection_error)
                                .setPositiveButton(android.R.string.ok, null)
                                .create().show();
                        Log.e(TAG, "Fail: ", e);
                    } else {
                        Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "done ");
                    }
                }
            });
        } else {
            //no network.
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.generic_error_title)
                    .setMessage(R.string.connection_error)
                    .setPositiveButton(android.R.string.ok, null)
                    .create().show();

        }
    }


    MaterialDialog dialog = null;

    protected void saveFileInBackgroundWithProgressDialog(final Context context, ParseFile parseFile) {
        if (Utility.isNetworkAvailableWithToast(context)) {
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
                        Log.e(TAG, "fail ", e);
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

    public static ParseUser getCreator() {
        return creator;
    }

    public static void setCreator(ParseUser creator) {
        BaseDataItem.creator = creator;
    }

    public static String getUniversity() {
        return university;
    }

    public static void setUniversity(String university) {
        BaseDataItem.university = university;
    }

    public static String getFaculty() {
        return faculty;
    }

    public static void setFaculty(String faculty) {
        BaseDataItem.faculty = faculty;
    }

    public static String getDepartment() {
        return department;
    }

    public static void setDepartment(String department) {
        BaseDataItem.department = department;
    }

    public static String getGrade() {
        return grade;
    }

    public static void setGrade(String grade) {
        BaseDataItem.grade = grade;
    }

    public static String getTerm() {
        return term;
    }

    public static void setTerm(String term) {
        BaseDataItem.term = term;
    }

    public static String getSubject() {
        return subject;
    }

    public static void setSubject(String subject) {
        BaseDataItem.subject = subject;
    }

    public static String getWeek() {
        return week;
    }

    public static void setWeek(String week) {
        BaseDataItem.week = week;
    }


    public String getFragmentSource() {
        return getString(ParseConstants.KEY_FRAGMENT_SOURCE);
    }

    public void setFragmentSource(@ParseConstants.FragmentSource String fragmentSource) {
        put(ParseConstants.KEY_FRAGMENT_SOURCE, fragmentSource);
    }

    public String getDataType() {
        return getString(ParseConstants.KEY_DATA_TYPE);
    }

    public void setDataType(@ParseConstants.DataType String dataType) {
        put(ParseConstants.KEY_DATA_TYPE, dataType);
    }

}
