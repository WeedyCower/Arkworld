package com.weedycow.arkworld.entity.enemy.kazdel.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.entity.enemy.kazdel.elite.SarkazBladeweaver;
import com.weedycow.arkworld.entity.enemy.kazdel.elite.SarkazGrudgebearer;
import com.weedycow.arkworld.util.ParticleList;
import com.weedycow.weedylib.ai.WLEntityAIAttackBase;
import com.weedycow.weedylib.entity.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

public class SarkazSentinel extends ArkMob
{
    public static final String ID = "sarkaz_sentinel";
    public static final String NAME = Arkworld.MODID + ".sarkazSentinel";
    private static final DataParameter<Boolean> ACTIVE = EntityDataManager.createKey(SarkazSentinel.class, DataSerializers.BOOLEAN);

    public SarkazSentinel(World worldIn)
    {
        super(worldIn,ID,NAME, EnumTypes.SARKAZ, EnumCamps.KAZDEL, EnumStatus.NORMAL, EnumAttackMethod.OTHER,EnumDamageTypes.OTHER);
        this.setSize(0.8f, 2f);
        experienceValue = kazdel.sarkazSentinel.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(kazdel.sarkazSentinel.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(kazdel.sarkazSentinel.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(kazdel.sarkazSentinel.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(kazdel.sarkazSentinel.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(kazdel.sarkazSentinel.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(kazdel.sarkazSentinel.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(kazdel.sarkazSentinel.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(kazdel.sarkazSentinel.attackDamage);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new WLEntityAIAttackBase(this));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, getMoveSpeed()));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 32.0f));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, SarkazGrudgebearer.class, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, SarkazBladeweaver.class, true));
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(getActiveState())
        {
            controllerActive.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_sentinel.active", false));

            if(controllerActive.getAnimationState() != AnimationState.Running)
            {
                if(event.isMoving())
                {
                    controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_sentinel.active_move", true));
                }
                else
                    controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_sentinel.active_idle", true));
            }
            else
                ParticleList.SarkazSentinel(this);
        }
        else
        {
            if(event.isMoving())
            {
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_sentinel.move", true));
            }
            else
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.sarkaz_sentinel.idle", true));
        }

        controllerMove.setAnimationSpeed(getMoveSpeed()/kazdel.sarkazSentinel.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<SarkazSentinel> controllerActive = new AnimationController<>(this,"controllerActive",1,this::PlayState);
    AnimationController<SarkazSentinel> controllerMove = new AnimationController<>(this,"controllerMove",10,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
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

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data)
    {
        setType(rand.nextInt(2));
        IAttributeInstance health = this.getEntityAttribute(WLMAttributes.MAX_HEALTH);
        IAttributeInstance defensive = this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER);
        health.applyModifier(new AttributeModifier("Health boost",this.getType()*kazdel.sarkazSentinel.leaderMaxHealth,0));
        defensive.applyModifier(new AttributeModifier("Defensive boost",this.getType()*kazdel.sarkazSentinel.leaderDefensivePower,0));
        this.setHealth(getMaxHealth());
        return super.onInitialSpawn(difficulty,data);
    }

    public boolean getActiveState()
    {
        return this.getDataManager().get(ACTIVE);
    }

    public void setActiveState(boolean isActive)
    {
        this.getDataManager().set(ACTIVE, isActive);
    }

    public static void onActive(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        if(entity instanceof SarkazSentinel && !((SarkazSentinel) entity).getActiveState())
        {
            ((SarkazSentinel) entity).setActiveState(true);
            BlockPos pos = new BlockPos(entity.posX,entity.posY,entity.posZ);
            AxisAlignedBB AABB = new AxisAlignedBB(pos).grow(16);
            for (EntityLivingBase entityLivingBase : entity.world.getEntitiesWithinAABB(EntityLivingBase.class, AABB))
            {
                if(entityLivingBase instanceof SarkazBladeweaver && !((SarkazBladeweaver) entityLivingBase).getActiveState())
                {
                    ((SarkazBladeweaver) entityLivingBase).setActiveState(true);
                    IAttributeInstance instance = entityLivingBase.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED);
                    AttributeModifier modifier = new AttributeModifier(UUID.fromString("14f91b31-53d0-40cb-846b-015f452f166e"),"speed",instance.getAttributeValue()*0.5f,0);
                    if(!instance.hasModifier(modifier))
                        instance.applyModifier(modifier);
                }
                if(entityLivingBase instanceof SarkazGrudgebearer && !((SarkazGrudgebearer) entityLivingBase).getActiveState())
                {
                    ((SarkazGrudgebearer) entityLivingBase).setActiveState(true);
                    IAttributeInstance instance = entityLivingBase.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED);
                    AttributeModifier modifier = new AttributeModifier(UUID.fromString("1e1b31cb-1e39-bc09-997c-c5394f87f053"),"speed",instance.getAttributeValue()*0.5f,0);
                    if(!instance.hasModifier(modifier))
                        instance.applyModifier(modifier);
                }
            }
        }
    }
}
