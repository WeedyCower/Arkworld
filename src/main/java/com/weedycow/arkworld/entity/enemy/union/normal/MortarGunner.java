package com.weedycow.arkworld.entity.enemy.union.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.entity.weapon.Bomb;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class MortarGunner extends ArkMob
{
    public static final String ID = "mortar_gunner";
    public static final String NAME = Arkworld.MODID + ".mortarGunner";

    public MortarGunner(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.NORMAL, EnumAttackMethod.RANGE, EnumDamageTypes.PHYSICAL);
        this.setSize(0.8f, 2f);
        this.experienceValue = union.mortarGunner.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.mortarGunner.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.mortarGunner.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.mortarGunner.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.mortarGunner.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.mortarGunner.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.mortarGunner.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.mortarGunner.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.mortarGunner.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(getTargetState() && EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),35*getAttackInterval()/union.mortarGunner.attackInterval,0))
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.mortar_gunner.attack", false));
            controllerAttack.markNeedsReload();
        }

        if (event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.mortar_gunner.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.mortar_gunner.idle", true));

        controllerAttack.setAnimationSpeed((float)union.mortarGunner.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.mortarGunner.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<MortarGunner> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);
    AnimationController<MortarGunner> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data)
    {
        setType(rand.nextInt(2));
        addHP(getType()*union.mortarGunner.leaderMaxHealth);
        addAD(getType()*union.mortarGunner.leaderAttackDamage);
        addDP(getType()*union.mortarGunner.leaderDefensivePower);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void commonExecute(WLM wlm, EntityLivingBase target)
    {
        double d0 = target.posY - wlm.posY + wlm.height*6;
        double d1 = target.posX - wlm.posX;
        double d3 = target.posZ - wlm.posZ;

        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0) && !world.isRemote)
        {
            Bomb bomb = new Bomb(world, wlm, getAttackDamage(), union.mortarGunner.destroyTerrain, Bomb.Type.GRENADE);
            bomb.shoot(d1, d0, d3, getDistance() / 12, 1);
            world.spawnEntity(bomb);
        }
    }
}
