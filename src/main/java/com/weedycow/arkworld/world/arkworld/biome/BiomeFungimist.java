package com.weedycow.arkworld.world.arkworld.biome;

import com.google.common.collect.Lists;
import com.weedycow.arkworld.entity.animal.Elk;
import com.weedycow.arkworld.entity.animal.Owl;
import com.weedycow.arkworld.entity.animal.Skunk;
import com.weedycow.arkworld.entity.animal.Squirrel;
import com.weedycow.arkworld.entity.enemy.union.normal.OriginiumSlug;
import com.weedycow.arkworld.entity.enemy.union.normal.Wraith;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

import javax.annotation.Nonnull;
import java.util.List;

public class BiomeFungimist extends Biome
{
    public BiomeFungimist()
    {
        super(new Biome.BiomeProperties("fungimist").setRainfall(0.1F).setBaseHeight(0F).setHeightVariation(0F).setTemperature(0.1F));
        topBlock = Blocks.GRASS.getDefaultState();
        fillerBlock = Blocks.DIRT.getDefaultState();
        this.decorator = this.createBiomeDecorator();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Elk.class, 9, 1, 3));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Skunk.class, 9, 1, 3));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityWolf.class, 9, 1, 3));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Squirrel.class, 9, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Owl.class, 9, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Wraith.class, 6, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(OriginiumSlug.class, 6, 1, 3));
    }

    @Nonnull
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
        decorator.grassGenerate =true;
        decorator.grassPerChunk=3;
        decorator.hugeTreeGenerate =true;
        decorator.hugeTreePerChunk=1;
        decorator.niceMushroomGenerate=true;
        decorator.niceMushroomPerChunk=1;
        decorator.niceMushroomGenChance=1;
        decorator.mushroomGenerate=true;
        decorator.mushroomPerChunk=2;
        decorator.flowerGenerate=true;
        decorator.flowerPerChunk=2;
        decorator.shrubGenerate=true;
        decorator.shrubPerChunk=1;
        decorator.shrubGenChance=32;
        decorator.pumpkinGenerate=true;
        decorator.pumpkinGenChance=32;
        decorator.treeGenerate=true;
        decorator.treePerChunk=1;
        decorator.treeMetas=new int[] {0,2};
        decorator.mossGenerate=true;
        decorator.mossPerChunk=2;
        decorator.bigMushroomGenerate=true;
        decorator.bigMushroomPerChunk=1;
        decorator.bigMushroomGenChance=6;
        return getModdedBiomeDecorator(decorator);
    }

    @Nonnull
    @Override
    public Biome.TempCategory getTempCategory()
    {
        return TempCategory.WARM;
    }

}
