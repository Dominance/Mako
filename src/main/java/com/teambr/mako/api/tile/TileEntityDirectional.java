package com.teambr.mako.api.tile;

import net.minecraft.util.EnumFacing;

public class TileEntityDirectional extends TileEntityBase {

    private EnumFacing facing;

    public TileEntityDirectional(EnumFacing facing) {
        this.facing = facing;
    }
}
