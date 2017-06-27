package com.teambr.mako.api.tile;


import com.teambr.mako.api.multiblock.IMultiblock;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityMultiblock extends TileEntityDirectional implements IHasExternalCapability {

    private IMultiblock multiblock;

    public TileEntityMultiblock() {
        super(EnumFacing.NORTH);
    }

    public IMultiblock getMultiblock() {
        return multiblock;
    }

    public void setMultiblock(IMultiblock multiblock) {
        this.multiblock = multiblock;
    }

    public void destroyMultiblock() {
        getMultiblock().destroyMultiblock(world, this.pos, this.world.getBlockState(pos), getFacing());
    }
}
