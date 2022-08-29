package com.weedycow.arkworld.entity.enemy.cthulhu.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class ShellSeaRunner extends ArkMob
{
    public static final String ID = "shell_sea_runner";
    public static final String NAME = Arkworld.MODID + ".shellSeaRunner";

    public ShellSeaRunner(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.SEA_MONSTER, EnumCamps.CTHULHU, EnumStatus.NORMAL, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(0.8f, 1f);
        experienceValue = cthulhu.shellSeaRunner.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(cthulhu.shellSeaRunner.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(cthulhu.shellSeaRunner.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(cthulhu.shellSeaRunner.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(cthulhu.shellSeaRunner.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(cthulhu.shellSeaRunner.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(cthulhu.shellSeaRunner.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(cthulhu.shellSeaRunner.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(cthulhu.shellSeaRunner.attackDamage);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),15*getAttackInterval()/cthulhu.shellSeaRunner.attackInterval,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.shell_sea_runner.attack", false));
            controllerAttack.markNeedsReload();
        }

        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.shell_sea_runner.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.shell_sea_runner.idle", true));

        controllerAttack.setAnimationSpeed((float)cthulhu.shellSeaRunner.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/cthulhu.shellSeaRunner.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<ShellSeaRunner> controllerAttack = new AnimationController<>(this,"controllerAttack",1,this::PlayState);
    AnimationController<ShellSeaRunner> controllerMove = new AnimationController<>(this,"controllerMove",1,this::PlayState);

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
        addHP(getType()*cthulhu.shellSeaRunner.leaderMaxHealth);
        addAD(getType()*cthulhu.shellSeaRunner.leaderAttackDamage);
        addDP(getType()*cthulhu.shellSeaRunner.leaderDefensivePower);
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm, getAttackRange(), getAttackDamage(), DamageSource.causeMobDamage(this));
    }
}
