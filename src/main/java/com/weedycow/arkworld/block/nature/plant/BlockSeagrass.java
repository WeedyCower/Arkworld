package com.weedycow.arkworld.block.nature.plant;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.ArkMaterial;
import com.weedycow.arkworld.block.ArkTile;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockSeagrass extends Block implements ITileEntityProvider
{
    public BlockSeagrass()
    {
        super(ArkMaterial.SEA_PLANT);
        this.setUnlocalizedName(Arkworld.MODID + ".seagrass");
        this.setCreativeTab(ArkCreateTab.BLOCK);
        this.setRegistryName("seagrass");
        this.setSoundType(SoundType.PLANT);
        this.setHardness(0);
    }

    @Nonnull
    @Override
    public EnumBlockRenderType getRenderType(@Nonnull IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileSeagrass();
    }

    public static class TileSeagrass extends ArkTile
    {
        public TileSeagrass()
        {
            super("seagrass");
        }
    }
}
