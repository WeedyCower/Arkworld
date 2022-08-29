package com.weedycow.arkworld.entity.enemy.union.boss;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkBoss;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class Regicide extends ArkBoss
{
    public static final String ID = "regicide";
    public static final String NAME = Arkworld.MODID + ".regicide";

    public Regicide(World worldIn)
    {
        super(worldIn, ID, NAME, BossInfo.Color.RED, EnumTypes.OTHER, EnumCamps.UNION, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(1, 2);
        experienceValue = union.regicide.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.regicide.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.regicide.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.regicide.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.regicide.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.regicide.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.regicide.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.regicide.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.regicide.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),4*getAttackInterval()/union.regicide.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.regicide.attack", false));
            controllerAttack.markNeedsReload();
        }
        if (event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.regicide.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.regicide.idle", true));

        controllerAttack.setAnimationSpeed((float)union.regicide.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.regicide.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<Regicide> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);
    AnimationController<Regicide> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);

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

        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void commonExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
    }

    public static void onAttacked(LivingAttackEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        Entity source = event.getSource().getTrueSource();
        if (source instanceof EntityLivingBase && entity instanceof Regicide && Math.random() * 100 <= 30)
        {
            double x = source.posX-entity.posX;
            double y = source.posY-entity.posY;
            double z = source.posZ-entity.posZ;
            entity.setSprinting(true);
            entity.motionX = x;
            entity.motionY = y;
            entity.motionZ = z;
            entity.addPotionEffect(new PotionEffect(MobEffects.SPEED,120,1));
            entity.attackEntityFrom(DamageSource.GENERIC,((Regicide) entity).getAttackDamage()*2);
        }
    }
}
