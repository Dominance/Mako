package com.teambr.mako.utils;

import com.teambr.mako.api.mako.IMako;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class MakoUtils {

    public static void renderMako(IMako mako, double x, double y, double z, double minY, double maxY, int j, int k, double x1, double x2, double z1, double z2) {
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();

        buffer.setTranslation(x, y, z);

        Color c = new Color(mako.color());
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        TextureAtlasSprite flow = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(new ResourceLocation(Reference.MODID, "blocks/mako_flow").toString());
        TextureAtlasSprite still = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(new ResourceLocation(Reference.MODID, "blocks/mako_still").toString());
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);//TOP
        buffer.pos(x1, maxY, z1).tex(still.getInterpolatedU(0), still.getInterpolatedV(0)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(x1, maxY, z2).tex(still.getInterpolatedU(0), still.getInterpolatedV(16)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(x2, maxY, z2).tex(still.getInterpolatedU(16), still.getInterpolatedV(16)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(x2, maxY, z1).tex(still.getInterpolatedU(16), still.getInterpolatedV(0)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        tess.draw();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);//NORTH
        buffer.pos(x1, minY, x1).tex(flow.getInterpolatedU(16), flow.getInterpolatedV(16 * maxY)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(x1, maxY, x1).tex(flow.getInterpolatedU(16), flow.getInterpolatedV(0)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(x2, maxY, x1).tex(flow.getInterpolatedU(0), flow.getInterpolatedV(0)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(x2, minY, x1).tex(flow.getInterpolatedU(0), flow.getInterpolatedV(16 * maxY)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        tess.draw();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);//SOUTH
        buffer.pos(x2, minY, z2).tex(flow.getInterpolatedU(16), flow.getInterpolatedV(16 * maxY)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(x2, maxY, z2).tex(flow.getInterpolatedU(16), flow.getInterpolatedV(0)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(x1, maxY, z2).tex(flow.getInterpolatedU(0), flow.getInterpolatedV(0)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(x1, minY, z2).tex(flow.getInterpolatedU(0), flow.getInterpolatedV(16 * maxY)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        tess.draw();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);//EAST
        buffer.pos(x2, minY, z1).tex(flow.getInterpolatedU(16), flow.getInterpolatedV(16 * maxY)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(x2, maxY, z1).tex(flow.getInterpolatedU(16), flow.getInterpolatedV(0)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(x2, maxY, z2).tex(flow.getInterpolatedU(0), flow.getInterpolatedV(0)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(x2, minY, z2).tex(flow.getInterpolatedU(0), flow.getInterpolatedV(16 * maxY)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        tess.draw();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);//WEST
        buffer.pos(z1, minY, z2).tex(flow.getInterpolatedU(16), flow.getInterpolatedV(16 * maxY)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(z1, maxY, z2).tex(flow.getInterpolatedU(16), flow.getInterpolatedV(0)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(z1, maxY, z1).tex(flow.getInterpolatedU(0), flow.getInterpolatedV(0)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(z1, minY, z1).tex(flow.getInterpolatedU(0), flow.getInterpolatedV(16 * maxY)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        tess.draw();

        buffer.setTranslation(0, 0, 0);
    }

}
