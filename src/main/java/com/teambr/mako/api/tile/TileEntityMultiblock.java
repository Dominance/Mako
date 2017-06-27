package com.teambr.mako.api.tile;


import com.teambr.mako.api.multiblock.IMultiblock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityMultiblock extends TileEntityDirectional implements IHasExternalCapability, IHasGui {

    public static String NBT_FORMED = "Formed";

    private IMultiblock multiblock;
    private boolean isFormed;

    public TileEntityMultiblock() {
        super(EnumFacing.NORTH);
        isFormed = false;
    }

    public IMultiblock getMultiblock() {
        return multiblock;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setBoolean(NBT_FORMED, isFormed);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        isFormed = false;
        if (compound.hasKey(NBT_FORMED)) isFormed = compound.getBoolean(NBT_FORMED);
        super.readFromNBT(compound);
    }

    public void setMultiblock(IMultiblock multiblock) {
        this.multiblock = multiblock;
    }

    public void destroyMultiblock() {
        if (multiblock != null) {
            System.out.println("Destroing mutli");
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
