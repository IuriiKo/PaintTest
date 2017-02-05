package com.kushyk.paint.manager.path;

import android.graphics.Path;
import android.view.MotionEvent;

/**
 * Created by Iurii Kushyk on 05.02.2017.
 */

public class StratchManager extends BasePathManager {
    @Override
    public Path startPaint(MotionEvent event) {
        path.moveTo(event.getX(), event.getY());
        return path;
    }

    @Override
    public Path paint(MotionEvent event) {
        path.lineTo(event.getX(), event.getY());
        return path;
    }

    @Override
    public Path endPaint(MotionEvent event) {
        path.lineTo(event.getX(), event.getY());
        return path;
    }
}
