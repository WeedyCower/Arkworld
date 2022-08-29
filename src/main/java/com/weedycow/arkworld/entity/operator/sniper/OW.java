package com.weedycow.arkworld.entity.operator.sniper;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.machine.infrastructure.BlockTrainingRoom;
import com.weedycow.arkworld.capability.CapabilityOperatorCard;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.entity.device.EntityMine;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.entity.operator.Skill;
import com.weedycow.arkworld.entity.weapon.Bomb;
import com.weedycow.arkworld.item.operator.WCard;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.arkworld.util.ArkEntityUtil;
import com.weedycow.arkworld.util.ArkItemUtil;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.util.AoeRangeUtil;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.*;

public class OW extends Operator
{
    public SkillI skillI = new SkillI();
    public SkillII skillII = new SkillII();
    public SkillIII skillIII = new SkillIII();
    public static final String ID = "ow";
    public static final String NAME = Arkworld.MODID + ".ow";

    public OW(World world)
    {
        super(world,ID,NAME,Class.SNIPER,Rarity.VI,Gender.WOMAN,Access.HEADHUNT);
        this.setSize(0.8f, 2f);

        if(getPotential()>=3)
            this.setRedeployTime(1320);
        else
            this.setRedeployTime(1400);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(operator.sniper.ow.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(operator.sniper.ow.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(operator.sniper.ow.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(operator.sniper.ow.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(operator.sniper.ow.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(operator.sniper.ow.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(operator.sniper.ow.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(operator.sniper.ow.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(getSelectSkill()==3 && getCountdown()==80)
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.w.skill", false));
            controllerAttack.markNeedsReload();
        }
        else if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(), (int) (12/controllerAttack.getAnimationSpeed()),0) && getTargetState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.w.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.w.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.w.idle", true));

        return PlayState.CONTINUE;
    }

    AnimationController<Operator> controllerAttack = new AnimationController<>(this,"controllerAttack",5,this::PlayState);
    AnimationController<Operator> controllerMove = new AnimationController<>(this,"controllerMove",5,this::PlayState);

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

        controllerAttack.setAnimationSpeed((float)operator.sniper.ow.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/operator.sniper.ow.movementSpeed);

        if(world.isRemote)
        {
            if (getElite() == 2)
                setGiftFir(I18n.format("gui.arkworld.operator_owg1p"));
            else if (getElite() == 1)
                setGiftFir(I18n.format("gui.arkworld.operator_owg1"));

            if (getPotential() >= 5)
                setGiftSec(I18n.format("gui.arkworld.operator_owg2p"));
            else
                setGiftSec(I18n.format("gui.arkworld.operator_owg2"));

            if(getElite()==2)
                setLogistics(I18n.format("gui.arkworld.operator_owl2"));
            else
                setLogistics(I18n.format("gui.arkworld.operator_owl1"));
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
    }

    @Override
    public void commonExecute(Operator attacker, EntityLivingBase target)
    {
        Bomb bomb = new Bomb(world, attacker,getAttackDamage(),false,Bomb.Type.INSTANT_BOMB);

        if (EntityUtil.atSetIntervals(TICK,attacker, getAttackInterval(), 0, 0))
            EntityUtil.attackRange(attacker,bomb,null,getDistance() / 7,1);
    }

    @Override
    public void potentialUpFunction()
    {
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
        if(getPotential()>=6)
        {
            if (getElite()==2) return 27;
            else if (getElite()==1) return 25;
            else return 23;
        }
        else if(getPotential()>=2)
        {
            if (getElite()==2) return 28;
            else if (getElite()==1) return 26;
            else return 24;
        }
        else
        {
            if (getElite()==2) return 29;
            else if (getElite()==1) return 27;
            else return 25;
        }
    }

    @Override
    public float getLogSkillRatio()
    {
        if(getRoom() instanceof BlockTrainingRoom.TileTrainingRoom &&
                ArkEntityUtil.getOperator(world,((BlockTrainingRoom.TileTrainingRoom) getRoom()).getCoach())==this
                && ArkEntityUtil.getOperator(world,((BlockTrainingRoom.TileTrainingRoom) getRoom()).getTrainer()) != null &&
                ArkEntityUtil.getOperator(world,((BlockTrainingRoom.TileTrainingRoom) getRoom()).getTrainer()).getOperatorClass()==Class.SNIPER)
        {
            if (getElite() == 2) return 0.6f;
            else return 0.3f;
        }
        return 0;
    }

    @Override
    public void attributeLevelUp()
    {
        if(getElite()==0)
            maxHealthModifiedLevel = new AttributeModifier(UUID.fromString("436248ed-9bcf-426b-a2b0-55e4b8045f92"),"Levelup max health",(getLevel()-1)*0.206,0);
        if(getElite()==1)
            maxHealthModifiedLevel = new AttributeModifier(UUID.fromString("436248ed-9bcf-426b-a2b0-55e4b8045f92"),"Levelup max health",(getLevel()-1)*0.160+10.3,0);
        if(getElite()==2)
            maxHealthModifiedLevel = new AttributeModifier(UUID.fromString("436248ed-9bcf-426b-a2b0-55e4b8045f92"),"Levelup max health",(getLevel()-1)*0.178+23.15,0);

        if(maxHealthModifiedLevel != null)
        {
            if(maxHealth.hasModifier(maxHealthModifiedLevel))
                maxHealth.removeModifier(maxHealthModifiedLevel);

            maxHealth.applyModifier(maxHealthModifiedLevel);

            setHealth(getMaxHealth());
        }

        if(getElite()==0)
            attackDamageModifiedLevel = new AttributeModifier(UUID.fromString("2944d37f-84c8-434f-93bb-9dd6702c1dae"),"Levelup attack damage",(getLevel()-1)*0.171+getTrust()*0.05,0);
        if(getElite()==1)
            attackDamageModifiedLevel = new AttributeModifier(UUID.fromString("2944d37f-84c8-434f-93bb-9dd6702c1dae"),"Levelup attack damage",(getLevel()-1)*0.102+8.55+getTrust()*0.05,0);
        if(getElite()==2)
            attackDamageModifiedLevel = new AttributeModifier(UUID.fromString("2944d37f-84c8-434f-93bb-9dd6702c1dae"),"Levelup attack damage",(getLevel()-1)*0.111+15.7+getTrust()*0.05,0);

        if(attackDamageModifiedLevel != null)
        {
            if(attackDamage.hasModifier(attackDamageModifiedLevel))
                attackDamage.removeModifier(attackDamageModifiedLevel);

            attackDamage.applyModifier(attackDamageModifiedLevel);
        }

        if(getElite()==0)
            defencePowerModifiedLevel = new AttributeModifier(UUID.fromString("f673dcd1-de86-4d7c-b398-d3e3821c7a02"),"Levelup defence power",(getLevel()-1)*0.021,0);
        if(getElite()==1)
            defencePowerModifiedLevel = new AttributeModifier(UUID.fromString("f673dcd1-de86-4d7c-b398-d3e3821c7a02"),"Levelup defence power",(getLevel()-1)*0.014+1.05,0);
        if(getElite()==2)
            defencePowerModifiedLevel = new AttributeModifier(UUID.fromString("f673dcd1-de86-4d7c-b398-d3e3821c7a02"),"Levelup defence power",(getLevel()-1)*0.011+2.2,0);

        if(defencePowerModifiedLevel != null)
        {
            if(defensivePower.hasModifier(defencePowerModifiedLevel))
                defensivePower.removeModifier(defencePowerModifiedLevel);

            defensivePower.applyModifier(defencePowerModifiedLevel);
        }
    }

    public static void onDeath(LivingDeathEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();

        if(entity instanceof OW)
        {
            OW OW = (OW) entity;

            if(OW.getMaster()!=null)
            {
                ItemStack stack = ArkItemUtil.findItemStackInPlayerSlot(OW.getMaster(), WCard.class);

                if(stack != ItemStack.EMPTY && stack.getItem() instanceof WCard)
                {
                    if(stack.hasCapability(CapabilityRegistry.capOperatorCard,null))
                    {
                        CapabilityOperatorCard card = stack.getCapability(CapabilityRegistry.capOperatorCard, null);

                        card.setLevel(OW.getLevel());

                        card.setExp(OW.getExp());

                        card.setMoveMode(OW.getMoveMode().getValue());

                        card.setActionMode(OW.getActionMode().getValue());

                        card.setTrust(OW.getTrust());

                        card.setSkillLevel(OW.getSkillLevel());

                        card.setSkillFirRank(OW.getSkillFirRank());

                        card.setSkillSecRank(OW.getSkillSecRank());

                        card.setSkillThiRank(OW.getSkillThiRank());

                        card.setSelectSkill(OW.getSelectSkill());

                        card.setElite(OW.getElite());

                        card.setPotential(OW.getPotential());

                        card.setCountdown(OW.getRedeployTime());

                        card.setTimes(OW.getTimes());

                        card.setExistedTime(OW.getExistedTime());

                        card.setMood(OW.getMood());

                        card.setTrainTime(OW.getTrainTime());

                        if (OW.getMaster() != null)
                            card.setMaster(OW.getMaster().getName());

                        card.setClear(false);
                    }
                }
            }
        }
    }

    @Override
    public void playIdleSound()
    {
        int chance = new Random().nextInt(4);

        if(operator.sniper.ow.idleSound)
        {
            if (chance == 0)
                playSound(SoundRegistry.W_IDLE_I, 1, 1);

            else if (chance == 1)
                playSound(SoundRegistry.W_IDLE_II, 1, 1);

            else if (chance == 2)
                playSound(SoundRegistry.W_IDLE_III, 1, 1);

            else
                playSound(SoundRegistry.W_IDLE_IV, 1, 1);
        }
    }

    @Override
    public void playInteractiveSound()
    {
        int chance = new Random().nextInt(4);

        if(operator.sniper.ow.interactiveSound)
        {
            if (chance == 0)
                playSound(SoundRegistry.W_INTERACT_I, 1, 1);

            else if (chance == 1)
                playSound(SoundRegistry.W_INTERACT_II, 1, 1);

            else if (chance == 2)
                playSound(SoundRegistry.W_INTERACT_III, 1, 1);

            else
                playSound(SoundRegistry.W_INTERACT_IV, 1, 1);
        }
    }

    @Override
    public void playFightingSound()
    {
        int chance = new Random().nextInt(4);

        if(operator.sniper.ow.fightSound)
        {
            if (chance == 0)
                playSound(SoundRegistry.W_FIGHT_I, 1, 1);

            else if (chance == 1)
                playSound(SoundRegistry.W_FIGHT_II, 1, 1);

            else if (chance == 2)
                playSound(SoundRegistry.W_FIGHT_III, 1, 1);

            else
                playSound(SoundRegistry.W_FIGHT_IV, 1, 1);
        }
    }

    @Override
    public Tag[] getTagsOperator()
    {
        return new Tag[]{Tag.DPS,Tag.CROWD_CONTROL};
    }

    @Override
    public List<ItemStack> getEliteI()
    {
        return new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SNIPER_CHIP, 5), new ItemStack(ItemRegistry.ORIROCK_CUBE, 12), new ItemStack(ItemRegistry.SUGAR, 5)));
    }

    @Override
    public List<ItemStack> getEliteII()
    {
        return new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SNIPER_DUALCHIP, 4), new ItemStack(ItemRegistry.BIPOLAR_NANOFLAKE, 4), new ItemStack(ItemRegistry.KETON_COLLOID, 7)));
    }

    @Override
    public List<List<ItemStack>> getSluMaterial()
    {
        List<List<ItemStack>> slu = new ArrayList<>();
        slu.add(0, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_I, 5), ItemStack.EMPTY, ItemStack.EMPTY)));
        slu.add(1, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_I, 5), new ItemStack(ItemRegistry.DIKETON, 6), new ItemStack(ItemRegistry.ORIROCK, 4))));
        slu.add(2, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_II, 8), new ItemStack(ItemRegistry.DEVICE, 3), ItemStack.EMPTY)));
        slu.add(3, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_II, 8), new ItemStack(ItemRegistry.ORIROCK_CUBE, 5), new ItemStack(ItemRegistry.DEVICE, 3))));
        slu.add(4, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_II, 8), new ItemStack(ItemRegistry.INTEGRATED_DEVICE, 4), ItemStack.EMPTY)));
        slu.add(5, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III, 8), new ItemStack(ItemRegistry.MANGANESE_ORE, 3), new ItemStack(ItemRegistry.RMAY, 4))));
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
            super(1, world.isRemote ? I18n.format("gui.arkworld.operator_ows1") : "", RecoveryType.AUTO, TriggerType.MANUAL, OW.this, new ArrayList<ArrayList<ItemStack>>()
            {
                {
                    this.add(0,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,8),new ItemStack(ItemRegistry.INCANDESCENT_ALLOY_BLOCK,4),new ItemStack(ItemRegistry.RMAY,5))));
                    this.add(1,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,12),new ItemStack(ItemRegistry.RMAY,4),new ItemStack(ItemRegistry.MANGANESE_TRIHYDRATE,7))));
                    this.add(2,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,15),new ItemStack(ItemRegistry.DSTEEL,6),new ItemStack(ItemRegistry.POLYMERIZED_GEL,6))));
                }
            });
        }

        @Override
        public void skillFunctionPerTick()
        {
            if(getSkillFirRank()==0)
            {
                if (getSkillLevel() == 1) setConsumePoint(25);

                if (getSkillLevel() == 2) setConsumePoint(24);

                if (getSkillLevel() == 3) setConsumePoint(23);

                if (getSkillLevel() == 4) setConsumePoint(22);

                if (getSkillLevel() == 5) setConsumePoint(21);

                if (getSkillLevel() == 6) setConsumePoint(20);

                if (getSkillLevel() == 7) setConsumePoint(19);
            }

            if(getSkillFirRank()==1) setConsumePoint(18);

            if(getSkillFirRank()==1) setConsumePoint(17);

            if(getSkillFirRank()==1) setConsumePoint(16);

            if(operator.getSkillStartup()>0 && operator.getSkillPoint()>=getConsumePoint())
            {
                skillFunctionInstance();

                operator.setSkillPoint(0);

                operator.setCountdown(2);
            }
        }

        @Override
        public void skillFunctionInstance()
        {
            float atk = 1;

            if(getSkillFirRank()==0)
            {
                if (getSkillLevel() == 1) atk = 2.3f;

                if (getSkillLevel() == 2) atk = 2.4f;

                if (getSkillLevel() == 3) atk = 2.5f;

                if (getSkillLevel() == 4) atk = 2.7f;

                if (getSkillLevel() == 5) atk = 2.8f;

                if (getSkillLevel() == 6) atk = 2.9f;

                if (getSkillLevel() == 7) atk = 3.1f;
            }

            if(getSkillFirRank()==1) atk = 3.2f;

            if(getSkillFirRank()==2) atk = 3.3f;

            if(getSkillFirRank()==3) atk = 3.5f;

            Bomb bomb = new Bomb(world, operator,getAttackDamage()*atk,false,Bomb.Type.INSTANT_BOMB);

            bomb.setVertigo(true);

            if(getSkillFirRank()==0)
            {
                if(getSkillLevel()<=3) bomb.setVertigoTime(30);
                else if (getSkillLevel()<=6) bomb.setVertigoTime(36);
                else bomb.setVertigoTime(42);
            }

            if(getSkillFirRank()==1) bomb.setVertigoTime(48);

            if(getSkillFirRank()==2) bomb.setVertigoTime(52);

            if(getSkillFirRank()==3) bomb.setVertigoTime(60);

            EntityUtil.attackRange(operator,bomb,null,getDistance() / 7,1);
        }
    }

    public class SkillII extends Skill
    {
        public SkillII()
        {
            super(2, world.isRemote ? I18n.format("gui.arkworld.operator_ows2") : "", RecoveryType.AUTO, TriggerType.AUTO, OW.this, new ArrayList<ArrayList<ItemStack>>()
            {
                {
                    this.add(0,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,8),new ItemStack(ItemRegistry.OPTIMIZED_DEVICE,3),new ItemStack(ItemRegistry.ORIRON_CLUSTER,4))));
                    this.add(1,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,12),new ItemStack(ItemRegistry.POLYMERIZED_GEL,4),new ItemStack(ItemRegistry.ORIROCK_CONCENTRATION,10))));
                    this.add(2,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,15),new ItemStack(ItemRegistry.BIPOLAR_NANOFLAKE,6),new ItemStack(ItemRegistry.INCANDESCENT_ALLOY_BLOCK,5))));
                }
            });
        }

        @Override
        public void skillFunctionPerTick()
        {
            if(getSkillFirRank()==0)
            {
                if (getSkillLevel() == 1) setConsumePoint(12);

                if (getSkillLevel() == 2) setConsumePoint(12);

                if (getSkillLevel() == 3) setConsumePoint(12);

                if (getSkillLevel() == 4) setConsumePoint(11);

                if (getSkillLevel() == 5) setConsumePoint(11);

                if (getSkillLevel() == 6) setConsumePoint(11);

                if (getSkillLevel() == 7) setConsumePoint(10);
            }

            if(getSkillFirRank()==1) setConsumePoint(9);

            if(getSkillFirRank()==1) setConsumePoint(9);

            if(getSkillFirRank()==1) setConsumePoint(8);

            if(operator.getSkillPoint()>=getConsumePoint() && getTargetState())
            {
                if(!world.isRemote)
                    skillFunctionInstance();

                operator.setSkillPoint(0);
            }
        }

        @Override
        public void skillFunctionInstance()
        {
            float atk = 1;
            int vertigo = 0;

            if(getSkillSecRank()==0)
            {
                if (getSkillLevel() == 1)
                {
                    atk = 1.9f;

                    vertigo = 28;
                }

                if (getSkillLevel() == 2)
                {
                    atk = 2.0f;

                    vertigo = 28;
                }

                if (getSkillLevel() == 3)
                {
                    atk = 2.1f;

                    vertigo = 28;
                }

                if (getSkillLevel() == 4)
                {
                    atk = 2.2f;

                    vertigo = 32;
                }

                if (getSkillLevel() == 5)
                {
                    atk = 2.3f;

                    vertigo = 32;
                }

                if (getSkillLevel() == 6)
                {
                    atk = 2.4f;

                    vertigo = 32;
                }

                if (getSkillLevel() == 7)
                {
                    atk = 2.5f;

                    vertigo = 36;
                }
            }

            if(getSkillSecRank()==1)
            {
                atk = 2.6f;

                vertigo = 36;
            }

            if(getSkillSecRank()==2)
            {
                atk = 2.7f;

                vertigo = 40;
            }

            if(getSkillSecRank()==3)
            {
                atk = 2.8f;

                vertigo = 44;
            }

            EntityMine mine = new EntityMine(world,operator,getAttackDamage()*atk,vertigo);

            if(getAttackTarget()!=null && getDistance()<=getAttackRange())
                mine.setPosition(getAttackTarget().posX,getAttackTarget().posY,getAttackTarget().posZ);
            else
                mine.setPosition(operator.posX+new Random().nextInt(16)-7,operator.posY,operator.posZ+new Random().nextInt(16)-7);

            world.spawnEntity(mine);
        }
    }

    public class SkillIII extends Skill
    {
        public SkillIII()
        {
            super(3, world.isRemote ? I18n.format("gui.arkworld.operator_ows3") : "", RecoveryType.AUTO, TriggerType.MANUAL, OW.this, new ArrayList<ArrayList<ItemStack>>()
            {
                {
                    this.add(0,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,8),new ItemStack(ItemRegistry.WHITE_HORSE_KOHL,4),new ItemStack(ItemRegistry.AKETON,8))));
                    this.add(1,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,12),new ItemStack(ItemRegistry.ORIRON_BLOCK,4),new ItemStack(ItemRegistry.INCANDESCENT_ALLOY_BLOCK,7))));
                    this.add(2,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,15),new ItemStack(ItemRegistry.BIPOLAR_NANOFLAKE,6),new ItemStack(ItemRegistry.POLYMERIZED_GEL,6))));
                }
            });
        }

        @Override
        public void skillFunctionPerTick()
        {
            float atk = 1;
            int vertigo = 0;
            int amount = 1;

            if(getSkillSecRank()==0)
            {
                amount=3;

                if (getSkillLevel() == 1)
                {
                    atk = 2.2f;

                    vertigo = 60;

                    setInitPoint(11);

                    setConsumePoint(45);
                }

                if (getSkillLevel() == 2)
                {
                    atk = 2.3f;

                    vertigo = 60;

                    setInitPoint(12);

                    setConsumePoint(44);
                }

                if (getSkillLevel() == 3)
                {
                    atk = 2.4f;

                    vertigo = 60;

                    setInitPoint(13);

                    setConsumePoint(43);
                }

                if (getSkillLevel() == 4)
                {
                    atk = 2.5f;

                    vertigo = 70;

                    setInitPoint(14);

                    setConsumePoint(42);
                }

                if (getSkillLevel() == 5)
                {
                    atk = 2.6f;

                    vertigo = 70;

                    setInitPoint(15);

                    setConsumePoint(41);
                }

                if (getSkillLevel() == 6)
                {
                    atk = 2.7f;

                    vertigo = 70;

                    setInitPoint(16);

                    setConsumePoint(40);
                }

                if (getSkillLevel() == 7)
                {
                    atk = 2.8f;

                    vertigo = 80;

                    setInitPoint(17);

                    setConsumePoint(39);
                }
            }

            if(getSkillSecRank()==1)
            {
                amount=4;

                atk = 2.9f;

                vertigo = 80;

                setInitPoint(18);

                setConsumePoint(47);
            }

            if(getSkillSecRank()==2)
            {
                amount=4;

                atk = 3f;

                vertigo = 90;

                setInitPoint(19);

                setConsumePoint(35);
            }

            if(getSkillSecRank()==3)
            {
                amount=4;

                atk = 3.1f;

                vertigo = 100;

                setInitPoint(20);

                setConsumePoint(33);
            }

            List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AoeRangeUtil(operator,getAttackRange()),entity -> entity instanceof EntityMob);

            if(operator.getSkillStartup()>0 && operator.getSkillPoint()>=getConsumePoint())
            {
                operator.setSkillPoint(0);

                operator.setCountdown(80);

                operator.playSound(SoundRegistry.W_COUNTDOWN,1,1);

                for (EntityLivingBase target : entities.size()>amount?entities.subList(0,amount):entities)
                {
                    if(!world.isRemote)
                    {
                        if(target.hasCapability(CapabilityRegistry.capState, null))
                        {
                            new CapabilityState.Process(target).addFunction(EnumState.D12, (int) ((getAttackDamage()*atk)/2),61);
                        }
                    }
                }
            }

            if(getCountdown()==20)
            {
                for (EntityLivingBase target : entities.size()>amount?entities.subList(0,amount):entities)
                {
                    if(!world.isRemote)
                    {
                        new CapabilityState.Process(target).addFunctionOnlyTick(EnumState.VERTIGO,vertigo);
                    }
                }
            }
        }
    }
}
