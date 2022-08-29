package com.weedycow.arkworld.capability;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.networlk.PacketSwing;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityPlayer implements INBTSerializable<NBTTagCompound>
{
    private boolean swing;
    private int times;
    private boolean attackable = true;
    private String name = "";

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setAttackable(boolean attackable)
    {
        this.attackable = attackable;
    }

    public boolean isAttackable()
    {
        return attackable;
    }

    public boolean isSwing()
    {
        return swing;
    }

    public void setTimes(int times)
    {
        this.times = times;
    }

    public int getTimes()
    {
        return times;
    }

    public void setSwing(boolean swing)
    {
        this.swing = swing;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setBoolean("swing",swing);

        nbt.setInteger("times",times);

        nbt.setBoolean("attackable",attackable);

        nbt.setString("name",name);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("swing"))
            setSwing(nbt.getBoolean("swing"));

        if(nbt.hasKey("times"))
            setTimes(nbt.getInteger("times"));

        if(nbt.hasKey("attackable"))
            setAttackable(nbt.getBoolean("attackable"));

        if(nbt.hasKey("name"))
            setName(nbt.getString("name"));
    }

    public static class Storage implements Capability.IStorage<CapabilityPlayer>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<CapabilityPlayer> capability, CapabilityPlayer instance, EnumFacing side)
        {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<CapabilityPlayer> capability, CapabilityPlayer instance, EnumFacing side, NBTBase nbt)
        {
            if(nbt instanceof NBTTagCompound)
                instance.deserializeNBT((NBTTagCompound) nbt);
        }
    }

    public static class Provide implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider
    {
        private final CapabilityPlayer instance;
        private final Capability<CapabilityPlayer> capability;

        public Provide()
        {
            instance = new CapabilityPlayer();
            capability = CapabilityRegistry.capPlayer;
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
        CapabilityPlayer p;

        public Process(EntityPlayer player)
        {
            this.player = player;
            this.p = player.getCapability(CapabilityRegistry.capPlayer, null);
            this.hasCapability = player.hasCapability(CapabilityRegistry.capPlayer, null);
        }

        public void setName(String name)
        {
            if(hasCapability)
                this.p.name = name;
        }

        public String getName()
        {
            if(hasCapability)
                return p.name;
            else
                return "";
        }

        public boolean isSwing()
        {
            if(hasCapability)
                return p.swing;
            else
                return false;
        }

        public void setTimes(int times)
        {
            if(hasCapability)
                this.p.times = times;
        }

        public int getTimes()
        {
            if(hasCapability)
                return p.times;
            else
                return 0;
        }

        public void setSwing(boolean swing)
        {
            if(hasCapability)
                this.p.swing = swing;
        }

        public void setAttackable(boolean attackable)
        {
            if(hasCapability)
                this.p.attackable = attackable;
        }

        public boolean isAttackable()
        {
            if(hasCapability)
                return p.attackable;
            else
                return false;
        }

        public void sendPacketToServer()
        {
            if (player.world.isRemote&&hasCapability)
            {
                PacketSwing message = new PacketSwing();
                message.attackable = this.isAttackable();
                message.name = this.getName();
                Arkworld.getNetwork().sendToServer(message);
            }
        }
    }
}
