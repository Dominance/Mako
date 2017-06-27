package com.teambr.mako.api.tile;

import net.minecraft.util.ITickable;

public abstract class TileEntityTick extends TileEntityBase implements ITickable, IHasGui {

    @Override
    public void update() {
        if (world.isRemote) return;
    }


}
