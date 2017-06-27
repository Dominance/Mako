package com.teambr.mako.tile;


import com.teambr.mako.api.tile.TileEntityMultiblock;
import com.teambr.mako.multiblock.InfuserMultiblock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileEntityInfuser extends TileEntityMultiblock {

    public TileEntityInfuser() {
        super();
        this.setMultiblock(InfuserMultiblock.INFUSER_MULTIBLOCK);
    }

    @Override
    public boolean hasExternalCapability(BlockPos pos, Capability<?> capability, @Nullable EnumFacing facing) {
        return false;
    }

    @Override
    public <T> T getExternalCapability(BlockPos pos, Capability<T> capability, @Nullable EnumFacing facing) {
        return null;
    }

    @Override
    public Container getClientGUI(int id, EntityPlayer player, World world, BlockPos pos) {
        return null;
    }
}
