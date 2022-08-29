package com.weedycow.arkworld.item.block.doll;

import com.weedycow.arkworld.registry.BlockRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class ItemBlackSnakeDoll extends ArkItemDoll
{
    AnimationController<ItemBlackSnakeDoll> controllerAttack = new AnimationController<>(this, "attack", 1, this::predicate);
    AnimationController<ItemBlackSnakeDoll> controllerIdle = new AnimationController<>(this, "idle", 1, this::predicate);

    public ItemBlackSnakeDoll()
    {
        super(BlockRegistry.TALULAH_DOLL);
    }

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event)
    {
        controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData)
    {
        animationData.addAnimationController(controllerIdle);
        animationData.addAnimationController(controllerAttack);
    }

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
