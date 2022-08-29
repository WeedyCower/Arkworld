package com.weedycow.arkworld.capability;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.networlk.PacketLevel;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityLevel implements INBTSerializable<NBTTagCompound>
{
    private int level;
    private int experience;
    private int need;
    private int time;

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }
    
    public int getExperience()
    {
        return experience;
    }
    
    public void setExperience(int experience)
    {
        this.experience = experience;
    }

    public int getNeed()
    {
        return need;
    }

    public void setNeed(int need)
    {
        this.need = need;
    }

    public int getTime()
    {
        return time;
    }

    public void setTime(int t)
    {
        this.time = t;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setInteger("level",level);
        
        nbt.setInteger("experience",experience);

        nbt.setInteger("need",need);

        nbt.setInteger("time",time);
        
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("level"))
            setLevel(nbt.getInteger("level"));

        if(nbt.hasKey("experience"))
            setExperience(nbt.getInteger("experience"));

        if(nbt.hasKey("need"))
            setNeed(nbt.getInteger("need"));

        if(nbt.hasKey("time"))
            setTime(nbt.getInteger("time"));
    }

    public static class Storage implements Capability.IStorage<CapabilityLevel>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<CapabilityLevel> capability, CapabilityLevel instance, EnumFacing side)
        {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<CapabilityLevel> capability, CapabilityLevel instance, EnumFacing side, NBTBase nbt)
        {
            if(nbt instanceof NBTTagCompound)
                instance.deserializeNBT((NBTTagCompound) nbt);
        }
    }

    public static class Provide implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider
    {
        private final CapabilityLevel instance;
        private final Capability<CapabilityLevel> capability;

        public Provide()
        {
            instance = new CapabilityLevel();
            capability = CapabilityRegistry.capLevel;
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

    public static class Process
    {
        EntityPlayer player;
        boolean hasCapability;
        CapabilityLevel level;

        public Process(EntityPlayer player)
        {
            this.player = player;
            this.level = player.getCapability(CapabilityRegistry.capLevel, null);
            this.hasCapability = player.hasCapability(CapabilityRegistry.capLevel, null);
        }

        public void addLevel(int l)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                level.setLevel(level.getLevel()+l);
            }
        }

        public void reduceLevel(int l)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                level.setLevel(Math.max(level.getLevel()-l,0));
            }
        }

        public void setLevel(int l)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                level.setLevel(l);
            }
        }

        public void addExperience(int e)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                level.setExperience(level.getExperience()+e);
            }
        }

        public void reduceExperience(int e)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                level.setExperience(Math.max(level.getExperience()-e,0));
            }
        }

        public void setExperience(int e)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                level.setExperience(e);
            }
        }

        public int getLevel()
        {
            return level.getLevel();
        }

        public int getExperience()
        {
            return level.getExperience();
        }

        public int getNeed()
        {
            return level.getNeed();
        }

        public void setNeed(int i)
        {
            level.setNeed(Math.max(i,0));
        }

        public void setTime(int t)
        {
            level.setTime(t);
        }

        public int getTime()
        {
            return level.getTime();
        }

        public void sendPacketToClient()
        {
            if (!player.world.isRemote&&hasCapability)
            {
                PacketLevel message = new PacketLevel();
                message.level = this.level.getLevel();
                message.need = this.level.getNeed();
                message.time = this.level.getTime();
                message.experience = this.level.getExperience();
                Arkworld.getNetwork().sendTo(message, (EntityPlayerMP) player);
            }
        }
    }
}
