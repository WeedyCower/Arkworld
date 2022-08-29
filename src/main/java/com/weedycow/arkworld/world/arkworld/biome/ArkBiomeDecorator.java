package com.weedycow.arkworld.world.arkworld.biome;

import com.weedycow.arkworld.registry.BlockRegistry;
import com.weedycow.arkworld.world.arkworld.gen.WorldGenHugeTree;
import com.weedycow.arkworld.world.arkworld.gen.WorldGenWillowTree;
import com.weedycow.arkworld.world.arkworld.gen.WorldGeneratorNormal;
import com.weedycow.arkworld.world.arkworld.gen.WorldGeneratorWater;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class ArkBiomeDecorator extends BiomeDecorator
{
    public boolean decorating;
    public BlockPos chunkPos;
    public ChunkGeneratorSettings chunkProviderSettings;
    public WorldGenerator dirtGen;
    public WorldGenerator graniteGen;
    public WorldGenerator talaStoneGen;
    public WorldGenerator ironGen;
    public WorldGenerator redstoneGen;
    public WorldGenerator diamondGen;
    public WorldGenerator lapisGen;
    public WorldGenerator crudeGoldGen;
    public WorldGenerator grindstoneGen;
    public WorldGenerator incandescentGen;
    public WorldGenerator mixedGoldGen;
    public WorldGenerator oricoalGen;
    public WorldGenerator originiumGen;
    public WorldGenerator orimanganeseGen;
    public WorldGenerator orirockGen;
    public WorldGenerator orironGen;
    public WorldGenerator weaponGemGen;
    public WorldGenerator rmaGemGen;
    public WorldGenerator gravelOreGen;
    public int originiumAmountAdd;
    public boolean grassGenerate;
    public int grassPerChunk;
    public int grassGenChance;
    public boolean witheredGrassGenerate;
    public int witheredGrassPerChunk;
    public int witheredGrassGenChance;
    public boolean willowTreesGenerate;
    public int willowTreesPerChunk;
    public int willowTreesGenChance;
    public boolean originiumCrystalGenerate;
    public int originiumCrystalPerChunk;
    public int originiumCrystalGenChance;
    public boolean flowerFrostGenerate;
    public int flowerFrostPerChunk;
    public int flowerFrostGenChance;
    public boolean mossGenerate;
    public int mossPerChunk;
    public int mossGenChance;
    public boolean hugeTreeGenerate;
    public int hugeTreePerChunk;
    public int hugeTreeGenChance;
    boolean niceMushroomGenerate;
    public int niceMushroomPerChunk;
    public int niceMushroomGenChance;
    boolean pumpkinGenerate;
    public int pumpkinGenChance;
    boolean lavaFallGenerate;
    public int lavaFallGenChance;
    boolean waterFallGenerate;
    public int waterFallGenChance;
    boolean mushroomGenerate;
    public int mushroomPerChunk;
    public int mushroomGenChance;
    boolean flowerGenerate;
    public int flowerPerChunk;
    public int flowerGenChance;
    boolean shrubGenerate;
    public int shrubPerChunk;
    public int shrubGenChance;
    boolean treeGenerate;
    public int treePerChunk;
    public int treeGenChance;
    public int[] treeMetas;
    boolean bigMushroomGenerate;
    public int bigMushroomPerChunk;
    public int bigMushroomGenChance;
    boolean taigaGenerate;
    public int taigaPerChunk;
    public int taigaGenChance;
    boolean seaGrassGenerate;
    public int seaGrassPerChunk;
    public int seaGrassGenChance;
    boolean iberiaFlowerGenerate;
    public int iberiaFlowerPerChunk;
    public int iberiaFlowerGenChance;
    boolean iberiaBigFlowerGenerate;
    public int iberiaBigFlowerPerChunk;
    public int iberiaBigFlowerGenChance;
    boolean dripstoneGenerate;
    public int dripstonePerChunk;
    public int dripstoneGenChance;
    boolean fungusBlanketGenerate;
    public int fungusBlanketPerChunk;
    public int fungusBlanketGenChance;
    boolean iberiaWaterlilyGenerate;
    public int iberiaWaterlilyPerChunk;
    public int iberiaWaterlilyGenChance;
    boolean jungleTreeGenerate;
    public int jungleTreePerChunk;
    public int jungleTreeGenChance;
    boolean deadBushGenerate;
    public int deadBushPerChunk;
    public int deadBushGenChance;
    boolean cactusGenerate;
    public int cactusPerChunk;
    public int cactusGenChance;
    boolean teaGenerate;
    public int teaPerChunk;
    public int teaGenChance;
    boolean originiumCrystalHugeGenerate;
    public int originiumCrystalHugePerChunk;
    public int originiumCrystalHugeGenChance;

    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    {
        if (this.decorating)
        {
            throw new RuntimeException("Already decorating");
        }
        else
        {
            this.chunkProviderSettings = ChunkGeneratorSettings.Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
            this.chunkPos = pos;
            this.dirtGen = new WorldGenMinable(Blocks.DIRT.getDefaultState(), this.chunkProviderSettings.dirtSize);
            this.graniteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), this.chunkProviderSettings.graniteSize);
            this.talaStoneGen = new WorldGenMinable(BlockRegistry.TALA_STONE.getDefaultState(), this.chunkProviderSettings.graniteSize);
            this.ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), this.chunkProviderSettings.ironSize);
            this.redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), this.chunkProviderSettings.redstoneSize);
            this.diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), this.chunkProviderSettings.diamondSize);
            this.lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), this.chunkProviderSettings.lapisSize);
            this.oricoalGen = new WorldGenMinable(BlockRegistry.ORICOAL_ORE.getDefaultState(), this.chunkProviderSettings.coalSize);
            this.mixedGoldGen = new WorldGenMinable(BlockRegistry.MIXED_GOLD_ORE.getDefaultState(), this.chunkProviderSettings.goldSize);
            this.crudeGoldGen = new WorldGenMinable(BlockRegistry.CRUDE_GOLD_ORE.getDefaultState(), this.chunkProviderSettings.ironSize);
            this.grindstoneGen = new WorldGenMinable(BlockRegistry.GRINDSTONE_ORE.getDefaultState(), this.chunkProviderSettings.goldSize);
            this.incandescentGen = new WorldGenMinable(BlockRegistry.INCANDESCENT_ORE.getDefaultState(), this.chunkProviderSettings.goldSize);
            this.originiumGen = new WorldGenMinable(BlockRegistry.ORIGINIUM_ORE.getDefaultState(), this.chunkProviderSettings.diamondSize);
            this.orimanganeseGen = new WorldGenMinable(BlockRegistry.ORIMANGANESE_ORE.getDefaultState(), this.chunkProviderSettings.goldSize);
            this.orirockGen = new WorldGenMinable(BlockRegistry.ORIROCK_ORE.getDefaultState(), this.chunkProviderSettings.coalSize);
            this.orironGen = new WorldGenMinable(BlockRegistry.ORIRON_ORE.getDefaultState(), this.chunkProviderSettings.goldSize);
            this.weaponGemGen = new WorldGenMinable(BlockRegistry.WEAPON_GEM_ORE.getDefaultState(), this.chunkProviderSettings.ironSize);
            this.rmaGemGen = new WorldGenMinable(BlockRegistry.RMA_ORE.getDefaultState(), this.chunkProviderSettings.goldSize);
            this.gravelOreGen = new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), this.chunkProviderSettings.gravelSize);
            this.genDecorations(biome, worldIn, random);
            this.decorating = false;
        }
    }

    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {
        net.minecraft.util.math.ChunkPos forgeChunkPos = new net.minecraft.util.math.ChunkPos(chunkPos);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre(worldIn, random, forgeChunkPos));
        this.generateOres(worldIn, random);

        if (originiumCrystalHugeGenerate && (originiumCrystalHugeGenChance>0 ? new Random().nextInt(originiumCrystalHugeGenChance) == 0 : true))
        {
            for (int j3 = 0; j3 < originiumCrystalHugePerChunk; ++j3)
            {
                int k7 = random.nextInt(16) + 8;
                int j11 = random.nextInt(16) + 8;
                int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

                if (l14 > 0)
                {
                    int i18 = random.nextInt(l14);
                    (new WorldGeneratorNormal(BlockRegistry.ORIGINIUM_CRYSTAL_HUGE, BlockRegistry.GRASS_WITHERED)).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
                }
            }
        }

        if (teaGenerate && (teaGenChance>0 ? new Random().nextInt(teaGenChance) == 0 : true))
        {
            for (int j3 = 0; j3 < teaPerChunk; ++j3)
            {
                int k7 = random.nextInt(16) + 8;
                int j11 = random.nextInt(16) + 8;
                int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

                if (l14 > 0)
                {
                    int i18 = random.nextInt(l14);
                    (new WorldGenBush(BlockRegistry.TEA_PLANT)).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
                }
            }
        }

        if (cactusGenerate && (cactusGenChance>0 ? new Random().nextInt(cactusGenChance) == 0 : true))
        {
            for (int j3 = 0; j3 < cactusPerChunk; ++j3)
            {
                int k7 = random.nextInt(16) + 8;
                int j11 = random.nextInt(16) + 8;
                int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

                if (l14 > 0)
                {
                    int i18 = random.nextInt(l14);
                    new WorldGenCactus().generate(worldIn,random,this.chunkPos.add(k7, i18, j11));
                }
            }
        }

        if (deadBushGenerate && (deadBushGenChance>0 ? new Random().nextInt(deadBushGenChance) == 0 : true))
        {
            for (int j3 = 0; j3 < deadBushPerChunk; ++j3)
            {
                int k7 = random.nextInt(16) + 8;
                int j11 = random.nextInt(16) + 8;
                int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

                if (l14 > 0)
                {
                    int i18 = random.nextInt(l14);
                    (new WorldGenBush(Blocks.DEADBUSH)).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
                }
            }
        }

        if(jungleTreeGenerate && (jungleTreeGenChance>0 ? new Random().nextInt(jungleTreeGenChance) == 0 : true))
        {
            for (int j2 = 0; j2 < jungleTreePerChunk; ++j2)
            {
                int k6 = random.nextInt(16) + 8;
                int l = random.nextInt(16) + 8;

                WorldGenMegaJungle worldgenabstracttree = new WorldGenMegaJungle(true,10,20,Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE),Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false)));
                worldgenabstracttree.setDecorationDefaults();
                BlockPos blockpos = worldIn.getHeight(this.chunkPos.add(k6, 0, l));

                if (worldgenabstracttree.generate(worldIn, random, blockpos))
                {
                    worldgenabstracttree.generateSaplings(worldIn, random, blockpos);
                }
            }
        }

        if (iberiaWaterlilyGenerate && (iberiaWaterlilyGenChance>0 ? new Random().nextInt(iberiaWaterlilyGenChance) == 0 : true))
        {
            for (int j3 = 0; j3 < iberiaWaterlilyPerChunk; ++j3)
            {
                int k7 = random.nextInt(16) + 8;
                int j11 = random.nextInt(16) + 8;
                int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

                if (l14 > 0)
                {
                    int i18 = random.nextInt(l14);
                    (new WorldGeneratorNormal(BlockRegistry.IBERIA_WATERLILY, BlockRegistry.IBERIA_WATER)).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
                }
            }
        }

        if (fungusBlanketGenerate && (fungusBlanketGenChance>0 ? new Random().nextInt(fungusBlanketGenChance) == 0 : true))
        {
            for (int j3 = 0; j3 < fungusBlanketPerChunk; ++j3)
            {
                int k7 = random.nextInt(16) + 8;
                int j11 = random.nextInt(16) + 8;
                int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

                if (l14 > 0)
                {
                    int i18 = random.nextInt(l14);
                    (new WorldGenBush(BlockRegistry.FUNGUS_BLANKET)).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
                }
            }
        }

        if (dripstoneGenerate && (dripstoneGenChance>0 ? new Random().nextInt(dripstoneGenChance) == 0 : true))
        {
            for (int j3 = 0; j3 < dripstonePerChunk; ++j3)
            {
                int k7 = random.nextInt(16) + 8;
                int j11 = random.nextInt(16) + 8;
                int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

                if (l14 > 0)
                {
                    int i18 = random.nextInt(l14);
                    (new WorldGeneratorNormal(BlockRegistry.DRIPSTONE, BlockRegistry.IBERIA_DIRT)).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
                }
            }
        }

        if (iberiaBigFlowerGenerate && (iberiaBigFlowerGenChance>0 ? new Random().nextInt(iberiaBigFlowerGenChance) == 0 : true))
        {
            for (int j3 = 0; j3 < iberiaBigFlowerPerChunk; ++j3)
            {
                int k7 = random.nextInt(16) + 8;
                int j11 = random.nextInt(16) + 8;
                int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

                if (l14 > 0)
                {
                    int i18 = random.nextInt(l14);
                    (new WorldGenBush(BlockRegistry.IBERIA_BIG_FLOWER)).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
                }
            }
        }

        if (iberiaFlowerGenerate && (iberiaFlowerGenChance>0 ? new Random().nextInt(iberiaFlowerGenChance) == 0 : true))
        {
            for (int j3 = 0; j3 < iberiaFlowerPerChunk; ++j3)
            {
                int k7 = random.nextInt(16) + 8;
                int j11 = random.nextInt(16) + 8;
                int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

                if (l14 > 0)
                {
                    int i18 = random.nextInt(l14);
                    (new WorldGenBush(BlockRegistry.IBERIA_FLOWER)).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
                }
            }
        }

        if (seaGrassGenerate && (seaGrassGenChance>0 ? new Random().nextInt(seaGrassGenChance) == 0 : true))
        {
            for (int j3 = 0; j3 < seaGrassPerChunk; ++j3)
            {
                int k7 = random.nextInt(16) + 8;
                int j11 = random.nextInt(16) + 8;
                int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

                if (l14 > 0)
                {
                    int i18 = random.nextInt(l14);
                    WorldGeneratorWater worldGeneratorWater = new WorldGeneratorWater(BlockRegistry.SEAGRASS, BlockRegistry.IBERIA_DIRT);
                    worldGeneratorWater.generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
                }
            }
        }

        if(bigMushroomGenerate && (bigMushroomGenChance>0 ? new Random().nextInt(bigMushroomGenChance) == 0 : true))
        {
            for (int k2 = 0; k2 < this.bigMushroomPerChunk; ++k2)
            {
                int l6 = random.nextInt(16) + 8;
                int k10 = random.nextInt(16) + 8;
                this.bigMushroomGen.generate(worldIn, random, worldIn.getHeight(this.chunkPos.add(l6, 0, k10)));
            }
        }

        if(taigaGenerate && (taigaGenChance>0 ? new Random().nextInt(taigaGenChance) == 0 : true))
        {
            for (int j2 = 0; j2 < taigaPerChunk; ++j2)
            {
                int k6 = random.nextInt(16) + 8;
                int l = random.nextInt(16) + 8;

                WorldGenTaiga2 worldgenabstracttree = new WorldGenTaiga2(false);
                worldgenabstracttree.setDecorationDefaults();
                BlockPos blockpos = worldIn.getHeight(this.chunkPos.add(k6, 0, l));

                if (worldgenabstracttree.generate(worldIn, random, blockpos))
                {
                    worldgenabstracttree.generateSaplings(worldIn, random, blockpos);
                }
            }
        }

        if(treeGenerate && (treeGenChance>0 ? new Random().nextInt(treeGenChance) == 0 : true))
        {
            for (int j2 = 0; j2 < treePerChunk; ++j2)
            {
                int k6 = random.nextInt(16) + 8;
                int l = random.nextInt(16) + 8;
                int i = new Random().nextInt(treeMetas.length);

                WorldGenTrees worldgenabstracttree = new WorldGenTrees(true,4,Blocks.LOG.getStateFromMeta(treeMetas[i]),Blocks.LEAVES.getStateFromMeta(treeMetas[i]),false);
                worldgenabstracttree.setDecorationDefaults();
                BlockPos blockpos = worldIn.getHeight(this.chunkPos.add(k6, 0, l));

                if (worldgenabstracttree.generate(worldIn, random, blockpos))
                {
                    worldgenabstracttree.generateSaplings(worldIn, random, blockpos);
                }
            }
        }

        if(shrubGenerate && (shrubGenChance>0 ? new Random().nextInt(shrubGenChance) == 0 : true))
        {
            for (int j2 = 0; j2 < shrubPerChunk; ++j2)
            {
                int k6 = random.nextInt(16) + 8;
                int l = random.nextInt(16) + 8;
                WorldGenShrub worldgenabstracttree = new WorldGenShrub(Blocks.LOG.getDefaultState(),Blocks.LEAVES.getDefaultState());
                worldgenabstracttree.setDecorationDefaults();
                BlockPos blockpos = worldIn.getHeight(this.chunkPos.add(k6, 0, l));

                if (worldgenabstracttree.generate(worldIn, random, blockpos))
                {
                    worldgenabstracttree.generateSaplings(worldIn, random, blockpos);
                }
            }
        }

        if(flowerGenerate && (flowerGenChance>0 ? new Random().nextInt(flowerGenChance) == 0 : true))
        {
            for (int l2 = 0; l2 < flowerPerChunk; ++l2)
            {
                int i7 = random.nextInt(16) + 8;
                int l10 = random.nextInt(16) + 8;
                int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

                if (j14 > 0)
                {
                    int k17 = random.nextInt(j14);
                    BlockPos blockpos1 = this.chunkPos.add(i7, k17, l10);
                    BlockFlower.EnumFlowerType blockflower$enumflowertype = biomeIn.pickRandomFlower(random, blockpos1);
                    BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();

                    if (blockflower.getDefaultState().getMaterial() != Material.AIR)
                    {
                        flowerGen.setGeneratedBlock(blockflower, blockflower$enumflowertype);
                        flowerGen.generate(worldIn, random, blockpos1);
                    }
                }
            }
        }

        if (niceMushroomGenerate && (niceMushroomGenChance>0 ? new Random().nextInt(niceMushroomGenChance) == 0 : true))
        {
            for (int j3 = 0; j3 < niceMushroomPerChunk; ++j3)
            {
                int k7 = random.nextInt(16) + 8;
                int j11 = random.nextInt(16) + 8;
                int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

                if (l14 > 0)
                {
                    int i18 = random.nextInt(l14);
                    (new WorldGenBush(BlockRegistry.NICE_MUSHROOM)).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
                }
            }
        }

        if (hugeTreeGenerate && (hugeTreeGenChance>0 ? new Random().nextInt(hugeTreeGenChance) == 0 : true))
        {
            for (int j2 = 0; j2 < hugeTreePerChunk; ++j2)
            {
                int k6 = random.nextInt(16) + 8;
                int l = random.nextInt(16) + 8;
                WorldGenHugeTree tree = new WorldGenHugeTree(true, 20, 20, BlockRegistry.HUGE_LOG.getDefaultState(), BlockRegistry.HUGE_LEAF.getDefaultState());
                BlockPos blockpos = worldIn.getHeight(this.chunkPos.add(k6, 0, l));
                if (tree.generate(worldIn, random, blockpos))
                {
                    tree.generateSaplings(worldIn, random, blockpos);
                }
            }
        }

        if (mossGenerate && (mossGenChance>0 ? new Random().nextInt(mossGenChance) == 0 : true))
        {
            for (int j3 = 0; j3 < mossPerChunk; ++j3)
            {
                int k7 = random.nextInt(16) + 8;
                int j11 = random.nextInt(16) + 8;
                int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

                if (l14 > 0)
                {
                    int i18 = random.nextInt(l14);
                    (new WorldGenBush(BlockRegistry.MOSS)).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
                }
            }
        }

        if (flowerFrostGenerate && (flowerFrostGenChance>0 ? new Random().nextInt(flowerFrostGenChance) == 0 : true))
        {
            for (int j3 = 0; j3 < flowerFrostPerChunk; ++j3)
            {
                int k7 = random.nextInt(16) + 8;
                int j11 = random.nextInt(16) + 8;
                int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

                if (l14 > 0)
                {
                    int i18 = random.nextInt(l14);
                    (new WorldGenBush(BlockRegistry.FLOWER_FROST)).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
                }
            }
        }

        if (originiumCrystalGenerate && (originiumCrystalGenChance>0 ? new Random().nextInt(originiumCrystalGenChance) == 0 : true))
        {
            for (int j3 = 0; j3 < originiumCrystalPerChunk; ++j3)
            {
                int k7 = random.nextInt(16) + 8;
                int j11 = random.nextInt(16) + 8;
                int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

                if (l14 > 0)
                {
                    int i18 = random.nextInt(l14);
                    (new WorldGeneratorNormal(BlockRegistry.ORIGINIUM_CRYSTAL, BlockRegistry.GRASS_WITHERED)).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
                }
            }
        }

        if (willowTreesGenerate && (willowTreesGenChance>0 ? new Random().nextInt(willowTreesGenChance) == 0 : true))
        {
            for (int j2 = 0; j2 < willowTreesPerChunk; ++j2)
            {
                int k6 = random.nextInt(16) + 8;
                int l = random.nextInt(16) + 8;
                WorldGenWillowTree tree = new WorldGenWillowTree(true);
                BlockPos blockpos = worldIn.getHeight(this.chunkPos.add(k6, 0, l));
                if (tree.generate(worldIn, random, blockpos))
                {
                    tree.generateSaplings(worldIn, random, blockpos);
                }
            }
        }

        if (witheredGrassGenerate && (witheredGrassGenChance>0 ? new Random().nextInt(witheredGrassGenChance) == 0 : true))
        {
            for (int j3 = 0; j3 < witheredGrassPerChunk; ++j3)
            {
                int k7 = random.nextInt(16) + 8;
                int j11 = random.nextInt(16) + 8;
                int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

                if (l14 > 0)
                {
                    int i18 = random.nextInt(l14);
                    (new WorldGenBush(BlockRegistry.WITHERED_GRASS)).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
                }
            }
        }

        if (grassGenerate && (grassGenChance>0 ? new Random().nextInt(grassGenChance) == 0 : true))
            for (int i3 = 0; i3 < this.grassPerChunk; ++i3)
            {
                int j7 = random.nextInt(16) + 8;
                int i11 = random.nextInt(16) + 8;
                int k14 = worldIn.getHeight(this.chunkPos.add(j7, 0, i11)).getY() * 2;

                if (k14 > 0)
                {
                    int l17 = random.nextInt(k14);
                    biomeIn.getRandomWorldGenForGrass(random).generate(worldIn, random, this.chunkPos.add(j7, l17, i11));
                }
            }

        if (pumpkinGenerate && (pumpkinGenChance>0 ? new Random().nextInt(pumpkinGenChance) == 0 : true))
        {

            int i5 = random.nextInt(16) + 8;
            int k9 = random.nextInt(16) + 8;
            int j13 = worldIn.getHeight(this.chunkPos.add(i5, 0, k9)).getY() * 2;

            if (j13 > 0)
            {
                int k16 = random.nextInt(j13);
                (new WorldGenPumpkin()).generate(worldIn, random, this.chunkPos.add(i5, k16, k9));
            }
        }

        if (waterFallGenerate && (waterFallGenChance>0 ? new Random().nextInt(waterFallGenChance) == 0 : true))
        {
            for (int k5 = 0; k5 < 50; ++k5)
            {
                int i10 = random.nextInt(16) + 8;
                int l13 = random.nextInt(16) + 8;
                int i17 = random.nextInt(248) + 8;

                if (i17 > 0)
                {
                    int k19 = random.nextInt(i17);
                    BlockPos blockpos6 = this.chunkPos.add(i10, k19, l13);
                    (new WorldGenLiquids(Blocks.FLOWING_WATER)).generate(worldIn, random, blockpos6);
                }
            }
        }

        if (lavaFallGenerate && (lavaFallGenChance>0 ? new Random().nextInt(lavaFallGenChance) == 0 : true))
        {
            for (int l5 = 0; l5 < 20; ++l5)
            {
                int j10 = random.nextInt(16) + 8;
                int i14 = random.nextInt(16) + 8;
                int j17 = random.nextInt(random.nextInt(random.nextInt(240) + 8) + 8);
                BlockPos blockpos3 = this.chunkPos.add(j10, j17, i14);
                (new WorldGenLiquids(Blocks.FLOWING_LAVA)).generate(worldIn, random, blockpos3);
            }
        }

        if(mushroomGenerate && (mushroomGenChance>0 ? new Random().nextInt(mushroomGenChance) == 0 : true))
        {
            for (int l3 = 0; l3 < this.mushroomPerChunk; ++l3)
            {
                int i8 = random.nextInt(16) + 8;
                int l11 = random.nextInt(16) + 8;
                BlockPos blockpos2 = worldIn.getHeight(this.chunkPos.add(i8, 0, l11));
                this.mushroomBrownGen.generate(worldIn, random, blockpos2);

                int j8 = random.nextInt(16) + 8;
                int i12 = random.nextInt(16) + 8;
                int j15 = worldIn.getHeight(this.chunkPos.add(j8, 0, i12)).getY() * 2;

                if (j15 > 0)
                {
                    int k18 = random.nextInt(j15);
                    BlockPos blockpos5 = this.chunkPos.add(j8, k18, i12);
                    this.mushroomRedGen.generate(worldIn, random, blockpos5);
                }
            }
        }

        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldIn, random, forgeChunkPos));
    }

    protected void generateOres(World worldIn, Random random)
    {
        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(worldIn, random, chunkPos));
        if (TerrainGen.generateOre(worldIn, random, dirtGen, chunkPos, OreGenEvent.GenerateMinable.EventType.DIRT))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.dirtCount, this.dirtGen, this.chunkProviderSettings.dirtMinHeight, this.chunkProviderSettings.dirtMaxHeight);

        if (TerrainGen.generateOre(worldIn, random, graniteGen, chunkPos, OreGenEvent.GenerateMinable.EventType.GRANITE))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.graniteCount, this.graniteGen, this.chunkProviderSettings.graniteMinHeight, this.chunkProviderSettings.graniteMaxHeight);

        if (TerrainGen.generateOre(worldIn, random, ironGen, chunkPos, OreGenEvent.GenerateMinable.EventType.IRON))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.ironCount, this.ironGen, this.chunkProviderSettings.ironMinHeight, this.chunkProviderSettings.ironMaxHeight);

        if (TerrainGen.generateOre(worldIn, random, redstoneGen, chunkPos, OreGenEvent.GenerateMinable.EventType.REDSTONE))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.redstoneCount, this.redstoneGen, this.chunkProviderSettings.redstoneMinHeight, this.chunkProviderSettings.redstoneMaxHeight);

        if (TerrainGen.generateOre(worldIn, random, diamondGen, chunkPos, OreGenEvent.GenerateMinable.EventType.DIAMOND))
            this.genStandardOre1(worldIn, random, this.chunkProviderSettings.diamondCount, this.diamondGen, this.chunkProviderSettings.diamondMinHeight, this.chunkProviderSettings.diamondMaxHeight);

        if (TerrainGen.generateOre(worldIn, random, lapisGen, chunkPos, OreGenEvent.GenerateMinable.EventType.LAPIS))
            this.genStandardOre2(worldIn, random, this.chunkProviderSettings.lapisCount, this.lapisGen, this.chunkProviderSettings.lapisCenterHeight, this.chunkProviderSettings.lapisSpread);

        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.graniteCount, this.talaStoneGen, this.chunkProviderSettings.graniteMinHeight, this.chunkProviderSettings.graniteMaxHeight);

        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.coalCount, this.oricoalGen, this.chunkProviderSettings.coalMinHeight, this.chunkProviderSettings.coalMaxHeight);

        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.coalCount, this.orirockGen, this.chunkProviderSettings.coalMinHeight, this.chunkProviderSettings.coalMaxHeight);

        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.goldCount, this.crudeGoldGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);

        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.goldCount, this.mixedGoldGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);

        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.goldCount, this.grindstoneGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);

        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.goldCount, this.incandescentGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);

        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.goldCount, this.orimanganeseGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);

        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.ironCount, this.weaponGemGen, this.chunkProviderSettings.ironMinHeight, this.chunkProviderSettings.ironMaxHeight);

        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.ironCount, this.orironGen, this.chunkProviderSettings.ironMinHeight, this.chunkProviderSettings.ironMaxHeight);

        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.diamondCount + originiumAmountAdd, this.originiumGen, this.chunkProviderSettings.diamondMaxHeight, this.chunkProviderSettings.diamondMaxHeight);

        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.goldCount, this.rmaGemGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);

        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.gravelCount, this.gravelOreGen, this.chunkProviderSettings.gravelMinHeight, this.chunkProviderSettings.gravelMaxHeight);

        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(worldIn, random, chunkPos));
    }

    protected void genStandardOre1(World worldIn, Random random, int blockCount, WorldGenerator generator, int minHeight, int maxHeight)
    {
        if (maxHeight < minHeight)
        {
            int i = minHeight;
            minHeight = maxHeight;
            maxHeight = i;
        }
        else if (maxHeight == minHeight)
        {
            if (minHeight < 255)
            {
                ++maxHeight;
            }
            else
            {
                --minHeight;
            }
        }

        for (int j = 0; j < blockCount; ++j)
        {
            BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(maxHeight - minHeight) + minHeight, random.nextInt(16));
            generator.generate(worldIn, random, blockpos);
        }
    }

    protected void genStandardOre2(World worldIn, Random random, int blockCount, WorldGenerator generator, int centerHeight, int spread)
    {
        for (int i = 0; i < blockCount; ++i)
        {
            BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(spread) + random.nextInt(spread) + centerHeight - spread, random.nextInt(16));
            generator.generate(worldIn, random, blockpos);
        }
    }
}
