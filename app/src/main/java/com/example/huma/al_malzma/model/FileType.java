package com.example.huma.al_malzma.model;


import com.parse.ParseFile;

public class FileType extends BaseDataItem {

    public static final String TYPE_PDF = "pdf";
    public static final String TYPE_IMAGE = "img";

    private String description;
    private String Type;

    ParseFile file;

    @Override
    public void create() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
