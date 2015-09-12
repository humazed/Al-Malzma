package com.example.huma.al_malzma.model;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.ui.SubjectActivity;
import com.parse.ParseClassName;
import com.parse.ParseFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@ParseClassName("Image")
public class ImageType extends BaseDataItem {

    public static final int REQUEST_CAPTURE_PHOTO = 1;
    public static final int REQUEST_CHOOSE_PHOTO = 2;
    private String description;

    private static Uri imageUri;

    ParseFile PDF;

    public static Intent getCapturePhotoIntent(Context context) {
        Intent capturePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageUri = getImageUri(context);
        capturePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        return capturePhotoIntent;
    }

    public static Intent getChoosePhotoIntent() {
        Intent choosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
        choosePhotoIntent.setType("image/*");
        return choosePhotoIntent;
    }

    /**
     * that method will make folder with subjectName into Al-Malzma folder
     * and inside that file will CREATE the Image file and return Uri of that file.
     * the Image file name will consist of "Week + timeStamp + + .jpg"
     */
    public static Uri getImageUri(Context context) {
        if (isExternalStorageAvailable()) {
            String appName = context.getString(R.string.app_name);
            String subjectName = SubjectActivity.subjectName;
            //must remove spaces as it used for Image file name.
            String week = SubjectActivity.week.replaceAll("\\s+", "");

            //directory where images will be saved.
            File imagesDir = new File(
                    Environment.getExternalStoragePublicDirectory(appName),
                    subjectName);

            //Create our subdirectory.
            if (!imagesDir.exists()) if (!imagesDir.mkdirs()) {
                Log.e(TAG, "Failed to create directory.");
                return null;
            }

            Date now = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);

            String path = imagesDir.getPath() + File.separator;

            File imageFile = new File(path + week + "_" + timestamp + ".jpg");

            return Uri.fromFile(imageFile);
        }
        return null;
    }

    private static boolean isExternalStorageAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /* refresh the gallery with the taken Image  */
    public static void refreshGallery(Context context) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        context.sendBroadcast(mediaScanIntent);
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
