package com.weedycow.arkworld.entity.enemy.other.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.Random;

public class Zergling extends ArkMob
{
    public static final String ID = "zergling";
    public static final String NAME = Arkworld.MODID + ".zergling";

    public Zergling(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.NORMAL, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(1.2f, 1.5f);
        experienceValue = other.springworm.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(other.springworm.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(other.springworm.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(other.springworm.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(other.springworm.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(other.springworm.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(other.springworm.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(other.springworm.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(other.springworm.attackDamage);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),10*getAttackInterval()/other.springworm.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.zergling.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.zergling.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.zergling.idle", true));

        controllerAttack.setAnimationSpeed((float)other.springworm.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/other.springworm.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<Zergling> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);
    AnimationController<Zergling> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(), DamageSource.causeMobDamage(this));
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundRegistry.ZERGLING_HURT;
    }

    @Override
    public void playLivingSound()
    {
        int i = new Random().nextInt(4);
        if(i==0)
            playSound(SoundRegistry.ZERGLING_LIVING_I,1,1);
        if(i==1)
            playSound(SoundRegistry.ZERGLING_LIVING_II,1,1);
        if(i==2)
            playSound(SoundRegistry.ZERGLING_LIVING_III,1,1);
        if(i==3)
            playSound(SoundRegistry.ZERGLING_LIVING_IV,1,1);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundRegistry.ZERGLING_DEATH;
    }
}
