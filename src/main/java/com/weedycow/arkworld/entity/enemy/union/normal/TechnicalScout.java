package com.weedycow.arkworld.entity.enemy.union.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.util.ParticleList;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.AoeRangeUtil;
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

public class TechnicalScout extends ArkMob
{
    public static final String ID = "technical_scout";
    public static final String NAME = Arkworld.MODID + ".technicalScout";

    public TechnicalScout(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.NORMAL, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(0.8f, 2f);
        experienceValue = union.technicalScout.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.technicalScout.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.technicalScout.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.technicalScout.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.technicalScout.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.technicalScout.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.technicalScout.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.technicalScout.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.technicalScout.attackDamage);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),10*getAttackInterval()/union.technicalScout.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.technical_scout.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.technical_scout.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.technical_scout.idle", true));

        ParticleList.skullshattererParticle(this);

        controllerAttack.setAnimationSpeed((float)union.technicalScout.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.technicalScout.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<TechnicalScout> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);
    AnimationController<TechnicalScout> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(EntityUtil.atSetIntervals(TICK,this,40,0,0) && !world.isRemote)
        {
            for (EntityLivingBase entity : world.getEntitiesWithinAABB(EntityLivingBase.class, new AoeRangeUtil(this, getAttackRange() * 4)))
            {
                CapabilityState.Process state = new CapabilityState.Process(entity);

                if(!(entity instanceof ArkMob) || (((ArkMob) entity).getCamp()!=getCamp()))
                {
                    if (state.getStates().contains(EnumState.HIDE) || state.getStates().contains(EnumState.CAMOUFLAGE))
                    {
                        state.removeState(EnumState.HIDE);
                        state.removeState(EnumState.CAMOUFLAGE);
                    }
                }
            }
        }
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
    }
}
