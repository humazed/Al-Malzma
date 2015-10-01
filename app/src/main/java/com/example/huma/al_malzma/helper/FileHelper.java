package com.example.huma.al_malzma.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.example.huma.al_malzma.ui.SubjectActivity;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileHelper {

    public static final String TAG = FileHelper.class.getSimpleName();

    public static final int SHORT_SIDE_TARGET = 1280;

    public static byte[] getByteArrayFromFile(Context context, Uri uri) {
        byte[] fileBytes = null;
        InputStream inStream = null;
        ByteArrayOutputStream outStream = null;

        if (uri.getScheme().equals("content")) {
            try {
                inStream = context.getContentResolver().openInputStream(uri);
                outStream = new ByteArrayOutputStream();

                byte[] bytesFromFile = new byte[1024 * 1024 * 10]; // buffer size (10 MB)
                int bytesRead = inStream != null ? inStream.read(bytesFromFile) : 0;
                while (bytesRead != -1) {
                    outStream.write(bytesFromFile, 0, bytesRead);
                    bytesRead = inStream != null ? inStream.read(bytesFromFile) : 0;
                }

                fileBytes = outStream.toByteArray();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } finally {
                try {
                    if (inStream != null) inStream.close();
                    if (outStream != null) outStream.close();
                } catch (IOException e) { /*( Intentionally blank */ }
            }
        } else {
            try {
                File file = new File(uri.getPath());
                FileInputStream fileInput = new FileInputStream(file);
                fileBytes = IOUtils.toByteArray(fileInput);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return fileBytes;
    }

    public static byte[] reduceImageForUpload(byte[] imageData) {
        Bitmap bitmap = ImageResizer.resizeImageMaintainAspectRatio(imageData, SHORT_SIDE_TARGET);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] reducedData = outputStream.toByteArray();
        try {
            outputStream.close();
        } catch (IOException e) {
            // Intentionally blank
        }

        return reducedData;
    }

    public static String getFileName(Context context, Uri uri, @ParseConstants.FileType String fileType) {
        String fileName = "uploaded_file.";

        if (fileType.equals(ParseConstants.TYPE_IMAGE)) {
            fileName += "png";
        } else {
            // For video, we want to get the actual file extension
            if (uri.getScheme().equals("content")) {
                // do it using the mime type
                String mimeType = context.getContentResolver().getType(uri);
                int slashIndex = mimeType != null ? mimeType.indexOf("/") : 0;
                String fileExtension = mimeType != null ? mimeType.substring(slashIndex + 1) : null;
                fileName += fileExtension;
            } else {
                fileName = uri.getLastPathSegment();
            }
        }

        return fileName;
    }

    public static boolean isExternalStorageAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * this method will make folder with subjectName inside Al-Malzma folder
     * and return the Uri
     */
    public static File getSubjectFile(Context context) {
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
        return imagesDir;
    }
}