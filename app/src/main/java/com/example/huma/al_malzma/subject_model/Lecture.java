package com.example.huma.al_malzma.subject_model;

import com.parse.ParseClassName;


@ParseClassName("Lecture")
public class Lecture extends BaseDataItem {

    public static final String TYPE_LECTURE = "lecture";

    public Lecture() { /*default empty constructor*/
    }

    public Lecture(int week) {
        super(TYPE_LECTURE, week);
    }
}
