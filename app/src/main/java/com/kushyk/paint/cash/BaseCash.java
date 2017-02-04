package com.kushyk.paint.cash;

import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Iurii Kushyk on 04.02.2017.
 */

public abstract class BaseCash {
    public abstract void add(@NonNull Path path);

    @Nullable
    public abstract Path pop();

    @Nullable
    public abstract Path peek();
}
