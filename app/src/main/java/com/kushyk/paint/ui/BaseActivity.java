package com.kushyk.paint.ui;

import android.support.v7.app.AppCompatActivity;

import butterknife.Unbinder;

/**
 * Created by Iurii Kushyk on 04.02.2017.
 */

public class BaseActivity extends AppCompatActivity {
    private Unbinder unbinder;

    protected void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
