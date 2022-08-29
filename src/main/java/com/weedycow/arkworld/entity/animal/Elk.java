package com.weedycow.arkworld.entity.animal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class Elk extends ArkAnimal
{
    public static final String ID = "elk";
    public static final String NAME = Arkworld.MODID + ".elk";

    public Elk(World worldIn)
    {
        super(worldIn,ID);
        setSize(1.8f,1.8f);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, getMoveSpeed()));
        this.tasks.addTask(2, new EntityAIMate(this, getMoveSpeed()));
        this.tasks.addTask(3, new EntityAITempt(this, getMoveSpeed()*1.25f, Items.WHEAT, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, getMoveSpeed()*1.125f));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, getMoveSpeed()));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, getMoveSpeed()));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(animal.elk.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(animal.elk.moveSpeed);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(event.isMoving())
        {
            if(getMS()>0)
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.elk.run", true));
            else
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.elk.move", true));
        }
        else
        {
            if(EntityUtil.atSetIntervals(TICK,this,200,0,0))
            {
                if(new Random().nextInt(11)>5)
                {
                    controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.elk.idle2", false));
                    controllerIdle.markNeedsReload();
                }
                else
                {
                    controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.elk.idle3", false));
                    controllerIdle.markNeedsReload();
                }
            }
            else if(controllerIdle.getAnimationState()!= AnimationState.Running)
            {
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.elk.idle", true));
            }
        }

        if(this.isChild())
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.elk.scale", true));

        return PlayState.CONTINUE;
    }

    AnimationController<Elk> controllerAttack = new AnimationController<>(this,"controllerAttack",2,this::PlayState);
    AnimationController<Elk> controllerMove = new AnimationController<>(this,"controllerMove",2,this::PlayState);
    AnimationController<Elk> controllerIdle = new AnimationController<>(this,"controllerIdle",2,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
        data.addAnimationController(controllerIdle);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(!world.isRemote)
        {
            if (this.getRevengeTarget() != null || this.isBurning())
            {
                addMS(0.35f);
                applyMoveSpeedModifier();
            }
            else
            {
                addMS(0);
                removeMoveSpeedModifier();
            }
        }
    }

    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == Items.WHEAT;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(@Nonnull EntityAgeable ageable)
    {
        return new Elk(world);
    }

    public float getEyeHeight()
    {
        return this.height;
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo(id);
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation(id);
    }
}
