package com.weedycow.arkworld.entity.weapon;

import com.weedycow.arkworld.Arkworld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityOrundumTh extends EntityThrowable
{
    static double amount;
    public static final String ID = "entity_orundum_th";
    public static final String NAME = Arkworld.MODID + ".entityOrundumTh";
    public EntityOrundumTh(World world) { super(world); }
    public EntityOrundumTh(World world, double x, double y, double z) { super(world, x, y, z); }

    public EntityOrundumTh(World world, EntityLivingBase shooter, double damage)
    {
        super(world, shooter);
        amount = damage;
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        Entity target = result.entityHit;
        if(target instanceof EntityLivingBase)
        {
            DamageSource source = DamageSource.causeThrownDamage(this,getThrower());
            if (!this.world.isRemote && target!= getThrower())
            {
                target.attackEntityFrom(source, (float) amount);
                target.world.createExplosion(getThrower(),target.posX,target.posY,target.posZ,3,false);
            }
        }
        else if(!world.isRemote && result.entityHit != null && getThrower()!= null){this.world.createExplosion(getThrower(),result.getBlockPos().getX(),result.getBlockPos().getY(),result.getBlockPos().getZ(),6,false);}
        else {this.setDead();}
    }
}
