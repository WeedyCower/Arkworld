package com.weedycow.arkworld.ai;

import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.entity.enemy.split.boss.Evolution;
import com.weedycow.arkworld.util.ArkEntityUtil;
import com.weedycow.weedylib.entity.EnumCamps;
import com.weedycow.weedylib.entity.WLM;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.DamageSource;

public class EntityAIEvolution extends EntityAIBase
{
    EntityLiving entity;
    DataParameter<Integer> TICK;

    public EntityAIEvolution(WLM entity, DataParameter<Integer> TICK)
    {
        this.entity = entity;
        this.TICK = TICK;
    }

    @Override
    public void updateTask()
    {
        if (entity.getHealth() / entity.getMaxHealth() <= 1 && entity.getHealth() / entity.getMaxHealth() > 0.6)
        {
            if (EntityUtil.atSetIntervals(TICK,entity, ArkMob.split.evolution.firstStageChargedStrikeInterval+ArkMob.split.evolution.alarmTime, 0, 0))
                EntityUtil.attackAOE(entity, EnumCamps.SPLIT, DamageSource.GENERIC, ((Evolution) entity).getAttackRange(), ArkMob.split.evolution.firstStageChargedStrikeDamage);
            if (EntityUtil.atSetIntervals(TICK,entity, ArkMob.split.evolution.firstStageSplitOriginiutantExcrescenceInterval, 0, 0))
                ArkEntityUtil.splitOriginiutantExcrescence(entity, ArkMob.split.evolution.firstStageSplitOriginiutantExcrescenceNumber);
            if (EntityUtil.atSetIntervals(TICK,entity, ArkMob.split.evolution.firstStageChargedStrikeInterval, 0, 0))
                ((Evolution) entity).setCountdown(ArkMob.split.evolution.alarmTime);
        }

        if (entity.getHealth() / entity.getMaxHealth() <= 0.6 && entity.getHealth() / entity.getMaxHealth() > 0.2)
        {
            if (EntityUtil.atSetIntervals(TICK,entity, ArkMob.split.evolution.secondStageChargedStrikeInterval+ArkMob.split.evolution.alarmTime, 0, 0))
                EntityUtil.attackAOE(entity, EnumCamps.SPLIT, DamageSource.GENERIC, ((Evolution) entity).getAttackRange(), ArkMob.split.evolution.secondStageChargedStrikeDamage);
            if (EntityUtil.atSetIntervals(TICK,entity, ArkMob.split.evolution.secondStageSplitOriginiutantTumorsInterval, 0, 0))
                ArkEntityUtil.splitOriginiutantTumor(entity, ArkMob.split.evolution.secondStageSplitOriginiutantTumorsNumber);
            if (EntityUtil.atSetIntervals(TICK,entity, ArkMob.split.evolution.secondStageChargedStrikeInterval, 0, 0))
                ((Evolution) entity).setCountdown(ArkMob.split.evolution.alarmTime);
        }

        if (entity.getHealth() / entity.getMaxHealth() <= 0.2 && entity.getHealth() / entity.getMaxHealth() > 0)
        {
            if (EntityUtil.atSetIntervals(TICK,entity, ArkMob.split.evolution.thirdStageChargedStrikeInterval+ArkMob.split.evolution.alarmTime, 0, 0))
                EntityUtil.attackAOE(entity, EnumCamps.SPLIT, DamageSource.GENERIC, ((Evolution) entity).getAttackRange(), ArkMob.split.evolution.thirdStageChargedStrikeDamage);
            if (EntityUtil.atSetIntervals(TICK,entity, ArkMob.split.evolution.thirdStageSplitOriginiutantTumorsInterval, 0, 0))
                ArkEntityUtil.splitOriginiutantTumor(entity, ArkMob.split.evolution.thirdStageSplitOriginiutantTumorsNumber);
            if (EntityUtil.atSetIntervals(TICK,entity, ArkMob.split.evolution.thirdStageChargedStrikeInterval, 0, 0))
                ((Evolution) entity).setCountdown(ArkMob.split.evolution.alarmTime);
        }
    }

    @Override
    public boolean shouldExecute()
    {
        return entity instanceof Evolution && ((Evolution)entity).getAttackState();
    }
}
