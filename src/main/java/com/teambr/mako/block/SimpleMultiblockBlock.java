package com.teambr.mako.block;

import com.teambr.mako.api.tile.TileEntityBase;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class SimpleMultiblockBlock<T extends TileEntityBase> extends MakoBlock {

    public static PropertyBool RENDER = PropertyBool.create("render");

    public SimpleMultiblockBlock(String name) {
        super(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(RENDER, false));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta == 0) return this.getDefaultState().withProperty(RENDER, false);
        if (meta == 1) return this.getDefaultState().withProperty(RENDER, true);
        return super.getStateFromMeta(meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(RENDER) ? 1 : 0;
    }

    @Override
    public void registerRender() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName().toString(), "render=false"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 1, new ModelResourceLocation(this.getRegistryName().toString(), "render=true"));
    }


}
