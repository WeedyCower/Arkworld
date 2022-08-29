package com.weedycow.arkworld.entity.enemy.union.boss;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.MovingSound;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.entity.enemy.ArkBoss;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.entity.weapon.SimpleThrowable;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.arkworld.util.ArkworldUtil;
import com.weedycow.arkworld.util.ParticleList;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import com.weedycow.weedylib.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.UUID;

public class Frostnova extends ArkBoss implements ICB
{
    public static final String ID = "frostnova";
    public static final String NAME = Arkworld.MODID + ".frostnova";
    private static final UUID DAMAGE_BOOST = UUID.fromString("6bf7b686-8b10-429b-83d2-a434be5c828b");

    public Frostnova(World worldIn)
    {
        super(worldIn, ID, NAME, BossInfo.Color.WHITE, EnumTypes.OTHER, EnumCamps.UNION, EnumAttackMethod.RANGE, EnumDamageTypes.SPELL);
        setSize(0.8f, 2f);
        experienceValue = union.frostnova.experienceValue;
        setCountdown(95);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.frostnova.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.frostnova.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.frostnova.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.frostnova.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.frostnova.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.frostnova.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.frostnova.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.frostnova.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(getStage()==2||getStage()==0)
        {
            if(getStage()==0)
            {
                if (EntityUtil.atSetIntervals(TICK,this, ArkMob.union.frostnova.iceRingInterval*getAttackInterval()/union.frostnova.attackInterval, 60*getAttackInterval()/union.frostnova.attackInterval, 0) && getAttackState())
                {
                    controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.frostnova.ice_ring", false));
                    controllerAttack.markNeedsReload();
                }
                else if (EntityUtil.atSetIntervals(TICK,this, getAttackInterval(), 15*getAttackInterval()/union.frostnova.attackInterval, 0) && getTargetState() && controllerAttack.getAnimationState() != AnimationState.Running)
                {
                    controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.frostnova.attack", false));
                    controllerAttack.markNeedsReload();
                }
            }
            if(getStage()==2)
            {
                if (EntityUtil.atSetIntervals(TICK,this, ArkMob.union.frostnova.freezeInterval*getAttackInterval()/union.frostnova.attackInterval, 30*getAttackInterval()/union.blackSnake.attackInterval, 0) && getAttackState())
                {
                    controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.frostnova.icicle", false));
                    controllerAttack.markNeedsReload();
                }
                else if (EntityUtil.atSetIntervals(TICK,this, ArkMob.union.frostnova.iceRingInterval*getAttackInterval()/union.frostnova.attackInterval, 60*getAttackInterval()/union.blackSnake.attackInterval, 0) && getAttackState() && controllerAttack.getAnimationState() != AnimationState.Running)
                {
                    controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.frostnova.ice_ring", false));
                    controllerAttack.markNeedsReload();
                }
                else if (EntityUtil.atSetIntervals(TICK,this, getAttackInterval(), 15*getAttackInterval()/union.blackSnake.attackInterval, 0) && getTargetState() && controllerAttack.getAnimationState() != AnimationState.Running)
                {
                    controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.frostnova.attack", false));
                    controllerAttack.markNeedsReload();
                }
            }
            controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.frostnova.idle", true));
            if (event.isMoving())
            {
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.frostnova.move", true));
            }
            else
            {
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.frostnova.idle", true));
            }
        }

        if(getStage()==1){controllerOther.setAnimation(new AnimationBuilder().addAnimation("animation.frostnova.transformation", false));}

        if(getStage()==3){controllerOther.setAnimation(new AnimationBuilder().addAnimation("animation.frostnova.death", false));}

        controllerAttack.setAnimationSpeed((float)union.faust.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.faust.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<Frostnova> controllerIdle = new AnimationController<>(this, "controllerIdle", 1, this::PlayState);
    AnimationController<Frostnova> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);
    AnimationController<Frostnova> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);
    AnimationController<Frostnova> controllerOther = new AnimationController<>(this, "controllerOther", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerIdle);
        data.addAnimationController(controllerMove);
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerOther);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(getCountdown() >0 && getStage()==1)
        {
            setCountdown(getCountdown()-1);
            motionX = 0; motionZ = 0;
        }

        if(getCountdown() >0 && getStage()==3)
        {
            setCountdown(getCountdown()-1);
            motionX = 0; motionZ = 0;
        }

        if(getCountdown()<=0 && getStage()==1)
        {
            setStage(2);
            setCountdown(95);
            if(world.isRemote)secondBgm();
            IAttributeInstance damage = this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
            damage.applyModifier(new AttributeModifier(DAMAGE_BOOST,"Damage boost",getAttackDamage()*0.5,0).setSaved(true));
            setHealth(getMaxHealth());
        }

        if(getCountdown()<=0 && getStage()==3)
        {
            this.setDead();
            this.onDeath(DamageSource.GENERIC);
        }

        if(getStage()==1)this.bossInfo.setPercent(1-(getCountdown()/95f));
        else this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());

        if(EntityUtil.atSetIntervals(TICK,this,ArkMob.union.frostnova.iceRingInterval,0,0) && getAttackState())
            ParticleList.frostnovaParticle(this);
    }

    @Override
    public void commonExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm,ArkMob.union.frostnova.freezeInterval*getAttackInterval()/union.frostnova.attackInterval,0,0) && getStage()==2 && getAttackState())
        {
            for(int i = 0; i<=3; i++)
            {
                double x = posX + i*MathUtil.getRandomDouble()*MathUtil.getRandomDouble() + MathUtil.getRandomDouble()*union.frostnova.freezeRange;
                double z = posZ + i*MathUtil.getRandomDouble()*MathUtil.getRandomDouble() + MathUtil.getRandomDouble()*union.frostnova.freezeRange;
                double y = ArkworldUtil.findSuitableY(world,x,z);
                SimpleThrowable throwable = new SimpleThrowable(world,x,y+9,z,0, SimpleThrowable.Type.ICICLE);
                world.spawnEntity(throwable);
            }
        }
        else if(EntityUtil.atSetIntervals(TICK,wlm,ArkMob.union.frostnova.iceRingInterval*getAttackInterval()/union.frostnova.attackInterval,0,0) && getAttackState())
        {
            wlm.playSound(SoundRegistry.FROSTNOVA_SKILL_SECOND, 2, 1);
            EntityUtil.attackAOE(wlm, EnumCamps.UNION, DamageSource.causeIndirectMagicDamage(wlm,null),ArkMob.union.frostnova.iceRingRange, wlm.getAttackDamage()*1.5f);
        }
        else if (EntityUtil.atSetIntervals(TICK,wlm, wlm.getAttackInterval(), 0, 0))
        {
            SimpleThrowable throwable = new SimpleThrowable(wlm.world, wlm, wlm.getAttackDamage(), SimpleThrowable.Type.ICE_CRYSTAL);
            EntityUtil.attackRange(wlm, throwable, null, getDistance()/5, 1);
        }
    }

    @Override
    public void capabilityExecute(EntityLiving entity, EntityLivingBase target)
    {
        CapabilityState.Process stateFunction = new CapabilityState.Process(target);

        stateFunction.addFunctionOnlyTick(EnumState.COLD,union.frostnova.coldTime);
    }

    @SideOnly(Side.CLIENT)
    public static void onBgm(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof Frostnova && ((Frostnova) entity).getStage()==0 && ArkConfig.common.bgm && entity.world.isRemote)
        {
            MovingSound BGM = new MovingSound((EntityLivingBase) entity,SoundRegistry.FROSTNOVA_FIRST_BGM);
            Minecraft.getMinecraft().getSoundHandler().playSound(BGM);
        }
        if (entity instanceof Frostnova && ((Frostnova) entity).getStage()==2 && ArkConfig.common.bgm && entity.world.isRemote)
        {
            MovingSound BGM = new MovingSound((EntityLivingBase) entity,SoundRegistry.FROSTNOVA_SECOND_BGM);
            Minecraft.getMinecraft().getSoundHandler().playSound(BGM);
        }
    }

    @SideOnly(Side.CLIENT)
    public void secondBgm()
    {
        if (ArkConfig.common.bgm)
        {
            MovingSound BGM = new MovingSound(this,SoundRegistry.FROSTNOVA_SECOND_BGM);
            Minecraft.getMinecraft().getSoundHandler().playSound(BGM);
        }
    }

    public static void onDeath(LivingDeathEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        if(entity instanceof Frostnova && ((Frostnova) entity).getStage()==0)
        {
            event.setCanceled(true);
            entity.setHealth(1);
            ((Frostnova) entity).setStage(1);
            entity.playSound(SoundRegistry.FROSTNOVA_TRANSFORMATION,2,1);
        }

        if(entity instanceof Frostnova && ((Frostnova) entity).getStage()==2)
        {
            if(event.getSource().getTrueSource() instanceof EntityPlayer)
                {((EntityPlayer) event.getSource().getTrueSource()).addExperience(((Frostnova) entity).getExperienceValue());}
            event.setCanceled(true);
            entity.setHealth(1);
            entity.world.getWorldInfo().setRaining(true);
            ((Frostnova) entity).setStage(3);
            if (ArkConfig.common.bgm) {entity.playSound(SoundRegistry.FROSTNOVA_DEATH_BGM,3,1);}
        }
    }

    public static void onTrans(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        event.setCanceled(entity instanceof Frostnova && (((Frostnova) entity).getStage()==1 || ((Frostnova) entity).getStage()==3));
    }
}