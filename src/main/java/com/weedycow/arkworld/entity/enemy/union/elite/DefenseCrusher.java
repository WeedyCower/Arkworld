package com.weedycow.arkworld.entity.enemy.union.elite;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLiving;
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

public class DefenseCrusher extends ArkMob implements ICB
{
    private int attackTimes;
    public static final String ID = "defense_crusher";
    public static final String NAME = Arkworld.MODID + ".defenseCrusher";

    public DefenseCrusher(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.ELITE, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(1.5f, 3f);
        experienceValue = union.defenseCrusher.experienceValue;
        attackTimes = 0;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.defenseCrusher.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.defenseCrusher.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.defenseCrusher.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.defenseCrusher.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.defenseCrusher.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.defenseCrusher.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.defenseCrusher.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.defenseCrusher.attackDamage);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),29*getAttackInterval()/union.defenseCrusher.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.smash_stormer.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.smash_stormer.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.smash_stormer.idle", true));

        controllerAttack.setAnimationSpeed((float)union.defenseCrusher.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.defenseCrusher.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<DefenseCrusher> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);
    AnimationController<DefenseCrusher> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);

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
        addHP(getType()*union.defenseCrusher.leaderMaxHealth);
        addAD(getType()*union.defenseCrusher.leaderAttackDamage);
        addDP(getType()*union.defenseCrusher.leaderDefensivePower);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
        {
            EntityUtil.attackMeleeAOE(wlm, EnumCamps.UNION, DamageSource.causeMobDamage(this), getAttackRange(), getAttackDamage(), true);
            attackTimes+=1;
        }
    }

    @Override
    public void capabilityExecute(EntityLiving wlm, EntityLivingBase target)
    {
        if(attackTimes%3==0 && target.hasCapability(CapabilityRegistry.capState,null))
        {
            CapabilityState.Process process = new CapabilityState.Process(target);
            process.addFunctionOnlyTick(EnumState.VERTIGO,union.defenseCrusher.vertigoTime);
        }
    }
}