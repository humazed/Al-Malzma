package com.example.huma.al_malzma.parse;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface ParseConstants {

    //ParseObjects names.
    String OBJECT_LECTURES = "lectures";
    String OBJECT_SECTIONS = "sections";
    String OBJECT_ANNOUNCEMENTS = "announcements";


    ///////////////////////////////////////////////////////////////////////////
    // KEYs
    ///////////////////////////////////////////////////////////////////////////
    // User keys
    String KEY_CREATOR = "creator";
    String KEY_UNIVERSITY = "university";
    String KEY_FACULTY = "faculty";
    String KEY_DEPARTMENT = "department";
    String KEY_GRADE = "grade";
    String KEY_TERM = "term";
    String KEY_WEEK = "week";
    String KEY_SUBJECT = "subject";

    //Image
    String KEY_IMAGE = "image";
    String KEY_IMAGE_DESCRIPTION = "description";

    //Pdf

    //link
    String KEY_LINK = "link";
    String KEY_LINK_DESCRIPTION = "description";


    String KEY_CREATED_AT = "createdAt";
    String KEY_CURRENT_USER = "currentUser";
    String KEY_SUBJECT_NAME = "subject";
    String KEY_QUOTE = "quote";
    String KEY_QUOTE_TEXT = "text";
    String KEY_TYPE = "type";
    String KEY_LECTURE_LINK = "lecture_link";


    @StringDef({TYPE_IMAGE, TYPE_PDF})
    @Retention(RetentionPolicy.SOURCE) @interface FileType {
    }

    String TYPE_IMAGE = "image";
    String  TYPE_PDF = "pdf";


}
