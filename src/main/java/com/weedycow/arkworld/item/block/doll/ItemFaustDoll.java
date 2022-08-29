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

public class ItemFaustDoll extends ArkItemDoll
{
    AnimationController<ItemFaustDoll> controllerAttack = new AnimationController<>(this, "attack", 1, this::predicate);
    AnimationController<ItemFaustDoll> controllerIdle = new AnimationController<>(this, "idle", 1, this::predicate);

    public ItemFaustDoll()
    {
        super(BlockRegistry.FAUST_DOLLL);
    }

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event)
    {
        controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.faust.idle", true));
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
        return ArkResUtil.textureEntities(id);
    }
}
