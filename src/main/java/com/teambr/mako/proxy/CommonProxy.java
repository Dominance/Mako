package com.teambr.mako.proxy;

import com.teambr.mako.api.block.IRegistrable;
import com.teambr.mako.block.MakoBlock;
import com.teambr.mako.network.PacketManager;
import com.teambr.mako.world.GeiserChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.HashMap;

public class CommonProxy {

    public static HashMap<String, IRegistrable> blockRegistry = new HashMap<>();

    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(GeiserChunkManager.getInstance());

        blockRegistry.forEach((s, iRegistrable) -> iRegistrable.register());
    }

    public void init(FMLInitializationEvent event) {
        PacketManager.getInstance();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void addBlock(MakoBlock block) {
        blockRegistry.put(block.getRegistryName().getResourcePath(), block);
    }
}
