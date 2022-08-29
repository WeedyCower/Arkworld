package com.weedycow.arkworld.entity.animal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
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

public class Rhino extends ArkAnimal implements IRangedAttackMob
{
    public static final String ID = "rhino";
    public static final String NAME = Arkworld.MODID + ".rhino";

    public Rhino(World worldIn)
    {
        super(worldIn,ID);
        setSize(3f,2.5f);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackRanged(this,getMoveSpeed(),3600,animal.rhino.attackRange-1));
        this.tasks.addTask(2, new EntityAIMate(this, getMoveSpeed()));
        this.tasks.addTask(3, new EntityAITempt(this, getMoveSpeed()*1.25f, Item.getItemFromBlock(Blocks.HAY_BLOCK), false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, getMoveSpeed()*1.125f));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, getMoveSpeed()));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, getMoveSpeed()));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(animal.rhino.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(animal.rhino.moveSpeed);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(animal.rhino.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(animal.rhino.attackDamage);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(animal.rhino.attackRange);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),6,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.rhino.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.rhino.move", true));
        }
        else
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.rhino.idle", true));
        }

        return PlayState.CONTINUE;
    }

    AnimationController<Rhino> controllerAttack = new AnimationController<>(this,"controllerAttack",2,this::PlayState);
    AnimationController<Rhino> controllerMove = new AnimationController<>(this,"controllerMove",2,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
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
        return stack.getItem() == Item.getItemFromBlock(Blocks.HAY_BLOCK);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(@Nonnull EntityAgeable ageable)
    {
        return new Rhino(world);
    }

    public float getEyeHeight()
    {
        return this.height*0.6f;
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
