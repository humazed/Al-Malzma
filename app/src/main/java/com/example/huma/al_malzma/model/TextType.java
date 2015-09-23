package com.example.huma.al_malzma.model;

import android.content.Context;

import com.example.huma.al_malzma.parse.ParseConstants;


public class TextType extends DataItem {
    private static final String TAG = TextType.class.getSimpleName();

    private String title;
    private String text;

    public TextType() {/*Default constructor required by parse */}

    public TextType(@ParseConstants.FragmentSource String fragmentSource) {
        super(fragmentSource, ParseConstants.KEY_TYPE_TEXT);
    }

    @Override
    public void saveToParse(Context context) {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
