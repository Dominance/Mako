package com.teambr.mako.api.multiblock;

import com.teambr.mako.api.tile.TileEntityMultiblock;
import com.teambr.mako.block.InvisibleMakoBlock;
import com.teambr.mako.block.SimpleMultiblockBlock;
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
        for (int x = 0; x < multiblock.length; ++x) {
            for (int z = 0; z < multiblock[0][0].length; ++z) {
                for (int y = 0; y < multiblock[0].length; ++y) {
                    BlockPos blockPos = pos.offset(EnumFacing.DOWN, controller.getY()).offset(playerFacing.rotateY(), x).offset(EnumFacing.UP, y).offset(playerFacing, z);
                    Block test = world.getBlockState(blockPos).getBlock();
                    if (!new ItemStack(test).isItemEqual(getStructureBlocks()[x][y][z]) || world.getTileEntity(blockPos) != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void createStructureMultiblock(World world, BlockPos pos, IBlockState state, EnumFacing playerFacing) {
        for (int x = 0; x < multiblock.length; ++x) {
            for (int z = 0; z < multiblock[0][0].length; ++z) {
                for (int y = 0; y < multiblock[0].length; ++y) {
                    BlockPos blockPos = pos.offset(EnumFacing.DOWN, controller.getY()).offset(playerFacing.rotateY(), x).offset(EnumFacing.UP, y).offset(playerFacing, z);
                    Block test = world.getBlockState(blockPos).getBlock();
                    if (world.getBlockState(blockPos).getProperties().containsKey(InvisibleMakoBlock.INVISIBLE)) {
                        world.setBlockState(blockPos, ((InvisibleMakoBlock) test).setInvisible(world.getBlockState(blockPos), true));
                    }
                    if (isController(x, y, z)) {
                        if (test instanceof SimpleMultiblockBlock) {
                            world.setBlockState(pos, ((SimpleMultiblockBlock) test).setMultiblockRender(world.getBlockState(pos), playerFacing, true));
                        }
                        TileEntityMultiblock tileEntityMultiblock = createTile(playerFacing);
                        if (tileEntityMultiblock == null) continue;
                        tileEntityMultiblock.setWorld(world);
                        tileEntityMultiblock.setPos(pos);
                    }
                }
            }
        }
    }

    public TileEntityMultiblock createTile(EnumFacing facing) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("ID: " + getName() + "\n");
        for (int y = 0; y < multiblock[0].length; ++y) {
            for (int x = 0; x < multiblock.length; ++x) {
                for (int z = 0; z < multiblock[0][0].length; ++z) {
                    char c = isController(x, y, z) ? 'C' : getStructureBlocks()[x][y][z].isEmpty() ? ' ' : 'I';
                    builder.append(c);
                }
                builder.append("\n");
            }
            builder.append("------------\n");
        }
        return builder.toString();
    }


}
