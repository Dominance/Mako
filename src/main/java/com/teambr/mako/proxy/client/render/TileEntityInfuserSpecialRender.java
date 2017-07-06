package com.teambr.mako.proxy.client.render;

import com.teambr.mako.proxy.client.ClientProxy;
import com.teambr.mako.tile.TileEntityInfuser;
import com.teambr.mako.utils.MakoUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

public class TileEntityInfuserSpecialRender extends TileEntitySpecialRenderer<TileEntityInfuser> {


    @Override
    public void render(TileEntityInfuser te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        RenderHelper.disableStandardItemLighting();
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }
        int i = te.getWorld().getCombinedLight(te.getPos(), 0);
        int j = i >> 16 & 65535;
        int k = i & 65535;
        double x1, x2, z1, z2;
        x1 = x2 = z1 = z2 = 0;
        x2 = 0.65;
        z2 = 1.65;
        if (te.getFacing().equals(EnumFacing.NORTH)) {
            GlStateManager.translate(x, y - 1, z + 1);
            GlStateManager.rotate(90, 0, 1, 0);
        }
        if (te.getFacing().equals(EnumFacing.SOUTH)) {
            GlStateManager.translate(x + 1, y - 1, z);
            GlStateManager.rotate(90, 0, -1, 0);
        }
        if (te.getFacing().equals(EnumFacing.EAST)) {
            GlStateManager.translate(x, y - 1, z);

        }
        if (te.getFacing().equals(EnumFacing.WEST)) {
            GlStateManager.translate(x, y - 1, z - 1);
        }
        double offset = 0;
        GlStateManager.translate(0.15, 1, 0.15);
        if (te.getPrimary().getMakoStack() != null) {
            MakoUtils.renderMako(te.getPrimary().getMakoStack().getMako(), x, y, z, 0, 0.0002d * te.getPrimary().getMakoAmount(), j, k, x1, x2, z1, z2);
            offset += 0.0002d * te.getPrimary().getMakoAmount();
        }
        if (te.getSecondary().getMakoStack() != null) {
            MakoUtils.renderMako(te.getSecondary().getMakoStack().getMako(), x, y, z, offset, offset + 0.0002d * te.getSecondary().getMakoAmount(), j, k, x1, x2, z1, z2);
            offset += 0.0002d * te.getSecondary().getMakoAmount();
        }
        if (te.getOutput().getMakoStack() != null) {
            MakoUtils.renderMako(te.getOutput().getMakoStack().getMako(), x, y, z, offset, offset + 0.0002d * te.getOutput().getMakoAmount(), j, k, x1, x2, z1, z2);
        }
        GlStateManager.translate(-0.15 + 0.5, 0.38, -0.15);
        //GlStateManager.rotate((getWorld().getTotalWorldTime() % 360) *0.5f, 0, 0, 1); //TODO Change rotation based on tile
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModelBrightnessColor(ClientProxy.infuser_rotor, 0.5f, 255, 255, 255);
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }


}
