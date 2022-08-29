package com.weedycow.arkworld.world.arkworld.gen;

import com.weedycow.arkworld.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGeneratorNormal extends WorldGenerator
{
    private final Block block;
    private final Block onWhatBlock;
    public int adjustY;

    public WorldGeneratorNormal(Block blockIn ,Block onWhatBlock)
    {
        this.block = blockIn;
        this.onWhatBlock = onWhatBlock;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4)+ adjustY, rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.isNether() || blockpos.getY() < worldIn.getHeight() - 1)
                    && worldIn.getBlockState(blockpos.down()).getBlock() == onWhatBlock)
            {
                if(block!= BlockRegistry.ORIGINIUM_CRYSTAL_HUGE)
                    worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
                else
                {
                    List<Block> blockList = new ArrayList<>();

                    for(int x=0; x<=8; x++)
                    {
                        for(int z=0; z<=8; z++)
                        {
                            for(int y=0; y<=8; y++)
                            {
                                blockList.add(worldIn.getBlockState(position.add(x-4,y-4,z-4)).getBlock());
                            }
                        }
                    }

                    if(!blockList.contains(BlockRegistry.ORIGINIUM_CRYSTAL_HUGE))
                    {
                        worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
                    }
                }
            }
        }

        return true;
    }
}
