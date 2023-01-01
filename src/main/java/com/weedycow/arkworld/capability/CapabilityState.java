package com.weedycow.arkworld.capability;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.entity.enemy.union.boss.Patriot;
import com.weedycow.arkworld.entity.enemy.union.normal.GuerrillaHerald;
import com.weedycow.arkworld.networlk.PacketState;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.arkworld.util.ParticleList;
import com.weedycow.weedylib.entity.EnumCamps;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.util.AoeRangeUtil;
import com.weedycow.weedylib.util.EntityUtil;
import com.weedycow.weedylib.util.ListUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CapabilityState implements INBTSerializable<NBTTagCompound>
{
    private List<EnumState> states = new ArrayList<>();

    public List<String> getIds()
    {
        return getStates().stream().map(EnumState::getStateId).collect(Collectors.toList());
    }

    public List<EnumState> getStates()
    {
        return this.states;
    }

    public void setStates(List<EnumState> states)
    {
        this.states = states;
    }

    public void addState(EnumState state)
    {
        this.states.add(state);
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setIntArray("state",states.stream().map(EnumState::getStateId).collect(Collectors.toList()).stream().map(Integer::valueOf)
                .collect(Collectors.toList()).stream().mapToInt(Integer::intValue).toArray());

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("state"))
        {
            setStates(Arrays.stream(nbt.getIntArray("state")).boxed().collect(Collectors.toList()).stream().map(String::valueOf)
                    .collect(Collectors.toList()).stream().map(EnumState::getStateFormId).collect(Collectors.toList()));
        }
    }

    public static class Storage implements Capability.IStorage<CapabilityState>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<CapabilityState> capability, CapabilityState instance, EnumFacing side)
        {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<CapabilityState> capability, CapabilityState instance, EnumFacing side, NBTBase nbt)
        {
            if(nbt instanceof NBTTagCompound)
                instance.deserializeNBT((NBTTagCompound) nbt);
        }
    }

    public static class Provide implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider
    {
        private final CapabilityState instance;
        private final Capability<CapabilityState> capability;

        public Provide()
        {
            instance = new CapabilityState();
            capability = CapabilityRegistry.capState;
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
        int tacticalOrderLevel;
        EntityLivingBase entity;
        public CapabilityState state;
        boolean hasCapability;
        IAttributeInstance moveSpeed;
        IAttributeInstance attackDamage;
        IAttributeInstance defensivePower;
        IAttributeInstance spellResistance;
        IAttributeInstance attackInterval;
        IAttributeInstance maxHealth;
        AttributeModifier moveSpeedCold;
        AttributeModifier moveSpeedFreeze;
        AttributeModifier moveSpeedVertigo;
        AttributeModifier moveSpeedPause;
        AttributeModifier moveSpeedShackles;
        AttributeModifier moveSpeedSleep;
        AttributeModifier moveSpeedInspire;
        AttributeModifier attackDamageInspire;
        AttributeModifier attackDamageEnergetic;
        AttributeModifier attackDamageTacticalOrder;
        AttributeModifier attackDamageOriginiumStimulation;
        AttributeModifier defensivePowerInspire;
        AttributeModifier defensivePowerTacticalOrder;
        AttributeModifier defensivePowerReduceDefense;
        AttributeModifier defensivePowerAddDefense;
        AttributeModifier attackIntervalInspire;
        AttributeModifier attackIntervalCold;
        AttributeModifier attackIntervalFreeze;
        AttributeModifier attackIntervalVertigo;
        AttributeModifier attackIntervalOriginiumStimulation;
        AttributeModifier maxHealthWeUnite;
        AttributeModifier defensivePowerWeUnite;
        AttributeModifier spellResistancePowerWeUnite;

        public Process(EntityLivingBase entity)
        {
            this.entity = entity;
            this.state = entity.getCapability(CapabilityRegistry.capState, null);
            this.hasCapability = entity.hasCapability(CapabilityRegistry.capState, null);
            this.tacticalOrderLevel = entity.world.getEntitiesWithinAABB(Patriot.class,new AoeRangeUtil(entity,32)).size()
                    + entity.world.getEntitiesWithinAABB(GuerrillaHerald.class,new AoeRangeUtil(entity,32)).size();

            this.moveSpeed = entity.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED);
            this.attackDamage = entity.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE);
            this.defensivePower = entity.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER);
            this.attackInterval = entity.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL);
            this.maxHealth = entity.getEntityAttribute(WLMAttributes.MAX_HEALTH);
            this.spellResistance = entity.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE);

            if(spellResistance!=null)
            {
                this.spellResistancePowerWeUnite = new AttributeModifier(UUID.fromString("c7b9ee09-2f65-414d-bc69-42512e1f218d"), "spell_resistance_power_we_unite", spellResistance.getAttributeValue(), 0);
            }

            if(moveSpeed!=null)
            {
                this.moveSpeedCold = new AttributeModifier(UUID.fromString("30dafd5a-9584-44c1-db5f-8209341035e9"), "move_speed_cold", -moveSpeed.getAttributeValue() / 2, 0);
                this.moveSpeedFreeze = new AttributeModifier(UUID.fromString("b499172a-84b1-fcb9-0d87-2391181c89b5"), "move_speed_freeze", -moveSpeed.getAttributeValue() * 10, 0);
                this.moveSpeedVertigo = new AttributeModifier(UUID.fromString("520d9b6a-eaeb-535a-680b-1ae61f504dfd"), "move_speed_vertigo", -moveSpeed.getAttributeValue() * 10, 0);
                this.moveSpeedPause = new AttributeModifier(UUID.fromString("53a7f6a1-11f2-40cc-9590-54584efe2112"), "move_speed_pause", -moveSpeed.getAttributeValue()/2, 0);
                this.moveSpeedShackles = new AttributeModifier(UUID.fromString("67ef6762-6d37-4491-80c1-04055bc6c691"), "move_speed_shackles", -moveSpeed.getAttributeValue()*10, 0);
                this.moveSpeedSleep = new AttributeModifier(UUID.fromString("ed35e207-86d6-4fdb-925f-42bfb7576510"), "move_speed_sleep", -moveSpeed.getAttributeValue()*10, 0);
                this.moveSpeedInspire = new AttributeModifier(UUID.fromString("38b3c197-dabc-4d23-8de8-46a5eb924bf5"), "move_speed_inspire", moveSpeed.getAttributeValue()*getLevel(EnumState.INSPIRE)/10f, 0);
            }

            if(attackDamage!=null)
            {
                this.attackDamageInspire = new AttributeModifier(UUID.fromString("626c6e30-b5c3-4616-a4b8-2d279bf95544"), "attack_damage_inspire", attackDamage.getAttributeValue()*getLevel(EnumState.INSPIRE)/10f, 0);
                this.attackDamageEnergetic = new AttributeModifier(UUID.fromString("f17540fa-7f7c-4db8-bce0-8d2bc874c2a1"), "attack_damage_energetic", attackDamage.getAttributeValue()*(entity.getHealth()/entity.getHealth()), 0);
                this.attackDamageTacticalOrder = new AttributeModifier(UUID.fromString("0b03fc82-6d15-ff9f-a09d-13e50ec7c028"), "attack_damage_tactical_order", tacticalOrderLevel * 0.2 * attackDamage.getAttributeValue(), 0);
                this.attackDamageOriginiumStimulation = new AttributeModifier(UUID.fromString("4abf67cf-6731-4525-8607-3adaccf2f984"), "attack_damage_originium_stimulation", attackDamage.getAttributeValue() * 1.8, 0);
            }

            if(defensivePower!=null)
            {
                this.defensivePowerAddDefense = new AttributeModifier(UUID.fromString("92f5bf07-f048-4196-8582-e3e860d6630a"), "defensive_power_add_defense", -defensivePower.getAttributeValue()/2, 0);
                this.defensivePowerInspire = new AttributeModifier(UUID.fromString("27b5fd58-b26a-4fef-bbad-67d4362cc077"), "defensive_power_inspire", defensivePower.getAttributeValue()*getLevel(EnumState.INSPIRE)/10f, 0);
                this.defensivePowerReduceDefense = new AttributeModifier(UUID.fromString("f95deaf0-1adc-4853-8235-30864640b233"), "defensive_power_reduce_defense", -defensivePower.getAttributeValue()/2, 0);
                this.defensivePowerTacticalOrder = new AttributeModifier(UUID.fromString("15bb7663-85fd-d06e-968a-f15bccd3b65d"), "defensive_power_tactical_order", tacticalOrderLevel * 10, 0);
                this.defensivePowerWeUnite = new AttributeModifier(UUID.fromString("4954338a-75c1-4507-bdaf-939d11d2a346"), "defensive_power_we_union", defensivePower.getAttributeValue(), 0);
            }

            if(attackInterval!=null)
            {
                this.attackIntervalInspire = new AttributeModifier(UUID.fromString("1fcdf56c-a7b9-414d-899b-9ca7bb9473d9"), "attack_interval_inspire", -attackInterval.getAttributeValue()*getLevel(EnumState.INSPIRE)/10f, 0);
                this.attackIntervalCold = new AttributeModifier(UUID.fromString("45631d2e-865c-44e9-abed-1a7bc7f3a0fe"), "attack_interval_cold", attackInterval.getAttributeValue(), 0);
                this.attackIntervalFreeze = new AttributeModifier(UUID.fromString("97f621ef-d518-1370-cdce-f5099ae0c90f"), "attack_interval_freeze", attackInterval.getAttributeValue() * 10, 0);
                this.attackIntervalVertigo = new AttributeModifier(UUID.fromString("6e0285a1-ea20-c621-a38b-13289353ef68"), "attack_interval_vertigo", attackInterval.getAttributeValue() * 10, 0);
                this.attackIntervalOriginiumStimulation = new AttributeModifier(UUID.fromString("f17540fa-7f7c-4db8-bce0-8d2bc874c2a1"), "attack_interval_originium_stimulation", -attackInterval.getAttributeValue() * 1.8, 0);
            }

            if(maxHealth != null)
            {
                this.maxHealthWeUnite = new AttributeModifier(UUID.fromString("14f91b31-53d0-40cb-846b-015f452f166e"),"max_health_we_unite",maxHealth.getAttributeValue(),0);
            }
        }

        public int getLevel(EnumState sta)
        {
            if(hasCapability && state.getStates()!=null)
            {
                for (EnumState s : state.getStates())
                {
                    if (s == sta)
                        return s.getLevel();
                }
                return 0;
            }
            else
                return 0;
        }

        public int getTick(EnumState sta)
        {
            if(hasCapability && state.getStates()!=null)
            {
                for (EnumState s : state.getStates())
                {
                    if (s == sta)
                        return s.getTick();
                }
                return 0;
            }
            else return 0;
        }

        public void addFunctionOnlyTick(EnumState s, int tick)
        {
            addFunction(s,11,tick);
        }

        //level must 2 number
        public void addFunctionOnlyLevel(EnumState s, int level)
        {
            addFunction(s,level,6000);
        }

        public void addFunction(EnumState s, int level, int tick)
        {
            if(hasCapability && !entity.world.isRemote)
            {
                if((s==EnumState.COLD || s==EnumState.FREEZE || s ==EnumState.VERTIGO || s==EnumState.BURNING_BREATH) && state.getStates().contains(EnumState.RESISTANCE))
                    state.addState(s.getLevelTickState(level,tick/2));
                else if(s != EnumState.NERVE_INJURY)
                    state.addState(s.getLevelTickState(level,tick));

                if (s == EnumState.NERVE_INJURY)
                {
                    if(!state.getStates().contains(EnumState.NERVE_INJURY))
                        state.addState(s.getLevelTickState(10+level,tick));
                    else if(s.getLevel()<50+10)
                        state.addState(s.getLevelTickState(getLevel(s)+level,tick));
                }

                if (s == EnumState.COLD)
                {
                    if(moveSpeed!=null && !moveSpeed.hasModifier(moveSpeedCold))
                        moveSpeed.applyModifier(moveSpeedCold);
                    if(attackInterval!=null && !attackInterval.hasModifier(attackIntervalCold))
                        attackInterval.applyModifier(attackIntervalCold);
                }

                if (s == EnumState.FREEZE)
                {
                    if(moveSpeed!=null && !moveSpeed.hasModifier(moveSpeedFreeze))
                        moveSpeed.applyModifier(moveSpeedFreeze);
                    if(attackInterval!=null && !attackInterval.hasModifier(attackIntervalFreeze))
                        attackInterval.applyModifier(attackIntervalFreeze);
                }

                if(s==EnumState.VERTIGO)
                {
                    if(moveSpeed!=null && !moveSpeed.hasModifier(moveSpeedVertigo))
                        moveSpeed.applyModifier(moveSpeedVertigo);
                    if(attackInterval!=null && !attackInterval.hasModifier(attackIntervalVertigo))
                        attackInterval.applyModifier(attackIntervalVertigo);
                }

                if(s==EnumState.WE_UNITE)
                {
                    if(maxHealth!=null && !maxHealth.hasModifier(maxHealthWeUnite))
                        maxHealth.applyModifier(maxHealthWeUnite);

                    if(defensivePower!=null && !defensivePower.hasModifier(defensivePowerWeUnite))
                        defensivePower.applyModifier(defensivePowerWeUnite);

                    if(spellResistance!=null && !spellResistance.hasModifier(spellResistancePowerWeUnite))
                        spellResistance.applyModifier(spellResistancePowerWeUnite);
                }

                if(s==EnumState.REDUCE_DEFENSE)
                {
                    if(defensivePower!=null && !defensivePower.hasModifier(defensivePowerReduceDefense))
                        defensivePower.applyModifier(defensivePowerReduceDefense);
                }

                if(s==EnumState.ADD_DEFENSE)
                {
                    if(defensivePower!=null && !defensivePower.hasModifier(defensivePowerAddDefense))
                        defensivePower.applyModifier(defensivePowerAddDefense);
                }

                if(s==EnumState.PAUSE)
                {
                    if(moveSpeed!=null && !moveSpeed.hasModifier(moveSpeedPause))
                        moveSpeed.applyModifier(moveSpeedPause);
                }

                if(s==EnumState.SHACKLES)
                {
                    if(moveSpeed!=null && !moveSpeed.hasModifier(moveSpeedShackles))
                        moveSpeed.applyModifier(moveSpeedShackles);
                }

                if(s==EnumState.ORIGINIUM_STIMULATION)
                {
                    if(attackDamage!=null && !attackDamage.hasModifier(attackDamageOriginiumStimulation))
                        attackDamage.applyModifier(attackDamageOriginiumStimulation);
                    if(attackInterval!=null && !attackInterval.hasModifier(attackIntervalOriginiumStimulation))
                        attackInterval.applyModifier(attackIntervalOriginiumStimulation);
                }

                if(s==EnumState.ENERGETIC)
                {
                    if(attackDamage!=null && !attackDamage.hasModifier(attackDamageEnergetic))
                        attackDamage.applyModifier(attackDamageEnergetic);
                }

                if(s==EnumState.SLEEP)
                {
                    if(moveSpeed!=null && !moveSpeed.hasModifier(moveSpeedSleep))
                        moveSpeed.applyModifier(moveSpeedSleep);
                }

                if(s==EnumState.INSPIRE)
                {
                    if(moveSpeed!=null && !moveSpeed.hasModifier(moveSpeedInspire))
                        moveSpeed.applyModifier(moveSpeedInspire);
                    if(attackDamage!=null && !attackDamage.hasModifier(attackDamageInspire))
                        attackDamage.applyModifier(attackDamageInspire);
                    if(defensivePower!=null && !defensivePower.hasModifier(defensivePowerInspire))
                        defensivePower.applyModifier(defensivePowerInspire);
                    if(attackInterval!=null && !attackInterval.hasModifier(attackIntervalInspire))
                        attackInterval.applyModifier(attackIntervalInspire);
                }
            }
        }

        public void removeFunction()
        {
            if(hasCapability && !entity.world.isRemote)
            {
                if(state.getStates().contains(EnumState.NERVE_INJURY))
                {
                    if(getLevel(EnumState.NERVE_INJURY)>=50+10)
                    {
                        removeState(EnumState.NERVE_INJURY);
                        entity.attackEntityFrom(DamageSource.GENERIC, 50);
                        addFunctionOnlyTick(EnumState.VERTIGO, 200);
                    }
                }

                if (!state.getStates().contains(EnumState.COLD))
                {
                    if(moveSpeed!=null && moveSpeed.hasModifier(moveSpeedCold))
                        moveSpeed.removeModifier(moveSpeedCold);
                    if(attackInterval!=null && attackInterval.hasModifier(attackIntervalCold))
                        attackInterval.removeModifier(attackIntervalCold);
                }

                if (!state.getStates().contains(EnumState.FREEZE))
                {
                    if(moveSpeed!=null && moveSpeed.hasModifier(moveSpeedFreeze))
                        moveSpeed.removeModifier(moveSpeedFreeze);
                    if(attackInterval!=null && attackInterval.hasModifier(attackIntervalFreeze))
                        attackInterval.removeModifier(attackIntervalFreeze);
                }

                if (!state.getStates().contains(EnumState.VERTIGO))
                {
                    if(moveSpeed!=null && moveSpeed.hasModifier(moveSpeedVertigo))
                        moveSpeed.removeModifier(moveSpeedVertigo);
                    if(attackInterval!=null && attackInterval.hasModifier(attackIntervalVertigo))
                        attackInterval.removeModifier(attackIntervalVertigo);
                }

                if(!state.getStates().contains(EnumState.WE_UNITE))
                {
                    if(maxHealth!=null && maxHealth.hasModifier(maxHealthWeUnite))
                        maxHealth.removeModifier(maxHealthWeUnite);

                    if(defensivePower!=null && defensivePower.hasModifier(defensivePowerWeUnite))
                        defensivePower.removeModifier(defensivePowerWeUnite);

                    if(spellResistance!=null && spellResistance.hasModifier(spellResistancePowerWeUnite))
                        spellResistance.removeModifier(spellResistancePowerWeUnite);
                }

                if(!state.getStates().contains(EnumState.REDUCE_DEFENSE))
                {
                    if(defensivePower!=null && defensivePower.hasModifier(defensivePowerReduceDefense))
                        maxHealth.removeModifier(defensivePowerReduceDefense);
                }

                if(!state.getStates().contains(EnumState.ADD_DEFENSE))
                {
                    if(defensivePower!=null && defensivePower.hasModifier(defensivePowerAddDefense))
                        defensivePower.removeModifier(defensivePowerAddDefense);
                }

                if(!state.getStates().contains(EnumState.PAUSE))
                {
                    if(moveSpeed!=null && moveSpeed.hasModifier(moveSpeedPause))
                        moveSpeed.removeModifier(moveSpeedPause);
                }

                if(!state.getStates().contains(EnumState.SHACKLES))
                {
                    if(moveSpeed!=null && moveSpeed.hasModifier(moveSpeedShackles))
                        moveSpeed.removeModifier(moveSpeedShackles);
                }

                if(!state.getStates().contains(EnumState.ORIGINIUM_STIMULATION))
                {
                    if(attackDamage!=null && attackDamage.hasModifier(attackDamageOriginiumStimulation))
                        attackDamage.removeModifier(attackDamageOriginiumStimulation);
                    if(attackInterval!=null && attackInterval.hasModifier(attackIntervalOriginiumStimulation))
                        attackInterval.removeModifier(attackIntervalOriginiumStimulation);
                }

                if(!state.getStates().contains(EnumState.ENERGETIC))
                {
                    if(attackDamage!=null && attackDamage.hasModifier(attackDamageEnergetic))
                        attackDamage.removeModifier(attackDamageEnergetic);
                }

                if(!state.getStates().contains(EnumState.SLEEP))
                {
                    if(moveSpeed!=null && moveSpeed.hasModifier(moveSpeedSleep))
                        moveSpeed.removeModifier(moveSpeedSleep);
                }

                if(!state.getStates().contains(EnumState.INSPIRE))
                {
                    if(moveSpeed!=null && moveSpeed.hasModifier(moveSpeedInspire))
                        moveSpeed.removeModifier(moveSpeedInspire);
                    if(attackDamage!=null && attackDamage.hasModifier(attackDamageInspire))
                        attackDamage.removeModifier(attackDamageInspire);
                    if(defensivePower!=null && defensivePower.hasModifier(defensivePowerInspire))
                        defensivePower.removeModifier(defensivePowerInspire);
                    if(attackInterval!=null && attackInterval.hasModifier(attackIntervalInspire))
                        attackInterval.removeModifier(attackIntervalInspire);
                }
            }
        }

        public void removeState(EnumState s)
        {
            if(hasCapability && !entity.world.isRemote)
            {
                state.getStates().removeIf(state -> state.equals(s));
            }
        }

        public void processOther()
        {
            if(hasCapability && !entity.world.isRemote)
            {
                if (state.getStates().contains(EnumState.FREEZE))
                    state.getStates().removeIf(state -> state.equals(EnumState.COLD));

                tacticalOrderFunction();

                onD12();
            }
        }

        public void processMain()
        {
            if(hasCapability && !entity.world.isRemote)
            {
                List<EnumState> states = state.getStates();
                List<EnumState> statesDuplicate = ListUtil.getDuplicate(states);

                for(EnumState stater : states)
                {
                    stater.tickTok();

                    if(stater.getTick()<=0 || statesDuplicate.contains(stater) || stater.getLevel()<=10)
                    {
                        if(statesDuplicate.contains(EnumState.COLD))
                        {
                            addFunctionOnlyTick(EnumState.FREEZE,80);
                        }
                        states.remove(stater);
                        break;
                    }
                }
            }
        }

        public void sendPacketToClient()
        {
            if (!entity.world.isRemote&&hasCapability)
            {
                PacketState message = new PacketState();
                message.id = entity.getEntityId();
                message.ids = state.getIds();
                Arkworld.getNetwork().sendToDimension(message, entity.dimension);
            }
        }

        public void processPerTick()
        {
            if(hasCapability && !entity.world.isRemote)
            {
                if (state.getStates().contains(EnumState.BURNING_BREATH))
                {
                    entity.attackEntityFrom(DamageSource.GENERIC,0.05f);
                    entity.setFire(1);
                }

                if(state.getStates().contains(EnumState.WE_UNITE))
                    entity.heal(0.5f);

                if(state.getStates().contains(EnumState.ORIGINIUM_STIMULATION) && EntityUtil.atSetIntervals(entity,20,0,0))
                    entity.attackEntityFrom(DamageSource.GENERIC, 4);

                entity.setInvisible(state.getStates().contains(EnumState.HIDE) || state.getStates().contains(EnumState.CAMOUFLAGE));
            }

            if(hasCapability && state.getStates().contains(EnumState.HIDE))
                ParticleList.invisibleParticle(entity);

            if(hasCapability && state.getStates().contains(EnumState.CAMOUFLAGE))
                ParticleList.camouflageParticle(entity);
        }

        private void tacticalOrderModifier()
        {
            if(attackDamage.hasModifier(attackDamageTacticalOrder))
            {
                attackDamage.removeModifier(attackDamageTacticalOrder);
            }
            attackDamage.applyModifier(attackDamageTacticalOrder);


            if(defensivePower.hasModifier(defensivePowerTacticalOrder))
            {
                defensivePower.removeModifier(defensivePowerTacticalOrder);
            }
            defensivePower.applyModifier(defensivePowerTacticalOrder);
        }

        private void tacticalOrderFunction()
        {
            if(tacticalOrderLevel >0 && entity instanceof ArkMob && ((ArkMob) entity).getCamp() == EnumCamps.UNION)
            {
                if(state.getStates().contains(EnumState.TACTICAL_ORDER))
                {
                    for(EnumState stater : state.getStates())
                    {
                        if(stater.equals(EnumState.TACTICAL_ORDER))
                        {
                            stater.setLevel(tacticalOrderLevel +10);
                        }
                    }

                    tacticalOrderModifier();
                }
                else
                {
                    addFunctionOnlyLevel(EnumState.TACTICAL_ORDER, tacticalOrderLevel +10);

                    tacticalOrderModifier();
                }
            }

            if(tacticalOrderLevel ==0 && state.getStates().contains(EnumState.TACTICAL_ORDER))
            {
                state.getStates().removeIf(next -> next.equals(EnumState.TACTICAL_ORDER));

                tacticalOrderModifier();
            }
        }

        public void onD12()
        {
            if(hasCapability && state.getStates().contains(EnumState.D12) && getTick(EnumState.D12)==1)
            {
                entity.playSound(SoundRegistry.W_EXPLODE,1,1);
                entity.world.createExplosion(null,entity.posX,entity.posY,entity.posZ,getLevel(EnumState.D12)/7f,false);
            }
        }

        public List<EnumState> getStates()
        {
            if(hasCapability)
                return state.states;
            else
                return new ArrayList<>();
        }

        public static void onCapAttack(LivingHurtEvent event)
        {
            Entity source = event.getSource().getTrueSource();
            EntityLivingBase entity = event.getEntityLiving();

            if (source instanceof EntityLivingBase && source.hasCapability(CapabilityRegistry.capState, null))
            {
                CapabilityState stateSource = source.getCapability(CapabilityRegistry.capState, null);
                event.setCanceled(stateSource.getStates().contains(EnumState.VERTIGO));
            }

            if (source instanceof EntityLivingBase && entity.hasCapability(CapabilityRegistry.capState, null))
            {
                CapabilityState stateEntity = source.getCapability(CapabilityRegistry.capState, null);
                event.setCanceled(source.hasCapability(CapabilityRegistry.capState, null) && (stateEntity.getStates().contains(EnumState.INVINCIBLE) || stateEntity.getStates().contains(EnumState.HIDE)));
            }

            if (source instanceof EntityLivingBase && (source.hasCapability(CapabilityRegistry.capState, null) || entity.hasCapability(CapabilityRegistry.capState, null)))
            {
                CapabilityState stateSource = source.getCapability(CapabilityRegistry.capState, null);
                CapabilityState stateEntity = source.getCapability(CapabilityRegistry.capState, null);
                event.setCanceled(stateSource.getStates().contains(EnumState.SLEEP) || stateEntity.getStates().contains(EnumState.SLEEP));
            }

            if (entity != null && entity.hasCapability(CapabilityRegistry.capState, null))
            {
                CapabilityState.Process state = new Process(entity);

                if (state.getStates().contains(EnumState.SHELTER) && state.getLevel( EnumState.SHELTER) > 0)
                    event.setAmount(event.getAmount() * (1 - state.getLevel(EnumState.SHELTER) / 10f));
            }
        }

        public static void banHealing(LivingHealEvent event)
        {
            EntityLivingBase entity = event.getEntityLiving();
            if(entity.hasCapability(CapabilityRegistry.capState,null))
                event.setCanceled(entity.getCapability(CapabilityRegistry.capState,null).getStates().contains(EnumState.BANEAL));
        }
    }
}
