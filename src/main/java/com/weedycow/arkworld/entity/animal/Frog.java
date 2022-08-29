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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Frog extends ArkAnimal
{
    int jumpAdd;
    public static final String ID = "frog";
    public static final String NAME = Arkworld.MODID + ".frog";

    public Frog(World worldIn)
    {
        super(worldIn,ID);
        setSize(0.7f,0.4f);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, getMoveSpeed()));
        this.tasks.addTask(2, new EntityAIMate(this, getMoveSpeed()));
        this.tasks.addTask(3, new EntityAITempt(this, getMoveSpeed(),Items.SLIME_BALL, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, getMoveSpeed()));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, getMoveSpeed()));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, getMoveSpeed()));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(animal.frog.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(animal.frog.moveSpeed);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(motionX>0 || motionZ>0)
        {
            if(EntityUtil.atSetIntervals(TICK,this,30,0,0))
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.frog.move", false));
                controllerMove.markNeedsReload();
        }
        else
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.frog.idle", true));
        }

        if(!this.isChild())
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.frog.scale", true));

        return PlayState.CONTINUE;
    }

    AnimationController<Frog> controllerAttack = new AnimationController<>(this,"controllerAttack",2,this::PlayState);
    AnimationController<Frog> controllerMove = new AnimationController<>(this,"controllerMove",2,this::PlayState);
    AnimationController<Frog> controllerIdle = new AnimationController<>(this,"controllerIdle",2,this::PlayState);

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
                jumpAdd=1;
            }
            else
            {
                jumpAdd=0;
            }
        }

        if(EntityUtil.atSetIntervals(TICK,this,30,0,0) && (motionX>0 || motionZ>0)) jump();
    }

    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == Items.SLIME_BALL;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(@Nonnull EntityAgeable ageable)
    {
        return new Skunk(world);
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

    protected void jump()
    {
        super.jump();
        double d0 = this.moveHelper.getSpeed();

        if (d0 > 0.0D)
        {
            double d1 = this.motionX * this.motionX + this.motionZ * this.motionZ;

            if (d1 < 0.010000000000000002D)
            {
                this.moveRelative(0, jumpAdd, 1+jumpAdd, 0.1F);
            }
        }

        if (!this.world.isRemote)
        {
            this.world.setEntityState(this, (byte)1);
        }
    }
}
