package com.weedycow.arkworld.entity.enemy.union.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.entity.weapon.SimpleThrowable;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class Caster extends ArkMob
{
    public static final String ID = "caster";
    public static final String NAME = Arkworld.MODID + ".caster";

    public Caster(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.NORMAL, EnumAttackMethod.RANGE, EnumDamageTypes.SPELL);
        this.setSize(0.8f, 2f);
        experienceValue = union.caster.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.caster.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.caster.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.caster.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.caster.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.caster.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.caster.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.caster.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.caster.attackDamage);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),25*getAttackInterval()/union.caster.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.caster.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.caster.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.caster.idle", true));

        controllerAttack.setAnimationSpeed((float)union.caster.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.caster.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<Caster> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);
    AnimationController<Caster> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerMove);
        data.addAnimationController(controllerAttack);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data)
    {
        setType(rand.nextInt(2));
        addHP(getType()*union.caster.leaderMaxHealth);
        addAD(getType()*union.caster.leaderAttackDamage);
        addDP(getType()*union.caster.leaderDefensivePower);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void commonExecute(WLM wlm, EntityLivingBase target)
    {
        SimpleThrowable throwable = new SimpleThrowable(world,wlm,getAttackDamage(), SimpleThrowable.Type.MAGICAL_BALL);

        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackRange(wlm,throwable,null,getDistance()/6,3);
    }
}
