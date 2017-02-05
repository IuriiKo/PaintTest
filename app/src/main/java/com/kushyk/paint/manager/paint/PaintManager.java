package com.kushyk.paint.manager.paint;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;

import com.kushyk.paint.App;
import com.kushyk.paint.R;
import com.kushyk.paint.cash.BaseCash;
import com.kushyk.paint.cash.PaintCash;
import com.kushyk.paint.manager.path.BasePathManager;
import com.kushyk.paint.manager.path.PencilManager;
import com.kushyk.paint.options.PaintUtil;

/**
 * Created by Iurii Kushyk on 04.02.2017.
 */

public class PaintManager {
    public static final String LOG_TAG = PaintManager.class.getSimpleName();

    private BaseCash cash;
    private Paint paint;
    private BasePathManager manager;
    private Path tempPath;
    private boolean saveCanvas;
    private int currentColor = Color.CYAN;

    public PaintManager() {
        this(
                PaintUtil.initPaintOptions(
                        Color.CYAN,
                        App.getContext().getResources().getDimension(R.dimen.size_4)),
                new PaintCash(),
                new PencilManager());
    }

    public PaintManager(Paint paint, BaseCash cash, BasePathManager manager) {
        currentColor = paint.getColor();
        this.paint = paint;
        this.cash = cash;
        this.manager = manager;
    }

    public boolean onSuccessTouchEvent(MotionEvent event) {
        if (manager == null) {
            Log.e(LOG_TAG, "onSuccessTouchEvent() manager == null");
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tempPath = manager.startPaint(event);
                return true;
            case MotionEvent.ACTION_UP:
                tempPath = manager.endPaint(event);
                manager.resetPath();
                cash.add(tempPath, paint);
                saveCanvas = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                tempPath = manager.paint(event);
                return true;
        }
        return false;
    }

    public void onDraw(Canvas canvas) {
        for (Pair<Path, Paint> pathPaintPair : cash.getCash()) {
            canvas.drawPath(pathPaintPair.first, pathPaintPair.second);
        }

        if (tempPath != null) {
            canvas.drawPath(tempPath, paint);
        }

        if (saveCanvas) {
            saveCanvas = false;
            canvas.save();
        }
    }

    public void setCash(BaseCash cash) {
        this.cash = cash;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setManager(BasePathManager manager) {
        this.manager = manager;
    }

    public void clearCash() {
        cash.getCash().clear();
    }


    public void revert() {
        if (cash.getCash().size() > 0) {
            tempPath = null;
            cash.getCash().pop();
        }
    }

    public void setSize(@DimenRes int dimenRes) {
        setSize(App.getContext().getResources().getDimension(dimenRes));
    }

    public void setSize(float width) {
        paint = PaintUtil.initPaintOptions(paint.getColor(),width);
    }

    public void setColorRes(@ColorRes int color) {
        setColor(ContextCompat.getColor(App.getContext(), color));
    }

    public void setColor(int color) {
        this.currentColor = color;
        paint = PaintUtil.initPaintOptions(color,paint.getStrokeWidth());
    }

    public void setStretchColor(int color) {
        paint = PaintUtil.initPaintOptions(color,paint.getStrokeWidth());
    }

    public boolean cashIsEmpty() {
        return cash.getCash().isEmpty();
    }

    public int getCurrentColor() {
        return currentColor;
    }
}
