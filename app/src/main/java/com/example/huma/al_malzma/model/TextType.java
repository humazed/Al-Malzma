package com.example.huma.al_malzma.model;


import android.content.Context;

//@ParseClassName("Text")
public class TextType extends BaseDataItem {

    private String title;
    private String text;

    public TextType() {/*Default constructor required by parse */}

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
