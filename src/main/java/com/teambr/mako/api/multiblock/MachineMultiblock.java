package com.teambr.mako.api.multiblock;

import com.teambr.mako.api.tile.TileEntityMultiblock;
import com.teambr.mako.api.tile.TileEntitySimpleMultiblockComponent;
import com.teambr.mako.block.InvisibleMakoBlock;
import com.teambr.mako.block.SimpleMultiblockBlock;
import com.teambr.mako.utils.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineMultiblock<T extends TileEntityMultiblock> implements IMultiblock {

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
                    if (test instanceof InvisibleMakoBlock) {
                        world.setBlockState(blockPos, ((InvisibleMakoBlock) test).setInvisible(world.getBlockState(blockPos), true));
                        TileEntitySimpleMultiblockComponent component = new TileEntitySimpleMultiblockComponent(pos, world, blockPos);
                        world.setTileEntity(blockPos, component);
                    }
                    if (isController(x, y, z)) {
                        if (test instanceof SimpleMultiblockBlock) {
                            world.setBlockState(pos, ((SimpleMultiblockBlock) test).setMultiblockRender(world.getBlockState(pos), playerFacing, true));
                        }
                        NBTTagCompound compound = new NBTTagCompound();
                        compound.setString("id", new ResourceLocation(Reference.MODID, name + "_tile").toString());
                        TileEntityMultiblock tileEntityMultiblock = (TileEntityMultiblock) T.create(world, compound);
                        tileEntityMultiblock.setWorld(world);
                        tileEntityMultiblock.setPos(blockPos);
                        tileEntityMultiblock.setFacing(playerFacing);
                        world.setTileEntity(blockPos, tileEntityMultiblock);
                        tileEntityMultiblock.setMultiblock(this);
                    }
                }
            }
        }
    }

    @Override
    public void destroyMultiblock(World world, BlockPos pos, IBlockState state, EnumFacing facing) {
        for (int x = 0; x < multiblock.length; ++x) {
            for (int z = 0; z < multiblock[0][0].length; ++z) {
                for (int y = 0; y < multiblock[0].length; ++y) {
                    BlockPos blockPos = pos.offset(EnumFacing.DOWN, controller.getY()).offset(facing.rotateY(), x).offset(EnumFacing.UP, y).offset(facing, z);
                    Block test = world.getBlockState(blockPos).getBlock();
                    if (world.getTileEntity(blockPos) != null) {//TODO Implement item drop
                        world.removeTileEntity(blockPos);
                        if (test instanceof InvisibleMakoBlock) {
                            world.setBlockState(blockPos, ((InvisibleMakoBlock) test).setInvisible(world.getBlockState(blockPos), false));
                        }
                        if (test instanceof SimpleMultiblockBlock) {
                            world.setBlockState(pos, ((SimpleMultiblockBlock) test).setMultiblockRender(world.getBlockState(pos), facing, false));
                        }
                    }
                }
            }
        }
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
