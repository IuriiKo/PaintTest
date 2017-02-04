package com.kushyk.paint.util;

import android.content.Intent;

/**
 * Created by Iurii Kushyk on 04.02.2017.
 */

public class IntentUtil {

    public static Intent getImageIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        return intent;
    }
}
