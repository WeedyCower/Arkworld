package com.weedycow.arkworld.block.nature.surface;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockInactivationOriginiumBlock extends Block
{
    public BlockInactivationOriginiumBlock()
    {
        super(Material.IRON);
        this.setUnlocalizedName(Arkworld.MODID + ".inactivationOriginiumBlock");
        this.setCreativeTab(ArkCreateTab.BLOCK);
        this.setHarvestLevel("pickaxe",3);
        this.setRegistryName("inactivation_originium_block");
        this.setHardness(4f);
        this.setResistance(6f);
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(@Nonnull IBlockState state) {
        return false;
    }

    @Deprecated
    @SideOnly(Side.CLIENT)
    @ParametersAreNonnullByDefault
    public float getAmbientOcclusionLightValue(IBlockState state)
    {
        return 1F;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();

        if (blockState != iblockstate)
        {
            return true;
        }

        if (block == BlockRegistry.ORIGINIUM_BLOCK || block == BlockRegistry.ACTIVE_ORIGINIUM_BLOCK || block == BlockRegistry.INACTIVATION_ORIGINIUM_BLOCK)
        {
            return false;
        }

        return block != this && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
}
