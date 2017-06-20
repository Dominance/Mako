package com.teambr.mako.api.mako.stack;

import jline.internal.Nullable;

public class MakoTankInfo {

    @Nullable
    public final MakoStack mako;
    public final int capacity;

    public MakoTankInfo(@Nullable MakoStack mako, int capacity) {
        this.mako = mako;
        this.capacity = capacity;
    }

    public MakoTankInfo(IMakoTank tank) {
        this(tank.getMakoStack(), tank.getMaxMakoAmount());
    }
}
