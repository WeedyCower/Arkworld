package com.weedycow.arkworld.entity.enemy.union.boss;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.MovingSound;
import com.weedycow.arkworld.entity.enemy.ArkBoss;
import com.weedycow.arkworld.entity.weapon.Arrow;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.arkworld.util.ParticleList;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;

public class Faust extends ArkBoss
{
    public static final String ID = "faust";
    public static final String NAME = Arkworld.MODID + ".faust";

    public Faust(World worldIn)
    {
        super(worldIn, ID, NAME, BossInfo.Color.BLUE, EnumTypes.OTHER, EnumCamps.UNION, EnumAttackMethod.RANGE, EnumDamageTypes.PHYSICAL);
        this.setSize(1, 2);
        experienceValue=union.faust.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.faust.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.faust.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.faust.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.faust.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.faust.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.faust.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.faust.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.faust.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval()*4,25*getAttackInterval()/union.faust.attackInterval,0) && getTargetState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.faust.attack", false));
            controllerAttack.markNeedsReload();
        }
        else if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),25*getAttackInterval()/union.faust.attackInterval,0) && getTargetState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.faust.attack", false));
            controllerAttack.markNeedsReload();
        }

        if (event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.faust.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.faust.idle", true));

        controllerAttack.setAnimationSpeed((float)union.faust.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.faust.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<Faust> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);
    AnimationController<Faust> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerMove);
        data.addAnimationController(controllerAttack);
    }

    @Override
    public IEntityLivingData onInitialSpawn(@Nonnull DifficultyInstance difficulty, IEntityLivingData data)
    {
        Mayfest mayfest = new Mayfest(this.world);
        mayfest.setPosition(this.posX+Math.random()*2, this.posY, this.posZ+Math.random()*2);
        world.spawnEntity(mayfest);
        setCountdown(3000);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());

        if(getCountdown() >0)
        {
            setCountdown(getCountdown()-1);
            ParticleList.faustParticle(this);
        }
    }

    @Override
    public void commonExecute(WLM wlm, EntityLivingBase target)
    {
        if (EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval()*4,0,0))
        {
            wlm.playSound(SoundRegistry.FAUST_SKILL, 1, 1);
            Arrow arrow = new Arrow(wlm.world,wlm,getAttackDamage()*2, Arrow.Type.FAUST_SPECIAL_ARROW);
            EntityUtil.attackRange(wlm, null, arrow, getDistance()/5, 1);
        }
        else if (EntityUtil.atSetIntervals(TICK,wlm,getAttackInterval(),0,0))
        {
            wlm.playSound(SoundRegistry.FAUST_ATTACK, 1, 1);
            Arrow arrow = new Arrow(wlm.world,wlm,getAttackDamage(), Arrow.Type.FAUST_COMMON_ARROW);
            EntityUtil.attackRange(wlm, null, arrow, getDistance()/5, 1);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onBgm(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof Faust && ArkConfig.common.bgm && entity.world.isRemote)
        {
            MovingSound BGM = new MovingSound((EntityLivingBase) entity,SoundRegistry.FAUST_BGM);
            Minecraft.getMinecraft().getSoundHandler().playSound(BGM);
        }
    }

    public static void onTrans(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        event.setCanceled(entity instanceof Faust && ((Faust) entity).getCountdown()>0);
    }
}
