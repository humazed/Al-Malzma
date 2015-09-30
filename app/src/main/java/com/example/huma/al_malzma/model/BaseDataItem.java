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
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseDataItem extends ParseObject {
    private static final String TAG = DataItem.class.getSimpleName();


    private ParseUser currentUser = ParseUser.getCurrentUser();

    // Fields that identify the ParseObject and help when retrieving it
    private String university = currentUser.getString(ParseConstants.KEY_UNIVERSITY);
    private String currentUserName = currentUser.getUsername();
    private String faculty = currentUser.getString(ParseConstants.KEY_FACULTY);
    private String department = currentUser.getString(ParseConstants.KEY_DEPARTMENT);
    private String grade = currentUser.getString(ParseConstants.KEY_GRADE);

    private String term = Utility.getCurrentTerm();

    private String subject = SubjectActivity.subjectName;
    private String week = SubjectActivity.week;

    private String fragmentSource; //ie: lecture, section or announcement. get it from ParseConstants.java
    private String dataType; //ie: PDF, Image or link. get it from ParseConstants.java

    private int positiveVotes = 0;
    private int negativeVotes = 0;

    private List<ParseUser> positiveVoters;
    private List<ParseUser> negativeVoters;


    public BaseDataItem() { /*Default constructor required by parse */ }

    public BaseDataItem(@ParseConstants.FragmentSource String fragmentSource,
                        @ParseConstants.DataType String dataType) {
        put(ParseConstants.KEY_FRAGMENT_SOURCE, fragmentSource);
        put(ParseConstants.KEY_DATA_TYPE, dataType);

        putIdentifiers();
    }

    private void putIdentifiers() {
        put(ParseConstants.KEY_CREATOR, currentUser);
        put(ParseConstants.KEY_CREATOR_NAME, currentUserName);
        put(ParseConstants.KEY_UNIVERSITY, university);
        put(ParseConstants.KEY_FACULTY, faculty);
        put(ParseConstants.KEY_DEPARTMENT, department);
        put(ParseConstants.KEY_GRADE, grade);
        put(ParseConstants.KEY_TERM, term);
        put(ParseConstants.KEY_SUBJECT, subject);
        put(ParseConstants.KEY_WEEK, week);
        add(ParseConstants.KEY_POSITIVE_VOTERS_NAMES, "");
        add(ParseConstants.KEY_NEGATIVE_VOTERS_NAMES, "");
    }


    /**
     * in this class the member variables getters and setters are normal noe's because all of them
     * can be got without user direct input and save them to parse in the constructor.
     * <p/>
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

    private void saveInBackgroundWithLog() {
        saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(TAG, "done() returned: " + true);
                } else {
                    Log.e(TAG, "done: fail: ", e);
                }
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


    public ParseUser getCreator() {
        return getParseUser(ParseConstants.KEY_CREATOR);
    }

    public String getCreatorName() {
        return getString(ParseConstants.KEY_CREATOR_NAME);
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


    public void incrementPositiveVotes() {
        addToPositiveVoters();
        saveInBackgroundWithLog();
    }

    public void incrementNegativeVotes() {
        addToNegativeVoters();
        saveInBackgroundWithLog();
    }

    public int getPositiveVotes() {
        return getPositiveVotersNames().size();
    }

    public int getNegativeVotesCount() {
        return getPositiveVotersNames().size();
    }

    public int getVotes() {
        return getPositiveVotes() - getNegativeVotesCount();
    }

    public List<ParseUser> getPositiveVotersNames() {
        return getList(ParseConstants.KEY_POSITIVE_VOTERS_NAMES);
    }

    public void addToPositiveVoters() {
        addUnique(ParseConstants.KEY_POSITIVE_VOTERS_NAMES, currentUserName);
        saveInBackgroundWithLog();
    }

    public List<ParseUser> getNegativeVotersNames() {
        return getList(ParseConstants.KEY_NEGATIVE_VOTERS_NAMES);
    }

    public void addToNegativeVoters() {
        addUnique(ParseConstants.KEY_NEGATIVE_VOTERS_NAMES, currentUserName);
        saveInBackgroundWithLog();
    }

    public void removeFromPositiveVoters() {
        ArrayList<String> nameToRemove = new ArrayList<>();
        nameToRemove.add(currentUserName);
        removeAll(ParseConstants.KEY_POSITIVE_VOTERS_NAMES, nameToRemove);
        saveInBackgroundWithLog();
    }

    public void removeFromNegativeVoters() {
        ArrayList<String> nameToRemove = new ArrayList<>();
        nameToRemove.add(currentUserName);
        removeAll(ParseConstants.KEY_NEGATIVE_VOTERS_NAMES, nameToRemove);
        saveInBackgroundWithLog();
    }


}
