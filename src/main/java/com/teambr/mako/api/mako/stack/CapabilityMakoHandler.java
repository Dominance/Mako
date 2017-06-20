package com.teambr.mako.api.mako.stack;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapabilityMakoHandler {

    @CapabilityInject(IMakoHandler.class)
    public static Capability<IMakoHandler> MAKO_HANDLER_CAPABILITY = null;
}
