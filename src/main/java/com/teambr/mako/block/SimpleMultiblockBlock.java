package com.teambr.mako.block;

import com.teambr.mako.api.block.Render;
import com.teambr.mako.api.multiblock.IMultiblock;
import com.teambr.mako.api.tile.TileEntityMultiblock;
import com.teambr.mako.api.tile.TileEntitySimpleMultiblockComponent;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nullable;
import java.util.Arrays;

public class SimpleMultiblockBlock extends MakoBlock implements ITileEntityProvider {

    public static PropertyEnum<Render> RENDER = PropertyEnum.create("render", Render.class);

    private IMultiblock multiblock;

    public SimpleMultiblockBlock(String name) {
        super(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(RENDER, Render.OFF));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(RENDER, Render.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return Arrays.asList(Render.values()).indexOf(state.getValue(RENDER));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{RENDER});
    }

    @Override
    public void registerRender() {
        RENDER.getAllowedValues().forEach(render -> ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), Arrays.asList(Render.values()).indexOf(render), new ModelResourceLocation(this.getRegistryName().toString(), "render=" + render.getName())));
    }

    public IBlockState setMultiblockRender(IBlockState state, EnumFacing facing, boolean enable) {
        if (enable) {
            switch (facing) {
                case NORTH:
                    return state.withProperty(RENDER, Render.NORTH);
                case SOUTH:
                    return state.withProperty(RENDER, Render.SOUTH);
                case EAST:
                    return state.withProperty(RENDER, Render.EAST);
                default:
                    return state.withProperty(RENDER, Render.WEST);
            }
        } else {
            return state.withProperty(RENDER, Render.OFF);
        }
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) return true;
        TileEntity tile = worldIn.getTileEntity(pos);
        System.out.println(((TileEntityMultiblock) tile).getFacing());
        if (tile == null) return true;
        if (tile instanceof TileEntityMultiblock) {
            if (!((TileEntityMultiblock) tile).isFormed() && multiblock.isStructureMultiblock(worldIn, pos, state, facing.getOpposite())) {
                multiblock.createStructureMultiblock(worldIn, pos, state, facing.getOpposite());
                return true;
            }
        } else {

        }
        return true;
    }

    public IMultiblock getMultiblock() {
        return multiblock;
    }

    public void setMultiblock(IMultiblock multiblock) {
        this.multiblock = multiblock;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.isRemote) return;
        if (worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileEntityMultiblock) {
            ((TileEntityMultiblock) worldIn.getTileEntity(pos)).destroyMultiblock();
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySimpleMultiblockComponent();
    }
}
