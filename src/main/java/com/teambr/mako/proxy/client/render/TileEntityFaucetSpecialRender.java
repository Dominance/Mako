package com.teambr.mako.proxy.client.render;

import com.teambr.mako.api.mako.MakoRegistry;
import com.teambr.mako.block.DirectionalBlock;
import com.teambr.mako.tile.TileEntityFaucet;
import com.teambr.mako.utils.MakoUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;

public class TileEntityFaucetSpecialRender extends TileEntitySpecialRenderer<TileEntityFaucet> {

    @Override
    public void render(TileEntityFaucet te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        if (!te.isActive()) return;
        if (te.getSource() == null) return;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        RenderHelper.disableStandardItemLighting();
        int i = te.getWorld().getCombinedLight(te.getPos(), 0);
        int j = i >> 16 & 65535;
        int k = i & 65535;
        double x1, x2, z1, z2;
        x1 = x2 = z1 = z2 = 0;

        if (te.getWorld().getBlockState(te.getPos()).getValue(DirectionalBlock.FACING).equals(EnumFacing.NORTH)) {
            x2 = 0.1;
            z2 = 0.1;
            x += 0.45;
            z += 0.42;
        }
        if (te.getWorld().getBlockState(te.getPos()).getValue(DirectionalBlock.FACING).equals(EnumFacing.EAST)) {
            x2 = 0.1;
            z2 = 0.1;
            x += 0.48;
            z += 0.45;
        }
        if (te.getWorld().getBlockState(te.getPos()).getValue(DirectionalBlock.FACING).equals(EnumFacing.WEST)) {
            x2 = 0.1;
            z2 = 0.1;
            x += 0.42;
            z += 0.45;
        }
        if (te.getWorld().getBlockState(te.getPos()).getValue(DirectionalBlock.FACING).equals(EnumFacing.SOUTH)) {
            x2 = 0.1;
            z2 = 0.1;
            x += 0.45;
            z += 0.48;
        }

        if (te.getSource() != null) {
            MakoUtils.renderMako(MakoRegistry.getInstance().getMako(te.getSource()), x, y, z, 0, 0.3, j, k, x1, x2, z1, z2);
        }

        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.popMatrix();
    }
}
