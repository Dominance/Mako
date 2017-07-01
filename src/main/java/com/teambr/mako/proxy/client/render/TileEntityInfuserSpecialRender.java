package com.teambr.mako.proxy.client.render;

import com.teambr.mako.api.mako.IMako;
import com.teambr.mako.api.mako.MakoRegistry;
import com.teambr.mako.tile.TileEntityInfuser;
import com.teambr.mako.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class TileEntityInfuserSpecialRender extends TileEntitySpecialRenderer<TileEntityInfuser> {

    public static ResourceLocation flow = new ResourceLocation(Reference.MODID, "textures/blocks/mako_flow.png");
    public static ResourceLocation still = new ResourceLocation(Reference.MODID, "textures/blocks/mako_still.png");

    @Override
    public void render(TileEntityInfuser te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(516, 0.003921569F);
        RenderHelper.disableStandardItemLighting();
        int i = te.getWorld().getCombinedLight(te.getPos(), 7);
        int j = i >> 16 & 65535;
        int k = i & 65535;
        double x1, x2, z1, z2;
        x1 = x2 = z1 = z2 = 0;
        if (te.getFacing().equals(EnumFacing.NORTH)) {
            x2 = 1.65;
            z2 = 0.75;
            x += 0.15;
            z += 0.15;
        }
        if (te.getFacing().equals(EnumFacing.SOUTH)) {
            x2 = 1.65;
            z2 = 0.65;
            --x;
            x += 0.15;
            z += 0.15;
        }
        if (te.getFacing().equals(EnumFacing.EAST)) {
            x2 = 0.75;
            z2 = 1.65;
            x += 0.15;
            z += 0.15;
        }
        if (te.getFacing().equals(EnumFacing.WEST)) {
            x2 = 0.65;
            z2 = 1.65;
            x += 0.15;
            z += 0.15;
            --z;
        }
        double offset = 0;
        if (te.getPrimary().getMakoStack() != null) {
            renderMako(te.getPrimary().getMakoStack().getMako(), x, y, z, 0,  0.0002d*te.getPrimary().getMakoAmount(), j, k, x1, x2, z1, z2);
            offset += 0.0002d*te.getPrimary().getMakoAmount();
        }
        if (te.getSecondary().getMakoStack() != null ) {
            renderMako(te.getSecondary().getMakoStack().getMako(), x, y, z, offset, offset  + 0.0002d*te.getSecondary().getMakoAmount(), j, k, x1, x2, z1, z2);
            offset  += 0.0002d*te.getSecondary().getMakoAmount();
        }
        if (te.getOutput().getMakoStack() != null){
            renderMako(te.getOutput().getMakoStack().getMako(), x, y, z, offset, offset + 0.0002d*te.getOutput().getMakoAmount(), j, k, x1, x2, z1, z2);
        }
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.popMatrix();
    }


    public void renderMako(IMako mako, double x, double y, double z, double minY, double maxY, int j, int k, double x1, double x2, double z1, double z2) {
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();

        buffer.setTranslation(x, y, z);

        Color c = new Color(mako.color());
        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        TextureAtlasSprite flow = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(new ResourceLocation(Reference.MODID, "blocks/mako_flow").toString());
        TextureAtlasSprite still = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(new ResourceLocation(Reference.MODID, "blocks/mako_still").toString());
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);//TOP
        buffer.pos(x1, maxY, z1).tex(still.getInterpolatedU(0), still.getInterpolatedV(0)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(x1, maxY, z2).tex(still.getInterpolatedU(0), still.getInterpolatedV(16)).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        buffer.pos(x2, maxY, z2).tex(still.getInterpolatedU(16), still.getInterpolatedV(16 )).lightmap(j, k).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
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
