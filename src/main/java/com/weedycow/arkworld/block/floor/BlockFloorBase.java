package com.weedycow.arkworld.block.floor;

import com.weedycow.arkworld.block.ArkTile;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

public class BlockFloorBase extends BlockFloor
{
    public BlockFloorBase()
    {
        super(Type.FLOOR_BASE);
    }

    @Override
    @ParametersAreNonnullByDefault
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileFloorBase();
    }

    public static class TileFloorBase extends ArkTile
    {
        public TileFloorBase()
        {
            super(Type.FLOOR_BASE.getName());
        }

        @Override
        public ResourceLocation getModelLocation()
        {
            return ArkResUtil.geo("normal_block");
        }
    }
}
