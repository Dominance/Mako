package com.teambr.mako.api.multiblock;

import com.teambr.mako.api.tile.TileEntityMultiblock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMultiblock<T extends TileEntityMultiblock> {

    public String getName();

    public ItemStack[][][] getStructureBlocks();

    public boolean isController(int x, int y, int z);

    public boolean isStructureMultiblock(World world, BlockPos pos, IBlockState state, EnumFacing playerFacing);

    public void createStructureMultiblock(World world, BlockPos pos, IBlockState state, EnumFacing playerFacing);

    public void destroyMultiblock(World world, BlockPos pos, IBlockState state, EnumFacing facing);
}
