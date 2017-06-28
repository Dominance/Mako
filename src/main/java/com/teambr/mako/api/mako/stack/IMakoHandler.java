package com.teambr.mako.api.mako.stack;

import javax.annotation.Nullable;

public interface IMakoHandler {

    void fill(MakoStack stack);

    @Nullable
    void drain(int max);

    @Nullable
    void drain(MakoStack res);
}
