package com.weedycow.arkworld.block.machine;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.ArkMaterial;
import com.weedycow.arkworld.block.ArkTile;
import com.weedycow.arkworld.item.normal.Headhunt;
import com.weedycow.arkworld.item.normal.HeadhuntTen;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockOperatorBox extends Block implements ITileEntityProvider
{
    public BlockOperatorBox()
    {
        super(ArkMaterial.MACHINE);
        this.setCreativeTab(ArkCreateTab.DEVICE);
        this.setRegistryName("operator_box");
        this.setUnlocalizedName(Arkworld.MODID + ".operatorBox");
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

        if ((stack.getItem() instanceof Headhunt || stack.getItem() instanceof HeadhuntTen) && !world.isRemote)
        {
            List<ItemStack> six;
            List<ItemStack> five;
            List<ItemStack> four;
            List<ItemStack> three;

            if(!Arkworld.NEWDES)
            {
                six = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.W_CARD)));
                five = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.AMIYA_CARD)));
                four = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.PURESTREAM_CARD)));
                three = new ArrayList<>(Arrays.asList());
            }
            else
            {
                six = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.WHITE_HORSE_KOHL),
                        new ItemStack(ItemRegistry.MANGANESE_TRIHYDRATE),new ItemStack(ItemRegistry.GRINDSTONE_PENTAHYDRATE),
                        new ItemStack(ItemRegistry.RMAR),new ItemStack(ItemRegistry.ORIROCK_CONCENTRATION),
                        new ItemStack(ItemRegistry.OPTIMIZED_DEVICE),new ItemStack(ItemRegistry.POLYESTER_LUMP),
                        new ItemStack(ItemRegistry.SUGAR_LUMP),new ItemStack(ItemRegistry.ORIRON_BLOCK),
                        new ItemStack(ItemRegistry.KETON_COLLOID),new ItemStack(ItemRegistry.POLYMERIZED_GEL),
                        new ItemStack(ItemRegistry.INCANDESCENT_ALLOY_BLOCK),new ItemStack(ItemRegistry.CRYSTALLINE_CIRCUIT),
                        new ItemStack(ItemRegistry.CARBON_PACK),new ItemStack(ItemRegistry.REINFORCED_BUILDING_MATERIAL),
                        new ItemStack(ItemRegistry.SKILL_SUMMARY_III),new ItemStack(ItemRegistry.TACTICAL_BATTLE_RECORD),
                        new ItemStack(ItemRegistry.LMB_HUNDRED),new ItemStack(ItemRegistry.HEADHUNT),
                        new ItemStack(ItemRegistry.TOP_EMERGENCY_SANITY_POTION),new ItemStack(ItemRegistry.SURTR_CARD),
                        new ItemStack(ItemRegistry.KETTLE),new ItemStack(ItemRegistry.KEEL),
                        new ItemStack(ItemRegistry.TWO_LOAVES_WITH_CHEESE),new ItemStack(ItemRegistry.W_CARD)));
                five = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.LOXIC_KOHL),
                        new ItemStack(ItemRegistry.MANGANESE_ORE),new ItemStack(ItemRegistry.GRINDSTONE),
                        new ItemStack(ItemRegistry.RMAY),new ItemStack(ItemRegistry.ORIROCK_CLUSTER),
                        new ItemStack(ItemRegistry.INTEGRATED_DEVICE),new ItemStack(ItemRegistry.POLYESTER_PACK),
                        new ItemStack(ItemRegistry.SUGAR_PACK),new ItemStack(ItemRegistry.ORIRON_CLUSTER),
                        new ItemStack(ItemRegistry.AKETON),new ItemStack(ItemRegistry.COAGULATING_GEL),
                        new ItemStack(ItemRegistry.INCANDESCENT_ALLOY),new ItemStack(ItemRegistry.CRYSTALLINE_COMPONENT),
                        new ItemStack(ItemRegistry.CARBON_BRICK),new ItemStack(ItemRegistry.CONCRETE_BUILDING_MATERIAL),
                        new ItemStack(ItemRegistry.SKILL_SUMMARY_II),new ItemStack(ItemRegistry.STRATEGIC_BATTLE_RECORD),
                        new ItemStack(ItemRegistry.ORUNDUM),new ItemStack(ItemRegistry.ORIGINIUM_SHARD),
                        new ItemStack(ItemRegistry.CASTER_CHIP),new ItemStack(ItemRegistry.DEFENDER_CHIP),
                        new ItemStack(ItemRegistry.GUARD_CHIP),new ItemStack(ItemRegistry.MEDIC_CHIP),
                        new ItemStack(ItemRegistry.SNIPER_CHIP),new ItemStack(ItemRegistry.SPECIALIST_CHIP),
                        new ItemStack(ItemRegistry.SUPPORTER_CHIP),new ItemStack(ItemRegistry.VANGUARD_CHIP),
                        new ItemStack(ItemRegistry.EMERGENCY_SANITY_POTION),new ItemStack(ItemRegistry.CHEESE),
                        new ItemStack(ItemRegistry.AMIYA_CARD)));
                four = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.ORIROCK_CUBE),
                        new ItemStack(ItemRegistry.DEVICE),new ItemStack(ItemRegistry.POLYESTER),new ItemStack(ItemRegistry.PURESTREAM_CARD),
                        new ItemStack(ItemRegistry.SUGAR),new ItemStack(ItemRegistry.ORIRON),new ItemStack(ItemRegistry.LMB,2),
                        new ItemStack(ItemRegistry.POLYKETON),new ItemStack(ItemRegistry.CARBON),new ItemStack(ItemRegistry.WEAPON_FIX_GEN,2),
                        new ItemStack(ItemRegistry.LIGHT_BUILDING_MATERIAL),new ItemStack(ItemRegistry.SKILL_SUMMARY_I),
                        new ItemStack(ItemRegistry.FRONTLINE_BATTLE_RECORD),new ItemStack(ItemRegistry.COIN,2),
                        new ItemStack(ItemRegistry.WEAPON_GEM,2),new ItemStack(ItemRegistry.EMERGENCY_SANITY_SAMPLER)));
                three = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.ORIROCK),
                        new ItemStack(ItemRegistry.DAMAGED_DEVICE),new ItemStack(ItemRegistry.ESTER),
                        new ItemStack(ItemRegistry.SUGAR_SUBSTITUTE),new ItemStack(ItemRegistry.ORIRON_SHARD),
                        new ItemStack(ItemRegistry.DIKETON),new ItemStack(ItemRegistry.DRILL_BATTLE_RECORD),
                        new ItemStack(ItemRegistry.LMB),new ItemStack(ItemRegistry.WEAPON_GEM),new ItemStack(ItemRegistry.COIN)));
            }

            int chance = new Random().nextInt(100)+1;

            if(stack.getItem() instanceof HeadhuntTen)
            {
                for(int i=0; i<10; i++)
                {
                    if (chance <= 2)
                    {
                        if (six.size() > 0) player.inventory.add(-1, six.get(new Random().nextInt(six.size())));
                    }
                    else if (chance <= 10)
                    {
                        if (five.size() > 0) player.inventory.add(-1, five.get(new Random().nextInt(five.size())));
                    }
                    else if (chance <= 60)
                    {
                        if (four.size() > 0) player.inventory.add(-1, four.get(new Random().nextInt(four.size())));
                    }
                    else
                    {
                        if(three.size() > 0) player.inventory.add(-1, three.get(new Random().nextInt(three.size())));
                    }
                }
                stack.shrink(1);
            }
            else
            {
                if (chance <= 2)
                {
                    if (six.size() > 0) player.inventory.add(-1, six.get(new Random().nextInt(six.size())));
                }
                else if (chance <= 10)
                {
                    if (five.size() > 0) player.inventory.add(-1, five.get(new Random().nextInt(five.size())));
                }
                else if (chance <= 60)
                {
                    if (four.size() > 0) player.inventory.add(-1, four.get(new Random().nextInt(four.size())));
                }
                else
                {
                    if(three.size() > 0) player.inventory.add(-1, three.get(new Random().nextInt(three.size())));
                }

                stack.shrink(1);
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
        return new TileOperatorBox();
    }

    public static class TileOperatorBox extends ArkTile
    {
        public TileOperatorBox()
        {
            super("operator_box");
        }

        @Override
        public ResourceLocation getModelLocation()
        {
            return ArkResUtil.geo("normal_block");
        }
    }
}
