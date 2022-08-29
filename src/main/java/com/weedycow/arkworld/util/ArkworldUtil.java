package com.weedycow.arkworld.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ArkworldUtil
{
    public static double findSuitableY(World world, double x, double z)
    {
        boolean foundGround = false;
        double y = world.provider.getDimension()==-1 ? world.getHeight()-180 : world.getHeight()-56;
        while (!foundGround && y-- > 0)
        {
            foundGround = !world.isAirBlock(new BlockPos(x, y, z)) && world.isAirBlock(new BlockPos(x, y, z).up());
        }
        return ((world.provider.getDimension()==-1 && y<world.getHeight()-180) || y<world.getHeight()-100) && y>0 ? y+1 : 1;
    }
}
