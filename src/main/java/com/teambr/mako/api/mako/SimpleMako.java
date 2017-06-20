package com.teambr.mako.api.mako;

import net.minecraft.util.ResourceLocation;

public class SimpleMako implements IMako {

    private String name;
    private ResourceLocation icon;
    private int color;

    public SimpleMako(String name, ResourceLocation icon, int color) {
        this.name = name;
        this.icon = icon;
        this.color = color;
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
}
