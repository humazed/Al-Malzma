package com.example.huma.al_malzma.model;

import com.example.huma.al_malzma.parse.ParseConstants;
import com.parse.ParseObject;
import com.parse.ParseUser;


public abstract class BaseDataItem extends ParseObject {

    ParseUser curranUser = ParseUser.getCurrentUser();

    // Fields that identify the ParseObject and help when retrieving it
    String mCollege = curranUser.getString(ParseConstants.KEY_FACULTY);
    String mDepartment = curranUser.getString(ParseConstants.KEY_DEPARTMENT);
    String mGrade = curranUser.getString(ParseConstants.KEY_GRADE);

    int mWeek; // week will be get from Activity

    String mType; // Lecture or Section


    // Data fields
    String mNote;
    // TODO: 8/25/2015 add other fields

    public BaseDataItem() {
    }

    public BaseDataItem(String type, int week) {
        mType = type;
        mWeek = week;
    }
}
