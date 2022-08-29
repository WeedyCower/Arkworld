package com.weedycow.arkworld.block.floor;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.ArkMaterial;
import com.weedycow.weedylib.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public abstract class BlockFloor extends Block
{
    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    public BlockFloor(Type type)
    {
        super(ArkMaterial.TRAP);
        this.setCreativeTab(ArkCreateTab.DEVICE);
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(TYPE,type));
        this.setRegistryName(type.getName());
        this.setUnlocalizedName(Arkworld.MODID + "." + StringUtil.lineToHump(type.getName()));
        this.setHarvestLevel("pickaxe",2);
        this.setHardness(2f);
        this.setResistance(3f);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

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
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(TYPE).getSerialNumber();
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(TYPE,Type.getType(meta));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE);
    }

    public Type getType()
    {
        return blockState.getBaseState().getValue(TYPE);
    }

    protected void setType(Type type)
    {
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE,type));
    }

    public enum Type implements IStringSerializable
    {
        SEALED_FLOOR(0),
        ORIGINIUM_FLOOR(1),
        FLOOR_BASE(2),
        DEFENSE_FLOOR(3),
        PUSH_FLOOR(4),
        HEALING_FLOOR(5),
        HOT_FLOOR(6),
        REDUCE_DEFENSE_FLOOR(7);

        final int sn;

        Type(int sn)
        {
            this.sn = sn;
        }

        @Nonnull
        @Override
        public String getName()
        {
            return name().toLowerCase();
        }

        public int getSerialNumber()
        {
            return this.sn;
        }

        public static Type getType(int sn)
        {
            for(Type type : Type.values())
            {
                if(type.getSerialNumber() == sn)
                    return type;
            }
            return null;
        }
    }
}
