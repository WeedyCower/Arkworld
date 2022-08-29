package com.weedycow.arkworld.world.arkworld.biome;

import com.google.common.collect.Lists;
import com.weedycow.arkworld.entity.enemy.cthulhu.elite.BasinSeaReaper;
import com.weedycow.arkworld.entity.enemy.cthulhu.normal.DeepSeaSlider;
import com.weedycow.arkworld.entity.enemy.cthulhu.normal.PocketSeaCrawler;
import com.weedycow.arkworld.entity.enemy.cthulhu.normal.ShellSeaRunner;
import com.weedycow.arkworld.registry.BlockRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

import javax.annotation.Nonnull;
import java.util.List;

public class BiomeIberia extends Biome
{
    public BiomeIberia()
    {
        super(new Biome.BiomeProperties("iberia").setRainfall(0.1F).setBaseHeight(-0.25F).setHeightVariation(0F).setTemperature(0.2F));
        topBlock = BlockRegistry.IBERIA_DIRT.getDefaultState();
        fillerBlock = BlockRegistry.IBERIA_DIRT.getDefaultState();
        this.decorator = this.createBiomeDecorator();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(DeepSeaSlider.class, 2, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(BasinSeaReaper.class, 2, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(PocketSeaCrawler.class, 2, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(ShellSeaRunner.class, 2, 1, 1));
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
        decorator.seaGrassGenerate = true;
        decorator.seaGrassPerChunk = 10;
        decorator.iberiaFlowerGenerate = true;
        decorator.iberiaFlowerPerChunk = 2;
        decorator.iberiaBigFlowerGenerate = true;
        decorator.iberiaBigFlowerPerChunk = 2;
        decorator.dripstoneGenerate = true;
        decorator.dripstonePerChunk = 2;
        decorator.fungusBlanketGenerate = true;
        decorator.fungusBlanketPerChunk = 4;
        decorator.iberiaWaterlilyGenerate = true;
        decorator.iberiaWaterlilyPerChunk = 8;
        return getModdedBiomeDecorator(decorator);
    }

    @Nonnull
    @Override
    public Biome.TempCategory getTempCategory()
    {
        return TempCategory.COLD;
    }
}
