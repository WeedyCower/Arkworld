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

public class HeavyDefender extends ArkMob
{
    public static final String ID = "heavy_defender";
    public static final String NAME = Arkworld.MODID + ".heavyDefender";

    public HeavyDefender(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.ELITE, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(1.3f, 2.5f);
        experienceValue = union.heavyDefender.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.heavyDefender.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.heavyDefender.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.heavyDefender.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.heavyDefender.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.heavyDefender.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.heavyDefender.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.heavyDefender.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.heavyDefender.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),10*getAttackInterval()/union.heavyDefender.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.heavy_defender.attack", false));
            controllerAttack.markNeedsReload();
        }
        else if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.heavy_defender.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.heavy_defender.idle", true));

        controllerAttack.setAnimationSpeed((float)union.heavyDefender.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.heavyDefender.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<HeavyDefender> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);
    AnimationController<HeavyDefender> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);

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
        addHP(getType()*union.heavyDefender.leaderMaxHealth);
        addAD(getType()*union.heavyDefender.leaderAttackDamage);
        addDP(getType()*union.heavyDefender.leaderDefensivePower);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(), DamageSource.causeMobDamage(this));
    }
}
