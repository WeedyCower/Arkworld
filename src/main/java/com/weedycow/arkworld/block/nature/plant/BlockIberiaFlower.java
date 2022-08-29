package com.weedycow.arkworld.block.nature.plant;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.ArkTile;
import com.weedycow.arkworld.item.tool.MeleeWeapon;
import com.weedycow.arkworld.registry.BlockRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

public class BlockIberiaFlower extends BlockBush implements ITileEntityProvider, IGrowable
{
    private static final IProperty<EnumFacing> FACING = PropertyDirection.create("facing",EnumFacing.Plane.HORIZONTAL);

    public BlockIberiaFlower()
    {
        super(Material.PLANTS);
        this.setUnlocalizedName(Arkworld.MODID + ".iberiaFlower");
        this.setCreativeTab(ArkCreateTab.BLOCK);
        this.setRegistryName("iberia_flower");
        this.setSoundType(SoundType.PLANT);
        this.setHardness(0);
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(FACING,EnumFacing.NORTH));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("item.arkworld.info.breed"));

        tooltip.add(I18n.format("item.arkworld.info.sword"));
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (!worldIn.isRemote && (stack.getItem() instanceof ItemSword || stack.getItem() instanceof MeleeWeapon))
        {
            spawnAsEntity(worldIn, pos, new ItemStack(this));
        }
        else
        {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemRegistry.IBERIA_STEM,new Random().nextInt(1)+1));
            spawnAsEntity(worldIn, pos, new ItemStack(ItemRegistry.IBERIA_PETAL,new Random().nextInt(1)+1));
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(worldIn,pos,state,rand);

        if (!worldIn.isAreaLoaded(pos, 1)) return;

        if(rand.nextInt(8)==0)
            grow(worldIn,rand,pos.add(rand.nextInt(3)-2,0,rand.nextInt(3)-2),state);
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this,FACING);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING,EnumFacing.getHorizontal(meta));
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING,placer.getHorizontalFacing().getOpposite());
    }

    @Override
    protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() == BlockRegistry.IBERIA_DIRT || state.getBlock() == BlockRegistry.FERTILE_DIRT;
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
        return  new AxisAlignedBB(0, 0, 0, 1, 1.4, 1);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return (double)rand.nextFloat() < 0.4D;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        this.generate(worldIn,pos);
    }

    public boolean canGrow(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).getBlock() == BlockRegistry.FERTILE_DIRT && worldIn.getBlockState(pos).getBlock()== Blocks.AIR;
    }

    public void generate(World worldIn, BlockPos pos)
    {
        worldIn.setBlockToAir(pos);
        if(canGrow(worldIn,pos))
            worldIn.setBlockState(pos,getDefaultState(),3);
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileIberiaFlower();
    }

    public static class TileIberiaFlower extends ArkTile
    {
        public TileIberiaFlower()
        {
            super("iberia_flower");
        }

        AnimationController<TileIberiaFlower> controllerIdle = new AnimationController<>(this, "idle", 1, this::PlayState);

        private <E extends TileEntity & IAnimatable> PlayState PlayState(AnimationEvent<E> event)
        {
            controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.iberia_flower.idle", true));
            return PlayState.CONTINUE;
        }

        @Override
        public void registerControllers(AnimationData data)
        {
            data.addAnimationController(controllerIdle);
        }
    }
}
