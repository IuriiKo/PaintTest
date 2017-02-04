package com.kushyk.paint.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.kushyk.paint.R;

/**
 * Created by Iurii Kushyk on 05.02.2017.
 */

public class PermissionUtil {
    public static boolean checkWritePermission(Activity activity, int requestCode) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(activity)
                        .setTitle(R.string.need_permission)
                        .setMessage(R.string.permission_explane)
                        .setPositiveButton(
                                R.string.ok,
                                (dialog, p) -> requestWriteExternalPermission(
                                        activity,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        requestCode))
                        .setNegativeButton(R.string.cancel, null)
                        .show();

            } else {
                requestWriteExternalPermission(
                        activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        requestCode);
            }
            return false;
        }
        return true;
    }

    private static void requestWriteExternalPermission(Activity activity, String permission, int requestCode) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{permission},
                requestCode);
    }
}
