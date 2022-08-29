package com.weedycow.arkworld.world.arkworld.biome;

import com.weedycow.arkworld.registry.BiomesRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class ArkCityGenLayer extends GenLayer
{
    private final Biome[] cityBiomes = {BiomesRegistry.CHERNOBERG};

    private static final int CITY_BIOME_CHANCE = 25;

    public ArkCityGenLayer(long seed, GenLayer parent)
    {
        super(seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int x, int z, int width, int depth)
    {
        int i = x - 1;
        int j = z - 1;
        int k = width + 2;
        int l = depth + 2;
        int[] aint = this.parent.getInts(i, j, k, l);
        int dest[] = IntCache.getIntCache(width * depth);

        for (int dz = 0; dz < depth; dz++)
        {
            for (int dx = 0; dx < width; dx++)
            {
                int k1 = aint[dx + 0 + (dz + 0) * k];
                int l1 = aint[dx + 2 + (dz + 0) * k];
                int i2 = aint[dx + 0 + (dz + 2) * k];
                int j2 = aint[dx + 2 + (dz + 2) * k];
                int k2 = aint[dx + 1 + (dz + 1) * k];
                this.initChunkSeed(dx + x, dz + z);

                if (k2 != 0 && k1 != 0 && l1 != 0 && i2 != 0 && j2 != 0 && nextInt(CITY_BIOME_CHANCE)==0)
                {
                    dest[(dx + dz * width)] = Biome.getIdForBiome(this.cityBiomes[nextInt(this.cityBiomes.length)]);
                }
                else
                {
                    dest[(dx + dz * width)] = k2;
                }
            }
        }
        return dest;
    }
}
