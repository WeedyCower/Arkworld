package com.weedycow.arkworld.entity.enemy.union.boss;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkBoss;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class SarkazCenturion extends ArkBoss
{
    public static final String ID = "sarkaz_centurion";
    public static final String NAME = Arkworld.MODID + ".sarkazCenturion";

    public SarkazCenturion(World worldIn)
    {
        super(worldIn, ID, NAME, BossInfo.Color.RED, EnumTypes.OTHER, EnumCamps.UNION, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(0.8f, 4f);
        experienceValue = union.sarkazCenturion.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.sarkazCenturion.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.sarkazCenturion.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.sarkazCenturion.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.sarkazCenturion.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.sarkazCenturion.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.sarkazCenturion.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.sarkazCenturion.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.sarkazCenturion.attackDamage);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),30*getAttackInterval()/union.sarkazCenturion.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_centurion.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_centurion.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_centurion.idle", true));

        controllerScale.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_centurion.scale", true));

        controllerAttack.setAnimationSpeed((float)union.sarkazCenturion.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.sarkazCenturion.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<SarkazCenturion> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);
    AnimationController<SarkazCenturion> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);
    AnimationController<SarkazCenturion> controllerScale = new AnimationController<>(this,"controllerScale",1,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
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
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(), DamageSource.causeMobDamage(this));
    }
}
