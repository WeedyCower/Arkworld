package com.weedycow.arkworld.ai;

import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.entity.enemy.union.boss.BlackSnake;
import com.weedycow.arkworld.entity.weapon.FireBall;
import com.weedycow.weedylib.entity.EnumCamps;
import com.weedycow.weedylib.util.EntityUtil;
import com.weedycow.weedylib.util.MathUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.DamageSource;

public class ArkEntityAiBlackSnakeMeteorite extends EntityAIBase
{
    EntityLiving entity;

    public ArkEntityAiBlackSnakeMeteorite(EntityLiving entity)
    {
        this.entity = entity;
    }

    @Override
    public void updateTask()
    {
        if(((BlackSnake)entity).getStage()==2 || ((BlackSnake)entity).getStage()==5)
        {
            EntityUtil.attackAOE(entity, EnumCamps.UNION,DamageSource.FIREWORKS, ArkMob.union.blackSnake.endlessFireRange, 0.05f);
            double x = entity.posX + MathUtil.getRandomDouble() * ArkMob.union.blackSnake.endlessFireRange / 2;
            double y = entity.posY + 48 + Math.random() * 16;
            double z = entity.posZ + MathUtil.getRandomDouble() * ArkMob.union.blackSnake.endlessFireRange / 2;
            FireBall entityFireball = new FireBall(entity.world,entity,((BlackSnake)entity).getAttackDamage(),ArkMob.union.blackSnake.endlessFireDestroyTerrain, FireBall.Type.ENDLESS_FIRE);
            entityFireball.setPosition(x,entity.posY+64,z);
            entityFireball.shoot(0,-y,0,1,1);
            entity.world.spawnEntity(entityFireball);
        }
    }

    @Override
    public boolean shouldExecute()
    {
        return entity instanceof BlackSnake && (((BlackSnake)entity).getStage() == 2 || (((BlackSnake)entity).getStage() == 5));
    }
}