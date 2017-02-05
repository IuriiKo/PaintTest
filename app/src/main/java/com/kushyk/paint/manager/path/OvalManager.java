package com.kushyk.paint.manager.path;

import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;

/**
 * Created by Iurii Kushyk on 05.02.2017.
 */

public class OvalManager extends BasePathManager {
    private float startX;
    private float startY;
    private RectF rect = new RectF();

    @Override
    public Path startPaint(MotionEvent event) {
        startX = event.getX();
        startY = event.getY();
        rect.left =startX;
        rect.top = startY;
        path.moveTo(startX, startY);
        return path;
    }

    @Override
    public Path paint(MotionEvent event) {
        path.reset();
        path.moveTo(startX, startY);
        rect.right = event.getX();
        rect.bottom = event.getY();

        path.addOval(rect, Path.Direction.CW);
        return path;
    }

    @Override
    public Path endPaint(MotionEvent event) {
        path.reset();
        path.moveTo(startX, startY);
        rect.right = event.getX();
        rect.bottom = event.getY();

        path.addOval(rect, Path.Direction.CW);
        return path;
    }
}
