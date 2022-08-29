package com.weedycow.arkworld.entity.enemy.other.boss;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkBoss;
import com.weedycow.arkworld.entity.weapon.Arrow;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.Random;

public class Queen extends ArkBoss
{
    public static final String ID = "queen";
    public static final String NAME = Arkworld.MODID + ".queen";

    public Queen(World worldIn)
    {
        super(worldIn, ID, NAME, BossInfo.Color.PURPLE, EnumTypes.OTHER, EnumCamps.OTHER, EnumAttackMethod.RANGE_MELEE, EnumDamageTypes.PHYSICAL);
        this.setSize(3f, 6f);
        this.experienceValue = other.queen.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(other.queen.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(other.queen.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(other.queen.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(other.queen.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(other.queen.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(other.queen.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(other.queen.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(other.queen.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if (EntityUtil.atSetIntervals(TICK, this, getAttackInterval(), 8 * getAttackInterval() / other.queen.attackInterval, 0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.queen.melee", false));
            controllerAttack.markNeedsReload();
        } else if (EntityUtil.atSetIntervals(TICK, this, getAttackInterval(), 8 * getAttackInterval() / other.queen.attackInterval, 0) && getTargetState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.queen.range", false));
            controllerAttack.markNeedsReload();
        }

        if (event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.queen.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.queen.idle", true));

        controllerScale.setAnimation(new AnimationBuilder().addAnimation("animation.queen.scale", true));

        controllerAttack.setAnimationSpeed((float) other.queen.attackInterval / getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed() / other.queen.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<Queen> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);
    AnimationController<Queen> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);
    AnimationController<Queen> controllerScale = new AnimationController<>(this, "controllerScale", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerMove);
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerScale);
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
        if (EntityUtil.atSetIntervals(TICK, wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm, getAttackRange(), getAttackDamage(), DamageSource.causeMobDamage(this));
    }

    @Override
    public void remoteExecute(WLM wlm, EntityLivingBase target)
    {
        Arrow arrow = new Arrow(world,wlm,getAttackDamage(), Arrow.Type.BONE_SPUR);
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackRange(wlm,null,arrow,getDistance()/6,1);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundRegistry.QUEEN_HURT;
    }

    @Override
    public void playLivingSound()
    {
        int i = new Random().nextInt(2);
        if(i==0)
            playSound(SoundRegistry.QUEEN_LIVING_I,1,1);
        else
            playSound(SoundRegistry.QUEEN_LIVING_II,1,1);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundRegistry.QUEEN_DEATH;
    }
}
