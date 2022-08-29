package com.weedycow.arkworld.world.arkworld.biome;

import com.google.common.collect.Lists;
import com.weedycow.arkworld.registry.BlockRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

import javax.annotation.Nonnull;
import java.util.List;

public class BiomeChernoberg extends Biome
{
    public BiomeChernoberg()
    {
        super(new Biome.BiomeProperties("chernoberg").setRainfall(0.1F).setBaseHeight(0F).setHeightVariation(0F).setTemperature(1F));
        topBlock = BlockRegistry.CITY_STONE.getDefaultState();
        fillerBlock = BlockRegistry.TALA_STONE.getDefaultState();
        this.decorator = this.createBiomeDecorator();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
    }

    @Override
    public List<SpawnListEntry> getSpawnableList(EnumCreatureType creatureType)
    {
        switch (creatureType)
        {
            case MONSTER:
                return this.spawnableMonsterList;
            case CREATURE:
                return this.spawnableCreatureList;
            case WATER_CREATURE:
                return this.spawnableWaterCreatureList;
            case AMBIENT:
                return this.spawnableCaveCreatureList;
            default:
                if (!this.modSpawnableLists.containsKey(creatureType)) this.modSpawnableLists.put(creatureType, Lists.<Biome.SpawnListEntry>newArrayList());return this.modSpawnableLists.get(creatureType);
        }
    }

    @Nonnull
    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        ArkBiomeDecorator decorator = new ArkBiomeDecorator();
        return getModdedBiomeDecorator(decorator);
    }

    @Nonnull
    @Override
    public Biome.TempCategory getTempCategory()
    {
        return TempCategory.MEDIUM;
    }
}
