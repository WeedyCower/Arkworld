package com.weedycow.arkworld.block.floor;

import com.weedycow.arkworld.block.ArkTileTickable;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.util.ArkBlockUtil;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.EnumCamps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

public class BlockSealedFloor extends BlockFloor
{
    public BlockSealedFloor()
    {
        super(Type.SEALED_FLOOR);
    }

    @Override
    @ParametersAreNonnullByDefault
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileSealedFloor();
    }

    public static class TileSealedFloor extends ArkTileTickable
    {
        public TileSealedFloor()
        {
            super(Type.SEALED_FLOOR.getName(),20,false);
        }

        @Override
        protected void intervalExecute()
        {
            for(Entity entity : ArkBlockUtil.getEntityOnBlockUp(world,pos))
            {
                if(entity instanceof EntityLivingBase && (!(entity instanceof ArkMob) || ((ArkMob)entity).getCamp()!= EnumCamps.UNION))
                {
                    CapabilityState.Process state = new CapabilityState.Process((EntityLivingBase) entity);
                    if(entity.attackEntityFrom(DamageSource.GENERIC,10))
                        state.addFunctionOnlyTick(EnumState.COLD,60);
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