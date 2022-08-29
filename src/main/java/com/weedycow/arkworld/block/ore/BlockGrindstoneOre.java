package com.weedycow.arkworld.block.ore;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockGrindstoneOre extends Block
{
    public BlockGrindstoneOre()
    {
        super(Material.ROCK, MapColor.STONE);
        this.setHardness(3f);
        this.setResistance(5f);
        this.setRegistryName("grindstone_ore");
        this.setSoundType(SoundType.STONE);
        this.setHarvestLevel("pickaxe", 1);
        this.setCreativeTab(ArkCreateTab.BLOCK);
        this.setUnlocalizedName(Arkworld.MODID + ".grindstoneOre");
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
    {
        return ItemRegistry.GRINDSTONE_SHARD;
    }

    @Override
    public int quantityDropped(@Nonnull Random random)
    {
        return  Math.max(random.nextInt(4),1);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, @Nonnull Random random)
    {
        if (fortune > 0)
        {
            int bonusFactor = Math.max(random.nextInt(fortune), 0);
            return this.quantityDropped(random) * bonusFactor;
        }
        else
        {
            return this.quantityDropped(random);
        }
    }

    @Override
    public int getExpDrop(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, int fortune)
    {
        Random random = world instanceof World ? ((World) world).rand : new Random();
        return MathHelper.getInt(random, 3, 7);
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(@Nonnull IBlockState state, @Nonnull RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player)
    {
        return new ItemStack(ItemRegistry.GRINDSTONE_ORE);
    }
}
