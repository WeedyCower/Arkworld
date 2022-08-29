package com.weedycow.arkworld.entity.enemy.hypergryph;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.weapon.EntityOrundumTh;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.weedylib.util.EntityUtil;
import com.weedycow.weedylib.util.MathUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

public class BossOceanCat extends EntityMob
{
    public static final String ID = "boss_ocean_cat";
    public static final String NAME = Arkworld.MODID + ".bossOceanCat";

    public BossOceanCat(World worldIn)
    {
        super (worldIn);
        this.setSize(0.8f, 2f);
        this.enablePersistence();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(36888);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(368);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(368);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this,0.6,false));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this,0.6));
        this.tasks.addTask(3, new EntityAIWatchClosest(this,EntityPlayer.class,32.0f));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this,false));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this,EntityPlayer.class,false));
    }

    @Override
    public boolean isNonBoss()
    {
        return false;
    }
    private final BossInfoServer bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.PROGRESS);

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

        if(EntityUtil.atSetIntervals(this,600,0,0) && getAttackTarget() != null)
        {
            BlockPos blockPos = new BlockPos(this.posX, this.posY , this.posZ);
            AxisAlignedBB AABB = new AxisAlignedBB(blockPos).grow(16);
            for (EntityLivingBase entityLivingBase : world.getEntitiesWithinAABB(EntityLivingBase.class, AABB))
            {
                if(entityLivingBase == getAttackTarget())
                {
                    BlockPos blockPos1 = new BlockPos(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ);
                    BlockPos blockPos2 = new BlockPos(entityLivingBase.posX, entityLivingBase.posY + 1, entityLivingBase.posZ);
                    entityLivingBase.world.setBlockState(blockPos1, Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos1.north(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos1.north().east(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos1.north().west(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos1.north().north(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos1.south(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos1.south().south(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos1.south().east(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos1.south().west(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos1.west(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos1.west().west(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos1.east(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos1.east().east(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2, Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.up(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.up().up(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.north(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.north().up(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.north().up().west(), Blocks.LAVA.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.north().up().east(), Blocks.LAVA.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.north().north(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.north().east(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.north().west(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.south(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.south().up(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.south().south(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.south().east(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.south().west(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.south().up().west(), Blocks.LAVA.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.south().up().east(), Blocks.LAVA.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.west(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.west().up(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.west().west(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.east(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.east().up(), Blocks.SAND.getDefaultState());
                    entityLivingBase.world.setBlockState(blockPos2.east().east(), Blocks.SAND.getDefaultState());
                }
            }

            for(int i=1; i<=3; i++)
            {
                EntityGhast husk1 = new EntityGhast(world);
                husk1.setPosition(this.posX + MathUtil.getRandomDouble() * 6, this.posY + 6, this.posZ + MathUtil.getRandomDouble() * 6);
                world.spawnEntity(husk1);
            }

            this.playSound(SoundRegistry.DAMEDANE,6,1);

//            ArkParticleUtil.evolution(this);
        }
        else if(EntityUtil.atSetIntervals(this,100,0,0) && getAttackTarget() != null)
        {
            EntityUtil.attackRange(this,new EntityOrundumTh(world,this,38),null,1,1);
        }
    }
}
