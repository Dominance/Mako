package com.teambr.mako.block.multiblock;


import com.google.common.collect.Lists;
import com.teambr.mako.block.SimpleMultiblockBlock;
import com.teambr.mako.tile.TileEntityInfuser;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

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
