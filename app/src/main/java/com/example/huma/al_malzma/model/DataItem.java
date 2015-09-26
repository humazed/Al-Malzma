package com.example.huma.al_malzma.model;

import com.example.huma.al_malzma.helper.Utility;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.example.huma.al_malzma.ui.SubjectActivity;
import com.parse.ParseObject;
import com.parse.ParseUser;

public abstract class DataItem extends ParseObject {
    private static final String TAG = DataItem.class.getSimpleName();


    private static ParseUser creator = ParseUser.getCurrentUser();

    // Fields that identify the ParseObject and help when retrieving it
    private static String university = creator.getString(ParseConstants.KEY_UNIVERSITY);
    private static String faculty = creator.getString(ParseConstants.KEY_FACULTY);
    private static String department = creator.getString(ParseConstants.KEY_DEPARTMENT);
    private static String grade = creator.getString(ParseConstants.KEY_GRADE);

    private static String term = Utility.getCurrentTerm();

    private static String subject = SubjectActivity.subjectName;
    private static String week = SubjectActivity.week;


    public static String getUniversity() {
        return university;
    }

    public static void setUniversity(String university) {
        DataItem.university = university;
    }

    public static String getFaculty() {
        return faculty;
    }

    public static void setFaculty(String faculty) {
        DataItem.faculty = faculty;
    }

    public static String getDepartment() {
        return department;
    }

    public static void setDepartment(String department) {
        DataItem.department = department;
    }

    public static String getGrade() {
        return grade;
    }

    public static void setGrade(String grade) {
        DataItem.grade = grade;
    }

    public static String getTerm() {
        return term;
    }

    public static void setTerm(String term) {
        DataItem.term = term;
    }

    public static String getSubject() {
        return subject;
    }

    public static void setSubject(String subject) {
        DataItem.subject = subject;
    }

    public static String getWeek() {
        return week;
    }

    public static void setWeek(String week) {
        DataItem.week = week;
    }
}
