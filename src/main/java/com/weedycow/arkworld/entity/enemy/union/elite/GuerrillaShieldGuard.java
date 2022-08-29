package com.weedycow.arkworld.entity.enemy.union.elite;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.util.ArkEntityUtil;
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

public class GuerrillaShieldGuard extends ArkMob
{
    public static final String ID = "guerrilla_shield_guard";
    public static final String NAME = Arkworld.MODID + ".guerrillaShieldGuard";

    public GuerrillaShieldGuard(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.ELITE, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(1.3f, 2.5f);
        experienceValue = union.shieldSoldier.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.guerrillaShieldGuard.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.guerrillaShieldGuard.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.guerrillaShieldGuard.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.guerrillaShieldGuard.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.guerrillaShieldGuard.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.guerrillaShieldGuard.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.guerrillaShieldGuard.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.guerrillaShieldGuard.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),10*getAttackInterval()/union.guerrillaShieldGuard.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.shieldguard.attack", false));
            controllerAttack.markNeedsReload();
        }
        else if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.shieldguard.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.shieldguard.idle", true));

        controllerAttack.setAnimationSpeed((float)union.guerrillaShieldGuard.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.guerrillaShieldGuard.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<GuerrillaShieldGuard> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);
    AnimationController<GuerrillaShieldGuard> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);

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
        addHP(getType()*union.guerrillaShieldGuard.leaderMaxHealth);
        addAD(getType()*union.guerrillaShieldGuard.leaderAttackDamage);
        addDP(getType()*union.guerrillaShieldGuard.leaderDefensivePower);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void commonExecute(WLM wlm, EntityLivingBase target)
    {
        if(union.guerrillaShieldGuard.canTaunt)
            ArkEntityUtil.taunt(this,union.guerrillaShieldGuard.tauntRange);
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
    }
}
