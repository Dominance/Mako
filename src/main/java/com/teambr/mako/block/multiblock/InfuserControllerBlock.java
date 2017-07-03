package com.teambr.mako.block.multiblock;


import com.teambr.mako.block.SimpleMultiblockBlock;
import com.teambr.mako.tile.TileEntityInfuser;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class InfuserControllerBlock extends SimpleMultiblockBlock {


    public InfuserControllerBlock() {
        super("infuser_controller");
        setLightOpacity(0);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        if (worldIn.isRemote) return new TileEntityInfuser();
        return null;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return true;
    }
}
