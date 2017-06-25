package com.teambr.mako.api.block;

import net.minecraft.util.IStringSerializable;

public enum Render implements IStringSerializable {
    OFF, NORTH, SOUTH, EAST, WEST;

    @Override
    public String getName() {
        return this.toString().toLowerCase();
    }
}
