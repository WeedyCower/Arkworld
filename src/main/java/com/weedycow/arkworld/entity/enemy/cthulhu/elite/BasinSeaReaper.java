package com.weedycow.arkworld.entity.enemy.cthulhu.elite;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.ai.WLEntityAIAttackBase;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class BasinSeaReaper extends ArkMob implements ICB
{
    public static final String ID = "basin_sea_reaper";
    public static final String NAME = Arkworld.MODID + ".basinSeaReaper";

    public BasinSeaReaper(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.SEA_MONSTER, EnumCamps.CTHULHU, EnumStatus.ELITE, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(0.8f, 1.7f);
        experienceValue = cthulhu.basinSeaReaper.experienceValue;
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new WLEntityAIAttackBase(this));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, getMoveSpeed()));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 32.0f));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(cthulhu.basinSeaReaper.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(cthulhu.basinSeaReaper.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(cthulhu.basinSeaReaper.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(cthulhu.basinSeaReaper.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(cthulhu.basinSeaReaper.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(cthulhu.basinSeaReaper.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(cthulhu.basinSeaReaper.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(cthulhu.basinSeaReaper.attackDamage);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),12*getAttackInterval()/cthulhu.basinSeaReaper.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.basin_sea_reaper.attack", false));
            controllerAttack.markNeedsReload();
        }

        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.basin_sea_reaper.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.basin_sea_reaper.idle", true));

        controllerAttack.setAnimationSpeed((float)cthulhu.basinSeaReaper.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/cthulhu.basinSeaReaper.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<BasinSeaReaper> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);
    AnimationController<BasinSeaReaper> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
        {
            EntityUtil.attackMelee(wlm, getAttackRange(), getAttackDamage(), DamageSource.causeMobDamage(this));
        }
    }

    @Override
    public void commonExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK, wlm, 20, 0, 0))
            EntityUtil.attackAOE(wlm, EnumCamps.CTHULHU, DamageSource.GENERIC, getAttackRange(),0);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data)
    {
        setType(rand.nextInt(2));
        addHP(getType()*cthulhu.basinSeaReaper.leaderMaxHealth);
        addAD(getType()*cthulhu.basinSeaReaper.leaderAttackDamage);
        addDP(getType()*cthulhu.basinSeaReaper.leaderDefensivePower);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(getHealth()<getMaxHealth())
        {
            EntityUtil.setTargetState(this,TARGET);
            EntityUtil.setAttackState(this,ATTACK,getAttackRange());

            if(EntityUtil.atSetIntervals(TICK,this,20,0,0))
                setHealth(getHealth()-getMaxHealth()*0.04f);

            addMS(0.15f);
            applyMoveSpeedModifier();
        }
        else
        {
            addMS(0);
            removeMoveSpeedModifier();
        }
    }

    public boolean isNormalGetAttackTarget()
    {
        return false;
    }

    public boolean isNormalGetAttackState()
    {
        return false;
    }

    @Override
    public void capabilityExecute(EntityLiving entity, EntityLivingBase target)
    {
        CapabilityState.Process stateFunction = new CapabilityState.Process(target);

        stateFunction.addFunctionOnlyLevel(EnumState.NERVE_INJURY, (int) (getAttackDamage()*0.15f));
    }

    public ResourceLocation getModelLocation()
    {
        if(getHealth()<getMaxHealth())
            return ArkResUtil.geo(id+"_a");

        return ArkResUtil.geo(id);
    }

    public ResourceLocation getAnimationLocation()
    {
        if(getHealth()<getMaxHealth())
            return ArkResUtil.animation(id+"_a");

        return ArkResUtil.animation(id);
    }
}
