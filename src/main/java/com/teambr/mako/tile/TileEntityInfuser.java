package com.teambr.mako.tile;


import com.teambr.mako.api.mako.CombinedMako;
import com.teambr.mako.api.mako.MakoRegistry;
import com.teambr.mako.api.mako.stack.MakoStack;
import com.teambr.mako.api.mako.stack.MakoTank;
import com.teambr.mako.api.tile.TileEntityMultiblock;
import com.teambr.mako.multiblock.InfuserMultiblock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileEntityInfuser extends TileEntityMultiblock {

    private MakoTank primary;
    private MakoTank secondary;
    private MakoTank output;

    public TileEntityInfuser() {
        super("infuser", EnumFacing.NORTH);
        this.setMultiblock(InfuserMultiblock.INFUSER_MULTIBLOCK);
        primary = new MakoTank(4000);
        secondary = new MakoTank(4000);
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
        return false;
    }

    @Override
    public <T> T getExternalCapability(BlockPos pos, Capability<T> capability, @Nullable EnumFacing facing) {
        return null;
    }

    private static int WORK_AMOUNT = 1;

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

}
