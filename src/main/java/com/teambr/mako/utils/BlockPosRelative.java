package com.teambr.mako.utils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BlockPosRelative {

    public static BlockPos getRealBlockPosFromRelative(EnumFacing facing, BlockPos current, BlockPos relative) {
        return current.offset(facing, relative.getX()).offset(EnumFacing.UP, relative.getY()).offset(facing.rotateY(), relative.getZ());
    }

}
