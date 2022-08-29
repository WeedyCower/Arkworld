package com.weedycow.arkworld.block.floor;

import com.weedycow.arkworld.block.ArkTileTickable;
import com.weedycow.arkworld.util.ArkBlockUtil;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.arkworld.util.ParticleList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

public class BlockHotFloor extends BlockFloor
{
    public BlockHotFloor()
    {
        super(Type.HOT_FLOOR);
    }

    @Override
    @ParametersAreNonnullByDefault
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileHotFloor();
    }

    public static class TileHotFloor extends ArkTileTickable
    {
        public TileHotFloor()
        {
            super(Type.HOT_FLOOR.getName(),100,false);
        }

        @Override
        protected void intervalExecute()
        {
            ParticleList.hotFloorParticle(world,pos);

            for(Entity entity : ArkBlockUtil.getEntityOnBlockUp(world,pos))
            {
                if(entity instanceof EntityLivingBase)
                {
                    entity.attackEntityFrom(DamageSource.GENERIC,100);
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
