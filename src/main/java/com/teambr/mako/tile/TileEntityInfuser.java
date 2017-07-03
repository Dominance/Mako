package com.teambr.mako.tile;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.teambr.mako.api.mako.CombinedMako;
import com.teambr.mako.api.mako.MakoRegistry;
import com.teambr.mako.api.mako.stack.CapabilityMakoHandler;
import com.teambr.mako.api.mako.stack.MakoStack;
import com.teambr.mako.api.mako.stack.MakoTank;
import com.teambr.mako.api.tile.TileEntityMultiblock;
import com.teambr.mako.multiblock.InfuserMultiblock;
import com.teambr.mako.utils.BlockPosRelative;
import net.minecraft.client.Minecraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nullable;
import javax.vecmath.AxisAngle4d;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3d;

public class TileEntityInfuser extends TileEntityMultiblock implements ITickable {

    private MakoTank primary;
    private MakoTank secondary;
    private MakoTank output;

    public TileEntityInfuser() {
        super("infuser", EnumFacing.NORTH);
        this.setMultiblock(InfuserMultiblock.INFUSER_MULTIBLOCK);
        primary = new MakoTank(1000);
        secondary = new MakoTank(1000);
        output = new MakoTank(1000);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound tag = super.writeToNBT(compound);
        tag.setTag("Primary", primary.writeToNBT(new NBTTagCompound()));
        tag.setTag("Secondary", secondary.writeToNBT(new NBTTagCompound()));
        tag.setTag("Output", output.writeToNBT(new NBTTagCompound()));
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("Primary")) primary = primary.readFromNBT(compound.getCompoundTag("Primary"));
        if (compound.hasKey("Secondary")) secondary = secondary.readFromNBT(compound.getCompoundTag("Secondary"));
        if (compound.hasKey("Output")) output = output.readFromNBT(compound.getCompoundTag("Output"));
        super.readFromNBT(compound);
    }

    @Override
    public boolean hasExternalCapability(BlockPos pos, Capability<?> capability, @Nullable EnumFacing facing) {
        if (pos.equals(BlockPosRelative.getRealBlockPosFromRelative(this.getFacing(), this.getPos(), new BlockPos(0,0,1))) && capability == CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY && facing == EnumFacing.UP) return true;
        if (pos.equals(BlockPosRelative.getRealBlockPosFromRelative(this.getFacing(), this.getPos(), new BlockPos(0,0,1))) && capability == CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY && facing == this.getFacing().rotateY()) return true;
        return false;
    }

    @Override
    public <T> T getExternalCapability(BlockPos pos, Capability<T> capability, @Nullable EnumFacing facing) {
        if (pos.equals(BlockPosRelative.getRealBlockPosFromRelative(this.getFacing(), this.getPos(), new BlockPos(0,0,1))) && capability == CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY && facing == EnumFacing.UP) return (T) secondary;
        if (pos.equals(BlockPosRelative.getRealBlockPosFromRelative(this.getFacing(), this.getPos(), new BlockPos(0,0,1))) && capability == CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY && facing == this.getFacing().rotateY()) return (T) output;
        return null;
    }

    private static int WORK_AMOUNT = 20;

    private void work() {
        if (primary.getMakoStack() == null) return;
        if (secondary.getMakoStack() == null) return;
        if (primary.getMakoAmount() < WORK_AMOUNT && secondary.getMakoAmount() < WORK_AMOUNT) return;
        CombinedMako mako = MakoRegistry.getInstance().getCombinedMakoFromParents(primary.getMakoStack().getMako(), secondary.getMakoStack().getMako());
        if (mako == null) return;
        if (output.getMakoStack() == null || (output.getMakoStack().getMako().equals(mako)) && output.getMakoAmount() + 2 * WORK_AMOUNT <= output.getMaxMakoAmount()) {
            primary.drain(WORK_AMOUNT);
            secondary.drain(WORK_AMOUNT);
            output.fill(new MakoStack(mako, WORK_AMOUNT * 2));
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY && facing == EnumFacing.UP) return true;
        if (capability == CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY && facing == this.getFacing().rotateYCCW()) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY && facing == EnumFacing.UP) return (T) primary;
        if (capability == CapabilityMakoHandler.MAKO_HANDLER_CAPABILITY && facing == this.getFacing().rotateYCCW()) return (T) output;
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        if (world.isRemote) return;
        if (world.getWorldTime() % 2 == 0) {
            work();
            sendUpdates();
        }
    }

    public MakoTank getPrimary() {
        return primary;
    }

    public MakoTank getSecondary() {
        return secondary;
    }

    public MakoTank getOutput() {
        return output;
    }
}
