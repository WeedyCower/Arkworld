package com.weedycow.arkworld.block.machine;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.ArkMaterial;
import com.weedycow.arkworld.block.ArkTile;
import com.weedycow.arkworld.capability.CapabilityItem;
import com.weedycow.arkworld.item.normal.Coin;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

public class BlockModBox extends Block implements ITileEntityProvider
{
    public BlockModBox()
    {
        super(ArkMaterial.MACHINE);
        this.setCreativeTab(ArkCreateTab.DEVICE);
        this.setRegistryName("mod_box");
        this.setUnlocalizedName(Arkworld.MODID + ".modBox");
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        onDraw(playerIn);
        return true;
    }

    public void onDraw(EntityPlayer player)
    {
        ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
        World world = player.world;

        if (stack.getItem() instanceof Coin && !world.isRemote)
        {
            ItemStack armsMod = new ItemStack(ItemRegistry.ARMS_MOD);
            ItemStack defenceMod = new ItemStack(ItemRegistry.DEFENCE_MOD);
            ItemStack playerMod = new ItemStack(ItemRegistry.PLAYER_MOD);
            ItemStack normalWeaponMod = new ItemStack(ItemRegistry.NORMAL_WEAPON_MOD);
            ItemStack highWeaponMod = new ItemStack(ItemRegistry.HIGH_WEAPON_MOD);
            ItemStack epicWeaponMod = new ItemStack(ItemRegistry.EPIC_WEAPON_MOD);

            int whatMod = new Random().nextInt(2);

            if (whatMod == 0)
            {
                CapabilityItem.Process armsModProcess = new CapabilityItem.Process(armsMod);
                CapabilityItem.Process defenceModProcess = new CapabilityItem.Process(defenceMod);
                CapabilityItem.Process playerModProcess = new CapabilityItem.Process(playerMod);

                int forTimes;

                if(Arkworld.NEWDES)
                    forTimes = new Random().nextInt(2) + new Random().nextInt(2) + new Random().nextInt(2);
                else
                    forTimes = new Random().nextInt(3) + 1;

                for (int i = 1; i <= forTimes; i++)
                {
                    armsModProcess.addEntry(CapabilityItem.arms[new Random().nextInt(CapabilityItem.arms.length)], new Random().nextInt(5) + 1);
                    defenceModProcess.addEntry(CapabilityItem.defence[new Random().nextInt(CapabilityItem.defence.length)], new Random().nextInt(5) + 1);
                    playerModProcess.addEntry(CapabilityItem.player[new Random().nextInt(CapabilityItem.player.length)], new Random().nextInt(5) + 1);
                }

                int s = new Random().nextInt(3);

                if (s == 0)
                {
                    if(player.inventory.add(-1,armsMod)) stack.shrink(1);
                }

                if (s == 1)
                {
                    if(player.inventory.add(-1,defenceMod)) stack.shrink(1);
                }

                if (s == 2)
                {
                    if(player.inventory.add(-1,playerMod)) stack.shrink(1);
                }
            }
            else
            {
                int chance;

                if(Arkworld.NEWDES)
                    chance = new Random().nextInt(20);
                else
                    chance = new Random().nextInt(10);

                if (chance < 1)
                {
                    if(player.inventory.add(-1,epicWeaponMod)) stack.shrink(1);
                }
                else if (chance < 4)
                {
                    if(player.inventory.add(-1,highWeaponMod)) stack.shrink(1);
                }
                else
                {
                    if(player.inventory.add(-1,normalWeaponMod)) stack.shrink(1);
                }
            }
        }
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

    @Override
    @ParametersAreNonnullByDefault
    public boolean hasTileEntity(IBlockState state) {return true;}

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileModBox();
    }

    public static class TileModBox extends ArkTile
    {
        public TileModBox()
        {
            super("mod_box");
        }

        @Override
        public ResourceLocation getModelLocation()
        {
            return ArkResUtil.geo("normal_block");
        }
    }
}
