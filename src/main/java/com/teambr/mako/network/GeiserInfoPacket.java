package com.teambr.mako.network;

import com.teambr.mako.api.mako.IMako;
import com.teambr.mako.api.mako.MakoRegistry;
import com.teambr.mako.proxy.client.render.GeiserRender;
import com.teambr.mako.world.GeiserData;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GeiserInfoPacket implements IMessage, IMessageHandler<GeiserInfoPacket, IMessage> {

    public ChunkPos pos;
    public IMako mako;
    public int amount;
    public long last;

    public GeiserInfoPacket(ChunkPos pos, IMako mako, int amount, long last) {
        this.pos = pos;
        this.mako = mako;
        this.amount = amount;
        this.last = last;
    }

    public GeiserInfoPacket() {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = new ChunkPos(buf.readInt(), buf.readInt());
        String name = new PacketBuffer(buf).readString(50);
        this.mako = MakoRegistry.getInstance().getMako(name);
        this.amount = buf.readInt();
        this.last = buf.readLong();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.x).writeInt(pos.z);
        buf.writeBytes(new PacketBuffer(buf).writeString(mako.getName()));
        buf.writeInt(amount);
        buf.writeLong(last);

    }

    @Override
    public IMessage onMessage(GeiserInfoPacket message, MessageContext ctx) {
        if (ctx.side.isClient()) {
            GeiserRender.pos = message.pos;
            GeiserRender.data = new GeiserData(message.mako, message.amount, message.last);
        }
        return null;
    }

    @Override
    public String toString() {
        return "GeiserInfoPacket{" +
                "pos=" + pos +
                ", mako=" + mako +
                ", amount=" + amount +
                ", last=" + last +
                '}';
    }
}
