package com.teambr.mako.block;

import com.teambr.mako.api.block.Render;
import com.teambr.mako.api.tile.TileEntitySimpleMultiblockComponent;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nullable;

public class InvisibleMakoBlock extends MakoBlock implements ITileEntityProvider {

    public static PropertyBool INVISIBLE = PropertyBool.create("invisible");

    public InvisibleMakoBlock(String name) {
        super(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(INVISIBLE, false));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta == 0) return this.getDefaultState().withProperty(INVISIBLE, false);
        if (meta == 1) return this.getDefaultState().withProperty(INVISIBLE, true);
        return super.getStateFromMeta(meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(INVISIBLE) ? 1 : 0;
    }

    @Override
    public void registerRender() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName().toString(), "invisible=false"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 1, new ModelResourceLocation(this.getRegistryName().toString(), "invisible=true"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0,new ModelResourceLocation(this.getRegistryName().toString(), "invisible=false"));
    }

    public IBlockState setInvisible(IBlockState state, boolean invisible) {
        return state.withProperty(INVISIBLE, invisible);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{INVISIBLE});
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.isRemote) return;
        if (worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileEntitySimpleMultiblockComponent) {
            ((TileEntitySimpleMultiblockComponent) worldIn.getTileEntity(pos)).notifyContollerOnBreak();
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        if (worldIn.isRemote) return new TileEntitySimpleMultiblockComponent();
        return null;
    }
    @Override
    public boolean isFullCube(IBlockState state) {
        return !state.getValue(INVISIBLE);
    }

}
