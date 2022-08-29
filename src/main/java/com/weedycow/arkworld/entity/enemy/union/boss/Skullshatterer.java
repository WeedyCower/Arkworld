package com.weedycow.arkworld.entity.enemy.union.boss;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.MovingSound;
import com.weedycow.arkworld.entity.enemy.ArkBoss;
import com.weedycow.arkworld.entity.weapon.Bomb;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.arkworld.util.ParticleList;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class Skullshatterer extends ArkBoss
{
    public static final String ID = "skullshatterer";
    public static final String NAME = Arkworld.MODID + ".skullshatterer";

    public Skullshatterer(World worldIn)
    {
        super(worldIn, ID, NAME, BossInfo.Color.RED, EnumTypes.OTHER, EnumCamps.UNION, EnumAttackMethod.RANGE_MELEE, EnumDamageTypes.PHYSICAL);
        this.setSize(0.8f, 2f);
        this.experienceValue = union.skullshatterer.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.skullshatterer.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.skullshatterer.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.skullshatterer.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.skullshatterer.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.skullshatterer.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.skullshatterer.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.skullshatterer.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.skullshatterer.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),20*getAttackInterval()/union.skullshatterer.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.skullshatterer_melee.attack", false));
            controllerAttack.markNeedsReload();
        }
        else if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),10*getAttackInterval()/union.skullshatterer.attackInterval,0) && getTargetState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.skullshatterer_range.attack", false));
            controllerAttack.markNeedsReload();
        }


        if (event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.skullshatterer.move", true));
        } else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.skullshatterer.idle", true));

        ParticleList.skullshattererParticle(this);

        controllerAttack.setAnimationSpeed((float)union.skullshatterer.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.skullshatterer.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<Skullshatterer> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);
    AnimationController<Skullshatterer> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);

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
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(getHealth()/getMaxHealth()<=0.5)
        {
            if (EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
                EntityUtil.attackMelee(wlm, getAttackRange(), getAttackDamage()*2, DamageSource.causeMobDamage(this));
        }
        else
        {
            if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
                EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
        }
    }

    @Override
    public void remoteExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm,getAttackInterval(),0,0))
        {
            if (getHealth() / getMaxHealth() <= 0.5)
            {
                Bomb b1 = new Bomb(wlm.world, wlm, getAttackDamage(), union.skullshatterer.explosionDestroyTerrain, Bomb.Type.GRENADE);
                Bomb b2 = new Bomb(wlm.world, wlm, getAttackDamage(), union.skullshatterer.explosionDestroyTerrain, Bomb.Type.GRENADE);
                EntityUtil.attackRange(wlm, b1, null, getDistance() / 7, 1);
                EntityUtil.attackRange(wlm, b2, null, getDistance() / 7, 1);
            }
            else
            {
                Bomb b1 = new Bomb(wlm.world, wlm, getAttackDamage() / 2, union.skullshatterer.explosionDestroyTerrain, Bomb.Type.GRENADE);
                Bomb b2 = new Bomb(wlm.world, wlm, getAttackDamage() / 2, union.skullshatterer.explosionDestroyTerrain, Bomb.Type.GRENADE);
                EntityUtil.attackRange(wlm, b1, null, getDistance() / 7, 1);
                EntityUtil.attackRange(wlm, b2, null, getDistance() / 7, 1);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onBgm(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof Skullshatterer && ArkConfig.common.bgm)
        {
            MovingSound BGM = new MovingSound((EntityLivingBase) entity,SoundRegistry.SKULLSHATTERER_BGM);
            Minecraft.getMinecraft().getSoundHandler().playSound(BGM);
        }
    }

    public ResourceLocation getModelLocation()
    {
        if(isRightRange())
            return ArkResUtil.geo(id+"_melee");
        else
            return ArkResUtil.geo(id+"_range");
    }

    public ResourceLocation getAnimationLocation()
    {
        if(isRightRange())
            return ArkResUtil.animation(id+"_melee");
        else
            return ArkResUtil.animation(id+"_range");
    }
}
