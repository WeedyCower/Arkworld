package com.weedycow.arkworld.block.doll;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.ArkMaterial;
import com.weedycow.arkworld.item.block.doll.*;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public abstract class BlockDoll extends Block implements ITileEntityProvider
{
    private static final IProperty<EnumFacing> FACING = PropertyDirection.create("facing",EnumFacing.Plane.HORIZONTAL);

    public BlockDoll(String registryName, String unlocalizedName)
    {
        super(ArkMaterial.DOLL);
        this.setUnlocalizedName(Arkworld.MODID + "." + unlocalizedName);
        this.setCreativeTab(ArkCreateTab.DOLL);
        this.setRegistryName(registryName);
        this.setHarvestLevel("pickaxe",1);
        this.setSoundType(SoundType.STONE);
        this.setLightLevel(0.3F);
        this.setHardness(6.0f);
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(FACING,EnumFacing.NORTH));
    }

    @Nonnull
    @Override
    public EnumBlockRenderType getRenderType(@Nonnull IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
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

    public static void onRightClick(PlayerInteractEvent.RightClickEmpty event)
    {
        ItemStack main = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
        ItemStack off = event.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND);

        if(main.getItem() instanceof ItemFaustDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemFaustDoll) main.getItem()).factory, main, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.faust.attack", false));
            controller.markNeedsReload();
        }
        else if(off.getItem() instanceof ItemFaustDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemFaustDoll) off.getItem()).factory, off, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.faust.attack", false));
            controller.markNeedsReload();
        }

        if(main.getItem() instanceof ItemFrostnovaDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemFrostnovaDoll) main.getItem()).factory, main, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.frostnova.attack", false));
            controller.markNeedsReload();
        }
        else if(off.getItem() instanceof ItemFrostnovaDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemFrostnovaDoll) off.getItem()).factory, off, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.frostnova.attack", false));
            controller.markNeedsReload();
        }

        if(main.getItem() instanceof ItemMayfestDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemMayfestDoll) main.getItem()).factory, main, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.mayfest.boom", false));
            controller.markNeedsReload();
        }
        else if(off.getItem() instanceof ItemMayfestDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemMayfestDoll) off.getItem()).factory, off, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.mayfest.boom", false));
            controller.markNeedsReload();
        }

        if(main.getItem() instanceof ItemPatriotDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemPatriotDoll) main.getItem()).factory, main, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.patriot_first.attack", false));
            controller.markNeedsReload();
        }
        else if(off.getItem() instanceof ItemPatriotDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemPatriotDoll) off.getItem()).factory, off, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.patriot_first.attack", false));
            controller.markNeedsReload();
        }

        if(main.getItem() instanceof ItemRegicideDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemRegicideDoll) main.getItem()).factory, main, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.regicide.attack", false));
            controller.markNeedsReload();
        }
        else if(off.getItem() instanceof ItemRegicideDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemRegicideDoll) off.getItem()).factory, off, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.regicide.attack", false));
            controller.markNeedsReload();
        }

        if(main.getItem() instanceof ItemSkullshattererDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemSkullshattererDoll) main.getItem()).factory, main, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.skullshatterer_range.attack", false));
            controller.markNeedsReload();
        }
        else if(off.getItem() instanceof ItemSkullshattererDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemSkullshattererDoll) off.getItem()).factory, off, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.skullshatterer_range.attack", false));
            controller.markNeedsReload();
        }

        if(main.getItem() instanceof ItemBlackSnakeDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemBlackSnakeDoll) main.getItem()).factory, main, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake.attack", false));
            controller.markNeedsReload();
        }
        else if(off.getItem() instanceof ItemBlackSnakeDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemBlackSnakeDoll) off.getItem()).factory, off, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake.attack", false));
            controller.markNeedsReload();
        }

        if(main.getItem() instanceof ItemWDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemWDoll) main.getItem()).factory, main, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.w.boom", false));
            controller.markNeedsReload();
        }
        else if(off.getItem() instanceof ItemWDoll)
        {
            AnimationController controller = GeckoLibUtil.getControllerForStack(((ItemWDoll) off.getItem()).factory, off, "attack");
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.w.boom", false));
            controller.markNeedsReload();
        }
    }
}
