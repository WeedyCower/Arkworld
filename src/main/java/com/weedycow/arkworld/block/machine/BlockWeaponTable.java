package com.weedycow.arkworld.block.machine;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.ArkMaterial;
import com.weedycow.arkworld.block.ArkTile;
import com.weedycow.arkworld.registry.GuiRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockWeaponTable extends BlockContainer
{
    public BlockWeaponTable()
    {
        super(ArkMaterial.MACHINE);
        this.setCreativeTab(ArkCreateTab.DEVICE);
        this.setRegistryName("weapon_table");
        this.setUnlocalizedName(Arkworld.MODID + ".weaponTable");
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
            playerIn.openGui(Arkworld.instance, GuiRegistry.WEAPON_TABLE, worldIn, pos.getX(), pos.getY(), pos.getZ());

        return true;
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

    @Deprecated
    @SideOnly(Side.CLIENT)
    @ParametersAreNonnullByDefault
    public float getAmbientOcclusionLightValue(IBlockState state)
    {
        return 1F;
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
    @ParametersAreNonnullByDefault
    public boolean hasTileEntity(IBlockState state) {return true;}

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileWeaponTable();
    }

    public static class TileWeaponTable extends ArkTile
    {
        protected ItemStackHandler slot = new ItemStackHandler(2);

        public TileWeaponTable()
        {
            super("weapon_table");
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
        {
            if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
            {
                return true;
            }
            return super.hasCapability(capability, facing);
        }

        @Nullable
        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
            {
                @SuppressWarnings("unchecked")
                T result = (T) slot;
                return result;
            }
            return super.getCapability(capability, facing);
        }

        @Override
        public void readFromNBT(@Nonnull NBTTagCompound compound)
        {
            super.readFromNBT(compound);

            this.slot.deserializeNBT(compound.getCompoundTag("Slot"));
        }

        @Nonnull
        @Override
        public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
        {
            super.writeToNBT(compound);

            compound.setTag("Slot", this.slot.serializeNBT());

            return compound;
        }

        public ItemStackHandler getSlot()
        {
            return slot;
        }

        @Override
        public ResourceLocation getModelLocation()
        {
            return ArkResUtil.geo("normal_block");
        }
    }
}
