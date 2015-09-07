package com.example.huma.al_malzma.model;


public class TextType extends BaseDataItem {

    private String title;
    private String data;


    @Override
    public void create() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
