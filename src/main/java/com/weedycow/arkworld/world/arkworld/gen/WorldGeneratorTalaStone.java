package com.weedycow.arkworld.world.arkworld.gen;

import com.weedycow.arkworld.registry.BlockRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class WorldGeneratorTalaStone extends WorldGenerator
{
    public BlockPos pos;
    private final WorldGenerator glowstoneGenerator = new WorldGenMinable(BlockRegistry.TALA_STONE.getDefaultState(), 16);

    @SubscribeEvent
    public void onOreGenPost(OreGenEvent.Post event)
    {
        WorldGeneratorTalaStone talaStone = new WorldGeneratorTalaStone();
        if (!event.getPos().equals(talaStone.pos))
        {
            talaStone.pos = event.getPos();
            talaStone.generate(event.getWorld(), event.getRand(), event.getPos());
        }
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if (TerrainGen.generateOre(world, rand, this, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
        {
            for (int i = 0; i < 4; ++i)
            {
                int posX = pos.getX() + rand.nextInt(16);
                int posY = 16 + rand.nextInt(16);
                int posZ = pos.getZ() + rand.nextInt(16);
                BlockPos blockpos = new BlockPos(posX, posY, posZ);
                glowstoneGenerator.generate(world, rand, blockpos);
            }
        }
        return true;
    }
}
