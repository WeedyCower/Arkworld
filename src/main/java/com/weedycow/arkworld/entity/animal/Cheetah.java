package com.weedycow.arkworld.entity.animal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
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

import static com.weedycow.weedylib.util.EntityUtil.distanceToTarget;

public class Cheetah extends ArkAnimal implements IRangedAttackMob
{
    public static final String ID = "cheetah";
    public static final String NAME = Arkworld.MODID + ".cheetah";

    public Cheetah(World worldIn)
    {
        super(worldIn,ID);
        setSize(1.5f,1.2f);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackRanged(this,getMoveSpeed(),3600, animal.cheetah.attackRange-1));
        this.tasks.addTask(2, new EntityAIMate(this, getMoveSpeed()));
        this.tasks.addTask(3, new EntityAITempt(this, getMoveSpeed()*1.25f, Items.PORKCHOP, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, getMoveSpeed()*1.125f));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, getMoveSpeed()));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, getMoveSpeed()));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(animal.cheetah.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(animal.cheetah.moveSpeed);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(animal.cheetah.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(animal.cheetah.attackDamage);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(animal.cheetah.attackRange);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),10,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.cheetah.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.cheetah.move", true));
        }
        else
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.cheetah.idle", true));
        }

        if(this.isChild())
            controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.cheetah.scale", true));

        return PlayState.CONTINUE;
    }

    AnimationController<Cheetah> controllerAttack = new AnimationController<>(this,"controllerAttack",2,this::PlayState);
    AnimationController<Cheetah> controllerMove = new AnimationController<>(this,"controllerMove",2,this::PlayState);
    AnimationController<Cheetah> controllerIdle = new AnimationController<>(this,"controllerIdle",2,this::PlayState);

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

        if(isNormalGetAttackTarget()) EntityUtil.setTargetState(this,TARGET);

        if(isNormalGetAttackState()) EntityUtil.setAttackState(this,ATTACK,getAttackRange());

        if(!world.isRemote)setDistance((float) distanceToTarget(this));

        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),0,0) && getAttackState())
            EntityUtil.attackMeleeAOE(this,null, DamageSource.causeMobDamage(this),getAttackRange(),getAttackDamage(),true);
    }

    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == Items.PORKCHOP;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(@Nonnull EntityAgeable ageable)
    {
        return new Cheetah(world);
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureEntities(id);
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

    @Override
    public void attackEntityWithRangedAttack(@Nonnull EntityLivingBase target, float distanceFactor)
    {

    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {

    }
}
