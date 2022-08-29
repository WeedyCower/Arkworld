package com.weedycow.arkworld.world.arkworld.biome;

import com.google.common.collect.Lists;
import com.weedycow.arkworld.entity.animal.*;
import com.weedycow.arkworld.entity.enemy.union.normal.OriginiumSlug;
import com.weedycow.arkworld.entity.enemy.union.normal.SpiderOriginiumSlug;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

import javax.annotation.Nonnull;
import java.util.List;

public class BiomeJungle extends Biome
{
    public BiomeJungle()
    {
        super(new Biome.BiomeProperties("jungle").setRainfall(0.9F).setBaseHeight(0F).setHeightVariation(0F).setTemperature(0.95F));
        topBlock = Blocks.GRASS.getDefaultState();
        fillerBlock = Blocks.DIRT.getDefaultState();
        this.decorator = this.createBiomeDecorator();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Cheetah.class, 9, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Raccoon.class, 9, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Squirrel.class, 9, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Frog.class, 9, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Bullfrog.class, 9, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Skunk.class, 9, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityParrot.class, 9, 1, 2));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(OriginiumSlug.class, 6, 1, 3));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(SpiderOriginiumSlug.class, 3, 1, 1));
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
        decorator.jungleTreeGenerate=true;
        decorator.jungleTreePerChunk=2;
        decorator.shrubGenerate=true;
        decorator.shrubPerChunk=4;
        decorator.treeGenerate=true;
        decorator.treePerChunk=8;
        decorator.treeMetas=new int[]{3};
        decorator.grassGenerate=true;
        decorator.grassPerChunk=4;
        return getModdedBiomeDecorator(decorator);
    }

    @Nonnull
    @Override
    public Biome.TempCategory getTempCategory()
    {
        return TempCategory.COLD;
    }
}
