package com.weedycow.arkworld.entity.weapon;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.util.ParticleList;
import com.weedycow.weedylib.entity.EnumCamps;
import com.weedycow.weedylib.entity.WLDamageSource;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.entity.WLT;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class FireBall extends ProjectileT
{
    protected Type weaponType;
    protected boolean damagesTerrain;
    public static final String ID = "fire_ball";
    public static final String NAME = Arkworld.MODID + ".fireBall";

    public FireBall(World worldIn)
    {
        super(worldIn);
    }

    public FireBall(World world, double x, double y, double z, float damage, boolean damagesTerrain, Type weaponType)
    {
        super(world, ID, x, y, z);
        if(weaponType==Type.ENERGY_CRYSTAL) setNoGravity(true);
        this.damagesTerrain = damagesTerrain;
        setTypeString(weaponType.toString());
        setSize(1,1);
        setDamage(damage);
    }

    public FireBall(World world, EntityLivingBase shooter, float damage, boolean damagesTerrain, Type weaponType)
    {
        super(world, ID, shooter);
        if(weaponType==Type.ENERGY_CRYSTAL) setNoGravity(true);
        this.damagesTerrain = damagesTerrain;
        setTypeString(weaponType.toString());
        setSize(1,1);
        setDamage(damage);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setString("weapon_type",getTypeString());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("weapon_type"))
            setTypeString(compound.getString("weapon_type"));
    }

    public enum Type
    {
        ENERGY_CRYSTAL,
        ENDLESS_FIRE;

        @Override
        public String toString()
        {
            return name().toLowerCase();
        }
    }
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(100);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(10);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(this,10,0,0))
            ParticleList.fireBall(this);

        return PlayState.CONTINUE;
    }

    @Override
    protected float getGravityVelocity()
    {
        if(Objects.equals(getTypeString(), Type.ENERGY_CRYSTAL.toString()))
            return 0;
        else
            return 0.03F;
    }

    @Override
    protected void countdownZero()
    {
        if (!world.isRemote)
            world.createExplosion(thrower, posX, posY, posZ, getDamage() / 14, damagesTerrain);
        if(Objects.equals(getTypeString(), Type.ENERGY_CRYSTAL.toString()) || Objects.equals(getTypeString(), Type.ENDLESS_FIRE.toString()))
            EntityUtil.attackAOE(this, EnumCamps.UNION, DamageSource.causeThrownDamage(this,thrower),2,0);
    }

    @Override
    protected void onImpact(RayTraceResult rayTraceResult)
    {
        if(rayTraceResult.entityHit != thrower && !(rayTraceResult.entityHit instanceof WLT) &&  (!(rayTraceResult.entityHit instanceof ArkMob) || ((ArkMob)rayTraceResult.entityHit).getCamp()!=EnumCamps.UNION))
        {
            if (!world.isRemote)
            {
                world.createExplosion(thrower, posX, posY, posZ, getDamage() / 14, damagesTerrain);
                this.setDead();
            }
            if(Objects.equals(getTypeString(), Type.ENERGY_CRYSTAL.toString()) || getTypeString()==Type.ENDLESS_FIRE.toString())
                EntityUtil.attackAOE(this, EnumCamps.UNION, DamageSource.causeThrownDamage(this,thrower),2,0);
        }
    }

    @Override
    public void registerControllers(AnimationData animationData)
    {
        animationData.addAnimationController(new AnimationController(this,"controller",1,this::PlayState));
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return source == WLDamageSource.WEAPON_CLEAR;
    }
}
