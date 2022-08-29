package com.weedycow.arkworld.world.arkworld.biome;

import com.google.common.collect.Lists;
import com.weedycow.arkworld.entity.enemy.union.boss.Regicide;
import com.weedycow.arkworld.entity.enemy.union.boss.SarkazCenturion;
import com.weedycow.arkworld.entity.enemy.union.normal.*;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class BiomeWilderness extends Biome
{
    List<Biome.SpawnListEntry> spawnableBossList = Lists.newArrayList();

    public BiomeWilderness()
    {
        super(new Biome.BiomeProperties("wilderness").setRainfall(0.1F).setBaseHeight(0F).setHeightVariation(0F).setTemperature(1F));
        topBlock = Blocks.SAND.getDefaultState();
        fillerBlock = Blocks.SANDSTONE.getDefaultState();
        this.decorator = this.createBiomeDecorator();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityHusk.class, 10, 1, 4));
        this.spawnableBossList.add(new Biome.SpawnListEntry(SarkazCenturion.class, 1, 1, 1));
        this.spawnableBossList.add(new Biome.SpawnListEntry(Regicide.class, 1, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(MortarGunner.class, 2, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Soldier.class, 2, 1, 2));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(ShieldSoldier.class, 2, 1, 2));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Crossbowman.class, 2, 1, 2));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Caster.class, 2, 1, 1));
    }

    @Override
    public List<SpawnListEntry> getSpawnableList(EnumCreatureType creatureType)
    {
        switch (creatureType)
        {
            case MONSTER:
                return this.spawnableMonsterList;
            case CREATURE:
                if(new Random().nextInt(30)==0)
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
        decorator.deadBushGenerate=true;
        decorator.deadBushPerChunk=1;
        decorator.cactusGenerate=true;
        decorator.cactusPerChunk=2;
        return getModdedBiomeDecorator(decorator);
    }

    @Nonnull
    @Override
    public Biome.TempCategory getTempCategory()
    {
        return TempCategory.MEDIUM;
    }
}
