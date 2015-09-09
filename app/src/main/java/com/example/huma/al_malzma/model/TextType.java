package com.example.huma.al_malzma.model;


import android.content.Context;

import com.parse.ParseClassName;

@ParseClassName("Text")
public class TextType extends BaseDataItem {

    private String title;
    private String text;


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
