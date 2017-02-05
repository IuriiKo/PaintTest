package com.kushyk.paint.manager.path;

import android.graphics.Path;
import android.view.MotionEvent;

/**
 * Created by Iurii Kushyk on 05.02.2017.
 */

public class LineManager extends BasePathManager {
    private float startX;
    private float startY;

    @Override
    public Path startPaint(MotionEvent event) {
        startX = event.getX();
        startY = event.getY();
        path.moveTo(startX, startY);
        return path;
    }

    @Override
    public Path paint(MotionEvent event) {
        path.reset();
        path.moveTo(startX, startY);
        path.lineTo(event.getX(), event.getY());
        return path;
    }

    @Override
    public Path endPaint(MotionEvent event) {
        path.reset();
        path.moveTo(startX, startY);
        path.lineTo(event.getX(), event.getY());
        return path;
    }
}
