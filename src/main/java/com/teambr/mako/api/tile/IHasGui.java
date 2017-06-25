package com.teambr.mako.api.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHasGui {

    Container getClientGUI(int id, EntityPlayer player, World world, BlockPos pos);

}
