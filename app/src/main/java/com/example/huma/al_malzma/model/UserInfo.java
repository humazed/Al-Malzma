package com.example.huma.al_malzma.model;

import com.example.huma.al_malzma.helper.Utility;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.example.huma.al_malzma.ui.SubjectActivity;
import com.parse.ParseObject;
import com.parse.ParseUser;

public abstract class UserInfo extends ParseObject {
    private static final String TAG = UserInfo.class.getSimpleName();


    private static ParseUser currentUser = ParseUser.getCurrentUser();

    // Fields that identify the ParseObject and help when retrieving it
    private static String userName = currentUser.getUsername();

    private static String university = currentUser.getString(ParseConstants.KEY_UNIVERSITY);
    private static String faculty = currentUser.getString(ParseConstants.KEY_FACULTY);
    private static String department = currentUser.getString(ParseConstants.KEY_DEPARTMENT);
    private static String grade = currentUser.getString(ParseConstants.KEY_GRADE);

    private static String term = Utility.getCurrentTerm();

    private static String subject = SubjectActivity.subjectName;
    private static String week = SubjectActivity.week;


    public static String getUniversity() {
        return university;
    }

    public static String getFaculty() {
        return faculty;
    }

    public static String getDepartment() {
        return department;
    }

    public static String getGrade() {
        return grade;
    }

    public static String getTerm() {
        return term;
    }

    public static String getSubject() {
        return subject;
    }

    public static String getWeek() {
        return week;
    }

    public static ParseUser getCurrentUser() {
        return currentUser;
    }

    public static String getUserName() {
        return userName;
    }

}
