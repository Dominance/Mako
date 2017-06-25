package com.teambr.mako.api.block;

import net.minecraftforge.fml.common.registry.IForgeRegistry;

public interface IRegistrable {

    void register(IForgeRegistry registry);

    void registerItem(IForgeRegistry registry);

    void registerRender();

}
