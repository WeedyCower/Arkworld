package com.weedycow.arkworld.entity.enemy.union.boss;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkBoss;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.entity.weapon.Bomb;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.AoeRangeUtil;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.UUID;

public class W extends ArkBoss
{
    public static final String ID = "w";
    public static final String NAME = Arkworld.MODID + ".w";

    public W(World worldIn)
    {
        super(worldIn, ID, NAME, BossInfo.Color.RED, EnumTypes.OTHER, EnumCamps.UNION, EnumAttackMethod.RANGE, EnumDamageTypes.PHYSICAL);
        this.setSize(0.8f, 2f);
        this.experienceValue = union.w.experienceValue;
        this.countdown = 60;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.w.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.w.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.w.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.w.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.w.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.w.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.w.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.w.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if (event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.w.move", true));
        } else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.w.idle", true));

        if(EntityUtil.atSetIntervals(TICK,this,union.w.c4Interval*getAttackInterval()/union.w.attackInterval,60*getAttackInterval()/union.w.attackInterval,0) && getTargetState() && getDistance()<getAttackRange()*2)
        {
            controllerBoom.setAnimation(new AnimationBuilder().addAnimation("animation.w.boom", false));
            controllerBoom.markNeedsReload();
            playSound(SoundRegistry.W_COUNTDOWN, 2, 1);
        }
        else if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),5*getAttackInterval()/union.w.attackInterval,0) && getTargetState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.w.attack", false));
            controllerAttack.markNeedsReload();
        }

        controllerAttack.setAnimationSpeed((float)union.w.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.w.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<W> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);
    AnimationController<W> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);
    AnimationController<W> controllerBoom = new AnimationController<>(this, "controllerBoom", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerMove);
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerBoom);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());

        IAttributeInstance instance = getEntityAttribute(WLMAttributes.MOVEMENT_SPEED);
        AttributeModifier modifier = new AttributeModifier(UUID.fromString("d3ebe9c4-91cb-b135-d75e-f26323589542"),"speed",-10,0);

        if(EntityUtil.atSetIntervals(TICK,this,union.w.c4Interval*getAttackInterval()/union.w.attackInterval,60*getAttackInterval()/union.w.attackInterval,0)  && getTargetState() && getDistance()<getAttackRange()*2)
        {
            setCountdown(60);
            if(instance.hasModifier(modifier))
                instance.removeModifier(modifier);
            instance.applyModifier(modifier);
        }

        setCountdown(getCountdown()-1);

        if(getCountdown()<=0)
        {
            if(instance.hasModifier(modifier))
                instance.removeModifier(modifier);
        }
    }

    @Override
    public void commonExecute(WLM wlm, EntityLivingBase target)
    {
        attack(wlm);
    }

    public void attack(WLM wlm)
    {
        if (EntityUtil.atSetIntervals(TICK,wlm, union.w.c4Interval*getAttackInterval()/union.w.attackInterval, 0, 0))
        {
            for (EntityLivingBase target : wlm.world.getEntitiesWithinAABB(EntityLivingBase.class, new AoeRangeUtil(this,getAttackRange()*2)))
            {
                if (target!=this&&(!(target instanceof ArkMob)||((ArkMob)target).getCamp()!= EnumCamps.UNION))
                {
                    if(!wlm.world.isRemote)
                        target.world.createExplosion(this, target.posX, target.posY, target.posZ, getAttackDamage()*1.8f/14, union.w.explosionDestroyTerrain);
                    target.playSound(SoundRegistry.W_EXPLODE, 2, 1);
                }
            }
        }
        else if (EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
        {
            Bomb bomb = new Bomb(wlm.world, wlm,getAttackDamage(),union.w.explosionDestroyTerrain,Bomb.Type.INSTANT_BOMB);
            EntityUtil.attackRange(wlm,bomb,null,getDistance() / 7,1);
        }
    }
}