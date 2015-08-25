package com.example.huma.al_malzma.parse;

import android.app.Application;

import com.example.huma.al_malzma.subject_model.Lecture;
import com.parse.Parse;
import com.parse.ParseObject;


public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Add subclass.
        ParseObject.registerSubclass(Lecture.class);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "dlSDesaqtwU7R2f4DjRG5oXmOOOX3ivgRAmlJjM7", "cI1fuhKaTOWmyvOsAgvqqL0n9RO5Z5bSCsbc3iCm");
    }
}
