package com.weedycow.arkworld.world.arkworld.gen;

import com.weedycow.arkworld.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGeneratorWater extends WorldGenerator
{
    private final Block block;
    private final Block onWhatBlock;
    public int adjustY;

    public WorldGeneratorWater(Block blockIn ,Block onWhatBlock)
    {
        this.block = blockIn;
        this.onWhatBlock = onWhatBlock;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4)+ adjustY, rand.nextInt(8) - rand.nextInt(8));

            if ((worldIn.getBlockState(blockpos.up()).getBlock() == Blocks.WATER ||worldIn.getBlockState(blockpos.up()).getBlock() == BlockRegistry.IBERIA_WATER) && (!worldIn.provider.isNether() || blockpos.getY() < worldIn.getHeight() - 1)
                    && worldIn.getBlockState(blockpos).getBlock() == onWhatBlock)
            {
                worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
            }
        }

        return true;
    }
}
