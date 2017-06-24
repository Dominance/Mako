package com.teambr.mako.test;

import com.teambr.mako.utils.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import java.util.Arrays;

public class BlockTest extends Block {

    public static final PropertyEnum<State> STATE_PROPERTY_ENUM = PropertyEnum.<State>create("state", State.class);

    public BlockTest() {
        super(Material.ROCK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STATE_PROPERTY_ENUM, State.SINGLE));
        setUnlocalizedName(Reference.MODID + ":test");
        setRegistryName(Reference.MODID, "test");
        setHardness(3.0F);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(STATE_PROPERTY_ENUM, State.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return Arrays.asList(State.values()).indexOf(state.getValue(STATE_PROPERTY_ENUM));
    }

    public void registerModels() {
        STATE_PROPERTY_ENUM.getAllowedValues().forEach(integer -> ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), Arrays.asList(State.values()).indexOf(integer), new ModelResourceLocation(this.getRegistryName().toString(), "state=" + integer.getName())));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STATE_PROPERTY_ENUM);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        worldIn.setBlockState(pos, state.withProperty(STATE_PROPERTY_ENUM, State.RENDERER));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, getMetaFromState(state));
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    private enum State implements IStringSerializable {
        SINGLE, INVISIBLE, RENDERER;

        @Override
        public String getName() {
            return this.toString().toLowerCase();
        }
    }
}
