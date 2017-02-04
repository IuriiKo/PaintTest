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

public abstract class BaseCash {
    public abstract void add(@NonNull Path path, @NonNull Paint paint);

    @Nullable
    public abstract Pair<Path, Paint> pop();

    @Nullable
    public abstract Pair<Path, Paint> peek();

    public abstract Stack<Pair<Path, Paint>> getCash();
}
