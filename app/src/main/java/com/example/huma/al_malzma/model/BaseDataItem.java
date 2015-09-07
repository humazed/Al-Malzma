package com.example.huma.al_malzma.model;

import android.support.annotation.NonNull;

import com.example.huma.al_malzma.model.data.JsonAttributes;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Calendar;


public abstract class BaseDataItem extends ParseObject {

    private ParseUser creator = ParseUser.getCurrentUser();

    // Fields that identify the ParseObject and help when retrieving it
    private String university = getCreator().getString(ParseConstants.KEY_UNIVERSITY);
    private String faculty = getCreator().getString(ParseConstants.KEY_FACULTY);
    private String department = getCreator().getString(ParseConstants.KEY_DEPARTMENT);
    private String grade = getCreator().getString(ParseConstants.KEY_GRADE);

    private String term = getCurrentTerm();

    private String subject;
    private String week;

    private String FragmentSource; //ie: lecture,section or announcement. get it from SourceTypes.java

    public abstract void create();

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
