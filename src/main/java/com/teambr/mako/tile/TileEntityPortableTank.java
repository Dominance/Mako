package com.teambr.mako.tile;

import com.teambr.mako.api.mako.stack.CapabilityMakoHandler;
import com.teambr.mako.api.mako.stack.MakoStack;
import com.teambr.mako.api.mako.stack.MakoTank;
import com.teambr.mako.api.tile.TileEntityTick;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;


public class TileEntityPortableTank extends TileEntityTick {

    private MakoTank tank;
    private int tick;

    public TileEntityPortableTank() { //Todo implement push from bottom
        super("single_tank");
        tank = new MakoTank(8000){
            @Override
            public void onContentsChanged() {
                TileEntityPortableTank.this.sendUpdates();
            }
        };
        tick = 0;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound tagCompound = super.writeToNBT(compound);
        tagCompound.setTag("Tank", tank.writeToNBT(new NBTTagCompound()));
        return tagCompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("Tank")) tank = tank.readFromNBT(compound.getCompoundTag("Tank"));
        super.readFromNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY && (facing == EnumFacing.UP || facing == EnumFacing.DOWN))
            return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY && (facing == EnumFacing.UP || facing == EnumFacing.DOWN))
            return (T) tank;
        return super.getCapability(capability, facing);
    }

    public MakoTank getTank() {
        return tank;
    }

    public void setTank(MakoTank tank) {
        this.tank = tank;
    }

    @Override
    public void update() {
        if (world.isRemote || tank.getMakoStack() == null) return;
        ++tick;
        if (tick >= 2) {
            TileEntity entity = world.getTileEntity(this.pos.add(0, -1, 0));
            if (entity != null && entity.hasCapability(CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY, EnumFacing.UP)) {
                tank.drain(entity.getCapability(CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY, EnumFacing.UP).fill(new MakoStack(tank.getMakoStack().getMako(), 10)));
            }
            tick = 0;
        }
    }
}
