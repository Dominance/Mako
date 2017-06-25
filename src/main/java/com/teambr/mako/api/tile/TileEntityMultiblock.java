package com.teambr.mako.api.tile;

import com.teambr.mako.api.multiblock.IMultiblock;

public class TileEntityMultiblock {

    protected final IMultiblock multiblock;

    public TileEntityMultiblock(IMultiblock multiblock) {
        this.multiblock = multiblock;
    }
}
