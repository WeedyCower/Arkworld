package com.weedycow.arkworld.capability;

import com.weedycow.arkworld.gui.MyInfoContainer;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CapabilityItem implements INBTSerializable<NBTTagCompound>
{
    private List<EnumEntry> entries = new ArrayList<>();

    public static EnumEntry[] arms = {EnumEntry.DAMAGE,EnumEntry.SHOOT,EnumEntry.DURABLE,EnumEntry.MELEE,EnumEntry.RANGE,EnumEntry.SHIELD,
            EnumEntry.CRITICAL,EnumEntry.LESS_STAMINA,EnumEntry.LESS_MANA,EnumEntry.LESS_SAM,EnumEntry.KNOCKBACK,EnumEntry.VAMPIRE,
            EnumEntry.COLD,EnumEntry.REDUCE_DEFENCE,EnumEntry.SILENCE,EnumEntry.VERTIGO,EnumEntry.BURNING_BREATH, EnumEntry.FRAGILE,
            EnumEntry.PAUSE,EnumEntry.SHACKLES,EnumEntry.BANEAL,EnumEntry.D12,EnumEntry.ATTACK_SPEED};
    public static EnumEntry[] defence = {EnumEntry.ANTI_PHYSICAL,EnumEntry.ANTI_SPELL,EnumEntry.ANTI_DAMAGE,EnumEntry.ANTI_EXPLOSION,
            EnumEntry.ANTI_FIRE,EnumEntry.ANTI_FALL,EnumEntry.ANTI_WITHER,EnumEntry.ANTI_DROWN,EnumEntry.REFLEX};
    public static EnumEntry[] player = {EnumEntry.RESURRECTION,EnumEntry.MAX_HEALTH,EnumEntry.HEALING_STRENGTH,EnumEntry.HEALTH_RECOVER,
            EnumEntry.MOVE_SPEED,EnumEntry.LUCK,EnumEntry.ADD_STAMINA,EnumEntry.ADD_MAMA,EnumEntry.ADD_SAM};
    private int experience;
    private int level;
    private int type;//0-arms;1-defense;2-player;

    public List<String> getIds()
    {
        return getEntries().stream().map(EnumEntry::getEntryId).collect(Collectors.toList());
    }

    public void addEntry(EnumEntry entry)
    {
        this.entries.add(entry);
    }

    public void setEntries(List<EnumEntry> entries)
    {
        this.entries = entries;
    }

    public List<EnumEntry> getEntries()
    {
        return entries;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getLevel()
    {
        return level;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getType()
    {
        return type;
    }

    public int getExperience()
    {
        return experience;
    }

    public void setExperience(int experience)
    {
        this.experience = experience;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setInteger("experience",experience);

        nbt.setInteger("type",getType());

        nbt.setInteger("level",getLevel());

        nbt.setIntArray("entry",entries.stream().map(EnumEntry::getEntryId).collect(Collectors.toList()).stream().map(Integer::valueOf)
                .collect(Collectors.toList()).stream().mapToInt(Integer::intValue).toArray());

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("experience"))
            setExperience(nbt.getInteger("experience"));

        if(nbt.hasKey("level"))
            setLevel(nbt.getInteger("level"));

        if(nbt.hasKey("type"))
            setType(nbt.getInteger("type"));

        if(nbt.hasKey("entry"))
        {
            setEntries(Arrays.stream(nbt.getIntArray("entry")).boxed().collect(Collectors.toList()).stream().map(String::valueOf)
                    .collect(Collectors.toList()).stream().map(EnumEntry::getEntryFormId).collect(Collectors.toList()));
        }

    }

    public static class Storage implements Capability.IStorage<CapabilityItem>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<CapabilityItem> capability, CapabilityItem instance, EnumFacing side)
        {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<CapabilityItem> capability, CapabilityItem instance, EnumFacing side, NBTBase nbt)
        {
            if(nbt instanceof NBTTagCompound)
                instance.deserializeNBT((NBTTagCompound) nbt);
        }
    }

    public static class Provide implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider
    {
        private final CapabilityItem instance;
        private final Capability<CapabilityItem> capability;

        public Provide()
        {
            instance = new CapabilityItem();
            capability = CapabilityRegistry.capItem;
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
        ItemStack stack;
        boolean hasCapability;
        CapabilityItem item;

        public Process(ItemStack stack)
        {
            this.stack = stack;
            this.item = stack.getCapability(CapabilityRegistry.capItem, null);
            this.hasCapability = stack.hasCapability(CapabilityRegistry.capItem, null);
        }

        public String getEntriesString()
        {
            String s = getEntries().toString();

            return s
                    .replace("anti_physical",I18n.format("item.arkworld.info.antiPhysical")+"+"+getRank(EnumEntry.ANTI_PHYSICAL)*10+"%")
                    .replace("anti_spell",I18n.format("item.arkworld.info.antiSpell")+"+"+getRank(EnumEntry.ANTI_SPELL)*10+"%")
                    .replace("anti_damage",I18n.format("item.arkworld.info.antiDamage")+"+"+getRank(EnumEntry.ANTI_DAMAGE)*5+"%")
                    .replace("anti_explosion",I18n.format("item.arkworld.info.antiExplosion")+"+"+getRank(EnumEntry.ANTI_EXPLOSION)*10+"%")
                    .replace("anti_fire",I18n.format("item.arkworld.info.antiFire")+"+"+getRank(EnumEntry.ANTI_FIRE)*10+"%")
                    .replace("anti_fall",I18n.format("item.arkworld.info.antiFall")+"+"+getRank(EnumEntry.ANTI_FALL)*10+"%")
                    .replace("anti_wither",I18n.format("item.arkworld.info.antiWither")+"+"+getRank(EnumEntry.ANTI_WITHER)*10+"%")
                    .replace("anti_drown",I18n.format("item.arkworld.info.antiDrown")+"+"+getRank(EnumEntry.ANTI_DROWN)*10+"%")
                    .replace("less_stamina",I18n.format("item.arkworld.info.stamina_reduce")+"-"+getRank(EnumEntry.LESS_STAMINA)*10+"%")
                    .replace("less_mana",I18n.format("item.arkworld.info.mana_reduce")+"-"+getRank(EnumEntry.LESS_MANA)*10+"%")
                    .replace("less_sam",I18n.format("item.arkworld.info.sam_reduce")+"-"+getRank(EnumEntry.LESS_SAM)*10+"%")
                    .replace("reduce_defence",I18n.format("item.arkworld.info.defence_reduce")+"+"+getRank(EnumEntry.REDUCE_DEFENCE)+"s")
                    .replace("add_stamina",I18n.format("item.arkworld.info.add_stamina")+"+"+getRank(EnumEntry.ADD_STAMINA)*10+"%")
                    .replace("add_mama",I18n.format("item.arkworld.info.add_mama")+"+"+getRank(EnumEntry.ADD_MAMA)*10+"%")
                    .replace("add_sam",I18n.format("item.arkworld.info.add_sam")+"+"+getRank(EnumEntry.ADD_SAM)*10+"%")
                    .replace("damage", I18n.format("item.arkworld.info.damage")+"+"+getRank(EnumEntry.DAMAGE)*10+"%")
                    .replace("shoot",I18n.format("item.arkworld.info.shootVelocity")+"+"+getRank(EnumEntry.SHOOT)*20+"%")
                    .replace("durable",I18n.format("item.arkworld.info.durability")+"+"+getRank(EnumEntry.DURABLE)*100+"%")
                    .replace("melee",I18n.format("config.arkworld.melee")+"+"+getRank(EnumEntry.MELEE)*10+"%")
                    .replace("range",I18n.format("config.arkworld.range")+"+"+getRank(EnumEntry.RANGE)*10+"%")
                    .replace("shield",I18n.format("config.arkworld.shield")+"+"+getRank(EnumEntry.SHIELD)*10+"%")
                    .replace("critical",I18n.format("item.arkworld.info.critical")+"+"+getRank(EnumEntry.CRITICAL)*10+"%")
                    .replace("knockback",I18n.format("item.arkworld.info.knockback_level")+"+"+getRank(EnumEntry.KNOCKBACK))
                    .replace("vampire",I18n.format("item.arkworld.info.vampire")+"+"+getRank(EnumEntry.LESS_SAM)*10+"%")
                    .replace("cold",I18n.format("item.arkworld.info.cold")+"+"+getRank(EnumEntry.COLD)+"s")
                    .replace("silence",I18n.format("item.arkworld.info.silence")+"+"+getRank(EnumEntry.SILENCE)+"s")
                    .replace("vertigo",I18n.format("item.arkworld.info.vertigo")+"+"+getRank(EnumEntry.VERTIGO)+"s")
                    .replace("burning_breath",I18n.format("item.arkworld.info.burning_breath")+"+"+getRank(EnumEntry.BURNING_BREATH)+"s")
                    .replace("fragile",I18n.format("item.arkworld.info.fragile")+"+"+getRank(EnumEntry.FRAGILE)+"s")
                    .replace("pause",I18n.format("item.arkworld.info.pause")+"+"+getRank(EnumEntry.PAUSE)+"s")
                    .replace("shackles",I18n.format("item.arkworld.info.shackles")+"+"+getRank(EnumEntry.SHACKLES)+"s")
                    .replace("baneal",I18n.format("item.arkworld.info.baneal")+"+"+getRank(EnumEntry.BANEAL)+"s")
                    .replace("d12",I18n.format("item.arkworld.d12.name")+"+"+getRank(EnumEntry.D12)*10+"%")
                    .replace("reflex",I18n.format("item.arkworld.info.reflex")+"+"+getRank(EnumEntry.REFLEX)*10+"%")
                    .replace("resurrection",I18n.format("item.arkworld.info.resurrection")+"+"+getRank(EnumEntry.RESURRECTION)*5+"%")
                    .replace("max_health",I18n.format("config.arkworld.maxHealth")+"+"+getRank(EnumEntry.MAX_HEALTH)*20+"%")
                    .replace("healing_strength",I18n.format("item.arkworld.info.healing_strength")+"+"+getRank(EnumEntry.HEALING_STRENGTH)*20+"%")
                    .replace("health_recover",I18n.format("item.arkworld.info.health_recover")+"+"+getRank(EnumEntry.HEALTH_RECOVER))
                    .replace("move_speed",I18n.format("config.arkworld.movementSpeed")+"+"+getRank(EnumEntry.MOVE_SPEED)*20+"%")
                    .replace("attack_speed",I18n.format("item.arkworld.info.attack_speed")+"+"+getRank(EnumEntry.ATTACK_SPEED)*10+"%")
                    .replace("luck",I18n.format("item.arkworld.info.luck")+"+"+getRank(EnumEntry.LUCK))
                    .replace("[","").replace("]","");
        }

        public static void onHurt(LivingHurtEvent event)
        {
            EntityLivingBase entity = event.getEntityLiving();
            if(entity instanceof EntityPlayer)
            {
                List<ItemStack> equipmentList = new ArrayList<>();
                entity.getEquipmentAndArmor().forEach(equipmentList::add);
                List<ItemStack> armorList = equipmentList.subList(2, 6);
                ItemStack mod = new MyInfoContainer(((EntityPlayer) entity).inventory, (EntityPlayer) entity).getSlot(42).getStack();
                CapabilityItem.Process item = new CapabilityItem.Process(mod);
                DamageSource source =  event.getSource();
                if(!armorList.contains(ItemStack.EMPTY))
                {
                    event.setAmount(event.getAmount()*(1-item.getRank(EnumEntry.ANTI_DAMAGE)/20f));

                    if(!source.isMagicDamage() && !source.isFireDamage() && !source.isExplosion() && !source.isUnblockable())
                        event.setAmount(event.getAmount()*(1-item.getRank(EnumEntry.ANTI_PHYSICAL)/10f));

                    if(source.isMagicDamage())
                        event.setAmount(event.getAmount()*(1-item.getRank(EnumEntry.ANTI_SPELL)/10f));

                    if(source.isExplosion())
                        event.setAmount(event.getAmount()*(1-item.getRank(EnumEntry.ANTI_EXPLOSION)/10f));

                    if(source.isFireDamage())
                        event.setAmount(event.getAmount()*(1-item.getRank(EnumEntry.ANTI_FIRE)/10f));

                    if(source == DamageSource.FALL)
                        event.setAmount(event.getAmount()*(1-item.getRank(EnumEntry.ANTI_FALL)/10f));

                    if(source == DamageSource.WITHER)
                        event.setAmount(event.getAmount()*(1-item.getRank(EnumEntry.ANTI_WITHER)/10f));

                    if(source == DamageSource.DROWN)
                        event.setAmount(event.getAmount()*(1-item.getRank(EnumEntry.ANTI_DROWN)/10f));

                    if(item.getRank(EnumEntry.REFLEX)>0 && event.getSource().getTrueSource() instanceof EntityLivingBase)
                        event.getSource().getTrueSource().attackEntityFrom(DamageSource.causeThornsDamage(entity),event.getAmount()*item.getRank(EnumEntry.REFLEX)/10);
                }
            }

        }

        public static void onUpdate(TickEvent.PlayerTickEvent event)
        {
            EntityPlayer entity = event.player;

            if (entity != null && entity.ticksExisted % 20 == 0)
            {
                ItemStack mod = new MyInfoContainer(entity.inventory, entity).getSlot(43).getStack();
                Process item = new Process(mod);

                AttributeModifier max_health = new AttributeModifier(UUID.fromString("6bf523b8-06b3-48f4-a1b6-104468277f1b"),"mod_max_health",entity.getEntityAttribute(WLMAttributes.MAX_HEALTH).getAttributeValue()*(1f+item.getRank(EnumEntry.MAX_HEALTH)/5f),0);
                if(item.getRank(EnumEntry.MAX_HEALTH)>0)
                {
                    if(!entity.getEntityAttribute(WLMAttributes.MAX_HEALTH).hasModifier(max_health))
                        entity.getEntityAttribute(WLMAttributes.MAX_HEALTH).applyModifier(max_health);
                }
                else
                {
                    if(entity.getEntityAttribute(WLMAttributes.MAX_HEALTH).hasModifier(max_health))
                        entity.getEntityAttribute(WLMAttributes.MAX_HEALTH).removeModifier(max_health);
                }

                if(EntityUtil.atSetIntervals(entity,20,0,0) && item.getRank(EnumEntry.HEALTH_RECOVER)>0)
                    entity.heal(item.getRank(EnumEntry.HEALTH_RECOVER));

                AttributeModifier move_speed = new AttributeModifier(UUID.fromString("3dbf91ad-2eec-4bae-aac8-0e14c4fb1b4d"),"mod_move_speed",entity.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).getAttributeValue()*(1f+item.getRank(EnumEntry.MOVE_SPEED )/5f),0);
                if(item.getRank(EnumEntry.MOVE_SPEED)>0)
                {
                    if(!entity.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).hasModifier(move_speed))
                        entity.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).applyModifier(move_speed);
                }
                else
                {
                    if(entity.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).hasModifier(move_speed))
                        entity.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).removeModifier(move_speed);
                }

                AttributeModifier luck = new AttributeModifier(UUID.fromString("f973e5f2-f25b-4b6a-a2c1-f7350b50f06f"),"luck",entity.getEntityAttribute(WLMAttributes.LUCK).getAttributeValue()*(1f+item.getRank(EnumEntry.LUCK)/5f),0);
                if(item.getRank(EnumEntry.LUCK)>0)
                {
                    if(!entity.getEntityAttribute(WLMAttributes.LUCK).hasModifier(luck))
                        entity.getEntityAttribute(WLMAttributes.LUCK).applyModifier(luck);
                }
                else
                {
                    if(entity.getEntityAttribute(WLMAttributes.LUCK).hasModifier(luck))
                        entity.getEntityAttribute(WLMAttributes.LUCK).removeModifier(luck);
                }
            }
        }

        public static void onHeal(LivingHealEvent event)
        {
            EntityLivingBase entity = event.getEntityLiving();
            if (entity instanceof EntityPlayer)
            {
                ItemStack mod = new MyInfoContainer(((EntityPlayer) entity).inventory, (EntityPlayer) entity).getSlot(43).getStack();
                CapabilityItem.Process item = new CapabilityItem.Process(mod);
                if(item.getRank(EnumEntry.HEALING_STRENGTH)>0)
                    event.setAmount((1+item.getRank(EnumEntry.HEALING_STRENGTH)/5f)*event.getAmount());
            }
        }

        public static void onDeath(LivingDeathEvent event)
        {
            EntityLivingBase entity = event.getEntityLiving();
            if(entity instanceof EntityPlayer)
            {
                ItemStack mod = new MyInfoContainer(((EntityPlayer) entity).inventory, (EntityPlayer) entity).getSlot(43).getStack();
                CapabilityItem.Process item = new CapabilityItem.Process(mod);
                if(item.getRank(EnumEntry.RESURRECTION)>0)
                {
                    if(Math.random() < item.getRank(EnumEntry.RESURRECTION)/20f)
                    {
                        event.setCanceled(true);
                        entity.setHealth(entity.getMaxHealth() / 2);
                    }
                }
            }
        }

        public int getRank(EnumEntry e)
        {
            for(EnumEntry entry : getEntries())
            {
                if(entry.getValue()==e.getValue())
                {
                    return entry.getRank();
                }
            }
            return 0;
        }

        public void setExperience(int e)
        {
            if(hasCapability)
                this.item.setExperience(Math.max(e,0));
        }

        public int getExperience()
        {
            if(hasCapability)
                return this.item.getExperience();
            else
                return 0;
        }

        public List<String> getIds()
        {
            if(hasCapability)
                return getEntries().stream().map(EnumEntry::getEntryId).collect(Collectors.toList());
            else
                return new ArrayList<>();
        }

        public void addEntry(EnumEntry entry, int rank)
        {
            if(hasCapability)
                this.item.entries.add(entry.getEntry(rank));
        }

        public void removeEntry(EnumEntry entry)
        {
            if(hasCapability)
                this.item.entries.remove(entry);
        }

        public void setEntries(List<EnumEntry> entries)
        {
            if(hasCapability)
                this.item.entries = entries;
        }

        public List<EnumEntry> getEntries()
        {
            if(hasCapability)
                return item.entries;
            else
                return new ArrayList<>();
        }

        public void setLevel(int level)
        {
            if(hasCapability)
                this.item.level = level;
        }

        public int getLevel()
        {
            if (hasCapability)
                return item.level;
            else
                return 0;
        }

        public void setType(int type)
        {
            if(hasCapability)
                this.item.type = type;
        }

        public int getType()
        {
            if(hasCapability)
                return item.type;
            else
                return 0;
        }
    }
}
