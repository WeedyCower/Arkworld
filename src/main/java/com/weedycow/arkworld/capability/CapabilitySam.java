package com.weedycow.arkworld.capability;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.networlk.PacketSam;
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

public class CapabilitySam implements INBTSerializable<NBTTagCompound>
{
    private int sam;
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

    public int getSam()
    {
        return sam;
    }

    public void setSam(int sam)
    {
        this.sam = sam;
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

        nbt.setInteger("sam",sam);

        nbt.setInteger("sam_limit",limit);

        nbt.setInteger("sam_tick",tick);

        nbt.setInteger("sam_variable",variable);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("sam"))
            setSam(nbt.getInteger("sam"));

        if(nbt.hasKey("sam_limit"))
            setLimit(nbt.getInteger("sam_limit"));

        if(nbt.hasKey("sam_tick"))
            setTick(nbt.getInteger("sam_tick"));

        if(nbt.hasKey("sam_variable"))
            setVariable(nbt.getInteger("sam_variable"));
    }

    public static class Storage implements Capability.IStorage<CapabilitySam>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<CapabilitySam> capability, CapabilitySam instance, EnumFacing side)
        {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<CapabilitySam> capability, CapabilitySam instance, EnumFacing side, NBTBase nbt)
        {
            if(nbt instanceof NBTTagCompound)
                instance.deserializeNBT((NBTTagCompound) nbt);
        }
    }

    public static class Provide implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider
    {
        private final CapabilitySam instance;
        private final Capability<CapabilitySam> capability;

        public Provide()
        {
            instance = new CapabilitySam();
            capability = CapabilityRegistry.capSam;
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
        CapabilitySam sam;

        public Process(EntityPlayer player)
        {
            this.player = player;
            this.sam = player.getCapability(CapabilityRegistry.capSam, null);
            this.hasCapability = player.hasCapability(CapabilityRegistry.capSam, null);
        }

        public void addSam(int addSam)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                sam.setSam(Math.min(sam.getSam() + addSam, sam.getLimit()));
            }
        }

        public void reduceSam(int reduceSam)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                sam.setSam(Math.max(sam.getSam() - reduceSam, 0));
            }
        }

        public void setSam(int setSam)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                if(setSam>sam.getLimit())
                    sam.setSam(sam.getLimit());
                else sam.setSam(Math.max(setSam, 0));
            }
        }

        public void addLimit(int l)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                sam.setLimit(sam.getLimit() + l);
            }
        }

        public void reduceLimited(int l)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                sam.setLimit(sam.getLimit()-l);
            }
        }

        public void setLimited(int l)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                sam.setLimit(l);
            }
        }

        public void setVariety(int tick, int variable)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                sam.setTick(Math.max(tick,1));
                sam.setVariable(variable);
            }
        }

        public void changeVariety(int tick, int variable)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                sam.setTick(Math.max(sam.getTick()+tick,1));
                sam.setVariable(sam.getVariable()+variable);
            }
        }

        public int getTick()
        {
            return sam.getTick();
        }

        public int getVariable()
        {
            return sam.getVariable();
        }

        public int getSam()
        {
            return sam.getSam();
        }

        public int getLimit()
        {
            return sam.getLimit();
        }

        public void sendPacketToClient()
        {
            if (!player.world.isRemote&&hasCapability)
            {
                PacketSam message = new PacketSam();
                message.sam = this.sam.getSam();
                message.limit = this.sam.getLimit();
                message.tick = this.sam.getTick();
                message.variable = this.sam.getVariable();
                Arkworld.getNetwork().sendTo(message, (EntityPlayerMP) player);
            }
        }
    }
}
