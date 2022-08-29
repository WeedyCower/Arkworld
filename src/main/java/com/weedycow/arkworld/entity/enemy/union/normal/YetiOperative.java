package com.weedycow.arkworld.entity.enemy.union.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class YetiOperative extends ArkMob
{
    public static final String ID = "yeti_operative";
    public static final String NAME = Arkworld.MODID + ".yetiOperative";

    public YetiOperative(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.NORMAL, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(0.8f, 2f);
        experienceValue = union.yetiOperative.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.yetiOperative.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.yetiOperative.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.yetiOperative.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.yetiOperative.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.yetiOperative.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.yetiOperative.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.yetiOperative.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.yetiOperative.attackDamage);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),10*getAttackInterval()/union.yetiOperative.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.yeti_operative.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.yeti_operative.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.yeti_operative.idle", true));

        controllerAttack.setAnimationSpeed((float)union.yetiOperative.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.yetiOperative.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<YetiOperative> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);
    AnimationController<YetiOperative> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(target.hasCapability(CapabilityRegistry.capState,null) && wlm.getCapability(CapabilityRegistry.capState,null).getStates().contains(EnumState.COLD))
        {
            if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
                EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage()*1.5f,DamageSource.causeMobDamage(this));
        }
        else
        {
            if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
                EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
        }
    }
}