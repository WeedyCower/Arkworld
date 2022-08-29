package com.weedycow.arkworld.entity.operator.caster;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.machine.infrastructure.BlockMachine;
import com.weedycow.arkworld.capability.CapabilityOperatorCard;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.entity.operator.Skill;
import com.weedycow.arkworld.entity.weapon.SimpleThrowable;
import com.weedycow.arkworld.item.operator.AmiyaCard;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.arkworld.util.ArkItemUtil;
import com.weedycow.arkworld.util.ParticleList;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
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

public class Amiya extends Operator
{
    public SkillI skillI = new SkillI();
    public SkillII skillII = new SkillII();
    public SkillIII skillIII = new SkillIII();
    public static final String ID = "amiya";
    public static final String NAME = Arkworld.MODID + ".amiya";

    public Amiya(World world)
    {
        super(world,ID,NAME,Class.CASTER,Rarity.V,Gender.WOMAN,Access.OTHER);
        this.setSize(0.8f, 2.3f);
        this.setRedeployTime(1400);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(operator.caster.amiya.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(operator.caster.amiya.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(operator.caster.amiya.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(operator.caster.amiya.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(operator.caster.amiya.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(operator.caster.amiya.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(operator.caster.amiya.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(operator.caster.amiya.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(getSkillTime()==getSkills().get(getSelectSkill()-1).getTime()-1 && getSelectSkill()==3)
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.amiya.skill3", false));
            controllerAttack.markNeedsReload();
        }
        else if(getSkillTime()==getSkills().get(getSelectSkill()-1).getTime()-1 && getSelectSkill()==2)
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.amiya.skill2", false));
            controllerAttack.markNeedsReload();
        }
        else if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(), (int) (30/controllerAttack.getAnimationSpeed()),0) && getTargetState()&& !((getSelectSkill()==2 || getSelectSkill()==3) && getSkillTime()>0))
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.amiya.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.amiya.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.amiya.idle", true));

        return PlayState.CONTINUE;
    }

    AnimationController<Amiya> controllerAttack = new AnimationController<>(this,"controllerAttack",5,this::PlayState);
    AnimationController<Amiya> controllerMove = new AnimationController<>(this,"controllerMove",5,this::PlayState);

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

        controllerAttack.setAnimationSpeed((float)operator.caster.amiya.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/operator.caster.amiya.movementSpeed);

        if(world.isRemote)
        {
            if (getPotential() < 6)
                setGiftFir(I18n.format("gui.arkworld.operator_amiyag1"));
            else
                setGiftFir(I18n.format("gui.arkworld.operator_amiyag1p"));

            if (getElite() >= 2)
                setLogistics(I18n.format("gui.arkworld.operator_amiyal2"));
            else
                setLogistics(I18n.format("gui.arkworld.operator_amiyal1"));
        }
        else
        {
            setGiftFir("");
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
    }

    @Override
    public void commonExecute(Operator attacker, EntityLivingBase target)
    {
        SimpleThrowable throwable = new SimpleThrowable(world,attacker,getAttackDamage(), SimpleThrowable.Type.AMIYA_BALL);

        if(EntityUtil.atSetIntervals(TICK,attacker, getAttackInterval(), 0, 0) && !((getSelectSkill()==2 || getSelectSkill()==3) && getSkillTime()>0))
            EntityUtil.attackRange(attacker,throwable,null,getDistance()/5,1);
    }

    @Override
    public void potentialUpFunction()
    {
        if(getPotential()>=2)
        {
            AttributeModifier maxHealthModifiedPro = new AttributeModifier(UUID.fromString("fafd731f-c215-44c5-a96f-82a8d2c739a0"),"Potential max health",10,0);

            if(maxHealth.hasModifier(maxHealthModifiedPro))
                maxHealth.removeModifier(maxHealthModifiedPro);

            maxHealth.applyModifier(maxHealthModifiedPro);

            setHealth(getMaxHealth());
        }

        if(getPotential()>=4)
        {
            AttributeModifier attackDamageModifiedPro = new AttributeModifier(UUID.fromString("e5966aa7-3d7c-4908-8a7d-a311e1bdeb23"),"Potential attack damage",1.5,0);

            if(attackDamage.hasModifier(attackDamageModifiedPro))
                attackDamage.removeModifier(attackDamageModifiedPro);

            attackDamage.applyModifier(attackDamageModifiedPro);
        }
    }

    @Override
    public int getDeployPoint()
    {
        if(getPotential()>=5)
        {
            if(getElite()==2) return 18;
            else return 16;
        }
        else if(getPotential()>=3)
        {
            if(getElite()==2) return 19;
            else return 17;
        }
        else
        {
            if (getElite()==2) return 20;
            else return 18;
        }
    }

    public static void targetHurt(LivingHurtEvent event)
    {
        DamageSource source = event.getSource();

        if(source.getTrueSource() instanceof Amiya)
        {
            Amiya amiya = (Amiya) source.getTrueSource();

            if(amiya.getPotential() < 6)
                amiya.setSkillPoint(amiya.getSkillPoint()+2);
            else
                amiya.setSkillPoint(amiya.getSkillPoint()+3);
        }
    }

    public static void targetDeath(LivingDeathEvent event)
    {
        DamageSource source = event.getSource();

        if(source.getTrueSource() instanceof Amiya)
        {
            Amiya amiya = (Amiya) source.getTrueSource();

            if(amiya.getPotential() < 6)
                amiya.setSkillPoint(amiya.getSkillPoint()+8);
            else
                amiya.setSkillPoint(amiya.getSkillPoint()+10);
        }
    }

    @Override
    public void playIdleSound()
    {
        int chance = new Random().nextInt(4);

        if(operator.caster.amiya.idleSound)
        {
            if (chance == 0)
                playSound(SoundRegistry.AMIYA_IDLE_I, 1, 1);

            else if (chance == 1)
                playSound(SoundRegistry.AMIYA_IDLE_II, 1, 1);

            else if (chance == 2)
                playSound(SoundRegistry.AMIYA_IDLE_III, 1, 1);

            else
                playSound(SoundRegistry.AMIYA_IDLE_IV, 1, 1);
        }
    }

    @Override
    public void playInteractiveSound()
    {
        int chance = new Random().nextInt(4);

        if(operator.caster.amiya.interactiveSound)
        {
            if (chance == 0)
                playSound(SoundRegistry.AMIYA_INTERACT_I, 1, 1);

            else if (chance == 1)
                playSound(SoundRegistry.AMIYA_INTERACT_II, 1, 1);

            else if (chance == 2)
                playSound(SoundRegistry.AMIYA_INTERACT_III, 1, 1);

            else
                playSound(SoundRegistry.AMIYA_INTERACT_IV, 1, 1);
        }
    }

    @Override
    public void playFightingSound()
    {
        int chance = new Random().nextInt(4);

        if(operator.caster.amiya.fightSound)
        {
            if (chance == 0)
                playSound(SoundRegistry.AMIYA_FIGHT_I, 1, 1);

            else if (chance == 1)
                playSound(SoundRegistry.AMIYA_FIGHT_II, 1, 1);

            else if (chance == 2)
                playSound(SoundRegistry.AMIYA_FIGHT_III, 1, 1);

            else
                playSound(SoundRegistry.AMIYA_FIGHT_IV, 1, 1);
        }
    }

    public float getLogSkillRatio()
    {
        if(getElite()==2)
        {
            if (getRoomType() == BlockMachine.Type.DORMITORY)
                return 0.15f;
            else
                return 0;
        }

        if (getRoomType() == BlockMachine.Type.CONTROL_CENTER)
            return 0.07f;
        else
            return 0;
    }

    @Override
    public void attributeLevelUp()
    {
        if(getElite()==0)
            maxHealthModifiedLevel = new AttributeModifier(UUID.fromString("436248ed-9bcf-426b-a2b0-55e4b8045f92"),"Levelup max health",(getLevel()-1)*0.265+getTrust()*0.1,0);
        if(getElite()==1)
            maxHealthModifiedLevel = new AttributeModifier(UUID.fromString("436248ed-9bcf-426b-a2b0-55e4b8045f92"),"Levelup max health",(getLevel()-1)*0.173+13+getTrust()*0.1,0);
        if(getElite()==2)
            maxHealthModifiedLevel = new AttributeModifier(UUID.fromString("436248ed-9bcf-426b-a2b0-55e4b8045f92"),"Levelup max health",(getLevel()-1)*0.177+25+getTrust()*0.1,0);

        if(maxHealthModifiedLevel != null)
        {
            if(maxHealth.hasModifier(maxHealthModifiedLevel))
                maxHealth.removeModifier(maxHealthModifiedLevel);

            maxHealth.applyModifier(maxHealthModifiedLevel);

            setHealth(getMaxHealth());
        }

        if(getElite()==0)
            attackDamageModifiedLevel = new AttributeModifier(UUID.fromString("2944d37f-84c8-434f-93bb-9dd6702c1dae"),"Levelup attack damage",(getLevel()-1)*0.114+getTrust()*0.035,0);
        if(getElite()==1)
            attackDamageModifiedLevel = new AttributeModifier(UUID.fromString("2944d37f-84c8-434f-93bb-9dd6702c1dae"),"Levelup attack damage",(getLevel()-1)*0.088+5.7+getTrust()*0.035,0);
        if(getElite()==2)
            attackDamageModifiedLevel = new AttributeModifier(UUID.fromString("2944d37f-84c8-434f-93bb-9dd6702c1dae"),"Levelup attack damage",(getLevel()-1)*0.061+11.9+getTrust()*0.035,0);

        if(attackDamageModifiedLevel != null)
        {
            if(attackDamage.hasModifier(attackDamageModifiedLevel))
                attackDamage.removeModifier(attackDamageModifiedLevel);

            attackDamage.applyModifier(attackDamageModifiedLevel);
        }

        if(getElite()==0)
            defencePowerModifiedLevel = new AttributeModifier(UUID.fromString("f673dcd1-de86-4d7c-b398-d3e3821c7a02"),"Levelup defence power",(getLevel()-1)*0.033,0);
        if(getElite()==1)
            defencePowerModifiedLevel = new AttributeModifier(UUID.fromString("f673dcd1-de86-4d7c-b398-d3e3821c7a02"),"Levelup defence power",(getLevel()-1)*0.020+1.65,0);
        if(getElite()==2)
            defencePowerModifiedLevel = new AttributeModifier(UUID.fromString("f673dcd1-de86-4d7c-b398-d3e3821c7a02"),"Levelup defence power",(getLevel()-1)*0.007+3.1,0);

        if(defencePowerModifiedLevel != null)
        {
            if(defensivePower.hasModifier(defencePowerModifiedLevel))
                defensivePower.removeModifier(defencePowerModifiedLevel);

            defensivePower.applyModifier(defencePowerModifiedLevel);
        }

        if(getElite()==0)
            spellResistanceModifiedLevel = new AttributeModifier(UUID.fromString("3b2d2a38-4641-4bcd-943d-2adb11e1c8a5"),"Levelup spell resistance",0,0);
        if(getElite()==1)
            spellResistanceModifiedLevel = new AttributeModifier(UUID.fromString("3b2d2a38-4641-4bcd-943d-2adb11e1c8a5"),"Levelup spell resistance",(getLevel()-1)*0.0007,0);
        if(getElite()==2)
            spellResistanceModifiedLevel = new AttributeModifier(UUID.fromString("3b2d2a38-4641-4bcd-943d-2adb11e1c8a5"),"Levelup spell resistance",(getLevel()-1)*0.0006+0.05,0);

        if(spellResistanceModifiedLevel != null)
        {
            if(spellResistance.hasModifier(spellResistanceModifiedLevel))
                spellResistance.removeModifier(spellResistanceModifiedLevel);

            spellResistance.applyModifier(spellResistanceModifiedLevel);
        }
    }

    @Override
    public Tag[] getTagsOperator()
    {
        return new Tag[]{Tag.DPS};
    }

    @Override
    public List<ItemStack> getEliteI()
    {
        return new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.CASTER_CHIP, 3), new ItemStack(ItemRegistry.DEVICE, 4), new ItemStack(ItemRegistry.ORIRON, 4)));
    }

    @Override
    public List<ItemStack> getEliteII()
    {
        return new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.CASTER_DUALCHIP, 3), new ItemStack(ItemRegistry.ORIROCK_CONCENTRATION, 10), new ItemStack(ItemRegistry.LOXIC_KOHL, 10)));
    }

    @Override
    public List<List<ItemStack>> getSluMaterial()
    {
        List<List<ItemStack>> slu = new ArrayList<>();
        slu.add(0, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_I, 4), ItemStack.EMPTY, ItemStack.EMPTY)));
        slu.add(1, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_I, 4), new ItemStack(ItemRegistry.DAMAGED_DEVICE, 4), ItemStack.EMPTY)));
        slu.add(2, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_II, 6), new ItemStack(ItemRegistry.ORIROCK_CUBE, 4), ItemStack.EMPTY)));
        slu.add(3, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_II, 6), new ItemStack(ItemRegistry.SUGAR, 5), ItemStack.EMPTY)));
        slu.add(4, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_II, 6), new ItemStack(ItemRegistry.AKETON, 4), ItemStack.EMPTY)));
        slu.add(5, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III, 6), new ItemStack(ItemRegistry.INTEGRATED_DEVICE, 2), new ItemStack(ItemRegistry.SUGAR_PACK, 3))));
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

    public static void onDeath(LivingDeathEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();

        if(entity instanceof Amiya)
        {
            Amiya amiya = (Amiya) entity;

            if(amiya.getMaster()!=null)
            {
                ItemStack stack = ArkItemUtil.findItemStackInPlayerSlot(amiya.getMaster(), AmiyaCard.class);

                if(stack != ItemStack.EMPTY && stack.getItem() instanceof AmiyaCard)
                {
                    if(stack.hasCapability(CapabilityRegistry.capOperatorCard,null))
                    {
                        CapabilityOperatorCard card = stack.getCapability(CapabilityRegistry.capOperatorCard, null);

                        card.setLevel(amiya.getLevel());

                        card.setExp(amiya.getExp());

                        card.setMoveMode(amiya.getMoveMode().getValue());

                        card.setActionMode(amiya.getActionMode().getValue());

                        card.setTrust(amiya.getTrust());

                        card.setSkillLevel(amiya.getSkillLevel());

                        card.setSkillFirRank(amiya.getSkillFirRank());

                        card.setSkillSecRank(amiya.getSkillSecRank());

                        card.setSkillThiRank(amiya.getSkillThiRank());

                        card.setSelectSkill(amiya.getSelectSkill());

                        card.setElite(amiya.getElite());

                        card.setPotential(amiya.getPotential());

                        card.setCountdown(amiya.getRedeployTime());

                        card.setTimes(amiya.getTimes());

                        card.setExistedTime(amiya.getExistedTime());

                        card.setMood(amiya.getMood());

                        card.setTrainTime(amiya.getTrainTime());

                        if (amiya.getMaster() != null)
                            card.setMaster(amiya.getMaster().getName());

                        card.setClear(false);
                    }
                }
            }
        }
    }

    public class SkillI extends Skill
    {
        public SkillI()
        {
            super(1, world.isRemote ? I18n.format("gui.arkworld.operator_amiyas1") : "", RecoveryType.AUTO, TriggerType.MANUAL, Amiya.this, new ArrayList<ArrayList<ItemStack>>()
            {
                {
                    this.add(0,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,5),new ItemStack(ItemRegistry.WHITE_HORSE_KOHL,3),new ItemStack(ItemRegistry.AKETON,5))));
                    this.add(1,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,6),new ItemStack(ItemRegistry.GRINDSTONE_PENTAHYDRATE,3),new ItemStack(ItemRegistry.WHITE_HORSE_KOHL,6))));
                    this.add(2,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,10),new ItemStack(ItemRegistry.DSTEEL,4),new ItemStack(ItemRegistry.ORIROCK_CONCENTRATION,5))));
                }
            });
        }

        @Override
        public void skillFunctionPerTick()
        {
            if(operator.getSkillLevel()<=3)
            {
                setInitPoint(0);
                setConsumePoint(40);
            }

            if(operator.getSkillLevel()>3 && operator.getSkillLevel()<=6)
            {
                setInitPoint(5);
                setConsumePoint(35);
            }

            if(operator.getSkillLevel()>6 && operator.getSkillFirRank()<=2)
            {
                setInitPoint(10);
                setConsumePoint(32);
            }

            if(operator.getSkillFirRank()>2)
            {
                setInitPoint(15);
                setConsumePoint(30);
            }

            if(operator.getSkillStartup()>0 && operator.getSkillPoint()>=getConsumePoint())
            {
                operator.setSkillTime(getTime());
                operator.setSkillPoint(0);
                operator.setAI(-0.18f-(operator.getSkillLevel()-1)*0.03f-operator.getSkillFirRank()*0.06f);
                operator.applyAttackIntervalModifier();

                if(getSelectSkill()==2)
                {
                    playSound(SoundRegistry.AMIYA_SKILL_II,1,1);
                }
                else
                    playSound(SoundRegistry.OPERATOR_SKILL_START,1,1);
            }

            if(operator.getSkillTime()>0 && EntityUtil.atSetIntervals(TICK,operator,5,0,0))
            {
                ParticleList.attackSpeedParticle(operator);
            }

            if(operator.getSkillTime()==1)
            {
                operator.removeAttackIntervalModifier();
                operator.setAI(0);
            }

            setTime(600);
        }
    }

    public class SkillII extends Skill
    {
        public SkillII()
        {
            super(2, world.isRemote ? I18n.format("gui.arkworld.operator_amiyas2") : "", RecoveryType.AUTO, TriggerType.AUTO, Amiya.this, new ArrayList<ArrayList<ItemStack>>()
            {
                {
                    this.add(0,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,5),new ItemStack(ItemRegistry.MANGANESE_TRIHYDRATE,3),new ItemStack(ItemRegistry.INTEGRATED_DEVICE,2))));
                    this.add(1,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,6),new ItemStack(ItemRegistry.RMAR,3),new ItemStack(ItemRegistry.MANGANESE_TRIHYDRATE,5))));
                    this.add(2,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,10),new ItemStack(ItemRegistry.POLYMERIZATION_PREPARATION,4),new ItemStack(ItemRegistry.WHITE_HORSE_KOHL,5))));
                }
            });
        }

        @Override
        public void skillFunctionPerTick()
        {
            float atk;
            int nb;

            if(operator.getSkillSecRank()<=0)
            {
                atk=-0.69f+operator.getSkillLevel()*0.02f;

                if(operator.getSkillLevel()<=3)
                    nb=6;
                else
                    nb=7;
            }
            else
            {
                atk=-0.55f+operator.getSkillSecRank()*0.05f;

                nb=8;
            }

            setInitPoint(0);

            setConsumePoint(100);

            setTime(500);

            if(operator.getSkillPoint()>=getConsumePoint())
            {
                operator.setSkillTime(getTime());
                operator.setSkillPoint(0);
                operator.setAD(atk);
                operator.applyAttackDamageModifier();
            }

            if(operator.getSelectSkill()==2 && operator.getSkillTime()>0)
            {
                ParticleList.amiyaParticle(operator);

                SimpleThrowable throwable = new SimpleThrowable(world,operator,getAttackDamage(), SimpleThrowable.Type.AMIYA_BALL);

                if(EntityUtil.atSetIntervals(TICK,operator, operator.getAttackInterval(), 0, 0)) setCountdown(nb*4);

                if(getCountdown()==32)
                    EntityUtil.attackRange(operator,throwable,null,getDistance()/5,1);

                if(getCountdown()==28)
                    EntityUtil.attackRange(operator,throwable,null,getDistance()/5,1);

                if(getCountdown()==24)
                    EntityUtil.attackRange(operator,throwable,null,getDistance()/5,1);

                if(getCountdown()==20)
                    EntityUtil.attackRange(operator,throwable,null,getDistance()/5,1);

                if(getCountdown()==16)
                    EntityUtil.attackRange(operator,throwable,null,getDistance()/5,1);

                if(getCountdown()==12)
                    EntityUtil.attackRange(operator,throwable,null,getDistance()/5,1);

                if(getCountdown()==8)
                    EntityUtil.attackRange(operator,throwable,null,getDistance()/5,1);

                if(getCountdown()==4)
                    EntityUtil.attackRange(operator,throwable,null,getDistance()/5,1);
            }

            if(getSkillTime()==1)
            {
                operator.removeAttackDamageModifier();
                CapabilityState.Process process = new CapabilityState.Process(operator);
                process.addFunctionOnlyTick(EnumState.VERTIGO,200);
            }
        }
    }

    public class SkillIII extends Skill
    {
        public SkillIII()
        {
            super(3, world.isRemote ? I18n.format("gui.arkworld.operator_amiyas3") : "", RecoveryType.AUTO, TriggerType.MANUAL, Amiya.this, new ArrayList<ArrayList<ItemStack>>()
            {
                {
                    this.add(0,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,5),new ItemStack(ItemRegistry.GRINDSTONE_PENTAHYDRATE,3),new ItemStack(ItemRegistry.LOXIC_KOHL,4))));
                    this.add(1,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,6),new ItemStack(ItemRegistry.ORIROCK_CONCENTRATION,3),new ItemStack(ItemRegistry.GRINDSTONE_PENTAHYDRATE,6))));
                    this.add(2,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,10),new ItemStack(ItemRegistry.POLYESTER_LUMP,4),new ItemStack(ItemRegistry.BIPOLAR_NANOFLAKE,4))));
                }
            });
        }

        @Override
        public void skillFunctionPerTick()
        {
            float atk;
            float hp = 0;

            setInitPoint(0);

            setConsumePoint(120);

            setTime(600);

            if(operator.getSkillThiRank()<=0)
            {
                atk=0.9f+operator.getSkillLevel()*0.1f;

                if(operator.getSkillLevel()<=3) hp=0.25f;

                if(operator.getSkillLevel()>3 && operator.getSkillLevel()<=6) hp=0.5f;

                if(operator.getSkillLevel()==7) hp=0.75f;
            }
            else
            {
                if(operator.getSkillThiRank()<=2) hp=0.75f;

                if(operator.getSkillThiRank()>2) hp=1f;

                atk=1.6f+operator.getSkillThiRank()*0.2f;
            }

            if(operator.getSkillStartup()>0 && operator.getSkillPoint()>=getConsumePoint())
            {
                operator.setSkillTime(getTime());
                operator.setSkillPoint(0);
                operator.setHP(hp);
                operator.setAD(atk);
                operator.setHealth(operator.getMaxHealth());
                operator.applyAttackDamageModifier();
                operator.applyMaxHealthModifier();
            }

            if(operator.getSkillTime()==1)
            {
                operator.setHP(0);
                operator.setAD(0);
                operator.removeMaxHealthModifier();
                operator.removeAttackDamageModifier();
                operator.setHealth(0.0F);
                operator.onDeath(DamageSource.GENERIC);
            }

            if(operator.getSkillTime()>0)
            {
                ParticleList.amiyaParticle(operator);

                if(EntityUtil.atSetIntervals(TICK,operator, (int) (operator.getAttackInterval()*1.5), 0, 0))
                {
                    SimpleThrowable throwable = new SimpleThrowable(world, operator, getAttackDamage() * atk, SimpleThrowable.Type.AMIYA_BALL);
                    EntityUtil.attackRange(operator, throwable, null, getDistance() / 5, 1);
                }
            }
        }
    }
}
