package com.weedycow.arkworld.capability;

import com.weedycow.arkworld.registry.CapabilityRegistry;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityOperatorCard implements INBTSerializable<NBTTagCompound>
{
    String master;
    String uuid;
    int level;
    int exp;
    int moveMode;
    int actionMode;
    int trust;
    int skillLevel;
    int skillFirRank;
    int skillSecRank;
    int skillThiRank;
    int selectSkill;
    int elite;
    int potential;
    int times;
    int countdown;
    boolean clear;
    int[] ints;
    int existedTime;
    float mood;
    int trainTime;
    int x;
    int y;
    int z;
    int dia;

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        ints = new int[]{level, exp, moveMode, actionMode, trust, skillLevel, skillFirRank, skillSecRank, skillThiRank, selectSkill, elite, potential, times, countdown, existedTime, trainTime, x, y, z, dia};

        nbt.setIntArray("ints", ints);

        if(master!=null && !master.equals(""))
            nbt.setString("master",master);

        nbt.setBoolean("clear",clear);

        nbt.setFloat("mood",mood);

        if(uuid!=null && !uuid.equals(""))
            nbt.setString("uuid",uuid);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("master"))
            setMaster(nbt.getString("master"));

        if(nbt.hasKey("clear"))
            setClear(nbt.getBoolean("clear"));

        if(nbt.hasKey("mood"))
            setMood(nbt.getFloat("mood"));

        if(nbt.hasKey("uuid"))
            setUuid(nbt.getString("uuid"));

        if(nbt.hasKey("ints"))
        {
            setLevel(nbt.getIntArray("ints")[0]);

            setExp(nbt.getIntArray("ints")[1]);

            setMoveMode(nbt.getIntArray("ints")[2]);

            setActionMode(nbt.getIntArray("ints")[3]);

            setTrust(nbt.getIntArray("ints")[4]);

            setSkillLevel(nbt.getIntArray("ints")[5]);

            setSkillFirRank(nbt.getIntArray("ints")[6]);

            setSkillSecRank(nbt.getIntArray("ints")[7]);

            setSkillThiRank(nbt.getIntArray("ints")[8]);

            setSelectSkill(nbt.getIntArray("ints")[9]);

            setElite(nbt.getIntArray("ints")[10]);

            setPotential(nbt.getIntArray("ints")[11]);

            setTimes(nbt.getIntArray("ints")[12]);

            setCountdown(nbt.getIntArray("ints")[13]);

            setExistedTime(nbt.getIntArray("ints")[14]);

            setTrainTime(nbt.getIntArray("ints")[15]);

            if(nbt.getIntArray("ints").length>=17)
                setX(nbt.getIntArray("ints")[16]);

            if(nbt.getIntArray("ints").length>=18)
                setY(nbt.getIntArray("ints")[17]);

            if(nbt.getIntArray("ints").length>=19)
                setZ(nbt.getIntArray("ints")[18]);

            if(nbt.getIntArray("ints").length>=20)
                setDia(nbt.getIntArray("ints")[19]);
        }
    }

    public static class Storage implements Capability.IStorage<CapabilityOperatorCard>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<CapabilityOperatorCard> capability, CapabilityOperatorCard instance, EnumFacing side)
        {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<CapabilityOperatorCard> capability, CapabilityOperatorCard instance, EnumFacing side, NBTBase nbt)
        {
            if(nbt instanceof NBTTagCompound)
                instance.deserializeNBT((NBTTagCompound) nbt);
        }
    }

    public static class Provide implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider
    {
        private final CapabilityOperatorCard instance;
        private final Capability<CapabilityOperatorCard> capability;

        public Provide()
        {
            instance = new CapabilityOperatorCard();
            capability = CapabilityRegistry.capOperatorCard;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
        {
            return this.capability.equals(capability);
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
        {
            return this.capability.equals(capability) ? this.capability.cast(instance) : null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return instance.serializeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            instance.deserializeNBT(nbt);
        }
    }

    public int getZ()
    {
        return z;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getDia()
    {
        return dia;
    }

    public void setDia(int dia)
    {
        this.dia = dia;
    }

    public void setZ(int z)
    {
        this.z = z;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }

    public int getTrainTime()
    {
        return trainTime;
    }

    public void setTrainTime(int trainTime)
    {
        this.trainTime = trainTime;
    }

    public void setMood(float mood)
    {
        this.mood = mood;
    }

    public float getMood()
    {
        return mood;
    }

    public void setExistedTime(int existedTime)
    {
        this.existedTime = existedTime;
    }

    public int getExistedTime()
    {
        return existedTime;
    }

    public int getCountdown()
    {
        return countdown;
    }

    public void setCountdown(int countdown)
    {
        this.countdown = countdown;
    }

    public void setClear(boolean clear)
    {
        this.clear = clear;
    }

    public boolean isClear()
    {
        return clear;
    }

    public int getTimes()
    {
        return times;
    }

    public void setTimes(int times)
    {
        this.times = times;
    }

    public void setMaster(String master)
    {
        this.master = master;
    }

    public String getMaster()
    {
        return master;
    }

    public void setPotential(int potential)
    {
        this.potential = potential;
    }

    public int getPotential()
    {
        return potential;
    }

    public void setElite(int elite)
    {
        this.elite = elite;
    }

    public int getElite()
    {
        return elite;
    }

    public void setSelectSkill(int selectSkill)
    {
        this.selectSkill = selectSkill;
    }

    public int getSelectSkill()
    {
        return selectSkill;
    }

    public void setSkillThiRank(int skillThiRank)
    {
        this.skillThiRank = skillThiRank;
    }

    public int getSkillThiRank()
    {
        return skillThiRank;
    }

    public void setSkillSecRank(int skillSecRank)
    {
        this.skillSecRank = skillSecRank;
    }

    public int getSkillSecRank()
    {
        return skillSecRank;
    }

    public void setSkillFirRank(int skillFirRank)
    {
        this.skillFirRank = skillFirRank;
    }

    public int getSkillFirRank()
    {
        return skillFirRank;
    }

    public int getSkillLevel()
    {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel)
    {
        this.skillLevel = skillLevel;
    }

    public int getTrust()
    {
        return trust;
    }

    public void setTrust(int trust)
    {
        this.trust = trust;
    }

    public void setActionMode(int actionMode)
    {
        this.actionMode = actionMode;
    }

    public int getActionMode()
    {
        return actionMode;
    }

    public void setMoveMode(int moveMode)
    {
        this.moveMode = moveMode;
    }

    public int getMoveMode()
    {
        return moveMode;
    }

    public int getExp()
    {
        return exp;
    }

    public void setExp(int exp)
    {
        this.exp = exp;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }
}
