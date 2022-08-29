package com.weedycow.arkworld.entity.enemy.other.elite;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class SmilingCorpseMountain extends ArkMob
{
    public static final String ID = "smiling_corpse_mountain";
    public static final String NAME = Arkworld.MODID + ".smilingCorpseMountain";

    public SmilingCorpseMountain(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.ELITE, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(0.8f, 2f);
        experienceValue = other.smilingCorpseMountain.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(other.smilingCorpseMountain.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(other.smilingCorpseMountain.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(other.smilingCorpseMountain.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(other.smilingCorpseMountain.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(other.smilingCorpseMountain.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(other.smilingCorpseMountain.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(other.smilingCorpseMountain.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(other.smilingCorpseMountain.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if (EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),10*getAttackInterval()/other.smilingCorpseMountain.attackInterval,0) && getTargetState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.smiling_corpse_mountain.attack", false));
            controllerAttack.markNeedsReload();
        }
        if (event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.smiling_corpse_mountain.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.smiling_corpse_mountain.idle", true));

        controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.smiling_corpse_mountain.scale", true));

        controllerAttack.setAnimationSpeed((float)other.smilingCorpseMountain.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/other.smilingCorpseMountain.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<SmilingCorpseMountain> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);
    AnimationController<SmilingCorpseMountain> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);
    AnimationController<SmilingCorpseMountain> controllerIdle = new AnimationController<>(this, "controllerIdle", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerMove);
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerIdle);
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(), DamageSource.causeMobDamage(this));
    }
}
