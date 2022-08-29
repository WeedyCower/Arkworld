package com.weedycow.arkworld.util;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.entity.enemy.split.normal.OriginiutantExcrescence;
import com.weedycow.arkworld.entity.enemy.split.normal.OriginiutantTumor;
import com.weedycow.arkworld.entity.enemy.union.boss.Patriot;
import com.weedycow.arkworld.entity.enemy.union.elite.GuerrillaShieldGuard;
import com.weedycow.arkworld.entity.enemy.union.elite.GuerrillaSiegebreaker;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.weedylib.entity.EnumCamps;
import com.weedycow.weedylib.entity.WLM;
import com.weedycow.weedylib.util.AoeRangeUtil;
import com.weedycow.weedylib.util.EntityUtil;
import com.weedycow.weedylib.util.MathUtil;
import com.weedycow.weedylib.util.StringUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import scala.util.Random;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ArkEntityUtil
{
    public static String getName(String ID)
    {
        return Arkworld.MODID+"."+ StringUtil.lineToHump(ID);
    }

    public static Operator getOperator(World world, String uuid)
    {
        for(Entity entity : world.loadedEntityList)
        {
            if(entity instanceof Operator)
            {
                if (((Operator) entity).getUuid().equals(uuid))
                {
                    return (Operator) entity;
                }
            }
        }
        return null;
    }

    public static void attackAOE(EntityLivingBase entityThis, @Nullable EnumCamps examples, DamageSource source, float range, float attackDamage, EnumState state, int level, int tick)
    {
        AoeRangeUtil AABB = new AoeRangeUtil(entityThis,range);
        for (EntityLivingBase target : entityThis.world.getEntitiesWithinAABB(EntityLivingBase.class, AABB))
        {
            if(examples == null)
            {
                if (target != entityThis)
                {
                    target.attackEntityFrom(source, attackDamage);
                    entityThis.attackEntityAsMob(target);
                    if(state!=null && target.hasCapability(CapabilityRegistry.capState,null))
                        new CapabilityState.Process(target).addFunction(state,level,tick);
                }
            }
            else
            {
                if (target != entityThis && (!(target instanceof WLM) || ((WLM) target).getCamp() != examples))
                {
                    target.attackEntityFrom(source, attackDamage);
                    entityThis.attackEntityAsMob(target);
                    if(state!=null && target.hasCapability(CapabilityRegistry.capState,null))
                        new CapabilityState.Process(target).addFunction(state,level,tick);
                }
            }
        }
    }

    public static void splitOriginiutantExcrescence(EntityLiving entityThis,int count)
    {
        double x = entityThis.posX;
        double y = entityThis.posY;
        double z = entityThis.posZ;

        if(!entityThis.world.isRemote)
        {
            if(count>=1)
            {
                OriginiutantExcrescence originiutantExcrescence = new OriginiutantExcrescence(entityThis.world);
                originiutantExcrescence.setPosition(x+MathUtil.getRandomDouble(),y,z+MathUtil.getRandomDouble());
                entityThis.world.spawnEntity(originiutantExcrescence);
            }
            if(count>=2)
            {
                OriginiutantExcrescence originiutantExcrescence = new OriginiutantExcrescence(entityThis.world);
                originiutantExcrescence.setPosition(x+MathUtil.getRandomDouble(),y,z+MathUtil.getRandomDouble());
                entityThis.world.spawnEntity(originiutantExcrescence);
            }
            if(count>=3)
            {
                OriginiutantExcrescence originiutantExcrescence = new OriginiutantExcrescence(entityThis.world);
                originiutantExcrescence.setPosition(x+MathUtil.getRandomDouble(),y,z+MathUtil.getRandomDouble());
                entityThis.world.spawnEntity(originiutantExcrescence);
            }
            if(count>=4)
            {
                OriginiutantExcrescence originiutantExcrescence = new OriginiutantExcrescence(entityThis.world);
                originiutantExcrescence.setPosition(x+MathUtil.getRandomDouble(),y,z+MathUtil.getRandomDouble());
                entityThis.world.spawnEntity(originiutantExcrescence);
            }
            if(count>=5)
            {
                OriginiutantExcrescence originiutantExcrescence = new OriginiutantExcrescence(entityThis.world);
                originiutantExcrescence.setPosition(x+MathUtil.getRandomDouble(),y,z+MathUtil.getRandomDouble());
                entityThis.world.spawnEntity(originiutantExcrescence);
            }
            if(count>=6)
            {
                OriginiutantExcrescence originiutantExcrescence = new OriginiutantExcrescence(entityThis.world);
                originiutantExcrescence.setPosition(x+MathUtil.getRandomDouble(),y,z+MathUtil.getRandomDouble());
                entityThis.world.spawnEntity(originiutantExcrescence);
            }
        }
    }

    public static void splitOriginiutantTumor(EntityLiving entityThis,int count)
    {
        double x = entityThis.posX;
        double y = entityThis.posY;
        double z = entityThis.posZ;

        if(!entityThis.world.isRemote)
        {
            if(count>=1)
            {
                OriginiutantTumor originiutantTumor = new OriginiutantTumor(entityThis.world);
                originiutantTumor.setPosition(x+MathUtil.getRandomDouble(),y,z+MathUtil.getRandomDouble());
                entityThis.world.spawnEntity(originiutantTumor);
            }
            if(count>=2)
            {
                OriginiutantTumor originiutantTumor = new OriginiutantTumor(entityThis.world);
                originiutantTumor.setPosition(x+MathUtil.getRandomDouble(),y,z+MathUtil.getRandomDouble());
                entityThis.world.spawnEntity(originiutantTumor);
            }
            if(count>=3)
            {
                OriginiutantTumor originiutantTumor = new OriginiutantTumor(entityThis.world);
                originiutantTumor.setPosition(x+MathUtil.getRandomDouble(),y,z+MathUtil.getRandomDouble());
                entityThis.world.spawnEntity(originiutantTumor);
            }
            if(count>=4)
            {
                OriginiutantTumor originiutantTumor = new OriginiutantTumor(entityThis.world);
                originiutantTumor.setPosition(x+MathUtil.getRandomDouble(),y,z+MathUtil.getRandomDouble());
                entityThis.world.spawnEntity(originiutantTumor);
            }
            if(count>=5)
            {
                OriginiutantTumor originiutantTumor = new OriginiutantTumor(entityThis.world);
                originiutantTumor.setPosition(x+MathUtil.getRandomDouble(),y,z+MathUtil.getRandomDouble());
                entityThis.world.spawnEntity(originiutantTumor);
            }
            if(count>=6)
            {
                OriginiutantTumor originiutantTumor = new OriginiutantTumor(entityThis.world);
                originiutantTumor.setPosition(x+MathUtil.getRandomDouble(),y,z+MathUtil.getRandomDouble());
                entityThis.world.spawnEntity(originiutantTumor);
            }
        }
    }

    public static void patriotTransformation(WLM entityLiving)
    {
        if(EntityUtil.atSetIntervals(entityLiving,16,0,0))
        {
            EntityUtil.attackAOE(entityLiving, EnumCamps.UNION, DamageSource.GENERIC, entityLiving.getAttackRange()*2, entityLiving.getAttackDamage());
        }

        if(EntityUtil.atSetIntervals(entityLiving,60,0,0) && ArkConfig.creature.mob.union.patriot.spawnGuerrillaSiegebreaker && !entityLiving.world.isRemote)
        {
            GuerrillaSiegebreaker guerrillaSiegebreaker = new GuerrillaSiegebreaker(entityLiving.world);
            guerrillaSiegebreaker.setType(new Random().nextInt(2));
            guerrillaSiegebreaker.setPosition(entityLiving.posX + MathUtil.getRandomDouble() * 12, entityLiving.posY, entityLiving.posZ + MathUtil.getRandomDouble() * 12);
            entityLiving.world.spawnEntity(guerrillaSiegebreaker);
        }

        ParticleList.patriotParticle(entityLiving);
    }

    public static void taunt(WLM entity, float range)
    {
        for(EntityLivingBase entityLivingBase : entity.world.getEntitiesWithinAABB(EntityLivingBase.class,new AoeRangeUtil(entity,range)))
        {
            if(entityLivingBase instanceof EntityPlayer && noPatriot(entity,range) && isNearest(entity,range))
            {
                List<Item> equipmentList = new ArrayList<>();
                entity.getEquipmentAndArmor().forEach((x) -> equipmentList.add(x.getItem()));
                List<Item> armorList = equipmentList.subList(2, 6);
                boolean hasHead = armorList.contains(ItemRegistry.PATRIOT_HEAD);

                if(!hasHead)
                {
                    double x0 = entity.posX - entityLivingBase.posX;
                    double z0 = entity.posZ - entityLivingBase.posZ;
                    double y0 = entity.posY - 1 - entityLivingBase.posY;
                    double l = MathHelper.sqrt(x0 * x0 + z0 * z0);
                    float y = (float) (MathHelper.atan2(z0, x0) * (180D / Math.PI)) - 90.0F;
                    float p = (float) (-(MathHelper.atan2(y0, l) * (180D / Math.PI)));
                    entityLivingBase.rotationPitch = updateRotation(entityLivingBase.rotationPitch, p, 30F);
                    entityLivingBase.rotationYaw = updateRotation(entityLivingBase.rotationYaw, y, 30F);
                }
            }
            if ((!(entityLivingBase instanceof WLM) || ((WLM) entityLivingBase).getCamp() != EnumCamps.UNION)
                    && entityLivingBase != entity && !noPatriot(entity, range) && entityLivingBase instanceof EntityLiving && isNearest(entity,range))
            {
                ((EntityLiving)entityLivingBase).setAttackTarget(entity);
            }
        }
    }

    public static boolean noPatriot(WLM entity, float range)
    {
        for(EntityLivingBase entityLivingBase : entity.world.getEntitiesWithinAABB(EntityLivingBase.class,new AoeRangeUtil(entity,range)))
        {
            return !(entityLivingBase instanceof Patriot);
        }
        return false;
    }

    public static boolean isNearest(WLM entity, float range)
    {
        for(EntityLivingBase entityLivingBase : entity.world.getEntitiesWithinAABB(EntityLivingBase.class,new AoeRangeUtil(entity,range)))
        {
            if(entityLivingBase instanceof GuerrillaShieldGuard && entityLivingBase != entity)
            {
                if(((GuerrillaShieldGuard) entityLivingBase).getAttackTarget()!=null && entity.getAttackTarget()!=null)
                {
                    double distanceOther = EntityUtil.distanceToTarget((EntityLiving) entityLivingBase);
                    double distanceMe = EntityUtil.distanceToTarget(entity);
                    if(distanceMe>distanceOther)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static float updateRotation(float angle, float targetAngle, float maxIncrease)
    {
        float i = MathHelper.wrapDegrees(targetAngle - angle);
        if (i > maxIncrease) {i = maxIncrease;}
        if (i < -maxIncrease) {i = -maxIncrease;}
        return angle + i;
    }
}
