package com.example.huma.al_malzma.model;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.helper.Utility;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.example.huma.al_malzma.ui.SubjectActivity;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

@ParseClassName(ParseConstants.CLASS_DATA)
public abstract class DataItem extends ParseObject {
    private static final String TAG = DataItem.class.getSimpleName();


    private ParseUser creator = ParseUser.getCurrentUser();

    // Fields that identify the ParseObject and help when retrieving it
    private String university = creator.getString(ParseConstants.KEY_UNIVERSITY);
    private String faculty = creator.getString(ParseConstants.KEY_FACULTY);
    private String department = creator.getString(ParseConstants.KEY_DEPARTMENT);
    private String grade = creator.getString(ParseConstants.KEY_GRADE);
    public final String className =
            String.format("%s_%s_%s_%s", university, faculty, department, grade);


    private String term = Utility.getCurrentTerm();

    private String subject = SubjectActivity.subjectName;
    private String week = SubjectActivity.week;

    private String fragmentSource; //ie: lecture, section or announcement. get it from ParseConstants.java
    private String dataType; //ie: PDF, Image or link. get it from ParseConstants.java

    private int positiveVotes = 0;
    private int negativeVotes = 0;


    public DataItem() { /*Default constructor required by parse */ }

    public DataItem(@ParseConstants.FragmentSource String fragmentSource,
                    @ParseConstants.DataType String dataType) {
        put(ParseConstants.KEY_FRAGMENT_SOURCE, fragmentSource);
        put(ParseConstants.KEY_DATA_TYPE, dataType);

        putIdentifiers();
    }

    private void putIdentifiers() {
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
                        dialog.setContent(context.getString(R.string.fail));
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


    public int getPositiveVotes() {
        return positiveVotes;
    }

    public void setPositiveVotes(int positiveVotes) {
        this.positiveVotes = positiveVotes;
    }

    public int getNegativeVotes() {
        return negativeVotes;
    }

    public void setNegativeVotes(int negativeVotes) {
        this.negativeVotes = negativeVotes;
    }

    public ParseUser getCreator() {
        return creator;
    }

    public String getTerm() {
        return term;
    }

    public String getSubject() {
        return subject;
    }

    public String getWeek() {
        return week;
    }

    public String getFragmentSource() {
        return fragmentSource;
    }

    public String getDataType() {
        return dataType;
    }

}
