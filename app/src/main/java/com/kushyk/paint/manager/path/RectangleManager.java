package com.kushyk.paint.manager.path;

import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;

/**
 * Created by Iurii Kushyk on 05.02.2017.
 */
public class RectangleManager extends BasePathManager {
    private float startX;
    private float startY;
    private RectF rect = new RectF();
    @Override
    public Path startPaint(MotionEvent event) {
        startX = event.getX();
        startY = event.getY();
        rect.left = startX;
        rect.top = startY;
        path.moveTo(startX, startY);
        return path;
    }

    @Override
    public Path paint(MotionEvent event) {
        path.reset();
        rect.left = startX > event.getX() ? event.getX() : startX;
        rect.top = startY > event.getY() ? event.getY() : startY;
        rect.right = startX > event.getX() ? startX : event.getX();
        rect.bottom = startY > event.getY() ? startY : event.getY();
        path.moveTo(rect.left, rect.top);
        path.addRect(rect, Path.Direction.CW);
        return path;
    }

    @Override
    public Path endPaint(MotionEvent event) {
        path.reset();
        rect.left = startX > event.getX() ? event.getX() : startX;
        rect.top = startY > event.getY() ? event.getY() : startY;
        rect.right = startX > event.getX() ? startX : event.getX();
        rect.bottom = startY > event.getY() ? startY : event.getY();
        path.moveTo(rect.left, rect.top);
        path.addRect(rect, Path.Direction.CW);
        return path;
    }
}
