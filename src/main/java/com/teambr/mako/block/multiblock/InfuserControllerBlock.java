package com.teambr.mako.block.multiblock;


import com.teambr.mako.block.SimpleMultiblockBlock;
import com.teambr.mako.tile.TileEntityInfuser;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class InfuserControllerBlock extends SimpleMultiblockBlock {

    public InfuserControllerBlock() {
        super("infuser_controller");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityInfuser();
    }
}
