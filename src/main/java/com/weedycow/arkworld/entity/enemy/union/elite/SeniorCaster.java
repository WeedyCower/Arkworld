package com.weedycow.arkworld.entity.enemy.union.elite;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.util.ParticleList;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.AoeRangeUtil;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;

public class SeniorCaster extends ArkMob
{
    public static final String ID = "senior_caster";
    public static final String NAME = Arkworld.MODID + ".seniorCaster";
    protected static final DataParameter<BlockPos> TARGET_POS = EntityDataManager.createKey(SeniorCaster.class, DataSerializers.BLOCK_POS);

    public SeniorCaster(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.ELITE, EnumAttackMethod.RANGE, EnumDamageTypes.SPELL);
        this.setSize(0.8f, 2f);
        experienceValue = union.seniorCaster.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.seniorCaster.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.seniorCaster.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.seniorCaster.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.seniorCaster.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.seniorCaster.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.seniorCaster.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.seniorCaster.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.seniorCaster.attackDamage);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),5*getAttackInterval()/union.seniorCaster.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.senior_caster.boom", false));
            controllerAttack.markNeedsReload();
        }

        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.senior_caster.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.senior_caster.idle", true));

        controllerAttack.setAnimationSpeed((float)union.seniorCaster.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.seniorCaster.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<SeniorCaster> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);
    AnimationController<SeniorCaster> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerMove);
        data.addAnimationController(controllerAttack);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(TARGET_POS,new BlockPos(0,0,0));
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),0,0) && getAttackState())
            ParticleList.casterParticle(this,getDataManager().get(TARGET_POS));
    }

    @Override
    public IEntityLivingData onInitialSpawn(@Nonnull DifficultyInstance difficulty, IEntityLivingData data)
    {
        setType(rand.nextInt(2));
        addHP(getType()*union.seniorCaster.leaderMaxHealth);
        addAD(getType()*union.seniorCaster.leaderAttackDamage);
        addDP(getType()*union.seniorCaster.leaderDefensivePower);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        attack(wlm);
    }

    public void attack(WLM wlm)
    {
        if (EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),0,0))
        {
            for (EntityLivingBase target : wlm.world.getEntitiesWithinAABB(EntityLivingBase.class, new AoeRangeUtil(wlm,getAttackRange())))
            {
                if (target == wlm.getAttackTarget() && (!(target instanceof WLM) || ((WLM)target).getCamp()!=getCamp()))
                {
                    target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(wlm, null), getAttackDamage());
                    this.getDataManager().set(TARGET_POS,new BlockPos(target.posX,target.posY,target.posZ));
                }
            }
        }
    }
}
