package com.weedycow.arkworld.entity.enemy.hypergryph;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.split.normal.OriginiutantTumor;
import com.weedycow.weedylib.util.EntityUtil;
import com.weedycow.weedylib.util.MathUtil;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

public class BossRuaCow extends EntityMob
{
	public static final String ID = "boss_rua_cow";
	public static final String NAME = Arkworld.MODID + ".bossRuaCow";
	
	public BossRuaCow(World worldIn)
	{
		super (worldIn);
		this.setSize(2f, 2f);
		this.enablePersistence();
	}
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100000);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1000);
	}
	
	protected void initEntityAI()
    {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackMelee(this,0.5,false));
		this.tasks.addTask(2, new EntityAIWanderAvoidWater(this,0.5));
		this.tasks.addTask(3, new EntityAIWatchClosest(this,EntityPlayer.class,32.0f));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.targetTasks.addTask(0, new EntityAIHurtByTarget(this,false));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this,EntityPlayer.class,false));
    }

    @Override
	public boolean isNonBoss() {return false;}
	private final BossInfoServer bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS);

	@Override
	@ParametersAreNonnullByDefault
	public void addTrackingPlayer(EntityPlayerMP player)
	{
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}
	@Override
	@ParametersAreNonnullByDefault
	public void removeTrackingPlayer(EntityPlayerMP player)
	{
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());

		if(EntityUtil.atSetIntervals(this,20,0,0) && getAttackTarget() != null)
		{
			OriginiutantTumor mutatedTumor = new OriginiutantTumor(world);
			mutatedTumor.setPosition(posX + MathUtil.getRandomDouble(), posY, posZ + MathUtil.getRandomDouble());
			world.spawnEntity(mutatedTumor);
		}
	}
}