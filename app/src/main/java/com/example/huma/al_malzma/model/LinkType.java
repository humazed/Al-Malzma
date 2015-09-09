package com.example.huma.al_malzma.model;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Patterns;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.parse.ParseClassName;

@ParseClassName("Link")
public class LinkType extends BaseDataItem {
    public static final String TAG = LinkType.class.getSimpleName();


    public LinkType() {
    }

    @Override
    public void saveToParse(Context context) {
        saveInBackgroundWithAlertDialog(context);
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
        return getString(ParseConstants.KEY_LINK_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(ParseConstants.KEY_LINK_DESCRIPTION, description);
    }

    public String getLink() {
        return getString(ParseConstants.KEY_LINK);
    }

    public void setLink(String link) {
        link = validateLink(link);
        put(ParseConstants.KEY_LINK, link);
    }
}
