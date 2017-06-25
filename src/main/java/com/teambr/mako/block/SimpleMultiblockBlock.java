package com.teambr.mako.block;

import com.teambr.mako.api.block.Render;
import com.teambr.mako.api.multiblock.IMultiblock;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.ModelLoader;

import java.util.Arrays;

public class SimpleMultiblockBlock extends MakoBlock {

    public static PropertyEnum<Render> RENDER = PropertyEnum.create("render", Render.class);

    private IMultiblock multiblock;

    public SimpleMultiblockBlock(String name, IMultiblock multiblock) {
        super(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(RENDER, Render.OFF));
        this.multiblock = multiblock;
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
    public void registerRender() {
        RENDER.getAllowedValues().forEach(render -> ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), Arrays.asList(Render.values()).indexOf(render), new ModelResourceLocation(this.getRegistryName().toString(), "render=" + render.getName())));
    }

    public void setMultiblockRender(IBlockState state, EnumFacing facing, boolean enable) {
        if (enable) {
            switch (facing) {
                case NORTH:
                    state.withProperty(RENDER, Render.NORTH);
                    break;
                case SOUTH:
                    state.withProperty(RENDER, Render.SOUTH);
                    break;
                case EAST:
                    state.withProperty(RENDER, Render.EAST);
                    break;
                default:
                    state.withProperty(RENDER, Render.WEST);
            }
        } else {
            state.withProperty(RENDER, Render.OFF);
        }
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
