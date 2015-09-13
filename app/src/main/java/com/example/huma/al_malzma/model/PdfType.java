package com.example.huma.al_malzma.model;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.parse.ParseClassName;
import com.parse.ParseFile;

import java.io.File;


@ParseClassName("PDF")
public class PdfType extends BaseDataItem {

    public static final int REQUEST_CHOOSE_PDF = 3;

    private String description;

    ParseFile PDF;

    public void displayPdf(Context context) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS) + "/Mind Map.pdf");

        Toast.makeText(context, file.toString(), Toast.LENGTH_LONG).show();
        if (file.exists()) {
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(Uri.fromFile(file), "application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            try {
                context.startActivity(Intent.createChooser(target, "Open File"));
            } catch (ActivityNotFoundException e) {
                // Instruct the user to install a PDF reader here, or something
                Toast.makeText(context, "there is no program PDF", Toast.LENGTH_SHORT).show();
            }
        } else
            Toast.makeText(context, "File path is incorrect.", Toast.LENGTH_LONG).show();
    }


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
