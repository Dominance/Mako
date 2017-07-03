package com.teambr.mako.block.directional;

import com.teambr.mako.block.DirectionalBlock;
import com.teambr.mako.tile.TileEntityFaucet;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FaucetBlock extends DirectionalBlock implements ITileEntityProvider {

    public FaucetBlock() {
        super("faucet");
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        if (facing == EnumFacing.NORTH) return new AxisAlignedBB(0.25, 0.09, 0.6, 0.75, 0.59, 0);
        if (facing == EnumFacing.SOUTH) return new AxisAlignedBB(0.25, 0.09, 0.4, 0.75, 0.59, 1);
        if (facing == EnumFacing.EAST) return new AxisAlignedBB(0.4, 0.09, 0.25, 1, 0.59, 0.75);
        return new AxisAlignedBB(0.6, 0.09, 0.25, 0, 0.59, 0.75);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        EnumFacing facing = blockState.getValue(FACING);
        if (facing == EnumFacing.NORTH) return new AxisAlignedBB(0.25, 0.09, 0.6, 0.75, 0.59, 0);
        if (facing == EnumFacing.SOUTH) return new AxisAlignedBB(0.25, 0.09, 0.4, 0.75, 0.59, 1);
        if (facing == EnumFacing.EAST) return new AxisAlignedBB(0.4, 0.09, 0.25, 1, 0.59, 0.75);
        return new AxisAlignedBB(0.6, 0.09, 0.25, 0, 0.59, 0.75);
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

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFaucet();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) return true;
        if (worldIn.getTileEntity(pos) instanceof TileEntityFaucet) {
            ((TileEntityFaucet) worldIn.getTileEntity(pos)).toggle();
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
