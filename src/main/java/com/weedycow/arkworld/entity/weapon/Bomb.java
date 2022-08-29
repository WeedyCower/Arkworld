package com.weedycow.arkworld.entity.weapon;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.weedylib.entity.WLDamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

public class Bomb extends ProjectileT
{
    protected boolean vertigo;
    protected int vertigoTime;
    protected boolean damagesTerrain;
    public static final String ID = "bomb";
    public static final String NAME = Arkworld.MODID + ".bomb";
    public static Type[] grenadeGun = {Type.GRENADE};
    public static Type[] d12 = {Type.INSTANT_BOMB};

    public Bomb(World worldIn)
    {
        super(worldIn);
    }

    public Bomb(World world, double x, double y, double z, float damage, boolean damagesTerrain, Type type)
    {
        super(world, ID, x, y, z);
        this.damagesTerrain = damagesTerrain;
        setTypeString(type.toString());
        setDamage(damage);
    }

    public Bomb(World world, EntityLivingBase shooter, float damage, boolean damagesTerrain, Type type)
    {
        super(world, ID, shooter);
        this.damagesTerrain = damagesTerrain;
        setTypeString(type.toString());
        setDamage(damage);
    }

    @Override
    protected void onImpact(RayTraceResult rayTraceResult)
    {
        if(rayTraceResult.entityHit != thrower && rayTraceResult.entityHit != this)
        {
            double x = rayTraceResult.hitVec.x;
            double y = rayTraceResult.hitVec.y;
            double z = rayTraceResult.hitVec.z;

            playSound(SoundRegistry.W_EXPLODE,1,1);

            if (!world.isRemote)
            {
                if (getTypeString().equals(Type.FIREBOMB.toString()) && rayTraceResult.entityHit != null) rayTraceResult.entityHit.setFire(6);

                world.createExplosion(thrower, x, y, z, getDamage() / 14f, damagesTerrain);

                if(rayTraceResult.entityHit instanceof EntityLivingBase && vertigo)
                    new CapabilityState.Process((EntityLivingBase) rayTraceResult.entityHit).addFunctionOnlyTick(EnumState.VERTIGO,getVertigoTime());

                this.setDead();
            }
        }
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

    @Override
    @ParametersAreNonnullByDefault
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return source == WLDamageSource.WEAPON_CLEAR;
    }

    public boolean isVertigo()
    {
        return vertigo;
    }

    public void setVertigo(boolean vertigo)
    {
        this.vertigo = vertigo;
    }

    public void setVertigoTime(int vertigoTime)
    {
        this.vertigoTime = vertigoTime;
    }

    public int getVertigoTime()
    {
        return vertigoTime;
    }

    public enum Type
    {
        GRENADE,
        INSTANT_BOMB,
        C4,
        FIREBOMB;

        @Override
        public String toString()
        {
            return name().toLowerCase();
        }
    }
}
