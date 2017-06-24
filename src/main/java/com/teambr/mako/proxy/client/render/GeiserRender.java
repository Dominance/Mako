package com.teambr.mako.proxy.client.render;

import com.teambr.mako.proxy.client.fx.GeiserParticle;
import com.teambr.mako.world.GeiserData;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GeiserRender {

    public static GeiserData data;
    public static ChunkPos pos;

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        if (data != null && pos != null && Minecraft.getMinecraft().world != null) {
            BlockPos p = Minecraft.getMinecraft().world.getTopSolidOrLiquidBlock(new BlockPos(pos.x * 16 + 8, 0, pos.z * 16 + 8));
            for (int i = 0; i < 1; ++i) {
                GeiserParticle particle = new GeiserParticle(Minecraft.getMinecraft().world, p.getX() + Minecraft.getMinecraft().world.rand.nextDouble() - 0.5, p.getY() + Minecraft.getMinecraft().world.rand.nextDouble(), p.getZ(), data);
                Minecraft.getMinecraft().effectRenderer.addEffect(particle);
                particle = new GeiserParticle(Minecraft.getMinecraft().world, p.getX(), p.getY() + Minecraft.getMinecraft().world.rand.nextDouble(), p.getZ() + Minecraft.getMinecraft().world.rand.nextDouble() - 0.5, data);
                Minecraft.getMinecraft().effectRenderer.addEffect(particle);
            }
        }
    }
}
