package com.teambr.mako.block.invisible;

import com.teambr.mako.block.InvisibleMakoBlock;
import net.minecraft.block.state.IBlockState;

public class TestInvisibleBlock extends InvisibleMakoBlock {

    public TestInvisibleBlock() {
        super("test");
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
