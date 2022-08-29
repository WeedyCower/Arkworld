package com.weedycow.arkworld.ai;

import com.weedycow.arkworld.entity.enemy.kazdel.normal.SarkazSentinel;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class ArkEntityAiSarkazSentinel extends EntityAIBase
{
    int range;
    EntityLiving entity;

    public ArkEntityAiSarkazSentinel(EntityLiving entity, int range)
    {
        this.entity = entity;
        this.range = range;
    }

    @Override
    public void updateTask()
    {

    }

    @Override
    public boolean shouldExecute()
    {
        return entity instanceof SarkazSentinel && ((SarkazSentinel) entity).getActiveState();
    }
}
