package com.teambr.mako.proxy;

import com.teambr.mako.Mako;
import com.teambr.mako.api.block.IRegistrable;
import com.teambr.mako.block.InfuserControllerBlock;
import com.teambr.mako.block.MakoBlock;
import com.teambr.mako.block.SimpleMultiblockBlock;
import com.teambr.mako.multiblock.InfuserMultiblock;
import com.teambr.mako.network.PacketManager;
import com.teambr.mako.world.GeiserChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

import java.util.HashMap;

public class CommonProxy {

    public static HashMap<String, IRegistrable> blockRegistry = new HashMap<>();

    static {
        addBlock(new InfuserControllerBlock());
    }

    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(GeiserChunkManager.getInstance());
    }

    public void init(FMLInitializationEvent event) {
        PacketManager.getInstance();
        ((SimpleMultiblockBlock) blockRegistry.get("infuser_controller")).setMultiblock(InfuserMultiblock.INFUSER_MULTIBLOCK);
    }

    public void postInit(FMLPostInitializationEvent event) {

    }


    public static void addBlock(MakoBlock block) {
        Mako.LOGGER.log(Level.INFO, "Added Block " + block.getRegistryName());
        blockRegistry.put(block.getRegistryName().getResourcePath(), block);
    }
}
