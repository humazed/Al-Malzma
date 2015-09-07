package com.example.huma.al_malzma.model;


public class FileType extends BaseDataItem {

    private String description;
    private String Type;

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
