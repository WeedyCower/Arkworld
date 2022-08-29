package com.weedycow.arkworld.world.arkworld.biome;

import com.google.common.collect.Lists;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.other.boss.Queen;
import com.weedycow.arkworld.entity.enemy.other.elite.Hydralisk;
import com.weedycow.arkworld.entity.enemy.other.elite.SmilingCorpseMountain;
import com.weedycow.arkworld.entity.enemy.other.normal.Zergling;
import com.weedycow.arkworld.entity.enemy.split.boss.Evolution;
import com.weedycow.arkworld.entity.enemy.split.normal.MutantRockSpider;
import com.weedycow.arkworld.entity.enemy.union.normal.SpiderOriginiumSlug;
import com.weedycow.arkworld.registry.BlockRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class BiomeWasteland extends Biome
{
    List<Biome.SpawnListEntry> spawnableBossList = Lists.newArrayList();
    public BiomeWasteland()
    {
        super(new Biome.BiomeProperties("wasteland").setRainfall(0F).setBaseHeight(0F).setHeightVariation(0F).setTemperature(2F).setWaterColor(16746240));
        this.topBlock = BlockRegistry.GRASS_WITHERED.getDefaultState();
        this.fillerBlock = BlockRegistry.DERELICT_DIRT.getStateFromMeta(0);
        this.decorator = this.createBiomeDecorator();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(SpiderOriginiumSlug.class, 2, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Zergling.class, 2, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(SmilingCorpseMountain.class, 1, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(Hydralisk.class, 1, 1, 1));
        this.spawnableBossList.add(new Biome.SpawnListEntry(Queen.class, 1, 1, 1));
        this.spawnableBossList.add(new Biome.SpawnListEntry(Evolution.class, 1, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(MutantRockSpider.class, 2, 1, 1));
    }

    public List<Biome.SpawnListEntry> getSpawnableList(EnumCreatureType creatureType)
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
                if (!this.modSpawnableLists.containsKey(creatureType)) this.modSpawnableLists.put(creatureType, Lists.<Biome.SpawnListEntry>newArrayList());
                return this.modSpawnableLists.get(creatureType);
        }
    }

    @Nonnull
    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        ArkBiomeDecorator decorator = new ArkBiomeDecorator();
        decorator.witheredGrassGenerate =true;
        decorator.witheredGrassPerChunk=32;
        decorator.willowTreesGenerate =true;
        decorator.willowTreesPerChunk=1;
        decorator.willowTreesGenChance=30;
        if(!Arkworld.NEWDES)
            decorator.originiumAmountAdd=1;
        else
            decorator.originiumAmountAdd=0;
        decorator.originiumCrystalGenerate=true;
        decorator.originiumCrystalPerChunk=1;
        decorator.originiumCrystalHugeGenerate=true;
        decorator.originiumCrystalHugeGenChance=32;
        decorator.originiumCrystalHugePerChunk=1;
        return getModdedBiomeDecorator(decorator);
    }

    @Nonnull
    @Override
    public Biome.TempCategory getTempCategory()
    {
        return TempCategory.WARM;
    }
}
