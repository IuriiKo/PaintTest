package com.kushyk.paint.cash;

import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Stack;

/**
 * Created by Iurii Kushyk on 04.02.2017.
 */

public class PaintCash extends BaseCash{
    private Stack<Path> cash = new Stack<>();

    @Override
    public Path add(@NonNull Path path) {
        cash.add(path);
        return path;
    }

    @Override
    @Nullable
    public Path pop() {
        return pop().isEmpty() ? null : cash.pop();
    }

    @Override
    @Nullable
    public Path peek() {
        return pop().isEmpty() ? null : cash.peek();
    }
}
