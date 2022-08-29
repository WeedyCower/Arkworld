package com.weedycow.arkworld.entity.enemy.kazdel.elite;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.ParametersAreNonnullByDefault;

public class SarkazBladeweaver extends ArkMob
{
    public static final String ID = "sarkaz_bladeweaver";
    public static final String NAME = Arkworld.MODID + ".sarkazBladeweaver";
    private static final DataParameter<Boolean> ACTIVE = EntityDataManager.createKey(SarkazBladeweaver.class, DataSerializers.BOOLEAN);

    public SarkazBladeweaver(World worldIn)
    {
        super(worldIn,ID,NAME, EnumTypes.SARKAZ, EnumCamps.KAZDEL, EnumStatus.ELITE, EnumAttackMethod.MELEE, EnumDamageTypes.SPELL);
        setSize(0.8f, 2.5f);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(kazdel.sarkazBladeweaver.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(kazdel.sarkazBladeweaver.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(kazdel.sarkazBladeweaver.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(kazdel.sarkazBladeweaver.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(kazdel.sarkazBladeweaver.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(kazdel.sarkazBladeweaver.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(kazdel.sarkazBladeweaver.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(kazdel.sarkazBladeweaver.attackDamage);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(getActiveState())
        {
            controllerActive.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_bladeweaver.active", false));

            if(controllerActive.getAnimationState() != AnimationState.Running)
            {

                if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),10,0) && isRightRange())
                {
                    controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_bladeweaver.active_attack", false));
                    controllerAttack.markNeedsReload();
                }

                if(event.isMoving())
                {
                    controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_bladeweaver.active_move", true));
                }
                else
                    controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_bladeweaver.active_idle", true));
            }
        }
        else
        {
            if(event.isMoving())
            {
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_bladeweaver.move", true));
            }
            else
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_bladeweaver.idle", true));
        }

        controllerAttack.setAnimationSpeed((float)kazdel.sarkazBladeweaver.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/kazdel.sarkazBladeweaver.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<SarkazBladeweaver> controllerActive = new AnimationController<>(this,"controllerActive",1,this::PlayState);
    AnimationController<SarkazBladeweaver> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);
    AnimationController<SarkazBladeweaver> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);

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
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(), DamageSource.causeMobDamage(this));
    }
}
