package com.teambr.mako.world;

import com.teambr.mako.api.mako.MakoRegistry;
import com.teambr.mako.network.GeiserInfoPacket;
import com.teambr.mako.network.PacketManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Random;

public class GeiserChunkManager {

    private static GeiserChunkManager ourInstance = new GeiserChunkManager();
    private HashMap<ChunkPos, GeiserData> chunkGeiserData;
    private Random random;
    private GeiserChunkManager() {
        chunkGeiserData = new HashMap<>();
        random = new Random(); //TODO Use a better random
    }

    public static GeiserChunkManager getInstance() {
        return ourInstance;
    }

    @SubscribeEvent
    public void chunkLoad(ChunkDataEvent.Load event) {
        if (!event.getWorld().isRemote) {
            if (event.getData().hasKey("Geiser")) {
                NBTTagCompound compound = event.getData().getCompoundTag("Geiser");
                if (!compound.hasKey("Empty")) {
                    chunkGeiserData.put(event.getChunk().getPos(), GeiserData.readFromNBT(compound));
                    //System.out.println("Loaded Geiser data from " + event.getChunk().getPos() + " -> " + chunkGeiserData.get(event.getChunk().getPos()).toString());
                } else {
                    //chunkGeiserData.put(event.getChunk().getPos(), null);
                }
            }
        }
    }

    @SubscribeEvent
    public void chunkSave(ChunkDataEvent.Save event) {
        if (!event.getWorld().isRemote) {
            if (chunkGeiserData.containsKey(event.getChunk().getPos())) {
                if (chunkGeiserData.get(event.getChunk().getPos()) == null) {
                    NBTTagCompound compound = new NBTTagCompound();
                    compound.setString("Empty", "");
                    event.getData().setTag("Geiser", compound);
                    return;
                }
                event.getData().setTag("Geiser", chunkGeiserData.get(event.getChunk().getPos()).writeFromNBT());
                //System.out.println("Saving Geiser data from " + event.getChunk().getPos() + " -> " + chunkGeiserData.get(event.getChunk().getPos()).toString());
            } else {
                if (event.getData().hasKey("Geiser")) return;
                float f = random.nextFloat();
                if (f < 0.05D) {
                    GeiserData g = new GeiserData(MakoRegistry.getInstance().getRandomPureMako(), 0, 0);
                    chunkGeiserData.put(event.getChunk().getPos(), g);
                    event.getData().setTag("Geiser", g.writeFromNBT());
                    //System.out.println("Generated Geiser data from " + event.getChunk().getPos() + " -> " + chunkGeiserData.get(event.getChunk().getPos()).toString());
                    for (int x = 0; x < 16; ++x) {
                        for (int z = 0; z < 16; ++z) {
                            event.getChunk().getWorld().setBlockState(new BlockPos(event.getChunk().x * 16 + x, 3, event.getChunk().z * 16 + z), Blocks.STONE.getDefaultState());
                        }
                    }
                } else {
                    //System.out.println("Generated Geiser data from " + chunk.getPos() + " -> " + null);
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setString("Empty", "");
                    event.getData().setTag("Geiser", tagCompound);
                    chunkGeiserData.put(event.getChunk().getPos(), null);
                }
            }
        }
    }

    @SubscribeEvent
    public void chunkUnload(ChunkEvent.Unload event) {
        if (!event.getWorld().isRemote && chunkGeiserData.containsKey(event.getChunk().getPos())) {
            //System.out.println("Removing Geiser data from " + event.getChunk().getPos() + " -> " + (chunkGeiserData.get(event.getChunk().getPos()) == null ? "null" : chunkGeiserData.get(event.getChunk().getPos()).toString()));
            chunkGeiserData.remove(event.getChunk().getPos());
        }
    }

    @SubscribeEvent
    public void onChunkChange(EntityEvent.EnteringChunk event) {
        World world = event.getEntity().world;
        if (!world.isRemote && event.getEntity() instanceof EntityPlayerMP) {
            ChunkPos chunkPos = new ChunkPos(event.getNewChunkX(), event.getNewChunkZ());
            if (chunkGeiserData.containsKey(chunkPos) && chunkGeiserData.get(chunkPos) != null) {
                GeiserData data = chunkGeiserData.get(chunkPos);
                GeiserInfoPacket packet = new GeiserInfoPacket(chunkPos, data.getMako(), data.getCurrentAmount(), data.getLastOutput());
                PacketManager.getInstance().getNetworkWrapper().sendTo(packet, (EntityPlayerMP) event.getEntity());
            }
        }
    }
}
