package com.teambr.mako.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class GeiserWorldGenerattor implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int chancesToSpawn = 1;
        if (world.provider.getDimension() == 0) {
            for (int i = 0; i < chancesToSpawn && random.nextBoolean() && random.nextBoolean(); i++) {
                int x = chunkX * 16 + random.nextInt(16);
                int z = chunkZ * 16 + random.nextInt(16);
                BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
                System.out.println("Generating:" + pos);
                world.getChunkFromChunkCoords(chunkX, chunkZ);
                world.setBlockState(pos, Blocks.STONE.getDefaultState());
            }
        }
    }

}
