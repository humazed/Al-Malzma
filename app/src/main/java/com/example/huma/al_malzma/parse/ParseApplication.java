package com.example.huma.al_malzma.parse;

import android.app.Application;

import com.parse.Parse;


public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Add subclass.
//        ParseObject.registerSubclass(Lecture.class);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "dlSDesaqtwU7R2f4DjRG5oXmOOOX3ivgRAmlJjM7", "cI1fuhKaTOWmyvOsAgvqqL0n9RO5Z5bSCsbc3iCm");
    }
}
