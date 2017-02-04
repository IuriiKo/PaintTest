package com.kushyk.paint.manager;

import android.graphics.Path;
import android.view.MotionEvent;

import com.kushyk.paint.cash.BaseCash;

/**
 * Created by Iurii Kushyk on 04.02.2017.
 */

public abstract class BaseManager {
    protected BaseCash cash;

    public abstract Path startPaint(MotionEvent event);

    public abstract Path endPaint(MotionEvent event);

    public abstract Path paint(MotionEvent event);
}
