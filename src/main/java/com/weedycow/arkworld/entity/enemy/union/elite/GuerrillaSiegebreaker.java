package com.weedycow.arkworld.entity.enemy.union.elite;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class GuerrillaSiegebreaker extends ArkMob
{
    public static final String ID = "guerrilla_siegebreaker";
    public static final String NAME = Arkworld.MODID + ".guerrillaSiegebreaker";

    public GuerrillaSiegebreaker(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.ELITE, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(1, 2);
        experienceValue = union.guerrillaSiegebreaker.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.guerrillaSiegebreaker.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.guerrillaSiegebreaker.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.guerrillaSiegebreaker.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.guerrillaSiegebreaker.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.guerrillaSiegebreaker.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.guerrillaSiegebreaker.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.guerrillaSiegebreaker.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.guerrillaSiegebreaker.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(getCountdown()>0)
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.guerrilla_siegebreaker.fall", false));
        }
        if (EntityUtil.atSetIntervals(TICK,this, getAttackInterval(), 19*getAttackInterval()/union.guerrillaSiegebreaker.attackInterval, 0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.guerrilla_siegebreaker.attack", false));
            controllerAttack.markNeedsReload();
        }
        if (event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.guerrilla_siegebreaker.move", true));
        } else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.guerrilla_siegebreaker.idle", true));

        controllerAttack.setAnimationSpeed((float)union.guerrillaSiegebreaker.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.guerrillaSiegebreaker.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<GuerrillaSiegebreaker> controllerIdle = new AnimationController<>(this, "controllerIdle", 1, this::PlayState);
    AnimationController<GuerrillaSiegebreaker> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);
    AnimationController<GuerrillaSiegebreaker> controllerAttack = new AnimationController<>(this, "controllerAttack", 2, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerIdle);
        data.addAnimationController(controllerMove);
        data.addAnimationController(controllerAttack);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(getCountdown() >0)
        {
            setCountdown(getCountdown() - 1);
            motionX = 0; motionZ = 0;
        }
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data)
    {
        setType(rand.nextInt(2));
        setCountdown(20);
        addHP(getType()*union.guerrillaSiegebreaker.leaderMaxHealth);
        addAD(getType()*union.guerrillaSiegebreaker.leaderAttackDamage);
        addDP(getType()*union.guerrillaSiegebreaker.leaderDefensivePower);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
    }
}