package com.teambr.mako.proxy.client;

import com.teambr.mako.proxy.CommonProxy;
import com.teambr.mako.proxy.client.event.MakoTextuxteStichEvent;
import com.teambr.mako.proxy.client.render.GeiserRender;
import com.teambr.mako.proxy.client.render.TileEntityFaucetSpecialRender;
import com.teambr.mako.proxy.client.render.TileEntityInfuserSpecialRender;
import com.teambr.mako.tile.TileEntityFaucet;
import com.teambr.mako.tile.TileEntityInfuser;
import com.teambr.mako.utils.Reference;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    public static IBakedModel infuser_rotor;
    public static IModel infuser_model;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        OBJLoader.INSTANCE.addDomain("mako");

        MinecraftForge.EVENT_BUS.register(new GeiserRender());
        MinecraftForge.EVENT_BUS.register(new MakoTextuxteStichEvent());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInfuser.class, new TileEntityInfuserSpecialRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFaucet.class, new TileEntityFaucetSpecialRender());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        blockRegistry.forEach((s, iRegistrable) -> iRegistrable.registerRender());
        try {
            infuser_model = OBJLoader.INSTANCE.loadModel(new ResourceLocation(Reference.MODID, "models/block/infuser_rotor.obj"));
            infuser_rotor = infuser_model.bake(TRSRTransformation.identity(), DefaultVertexFormats.BLOCK, ModelLoader.defaultTextureGetter());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
