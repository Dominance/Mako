package com.teambr.mako.network;

import com.teambr.mako.utils.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketManager {

    private static PacketManager ourInstance = new PacketManager();
    private SimpleNetworkWrapper networkWrapper;
    private int id;

    private PacketManager() {
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID.toUpperCase());
        id = 0;
        register(GeiserInfoPacket.class, GeiserInfoPacket.class);
    }

    public static PacketManager getInstance() {
        return ourInstance;
    }

    private void register(Class packet, Class message) {
        networkWrapper.registerMessage(packet, message, id, Side.CLIENT);
        networkWrapper.registerMessage(packet, message, id, Side.SERVER);
        ++id;
    }

    public SimpleNetworkWrapper getNetworkWrapper() {
        return networkWrapper;
    }
}
