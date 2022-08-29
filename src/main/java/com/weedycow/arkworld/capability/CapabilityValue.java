package com.weedycow.arkworld.capability;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.networlk.PacketValue;
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

public class CapabilityValue implements INBTSerializable<NBTTagCompound>
{
    private int lmb;
    private int orundum;
    private int originium;
    private int signId;

    public int getLmb()
    {
        return lmb;
    }

    public void setLmb(int l)
    {
        this.lmb = l;
    }

    public int getOrundum()
    {
        return orundum;
    }

    public void setOrundum(int o)
    {
        this.orundum = o;
    }

    public int getOriginium()
    {
        return originium;
    }

    public void setOriginium(int o)
    {
        this.originium = o;
    }

    public void setSignId(int signId)
    {
        this.signId = signId;
    }

    public int getSignId()
    {
        return signId;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setInteger("lmb",lmb);

        nbt.setInteger("orundum",orundum);

        nbt.setInteger("originium",originium);

        nbt.setInteger("sign",signId);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("lmb"))
            setLmb(nbt.getInteger("lmb"));

        if(nbt.hasKey("orundum"))
            setOrundum(nbt.getInteger("orundum"));

        if(nbt.hasKey("originium"))
            setOriginium(nbt.getInteger("originium"));

        if(nbt.hasKey("sign"))
            setSignId(nbt.getInteger("sign"));
    }

    public static class Storage implements Capability.IStorage<CapabilityValue>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<CapabilityValue> capability, CapabilityValue instance, EnumFacing side)
        {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<CapabilityValue> capability, CapabilityValue instance, EnumFacing side, NBTBase nbt)
        {
            if(nbt instanceof NBTTagCompound)
                instance.deserializeNBT((NBTTagCompound) nbt);
        }
    }

    public static class Provide implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider
    {
        private final CapabilityValue instance;
        private final Capability<CapabilityValue> capability;

        public Provide()
        {
            instance = new CapabilityValue();
            capability = CapabilityRegistry.capValue;
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
        CapabilityValue value;

        public Process(EntityPlayer player)
        {
            this.player = player;
            this.value = player.getCapability(CapabilityRegistry.capValue, null);
            this.hasCapability = player.hasCapability(CapabilityRegistry.capValue, null);
        }

        public void addSign(int m)
        {
            if (!player.world.isRemote&&hasCapability&&m>0)
            {
                value.setLmb(value.getSignId()+m);
            }
        }

        public void reduceSign(int m)
        {
            if (!player.world.isRemote&&hasCapability&&m>0)
            {
                value.setLmb(Math.max(value.getSignId() - m, 0));
            }
        }

        public void setSign(int m)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                value.setSignId(Math.max(m, 0));
            }
        }

        public int getSign()
        {
            if (!player.world.isRemote&&hasCapability)
            {
                return value.getSignId();
            }
            return 0;
        }

        public void addLmb(int m)
        {
            if (!player.world.isRemote&&hasCapability&&m>0)
            {
                value.setLmb(value.getLmb()+m);
            }
        }

        public void reduceLmb(int m)
        {
            if (!player.world.isRemote&&hasCapability&&m>0)
            {
                value.setLmb(Math.max(value.getLmb() - m, 0));
            }
        }

        public void setLmb(int m)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                value.setLmb(Math.max(m, 0));
            }
        }

        public int getLmb()
        {
            if (!player.world.isRemote&&hasCapability)
            {
                return value.getLmb();
            }
            return 0;
        }

        public void addOrundum(int m)
        {
            if (!player.world.isRemote&&hasCapability&&m>0)
            {
                value.setOrundum(value.getOrundum()+m);
            }
        }

        public void reduceOrundum(int m)
        {
            if (!player.world.isRemote&&hasCapability&&m>0)
            {
                value.setOrundum(Math.max(value.getOrundum() - m, 0));
            }
        }

        public void setOrundum(int m)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                value.setOrundum(Math.max(m, 0));
            }
        }

        public int getOrundum()
        {
            if (!player.world.isRemote&&hasCapability)
            {
                return value.getOrundum();
            }
            return 0;
        }

        public void addOriginium(int m)
        {
            if (!player.world.isRemote&&hasCapability&&m>0)
            {
                value.setOriginium(value.getOriginium()+m);
            }
        }

        public void reduceOriginium(int m)
        {
            if (!player.world.isRemote&&hasCapability&&m>0)
            {
                value.setOriginium(Math.max(value.getOriginium() - m, 0));
            }
        }

        public void setOriginium(int m)
        {
            if (!player.world.isRemote&&hasCapability)
            {
                value.setOriginium(Math.max(m, 0));
            }
        }

        public int getOriginium()
        {
            if (!player.world.isRemote&&hasCapability)
            {
                return value.getOriginium();
            }
            return 0;
        }

        public void sendPacketToClient()
        {
            if (!player.world.isRemote&&hasCapability)
            {
                PacketValue message = new PacketValue();
                message.lmb = this.value.getLmb();
                message.orundum = this.value.getOrundum();
                message.originium = this.value.getOriginium();
                message.signId = this.value.getSignId();
                Arkworld.getNetwork().sendTo(message, (EntityPlayerMP) player);
            }
        }
    }
}
