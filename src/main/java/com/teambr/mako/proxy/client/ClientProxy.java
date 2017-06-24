package com.teambr.mako.proxy.client;

import com.teambr.mako.proxy.CommonProxy;
import com.teambr.mako.proxy.client.render.GeiserRender;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        OBJLoader.INSTANCE.addDomain("mako");

        MinecraftForge.EVENT_BUS.register(new GeiserRender());

        blockRegistry.forEach((s, iRegistrable) -> iRegistrable.registerRender());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
