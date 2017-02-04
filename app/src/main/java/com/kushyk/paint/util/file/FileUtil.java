package com.kushyk.paint.util.file;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;

import com.kushyk.paint.App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Observable;

/**
 * Created by Iurii Kushyk on 04.02.2017.
 */

public class FileUtil {

    public static final int QUALITY = 80;

    public static Observable<Boolean> saveImage(Bitmap bitmap) {
        return Observable.fromCallable(()->{
            FileOutputStream outputStream = null;
            try {
                File file = initPath();
                outputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, outputStream);
                addImageToGallery(file.getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                if (outputStream != null) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        });

    }

    public static void addImageToGallery(final String filePath) {
        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        App.getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public static File initPath() {
       return new File(
                App.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
               formatTime() + ".jpg");
    }

    private static String formatTime() {
        return new SimpleDateFormat("HH:mm_ddMMyy", Locale.getDefault()).format(new Date());
    }
}
