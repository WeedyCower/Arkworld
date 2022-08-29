package com.weedycow.arkworld.entity.enemy.ursus.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.entity.weapon.SimpleThrowable;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class UrsusArmoredCaster extends ArkMob
{
    public static final String ID = "ursus_armored_caster";
    public static final String NAME = Arkworld.MODID + ".ursusArmoredCaster";

    public UrsusArmoredCaster(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.URSUS, EnumStatus.NORMAL, EnumAttackMethod.RANGE, EnumDamageTypes.SPELL);
        this.setSize(0.8f, 2f);
        experienceValue = ursus.ursusArmoredCaster.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(ursus.ursusArmoredCaster.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(ursus.ursusArmoredCaster.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(ursus.ursusArmoredCaster.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(ursus.ursusArmoredCaster.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(ursus.ursusArmoredCaster.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(ursus.ursusArmoredCaster.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(ursus.ursusArmoredCaster.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(ursus.ursusArmoredCaster.attackDamage);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),10*getAttackInterval()/ursus.ursusArmoredCaster.attackInterval,0) && getTargetState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.ursus_armored_caster.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.ursus_armored_caster.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.ursus_armored_caster.idle", true));

        controllerAttack.setAnimationSpeed((float)ursus.ursusArmoredCaster.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/ursus.ursusArmoredCaster.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<UrsusArmoredCaster> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);
    AnimationController<UrsusArmoredCaster> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerMove);
        data.addAnimationController(controllerAttack);
    }

    @Override
    public void commonExecute(WLM wlm, EntityLivingBase target)
    {
        SimpleThrowable throwable = new SimpleThrowable(world,wlm,getAttackDamage(), SimpleThrowable.Type.MAGICAL_BALL);

        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackRange(wlm,throwable,null,getDistance()/6,3);
    }
}
