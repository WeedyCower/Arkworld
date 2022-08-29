package com.weedycow.arkworld.capability;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.networlk.PacketMana;
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

public class CapabilityMana implements INBTSerializable<NBTTagCompound>
{
    private int mana;
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

    public int getMana()
    {
        return mana;
    }

    public void setMana(int m)
    {
        this.mana = m;
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

        nbt.setInteger("mana",mana);

        nbt.setInteger("mana_limit",limit);

        nbt.setInteger("mana_tick",tick);

        nbt.setInteger("mana_variable",variable);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("mana"))
            setMana(nbt.getInteger("mana"));

        if(nbt.hasKey("mana_limit"))
            setLimit(nbt.getInteger("mana_limit"));

        if(nbt.hasKey("mana_tick"))
            setTick(nbt.getInteger("mana_tick"));

        if(nbt.hasKey("mana_variable"))
            setVariable(nbt.getInteger("mana_variable"));
    }

    public static class Storage implements Capability.IStorage<CapabilityMana>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<CapabilityMana> capability, CapabilityMana instance, EnumFacing side)
        {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<CapabilityMana> capability, CapabilityMana instance, EnumFacing side, NBTBase nbt)
        {
            if(nbt instanceof NBTTagCompound)
                instance.deserializeNBT((NBTTagCompound) nbt);
        }
    }

    public static class Provide implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider
    {
        private final CapabilityMana instance;
        private final Capability<CapabilityMana> capability;

        public Provide()
        {
            instance = new CapabilityMana();
            capability = CapabilityRegistry.capMana;
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
        CapabilityMana mana;

        public Process(EntityPlayer player)
        {
            this.player = player;
            this.mana = player.getCapability(CapabilityRegistry.capMana, null);
            this.hasCapability = player.hasCapability(CapabilityRegistry.capMana, null);
        }

        public int getMana()
        {
            return mana.getMana();
        }

        public void addMana(int m)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                mana.setMana(Math.min(mana.getMana() + m, mana.getLimit()));
            }
        }

        public void reduceMana(int m)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                mana.setMana(Math.max(mana.getMana() - m, 0));
            }
        }

        public void setMana(int m)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                if(m> mana.getLimit())
                    mana.setMana(mana.getLimit());
                else mana.setMana(Math.max(m, 0));
            }
        }

        public void addLimit(int l)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                mana.setLimit(mana.getLimit() + l);
            }
        }

        public void reduceLimit(int l)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                mana.setLimit(mana.getLimit()-l);
            }
        }

        public void setLimited(int l)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                mana.setLimit(l);
            }
        }

        public int getLimit()
        {
            return mana.getLimit();
        }

        public void setVariety(int tick, int variable)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                mana.setTick(Math.max(tick,1));
                mana.setVariable(variable);
            }
        }

        public void changeVariety(int tick, int variable)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                mana.setTick(Math.max(mana.getTick()+tick,1));
                mana.setVariable(mana.getVariable()+variable);
            }
        }

        public int getTick()
        {
            return mana.getTick();
        }

        public int getVariable()
        {
            return mana.getVariable();
        }

        public void sendPacketToClient()
        {
            if (!player.world.isRemote&&hasCapability)
            {
                PacketMana message = new PacketMana();
                message.mana = this.mana.getMana();
                message.limit = this.mana.getLimit();
                message.tick = this.mana.getTick();
                message.variable = this.mana.getVariable();
                Arkworld.getNetwork().sendTo(message, (EntityPlayerMP) player);
            }
        }
    }
}
