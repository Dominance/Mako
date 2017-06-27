package com.teambr.mako.block;

import com.teambr.mako.Mako;
import com.teambr.mako.api.block.IRegistrable;
import com.teambr.mako.utils.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import org.apache.logging.log4j.Level;


public class MakoBlock extends Block implements IRegistrable {

    public MakoBlock(String name) {
        super(Material.ROCK);
        this.setRegistryName(new ResourceLocation(Reference.MODID, name));
        this.setUnlocalizedName(this.getRegistryName().toString());
        this.setCreativeTab(Mako.creativeTab);

    }

    @Override
    public void register(IForgeRegistry registry) {
        Mako.LOGGER.log(Level.INFO, "Registering block" + this.getRegistryName());
        registry.register(this);
    }

    @Override
    public void registerItem(IForgeRegistry registry) {
        Mako.LOGGER.log(Level.INFO, "Registering item" + this.getRegistryName());
        registry.register(new ItemBlock(this).setRegistryName(this.getRegistryName()).setUnlocalizedName(this.getUnlocalizedName()));
    }

    @Override
    public void registerRender() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName().toString(), "normal"));
    }
}
