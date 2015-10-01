package com.example.huma.al_malzma.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.text.InputType;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.huma.al_malzma.helper.FileHelper;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.example.huma.al_malzma.ui.SubjectActivity;
import com.parse.ParseClassName;
import com.parse.ParseFile;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@ParseClassName(ParseConstants.CLASS_IMAGE)
public class ImageType extends BaseDataItem {
    private static final String TAG = ImageType.class.getSimpleName();


    @IntDef({REQUEST_CAPTURE_PHOTO, REQUEST_CHOOSE_PHOTO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestType {
    }

    public static final int REQUEST_CAPTURE_PHOTO = 1;
    public static final int REQUEST_CHOOSE_PHOTO = 2;

    private Context mContext;
    private int mType;

    private static Uri imageUri; //must be deleted.


    public ImageType() {/*Default constructor required by parse */}

    public ImageType(Context context, @RequestType int requestType,
                     @ParseConstants.FragmentSource String fragmentSource) {
        super(fragmentSource, ParseConstants.KEY_TYPE_IMAGE);
        mContext = context;
        mType = requestType;
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
     * that method will make folder with subjectName inside Al-Malzma folder
     * and inside that file will CREATE the Image file and return Uri of that file.
     * the Image file name will consist of "Week + timeStamp + + .jpg"
     */
    public static Uri getImageUri(Context context) {
        if (FileHelper.isExternalStorageAvailable()) {
            //must remove spaces as it used for Image file name.
            File subjectDir = FileHelper.getSubjectFile(context);
            if (subjectDir != null) {
                Date now = new Date();
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);
                String week = SubjectActivity.week.replaceAll("\\s+", "");

                String path = subjectDir.getPath() + File.separator;

                File imageFile = new File(path + week + "_" + timestamp + ".jpg");

                return Uri.fromFile(imageFile);
            }
        }
        return null;
    }


    // FIXME: 9/13/2015 didn't works on my sony phone
    /* refresh the gallery with the taken Image  */
    public static void refreshGallery(Context context) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        context.sendBroadcast(mediaScanIntent);
    }


    public static void showImageDescriptionDialog(final Context context, final ImageType image) {
        new MaterialDialog.Builder(context)
                .title("Description")
                .content("Enter some description!")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .positiveText(android.R.string.ok)
                .input("the Description", "", false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        image.setDescription(input.toString());

                        image.saveToParse(context);
                    }
                }).show();
    }

    @Override
    public void saveToParse(Context context) {
        saveInBackgroundWithAlertDialog(context);
    }


    public ParseFile getImage() {
        return getParseFile(ParseConstants.KEY_IMAGE);
    }

    public void setImage(Uri imageUri) {
        byte[] fileBytes = FileHelper.getByteArrayFromFile(mContext, imageUri);
        String fileName = imageUri.getLastPathSegment()
                .replaceAll("\\s+", "_").replaceAll(":", "_") + ".jpg";

        Log.d(TAG, "setImage uri: " + imageUri);
        Log.d(TAG, "setImage fileName: " + fileName);

        ParseFile parseFile = new ParseFile(fileName, fileBytes, "image");

        saveFileInBackgroundWithProgressDialog(mContext, parseFile);

        put(ParseConstants.KEY_IMAGE, parseFile);
    }


    public String getDescription() {
        return getString(ParseConstants.KEY_IMAGE_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(ParseConstants.KEY_IMAGE_DESCRIPTION, description);
    }
}
