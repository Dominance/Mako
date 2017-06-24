package com.teambr.mako.proxy;

import com.teambr.mako.network.PacketManager;
import com.teambr.mako.test.BlockTest;
import com.teambr.mako.world.GeiserChunkManager;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public static BlockTest test;

    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(GeiserChunkManager.getInstance());
        test = new BlockTest();
        GameRegistry.register(test);
        GameRegistry.register(new ItemBlock(test), test.getRegistryName());
    }

    public void init(FMLInitializationEvent event) {
        PacketManager.getInstance();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
