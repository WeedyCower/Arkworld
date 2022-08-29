package com.weedycow.arkworld.world.arkworld.world;

import com.weedycow.arkworld.registry.WorldGenRegistry;
import com.weedycow.arkworld.world.arkworld.biome.ArkBiomeProvider;
import com.weedycow.arkworld.world.arkworld.biome.ArkBiomesGenLayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class ArkWorldType extends WorldType
{
    public ArkWorldType()
    {
        super(WorldGenRegistry.ARKWWORLD_NAME);
    }

    @Nonnull
    @Override
    public BiomeProvider getBiomeProvider(@Nonnull World world)
    {
        return new ArkBiomeProvider();
    }

    @Nonnull
    @Override
    public GenLayer getBiomeLayer(long worldSeed, @Nonnull GenLayer parentLayer, @Nonnull ChunkGeneratorSettings chunkSettings)
    {
        return new ArkBiomesGenLayer(worldSeed);
    }

    @Nonnull
    @Override
    public IChunkGenerator getChunkGenerator(@Nonnull World world, @Nonnull String generatorOptions)
    {
        return new ArkChunkGenerator(world, world.getSeed() - WorldGenRegistry.ARKWORLD_DIM_ID, world.getWorldInfo().getGeneratorOptions());
    }

    @Override
    public int getMinimumSpawnHeight(World world)
    {
        return world.getSeaLevel() + 1;
    }

    @Override
    public double getHorizon(@Nonnull World world)
    {
        return 63.0D;
    }

    @Override
    public double voidFadeMagnitude()
    {
        return 0.03125D;
    }

    @Override
    public boolean handleSlimeSpawnReduction(@Nonnull Random random, @Nonnull World world)
    {
        return false;
    }

    @Override
    public int getSpawnFuzz(@Nonnull WorldServer world, net.minecraft.server.MinecraftServer server)
    {
        return Math.max(0, server.getSpawnRadius(world));
    }

    @Override
    public boolean isCustomizable()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasInfoNotice()
    {
        return true;
    }

    @Override
    public float getCloudHeight()
    {
        return 128.0F;
    }
}
