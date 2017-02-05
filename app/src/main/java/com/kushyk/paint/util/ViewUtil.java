package com.kushyk.paint.util;

import android.support.annotation.ColorInt;
import android.widget.ImageView;

/**
 * Created by Iurii Kushyk on 05.02.2017.
 */

public class ViewUtil {
    public static void changeColor(ImageView view, @ColorInt int color) {
        view.setColorFilter(color);
    }
}
