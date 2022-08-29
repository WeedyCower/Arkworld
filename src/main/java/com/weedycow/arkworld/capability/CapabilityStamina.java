package com.weedycow.arkworld.capability;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.networlk.PacketStamina;
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

public class CapabilityStamina implements INBTSerializable<NBTTagCompound>
{
    private int stamina;
    private int limit;
    private int tick;
    private int variable;

    public void setVariable(int variable)
    {
        this.variable = variable;
    }

    public int getVariable()
    {
        return variable;
    }

    public void setTick(int tick)
    {
        this.tick = tick;
    }

    public int getTick()
    {
        return tick;
    }

    public int getStamina()
    {
        return stamina;
    }

    public void setStamina(int m)
    {
        this.stamina = m;
    }

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setInteger("stamina",stamina);

        nbt.setInteger("stamina_limit",limit);

        nbt.setInteger("stamina_tick",tick);

        nbt.setInteger("stamina_variable",variable);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("stamina"))
            setStamina(nbt.getInteger("stamina"));

        if(nbt.hasKey("stamina_limit"))
            setLimit(nbt.getInteger("stamina_limit"));

        if(nbt.hasKey("stamina_tick"))
            setTick(nbt.getInteger("stamina_tick"));

        if(nbt.hasKey("stamina_variable"))
            setVariable(nbt.getInteger("stamina_variable"));
    }

    public static class Storage implements Capability.IStorage<CapabilityStamina>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<CapabilityStamina> capability, CapabilityStamina instance, EnumFacing side)
        {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<CapabilityStamina> capability, CapabilityStamina instance, EnumFacing side, NBTBase nbt)
        {
            if(nbt instanceof NBTTagCompound)
                instance.deserializeNBT((NBTTagCompound) nbt);
        }
    }

    public static class Provide implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider
    {
        private final CapabilityStamina instance;
        private final Capability<CapabilityStamina> capability;

        public Provide()
        {
            instance = new CapabilityStamina();
            capability = CapabilityRegistry.capStamina;
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
        CapabilityStamina stamina;

        public Process(EntityPlayer player)
        {
            this.player = player;
            this.stamina = player.getCapability(CapabilityRegistry.capStamina, null);
            this.hasCapability = player.hasCapability(CapabilityRegistry.capStamina, null);
        }

        public int getStamina()
        {
            return stamina.getStamina();
        }

        public void addStamina(int m)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                stamina.setStamina(Math.min(stamina.getStamina() + m, stamina.getLimit()));
            }
        }

        public void reduceStamina(int m)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                stamina.setStamina(Math.max(stamina.getStamina() - m, 0));
            }
        }

        public void setStamina(int m)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                if(m> stamina.getLimit())
                    stamina.setStamina(stamina.getLimit());
                else stamina.setStamina(Math.max(m, 0));
            }
        }

        public void addLimit(int l)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                stamina.setLimit(stamina.getLimit() + l);
            }
        }

        public void reduceLimited(int l)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                stamina.setLimit(stamina.getLimit()-l);
            }
        }

        public void setLimited(int l)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                stamina.setLimit(l);
            }
        }

        public int getLimit()
        {
            return stamina.getLimit();
        }

        public void setVariety(int tick, int variable)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                stamina.setTick(Math.max(tick,1));
                stamina.setVariable(variable);
            }
        }

        public void changeVariety(int tick, int variable)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                stamina.setTick(Math.max(stamina.getTick()+tick,1));
                stamina.setVariable(stamina.getVariable()+variable);
            }
        }

        public int getTick()
        {
            return stamina.getTick();
        }

        public int getVariable()
        {
            return stamina.getVariable();
        }

        public void sendPacketToClient()
        {
            if (!player.world.isRemote&&hasCapability)
            {
                PacketStamina message = new PacketStamina();
                message.stamina = this.stamina.getStamina();
                message.limit = this.stamina.getLimit();
                message.tick = this.stamina.getTick();
                message.variable = this.stamina.getVariable();
                Arkworld.getNetwork().sendTo(message, (EntityPlayerMP) player);
            }
        }
    }
}
