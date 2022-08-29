package com.weedycow.arkworld.entity.enemy.union.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.ParametersAreNonnullByDefault;

public class SpiderGlacialOriginiumSlug extends ArkMob
{
    public static final String ID = "spider_glacial_originium_slug";
    public static final String NAME = Arkworld.MODID + ".spiderGlacialOriginiumSlug";
    protected static final DataParameter<Boolean> BOOM = EntityDataManager.createKey(SpiderGlacialOriginiumSlug.class, DataSerializers.BOOLEAN);

    public SpiderGlacialOriginiumSlug(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.INFECTED_ORGANISMS, EnumCamps.UNION, EnumStatus.NORMAL, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        this.setSize(1, 1);
        experienceValue = union.spiderGlacialOriginiumSlug.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.spiderGlacialOriginiumSlug.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.spiderGlacialOriginiumSlug.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.spiderGlacialOriginiumSlug.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.spiderGlacialOriginiumSlug.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.spiderGlacialOriginiumSlug.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.spiderGlacialOriginiumSlug.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.spiderGlacialOriginiumSlug.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.spiderGlacialOriginiumSlug.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(getBoomState())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.spider_originium_slug.boom", false));
        }
        else
        {
            if (EntityUtil.atSetIntervals(TICK,this, getAttackInterval(), 12*getAttackInterval()/union.spiderGlacialOriginiumSlug.attackInterval, 0) && getAttackState())
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.spider_originium_slug.attack", false));
                controllerAttack.markNeedsReload();
            }
            if (event.isMoving())
            {
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.spider_originium_slug.move", true));
            }
            else
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.spider_originium_slug.idle", true));
        }

        controllerAttack.setAnimationSpeed((float)union.spiderGlacialOriginiumSlug.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.spiderGlacialOriginiumSlug.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<SpiderGlacialOriginiumSlug> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);
    AnimationController<SpiderGlacialOriginiumSlug> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(BOOM,false);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Boom", getBoomState());
    }

    @Override
    @ParametersAreNonnullByDefault
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setBoomState(compound.getBoolean("Boom"));
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (getCountdown() > 0 && getBoomState())
        {
            setCountdown(getCountdown() - 1);
            motionX=0;motionZ=0;
        }

        if(getCountdown() <= 0)
        {
            setDead();
            world.createExplosion(this,this.posX,this.posY,this.posZ,getAttackDamage()/7,union.spiderGlacialOriginiumSlug.explosionDestroyTerrain);
            EntityUtil.attackAOE(this, EnumCamps.UNION,DamageSource.causeExplosionDamage(this),getAttackRange()*2, 0);
        }
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data)
    {
        setCountdown(27);
        this.getDataManager().set(TYPE, this.rand.nextInt(2));
        addHP(getType()*union.spiderGlacialOriginiumSlug.alphaMaxHealth);
        addAD(getType()*union.spiderGlacialOriginiumSlug.alphaAttackDamage);
        return super.onInitialSpawn(difficulty, data);
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
    }

    public boolean getBoomState()
    {
        return this.getDataManager().get(BOOM);
    }

    public void setBoomState(boolean isAttack)
    {
        this.getDataManager().set(BOOM, isAttack);
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        if(getType()==1)
            return ArkResUtil.textureEntities("spider_glacial_originium_slug_a");
        else
            return ArkResUtil.textureEntities("spider_glacial_originium_slug");
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("spider_originium_slug");
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation("spider_originium_slug");
    }

    public static void onDeath(LivingDeathEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        if(entity instanceof SpiderGlacialOriginiumSlug && !((SpiderGlacialOriginiumSlug) entity).getBoomState())
        {
            event.setCanceled(true);
            entity.setHealth(1);
            ((SpiderGlacialOriginiumSlug) entity).setBoomState(true);
            if(event.getSource().getTrueSource() instanceof EntityPlayer) {((EntityPlayer) event.getSource().getTrueSource()).addExperience(((SpiderGlacialOriginiumSlug) entity).getExperienceValue());}
        }
    }

    public static void onAttack(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        event.setCanceled(entity instanceof SpiderGlacialOriginiumSlug && ((SpiderGlacialOriginiumSlug) entity).getBoomState() && ((SpiderGlacialOriginiumSlug) entity).getCountdown()>0);
    }
}