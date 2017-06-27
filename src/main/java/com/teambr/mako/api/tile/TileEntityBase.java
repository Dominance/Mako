package com.teambr.mako.api.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.HashMap;

public class TileEntityBase extends TileEntity {

    private HashMap<String, INBTSerializable<NBTTagCompound>> nbtStorage;

    public TileEntityBase() {
        nbtStorage = new HashMap<>();
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        return new SPacketUpdateTileEntity(this.pos, 3, this.writeToNBT(nbt));
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound tag = super.writeToNBT(compound);
        nbtStorage.forEach((s, nbtTagCompoundINBTSerializable) -> tag.setTag(s, nbtTagCompoundINBTSerializable.serializeNBT()));
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        nbtStorage.forEach((s, nbtTagCompoundINBTSerializable) -> {
            if (compound.hasKey(s)) nbtTagCompoundINBTSerializable.deserializeNBT(compound.getCompoundTag(s));
        });
    }

    public void addNBTToStorage(String name, INBTSerializable<NBTTagCompound> nbtable) {
        nbtStorage.put(name, nbtable);
    }

    public void sendUpdates() {
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
        markDirty();
    }

}
