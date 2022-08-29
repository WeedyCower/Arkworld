package com.weedycow.arkworld.entity.enemy.union.elite;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.registry.CapabilityRegistry;
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

public class YetiIcecleaver extends ArkMob
{
    public static final String ID = "yeti_icecleaver";
    public static final String NAME = Arkworld.MODID + ".yetiIcecleaver";

    public YetiIcecleaver(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.ELITE, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(0.8f, 2.5f);
        experienceValue = union.yetiIcecleaver.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.yetiIcecleaver.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.yetiIcecleaver.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.yetiIcecleaver.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.yetiIcecleaver.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.yetiIcecleaver.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.yetiIcecleaver.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.yetiIcecleaver.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.yetiIcecleaver.attackDamage);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),10*getAttackInterval()/union.yetiIcecleaver.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.icecleaver.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.icecleaver.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.icecleaver.idle", true));

        controllerAttack.setAnimationSpeed((float)union.yetiIcecleaver.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.yetiIcecleaver.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<YetiIcecleaver> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);
    AnimationController<YetiIcecleaver> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data)
    {
        setType(rand.nextInt(2));
        addHP(getType()*union.yetiIcecleaver.leaderMaxHealth);
        addAD(getType()*union.yetiIcecleaver.leaderAttackDamage);
        addDP(getType()*union.yetiIcecleaver.leaderDefensivePower);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(target.hasCapability(CapabilityRegistry.capState,null) && wlm.getCapability(CapabilityRegistry.capState,null).getStates().contains(EnumState.COLD))
        {
            if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
                EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage()*3,DamageSource.causeMobDamage(this));
        }
        else
        {
            if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
                EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
        }
    }
}

