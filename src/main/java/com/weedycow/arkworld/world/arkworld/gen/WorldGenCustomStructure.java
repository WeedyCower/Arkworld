package com.weedycow.arkworld.world.arkworld.gen;

import com.weedycow.arkworld.registry.BlockRegistry;
import com.weedycow.arkworld.registry.WorldGenRegistry;
import com.weedycow.arkworld.world.arkworld.biome.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WorldGenCustomStructure implements IWorldGenerator
{
    public int wastelandPointer=0;
    public int frostlandPointer=0;
    public int chernobergPointer=0;
    public static final WorldGenStructure DESERTED_SHACK = new WorldGenStructure("deserted_shack",BlockRegistry.GRASS_WITHERED);
    public static final WorldGenStructure SENTRY_TOWER = new WorldGenStructure("sentry_tower",BlockRegistry.GRASS_WITHERED);
    public static final WorldGenStructure IGLOO = new WorldGenStructure("igloo",BlockRegistry.GRASS_FROZEN);
    public static final WorldGenStructure CAMP = new WorldGenStructure("camp",BlockRegistry.GRASS_FROZEN);
    public static final WorldGenStructure CAMP_BIG = new WorldGenStructure("camp_big",Blocks.GRASS);
    public static final WorldGenStructure ORIGINIUM_CRYSTAL_MIDDLE = new WorldGenStructure("originium_crystal_middle",BlockRegistry.GRASS_WITHERED);
    public static final WorldGenStructure IBERIA_TOWER_I = new WorldGenStructure("iberia_tower_i",BlockRegistry.IBERIA_DIRT);
    public static final WorldGenStructure IBERIA_TOWER_II = new WorldGenStructure("iberia_tower_ii",BlockRegistry.IBERIA_DIRT);
    public static final WorldGenStructure IBERIA_TOWER_III = new WorldGenStructure("iberia_tower_iii",BlockRegistry.IBERIA_DIRT);
    public static final WorldGenStructure HOUSE1 = new WorldGenStructure("house1",BlockRegistry.CITY_STONE);
    public static final WorldGenStructure HOUSE2 = new WorldGenStructure("house2",BlockRegistry.CITY_STONE);
    public static final WorldGenStructure HOUSE3 = new WorldGenStructure("house3",BlockRegistry.CITY_STONE);
    public static final WorldGenStructure HOUSE4 = new WorldGenStructure("house4",BlockRegistry.CITY_STONE);
    public static final WorldGenStructure HOUSE5 = new WorldGenStructure("house5",BlockRegistry.CITY_STONE);
    public static final WorldGenStructure HOUSE6 = new WorldGenStructure("house6",BlockRegistry.CITY_STONE);
    public static final WorldGenStructure HOUSE7 = new WorldGenStructure("house7",BlockRegistry.CITY_STONE);
    public static final WorldGenStructure HOUSE8 = new WorldGenStructure("house8",BlockRegistry.CITY_STONE);
    public static final WorldGenStructure HOUSE9 = new WorldGenStructure("house9",BlockRegistry.CITY_STONE);
    public static final WorldGenStructure HOUSE10 = new WorldGenStructure("house10",BlockRegistry.CITY_STONE);
    public static final WorldGenStructure HOUSE11 = new WorldGenStructure("house11",BlockRegistry.CITY_STONE);
    public static final WorldGenStructure HOUSE12 = new WorldGenStructure("house12",BlockRegistry.CITY_STONE);

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkePrvider)
    {
        if(world.provider.getDimension()==WorldGenRegistry.ARKWORLD_DIM_ID)
        {
            generateStructure(IBERIA_TOWER_I, world, random, chunkX, chunkZ, 30, BlockRegistry.IBERIA_DIRT, BiomeIberia.class);

            generateStructure(CAMP_BIG, world, random, chunkX, chunkZ, 300, Blocks.GRASS, BiomePlant.class);

            if(wastelandPointer==0)
                generateStructure(DESERTED_SHACK, world, random, chunkX, chunkZ, 300, BlockRegistry.GRASS_WITHERED, BiomeWasteland.class);
            if(wastelandPointer==1)
                generateStructure(SENTRY_TOWER, world, random, chunkX, chunkZ, 300, BlockRegistry.GRASS_WITHERED, BiomeWasteland.class);
            if(wastelandPointer==2)
                generateStructure(ORIGINIUM_CRYSTAL_MIDDLE, world, random, chunkX, chunkZ, 300, BlockRegistry.GRASS_WITHERED, BiomeWasteland.class);

            if(frostlandPointer==0)
                generateStructure(IGLOO, world, random, chunkX, chunkZ, 300, BlockRegistry.GRASS_FROZEN, BiomeFrostland.class);
            if(frostlandPointer==1)
                generateStructure(CAMP, world, random, chunkX, chunkZ, 300, BlockRegistry.GRASS_FROZEN, BiomeFrostland.class);

            if(chernobergPointer==0)
                generateStructure(HOUSE1, world, random, chunkX, chunkZ, 1, BlockRegistry.CITY_STONE, BiomeChernoberg.class);
            if(chernobergPointer==1)
                generateStructure(HOUSE2, world, random, chunkX, chunkZ, 1, BlockRegistry.CITY_STONE, BiomeChernoberg.class);
            if(chernobergPointer==2)
                generateStructure(HOUSE3, world, random, chunkX, chunkZ, 1, BlockRegistry.CITY_STONE, BiomeChernoberg.class);
            if(chernobergPointer==3)
                generateStructure(HOUSE4, world, random, chunkX, chunkZ, 1, BlockRegistry.CITY_STONE, BiomeChernoberg.class);
            if(chernobergPointer==4)
                generateStructure(HOUSE5, world, random, chunkX, chunkZ, 1, BlockRegistry.CITY_STONE, BiomeChernoberg.class);
            if(chernobergPointer==5)
                generateStructure(HOUSE6, world, random, chunkX, chunkZ, 1, BlockRegistry.CITY_STONE, BiomeChernoberg.class);
            if(chernobergPointer==6)
                generateStructure(HOUSE7, world, random, chunkX, chunkZ, 6, BlockRegistry.CITY_STONE, BiomeChernoberg.class);//w
            if(chernobergPointer==7)
                generateStructure(HOUSE8, world, random, chunkX, chunkZ, 1, BlockRegistry.CITY_STONE, BiomeChernoberg.class);
            if(chernobergPointer==8)
                generateStructure(HOUSE9, world, random, chunkX, chunkZ, 3, BlockRegistry.CITY_STONE, BiomeChernoberg.class);//regicide
            if(chernobergPointer==9)
                generateStructure(HOUSE10, world, random, chunkX, chunkZ, 1, BlockRegistry.CITY_STONE, BiomeChernoberg.class);
            if(chernobergPointer==10)
                generateStructure(HOUSE11, world, random, chunkX, chunkZ, 1, BlockRegistry.CITY_STONE, BiomeChernoberg.class);
            if(chernobergPointer==11)
                generateStructure(HOUSE12, world, random, chunkX, chunkZ, 9, BlockRegistry.CITY_STONE, BiomeChernoberg.class);//black snake
        }
    }

    private void generateStructure(WorldGenerator generator , World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class... classes)
    {
        ArrayList<Class> classesList = new ArrayList<>(Arrays.asList(classes));
        int x = (chunkX * 16) + random.nextInt(15);
        int z = (chunkZ * 16) + random.nextInt(15);
        int y = calculateGenerationHeight(world, x, z, topBlock);
        BlockPos pos = new BlockPos(x, y, z);

        Class<? extends Biome> biome = world.provider.getBiomeForCoords(pos).getClass();

        if (world.getWorldType() != WorldType.FLAT && classesList.contains(biome) && random.nextInt(chance) == 0)
        {
            if (generator.generate(world, random, pos) && generator == IBERIA_TOWER_I)
            {
                IBERIA_TOWER_II.generate(world, random, pos.add(0,30,0));
                IBERIA_TOWER_III.generate(world, random, pos.add(0,62,0));
            }
        }

        if (generator == DESERTED_SHACK) wastelandPointer = 1;
        if (generator == SENTRY_TOWER) wastelandPointer = 2;
        if (generator == ORIGINIUM_CRYSTAL_MIDDLE) wastelandPointer = 0;

        if (generator == IGLOO) frostlandPointer = 1;
        if (generator == CAMP) frostlandPointer = 0;

        if (generator == HOUSE1) chernobergPointer = 1;
        if (generator == HOUSE2) chernobergPointer = 2;
        if (generator == HOUSE3) chernobergPointer = 3;
        if (generator == HOUSE4) chernobergPointer = 4;
        if (generator == HOUSE5) chernobergPointer = 5;
        if (generator == HOUSE6) chernobergPointer = 6;
        if (generator == HOUSE7) chernobergPointer = 7;
        if (generator == HOUSE8) chernobergPointer = 8;
        if (generator == HOUSE9) chernobergPointer = 9;
        if (generator == HOUSE10) chernobergPointer = 10;
        if (generator == HOUSE11) chernobergPointer = 11;
        if (generator == HOUSE12) chernobergPointer = 0;
    }

    public static boolean checkAround(World world, BlockPos pos, BlockPos size, Block topBlock)
    {
        List<Block> top = new ArrayList<>();
        List<Block> air = new ArrayList<>();

        for(int x=pos.getX()-1; x<=pos.getX()+size.getX()+1; x++)
        {
            for(int z=pos.getZ()-1; z<=pos.getZ()+size.getZ()+1; z++)
            {
                for(int y=pos.getY()+1; y<=pos.getY()+size.getY(); y++)
                {
                    top.add(world.getBlockState(new BlockPos(x, pos.getY(), z)).getBlock());
                    air.add(world.getBlockState(new BlockPos(x, y, z).up()).getBlock());
                }
            }
        }

        top = top.stream().distinct().collect(Collectors.toList());

        air = air.stream().distinct().collect(Collectors.toList());

        return top.size() == 1 && air.size() == 1 && top.contains(topBlock) && air.contains(Blocks.AIR);
    }

    public static int calculateGenerationHeight(World world, int x, int z, Block topBlock)
    {
        int y = world.getHeight();
        boolean foundGround = false;

        while (!foundGround && y-- >= 0)
        {
            Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
            foundGround = block == topBlock;
        }
        return y;
    }

}
