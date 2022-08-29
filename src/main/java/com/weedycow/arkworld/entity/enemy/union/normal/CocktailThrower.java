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

public class CocktailThrower extends ArkMob
{
    public static final String ID = "cocktail_thrower";
    public static final String NAME = Arkworld.MODID + ".cocktailThrower";

    public CocktailThrower(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.OTHER, EnumCamps.UNION, EnumStatus.NORMAL, EnumAttackMethod.RANGE, EnumDamageTypes.PHYSICAL);
        setSize(0.8f, 2f);
        experienceValue = union.cocktailThrower.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.cocktailThrower.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.cocktailThrower.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.cocktailThrower.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.cocktailThrower.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.cocktailThrower.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.cocktailThrower.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.cocktailThrower.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.cocktailThrower.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if (EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),20*getAttackInterval()/union.cocktailThrower.attackInterval,0) && getTargetState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.cocktail_thrower.attack", false));
            controllerAttack.markNeedsReload();
        }
        if (event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.cocktail_thrower.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.cocktail_thrower.idle", true));

        controllerAttack.setAnimationSpeed((float)union.cocktailThrower.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.cocktailThrower.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<CocktailThrower> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);
    AnimationController<CocktailThrower> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerMove);
        data.addAnimationController(controllerAttack);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data)
    {
        setType(rand.nextInt(2));
        addHP(getType()*union.cocktailThrower.leaderMaxHealth);
        addDP(getType()*union.cocktailThrower.leaderDefensivePower);
        addAD(getType()*union.cocktailThrower.leaderAttackDamage);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void commonExecute(WLM wlm, EntityLivingBase target)
    {
        Bomb bomb = new Bomb(world,wlm,getAttackDamage()/10,false, Bomb.Type.FIREBOMB);
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackRange(wlm,bomb,null,getDistance()/6,3);
    }
}
