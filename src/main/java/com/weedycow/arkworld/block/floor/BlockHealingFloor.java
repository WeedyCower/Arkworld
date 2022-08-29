package com.weedycow.arkworld.block.floor;

import com.weedycow.arkworld.block.ArkTileTickable;
import com.weedycow.arkworld.util.ArkBlockUtil;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

public class BlockHealingFloor extends BlockFloor
{
    public BlockHealingFloor()
    {
        super(Type.HEALING_FLOOR);
    }

    @Override
    @ParametersAreNonnullByDefault
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileHealingFloor();
    }

    public static class TileHealingFloor extends ArkTileTickable
    {
        public TileHealingFloor()
        {
            super(Type.HEALING_FLOOR.getName(),20,false);
        }

        @Override
        protected void intervalExecute()
        {
            for(Entity entity : ArkBlockUtil.getEntityOnBlockUp(world,pos))
            {
                if(entity instanceof EntityLivingBase)
                {
                    ((EntityLivingBase) entity).heal(0.5f);
                }
            }
        }

        @Override
        public ResourceLocation getModelLocation()
        {
            return ArkResUtil.geo("normal_block");
        }
    }
}
