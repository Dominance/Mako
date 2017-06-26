package com.teambr.mako.api.tile;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public interface IHasExternalCapability {

    public boolean hasExternalCapability(BlockPos pos, Capability<?> capability, @Nullable EnumFacing facing);

    public <T> T getExternalCapability(BlockPos pos, Capability<T> capability, @Nullable EnumFacing facing);
}
