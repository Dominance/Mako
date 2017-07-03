package com.teambr.mako.proxy.client.render;

import com.teambr.mako.tile.TileEntityInfuser;
import com.teambr.mako.utils.MakoUtils;
import com.teambr.mako.utils.Reference;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class TileEntityInfuserSpecialRender extends TileEntitySpecialRenderer<TileEntityInfuser> {

    public static ResourceLocation flow = new ResourceLocation(Reference.MODID, "textures/blocks/mako_flow.png");
    public static ResourceLocation still = new ResourceLocation(Reference.MODID, "textures/blocks/mako_still.png");

    @Override
    public void render(TileEntityInfuser te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        RenderHelper.disableStandardItemLighting();
        int i = te.getWorld().getCombinedLight(te.getPos(), 0);
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
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }


}
