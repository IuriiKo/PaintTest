package com.kushyk.paint.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kushyk.paint.R;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
