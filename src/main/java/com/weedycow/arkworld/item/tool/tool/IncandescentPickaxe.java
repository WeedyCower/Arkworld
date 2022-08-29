package com.weedycow.arkworld.item.tool.tool;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.world.BlockEvent;

public class IncandescentPickaxe extends ItemPickaxe
{
    public static final Item.ToolMaterial MATERIAL = EnumHelper.addToolMaterial("INCANDESCENT", 3, 1000, 10, 4, 8);

    public IncandescentPickaxe()
    {
        super(MATERIAL);
        this.setUnlocalizedName(Arkworld.MODID + ".incandescentPickaxe");
        this.setCreativeTab(ArkCreateTab.TOLL);
        this.setRegistryName("incandescent_pickaxe");
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }

    public static void onUse(BlockEvent.BreakEvent event)
    {
        BlockPos pos = event.getPos();
        World world = event.getWorld();
        EntityPlayer player = event.getPlayer();

        if(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IncandescentPickaxe)
        {
            if(world.getBlockState(pos.up()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.up(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.up()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.up()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.down()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.down(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.down()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.down()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.west()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.west(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.west()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.west()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.east()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.east(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.east()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.east()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.north()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.north(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.north()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.north()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.south()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.south(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.south()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.south()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.west().north()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.west().north(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.west().north()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.west().north()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.east().north()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.east().north(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.east().north()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.east().north()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.west().south()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.west().south(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.west().south()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.west().south()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.east().south()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.east().south(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.east().south()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.east().south()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.up().east()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.up().east(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.up().east()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.up().east()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.up().west()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.up().west(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.up().west()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.up().west()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.up().east().south()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.up().east().south(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.up().east().south()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.up().east().south()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.up().west().south()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.up().west().south(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.up().west().south()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.up().west().south()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.up().east().north()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.up().east().north(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.up().east().north()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.up().east().north()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.up().west().north()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.up().west().north(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.up().west().north()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.up().west().north()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.down().east()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.down().east(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.down().east()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.down().east()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.down().west()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.down().west(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.down().west()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.down().west()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.down().east().south()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.down().east().south(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.down().east().south()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.down().east().south()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.down().west().south()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.down().west().south(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.down().west().south()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.down().west().south()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.down().east().north()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.down().east().north(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.down().east().north()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.down().east().north()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.down().west().north()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.down().west().north(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.down().west().north()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.down().west().north()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.north().up()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.north().up(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.north().up()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.north().up()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.south().up()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.south().up(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.south().up()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.south().up()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.north().down()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.north().down(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.north().down()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.north().down()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
            if(world.getBlockState(pos.south().down()).getBlock() == Blocks.STONE)
            {
                world.setBlockState(pos.south().down(),Blocks.AIR.getDefaultState());
                world.getBlockState(pos.south().down()).getBlock().harvestBlock(world, player, pos, world.getBlockState(pos.south().down()), null, player.getHeldItem(EnumHand.MAIN_HAND));
            }
        }
    }
}
