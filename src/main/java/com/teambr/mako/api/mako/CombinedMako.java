package com.teambr.mako.api.mako;

import net.minecraft.util.ResourceLocation;

public class CombinedMako implements IMako {

    private String name;
    private ResourceLocation icon;
    private int color;
    private IMako[] parents;

    public CombinedMako(String name, ResourceLocation icon, int color, IMako... parents) {
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.parents = parents;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ResourceLocation getIcon() {
        return icon;
    }

    @Override
    public int color() {
        return color;
    }

    public IMako[] getParents() {
        return parents;
    }
}
