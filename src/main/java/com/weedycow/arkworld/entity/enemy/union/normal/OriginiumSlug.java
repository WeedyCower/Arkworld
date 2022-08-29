package com.weedycow.arkworld.entity.enemy.union.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class OriginiumSlug extends ArkMob
{
    public static final String ID = "originium_slug";
    public static final String NAME = Arkworld.MODID + ".originiumSlug";

    public OriginiumSlug(World worldIn)
    {
        super(worldIn, ID, NAME, EnumTypes.INFECTED_ORGANISMS, EnumCamps.UNION, EnumStatus.NORMAL, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
        setSize(0.4f, 0.3f);
        experienceValue = union.originiumSlug.experienceValue;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.originiumSlug.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.originiumSlug.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.originiumSlug.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.originiumSlug.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.originiumSlug.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.originiumSlug.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.originiumSlug.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.originiumSlug.attackDamage);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty,IEntityLivingData data)
    {
        setType(rand.nextInt(3));

        if(getType()==1)
        {
            addHP(union.originiumSlug.alphaMaxHealth);
            addAD(union.originiumSlug.alphaAttackDamage);
        }
        if(getType()==2)
        {
            addHP(union.originiumSlug.betaMaxHealth);
            addAD(union.originiumSlug.betaAttackDamage);
        }
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    public void registerControllers(AnimationData animationData) {}

    @Override
    public AnimationFactory getFactory()
    {
        return null;
    }

    @Override
    public void closeExecute(WLM wlm, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
            EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
    }

    public ResourceLocation getTextureLocation()
    {
        if(getType()==1)
            return ArkResUtil.textureEntities(id+"_b");
        if(getType()==2)
            return ArkResUtil.textureEntities(id+"_c");
        else
            return ArkResUtil.textureEntities(id+"_a");
    }
}