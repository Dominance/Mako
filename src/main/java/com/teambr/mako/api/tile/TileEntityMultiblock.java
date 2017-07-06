package com.teambr.mako.api.tile;


import com.teambr.mako.api.multiblock.IMultiblock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityMultiblock extends TileEntityDirectional implements IHasExternalCapability {

    private IMultiblock multiblock;
    private boolean isFormed;

    public TileEntityMultiblock(String name, EnumFacing facing) {
        super(name, facing);
        isFormed = false;
    }

    public IMultiblock getMultiblock() {
        return multiblock;
    }

    public void setMultiblock(IMultiblock multiblock) {
        this.multiblock = multiblock;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setBoolean("Formed", isFormed);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("Formed")) isFormed = compound.getBoolean("Formed");
        super.readFromNBT(compound);
    }

    public void destroyMultiblock() {
        if (multiblock != null) {
            getMultiblock().destroyMultiblock(world, this.pos, this.world.getBlockState(pos), getFacing());
        }
    }

    public boolean isFormed() {
        return isFormed;
    }

    public void setFormed(boolean formed) {
        isFormed = formed;
    }
}
