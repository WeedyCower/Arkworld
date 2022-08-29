package com.weedycow.arkworld.entity.enemy.split.boss;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.ai.EntityAIEvolution;
import com.weedycow.arkworld.entity.enemy.ArkBoss;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.arkworld.util.ParticleList;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class Evolution extends ArkBoss
{
    public static final String ID = "evolution";
    public static final String NAME = Arkworld.MODID + ".evolution";

    public Evolution(World worldIn)
    {
        super(worldIn, ID, NAME, BossInfo.Color.RED, EnumTypes.INFECTED_ORGANISMS, EnumCamps.SPLIT, EnumAttackMethod.OTHER, EnumDamageTypes.OTHER);
        setSize(1.6f, 2.3f);
        experienceValue = split.evolution.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(split.evolution.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(split.evolution.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(split.evolution.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(split.evolution.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(split.evolution.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(split.evolution.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(split.evolution.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(split.evolution.attackDamage);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIEvolution(this,TICK));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.evolution.default", true));
        return PlayState.CONTINUE;
    }

    AnimationController<Evolution> controllerIdle = new AnimationController<>(this, "controllerIdle", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerIdle);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());

        if (getCountdown()>0)
        {
            setCountdown(getCountdown() - 1);
            ParticleList.evolutionAlarm(this,getCountdown());
        }

        if(getHealth() / getMaxHealth() <= 0.2 && getHealth() / getMaxHealth() > 0)
        {
            if(EntityUtil.atSetIntervals(TICK,this,20,0,0))
            {
                attackEntityFrom(DamageSource.GENERIC, getMaxHealth() * 0.0028f);
                ParticleList.evolutionCover(this);
            }
        }
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data)
    {
        setCountdown(split.evolution.alarmTime);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        if (getHealth() / getMaxHealth() <= 1 && getHealth() / getMaxHealth() > 0.6)
        {
            return ArkResUtil.textureEntities(id+"_first");
        }
        else if (getHealth() / getMaxHealth() <= 0.6 && getHealth() / getMaxHealth() > 0.2)
        {
            return ArkResUtil.textureEntities(id+"_second");
        }
        else return ArkResUtil.textureEntities(id+"_third");
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        if (getHealth() / getMaxHealth() <= 1 && getHealth() / getMaxHealth() > 0.6)
        {
            return ArkResUtil.geo(id+"_first");
        }
        else if (getHealth() / getMaxHealth() <= 0.6 && getHealth() / getMaxHealth() > 0.2)
        {
            return ArkResUtil.geo(id+"_second");
        }
        else return ArkResUtil.geo(id+"_third");
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        if (getHealth() / getMaxHealth() <= 1 && getHealth() / getMaxHealth() > 0.6)
        {
            return ArkResUtil.animation(id+"_first");
        }
        else if (getHealth() / getMaxHealth() <= 0.6 && getHealth() / getMaxHealth() > 0.2)
        {
            return ArkResUtil.animation(id+"_second");
        }
        else return ArkResUtil.animation(id+"_third");
    }

    public static void onEvolutionThird(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource source = event.getSource();

        if(entity instanceof Evolution && entity.getHealth() / entity.getMaxHealth() <= 0.2 && entity.getHealth() / entity.getMaxHealth() > 0 && source!=DamageSource.GENERIC)
        {
            event.setAmount(event.getAmount()*0.01f);
        }
    }
}