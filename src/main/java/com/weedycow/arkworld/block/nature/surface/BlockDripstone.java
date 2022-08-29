package com.weedycow.arkworld.block.nature.surface;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.ArkTile;
import com.weedycow.arkworld.registry.BlockRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

public class BlockDripstone extends Block implements ITileEntityProvider, IGrowable
{
    public BlockDripstone()
    {
        super(Material.GROUND);
        this.setHardness(2f);
        this.setResistance(1f);
        this.setUnlocalizedName(Arkworld.MODID + ".dripstone");
        this.setCreativeTab(ArkCreateTab.BLOCK);
        this.setHarvestLevel("pickaxe",2);
        this.setRegistryName("dripstone");
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("item.arkworld.info.breed"));

        tooltip.add(I18n.format("item.arkworld.info.spade"));
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (!worldIn.isRemote && stack.getItem() instanceof ItemSpade)
        {
            spawnAsEntity(worldIn, pos, new ItemStack(this));
        }
        else
        {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemRegistry.DRIPSTONE_SHARD,new Random().nextInt(4)+3));
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(worldIn,pos,state,rand);

        if (!worldIn.isAreaLoaded(pos, 1)) return;

        if(rand.nextInt(9)==0)
            grow(worldIn,rand,pos.add(rand.nextInt(3)-2,0,rand.nextInt(3)-2),state);
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
    @Nonnull
    @ParametersAreNonnullByDefault
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0, 0, 0, 1, 2, 1);
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileDripstone();
    }

    public boolean canGrow(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).getBlock() == BlockRegistry.FERTILE_DIRT && worldIn.getBlockState(pos).getBlock()== Blocks.AIR;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return (double)rand.nextFloat() < 0.3D;
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

    public static class TileDripstone extends ArkTile
    {
        int s;

        public TileDripstone()
        {
            super("dripstone");
            s=new Random().nextInt(3) + 1;
        }

        public ResourceLocation getModelLocation()
        {
            return ArkResUtil.geo(id+"_"+s);
        }
    }
}
