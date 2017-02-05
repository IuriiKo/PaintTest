package com.kushyk.paint.cash;

import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import java.util.Stack;

/**
 * Created by Iurii Kushyk on 04.02.2017.
 */

public class PaintCash extends BaseCash{
    private Stack<Pair<Path, Paint>> cash = new Stack<>();

    @Override
    public void add(@NonNull Path path, Paint paint) {
        cash.add(new Pair<>(path, paint));
    }

    @Override
    @Nullable
    public Pair<Path, Paint> pop() {
        return cash.isEmpty() ? null : cash.pop();
    }

    @Override
    @Nullable
    public Pair<Path, Paint> peek() {
        return cash.isEmpty() ? null : cash.peek();
    }

    @Override
    public Stack<Pair<Path, Paint>> getCash() {
        return cash;
    }
}
