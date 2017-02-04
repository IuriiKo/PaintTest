package com.kushyk.paint.ui.view;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kushyk.paint.cash.BaseCash;
import com.kushyk.paint.cash.PaintCash;
import com.kushyk.paint.manager.BaseManager;

/**
 * Created by Iurii Kushyk on 04.02.2017.
 */

public class PaintView extends View {
    public static final String LOG_TAG = PaintView.class.getSimpleName();
    private BaseManager manager;
    private BaseCash cash;

    public PaintView(Context context) {
        super(context);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (manager == null) {
            Log.e(LOG_TAG, "onTouchEvent() manager == null");
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                manager.startPaint(event);
                break;
            case MotionEvent.ACTION_UP:
                cash.add(manager.endPaint(event));
                break;
            case MotionEvent.ACTION_MOVE:
                manager.paint(event);
                break;
        }
        return true;
    }

    public void setManager(@NonNull BaseManager manager) {
        this.manager = manager;
    }

    public void setCash( @NonNull BaseCash cash) {
        this.cash = cash;
    }
}
