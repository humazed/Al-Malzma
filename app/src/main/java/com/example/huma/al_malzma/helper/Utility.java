package com.example.huma.al_malzma.helper;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Patterns;
import android.widget.Toast;

import com.example.huma.al_malzma.R;

public class Utility {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static boolean isNetworkAvailableWithToast(Context context) {
        if (isNetworkAvailable(context)) {
            return true;
        } else {
            Toast.makeText(context, context.getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
            return false;
        }
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
}
