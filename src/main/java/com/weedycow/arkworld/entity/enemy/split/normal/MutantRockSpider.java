package com.weedycow.arkworld.entity.enemy.split.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.util.ArkEntityUtil;
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

public class MutantRockSpider extends ArkMob
{
    public static final String ID = "mutant_rock_spider";
    public static final String NAME = Arkworld.MODID + ".mutantRockSpider";
    protected static final DataParameter<Boolean> BOOM = EntityDataManager.createKey(MutantRockSpider.class, DataSerializers.BOOLEAN);

    public MutantRockSpider(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.INFECTED_ORGANISMS, EnumCamps.SPLIT, EnumStatus.NORMAL, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(0.8f, 1f);
        experienceValue = split.mutantRockSpider.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(split.mutantRockSpider.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(split.mutantRockSpider.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(split.mutantRockSpider.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(split.mutantRockSpider.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(split.mutantRockSpider.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(split.mutantRockSpider.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(split.mutantRockSpider.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(split.mutantRockSpider.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(getBoomState())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.spider_originium_slug.boom", false));
        }
        else
        {
            if (EntityUtil.atSetIntervals(TICK,this, getAttackInterval(), 12, 0) && isRightRange())
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.spider_originium_slug.attack", false));
                controllerAttack.markNeedsReload();
            }
            if (event.isMoving())
            {
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.spider_originium_slug.move", true));
            } else
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.spider_originium_slug.idle", true));
        }

        controllerAttack.setAnimationSpeed((float)split.mutantRockSpider.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/split.mutantRockSpider.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<MutantRockSpider> controllerIdle = new AnimationController<>(this, "controllerIdle", 1, this::PlayState);
    AnimationController<MutantRockSpider> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);
    AnimationController<MutantRockSpider> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerIdle);
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
            this.motionX=0;this.motionZ=0;
        }

        if(getCountdown()<=0)
        {
            this.setDead();
            ArkEntityUtil.splitOriginiutantExcrescence(this,ArkMob.split.mutantRockSpider.deadSplitOriginiutantExcrescence);
        }
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data)
    {
        setCountdown(27);
        setType(rand.nextInt(2));
        addHP(getType()*split.mutantRockSpider.alphaMaxHealth);
        addAD(getType()*split.mutantRockSpider.alphaAttackDamage);
        return super.onInitialSpawn(difficulty,data);
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
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation("spider_originium_slug");
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        if(getType()==1)
            return ArkResUtil.textureEntities(id+"_a");
        return ArkResUtil.textureEntities(id);
    }

    public static void onDeath(LivingDeathEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        if(entity instanceof MutantRockSpider && !((MutantRockSpider) entity).getBoomState())
        {
            event.setCanceled(true);
            entity.setHealth(1);
            ((MutantRockSpider) entity).setBoomState(true);
            if(event.getSource().getTrueSource() instanceof EntityPlayer) {((EntityPlayer) event.getSource().getTrueSource()).addExperience(((MutantRockSpider) entity).getExperienceValue());}
        }
    }

    public static void onAttack(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        event.setCanceled(entity instanceof MutantRockSpider && ((MutantRockSpider) entity).getBoomState() && ((MutantRockSpider) entity).getCountdown()>0);
    }
}