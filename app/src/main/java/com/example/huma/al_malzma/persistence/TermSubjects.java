package com.example.huma.al_malzma.persistence;

import android.provider.BaseColumns;


public interface TermSubjects {
    //TermSubjects Table
    String TABLE_TERM_SUBLETS = "TermSubjects";

    String COLUMN_TERM_SUBLETS_ID = BaseColumns._ID;
    String COLUMN_FK_GRADE_ID = "GradeID";
    String COLUMN_T1_SUBJECTS = "T1Subjects";
    String COLUMN_T2_SUBJECTS = "T2Subjects";
    String COLUMN_COMMON_SUBJECTS = "CommonSubjects";

    String CREATE_TERM_SUBLETS = "CREATE TABLE " + TABLE_TERM_SUBLETS + "(" +
            COLUMN_TERM_SUBLETS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "FOREIGN KEY(" + COLUMN_FK_GRADE_ID + ")" + "REFERENCES " + Grade.TABLE_GRADE + "(" + Grade.COLUMN_GRADE_ID + "), " +
            COLUMN_T1_SUBJECTS + " VARCHAR NOT NULL" +
            COLUMN_T2_SUBJECTS + " VARCHAR NOT NULL" +
            COLUMN_COMMON_SUBJECTS + " VARCHAR NOT NULL" +
            ")";
}
