package com.example.huma.al_malzma.model;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Patterns;

import com.example.huma.al_malzma.R;

public class LinkType extends BaseDataItem {

    private String description;
    private String link;

    @Override
    public void create() {

    }

    //make sure the user has enter wright Uri >> and add http:// to it if it don't.
    public static String validateLinkWithAlderDialog(String link, Context context) {
        if (Patterns.WEB_URL.matcher(link).matches()) {
            if (!link.startsWith("http://") && !link.startsWith("https://"))
                link = "http://" + link;
            return link;

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getString(R.string.wrong_uri_error_title))
                    .setMessage(R.string.wrong_uri_error_message)
                    .setPositiveButton(android.R.string.ok, null)
                    .create().show();
        }
        return null;
    }

    public static String validateLink(String link) {
        if (Patterns.WEB_URL.matcher(link).matches()) {
            if (!link.startsWith("http://") && !link.startsWith("https://"))
                link = "http://" + link;
            return link;

        }
        return null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
