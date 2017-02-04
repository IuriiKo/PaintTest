package com.kushyk.paint.options;

import android.graphics.Paint;
import android.support.annotation.ColorInt;

/**
 * Created by Iurii Kushyk on 04.02.2017.
 */

public class PaintUtil {
    public static Paint initPaintOptions(@ColorInt int color, float width) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(width);
        return  paint;
    }
}
