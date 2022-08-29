package com.weedycow.arkworld.entity.operator.guard;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.machine.infrastructure.BlockTrainingRoom;
import com.weedycow.arkworld.capability.CapabilityOperatorCard;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.entity.operator.Skill;
import com.weedycow.arkworld.item.operator.SurtrCard;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.arkworld.util.ArkEntityUtil;
import com.weedycow.arkworld.util.ArkItemUtil;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.WLM;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.util.EntityUtil;
import com.weedycow.weedylib.util.MeleeRangeUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.*;

public class Surtr extends Operator
{
    public SkillI skillI = new SkillI();
    public SkillII skillII = new SkillII();
    public SkillIII skillIII = new SkillIII();
    public boolean critical;
    public float criticalValue = 1;
    public boolean dusk;
    public boolean invincible;
    float damageDusk = 0.003f;
    public static final String ID = "surtr";
    public static final String NAME = Arkworld.MODID + ".surtr";

    public Surtr(World world)
    {
        super(world,ID,NAME,Class.GUARD,Rarity.VI,Gender.WOMAN,Access.HEADHUNT);
        this.setSize(0.8f, 2.3f);
        this.setRedeployTime(1400);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(operator.guard.surtr.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(operator.guard.surtr.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(operator.guard.surtr.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(operator.guard.surtr.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(operator.guard.surtr.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(operator.guard.surtr.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(operator.guard.surtr.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(operator.guard.surtr.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(), (int) (16/controllerAttack.getAnimationSpeed()),0) && getSkillTime()>0 && getSelectSkill()==3 && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.surtr.skill3", false));
            controllerAttack.markNeedsReload();
        }
        else if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(), (int) (20/controllerAttack.getAnimationSpeed()),0) && getSkillTime()>0 && getSelectSkill()==2 && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.surtr.skill2", false));
            controllerAttack.markNeedsReload();
        }
        else if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(), (int) (20/controllerAttack.getAnimationSpeed()),0) && getAttackState() && !((getSelectSkill()==2 || getSelectSkill()==3) && getSkillTime()>0))
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.surtr.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.surtr.move", true));
        }
        else
        {
            if(getSkillTime()>0 && getSelectSkill()==3)
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.surtr.dusk_idle", true));
            else
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.surtr.idle", true));
        }
        return PlayState.CONTINUE;
    }

    AnimationController<Surtr> controllerAttack = new AnimationController<>(this,"controllerAttack",5,this::PlayState);
    AnimationController<Surtr> controllerMove = new AnimationController<>(this,"controllerMove",5,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        controllerAttack.setAnimationSpeed((float)operator.guard.surtr.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/operator.guard.surtr.movementSpeed);

        if(world.isRemote)
        {
            setGiftFir(I18n.format("gui.arkworld.operator_surtrg1")+getIgnoreSpellResistance());

            setGiftSec(I18n.format("gui.arkworld.operator_surtrg2")+getInvincibleTime()/20+I18n.format("gui.arkworld.operator_surtrg2p"));

            if (getElite() >= 2)
                setLogistics(I18n.format("gui.arkworld.operator_surtrl2"));
            else
                setLogistics(I18n.format("gui.arkworld.operator_surtrl1"));
        }
        else
        {
            setGiftFir("");
            setGiftSec("");
            setLogistics("");
        }

        getSkills().get(getSelectSkill()-1).skillFunctionPerTick();

        if(getTargetState())
        {
            setMS(0.1f);
            applyMoveSpeedModifier();
        }
        else
        {
            setMS(0);
            removeMoveSpeedModifier();
        }

        if(getCountdown()==1 && isInvincible())
        {
            this.onDeath(DamageSource.GENERIC);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        compound.setFloat("CriticalValue", getCriticalValue());

        compound.setBoolean("Critical", isCritical());

        compound.setBoolean("Dusk", isDusk());

        compound.setBoolean("Invisible", isInvincible());

        compound.setFloat("DamageDark", getDamageDusk());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        setCriticalValue(compound.getFloat("CriticalValue"));

        setDamageDusk(compound.getFloat("DamageDark"));

        setCritical(compound.getBoolean("Critical"));

        setDusk(compound.getBoolean("Dusk"));

        setInvincible(compound.getBoolean("Invisible"));
    }

    @Override
    public void closeExecute(Operator attacker, EntityLivingBase target)
    {
        if(EntityUtil.atSetIntervals(TICK,attacker, getAttackInterval(), 0, 0) && !((getSelectSkill()==2 || getSelectSkill()==3) && getSkillTime()>0))
        {
            if(isCritical())
            {
                EntityUtil.attackMelee(attacker, getAttackRange(), getAttackDamage() * getCriticalValue(), DamageSource.causeIndirectMagicDamage(this, this));
                setCritical(false);
            }
            else
                EntityUtil.attackMelee(attacker, getAttackRange(), getAttackDamage(), DamageSource.causeIndirectMagicDamage(this, this));

            playSound(SoundRegistry.SURTR_ATTACK,1,1);
        }
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        if(getSelectSkill()==3 && getSkillTime()>0)
            return ArkResUtil.geo(id+"_dusk");
        else
            return ArkResUtil.geo(id);
    }

    public float getIgnoreSpellResistance()
    {
        if(getElite()==1)
        {
            if(getPotential() < 5) return 0.12f;
            else return 0.14f;
        }

        if(getElite()==2)
        {
            if(getPotential() < 5) return 0.2f;
            else return 0.22f;
        }

        return 0;
    }

    public int getInvincibleTime()
    {
        if(getElite()==1)
        {
            if(getPotential() < 3) return 80;
            else return 100;
        }

        if(getElite()==2)
        {
            if(getPotential() < 3) return 160;
            else return 180;
        }

        return 0;
    }

    @Override
    public float getLogSkillRatio()
    {
        if(getRoom() instanceof BlockTrainingRoom.TileTrainingRoom &&
                ArkEntityUtil.getOperator(world,((BlockTrainingRoom.TileTrainingRoom) getRoom()).getCoach())==this
                && ArkEntityUtil.getOperator(world,((BlockTrainingRoom.TileTrainingRoom) getRoom()).getTrainer()) != null &&
                ArkEntityUtil.getOperator(world,((BlockTrainingRoom.TileTrainingRoom) getRoom()).getTrainer()).getOperatorClass()==Class.GUARD)
        {
            if (getElite() >= 2) return 0.6f;
            else return 0.3f;
        }
        return 0;
    }

    @Override
    public void playIdleSound()
    {
        int chance = new Random().nextInt(4);

        if(operator.guard.surtr.idleSound)
        {
            if (chance == 0)
                playSound(SoundRegistry.SURTR_IDLE_I, 1, 1);

            else if (chance == 1)
                playSound(SoundRegistry.SURTR_IDLE_II, 1, 1);

            else if (chance == 2)
                playSound(SoundRegistry.SURTR_IDLE_III, 1, 1);

            else
                playSound(SoundRegistry.SURTR_IDLE_IV, 1, 1);
        }
    }

    @Override
    public void playInteractiveSound()
    {
        int chance = new Random().nextInt(4);

        if(operator.guard.surtr.interactiveSound)
        {
            if (chance == 0)
                playSound(SoundRegistry.SURTR_INTERACT_I, 1, 1);

            else if (chance == 1)
                playSound(SoundRegistry.SURTR_INTERACT_II, 1, 1);

            else if (chance == 2)
                playSound(SoundRegistry.SURTR_INTERACT_III, 1, 1);

            else
                playSound(SoundRegistry.SURTR_INTERACT_IV, 1, 1);
        }
    }

    @Override
    public void playFightingSound()
    {
        int chance = new Random().nextInt(4);

        if(operator.guard.surtr.fightSound)
        {
            if (chance == 0)
                playSound(SoundRegistry.SURTR_FIGHT_I, 1, 1);

            else if (chance == 1)
                playSound(SoundRegistry.SURTR_FIGHT_II, 1, 1);

            else if (chance == 2)
                playSound(SoundRegistry.SURTR_FIGHT_III, 1, 1);

            else
                playSound(SoundRegistry.SURTR_FIGHT_IV, 1, 1);
        }
    }

    @Override
    public void attributeLevelUp()
    {
        if(getElite()==0)
            maxHealthModifiedLevel = new AttributeModifier(UUID.fromString("436248ed-9bcf-426b-a2b0-55e4b8045f92"),"Levelup max health",(getLevel()-1)*0.420,0);
        if(getElite()==1)
            maxHealthModifiedLevel = new AttributeModifier(UUID.fromString("436248ed-9bcf-426b-a2b0-55e4b8045f92"),"Levelup max health",(getLevel()-1)*0.291+21,0);
        if(getElite()==2)
            maxHealthModifiedLevel = new AttributeModifier(UUID.fromString("436248ed-9bcf-426b-a2b0-55e4b8045f92"),"Levelup max health",(getLevel()-1)*0.388+44.3,0);

        if(maxHealthModifiedLevel != null)
        {
            if(maxHealth.hasModifier(maxHealthModifiedLevel))
                maxHealth.removeModifier(maxHealthModifiedLevel);

            maxHealth.applyModifier(maxHealthModifiedLevel);

            setHealth(getMaxHealth());
        }

        if(getElite()==0)
            attackDamageModifiedLevel = new AttributeModifier(UUID.fromString("2944d37f-84c8-434f-93bb-9dd6702c1dae"),"Levelup attack damage",(getLevel()-1)*0.13+getTrust()*0.05,0);
        if(getElite()==1)
            attackDamageModifiedLevel = new AttributeModifier(UUID.fromString("2944d37f-84c8-434f-93bb-9dd6702c1dae"),"Levelup attack damage",(getLevel()-1)*0.078+6.5+getTrust()*0.05,0);
        if(getElite()==2)
            attackDamageModifiedLevel = new AttributeModifier(UUID.fromString("2944d37f-84c8-434f-93bb-9dd6702c1dae"),"Levelup attack damage",(getLevel()-1)*0.072+12.8+getTrust()*0.05,0);

        if(attackDamageModifiedLevel != null)
        {
            if(attackDamage.hasModifier(attackDamageModifiedLevel))
                attackDamage.removeModifier(attackDamageModifiedLevel);

            attackDamage.applyModifier(attackDamageModifiedLevel);
        }

        if(getElite()==0)
            defencePowerModifiedLevel = new AttributeModifier(UUID.fromString("f673dcd1-de86-4d7c-b398-d3e3821c7a02"),"Levelup defence power",(getLevel()-1)*0.081,0);
        if(getElite()==1)
            defencePowerModifiedLevel = new AttributeModifier(UUID.fromString("f673dcd1-de86-4d7c-b398-d3e3821c7a02"),"Levelup defence power",(getLevel()-1)*0.047+4.05,0);
        if(getElite()==2)
            defencePowerModifiedLevel = new AttributeModifier(UUID.fromString("f673dcd1-de86-4d7c-b398-d3e3821c7a02"),"Levelup defence power",(getLevel()-1)*0.039+7.85,0);

        if(defencePowerModifiedLevel != null)
        {
            if(defensivePower.hasModifier(defencePowerModifiedLevel))
                defensivePower.removeModifier(defencePowerModifiedLevel);

            defensivePower.applyModifier(defencePowerModifiedLevel);
        }

        if(getElite()==0)
            spellResistanceModifiedLevel = new AttributeModifier(UUID.fromString("3b2d2a38-4641-4bcd-943d-2adb11e1c8a5"),"Levelup spell resistance",0,0);
        if(getElite()==1)
            spellResistanceModifiedLevel = new AttributeModifier(UUID.fromString("3b2d2a38-4641-4bcd-943d-2adb11e1c8a5"),"Levelup spell resistance",0,0);
        if(getElite()==2)
            spellResistanceModifiedLevel = new AttributeModifier(UUID.fromString("3b2d2a38-4641-4bcd-943d-2adb11e1c8a5"),"Levelup spell resistance",(getLevel()-1)*0.055,0);

        if(spellResistanceModifiedLevel != null)
        {
            if(spellResistance.hasModifier(spellResistanceModifiedLevel))
                spellResistance.removeModifier(spellResistanceModifiedLevel);

            spellResistance.applyModifier(spellResistanceModifiedLevel);
        }
    }

    @Override
    public int getDeployPoint()
    {
        if(getPotential()>=6)
        {
            if(getElite()==2) return 19;
            else return 17;
        }
        else if(getPotential()>=2)
        {
            if(getElite()==2) return 20;
            else return 18;
        }
        else
        {
            if (getElite() == 2) return 21;
            else return 19;
        }
    }

    @Override
    public void potentialUpFunction()
    {
        if(getPotential()>=4)
        {
            AttributeModifier attackDamageModifiedPro = new AttributeModifier(UUID.fromString("e5966aa7-3d7c-4908-8a7d-a311e1bdeb23"),"Potential attack damage",1.4,0);

            if(attackDamage.hasModifier(attackDamageModifiedPro))
                attackDamage.removeModifier(attackDamageModifiedPro);

            attackDamage.applyModifier(attackDamageModifiedPro);
        }
    }

    @Override
    public Tag[] getTagsOperator()
    {
        return new Tag[]{Tag.DPS};
    }

    public boolean isCritical()
    {
        return critical;
    }

    public void setCriticalValue(float criticalValue)
    {
        this.criticalValue = criticalValue;
    }

    public void setCritical(boolean critical)
    {
        this.critical = critical;
    }

    public float getCriticalValue()
    {
        return criticalValue;
    }

    public void setInvincible(boolean invincible)
    {
        this.invincible = invincible;
    }

    public boolean isInvincible()
    {
        return invincible;
    }

    public void setDusk(boolean dusk)
    {
        this.dusk = dusk;
    }

    public boolean isDusk()
    {
        return dusk;
    }

    public void setDamageDusk(float damageDusk)
    {
        this.damageDusk = damageDusk;
    }

    public float getDamageDusk()
    {
        return damageDusk;
    }

    public static void onDeath(LivingDeathEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();

        if(entity instanceof Surtr)
        {
            if(((Surtr) entity).isDusk() && !((Surtr) entity).isInvincible())
            {
                event.setCanceled(true);
                ((Surtr) entity).setInvincible(true);
                entity.setHealth(1);
                ((Surtr) entity).setCountdown(((Surtr) entity).getInvincibleTime()+1);
            }
            
            if(!((Surtr) entity).isDusk() || ((Surtr) entity).isInvincible())
            {
                Surtr surtr = (Surtr) entity;

                if(surtr.getMaster()!=null)
                {
                    ItemStack stack = ArkItemUtil.findItemStackInPlayerSlot(surtr.getMaster(), SurtrCard.class);

                    if(stack != ItemStack.EMPTY && stack.getItem() instanceof SurtrCard)
                    {
                        if(stack.hasCapability(CapabilityRegistry.capOperatorCard,null))
                        {
                            CapabilityOperatorCard card = stack.getCapability(CapabilityRegistry.capOperatorCard, null);

                            card.setLevel(surtr.getLevel());

                            card.setExp(surtr.getExp());

                            card.setMoveMode(surtr.getMoveMode().getValue());

                            card.setActionMode(surtr.getActionMode().getValue());

                            card.setTrust(surtr.getTrust());

                            card.setSkillLevel(surtr.getSkillLevel());

                            card.setSkillFirRank(surtr.getSkillFirRank());

                            card.setSkillSecRank(surtr.getSkillSecRank());

                            card.setSkillThiRank(surtr.getSkillThiRank());

                            card.setSelectSkill(surtr.getSelectSkill());

                            card.setElite(surtr.getElite());

                            card.setPotential(surtr.getPotential());

                            card.setCountdown(surtr.getRedeployTime());

                            card.setTimes(surtr.getTimes());

                            card.setExistedTime(surtr.getExistedTime());

                            card.setMood(surtr.getMood());

                            card.setTrainTime(surtr.getTrainTime());

                            if (surtr.getMaster() != null)
                                card.setMaster(surtr.getMaster().getName());

                            card.setClear(false);
                        }
                    }
                }
            }
        }
    }

    public static void onHurt(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();

        if(entity instanceof Surtr)
        {
            if(((Surtr) entity).isDusk() && ((Surtr) entity).isInvincible() && ((Surtr) entity).getCountdown()>1)
            {
                event.setCanceled(true);
            }
        }

        DamageSource source = event.getSource();

        if(source.getTrueSource() instanceof Surtr && entity instanceof WLM)
        {
            WLM wlm = (WLM) entity;
            Surtr surtr = (Surtr) source.getTrueSource();

            if (wlm.getSpellResistance()>0)
            {
                if(wlm.getSpellResistance()<surtr.getIgnoreSpellResistance())
                {
                    event.setAmount(event.getAmount()*(1+surtr.getIgnoreSpellResistance()-wlm.getSpellResistance()));
                }
                else
                {
                    event.setAmount(event.getAmount()*(1+surtr.getIgnoreSpellResistance()));
                }
            }

        }
    }

    @Override
    public List<ItemStack> getEliteII()
    {
        return new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.GUARD_DUALCHIP, 4), new ItemStack(ItemRegistry.POLYMERIZATION_PREPARATION, 4), new ItemStack(ItemRegistry.KETON_COLLOID, 5)));
    }

    @Override
    public List<ItemStack> getEliteI()
    {
        return new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.GUARD_CHIP, 5), new ItemStack(ItemRegistry.ORIROCK_CUBE, 12), new ItemStack(ItemRegistry.POLYKETON, 4)));
    }

    @Override
    public List<List<ItemStack>> getSluMaterial()
    {
        List<List<ItemStack>> slu = new ArrayList<>();
        slu.add(0, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_I, 5), ItemStack.EMPTY, ItemStack.EMPTY)));
        slu.add(1, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_I, 5), new ItemStack(ItemRegistry.DAMAGED_DEVICE, 4), new ItemStack(ItemRegistry.ESTER, 4))));
        slu.add(2, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_II, 8), new ItemStack(ItemRegistry.ORIROCK_CUBE, 7), ItemStack.EMPTY)));
        slu.add(3, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_II, 8), new ItemStack(ItemRegistry.POLYESTER, 4), new ItemStack(ItemRegistry.ORIRON, 4))));
        slu.add(4, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_II, 8), new ItemStack(ItemRegistry.ORIRON_CLUSTER, 6), ItemStack.EMPTY)));
        slu.add(5, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III, 8), new ItemStack(ItemRegistry.LOXIC_KOHL, 5), new ItemStack(ItemRegistry.AKETON, 4))));
        return slu;
    }

    @Override
    public List<Skill> getSkills()
    {
        List<Skill> skills = new ArrayList<>();
        skills.add(0,skillI);
        skills.add(1,skillII);
        skills.add(2,skillIII);
        return skills;
    }

    public class SkillI extends Skill
    {
        public SkillI()
        {
            super(1, world.isRemote ? I18n.format("gui.arkworld.operator_surtrs1") : "", RecoveryType.ATTACK, TriggerType.AUTO, Surtr.this, new ArrayList<ArrayList<ItemStack>>()
            {
                {
                    this.add(0,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,8),new ItemStack(ItemRegistry.MANGANESE_TRIHYDRATE,4),new ItemStack(ItemRegistry.INTEGRATED_DEVICE,4))));
                    this.add(1,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,12),new ItemStack(ItemRegistry.KETON_COLLOID,4),new ItemStack(ItemRegistry.POLYMERIZATION_PREPARATION,9))));
                    this.add(2,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,15),new ItemStack(ItemRegistry.DSTEEL,6),new ItemStack(ItemRegistry.OPTIMIZED_DEVICE,4))));
                }
            });
        }

        @Override
        public void skillFunctionPerTick()
        {
            if(operator.getSkillLevel()<=3)
            {
                setInitPoint(0);
                setConsumePoint(4);
            }

            if(operator.getSkillLevel()>3 && operator.getSkillFirRank()<=2)
            {
                setInitPoint(0);
                setConsumePoint(3);
            }

            if(operator.getSkillFirRank()>2)
            {
                setInitPoint(0);
                setConsumePoint(2);
            }

            if(getSkillPoint()>=getConsumePoint()) skillFunctionInstance();
        }

        @Override
        public void skillFunctionInstance()
        {
            operator.setSkillPoint(0);

            setCritical(true);

            if(operator.getSkillLevel()==1) setCriticalValue(2);

            if(operator.getSkillLevel()==2) setCriticalValue(2.05f);

            if(operator.getSkillLevel()==3) setCriticalValue(2.10f);

            if(operator.getSkillLevel()==4) setCriticalValue(2.20f);

            if(operator.getSkillLevel()==5) setCriticalValue(2.25f);

            if(operator.getSkillLevel()==6) setCriticalValue(2.30f);

            if(operator.getSkillLevel()==7) setCriticalValue(2.40f);

            if(operator.getSkillFirRank()==1) setCriticalValue(2.60f);

            if(operator.getSkillFirRank()==2) setCriticalValue(2.80f);

            if(operator.getSkillFirRank()==3) setCriticalValue(3.10f);
        }

        public boolean isInstance()
        {
            return true;
        }
    }

    public class SkillII extends Skill
    {
        public SkillII()
        {
            super(2, world.isRemote ? I18n.format("gui.arkworld.operator_surtrs2") : "", RecoveryType.AUTO, TriggerType.MANUAL, Surtr.this, new ArrayList<ArrayList<ItemStack>>()
            {
                {
                    this.add(0,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,8),new ItemStack(ItemRegistry.RMAR,4),new ItemStack(ItemRegistry.MANGANESE_ORE,5))));
                    this.add(1,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,12),new ItemStack(ItemRegistry.WHITE_HORSE_KOHL,4),new ItemStack(ItemRegistry.KETON_COLLOID,8))));
                    this.add(2,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,15),new ItemStack(ItemRegistry.BIPOLAR_NANOFLAKE,6),new ItemStack(ItemRegistry.RMAR,5))));
                }
            });
        }

        @Override
        public void skillFunctionPerTick()
        {
            float atk = 1;
            float onlyOne = 1;

            if(operator.getSkillLevel()==1)
            {
                atk=1.5f;
                onlyOne=1.4f;
                setInitPoint(7);
                setConsumePoint(28);
                setTime(300);
            }

            if(operator.getSkillLevel()==2)
            {
                atk=1.55f;
                onlyOne=1.4f;
                setInitPoint(7);
                setConsumePoint(27);
                setTime(300);
            }

            if(operator.getSkillLevel()==3)
            {
                atk=1.6f;
                onlyOne=1.4f;
                setInitPoint(7);
                setConsumePoint(26);
                setTime(300);
            }

            if(operator.getSkillLevel()==4)
            {
                atk=1.65f;
                onlyOne=1.4f;
                setInitPoint(8);
                setConsumePoint(25);
                setTime(320);
            }

            if(operator.getSkillLevel()==5)
            {
                atk=1.7f;
                onlyOne=1.4f;
                setInitPoint(8);
                setConsumePoint(24);
                setTime(320);
            }

            if(operator.getSkillLevel()==6)
            {
                atk=1.75f;
                onlyOne=1.4f;
                setInitPoint(8);
                setConsumePoint(23);
                setTime(320);
            }

            if(operator.getSkillLevel()==7)
            {
                atk=1.8f;
                onlyOne=1.5f;
                setInitPoint(9);
                setConsumePoint(22);
                setTime(340);
            }

            if(operator.getSkillSecRank()==1)
            {
                atk=1.9f;
                onlyOne=1.5f;
                setInitPoint(10);
                setConsumePoint(21);
                setTime(340);
            }

            if(operator.getSkillSecRank()==2)
            {
                atk=2f;
                onlyOne=1.5f;
                setInitPoint(11);
                setConsumePoint(20);
                setTime(340);
            }

            if(operator.getSkillSecRank()==3)
            {
                atk=2.2f;
                onlyOne=1.6f;
                setInitPoint(12);
                setConsumePoint(18);
                setTime(360);
            }

            if(operator.getSkillStartup()>0 && operator.getSkillPoint()>=getConsumePoint())
            {
                operator.setSkillTime(getTime());
                operator.setSkillPoint(0);
                operator.addAR(1);
                operator.applyAttackRangeModifier();
            }

            if(getSkillTime()==0)
            {
                operator.removeAttackRangeModifier();
            }

            if(operator.getSkillTime()>0 && EntityUtil.atSetIntervals(TICK,operator,getAttackInterval(),0,0))
            {
                if (world.getEntitiesWithinAABB(EntityLivingBase.class, new MeleeRangeUtil(operator, operator.getAttackRange() + 1)).size() >= 2)
                {
                    for (EntityLivingBase target : world.getEntitiesWithinAABB(EntityLivingBase.class, new MeleeRangeUtil(operator, operator.getAttackRange())))
                    {
                        if(target!=null && target!=operator)
                            target.attackEntityFrom(DamageSource.causeMobDamage(operator), operator.getAttackDamage() * atk);
                    }
                }
                else
                {
                    if (EntityUtil.atSetIntervals(TICK, operator, getAttackInterval(), 0, 0))
                    {
                        EntityUtil.attackMelee(operator, getAttackRange() + 1, getAttackDamage() * atk * onlyOne, DamageSource.causeIndirectMagicDamage(operator, null));
                    }
                }
            }
        }
    }

    public class SkillIII extends Skill
    {
        public SkillIII()
        {
            super(3, world.isRemote ? I18n.format("gui.arkworld.operator_surtrs3") : "", RecoveryType.AUTO, TriggerType.MANUAL, Surtr.this, new ArrayList<ArrayList<ItemStack>>()
            {
                {
                    this.add(0,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,8),new ItemStack(ItemRegistry.ORIROCK_CONCENTRATION,4),new ItemStack(ItemRegistry.GRINDSTONE,7))));
                    this.add(1,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,12),new ItemStack(ItemRegistry.GRINDSTONE_PENTAHYDRATE,4),new ItemStack(ItemRegistry.WHITE_HORSE_KOHL,9))));
                    this.add(2,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,15),new ItemStack(ItemRegistry.DSTEEL,6),new ItemStack(ItemRegistry.POLYMERIZED_GEL,6))));
                }
            });
        }

        @Override
        public void skillFunctionPerTick()
        {
            float atk = 1;

            if (operator.getSkillLevel() == 1)
            {
                atk = 2.8f;
                setConsumePoint(10);
            }

            if (operator.getSkillLevel() == 2)
            {
                atk = 2.9f;
                setConsumePoint(10);
            }

            if (operator.getSkillLevel() == 3)
            {
                atk = 3f;
                setConsumePoint(10);
            }

            if (operator.getSkillLevel() == 4)
            {
                atk = 3.1f;
                setConsumePoint(9);
            }

            if (operator.getSkillLevel() == 5)
            {
                atk = 3.2f;
                setConsumePoint(9);
            }

            if (operator.getSkillLevel() == 6)
            {
                atk = 3.3f;
                setConsumePoint(9);
            }

            if (operator.getSkillLevel() == 7)
            {
                atk = 3.4f;
                setConsumePoint(8);
            }

            if (operator.getSkillThiRank() == 1)
            {
                atk = 3.7f;
                setConsumePoint(7);
            }

            if (operator.getSkillThiRank() == 2)
            {
                atk = 4f;
                setConsumePoint(6);
            }

            if (operator.getSkillThiRank() == 3)
            {
                atk = 4.3f;
                setConsumePoint(5);
            }

            setTime(6000);

            if(operator.getSkillStartup()>0 && operator.getSkillPoint()>=getConsumePoint())
            {
                setDusk(true);

                addHP(250);

                applyMaxHealthModifier();

                setHealth(getMaxHealth());

                operator.setSkillTime(getTime());

                operator.setSkillPoint(0);

                playSound(SoundRegistry.SURTR_SKILL_III_START,1,1);

                operator.addAR(2);

                operator.applyAttackRangeModifier();
            }

            if (isDusk())
            {
                if(EntityUtil.atSetIntervals(TICK,operator,operator.getAttackInterval(),0,0) && getAttackState())
                {
                    List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new MeleeRangeUtil(operator, operator.getAttackRange()));

                    entities.removeIf(entityLivingBase -> entityLivingBase == operator);

                    if (operator.getSkillThiRank() >= 3)
                    {
                        for (EntityLivingBase target : entities.size() > 3 ? entities.subList(0, 3) : entities)
                        {
                            if (target != null)
                                target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(operator, operator), operator.getAttackDamage() * atk);
                        }
                    }
                    else
                    {
                        for (EntityLivingBase target : entities.size() > 2 ? entities.subList(0, 2) : entities)
                        {
                            if (target != null)
                                target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(operator, operator), operator.getAttackDamage() * atk);
                        }
                    }

                    playSound(SoundRegistry.SURTR_SKILL_III_ATTACK,1,1);
                }

                if(EntityUtil.atSetIntervals(TICK,operator,20,0,0))
                {
                    damageDusk += damageDusk;

                    if(damageDusk >0.2) damageDusk =0.2f;

                    operator.attackEntityFrom(DamageSource.GENERIC, damageDusk *operator.getMaxHealth());
                }
            }

            if(!isEntityAlive() || getSkillTime()==0)
            {
                setDusk(false);

                setHP(0);

                removeMaxHealthModifier();

                damageDusk =0.003f;

                setSkillTime(0);

                setInvincible(false);

                removeAttackRangeModifier();
            }
        }
    }
}
