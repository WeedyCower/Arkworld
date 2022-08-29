package com.weedycow.arkworld.entity.enemy.other.elite;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.entity.weapon.Arrow;
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

public class Hydralisk extends ArkMob
{
    public static final String ID = "hydralisk";
    public static final String NAME = Arkworld.MODID + ".hydralisk";

    public Hydralisk(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.ELITE, EnumAttackMethod.RANGE, EnumDamageTypes.PHYSICAL);
        setSize(2f, 4f);
        experienceValue = other.hydralisk.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(other.hydralisk.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(other.hydralisk.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(other.hydralisk.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(other.hydralisk.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(other.hydralisk.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(other.hydralisk.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(other.hydralisk.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(other.hydralisk.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if (EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),7*getAttackInterval()/other.hydralisk.attackInterval,0) && getTargetState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.hydralisk.attack", false));
            controllerAttack.markNeedsReload();
        }
        if (event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.hydralisk.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.hydralisk.idle", true));

        controllerAttack.setAnimationSpeed((float)other.hydralisk.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/other.hydralisk.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<Hydralisk> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);
    AnimationController<Hydralisk> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerMove);
        data.addAnimationController(controllerAttack);
    }

    @Override
    public void commonExecute(WLM wlm, EntityLivingBase target)
    {
        Arrow arrow = new Arrow(world,wlm,getAttackDamage(), Arrow.Type.BONE_SPUR);
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackRange(wlm,null,arrow,getDistance()/6,3);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundRegistry.HYDRALISK_HURT;
    }

    @Override
    public void playLivingSound()
    {
        int i = new Random().nextInt(4);
        if(i==0)
            playSound(SoundRegistry.HYDRALISK_LIVING_I,1,1);
        if(i==1)
            playSound(SoundRegistry.HYDRALISK_LIVING_II,1,1);
        if(i==2)
            playSound(SoundRegistry.HYDRALISK_LIVING_III,1,1);
        if(i==3)
            playSound(SoundRegistry.HYDRALISK_LIVING_IV,1,1);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundRegistry.HYDRALISK_DEATH;
    }
}
