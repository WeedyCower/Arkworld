package com.weedycow.arkworld.entity.enemy.ursus.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
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

public class InfectedPatrolCaptain extends ArkMob
{
    public static final String ID = "infected_patrol_captain";
    public static final String NAME = Arkworld.MODID + ".infectedPatrolCaptain";

    public InfectedPatrolCaptain(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.URSUS, EnumStatus.NORMAL, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        this.setSize(1, 2);
        experienceValue = ursus.infectedPatrolCaptain.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(ursus.infectedPatrolCaptain.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(ursus.infectedPatrolCaptain.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(ursus.infectedPatrolCaptain.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(ursus.infectedPatrolCaptain.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(ursus.infectedPatrolCaptain.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(ursus.infectedPatrolCaptain.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(ursus.infectedPatrolCaptain.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(ursus.infectedPatrolCaptain.attackDamage);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),10*getAttackInterval()/ursus.infectedPatrolCaptain.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.infected_patrol_captain.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.infected_patrol_captain.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.infected_patrol_captain.idle", true));

        controllerAttack.setAnimationSpeed((float)ursus.infectedPatrolCaptain.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/ursus.infectedPatrolCaptain.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<InfectedPatrolCaptain> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);
    AnimationController<InfectedPatrolCaptain> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);

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
        addHP(getType()*ursus.infectedPatrolCaptain.leaderMaxHealth);
        addAD(getType()*ursus.infectedPatrolCaptain.leaderAttackDamage);
        addDP(getType()*ursus.infectedPatrolCaptain.leaderDefensivePower);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
    }
}
