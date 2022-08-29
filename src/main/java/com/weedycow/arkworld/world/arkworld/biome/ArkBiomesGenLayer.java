package com.weedycow.arkworld.world.arkworld.biome;

import com.weedycow.arkworld.registry.BiomesRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class ArkBiomesGenLayer extends GenLayer
{
    private final Biome[] commonBiomes = {BiomesRegistry.WASTELAND,BiomesRegistry.FROSTLAND, BiomesRegistry.FUNGIMIST,
            BiomesRegistry.IBERIA,BiomesRegistry.PLANT,BiomesRegistry.HILL,BiomesRegistry.WILDERNESS,BiomesRegistry.JUNGLE};

    public ArkBiomesGenLayer(long seed)
    {
        super(seed);
    }

    @Override
    public int[] getInts(int x, int z, int width, int depth)
    {
        int dest[] = IntCache.getIntCache(width * depth);

        for (int dz = 0; dz < depth; dz++)
        {
            for (int dx = 0; dx < width; dx++)
            {
                initChunkSeed(dx + x, dz + z);
                dest[(dx + dz * width)] = Biome.getIdForBiome(this.commonBiomes[nextInt(this.commonBiomes.length)]);
            }
        }
        return dest;
    }
}
