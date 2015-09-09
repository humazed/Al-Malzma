package com.example.huma.al_malzma.model;


import android.content.Context;

import com.parse.ParseClassName;
import com.parse.ParseFile;

@ParseClassName("PDF")
public class PdfType extends BaseDataItem {

    private String description;

    ParseFile PDF;

    @Override
    public void saveToParse(Context context) {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
