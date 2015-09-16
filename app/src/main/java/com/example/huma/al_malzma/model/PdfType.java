package com.example.huma.al_malzma.model;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.huma.al_malzma.helper.FileHelper;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.parse.ParseClassName;
import com.parse.ParseFile;

import java.io.File;


@ParseClassName("PDF")
public class PdfType extends BaseDataItem {

    public static final int REQUEST_CHOOSE_PDF = 3;

    private Context mContext;

    private String description;

    ParseFile PDF;

    public PdfType() {/*Default constructor required by parse */}

    public PdfType(Context context) {
        mContext = context;
    }

    public Intent getPicPdfIntent() {
        Intent choosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
        choosePhotoIntent.setType("pdf/*");
        return choosePhotoIntent;
    }

    public static void displayPdf(Context context, Uri pdfDir) {
        File file = new File(pdfDir.getPath());

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
        saveInBackgroundWithAlertDialog(context);
    }


    public ParseFile getPDF() {
        return PDF;
    }

    public void setPDF(Uri pdfUri) {
        byte[] fileBytes = FileHelper.getByteArrayFromFile(mContext, pdfUri);
        //replace spaces with "_" because parse don't   accept file name with spaces.
        String fileName = pdfUri.getLastPathSegment().replaceAll("\\s+", "_");

        Log.d(TAG, "pdf uri: " + pdfUri);

        Log.d(TAG, "pdf fileName: " + fileName);

        ParseFile parseFile = new ParseFile(fileName, fileBytes, "pdf");
        saveInBackgroundWithAlertDialogAndProgressDialog(mContext, parseFile);
        put(ParseConstants.KEY_PDF, parseFile);
    }

    public String getDescription() {
        return getString(ParseConstants.KEY_PDF_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(ParseConstants.KEY_PDF_DESCRIPTION, description);
    }
}
