package com.example.huma.al_malzma.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.util.Log;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.ui.SubjectActivity;
import com.parse.ParseClassName;
import com.parse.ParseFile;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@ParseClassName("Image")
public class ImageType extends BaseDataItem {

    @IntDef({REQUEST_CAPTURE_PHOTO, REQUEST_CHOOSE_PHOTO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public static final int REQUEST_CAPTURE_PHOTO = 1;
    public static final int REQUEST_CHOOSE_PHOTO = 2;

    private Context mContext;
    private int mType;

    private ParseFile image;
    private String description;

    private static Uri imageUri;

    public ImageType() {/*Default constructor required by parse */}

    public ImageType(Context context, @Type int type) {
        mContext = context;
        mType = type;
    }

    public Intent getActionIntent() {
        if (mType == REQUEST_CAPTURE_PHOTO) {
            return getCapturePhotoIntent();
        } else {
            return getChoosePhotoIntent();
        }
    }

    private Intent getCapturePhotoIntent() {
        Intent capturePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageUri = getImageUri(mContext);
        capturePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        return capturePhotoIntent;
    }

    private Intent getChoosePhotoIntent() {
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

    // FIXME: 9/13/2015 did't works on my sony phone
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
