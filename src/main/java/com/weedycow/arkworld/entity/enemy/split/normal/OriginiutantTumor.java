package com.weedycow.arkworld.entity.enemy.split.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class OriginiutantTumor extends ArkMob
{
	public static final String ID = "originiutant_tumor";
	public static final String NAME = Arkworld.MODID + ".originiutantTumor";

	public OriginiutantTumor(World worldIn)
	{
		super(worldIn, ID, NAME, EnumTypes.INFECTED_ORGANISMS, EnumCamps.SPLIT, EnumStatus.NORMAL, EnumAttackMethod.MELEE, EnumDamageTypes.PHYSICAL);
		setSize(0.5f, 0.3f);
		experienceValue = split.mutantRockSpider.experienceValue;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(split.originiutantTumor.maxHealth);
		getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(split.originiutantTumor.movementSpeed);
		getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(split.originiutantTumor.knockbackResistance);
		getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(split.originiutantTumor.defensivePower);
		getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(split.originiutantTumor.spellResistance);
		getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(split.originiutantTumor.attackRange);
		getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(split.originiutantTumor.attackInterval);
		getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(split.originiutantTumor.attackDamage);
	}

	@Override
	public void registerControllers(AnimationData animationData) {}

	@Override
	public void commonExecute(WLM wlm, EntityLivingBase target)
	{
		if(EntityUtil.atSetIntervals(TICK,wlm, getAttackInterval(), 0, 0))
			EntityUtil.attackMelee(wlm,getAttackRange(),getAttackDamage(),DamageSource.causeMobDamage(this));
	}

	public ResourceLocation getTextureLocation()
	{
		return ArkResUtil.textureEntities(id);
	}

	public ResourceLocation getModelLocation()
	{
		return ArkResUtil.geo(OriginiutantExcrescence.ID);
	}

	public static void onMutatedTumorAttack(LivingAttackEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		Entity source = event.getSource().getTrueSource();
		if (source instanceof OriginiutantTumor)
		{
			entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE,20,5));
		}
	}
}

