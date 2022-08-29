package com.weedycow.arkworld.capability;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.networlk.PacketAttribute;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.weedylib.entity.WLM;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityAttribute implements INBTSerializable<NBTTagCompound>
{
    private float defencePower;
    private float spellResistance;

    public float getDefencePower()
    {
        return defencePower;
    }

    public void setDefencePower(float defencePower)
    {
        this.defencePower = defencePower;
    }

    public void setSpellResistance(float spellResistance)
    {
        this.spellResistance = spellResistance;
    }

    public float getSpellResistance()
    {
        return spellResistance;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setFloat("defence_power",defencePower);

        nbt.setFloat("spell_resistance",spellResistance);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("defence_power"))
            setDefencePower(nbt.getFloat("defence_power"));

        if(nbt.hasKey("spell_resistance"))
            setSpellResistance(nbt.getFloat("spell_resistance"));
    }

    public static class Storage implements Capability.IStorage<CapabilityAttribute>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<CapabilityAttribute> capability, CapabilityAttribute instance, EnumFacing side)
        {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<CapabilityAttribute> capability, CapabilityAttribute instance, EnumFacing side, NBTBase nbt)
        {
            if(nbt instanceof NBTTagCompound)
                instance.deserializeNBT((NBTTagCompound) nbt);
        }
    }

    public static class Provide implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider
    {
        private final CapabilityAttribute instance;
        private final Capability<CapabilityAttribute> capability;

        public Provide()
        {
            instance = new CapabilityAttribute();
            capability = CapabilityRegistry.capAttribute;
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
        EntityLivingBase entity;
        boolean hasCapability;
        CapabilityAttribute attribute;

        public Process(EntityLivingBase entity)
        {
            this.entity = entity;
            this.attribute = entity.getCapability(CapabilityRegistry.capAttribute, null);
            this.hasCapability = entity.hasCapability(CapabilityRegistry.capAttribute, null);
        }

        public float getDefencePower()
        {
            if(hasCapability)
                return attribute.getDefencePower();
            else 
                return 0;
        }

        public void setDefencePower(float defencePower)
        {
            if (!entity.world.isRemote&&hasCapability)
            {
                attribute.setDefencePower(Math.max(0,defencePower));
            }
        }

        public void addDefencePower(int defencePower)
        {
            if (!entity.world.isRemote&&hasCapability)
            {
                attribute.setDefencePower((Math.max(0,attribute.getDefencePower()+defencePower)));
            }
        }

        public void reduceDefencePower(int defencePower)
        {
            if (!entity.world.isRemote&&hasCapability)
            {
                attribute.setDefencePower((Math.max(0,attribute.getDefencePower()-defencePower)));
            }
        }

        public float getSpellResistance()
        {
            if(hasCapability)
                return attribute.getSpellResistance();
            else
                return 0;
        }

        public void setSpellResistance(float SpellResistance)
        {
            if (!entity.world.isRemote&&hasCapability&&SpellResistance>=0&&SpellResistance<=1)
            {
                attribute.setSpellResistance(SpellResistance);
            }
        }

        public void addSpellResistance(int SpellResistance)
        {
            if (!entity.world.isRemote&&hasCapability)
            {
                attribute.setSpellResistance((Math.max(0,attribute.getSpellResistance()+SpellResistance)));
            }
        }

        public void reduceSpellResistance(int SpellResistance)
        {
            if (!entity.world.isRemote&&hasCapability)
            {
                attribute.setSpellResistance((Math.max(0,attribute.getSpellResistance()-SpellResistance)));
            }
        }

        public void sendPacketToClient()
        {
            if (!entity.world.isRemote&&hasCapability)
            {
                PacketAttribute message = new PacketAttribute();
                message.id = entity.getEntityId();
                message.defencePower = this.attribute.getDefencePower();
                message.spellResistance = this.attribute.getSpellResistance();
                Arkworld.getNetwork().sendToDimension(message,entity.world.provider.getDimension());
            }
        }

        public static void onAttribute(LivingDamageEvent event)
        {
            EntityLivingBase entity = event.getEntityLiving();
            DamageSource source = event.getSource();

            if(entity.hasCapability(CapabilityRegistry.capAttribute,null))
            {
                CapabilityAttribute.Process attribute = new Process(entity);

                if(!source.isMagicDamage() && !source.isFireDamage() && !source.isExplosion() && !(entity instanceof WLM) && !source.isUnblockable())
                {
                    if(event.getAmount()-attribute.getDefencePower()>=0)
                        event.setAmount(event.getAmount()-attribute.getDefencePower());
                    else
                        event.setAmount(1);
                }

                if(source.isMagicDamage() && !(entity instanceof WLM))
                {
                    if(attribute.getSpellResistance()>=0 && attribute.getSpellResistance()<=0.95)
                        event.setAmount(event.getAmount()*(1-attribute.getSpellResistance()));
                    else
                        event.setAmount(event.getAmount()*0.05f);
                }

            }
        }
    }
}
