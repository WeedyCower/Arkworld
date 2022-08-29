package com.weedycow.arkworld.entity.enemy.split.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.manager.AnimationData;

public class OriginiutantExcrescence extends ArkMob
{
    public static final String ID = "originiutant_excrescence";
    public static final String NAME = Arkworld.MODID + ".originiutantExcrescence";

    public OriginiutantExcrescence(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.INFECTED_ORGANISMS, EnumCamps.SPLIT, EnumStatus.NORMAL, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(0.5f, 0.3f);
        experienceValue = split.mutantRockSpider.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(split.originiutantExcrescence.maxHealth);
        getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(split.originiutantExcrescence.movementSpeed);
        getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(split.originiutantExcrescence.knockbackResistance);
        getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(split.originiutantExcrescence.defensivePower);
        getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(split.originiutantExcrescence.spellResistance);
        getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(split.originiutantExcrescence.attackRange);
        getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(split.originiutantExcrescence.attackInterval);
        getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(split.originiutantExcrescence.attackDamage);
    }

    @Override
    public void registerControllers(AnimationData animationData) {}

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
    }

    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureEntities(id);
    }
}
