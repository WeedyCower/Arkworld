package com.weedycow.arkworld.entity.enemy.kazdel.elite;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.util.ParticleList;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.AoeRangeUtil;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.ParametersAreNonnullByDefault;

public class SarkazGrudgebearer extends ArkMob
{
    public static final String ID = "sarkaz_grudgebearer";
    public static final String NAME = Arkworld.MODID + ".sarkazGrudgebearer";
    private static final DataParameter<Boolean> ACTIVE = EntityDataManager.createKey(SarkazGrudgebearer.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<BlockPos> TARGET = EntityDataManager.createKey(SarkazGrudgebearer.class, DataSerializers.BLOCK_POS);

    public SarkazGrudgebearer(World worldIn)
    {
        super(worldIn,ID,NAME, EnumTypes.SARKAZ, EnumCamps.KAZDEL, EnumStatus.ELITE, EnumAttackMethod.RANGE, EnumDamageTypes.SPELL);
        setSize(0.8f, 2.5f);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(kazdel.sarkazGrudgebearer.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(kazdel.sarkazGrudgebearer.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(kazdel.sarkazGrudgebearer.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(kazdel.sarkazGrudgebearer.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(kazdel.sarkazGrudgebearer.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(kazdel.sarkazGrudgebearer.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(kazdel.sarkazGrudgebearer.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(kazdel.sarkazGrudgebearer.attackDamage);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(getActiveState())
        {
            controllerActive.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_grudgebearer.active_attack", false));

            if(controllerActive.getAnimationState() != AnimationState.Running)
            {
                if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),10,0) && isRightRange())
                {
                    controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_grudgebearer.active_attack", false));
                    controllerAttack.markNeedsReload();
                }

                if(event.isMoving())
                {
                    controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_grudgebearer.active_move", true));
                }
                else
                    controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_grudgebearer.active_idle", true));
            }
        }
        else
        {
            if(event.isMoving())
            {
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_grudgebearer.move", true));
            }
            else
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_grudgebearer.idle", true));
        }

        controllerAttack.setAnimationSpeed((float)kazdel.sarkazGrudgebearer.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/kazdel.sarkazGrudgebearer.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<SarkazGrudgebearer> controllerActive = new AnimationController<>(this,"controllerActive",1,this::PlayState);
    AnimationController<SarkazGrudgebearer> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);
    AnimationController<SarkazGrudgebearer> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
        data.addAnimationController(controllerActive);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(ACTIVE,false);
        this.getDataManager().register(TARGET,new BlockPos(0,0,0));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Active",getActiveState());
    }

    @Override
    @ParametersAreNonnullByDefault
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setActiveState(compound.getBoolean("Active"));
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),0,0) && isRightRange())
            ParticleList.casterParticle(this,getDataManager().get(TARGET));
    }

    public boolean getActiveState()
    {
        return this.getDataManager().get(ACTIVE);
    }

    public void setActiveState(boolean isActive)
    {
        this.getDataManager().set(ACTIVE, isActive);
    }

    public boolean shouldExecute()
    {
        return getActiveState();
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
                    this.getDataManager().set(TARGET,new BlockPos(target.posX,target.posY,target.posZ));
                }
            }
        }
    }
}
