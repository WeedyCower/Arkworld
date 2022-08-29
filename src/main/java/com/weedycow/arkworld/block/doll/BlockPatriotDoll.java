package com.weedycow.arkworld.block.doll;

import com.weedycow.arkworld.block.ArkTile;
import com.weedycow.arkworld.entity.enemy.union.boss.Patriot;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
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
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockPatriotDoll extends BlockDoll
{
    public BlockPatriotDoll()
    {
        super("patriot_doll","patriotDoll");
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0, 0, 0, 0, 0, 0).union(new AxisAlignedBB(0, 0, 0, 2, 4, 2));
    }

    @Override
    @ParametersAreNonnullByDefault
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TilePatriotDoll();
    }

    public static class TilePatriotDoll extends ArkTile
    {
        AnimationController<TilePatriotDoll> controllerAttack = new AnimationController<>(this, "attack", 1, this::PlayState);
        AnimationController<TilePatriotDoll> controllerIdle = new AnimationController<>(this, "idle", 1, this::PlayState);

        public TilePatriotDoll()
        {
            super(Patriot.ID);
        }

        private <E extends TileEntity & IAnimatable> PlayState PlayState(AnimationEvent<E> event)
        {
            if(event.getAnimatable().getWorld().isAirBlock(event.getAnimatable().getPos().down().down()))
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.patriot_first.attack", false));
                controllerAttack.markNeedsReload();
            }
            else controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.patriot_first.idle", true));
            return PlayState.CONTINUE;
        }

        @Override
        public void registerControllers(AnimationData data)
        {
            data.addAnimationController(controllerAttack);
            data.addAnimationController(controllerIdle);
        }

        @Override
        public ResourceLocation getTextureLocation()
        {
            return ArkResUtil.textureEntities(id);
        }

        @Override
        public ResourceLocation getModelLocation()
        {
            return ArkResUtil.geo(id+"_first");
        }

        @Override
        public ResourceLocation getAnimationLocation()
        {
            return ArkResUtil.animation(id+"_first");
        }
    }
}
