package com.teambr.mako.proxy;

import com.teambr.mako.Mako;
import com.teambr.mako.api.block.IRegistrable;
import com.teambr.mako.api.tile.TileEntitySimpleMultiblockComponent;
import com.teambr.mako.block.MakoBlock;
import com.teambr.mako.block.normal.PortableTankBlock;
import com.teambr.mako.block.SimpleMultiblockBlock;
import com.teambr.mako.block.directional.FaucetBlock;
import com.teambr.mako.block.invisible.TestInvisibleBlock;
import com.teambr.mako.block.multiblock.InfuserControllerBlock;
import com.teambr.mako.multiblock.InfuserMultiblock;
import com.teambr.mako.network.PacketManager;
import com.teambr.mako.tile.TileEntityFaucet;
import com.teambr.mako.tile.TileEntityInfuser;
import com.teambr.mako.tile.TileEntityPortableTank;
import com.teambr.mako.utils.Reference;
import com.teambr.mako.world.GeiserChunkManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Level;

import java.util.HashMap;

public class CommonProxy {

    public static HashMap<String, IRegistrable> blockRegistry = new HashMap<>();

    static {
        addBlock(new InfuserControllerBlock());
        addBlock(new TestInvisibleBlock());
        addBlock(new PortableTankBlock());
        addBlock(new FaucetBlock());
    }

    public static void addBlock(MakoBlock block) {
        Mako.LOGGER.log(Level.INFO, "Added Block " + block.getRegistryName());
        blockRegistry.put(block.getRegistryName().getResourcePath(), block);
    }

    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(GeiserChunkManager.getInstance());

        GameRegistry.registerTileEntity(TileEntitySimpleMultiblockComponent.class, new ResourceLocation(Reference.MODID, "simple_multiblock_component_tile").toString());
        GameRegistry.registerTileEntity(TileEntityInfuser.class, new ResourceLocation(Reference.MODID, "infuser_tile").toString());
        GameRegistry.registerTileEntity(TileEntityPortableTank.class, new ResourceLocation(Reference.MODID, "single_tank_tile").toString());
        GameRegistry.registerTileEntity(TileEntityFaucet.class, new ResourceLocation(Reference.MODID, "faucet_tile").toString());
    }

    public void init(FMLInitializationEvent event) {
        PacketManager.getInstance();
        ((SimpleMultiblockBlock) blockRegistry.get("infuser_controller")).setMultiblock(InfuserMultiblock.INFUSER_MULTIBLOCK);
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
