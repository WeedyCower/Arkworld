package com.weedycow.arkworld.entity.animal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.util.ArkResUtil;
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

public class Skunk extends ArkAnimal
{
    public static final String ID = "skunk";
    public static final String NAME = Arkworld.MODID + ".skunk";

    public Skunk(World worldIn)
    {
        super(worldIn,ID);
        setSize(0.7f,0.3f);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, getMoveSpeed()));
        this.tasks.addTask(2, new EntityAIMate(this, getMoveSpeed()));
        this.tasks.addTask(3, new EntityAITempt(this, getMoveSpeed()*1.25f, Items.CHICKEN, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, getMoveSpeed()*1.125f));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, getMoveSpeed()));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, getMoveSpeed()));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(animal.skunk.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(animal.skunk.moveSpeed);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.skunk.move", true));
        }
        else
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.skunk.idle", true));
        }

        if(this.isChild())
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.skunk.scale", true));

        return PlayState.CONTINUE;
    }

    AnimationController<Skunk> controllerAttack = new AnimationController<>(this,"controllerAttack",2,this::PlayState);
    AnimationController<Skunk> controllerMove = new AnimationController<>(this,"controllerMove",2,this::PlayState);
    AnimationController<Skunk> controllerIdle = new AnimationController<>(this,"controllerIdle",2,this::PlayState);

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
                addMS(0.25f);
                applyMoveSpeedModifier();
            }
            else
            {
                addMS(0);
                removeMoveSpeedModifier();
            }
        }
    }

    public float getEyeHeight()
    {
        return this.height;
    }

    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == Items.CHICKEN;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(@Nonnull EntityAgeable ageable)
    {
        return new Skunk(world);
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
