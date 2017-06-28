package com.teambr.mako.api.tile;


import com.teambr.mako.api.multiblock.IMultiblock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityMultiblock extends TileEntityDirectional implements IHasExternalCapability, IHasGui {

    private IMultiblock multiblock;

    public TileEntityMultiblock() {
        super(EnumFacing.NORTH);
    }

    public IMultiblock getMultiblock() {
        return multiblock;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    public void setMultiblock(IMultiblock multiblock) {
        this.multiblock = multiblock;
    }

    public void destroyMultiblock() {
        if (multiblock != null) {
            getMultiblock().destroyMultiblock(world, this.pos, this.world.getBlockState(pos), getFacing());
        }
    }
}
