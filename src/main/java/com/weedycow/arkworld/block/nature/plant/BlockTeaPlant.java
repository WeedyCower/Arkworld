package com.weedycow.arkworld.block.nature.plant;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.registry.BlockRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockTeaPlant extends BlockBush implements IGrowable
{
    public BlockTeaPlant()
    {
        super(Material.PLANTS);
        this.setUnlocalizedName(Arkworld.MODID + ".teaPlant");
        this.setCreativeTab(ArkCreateTab.BLOCK);
        this.setRegistryName("tea_plant");
        this.setSoundType(SoundType.PLANT);
        this.setHardness(0);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("item.arkworld.info.breed"));

        tooltip.add(I18n.format("item.arkworld.info.shears"));
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(worldIn,pos,state,rand);

        if (!worldIn.isAreaLoaded(pos, 1)) return;

        if(rand.nextInt(7)==0)
            grow(worldIn,rand,pos.add(rand.nextInt(3)-2,0,rand.nextInt(3)-2),state);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
        {
            spawnAsEntity(worldIn, pos, new ItemStack(this));
        }
        else
        {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemRegistry.TEA,new Random().nextInt(2)+1));
        }
    }

    public boolean canGrow(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).getBlock() == BlockRegistry.FERTILE_DIRT && worldIn.getBlockState(pos).getBlock()== Blocks.AIR;
    }

    @Override
    protected boolean canSustainBush(IBlockState state)
    {
        return (state.getMaterial()==Material.GRASS || state.getMaterial()==Material.GOURD) && state.getBlock()!= BlockRegistry.IBERIA_DIRT && state.getBlock()!= BlockRegistry.GRASS_WITHERED && state.getBlock()!= BlockRegistry.DERELICT_DIRT;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return this.canSustainBush(worldIn.getBlockState(pos.down()));
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos)
    {
        return super.getBoundingBox(state, source, pos).offset(state.getOffset(source, pos));
    }

    @Nonnull
    @Override
    public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.XZ;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return (double)rand.nextFloat() < 0.5D;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        this.generate(worldIn,pos);
    }

    public void generate(World worldIn, BlockPos pos)
    {
        worldIn.setBlockToAir(pos);
        if(canGrow(worldIn,pos))
            worldIn.setBlockState(pos,getDefaultState(),3);
    }
}
