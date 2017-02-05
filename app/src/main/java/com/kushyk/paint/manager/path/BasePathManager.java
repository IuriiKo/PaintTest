package com.kushyk.paint.manager.path;

import android.graphics.Path;
import android.view.MotionEvent;


/**
 * Created by Iurii Kushyk on 04.02.2017.
 */

public abstract class BasePathManager {
    protected Path path = new Path();

    public abstract Path startPaint(MotionEvent event);

    public abstract Path paint(MotionEvent event);

    public abstract Path endPaint(MotionEvent event);

    public void resetPath() {
        path = new Path();
    }
}
