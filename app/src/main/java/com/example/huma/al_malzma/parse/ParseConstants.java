package com.example.huma.al_malzma.parse;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface ParseConstants {

    String CLASS_DATA = "Data";


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
    String KEY_PDF = "pdf";
    String KEY_PDF_DESCRIPTION = "description";

    //link
    String KEY_LINK = "link";
    String KEY_LINK_DESCRIPTION = "description";


    @StringDef({KEY_LECTURES, KEY_SECTIONS, KEY_ANNOUNCEMENTS})
    @Retention(RetentionPolicy.SOURCE) @interface FragmentSource {
    }

    String KEY_FRAGMENT_SOURCE = "fragmentSource";
    String KEY_LECTURES = "lectures";
    String KEY_SECTIONS = "sections";
    String KEY_ANNOUNCEMENTS = "announcements";

    @StringDef({KEY_TYPE_IMAGE, KEY_TYPE_PDF, KEY_TYPE_LINK})
    @Retention(RetentionPolicy.SOURCE) @interface DataType {
    }

    String KEY_DATA_TYPE = "dataType";
    String KEY_TYPE_IMAGE = "image";
    String KEY_TYPE_PDF = "pdf";
    String KEY_TYPE_LINK = "link";


    @StringDef({TYPE_IMAGE, TYPE_PDF})
    @Retention(RetentionPolicy.SOURCE) @interface FileType {
    }

    String TYPE_IMAGE = "image";
    String TYPE_PDF = "pdf";


    String KEY_CREATED_AT = "createdAt";
    String KEY_CURRENT_USER = "currentUser";

}
