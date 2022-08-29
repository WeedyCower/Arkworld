package com.weedycow.arkworld.entity.enemy.union.boss;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkBoss;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.util.ParticleList;
import com.weedycow.weedylib.ai.WLEntityAIAttackBase;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.AoeRangeUtil;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class Mayfest extends ArkBoss
{
    public static final String ID = "mayfest";
    public static final String NAME = Arkworld.MODID + ".mayfest";

    public Mayfest(World worldIn)
    {
        super(worldIn, ID, NAME, BossInfo.Color.WHITE, EnumTypes.OTHER, EnumCamps.UNION, EnumAttackMethod.OTHER, EnumDamageTypes.HEALING);
        this.setSize(0.8f, 2f);
        this.experienceValue = union.mayfest.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.mayfest.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.mayfest.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.mayfest.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.mayfest.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.mayfest.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.mayfest.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.mayfest.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.mayfest.attackDamage);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new WLEntityAIAttackBase(this));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this,getMoveSpeed()));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, Faust.class,32.0f));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, Faust.class, true));
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),20*getAttackInterval()/union.mayfest.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.mayfest.healing", false));
            controllerAttack.markNeedsReload();
        }

        if (event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.mayfest.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.mayfest.idle", true));

        controllerAttack.setAnimationSpeed((float)union.mayfest.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.mayfest.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<Mayfest> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);
    AnimationController<Mayfest> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerMove);
        data.addAnimationController(controllerAttack);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());

        if (EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),0,0) && getAttackState())
            ParticleList.mayfestParticle(this);
    }

    @Override
    public void commonExecute(WLM wlm, EntityLivingBase target)
    {
        healing();
    }

    public void healing()
    {
        for (EntityLiving target : this.world.getEntitiesWithinAABB(EntityLiving.class, new AoeRangeUtil(this,getAttackRange())))
        {
            if(target instanceof ArkMob && target.getHealth()/ target.getMaxHealth()<1 && (((ArkMob) target).getCamp()==EnumCamps.UNION)) target.heal(getAttackDamage());
        }
    }
}
