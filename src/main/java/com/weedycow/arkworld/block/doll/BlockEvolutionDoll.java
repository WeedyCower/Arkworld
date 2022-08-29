package com.weedycow.arkworld.block.doll;

import com.weedycow.arkworld.block.ArkTile;
import com.weedycow.arkworld.entity.enemy.split.boss.Evolution;
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

public class BlockEvolutionDoll extends BlockDoll
{
    public BlockEvolutionDoll()
    {
        super("evolution_doll","evolutionDoll");
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0, 0, 0, 0, 0, 0).union(new AxisAlignedBB(-2, 0, -2, 2, 6, 2));
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEvolutionDoll();
    }

    public class TileEvolutionDoll extends ArkTile
    {
        AnimationController<TileEvolutionDoll> controller = new AnimationController<>(this, "controller", 1, this::PlayState);

        public TileEvolutionDoll()
        {
            super(Evolution.ID);
        }

        private <E extends TileEntity & IAnimatable> PlayState PlayState(AnimationEvent<E> event)
        {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.evolution.default", true));
            return PlayState.CONTINUE;
        }

        @Override
        public void registerControllers(AnimationData data) {data.addAnimationController(controller);}

        @Override
        public ResourceLocation getTextureLocation()
        {
            return ArkResUtil.textureEntities(id+"_first");
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
