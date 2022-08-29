package com.weedycow.arkworld.block.doll;

import com.weedycow.arkworld.block.ArkTile;
import com.weedycow.arkworld.entity.enemy.union.boss.Faust;
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
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockFaustDoll extends BlockDoll
{
    public BlockFaustDoll()
    {
        super("faust_doll","faustDoll");
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0, 0, 0, 0, 0, 0).union(new AxisAlignedBB(0, 0, 0, 1, 2, 1));
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileFaustDoll();
    }

    public static class TileFaustDoll extends ArkTile
    {
        AnimationController<TileFaustDoll> controllerAttack = new AnimationController<>(this, "attack", 1, this::PlayState);
        AnimationController<TileFaustDoll> controllerIdle = new AnimationController<>(this, "idle", 1, this::PlayState);

        public TileFaustDoll()
        {
            super(Faust.ID);
        }

        private <E extends TileEntity & IAnimatable> PlayState PlayState(AnimationEvent<E> event)
        {
            if(event.getAnimatable().getWorld().isAirBlock(event.getAnimatable().getPos().down().down()))
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.faust.attack", false));
                controllerAttack.markNeedsReload();
            }
            else controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.faust.idle", true));
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
    }
}
