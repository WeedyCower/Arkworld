package com.weedycow.arkworld.entity.enemy.cthulhu.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.entity.enemy.ArkMob;
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

public class DeepSeaSlider extends ArkMob implements ICB
{
    public static final String ID = "deep_sea_slider";
    public static final String NAME = Arkworld.MODID + ".deepSeaSlider";

    public DeepSeaSlider(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.SEA_MONSTER, EnumCamps.CTHULHU, EnumStatus.NORMAL, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(0.8f, 1.7f);
        experienceValue = cthulhu.deepSeaSlider.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(cthulhu.deepSeaSlider.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(cthulhu.deepSeaSlider.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(cthulhu.deepSeaSlider.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(cthulhu.deepSeaSlider.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(cthulhu.deepSeaSlider.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(cthulhu.deepSeaSlider.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(cthulhu.deepSeaSlider.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(cthulhu.deepSeaSlider.attackDamage);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),12*getAttackInterval()/cthulhu.deepSeaSlider.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.deep_sea_slider.attack", false));
            controllerAttack.markNeedsReload();
        }

        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.deep_sea_slider.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.deep_sea_slider.idle", true));

        controllerAttack.setAnimationSpeed((float)cthulhu.deepSeaSlider.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/cthulhu.deepSeaSlider.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<DeepSeaSlider> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);
    AnimationController<DeepSeaSlider> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);

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
        addHP(getType()*cthulhu.deepSeaSlider.leaderMaxHealth);
        addAD(getType()*cthulhu.deepSeaSlider.leaderAttackDamage);
        addDP(getType()*cthulhu.deepSeaSlider.leaderDefensivePower);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm, getAttackRange(), getAttackDamage(), DamageSource.causeMobDamage(this));
    }

    @Override
    public void capabilityExecute(EntityLiving entity, EntityLivingBase target)
    {
        CapabilityState.Process stateFunction = new CapabilityState.Process(target);

        stateFunction.addFunctionOnlyLevel(EnumState.NERVE_INJURY, (int) (getAttackDamage()*0.15f));
    }
}
