package com.teambr.mako.api.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityTick extends TileEntityBase implements ITickable, IHasGui {

    @Override
    public void update() {
        if (world.isRemote) return;

    }


    @Override
    public Container getClientGUI(int id, EntityPlayer player, World world, BlockPos pos) {
        return null;
    }

}
