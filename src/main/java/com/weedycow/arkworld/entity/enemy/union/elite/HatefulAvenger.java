package com.weedycow.arkworld.entity.enemy.union.elite;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.arkworld.util.ParticleList;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class HatefulAvenger extends ArkMob
{
    public static final String ID = "hateful_avenger";
    public static final String NAME = Arkworld.MODID + ".hatefulAvenger";

    public HatefulAvenger(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.ELITE, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(0.8f, 2f);
        experienceValue = union.hatefulAvenger.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.hatefulAvenger.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.hatefulAvenger.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.hatefulAvenger.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.hatefulAvenger.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.hatefulAvenger.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.hatefulAvenger.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.hatefulAvenger.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.hatefulAvenger.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if (EntityUtil.atSetIntervals(TICK,this, getAttackInterval(), 10*getAttackInterval()/union.hatefulAvenger.attackInterval, 0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.avenger.attack", false));
            controllerAttack.markNeedsReload();
        }
        if (event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.avenger.move", true));
        } else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.avenger.idle", true));

        ParticleList.skullshattererParticle(this);

        controllerAttack.setAnimationSpeed((float)union.hatefulAvenger.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.hatefulAvenger.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<HatefulAvenger> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);
    AnimationController<HatefulAvenger> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(getHealth()/getMaxHealth()<=0.5)
        {
            if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
                EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage()*2.8f,DamageSource.causeMobDamage(this));
        }
        else
        {
            if (EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
                EntityUtil.attackMelee(wlm, getAttackRange(), getAttackDamage(), DamageSource.causeMobDamage(this));
        }
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("avenger");
    }

    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation("avenger");
    }
}
