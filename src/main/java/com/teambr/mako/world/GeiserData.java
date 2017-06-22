package com.teambr.mako.world;

import com.teambr.mako.api.mako.IMako;
import com.teambr.mako.api.mako.MakoRegistry;
import net.minecraft.nbt.NBTTagCompound;

public class GeiserData {

    private IMako mako;
    private int currentAmount;
    private long lastOutput;

    public GeiserData(IMako mako, int currentAmount, long lastOutput) {
        this.mako = mako;
        this.currentAmount = currentAmount;
        this.lastOutput = lastOutput;
    }

    public NBTTagCompound writeFromNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("Type", mako.getName());
        compound.setInteger("Amount", currentAmount);
        compound.setLong("LastOutput", lastOutput);
        return compound;
    }


    public static GeiserData readFromNBT(NBTTagCompound compound) {
        return new GeiserData(compound.hasKey("Type") ? MakoRegistry.getInstance().getMako(compound.getString("Type")) : MakoRegistry.getInstance().getRandomPureMako(),
                compound.hasKey("Amount") ? compound.getInteger("Amount") : 0,
                compound.hasKey("LastOutput") ? compound.getLong("LastOutput") : 0);
    }

    @Override
    public String toString() {
        return "GeiserData{" +
                "mako=" + mako.getName() +
                ", currentAmount=" + currentAmount +
                ", lastOutput=" + lastOutput +
                '}';
    }

    public IMako getMako() {
        return mako;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public long getLastOutput() {
        return lastOutput;
    }
}
