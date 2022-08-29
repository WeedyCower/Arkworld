package com.weedycow.arkworld.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ArkBlockUtil
{
    public static List<Entity> getEntityOnBlockUp(World world, BlockPos pos)
    {
        AxisAlignedBB range = new AxisAlignedBB(pos.up());
        return world.getEntitiesWithinAABB(Entity.class,range);
    }
}
