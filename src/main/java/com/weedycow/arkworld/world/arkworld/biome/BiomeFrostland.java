package com.weedycow.arkworld.world.arkworld.biome;

import com.google.common.collect.Lists;
import com.weedycow.arkworld.entity.animal.TibetanAntelope;
import com.weedycow.arkworld.entity.enemy.union.boss.Frostnova;
import com.weedycow.arkworld.entity.enemy.union.boss.Patriot;
import com.weedycow.arkworld.entity.enemy.union.elite.YetiIcecleaver;
import com.weedycow.arkworld.entity.enemy.union.normal.SpiderGlacialOriginiumSlug;
import com.weedycow.arkworld.entity.enemy.union.normal.YetiOperative;
import com.weedycow.arkworld.entity.enemy.union.normal.YetiSniper;
import com.weedycow.arkworld.entity.enemy.ursus.normal.InfectedPatrolCaptain;
import com.weedycow.arkworld.entity.enemy.ursus.normal.UrsusArmoredCaster;
import com.weedycow.arkworld.registry.BlockRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class BiomeFrostland extends Biome
{
    List<Biome.SpawnListEntry> spawnableBossList = Lists.newArrayList();

    public BiomeFrostland()
    {
        super(new Biome.BiomeProperties("frost").setRainfall(0.2F).setBaseHeight(0F).setHeightVariation(0F).setTemperature(0F));
        topBlock = BlockRegistry.GRASS_FROZEN.getDefaultState();
        fillerBlock = BlockRegistry.FROZEN_DIRT.getDefaultState();
        this.decorator = this.createBiomeDecorator();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(TibetanAntelope.class, 6, 1, 2));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityRabbit.class, 6, 1, 2));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityPolarBear.class, 6, 1, 2));
        this.spawnableBossList.add(new Biome.SpawnListEntry(Frostnova.class, 1, 1, 1));
        this.spawnableBossList.add(new Biome.SpawnListEntry(Patriot.class, 1, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(YetiSniper.class, 3, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(YetiOperative.class, 3, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(YetiIcecleaver.class, 1, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(SpiderGlacialOriginiumSlug.class, 2, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(UrsusArmoredCaster.class, 3, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(InfectedPatrolCaptain.class, 3, 1, 1));
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
        decorator.flowerFrostGenerate=true;
        decorator.flowerFrostPerChunk=1;
        decorator.flowerFrostGenChance=3;
        decorator.mossGenerate=true;
        decorator.mossPerChunk=1;
        return getModdedBiomeDecorator(decorator);
    }

    @Nonnull
    @Override
    public Biome.TempCategory getTempCategory()
    {
        return TempCategory.COLD;
    }

}
