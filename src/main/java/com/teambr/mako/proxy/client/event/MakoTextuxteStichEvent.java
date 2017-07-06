package com.teambr.mako.proxy.client.event;

import com.teambr.mako.utils.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MakoTextuxteStichEvent {

    @SubscribeEvent
    public void textureStich(TextureStitchEvent.Pre pre) {
        pre.getMap().registerSprite(new ResourceLocation(Reference.MODID, "blocks/mako_still"));
        pre.getMap().registerSprite(new ResourceLocation(Reference.MODID, "blocks/mako_flow"));
        pre.getMap().registerSprite(new ResourceLocation(Reference.MODID, "blocks/infuser_rotor"));
    }
}
