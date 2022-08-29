package com.weedycow.arkworld.world.arkworld.biome;

import com.google.common.collect.Lists;
import com.weedycow.arkworld.entity.animal.Owl;
import com.weedycow.arkworld.entity.enemy.union.elite.HeavyDefender;
import com.weedycow.arkworld.entity.enemy.union.elite.SeniorCaster;
import com.weedycow.arkworld.entity.enemy.union.normal.Avenger;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

import javax.annotation.Nonnull;
import java.util.List;

public class BiomeHill extends Biome
{
    public BiomeHill()
    {
        super(new Biome.BiomeProperties("hill").setRainfall(0.3F).setBaseHeight(1F).setHeightVariation(0.5F).setTemperature(0.3F));
        topBlock = Blocks.GRASS.getDefaultState();
        fillerBlock = Blocks.DIRT.getDefaultState();
        this.decorator = this.createBiomeDecorator();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityParrot.class, 8, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Owl.class, 8, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(HeavyDefender.class, 1, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(SeniorCaster.class, 1, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Avenger.class, 2, 1, 1));
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
        decorator.treeGenerate=true;
        decorator.treePerChunk=2;
        decorator.grassGenerate=true;
        decorator.grassPerChunk=2;
        decorator.treeMetas=new int[]{1};
        decorator.teaGenerate=true;
        decorator.teaPerChunk=1;
        return getModdedBiomeDecorator(decorator);
    }

    @Nonnull
    @Override
    public Biome.TempCategory getTempCategory()
    {
        return TempCategory.COLD;
    }
}
