package com.teambr.mako.api.mako.stack;

import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public interface IMakoHandler {

    int fill(MakoStack stack, boolean doFill);

    @Nullable
    FluidStack drain(int max, boolean doDrain);

    @Nullable
    FluidStack drain(MakoStack res, boolean doDrain);
}
