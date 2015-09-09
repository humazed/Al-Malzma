package com.example.huma.al_malzma.parse;

import android.app.Application;

import com.example.huma.al_malzma.model.ImageType;
import com.example.huma.al_malzma.model.LinkType;
import com.example.huma.al_malzma.model.PdfType;
import com.example.huma.al_malzma.model.TextType;
import com.parse.Parse;
import com.parse.ParseObject;


public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Add subclass.
        ParseObject.registerSubclass(ImageType.class);
        ParseObject.registerSubclass(PdfType.class);
        ParseObject.registerSubclass(LinkType.class);
        ParseObject.registerSubclass(TextType.class);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "dlSDesaqtwU7R2f4DjRG5oXmOOOX3ivgRAmlJjM7", "cI1fuhKaTOWmyvOsAgvqqL0n9RO5Z5bSCsbc3iCm");
    }
}
