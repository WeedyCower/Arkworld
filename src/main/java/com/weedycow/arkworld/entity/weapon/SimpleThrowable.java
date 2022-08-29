package com.weedycow.arkworld.entity.weapon;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.entity.operator.caster.Amiya;
import com.weedycow.arkworld.registry.BlockRegistry;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.weedylib.entity.WLDamageSource;
import com.weedycow.weedylib.util.AoeRangeUtil;
import com.weedycow.weedylib.util.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class SimpleThrowable extends ProjectileT
{
    protected Type weaponType;
    public static final String ID = "simple_throwable";
    public static final String NAME = Arkworld.MODID + ".simpleThrowable";

    public SimpleThrowable(World world)
    {
        super(world);
    }

    public SimpleThrowable(World world, double x, double y, double z, float damage, Type type)
    {
        super(world, ID, x, y, z);
        setTypeString(type.toString());
        setDamage(damage);
    }

    public SimpleThrowable(World world, EntityLivingBase shooter, float damage, Type type)
    {
        super(world, ID, shooter);
        setTypeString(type.toString());
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

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(getTypeString().equals(Type.ICICLE.toString()))
            controllerBig.setAnimation(new AnimationBuilder().addAnimation("animation.icicle.big", true));
        if (getTypeString().equals(Type.AMIYA_BALL.toString()) && thrower instanceof Amiya && ((Amiya) thrower).getSelectSkill() == 3 && ((Amiya) thrower).getSkillTime() > 0)
            controllerBig.setAnimation(new AnimationBuilder().addAnimation("animation.amiya_ball.big", true));
        return PlayState.CONTINUE;
    }

    AnimationController<SimpleThrowable> controllerBig = new AnimationController<>(this, "controllerBig", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerBig);
    }

    public void addParticleEffect(EnumParticleTypes type)
    {
        world.spawnParticle(type,posX + MathUtil.getRandomDouble()/2,posY + MathUtil.getRandomDouble()/2, posZ + MathUtil.getRandomDouble()/2,0,0,0);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if (getTypeString().equals(Type.ICE_CRYSTAL.toString()))
            addParticleEffect(EnumParticleTypes.SPELL_INSTANT);
        if (getTypeString().equals(Type.ICICLE.toString()))
            addParticleEffect(EnumParticleTypes.SPELL_INSTANT);
        if (getTypeString().equals(Type.HEALING_BALL.toString()))
            addParticleEffect(EnumParticleTypes.VILLAGER_HAPPY);
        if (getTypeString().equals(Type.MAGICAL_BALL.toString()))
            addParticleEffect(EnumParticleTypes.PORTAL);
        if (getTypeString().equals(Type.AMIYA_BALL.toString()))
            addParticleEffect(EnumParticleTypes.SPELL_MOB);
    }

    public enum Type
    {
        ICE_CRYSTAL,
        ICICLE,
        HEALING_BALL,
        MAGICAL_BALL,
        AMIYA_BALL;

        @Override
        public String toString()
        {
            return name().toLowerCase();
        }
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        Entity target = result.entityHit;

        if (!world.isRemote && target!=null)
        {
            if (Objects.equals(getTypeString(), Type.ICICLE.toString()))
            {
                if(result.typeOfHit==RayTraceResult.Type.BLOCK)
                {
                    BlockPos pos = new BlockPos(posX,posY,posZ);
                    world.setBlockState(pos.down(), BlockRegistry.SEALED_FLOOR.getDefaultState());
                }
            }

            if (Objects.equals(getTypeString(), Type.ICE_CRYSTAL.toString()))
            {
                if ((thrower instanceof EntityPlayer||(thrower instanceof ArkMob&&(!(target instanceof ArkMob)
                        || ((ArkMob) target).getCamp() != ((ArkMob) thrower).getCamp())))&&target instanceof EntityLivingBase && target != getThrower() && target!=this)
                    target.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), getDamage());
            }

            if (Objects.equals(getTypeString(), Type.HEALING_BALL.toString()))
            {
                if (((target instanceof EntityLivingBase && target != getThrower()) || result.typeOfHit==RayTraceResult.Type.BLOCK))
                {
                    for (EntityLivingBase t : world.getEntitiesWithinAABB(EntityLivingBase.class, new AoeRangeUtil(this,1)))
                    {
                        if(t instanceof EntityPlayer || t instanceof Operator)
                            t.heal(getDamage());
                        else if(t.hasCapability(CapabilityRegistry.capState,null))
                            new CapabilityState.Process(t).addFunctionOnlyTick(EnumState.BANEAL, (int) (getDamage()*2));
                    }
                }
            }

            if (Objects.equals(getTypeString(), Type.MAGICAL_BALL.toString()))
            {
                if ((thrower instanceof EntityPlayer||(thrower instanceof ArkMob&&(!(target instanceof ArkMob)
                        || ((ArkMob) target).getCamp() != ((ArkMob) thrower).getCamp())))&&target instanceof EntityLivingBase && target != getThrower() && target!=this)
                    target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, getThrower()), getDamage());
            }

            if (Objects.equals(getTypeString(), Type.AMIYA_BALL.toString()))
            {
                if ((thrower instanceof Operator && target!=((Operator) thrower).getMaster() && !(target instanceof Operator)))
                {
                    if(thrower instanceof Amiya && ((Amiya) thrower).getSelectSkill()==3 && ((Amiya) thrower).getSkillTime()>0)
                        target.attackEntityFrom(DamageSource.GENERIC,getDamage());
                    else
                        target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, getThrower()), getDamage());
                }
            }

            this.setDead();
        }
    }

    @Override
    protected float getGravityVelocity()
    {
        return 0;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return source == WLDamageSource.WEAPON_CLEAR;
    }
}
