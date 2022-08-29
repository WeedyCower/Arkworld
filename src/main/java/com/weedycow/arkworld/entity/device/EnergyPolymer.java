package com.weedycow.arkworld.entity.device;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.weapon.FireBall;
import com.weedycow.weedylib.entity.WLM;
import com.weedycow.weedylib.entity.WLMAttributes;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class EnergyPolymer extends ArkDevice
{
    WLM attacker;
    public static final String ID = "energy_polymer";
    public static final String NAME = Arkworld.MODID + ".energyPolymer";
    AnimationController<EnergyPolymer> controller = new AnimationController<>(this, "controller", 1, this::PlayState);

    public EnergyPolymer(World worldIn)
    {
        super(worldIn,ID);
        setSize(1.3f,1.3f);
        setCountdown(500);
    }

    public EnergyPolymer(World worldIn, WLM attacker)
    {
        super(worldIn,ID);
        setSize(1.3f,1.3f);
        this.attacker = attacker;
        setCountdown(500);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(300);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(0);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(10);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if(getCountdown() > 0) {setCountdown(getCountdown()-1);}
        if(getCountdown() <= 0 && !world.isRemote && attacker != null)
        {
            this.setDead();
            for(int i = 0; i<=3; i++)
            {
                FireBall fireBall = new FireBall(world, attacker, attacker.getAttackDamage(), ArkConfig.creature.mob.union.blackSnake.energyPolymerDestroyTerrain, FireBall.Type.ENERGY_CRYSTAL);
                fireBall.shoot(this,0,180-i*90,1,1);
                world.spawnEntity(fireBall);
            }
        }
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        controller.setAnimation(new AnimationBuilder().addAnimation("animation.energy_polymer.start", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controller);
    }
}
