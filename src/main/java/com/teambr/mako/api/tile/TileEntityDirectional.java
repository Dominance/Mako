package com.teambr.mako.api.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityDirectional extends TileEntityBase {

    private EnumFacing facing;

    public TileEntityDirectional(EnumFacing facing) {
        this.facing = facing;
    }

    public EnumFacing getFacing() {
        return facing;
    }

    public void setFacing(EnumFacing facing) {
        this.facing = facing;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setString("facing", facing.getName());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        facing = EnumFacing.NORTH;
        if (compound.hasKey("facing")) facing = EnumFacing.byName(compound.getString("facing"));
        super.readFromNBT(compound);
    }
}
