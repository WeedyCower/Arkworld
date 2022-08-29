package com.weedycow.arkworld.entity.enemy.union.boss;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.MovingSound;
import com.weedycow.arkworld.entity.enemy.ArkBoss;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.entity.weapon.Arrow;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.arkworld.util.ArkEntityUtil;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.AoeRangeUtil;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.UUID;

public class Patriot extends ArkBoss
{
    public static final String ID = "patriot";
    public static final String NAME = Arkworld.MODID + ".patriot";
    private static final UUID ATTACK_INTERVAL = UUID.fromString("4984a0e0-aa2d-4771-a991-6211be963536");
    private static final UUID MOVE_SPEED = UUID.fromString("c9afe350-9703-4049-9c53-dbbed1e08d43");
    private static final UUID ATTACK_DAMAGE = UUID.fromString("bcb82c0c-7d90-4878-a772-47d046b0d214");
    private static final UUID DEFENSIVE_POWER = UUID.fromString("cc3e5781-a8ea-43a4-8569-348f7c628ac0");
    private static final UUID SPELL_RESISTANCE = UUID.fromString("e5ebd007-9777-4b1a-bcb2-e738e3f9b8b5");

    public Patriot(World worldIn)
    {
        super(worldIn, ID, NAME, BossInfo.Color.RED, EnumTypes.OTHER, EnumCamps.UNION, EnumAttackMethod.RANGE_MELEE, EnumDamageTypes.PHYSICAL);
        this.setSize(1.5f, 3f);
        this.experienceValue = union.patriot.experienceValue;
        this.countdown=600;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.patriot.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.patriot.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.patriot.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.patriot.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.patriot.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.patriot.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.patriot.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.patriot.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(getStage()==0)
        {
            if (EntityUtil.atSetIntervals(TICK,this, getAttackInterval(), 15*getAttackInterval()/union.patriot.attackInterval, 0) && getAttackState())
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.patriot_first.attack", false));
                controllerAttack.markNeedsReload();
            }
            if (event.isMoving())
            {
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.patriot_first.move", true));
            }
            else
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.patriot_first.idle", true));
        }

        if(getStage()==1)
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.patriot_resurrection.resurrection", false));

        if(getStage()==2)
        {
            if(EntityUtil.atSetIntervals(TICK,this,ArkMob.union.patriot.javelinInterval*getAttackInterval()/union.patriot.attackInterval,40*getAttackInterval()/union.patriot.attackInterval,0) && getTargetState() && !getAttackState())
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.patriot_second.throw", false));
                controllerAttack.markNeedsReload();
            }
            else if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),15,0) && getAttackState())
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.patriot_second.attack", false));
                controllerAttack.markNeedsReload();
            }
            if (event.isMoving())
            {
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.patriot_second.move", true));
            } else
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.patriot_second.idle", true));
        }

        controllerAttack.setAnimationSpeed((float)union.patriot.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.patriot.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<Patriot> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);
    AnimationController<Patriot> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData animationData)
    {
        animationData.addAnimationController(controllerMove);
        animationData.addAnimationController(controllerAttack);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(getCountdown() >0 && getStage()==1)
        {
            setCountdown(getCountdown()-1);
            ArkEntityUtil.patriotTransformation(this);
            this.motionX = 0;
            this.motionY = 0;
        }

        if(getCountdown()<=0 && getStage()==1)
        {
            setStage(2);
            if(world.isRemote)secondBgm();
            this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).applyModifier(new AttributeModifier(ATTACK_INTERVAL,"Attack interval",-getAttackInterval()/10f,0).setSaved(true));
            this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).applyModifier(new AttributeModifier(MOVE_SPEED,"Move speed",getMoveSpeed()/4f,0).setSaved(true));
            this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier(ATTACK_DAMAGE,"Attack damage",-getAttackDamage()/3f,0).setSaved(true));
            this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).applyModifier(new AttributeModifier(DEFENSIVE_POWER,"Defensive power",-getDefensivePower()*2/3f,0).setSaved(true));
            this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).applyModifier(new AttributeModifier(SPELL_RESISTANCE,"Spell resistance",-getSpellResistance()/2f,0).setSaved(true));
            this.setHealth(getMaxHealth());
            this.playSound(SoundRegistry.PATRIOT_SECOND_SPAWN, 2, 1);
        }

        if(getStage()==1)
            this.bossInfo.setPercent(1 - (this.getCountdown()/(float)countdown));
        else
            this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());

        if(ArkMob.union.patriot.canTaunt){ArkEntityUtil.taunt(this,ArkMob.union.patriot.tauntRange);}
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
        {
            EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
            if(getStage()==0)
            {
                EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
                EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
                EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
            }
        }
    }

    @Override
    public void remoteExecute(WLM wlm, EntityLivingBase target)
    {
        if(getStage()==2 && EntityUtil.atSetIntervals(TICK,wlm,ArkMob.union.patriot.javelinInterval*getAttackInterval()/union.patriot.attackInterval,0,0))
        {
            for (EntityLivingBase entity : world.getEntitiesWithinAABB(EntityLivingBase.class, new AoeRangeUtil(wlm,union.patriot.javelinRange)))
            {
                if (entity == wlm.getAttackTarget())
                {
                    Arrow arrow = new Arrow(world,wlm,getAttackDamage(), Arrow.Type.PATRIOT_JAVELIN);
                    arrow.setPosition(entity.posX,entity.posY+16,entity.posZ);
                    arrow.shoot(entity.posX-arrow.posX,-16,entity.posZ-arrow.posZ,2,1);
                    world.spawnEntity(arrow);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void secondBgm()
    {
        if (ArkConfig.common.bgm)
        {
            MovingSound BGM = new MovingSound(this,SoundRegistry.PATRIOT_SECOND_BGM);
            Minecraft.getMinecraft().getSoundHandler().playSound(BGM);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onBgm(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof Patriot && ((Patriot) entity).getStage()==0 && ArkConfig.common.bgm && entity.world.isRemote)
        {
            MovingSound BGM = new MovingSound((EntityLivingBase) entity,SoundRegistry.PATRIOT_FIRST_BGM);
            Minecraft.getMinecraft().getSoundHandler().playSound(BGM);
        }
        if (entity instanceof Patriot && ((Patriot) entity).getStage()!=0 && ArkConfig.common.bgm && entity.world.isRemote)
        {
            MovingSound BGM = new MovingSound((EntityLivingBase) entity,SoundRegistry.PATRIOT_SECOND_BGM);
            Minecraft.getMinecraft().getSoundHandler().playSound(BGM);
        }
    }

    public static void onDeath(LivingDeathEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        if(entity instanceof Patriot && ((Patriot) entity).getStage()==0)
        {
            event.setCanceled(true);
            entity.setHealth(1);
            ((Patriot) entity).setStage(1);
            entity.playSound(SoundRegistry.PATRIOT_RESURRECTION_SPAWN, 2, 1);
        }
        if(entity instanceof Patriot && ((Patriot) entity).getStage()==2)
        {
            if (ArkConfig.common.bgm) {entity.playSound(SoundRegistry.PATRIOT_DEATH_BGM,3,1);}
        }
    }

    public static void onTrans(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        event.setCanceled(entity instanceof Patriot && (((Patriot) entity).getStage()==1));
    }

    public ResourceLocation getModelLocation()
    {
        if(getStage()==0)
            return ArkResUtil.geo(id+"_first");
        if(getStage()==1)
            return ArkResUtil.geo(id+"_resurrection");
        if(getStage()==2)
            return ArkResUtil.geo(id+"_second");
        return ArkResUtil.geo(id);
    }

    public ResourceLocation getAnimationLocation()
    {
        if(getStage()==0)
            return ArkResUtil.animation(id+"_first");
        if(getStage()==1)
            return ArkResUtil.animation(id+"_resurrection");
        if(getStage()==2)
            return ArkResUtil.animation(id+"_second");
        return ArkResUtil.animation(id);
    }
}
