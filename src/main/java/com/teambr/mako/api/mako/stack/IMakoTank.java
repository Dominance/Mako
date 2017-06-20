package com.teambr.mako.api.mako.stack;


import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public interface IMakoTank {

    MakoStack getMakoStack();

    int getMakoAmount();

    int getMaxMakoAmount();

    MakoTankInfo getTankInfo();

    int fill(MakoStack stack, boolean doFill);

    @Nullable
    FluidStack drain(int max, boolean doDrain);
}
