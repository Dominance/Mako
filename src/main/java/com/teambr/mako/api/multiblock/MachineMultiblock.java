package com.teambr.mako.api.multiblock;

import com.teambr.mako.block.InvisibleMakoBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineMultiblock implements IMultiblock {

    private String name;
    private ItemStack[][][] multiblock;
    private BlockPos controller;

    public MachineMultiblock(String name, ItemStack[][][] multiblock, BlockPos controller) {
        this.name = name;
        this.multiblock = multiblock;
        this.controller = controller;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ItemStack[][][] getStructureBlocks() {
        return multiblock;
    }

    @Override
    public boolean isController(int x, int y, int z) {
        return controller.equals(new BlockPos(x, y, z));
    }

    @Override
    public boolean isStructureMultiblock(World world, BlockPos pos, IBlockState state, EnumFacing playerFacing) {
        BlockPos origin = pos.offset(playerFacing).offset(EnumFacing.DOWN, controller.getY());
        for (int x = 0; x < multiblock.length; ++x) {
            for (int z = 0; z < multiblock[0][0].length; ++z) {
                for (int y = 0; y < multiblock[0].length; ++y) {
                    BlockPos blockPos = origin.offset(playerFacing, x).offset(EnumFacing.UP, y).offset(playerFacing.rotateY(), z);
                    Block test = world.getBlockState(blockPos).getBlock();
                    if (isController(x, y, z)) continue;
                    if (!new ItemStack(test).getItem().equals(getStructureBlocks()[x][y][z].getItem())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void createStructureMultiblock(World world, BlockPos pos, IBlockState state, EnumFacing playerFacing) {
        BlockPos origin = pos.offset(playerFacing).offset(EnumFacing.DOWN, controller.getY());
        for (int x = 0; x < multiblock.length; ++x) {
            for (int z = 0; z < multiblock[0][0].length; ++z) {
                for (int y = 0; y < multiblock[0].length; ++y) {
                    BlockPos blockPos = origin.offset(playerFacing, x).offset(EnumFacing.UP, y).offset(playerFacing.rotateY(), z);
                    Block test = world.getBlockState(blockPos).getBlock();
                    if (test instanceof InvisibleMakoBlock) {
                        ((InvisibleMakoBlock) test).setInvisible(world.getBlockState(pos), true);
                    }
                    if (isController(x, y, z)) {
                        //TODO Create Tile and shuch
                    }
                }
            }
        }
    }

}
