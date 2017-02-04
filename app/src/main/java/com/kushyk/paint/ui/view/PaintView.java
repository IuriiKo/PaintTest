package com.kushyk.paint.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kushyk.paint.manager.paint.PaintManager;


/**
 * Created by Iurii Kushyk on 04.02.2017.
 */

public class PaintView extends View {
    public static final String LOG_TAG = PaintView.class.getSimpleName();
    private static final int BACKGROUND_COLOR = Color.WHITE;
    private PaintManager manager;
    private boolean clearCanvas;
    private int positionBefore;
    private Bitmap bitmap;
    private boolean revert;
    private Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PaintView(Context context) {
        super(context);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setManager(PaintManager manager) {
        this.manager = manager;
    }

    public PaintManager getManager() {
        return manager;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (manager == null) {
            Log.e(LOG_TAG, "onSuccessTouchEvent() manager == null");
            return true;
        }

        if (manager.onSuccessTouchEvent(event)) {
            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (clearCanvas) {
            clearCanvas = false;
            if (canvas.getSaveCount() <= 0) {
                canvas.restore();
            }
            manager.clearCash();
            bitmap = null;
            return;
        }

        if (revert) {
            revert = false;
            if (!manager.cashIsEmpty()) {
                manager.revert();
            } else if (bitmap != null){
                bitmap = null;
            }
            invalidate();
            return;
        }

        canvas.drawColor(BACKGROUND_COLOR);

        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        }

       manager.onDraw(canvas);
    }


    public void clearCanvas() {
        clearCanvas = true;
        invalidate();
    }

    public void backToPreviousView() {
        clearCanvas = true;
        invalidate();
    }

    public void restoreTo() {
        this.revert = true;
        invalidate();
    }

    public void drawBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        manager.clearCash();
        invalidate();
    }
}
