package com.weedycow.arkworld.entity.enemy;

import com.weedycow.weedylib.entity.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

public abstract class ArkBoss extends ArkMob
{
    protected BossInfo.Color color = BossInfo.Color.GREEN;
    protected BossInfoServer bossInfo;
    protected static final DataParameter<Integer> STAGE = EntityDataManager.createKey(ArkBoss.class, DataSerializers.VARINT);

    public ArkBoss(World worldIn)
    {
        super(worldIn);
        this.enablePersistence();
    }

    public ArkBoss(World worldIn, String id, String name, BossInfo.Color color, EnumTypes type, EnumCamps camp, EnumAttackMethod attackMethod, EnumDamageTypes damageTypes)
    {
        super(worldIn, id, name, type, camp, EnumStatus.LEADER, attackMethod, damageTypes);
        this.enablePersistence();
        this.color = color;
        this.bossInfo = new BossInfoServer(this.getDisplayName(), color, BossInfo.Overlay.PROGRESS);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Stage",getStage());
    }

    @Override
    @ParametersAreNonnullByDefault
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setStage(compound.getInteger("Stage"));
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(STAGE,0);
    }

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

    public void setStage(int stage){this.getDataManager().set(STAGE,stage);}

    public int getStage(){return this.getDataManager().get(STAGE);}

    @Override
    public boolean isNonBoss()
    {
        return false;
    }
}
