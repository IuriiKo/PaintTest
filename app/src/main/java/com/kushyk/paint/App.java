package com.kushyk.paint;

import android.app.Application;
import android.content.Context;

import com.kushyk.paint.manager.paint.PaintManager;

/**
 * Created by Iurii Kushyk on 04.02.2017.
 */

public class App extends Application {
    private static Context context;
    private static PaintManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        manager = new PaintManager();
    }

    public static Context getContext() {
        return context;
    }

    public static PaintManager getManager() {
        return manager;
    }
}
