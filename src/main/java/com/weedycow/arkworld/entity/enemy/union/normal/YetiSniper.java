package com.weedycow.arkworld.entity.enemy.union.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.entity.weapon.Arrow;
import com.weedycow.arkworld.registry.CapabilityRegistry;
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

public class YetiSniper extends ArkMob
{
    public static final String ID = "yeti_sniper";
    public static final String NAME = Arkworld.MODID + ".yetiSniper";

    public YetiSniper(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.NORMAL, EnumAttackMethod.RANGE, EnumDamageTypes.PHYSICAL);
        this.setSize(0.8f, 2f);
        this.experienceValue = union.yetiSniper.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.yetiSniper.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.yetiSniper.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.yetiSniper.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.yetiSniper.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.yetiSniper.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.yetiSniper.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.yetiSniper.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.yetiSniper.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(getTargetState() && EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),12*getAttackInterval()/union.yetiSniper.attackInterval,0))
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.sniper.attack", false));
            controllerAttack.markNeedsReload();
        }

        if (event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sniper.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sniper.idle", true));

        controllerAttack.setAnimationSpeed((float)union.yetiSniper.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.yetiSniper.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<YetiSniper> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);
    AnimationController<YetiSniper> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);

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
        addHP(getType()*union.yetiSniper.leaderMaxHealth);
        addAD(getType()*union.yetiSniper.leaderAttackDamage);
        addDP(getType()*union.yetiSniper.leaderDefensivePower);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void commonExecute(WLM wlm, EntityLivingBase target)
    {
        Arrow arrow;

        if(target.hasCapability(CapabilityRegistry.capState,null) && wlm.getCapability(CapabilityRegistry.capState,null).getStates().contains(EnumState.COLD))
            arrow = new Arrow(world,wlm,getAttackDamage()*1.5f, Arrow.Type.NORMAL_ARROW);
        else
            arrow = new Arrow(world,wlm,getAttackDamage(), Arrow.Type.NORMAL_ARROW);

        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackRange(wlm,null,arrow,getDistance()/6,3);
    }
}



