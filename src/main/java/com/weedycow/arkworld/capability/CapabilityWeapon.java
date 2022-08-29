package com.weedycow.arkworld.capability;

import com.weedycow.arkworld.registry.CapabilityRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CapabilityWeapon implements INBTSerializable<NBTTagCompound>
{
    UUID uuid;
    int rank;
    int level;
    int max_level;
    int experience;
    int exp_need;
    float damage;
    int durability;
    int loss;
    int mana;
    int stamina;
    int sam;
    int countdown;
    int flameSeconds;
    int knockbackLevel;
    boolean tripleTap;
    boolean rightClick;
    boolean leftClick;
    float shootVelocity;
    float sharp;
    float critical;
    float durable;
    float lossReduce;
    float manaReduce;
    float staminaReduce;
    float samReduce;
    float vampire;
    float defenceReduce;
    float pause;
    float shackles;
    float silence;
    float sprint;
    float reflex;
    float shelter;
    float resistance;
    float attackSpeed;
    int attackRange;
    int buffAmount;
    int modBuff;
    int modLevel;
    int materialExperience;
    int animationTick;
    Entity entityHit;
    int mods;

    public void setMods(int mods)
    {
        this.mods = mods;
    }

    public int getMods()
    {
        return mods;
    }

    public float getAttackSpeed()
    {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed)
    {
        this.attackSpeed = attackSpeed;
    }

    public void setLeftClick(boolean leftClick)
    {
        this.leftClick = leftClick;
    }

    public boolean isLeftClick()
    {
        return leftClick;
    }

    public void setCountdown(int countdown)
    {
        this.countdown = countdown;
    }

    public int getCountdown()
    {
        return countdown;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setRightClick(boolean rightClick)
    {
        this.rightClick = rightClick;
    }

    public boolean isRightClick()
    {
        return rightClick;
    }

    public void setResistance(float resistance)
    {
        this.resistance = resistance;
    }

    public float getResistance()
    {
        return resistance;
    }

    public void setShelter(float shelter)
    {
        this.shelter = shelter;
    }

    public float getShelter()
    {
        return shelter;
    }

    public void setReflex(float reflex)
    {
        this.reflex = reflex;
    }

    public float getReflex()
    {
        return reflex;
    }

    public void setSprint(float sprint)
    {
        this.sprint = sprint;
    }

    public float getSprint()
    {
        return sprint;
    }

    public void setSilence(float silence)
    {
        this.silence = silence;
    }

    public float getSilence()
    {
        return silence;
    }

    public void setShackles(float shackles)
    {
        this.shackles = shackles;
    }

    public float getShackles()
    {
        return shackles;
    }

    public void setPause(float pause)
    {
        this.pause = pause;
    }

    public float getPause()
    {
        return pause;
    }

    public void setDefenceReduce(float defence_reduce)
    {
        this.defenceReduce = defence_reduce;
    }

    public float getDefenceReduce()
    {
        return defenceReduce;
    }

    public void setVampire(float vampire)
    {
        this.vampire = vampire;
    }

    public float getVampire()
    {
        return vampire;
    }

    public void setSamReduce(float samReduce)
    {
        this.samReduce = samReduce;
    }

    public float getSamReduce()
    {
        return samReduce;
    }

    public void setStaminaReduce(float staminaReduce)
    {
        this.staminaReduce = staminaReduce;
    }

    public float getStaminaReduce()
    {
        return staminaReduce;
    }

    public void setManaReduce(float manaReduce)
    {
        this.manaReduce = manaReduce;
    }

    public float getManaReduce()
    {
        return manaReduce;
    }

    public void setLossReduce(float lossReduce)
    {
        this.lossReduce = lossReduce;
    }

    public float getLossReduce()
    {
        return lossReduce;
    }

    public void setDurable(float durable)
    {
        this.durable = durable;
    }

    public float getDurable()
    {
        return durable;
    }

    public void setCritical(float critical)
    {
        this.critical = critical;
    }

    public float getCritical()
    {
        return critical;
    }

    public void setSharp(float sharp)
    {
        this.sharp = sharp;
    }

    public float getSharp()
    {
        return sharp;
    }

    public void setMaxLevel(int max_level)
    {
        this.max_level = max_level;
    }

    public int getMaxLevel()
    {
        return max_level;
    }

    public void setLoss(int loss)
    {
        this.loss = loss;
    }

    public int getLoss()
    {
        return loss;
    }

    public void setDurability(int durability)
    {
        this.durability = durability;
    }

    public int getDurability()
    {
        return durability;
    }

    public void setSam(int sam)
    {
        this.sam = sam;
    }

    public int getSam()
    {
        return sam;
    }

    public void setStamina(int stamina)
    {
        this.stamina = stamina;
    }

    public int getStamina()
    {
        return stamina;
    }

    public int getMana()
    {
        return mana;
    }

    public void setMana(int mana)
    {
        this.mana = mana;
    }

    public void setShootVelocity(float shootVelocity)
    {
        this.shootVelocity = shootVelocity;
    }

    public float getShootVelocity()
    {
        return shootVelocity;
    }

    public void setTripleTap(boolean tripleTap)
    {
        this.tripleTap = tripleTap;
    }

    public boolean getTripleTap()
    {
        return tripleTap;
    }

    public void setKnockbackLevel(int knockbackLevel)
    {
        this.knockbackLevel = knockbackLevel;
    }

    public int getKnockbackLevel()
    {
        return knockbackLevel;
    }

    public void setFlameSeconds(int flameSeconds)
    {
        this.flameSeconds = flameSeconds;
    }

    public int getFlameSeconds()
    {
        return flameSeconds;
    }

    public void setDamage(float damage)
    {
        this.damage = damage;
    }

    public float getDamage()
    {
        return damage;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getRank()
    {
        return rank;
    }

    public void setRank(int r)
    {
        this.rank = r;
    }

    public int getExperience()
    {
        return experience;
    }

    public void setExperience(int e)
    {
        this.experience = e;
    }

    public int getExpNeed()
    {
        return exp_need;
    }

    public void setExpNeed(int e)
    {
        this.exp_need = e;
    }

    public int getModBuff()
    {
        return modBuff;
    }

    public void setModBuff(int modBuff)
    {
        this.modBuff = modBuff;
    }

    public void setModLevel(int modLevel)
    {
        this.modLevel = modLevel;
    }

    public int getModLevel()
    {
        return modLevel;
    }

    public void setMaterialExperience(int materialExperience)
    {
        this.materialExperience = materialExperience;
    }

    public int getMaterialExperience()
    {
        return materialExperience;
    }

    public void setEntityHit(Entity entityHit)
    {
        this.entityHit = entityHit;
    }

    public Entity getEntityHit()
    {
        return entityHit;
    }

    public void setAttackRange(int attackRange)
    {
        this.attackRange = attackRange;
    }

    public int getAttackRange()
    {
        return attackRange;
    }

    public int getAnimationTick()
    {
        return animationTick;
    }

    public void setAnimationTick(int animationTick)
    {
        this.animationTick = animationTick;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        if(uuid!=null)
            nbt.setUniqueId("uuid",uuid);

        nbt.setInteger("countdown",countdown);

        nbt.setInteger("level",level);

        nbt.setInteger("experience", experience);

        nbt.setInteger("rank",rank);

        nbt.setInteger("exp_need",exp_need);

        nbt.setFloat("damage",damage);

        nbt.setInteger("flame",flameSeconds);

        nbt.setInteger("knockback",knockbackLevel);

        nbt.setBoolean("triple_tap", tripleTap);

        nbt.setBoolean("right_click", rightClick);

        nbt.setBoolean("left_click", leftClick);

        nbt.setFloat("shoot_velocity",shootVelocity);

        nbt.setInteger("mana",mana);

        nbt.setInteger("stamina",stamina);

        nbt.setInteger("sam",sam);

        nbt.setInteger("durability",durability);

        nbt.setInteger("loss",loss);

        nbt.setInteger("max_level",max_level);

        nbt.setFloat("sharp",sharp);

        nbt.setFloat("critical",critical);

        nbt.setFloat("durable",durable);

        nbt.setFloat("lossReduce",lossReduce);

        nbt.setFloat("manaReduce",manaReduce);

        nbt.setFloat("staminaReduce",staminaReduce);

        nbt.setFloat("samReduce",samReduce);

        nbt.setFloat("vampire",vampire);

        nbt.setFloat("defence_reduce", defenceReduce);

        nbt.setFloat("pause",pause);

        nbt.setFloat("shackles",shackles);

        nbt.setFloat("silence",silence);

        nbt.setFloat("sprint",sprint);

        nbt.setFloat("reflex",reflex);

        nbt.setFloat("shelter",shelter);

        nbt.setFloat("resistance",resistance);

        nbt.setFloat("attack_speed", attackSpeed);

        nbt.setInteger("mod_buff",modBuff);

        nbt.setInteger("mod_level",modLevel);

        nbt.setInteger("material_experience",materialExperience);

        nbt.setInteger("attack_range",attackRange);

        nbt.setInteger("animation_tick",animationTick);

        nbt.setInteger("mods",mods);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("uuid"))
            setUuid(nbt.getUniqueId("uuid"));

        if(nbt.hasKey("level"))
            setLevel(nbt.getInteger("level"));

        if(nbt.hasKey("rank"))
            setRank(nbt.getInteger("rank"));

        if(nbt.hasKey("experience"))
            setExperience(nbt.getInteger("experience"));

        if(nbt.hasKey("exp_need"))
            setExpNeed(nbt.getInteger("exp_need"));

        if(nbt.hasKey("damage"))
            setDamage(nbt.getFloat("damage"));

        if(nbt.hasKey("flame"))
            setFlameSeconds(nbt.getInteger("flame"));

        if(nbt.hasKey("knockback"))
            setKnockbackLevel(nbt.getInteger("knockback"));

        if(nbt.hasKey("triple_tap"))
            setTripleTap(nbt.getBoolean("triple_tap"));

        if(nbt.hasKey("right_click"))
            setRightClick(nbt.getBoolean("right_click"));

        if(nbt.hasKey("left_click"))
            setLeftClick(nbt.getBoolean("left_click"));

        if(nbt.hasKey("shoot_velocity"))
            setShootVelocity(nbt.getFloat("shoot_velocity"));

        if(nbt.hasKey("mana"))
            setMana(nbt.getInteger("mana"));

        if(nbt.hasKey("stamina"))
            setStamina(nbt.getInteger("stamina"));

        if(nbt.hasKey("sam"))
            setSam(nbt.getInteger("sam"));

        if(nbt.hasKey("durability"))
            setDurability(nbt.getInteger("durability"));

        if(nbt.hasKey("loss"))
            setLoss(nbt.getInteger("loss"));

        if(nbt.hasKey("max_level"))
            setMaxLevel(nbt.getInteger("max_level"));

        if(nbt.hasKey("sharp"))
            setSharp(nbt.getFloat("sharp"));

        if(nbt.hasKey("critical"))
            setCritical(nbt.getFloat("critical"));

        if(nbt.hasKey("durable"))
            setDurable(nbt.getFloat("durable"));

        if(nbt.hasKey("lossReduce"))
            setLossReduce(nbt.getFloat("lossReduce"));

        if(nbt.hasKey("manaReduce"))
            setManaReduce(nbt.getFloat("manaReduce"));

        if(nbt.hasKey("staminaReduce"))
            setStaminaReduce(nbt.getFloat("staminaReduce"));

        if(nbt.hasKey("samReduce"))
            setSamReduce(nbt.getFloat("samReduce"));

        if(nbt.hasKey("vampire"))
            setVampire(nbt.getFloat("vampire"));

        if(nbt.hasKey("defence_reduce"))
            setDefenceReduce(nbt.getFloat("defence_reduce"));

        if(nbt.hasKey("pause"))
            setPause(nbt.getFloat("pause"));

        if(nbt.hasKey("shackles"))
            setShackles(nbt.getFloat("shackles"));

        if(nbt.hasKey("silence"))
            setSilence(nbt.getFloat("silence"));

        if(nbt.hasKey("sprint"))
            setSprint(nbt.getFloat("sprint"));

        if(nbt.hasKey("reflex"))
            setReflex(nbt.getFloat("reflex"));

        if(nbt.hasKey("shelter"))
            setShelter(nbt.getFloat("shelter"));

        if(nbt.hasKey("resistance"))
            setResistance(nbt.getFloat("resistance"));

        if(nbt.hasKey("countdown"))
            setCountdown(nbt.getInteger("countdown"));

        if(nbt.hasKey("attack_speed"))
            setAttackSpeed(nbt.getFloat("attack_speed"));

        if(nbt.hasKey("mod_buff"))
            setModBuff(nbt.getInteger("mod_buff"));

        if(nbt.hasKey("mod_level"))
            setModLevel(nbt.getInteger("mod_level"));

        if(nbt.hasKey("material_experience"))
            setMaterialExperience(nbt.getInteger("material_experience"));

        if(nbt.hasKey("attack_range"))
            setAttackRange(nbt.getInteger("attack_range"));

        if(nbt.hasKey("animation_tick"))
            setAnimationTick(nbt.getInteger("animation_tick"));

        if(nbt.hasKey("mods"))
            setMods(nbt.getInteger("mods"));
    }

    public static class Storage implements Capability.IStorage<CapabilityWeapon>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<CapabilityWeapon> capability, CapabilityWeapon instance, EnumFacing side)
        {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<CapabilityWeapon> capability, CapabilityWeapon instance, EnumFacing side, NBTBase nbt)
        {
            if(nbt instanceof NBTTagCompound)
                instance.deserializeNBT((NBTTagCompound) nbt);
        }
    }

    public static class Provide implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider
    {
        private final CapabilityWeapon instance;
        private final Capability<CapabilityWeapon> capability;

        public Provide()
        {
            instance = new CapabilityWeapon();
            capability = CapabilityRegistry.capWeapon;
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
        ItemStack itemStack;
        boolean hasCapability;
        CapabilityWeapon weapon;

        public static final Buffs[] buffs = {Buffs.FLAME_SECONDS,Buffs.KNOCKBACK_LEVEL,Buffs.TRIPLE_TAP,Buffs.SHOOT_VELOCITY,Buffs.SHARP,
                Buffs.CRITICAL,Buffs.DURABLE,Buffs.LOSS_REDUCE,Buffs.MANA_REDUCE, Buffs.STAMINA_REDUCE,Buffs.SAM_REDUCE,Buffs.VAMPIRE,
                Buffs.DEFENCE_REDUCE,Buffs.SHACKLES,Buffs.SILENCE,Buffs.SPRINT,Buffs.REFLEX,Buffs.SHELTER,Buffs.RESISTANCE,Buffs.ATTACK_SPEED,
                Buffs.ATTACK_RANGE};

        public Process(ItemStack stack)
        {
            this.itemStack = stack;
            this.weapon = stack.getCapability(CapabilityRegistry.capWeapon, null);
            this.hasCapability = stack.hasCapability(CapabilityRegistry.capWeapon, null);
        }

        public enum Buffs
        {
            FLAME_SECONDS(0),
            KNOCKBACK_LEVEL(1),
            TRIPLE_TAP(2),
            SHOOT_VELOCITY(3),
            SHARP(4),
            CRITICAL(5),
            DURABLE(6),
            LOSS_REDUCE(7),
            MANA_REDUCE(8),
            STAMINA_REDUCE(9),
            SAM_REDUCE(10),
            VAMPIRE(11),
            DEFENCE_REDUCE(12),
            SHACKLES(13),
            SILENCE(14),
            SPRINT(15),
            REFLEX(16),
            SHELTER(17),
            RESISTANCE(18),
            ATTACK_SPEED(19),
            ATTACK_RANGE(20);

            final int value;

            Buffs(int value)
            {
                this.value = value;
            }

            public static Buffs getBuff(int value)
            {
                for(Buffs buff : buffs)
                {
                    if(buff.value==value)
                    {
                        return buff;
                    }
                }
                return FLAME_SECONDS;
            }
        }

        public String getBuffs()
        {
            List<String> buffs = new ArrayList<>();

            if(getFlameSeconds()>0)
                buffs.add(I18n.format("item.arkworld.info.flame_seconds")+":+"+getFlameSeconds()+"s");

            if(getKnockbackLevel()>0)
                buffs.add(I18n.format("item.arkworld.info.knockback_level")+":+"+getKnockbackLevel());

            if(getTripleTap())
                buffs.add(I18n.format("item.arkworld.info.triple_tap"));

            if(getShootVelocity()>0)
                buffs.add(I18n.format("item.arkworld.info.shoot_velocity")+":+"+getShootVelocity());

            if(getSharp()>0)
                buffs.add(I18n.format("item.arkworld.info.sharp")+":+"+getSharp()*100+"%");

            if(getCritical()>0)
                buffs.add(I18n.format("item.arkworld.info.critical")+":+"+getCritical()*100+"%");

            if(getDurable()>0)
                buffs.add(I18n.format("item.arkworld.info.durable")+":+"+getDurable()*100+"%");

            if(getLossReduce()>0)
                buffs.add(I18n.format("item.arkworld.info.loss_reduce")+":-"+getLossReduce()*100+"%");

            if(getManaReduce()>0)
                buffs.add(I18n.format("item.arkworld.info.mana_reduce")+":-"+getManaReduce()*100+"%");

            if(getStaminaReduce()>0)
                buffs.add(I18n.format("item.arkworld.info.stamina_reduce")+":-"+getStaminaReduce()*100+"%");

            if(getSamReduce()>0)
                buffs.add(I18n.format("item.arkworld.info.sam_reduce")+":-"+getSamReduce()*100+"%");

            if(getVampire()>0)
                buffs.add(I18n.format("item.arkworld.info.vampire")+":"+getVampire()*100+"%");

            if(getDefenceReduce()>0)
                buffs.add(I18n.format("item.arkworld.info.defence_reduce")+":+"+getDefenceReduce()*100+"%");

            if(getShackles()>0)
                buffs.add(I18n.format("item.arkworld.info.shackles")+":+"+getShackles()*100+"%");

            if(getSilence()>0)
                buffs.add(I18n.format("item.arkworld.info.silence")+":+"+getSilence()*100+"%");

            if(getSprint()>0)
                buffs.add(I18n.format("item.arkworld.info.sprint")+":+"+getSprint()*100+"%");

            if(getReflex()>0)
                buffs.add(I18n.format("item.arkworld.info.reflex")+":+"+getReflex()*100+"%");

            if(getResistance()>0)
                buffs.add(I18n.format("item.arkworld.info.resistance")+":+"+getResistance()*100+"%");

            if(getAttackSpeed()>0)
                buffs.add(I18n.format("item.arkworld.info.attack_speed")+":+"+ getAttackSpeed()*100+"%");

            if(getShelter()>0)
                buffs.add(I18n.format("item.arkworld.info.shelter")+":+"+ getShelter());

            if(getAttackRange()>0)
                buffs.add(I18n.format("item.arkworld.info.attack_range")+":+"+getAttackRange());

            return buffs.toString().replace("[","").replace("]","");
        }

        public void add(Buffs buffs, int level)
        {
            if (buffs == Buffs.FLAME_SECONDS)
            {
                if (level == 1) addFlameSeconds(3);
                if (level == 2) addFlameSeconds(6);
                if (level == 3) addFlameSeconds(9);
            }

            if (buffs == Buffs.KNOCKBACK_LEVEL)
            {
                if (level == 1) addKnockbackLevel(1);
                if (level == 2) addKnockbackLevel(2);
                if (level == 3) addKnockbackLevel(3);
            }

            if (buffs == Buffs.TRIPLE_TAP)
            {
                setTripleTap(true);
            }

            if (buffs == Buffs.SHOOT_VELOCITY)
            {
                if (level == 1) addShootVelocity(0.5f);
                if (level == 2) addShootVelocity(1f);
                if (level == 3) addShootVelocity(1.5f);
            }

            if (buffs == Buffs.SHARP)
            {
                if (level == 1) addSharp(0.15f);
                if (level == 2) addSharp(0.3f);
                if (level == 3) addSharp(0.5f);
            }

            if (buffs == Buffs.CRITICAL)
            {
                if (level == 1) addCritical(0.05f);
                if (level == 2) addCritical(0.1f);
                if (level == 3) addCritical(0.15f);
            }

            if (buffs == Buffs.DURABLE)
            {
                if (level == 1) addDurable(0.1f);
                if (level == 2) addDurable(0.2f);
                if (level == 3) addDurable(0.3f);
            }

            if (buffs == Buffs.LOSS_REDUCE)
            {
                if (level == 1) addLossReduce(0.1f);
                if (level == 2) addLossReduce(0.2f);
                if (level == 3) addLossReduce(0.3f);
            }

            if (buffs == Buffs.MANA_REDUCE)
            {
                if (level == 1) addManaReduce(0.1f);
                if (level == 2) addManaReduce(0.2f);
                if (level == 3) addManaReduce(0.3f);
            }

            if (buffs == Buffs.STAMINA_REDUCE)
            {
                if (level == 1) addStaminaReduce(0.1f);
                if (level == 2) addStaminaReduce(0.2f);
                if (level == 3) addStaminaReduce(0.3f);
            }

            if (buffs == Buffs.SAM_REDUCE)
            {
                if (level == 1) addSamReduce(0.1f);
                if (level == 2) addSamReduce(0.2f);
                if (level == 3) addSamReduce(0.3f);
            }

            if (buffs == Buffs.VAMPIRE)
            {
                if (level == 1) addVampire(0.1f);
                if (level == 2) addVampire(0.2f);
                if (level == 3) addVampire(0.3f);
            }

            if (buffs == Buffs.DEFENCE_REDUCE)
            {
                if (level == 1) addDefenceReduce(0.1f);
                if (level == 2) addDefenceReduce(0.2f);
                if (level == 3) addDefenceReduce(0.3f);
            }

            if (buffs == Buffs.SHACKLES)
            {
                if (level == 1) addShackles(0.1f);
                if (level == 2) addShackles(0.2f);
                if (level == 3) addShackles(0.3f);
            }

            if (buffs == Buffs.SILENCE)
            {
                if (level == 1) addSilence(0.1f);
                if (level == 2) addSilence(0.2f);
                if (level == 3) addSilence(0.3f);
            }

            if (buffs == Buffs.SPRINT)
            {
                if (level == 1) addSprint(0.1f);
                if (level == 2) addSprint(0.2f);
                if (level == 3) addSprint(0.3f);
            }

            if (buffs == Buffs.REFLEX)
            {
                if (level == 1) addReflex(0.1f);
                if (level == 2) addReflex(0.2f);
                if (level == 3) addReflex(0.3f);
            }

            if (buffs == Buffs.SHELTER)
            {
                if (level == 1) addShelter(1);
                if (level == 2) addShelter(2);
                if (level == 3) addShelter(3);
            }

            if (buffs == Buffs.RESISTANCE)
            {
                if (level == 1) addResistance(0.1f);
                if (level == 2) addResistance(0.2f);
                if (level == 3) addResistance(0.3f);
            }

            if (buffs == Buffs.ATTACK_SPEED)
            {
                if (level == 1) addAttackSpeed(0.1f);
                if (level == 2) addAttackSpeed(0.2f);
                if (level == 3) addAttackSpeed(0.3f);
            }

            if (buffs == Buffs.ATTACK_RANGE)
            {
                addAttackRange(level);
            }
        }

        public void clear()
        {
            setFlameSeconds(0);
            setKnockbackLevel(0);
            setTripleTap(false);
            setShootVelocity(0);
            setSharp(0);
            setCritical(0);
            setDurable(0);
            setLossReduce(0);
            setManaReduce(0);
            setStaminaReduce(0);
            setSamReduce(0);
            setVampire(0);
            setDefenceReduce(0);
            setPause(0);
            setShackles(0);
            setSilence(0);
            setSprint(0);
            setReflex(0);
            setShelter(0);
            setResistance(0);
            setAttackSpeed(0);
            setAttackRange(0);
        }

        public int getAnimationTick()
        {
            if(hasCapability)
                return weapon.animationTick;
            else
                return 0;
        }

        public void setAnimationTick(int animationTick)
        {
            if(hasCapability)
                weapon.animationTick = animationTick;
        }

        public void setMaterialExperience(int materialExperience)
        {
            if(hasCapability)
                weapon.materialExperience = materialExperience;
        }

        public int getMaterialExperience()
        {
            if(hasCapability)
                return weapon.materialExperience;
            else
                return 0;
        }

        public int getModBuff()
        {
            if(hasCapability)
                return weapon.modBuff;
            else
                return 0;
        }

        public void setModBuff(int modBuff)
        {
            if(hasCapability)
                weapon.modBuff = modBuff;
        }

        public int getModLevel()
        {
            if(hasCapability)
                return weapon.modLevel;
            else
                return 0;
        }

        public void setModLevel(int level)
        {
            if(hasCapability)
                weapon.modLevel = level;
        }

        public void setUuid(UUID uuid)
        {
            if(hasCapability)
                weapon.setUuid(uuid);
        }

        public UUID getUuid()
        {
            if(hasCapability)
                return weapon.getUuid();
            else
                return UUID.randomUUID();
        }

        public CapabilityWeapon getWeapon()
        {
            if(hasCapability)
                return weapon;
            else
                return null;
        }

        public void setEntityHit(Entity entityHit)
        {
            if(hasCapability)
                weapon.entityHit = entityHit;
        }

        public Entity getEntityHit()
        {
            if(hasCapability)
                return weapon.entityHit;
            else
                return null;
        }

        public void addAttackRange(int e)
        {
            if (hasCapability && e>=0)
            {
                weapon.setAttackRange(weapon.getAttackRange()+e);
            }
        }

        public void setAttackRange(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.attackRange = l;
            }
        }

        public int getAttackRange()
        {
            if(hasCapability)
                return weapon.attackRange;
            else
                return 0;
        }

        public void addMods(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setMods(weapon.getMods()+l);
            }
        }

        public void reduceMods(int l)
        {
            if (hasCapability && l>0)
            {
                weapon.setMods(Math.max(weapon.getMods()-l,0));
            }
        }

        public void setMods(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setMods(l);
            }
        }

        public int getMods()
        {
            if(hasCapability)
                return weapon.getMods();
            else
                return 0;
        }

        public void addLevel(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setLevel(weapon.getLevel()+l);
            }
        }

        public void reduceLevel(int l)
        {
            if (hasCapability && l>0)
            {
                weapon.setLevel(Math.max(weapon.getLevel()-l,0));
            }
        }

        public void setLevel(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setLevel(l);
            }
        }

        public int getLevel()
        {
            if(hasCapability)
                return weapon.getLevel();
            else
                return 0;
        }

        public void addExperience(int e)
        {
            if (hasCapability && e>=0)
            {
                weapon.setExperience(weapon.getExperience()+e);
            }
        }

        public void reduceExperience(int e)
        {
            if (hasCapability && e<0)
            {
                weapon.setExperience(Math.max(weapon.getExperience()-e,0));
            }
        }

        public void setExperience(int e)
        {
            if (hasCapability && e>=0)
            {
                weapon.setExperience(e);
            }
        }

        public int getExperience()
        {
            return weapon.getExperience();
        }

        public void setRank(int r)
        {
            if (hasCapability && r>=0)
            {
                weapon.setRank(r);
            }
        }

        public int getRank()
        {
            if(hasCapability)
                return weapon.getRank();
            else
                return 0;
        }

        public void setExpNeed(int e)
        {
            if (hasCapability && e>=0)
            {
                weapon.setExpNeed(e);
            }
        }

        public int getExpNeed()
        {
            return weapon.getExpNeed();
        }

        public void addDamage(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setDamage(weapon.getDamage()+l);
            }
        }

        public void reduceDamage(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setDamage(Math.max(weapon.getDamage()-l,0));
            }
        }

        public void setDamage(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setDamage(l);
            }
        }

        public float getDamage()
        {
            return weapon.getDamage();
        }

        public void addFlameSeconds(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setFlameSeconds(weapon.getFlameSeconds()+l);
            }
        }

        public void reduceFlameSeconds(int l)
        {
            if (hasCapability && l>0)
            {
                weapon.setFlameSeconds(Math.max(weapon.getFlameSeconds()-l,0));
            }
        }

        public void setFlameSeconds(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setFlameSeconds(l);
            }
        }

        public int getFlameSeconds()
        {
            return weapon.getFlameSeconds();
        }

        public void addKnockbackLevel(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setKnockbackLevel(weapon.getKnockbackLevel()+l);
            }
        }

        public void reduceKnockbackLevel(int l)
        {
            if (hasCapability && l>0)
            {
                weapon.setKnockbackLevel(Math.max(weapon.getKnockbackLevel()-l,0));
            }
        }

        public void setKnockbackLevel(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setKnockbackLevel(l);
            }
        }

        public int getKnockbackLevel()
        {
            return weapon.getKnockbackLevel();
        }

        public void setTripleTap(boolean l)
        {
            if (hasCapability)
            {
                weapon.setTripleTap(l);
            }
        }

        public boolean getTripleTap()
        {
            return weapon.getTripleTap();
        }

        public void addShootVelocity(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setShootVelocity(weapon.getShootVelocity()+l);
            }
        }

        public void reduceShootVelocity(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setShootVelocity(Math.max(weapon.getShootVelocity()-l,0));
            }
        }

        public void setShootVelocity(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setShootVelocity(l);
            }
        }

        public float getShootVelocity()
        {
            return weapon.getShootVelocity();
        }

        public void addMana(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setMana(weapon.getMana()+l);
            }
        }

        public void reduceMana(int l)
        {
            if (hasCapability && l>0)
            {
                weapon.setMana(Math.max(weapon.getMana()-l,0));
            }
        }

        public void setMana(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setMana(l);
            }
        }

        public int getMana()
        {
            return weapon.getMana();
        }

        public void addStamina(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setStamina(weapon.getStamina()+l);
            }
        }

        public void reduceStamina(int l)
        {
            if (hasCapability && l>0)
            {
                weapon.setStamina(Math.max(weapon.getStamina()-l,0));
            }
        }

        public void setStamina(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setStamina(l);
            }
        }

        public int getStamina()
        {
            return weapon.getStamina();
        }

        public void addSam(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setSam(weapon.getSam()+l);
            }
        }

        public void reduceSam(int l)
        {
            if (hasCapability && l>0)
            {
                weapon.setSam(Math.max(weapon.getSam()-l,0));
            }
        }

        public void setSam(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setSam(l);
            }
        }

        public int getSam()
        {
            return weapon.getSam();
        }

        public void addDurability(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setDurability(weapon.getDurability()+l);
            }
        }

        public void reduceDurability(int l)
        {
            if (hasCapability && l>0)
            {
                weapon.setDurability(Math.max(weapon.getDurability()-l,0));
            }
        }

        public void setDurability(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setDurability(l);
            }
        }

        public int getDurability()
        {
            return weapon.getDurability();
        }

        public void addLoss(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setLoss(weapon.getLoss()+l);
            }
        }

        public void reduceLoss(int l)
        {
            if (hasCapability && l>0)
            {
                weapon.setLoss(Math.max(weapon.getLoss()-l,0));
            }
        }

        public void setLoss(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setLoss(l);
            }
        }

        public int getLoss()
        {
            return weapon.getLoss();
        }

        public void addMaxLevel(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setMaxLevel(weapon.getMaxLevel()+l);
            }
        }

        public void reduceMaxLevel(int l)
        {
            if (hasCapability && l>0)
            {
                weapon.setMaxLevel(Math.max(weapon.getMaxLevel()-l,0));
            }
        }

        public void setMaxLevel(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setMaxLevel(l);
            }
        }

        public int getMaxLevel()
        {
            return weapon.getMaxLevel();
        }

        public void addSharp(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setSharp(weapon.getSharp()+l);
            }
        }

        public void reduceSharp(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setSharp(Math.max(weapon.getSharp()-l,0));
            }
        }

        public void setSharp(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setSharp(l);
            }
        }

        public float getSharp()
        {
            return weapon.getSharp();
        }

        public void addCritical(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setCritical(weapon.getCritical()+l);
            }
        }

        public void reduceCritical(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setCritical(Math.max(weapon.getCritical()-l,0));
            }
        }

        public void setCritical(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setCritical(l);
            }
        }

        public float getCritical()
        {
            return weapon.getCritical();
        }

        public void addDurable(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setDurable(weapon.getDurable()+l);
            }
        }

        public void reduceDurable(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setDurable(Math.max(weapon.getDurable()-l,0));
            }
        }

        public void setDurable(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setDurable(l);
            }
        }

        public float getDurable()
        {
            return weapon.getDurable();
        }

        public void addLossReduce(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setLossReduce(weapon.getLossReduce()+l);
            }
        }

        public void reduceLossReduce(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setLossReduce(Math.max(weapon.getLossReduce()-l,0));
            }
        }

        public void setLossReduce(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setLossReduce(l);
            }
        }

        public float getLossReduce()
        {
            return weapon.getLossReduce();
        }

        public void addManaReduce(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setManaReduce(weapon.getManaReduce()+l);
            }
        }

        public void reduceManaReduce(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setManaReduce(Math.max(weapon.getManaReduce()-l,0));
            }
        }

        public void setManaReduce(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setManaReduce(l);
            }
        }

        public float getManaReduce()
        {
            return weapon.getManaReduce();
        }

        public void addStaminaReduce(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setStaminaReduce(weapon.getStaminaReduce()+l);
            }
        }

        public void reduceStaminaReduce(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setStaminaReduce(Math.max(weapon.getStaminaReduce()-l,0));
            }
        }

        public void setStaminaReduce(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setStaminaReduce(l);
            }
        }

        public float getStaminaReduce()
        {
            return weapon.getStaminaReduce();
        }

        public void addSamReduce(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setSamReduce(weapon.getSamReduce()+l);
            }
        }

        public void reduceSamReduce(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setSamReduce(Math.max(weapon.getSamReduce()-l,0));
            }
        }

        public void setSamReduce(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setSamReduce(l);
            }
        }

        public float getSamReduce()
        {
            return weapon.getSamReduce();
        }

        public void addVampire(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setVampire(weapon.getVampire()+l);
            }
        }

        public void reduceVampire(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setVampire(Math.max(weapon.getVampire()-l,0));
            }
        }

        public void setVampire(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setVampire(l);
            }
        }

        public float getVampire()
        {
            return weapon.getVampire();
        }
        public void addDefenceReduce(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setDefenceReduce(weapon.getDefenceReduce()+l);
            }
        }

        public void reduceDefenceReduce(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setDefenceReduce(Math.max(weapon.getDefenceReduce()-l,0));
            }
        }

        public void setDefenceReduce(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setDefenceReduce(l);
            }
        }

        public float getDefenceReduce()
        {
            return weapon.getDefenceReduce();
        }
        public void addPause(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setPause(weapon.getPause()+l);
            }
        }

        public void reducePause(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setPause(Math.max(weapon.getPause()-l,0));
            }
        }

        public void setPause(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setPause(l);
            }
        }

        public float getPause()
        {
            return weapon.getPause();
        }
        public void addShackles(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setShackles(weapon.getShackles()+l);
            }
        }

        public void reduceShackles(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setShackles(Math.max(weapon.getShackles()-l,0));
            }
        }

        public void setShackles(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setShackles(l);
            }
        }

        public float getShackles()
        {
            return weapon.getShackles();
        }
        public void addSilence(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setSilence(weapon.getSilence()+l);
            }
        }

        public void reduceSilence(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setSilence(Math.max(weapon.getSilence()-l,0));
            }
        }

        public void setSilence(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setSilence(l);
            }
        }

        public float getSilence()
        {
            return weapon.getSilence();
        }
        public void addSprint(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setSprint(weapon.getSprint()+l);
            }
        }

        public void reduceSprint(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setSprint(Math.max(weapon.getSprint()-l,0));
            }
        }

        public void setSprint(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setSprint(l);
            }
        }

        public float getSprint()
        {
            return weapon.getSprint();
        }
        public void addReflex(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setReflex(weapon.getReflex()+l);
            }
        }

        public void reduceReflex(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setReflex(Math.max(weapon.getReflex()-l,0));
            }
        }

        public void setReflex(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setReflex(l);
            }
        }

        public float getReflex()
        {
            return weapon.getReflex();
        }

        public void addShelter(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setShelter(weapon.getShelter()+l);
            }
        }

        public void reduceShelter(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setShelter(Math.max(weapon.getShelter()-l,0));
            }
        }

        public void setShelter(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setShelter(l);
            }
        }

        public float getShelter()
        {
            return weapon.getShelter();
        }

        public void addResistance(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setResistance(weapon.getResistance()+l);
            }
        }

        public void reduceResistance(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setResistance(Math.max(weapon.getResistance()-l,0));
            }
        }

        public void setResistance(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setResistance(l);
            }
        }

        public float getResistance()
        {
            if(hasCapability)
                return weapon.getResistance();
            else
                return 0;
        }

        public void setRightClick(boolean l)
        {
            if(hasCapability)
                weapon.setRightClick(l);
        }

        public boolean getRightClick()
        {
            if(hasCapability)
                return weapon.isRightClick();
            else
                return false;
        }

        public void setLeftClick(boolean l)
        {
            if(hasCapability)
                weapon.setLeftClick(l);
        }

        public boolean getLeftClick()
        {
            if(hasCapability)
                return weapon.isLeftClick();
            else
                return false;
        }

        public void addCountdown(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setCountdown(weapon.getCountdown()+l);
            }
        }

        public void reduceCountdown(int l)
        {
            if (hasCapability && l>0)
            {
                weapon.setCountdown(Math.max(weapon.getCountdown()-l,0));
            }
        }

        public void setCountdown(int l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setCountdown(l);
            }
        }

        public int getCountdown()
        {
            if(hasCapability)
                return weapon.getCountdown();
            else
                return 0;
        }

        public float getAttackSpeed()
        {
            if(hasCapability)
                return weapon.attackSpeed;
            else
                return 0;
        }

        public void setAttackSpeed(float reduceCast)
        {
            if(hasCapability)
                this.weapon.attackSpeed = reduceCast;
        }

        public void addAttackSpeed(float l)
        {
            if (hasCapability && l>=0)
            {
                weapon.setAttackSpeed(weapon.getAttackSpeed()+l);
            }
        }

        public void reduceAttackSpeed(float l)
        {
            if (hasCapability && l>0)
            {
                weapon.setAttackSpeed(Math.max(weapon.getAttackSpeed()-l,0));
            }
        }
    }
}
