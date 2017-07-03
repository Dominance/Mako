package com.teambr.mako.tile;

import com.teambr.mako.api.mako.stack.CapabilityMakoHandler;
import com.teambr.mako.api.mako.stack.IMakoHandler;
import com.teambr.mako.api.mako.stack.MakoStack;
import com.teambr.mako.api.mako.stack.MakoTank;
import com.teambr.mako.api.tile.TileEntityTick;
import com.teambr.mako.block.DirectionalBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityFaucet extends TileEntityTick{

    private boolean active;
    private String source;

    public TileEntityFaucet() {
        super("faucet");
        active = false;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setBoolean("Active", active);
        if (source != null) compound.setString("Source", source);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        active = false;
        if (compound.hasKey("Active")) active = compound.getBoolean("Active");
        source = null;
        if (compound.hasKey("Source")) source = compound.getString("Source");
        super.readFromNBT(compound);
    }

    @Override
    public void update() {
        if (world.isRemote) return;
        if (active){
            TileEntity down = world.getTileEntity(this.pos.add(0,-1,0));
            TileEntity back = world.getTileEntity(this.pos.offset(this.world.getBlockState(this.pos).getValue(DirectionalBlock.FACING)));
            if (down == null || back == null){
                active = false;
                source = null;
                sendUpdates();
                return;
            }
            if (down.hasCapability(CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY, EnumFacing.UP) && back.hasCapability(CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY, this.world.getBlockState(this.pos).getValue(DirectionalBlock.FACING).getOpposite())){
                IMakoHandler downCap = down.getCapability(CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY, EnumFacing.UP);
                IMakoHandler backCap = back.getCapability(CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY, this.world.getBlockState(this.pos).getValue(DirectionalBlock.FACING).getOpposite());
                if (downCap.getCurrent() == null || (backCap.getCurrent() != null && backCap.getCurrent().isMakoEqual(downCap.getCurrent()))){
                    source = backCap.getCurrent().getMako().getName();
                    int amount = downCap.fill(new MakoStack(backCap.getCurrent().getMako(), 10));
                    if (amount == 0){
                        active = false;
                        source = null;
                        sendUpdates();
                    }
                    backCap.drain(amount);
                }
            }
        }
    }

    public void toggle(){
        active = !active;
        TileEntity back = world.getTileEntity(this.pos.offset(this.world.getBlockState(this.pos).getValue(DirectionalBlock.FACING)));
        if (back != null && back.hasCapability(CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY, this.world.getBlockState(this.pos).getValue(DirectionalBlock.FACING).getOpposite())){
            IMakoHandler backCap = back.getCapability(CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY, this.world.getBlockState(this.pos).getValue(DirectionalBlock.FACING).getOpposite());
            if (backCap.getCurrent() != null) source = backCap.getCurrent().getMako().getName();
        }
        sendUpdates();
    }

    public boolean isActive() {
        return active;
    }

    public String getSource(){
        return source;
    }
}
