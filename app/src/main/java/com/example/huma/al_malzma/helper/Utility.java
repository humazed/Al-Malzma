package com.example.huma.al_malzma.helper;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

}
