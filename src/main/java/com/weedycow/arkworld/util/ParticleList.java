package com.weedycow.arkworld.util;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.entity.enemy.kazdel.normal.SarkazSentinel;
import com.weedycow.arkworld.entity.enemy.split.boss.Evolution;
import com.weedycow.weedylib.util.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ParticleList
{
    public static void SarkazSentinel(SarkazSentinel entityLiving)
    {
        if(ArkConfig.common.particle)
        {
            double x = entityLiving.posX;
            double y = entityLiving.posY;
            double z = entityLiving.posZ;
            for (int l = 0; l < 4*entityLiving.getAttackRange(); ++l)
            {
                double x0 = x + MathUtil.getRandomDouble()*2*entityLiving.getAttackRange();
                double y0 = y + MathUtil.getRandomDouble();
                double z0 = z + MathUtil.getRandomDouble()*2*entityLiving.getAttackRange();
                entityLiving.world.spawnParticle(EnumParticleTypes.REDSTONE, x0, y0, z0, 0, 0, 0);
            }
        }
    }

    public static void hotFloorParticle(World world, BlockPos pos)
    {
        if(ArkConfig.common.particle)
        {

            double x = pos.getX();
            double y = pos.getY()+1;
            double z = pos.getZ();
            for (int l = 0; l < 3; ++l)
            {
                double x0 = x + Math.random();
                double y0 = y + Math.random();
                double z0 = z + Math.random();
                world.spawnParticle(EnumParticleTypes.LAVA, x0, y0, z0, 0, 0.1, 0);
            }
        }
    }

    public static void faustParticle(EntityLiving entityLiving)
    {
        if(ArkConfig.common.particle)
        {
            double x = entityLiving.posX;
            double y = entityLiving.posY+1;
            double z = entityLiving.posZ;
            for (int l = 0; l < 3; ++l)
            {
                double x0 = x + MathUtil.getRandomDouble();
                double y0 = y + MathUtil.getRandomDouble();
                double z0 = z + MathUtil.getRandomDouble();
                double x1 = MathUtil.getRandomDouble() * 0.1;
                double y1 = MathUtil.getRandomDouble() * 0.1;
                double z1 = MathUtil.getRandomDouble() * 0.1;
                entityLiving.world.spawnParticle(EnumParticleTypes.CLOUD, x0, y0, z0, x1, y1, z1);
            }
        }
    }

    public static void invisibleParticle(EntityLivingBase entityLiving)
    {
        if(ArkConfig.common.particle)
        {
            double x = entityLiving.posX;
            double y = entityLiving.posY+1;
            double z = entityLiving.posZ;
            for (int l = 0; l < 1; ++l)
            {
                double x0 = x + MathUtil.getRandomDouble();
                double y0 = y + MathUtil.getRandomDouble();
                double z0 = z + MathUtil.getRandomDouble();
                double x1 = MathUtil.getRandomDouble() * 0.1;
                double y1 = MathUtil.getRandomDouble() * 0.1;
                double z1 = MathUtil.getRandomDouble() * 0.1;
                entityLiving.world.spawnParticle(EnumParticleTypes.CRIT, x0, y0, z0, x1, y1, z1);
            }
        }
    }

    public static void camouflageParticle(EntityLivingBase entityLiving)
    {
        if(ArkConfig.common.particle)
        {
            double x = entityLiving.posX;
            double y = entityLiving.posY+1;
            double z = entityLiving.posZ;
            for (int l = 0; l < 1; ++l)
            {
                double x0 = x + MathUtil.getRandomDouble();
                double y0 = y + MathUtil.getRandomDouble();
                double z0 = z + MathUtil.getRandomDouble();
                double x1 = MathUtil.getRandomDouble() * 0.1;
                double y1 = MathUtil.getRandomDouble() * 0.1;
                double z1 = MathUtil.getRandomDouble() * 0.1;
                entityLiving.world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, x0, y0, z0, x1, y1, z1);
            }
        }
    }

    public static void frostnovaParticle(EntityLiving entityLiving)
    {
        if(ArkConfig.common.particle)
        {
            double x = entityLiving.posX;
            double y = entityLiving.posY+2;
            double z = entityLiving.posZ;
            for (int l = 0; l < 100; ++l)
            {
                double x0 = x + MathUtil.getRandomDouble()*15;
                double y0 = y + MathUtil.getRandomDouble();
                double z0 = z + MathUtil.getRandomDouble()*15;
                double x1 = MathUtil.getRandomDouble();
                double y1 = 0;
                double z1 = MathUtil.getRandomDouble();
                entityLiving.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, x0, y0, z0, x1, y1, z1);
            }
        }
    }

    public static void patriotParticle(EntityLiving entityLiving)
    {
        if(ArkConfig.common.particle)
        {
            double x = entityLiving.posX;
            double y = entityLiving.posY;
            double z = entityLiving.posZ;
            for (int l = 0; l < 100; ++l)
            {
                double x0 = x + MathUtil.getRandomDouble()*30;
                double y0 = y + MathUtil.getRandomDouble();
                double z0 = z + MathUtil.getRandomDouble()*30;
                entityLiving.world.spawnParticle(EnumParticleTypes.SPELL_MOB, x0, y0, z0, 0, 0, 0);
            }
        }
    }

    public static void mayfestParticle(EntityLiving entityLiving)
    {
        if(ArkConfig.common.particle)
        {
            double x = entityLiving.posX;
            double y = entityLiving.posY;
            double z = entityLiving.posZ;
            for (int l = 0; l < 100; ++l)
            {
                double x0 = x + MathUtil.getRandomDouble()*30;
                double y0 = y + MathUtil.getRandomDouble();
                double z0 = z + MathUtil.getRandomDouble()*30;
                entityLiving.world.spawnParticle(EnumParticleTypes.SPIT, x0, y0, z0, 0, 0, 0);
            }
        }
    }

    public static void skullshattererParticle(EntityLivingBase entityLiving)
    {
        if(ArkConfig.common.particle && entityLiving.getHealth()/entityLiving.getMaxHealth()<=0.5)
        {
            double x = entityLiving.posX;
            double y = entityLiving.posY+1;
            double z = entityLiving.posZ;
            for (int l = 0; l < 3; ++l)
            {
                double x0 = x + MathUtil.getRandomDouble();
                double y0 = y + MathUtil.getRandomDouble();
                double z0 = z + MathUtil.getRandomDouble();
                entityLiving.world.spawnParticle(EnumParticleTypes.REDSTONE, x0, y0, z0, 0, 0, 0);
            }
        }
    }

    public static void amiyaParticle(EntityLivingBase entityLiving)
    {
        if(ArkConfig.common.particle)
        {
            double x = entityLiving.posX;
            double y = entityLiving.posY+1;
            double z = entityLiving.posZ;
            double x0 = x + MathUtil.getRandomDouble()*2;
            double y0 = y + MathUtil.getRandomDouble()*2;
            double z0 = z + MathUtil.getRandomDouble()*2;
            entityLiving.world.spawnParticle(EnumParticleTypes.SPELL_MOB, x0, y0, z0, 0, 0, 0);
        }
    }

    public static void attackSpeedParticle(EntityLivingBase entityLiving)
    {
        if(ArkConfig.common.particle)
        {
            double x = entityLiving.posX;
            double y = entityLiving.posY+1;
            double z = entityLiving.posZ;
            for (int l = 0; l < 3; ++l)
            {
                double x0 = x + MathUtil.getRandomDouble()*2;
                double y0 = y + MathUtil.getRandomDouble()*2;
                double z0 = z + MathUtil.getRandomDouble()*2;
                entityLiving.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, x0, y0, z0, 0, 0, 0);
            }
        }
    }

    public static void blackSnakeAttackParticle(EntityLiving entityLiving)
    {
        if(ArkConfig.common.particle)
        {
            double x = entityLiving.getLookVec().x;
            double y = entityLiving.getLookVec().y;
            double z = entityLiving.getLookVec().z;
            double x0 = entityLiving.posX;
            double y0 = entityLiving.posY+1;
            double z0 = entityLiving.posZ;
            for (int l = 0; l < 50; ++l)
            {
                double d0 = (x0 + MathUtil.getRandomDouble()*3);
                double d1 = (y0 + MathUtil.getRandomDouble());
                double d2 = (z0 + MathUtil.getRandomDouble()*3);
                double d3 = (x + MathUtil.getRandomDouble());
                double d4 = (y + MathUtil.getRandomDouble());
                double d5 = (z + MathUtil.getRandomDouble());
                entityLiving.world.spawnParticle(EnumParticleTypes.LAVA, d0, d1, d2, d3, d4, d5);
            }
        }
    }

    public static void blackSnakeSkillParticle(EntityLiving entityLiving)
    {
        if(ArkConfig.common.particle)
        {
            double x = entityLiving.posX;
            double y = entityLiving.posY+1;
            double z = entityLiving.posZ;
            for (int l = 0; l < 100; ++l)
            {
                double x0 = x + MathUtil.getRandomDouble()*9;
                double y0 = y + MathUtil.getRandomDouble();
                double z0 = z + MathUtil.getRandomDouble()*9;
                entityLiving.world.spawnParticle(EnumParticleTypes.FLAME, x0, y0, z0, 0, 0, 0);
            }
        }
    }

    public static void blackSnakeMeteoriteParticle(EntityLiving entityLiving)
    {
        if(ArkConfig.common.particle)
        {
            double x = entityLiving.posX;
            double y = entityLiving.posY+1;
            double z = entityLiving.posZ;
                for (int l = 0; l < ArkMob.union.blackSnake.endlessFireRange *5; ++l)
            {
                double x0 = x + MathUtil.getRandomDouble()*ArkConfig.creature.mob.union.blackSnake.endlessFireRange;
                double y0 = y + MathUtil.getRandomDouble()*ArkConfig.creature.mob.union.blackSnake.endlessFireRange;
                double z0 = z + MathUtil.getRandomDouble()*ArkConfig.creature.mob.union.blackSnake.endlessFireRange;
                entityLiving.world.spawnParticle(EnumParticleTypes.LAVA, x0, y0, z0, 0, -1, 0);
            }
        }
    }

    public static void casterParticle(EntityLivingBase entityLiving, BlockPos pos)
    {
        if(ArkConfig.common.particle)
        {
            double x = pos.getX();
            double y = pos.getY()+1;
            double z = pos.getZ();
            for (int l = 0; l < 10; ++l)
            {
                double x0 = x + MathUtil.getRandomDouble()*2;
                double y0 = y + MathUtil.getRandomDouble()*2;
                double z0 = z + MathUtil.getRandomDouble()*2;
                entityLiving.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, x0, y0, z0, 0, 0, 0);
            }
        }
    }

    public static void evolutionAlarm(Evolution entityLiving, int countdown)
    {
        if(ArkConfig.common.particle)
        {
            double posX = entityLiving.posX;
            double posY = entityLiving.posY;
            double posZ = entityLiving.posZ;
            for (int l = 0; l < 360; ++l)
            {
                MathUtil.Point point = MathUtil.getCirclePoint(posX, posZ, (int)((float)ArkConfig.creature.mob.split.evolution.alarmTime / countdown * entityLiving.getAttackRange() / ArkConfig.creature.mob.split.evolution.alarmTime), l);
                double x = point.x;
                double z = point.y;
                entityLiving.world.spawnParticle(EnumParticleTypes.REDSTONE, x, posY, z, 0, 0, 0);
            }
        }
    }

    public static void evolutionCover(Evolution entityLiving)
    {
        if(ArkConfig.common.particle)
        {
            double posX = entityLiving.posX;
            double posY = entityLiving.posY+2;
            double posZ = entityLiving.posZ;

            for(double t = 0; t<=Math.PI*2; t+=0.2)
            {
                for (double a = 0; a<=Math.PI*2; a+=0.2)
                {
                    entityLiving.world.spawnParticle(EnumParticleTypes.REDSTONE, posX + entityLiving.height * Math.cos(t) * Math.cos(a), posY + entityLiving.height * Math.cos(t) * Math.sin(a), posZ + entityLiving.height * Math.sin(t),0,0,0);
                }
            }
        }
    }

    public static void fireBall(Entity entityLiving)
    {
        if(ArkConfig.common.particle)
        {
            double x = entityLiving.posX;
            double y = entityLiving.posY;
            double z = entityLiving.posZ;
            for (int l = 0; l < 3; ++l)
            {
                double x0 = x + MathUtil.getRandomDouble()*2;
                double y0 = y + MathUtil.getRandomDouble()*2;
                double z0 = z + MathUtil.getRandomDouble()*2;
                entityLiving.world.spawnParticle(EnumParticleTypes.LAVA, x0, y0, z0, entityLiving.motionX, entityLiving.motionY, entityLiving.motionZ);
            }
        }
    }
}
