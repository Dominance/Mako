package com.teambr.mako.api.tile;

import com.teambr.mako.api.multiblock.IMultiblock;
import net.minecraft.util.EnumFacing;

public class TileEntityMultiblock extends TileEntityDirectional {

    private IMultiblock multiblock;

    public TileEntityMultiblock(EnumFacing facing, IMultiblock multiblock) {
        super(facing);
        this.multiblock = multiblock;
    }
}
