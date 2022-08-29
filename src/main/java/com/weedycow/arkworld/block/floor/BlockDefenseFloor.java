package com.weedycow.arkworld.block.floor;

import com.weedycow.arkworld.block.ArkTileTickable;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.util.ArkBlockUtil;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

public class BlockDefenseFloor extends BlockFloor
{
    public BlockDefenseFloor()
    {
        super(Type.DEFENSE_FLOOR);
    }

    @Override
    @ParametersAreNonnullByDefault
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileDefenseFloor();
    }

    public static class TileDefenseFloor extends ArkTileTickable
    {
        public TileDefenseFloor()
        {
            super(Type.DEFENSE_FLOOR.getName(),20,false);
        }

        @Override
        protected void intervalExecute()
        {
            for(Entity entity : ArkBlockUtil.getEntityOnBlockUp(world,pos))
            {
                if(entity instanceof EntityLivingBase)
                {
                    CapabilityState.Process state = new CapabilityState.Process((EntityLivingBase) entity);
                    state.addFunctionOnlyTick(EnumState.ADD_DEFENSE,20);
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
