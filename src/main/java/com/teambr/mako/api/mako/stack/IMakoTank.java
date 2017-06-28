package com.teambr.mako.api.mako.stack;


import javax.annotation.Nullable;

public interface IMakoTank {

    MakoStack getMakoStack();

    int getMakoAmount();

    int getMaxMakoAmount();

    MakoTankInfo getTankInfo();

    void fill(MakoStack stack);

    @Nullable
    void drain(int drainAmount);
}
