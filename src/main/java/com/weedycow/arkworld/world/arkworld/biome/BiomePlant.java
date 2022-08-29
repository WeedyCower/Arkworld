package com.weedycow.arkworld.world.arkworld.biome;

import com.google.common.collect.Lists;
import com.weedycow.arkworld.entity.animal.Cheetah;
import com.weedycow.arkworld.entity.animal.Hyena;
import com.weedycow.arkworld.entity.animal.Lion;
import com.weedycow.arkworld.entity.animal.Rhino;
import com.weedycow.arkworld.entity.enemy.union.boss.Faust;
import com.weedycow.arkworld.entity.enemy.union.boss.Skullshatterer;
import com.weedycow.arkworld.entity.enemy.union.elite.DefenseCrusher;
import com.weedycow.arkworld.entity.enemy.union.elite.SeniorCaster;
import com.weedycow.arkworld.entity.enemy.union.normal.CocktailThrower;
import com.weedycow.arkworld.entity.enemy.union.normal.DualSwordsman;
import com.weedycow.arkworld.entity.enemy.union.normal.Rioter;
import com.weedycow.arkworld.entity.enemy.union.normal.TechnicalScout;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class BiomePlant extends Biome
{
    List<Biome.SpawnListEntry> spawnableBossList = Lists.newArrayList();

    public BiomePlant()
    {
        super(new Biome.BiomeProperties("plant").setRainfall(0.4F).setBaseHeight(0F).setHeightVariation(0F).setTemperature(0.8F));
        topBlock = Blocks.GRASS.getDefaultState();
        fillerBlock = Blocks.DIRT.getDefaultState();
        this.decorator = this.createBiomeDecorator();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Hyena.class, 4, 1, 3));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Lion.class, 4, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Cheetah.class, 4, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Rhino.class, 4, 1, 1));
        this.spawnableBossList.add(new Biome.SpawnListEntry(Faust.class, 1, 1, 1));
        this.spawnableBossList.add(new Biome.SpawnListEntry(Skullshatterer.class, 1, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(DefenseCrusher.class, 1, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(SeniorCaster.class, 1, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(DualSwordsman.class, 2, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(CocktailThrower.class, 2, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Rioter.class, 2, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(TechnicalScout.class, 2, 1, 1));
    }

    @Override
    public List<SpawnListEntry> getSpawnableList(EnumCreatureType creatureType)
    {
        switch (creatureType)
        {
            case MONSTER:
                return this.spawnableMonsterList;
            case CREATURE:
                if(new Random().nextInt(50)==0)
                    return spawnableBossList;
                else
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
        decorator.grassGenerate=true;
        decorator.grassPerChunk=10;
        decorator.flowerGenerate=true;
        decorator.flowerPerChunk=2;
        decorator.pumpkinGenerate=true;
        decorator.pumpkinGenChance=32;
        return getModdedBiomeDecorator(decorator);
    }

    @Nonnull
    @Override
    public Biome.TempCategory getTempCategory()
    {
        return TempCategory.MEDIUM;
    }
}
