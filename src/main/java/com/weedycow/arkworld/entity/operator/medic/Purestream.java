package com.weedycow.arkworld.entity.operator.medic;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.machine.infrastructure.BlockManufacturingStation;
import com.weedycow.arkworld.block.machine.infrastructure.BlockPowerStation;
import com.weedycow.arkworld.capability.CapabilityOperatorCard;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.entity.operator.Skill;
import com.weedycow.arkworld.entity.weapon.SimpleThrowable;
import com.weedycow.arkworld.item.operator.PurestreamCard;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.arkworld.util.ArkItemUtil;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.util.EntityUtil;
import com.weedycow.weedylib.util.MeleeRangeUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
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

public class Purestream extends Operator
{
    public SkillI skillI = new SkillI();
    public SkillII skillII = new SkillII();
    public SkillIII skillIII = new SkillIII();
    public static final String ID = "purestream";
    public static final String NAME = Arkworld.MODID + ".purestream";

    public Purestream(World world)
    {
        super(world,ID,NAME,Class.MEDIC,Rarity.IV,Gender.WOMAN,Access.OTHER);
        this.setSize(0.8f, 2f);
        this.setRedeployTime(1600);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(operator.medic.purestream.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(operator.medic.purestream.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(operator.medic.purestream.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(operator.medic.purestream.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(operator.medic.purestream.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(operator.medic.purestream.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(operator.medic.purestream.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(operator.medic.purestream.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(getSkillTime()>0 && getSelectSkill()==2 && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.purestream.skill2", true));
        }
        else if(getSelectSkill()==1 && getCountdown()==25 && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.purestream.skill1", false));
            controllerAttack.markNeedsReload();
        }
        else if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(), (int) (14/controllerAttack.getAnimationSpeed()),0) && getTargetState() && !(getSelectSkill()==2 && getSkillTime()>0) && getCountdown()==0)
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.purestream.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.purestream.move", true));
        }
        else
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.purestream.idle", true));

        return PlayState.CONTINUE;
    }

    AnimationController<Purestream> controllerAttack = new AnimationController<>(this,"controllerAttack",5,this::PlayState);
    AnimationController<Purestream> controllerMove = new AnimationController<>(this,"controllerMove",5,this::PlayState);

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

        controllerAttack.setAnimationSpeed((float)operator.medic.purestream.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/operator.medic.purestream.movementSpeed);

        if(world.isRemote)
        {
            if (getElite() == 2)
                setGiftFir(I18n.format("gui.arkworld.operator_purestreamg1"));
            if (getElite() == 1)
                setGiftFir(I18n.format("gui.arkworld.operator_purestreamg1p"));

            if (getElite() >= 1)
                setLogistics(I18n.format("gui.arkworld.operator_purestreamgl2"));
            else
                setLogistics(I18n.format("gui.arkworld.operator_purestreamgl1"));
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
    public float getLogSkillRatio()
    {
        if(getRoom() instanceof BlockPowerStation.TilePowerStation)
        {
            return 0.15f;
        }

        if(getRoom() instanceof BlockManufacturingStation.TileManufacturingStation)
        {
            return 0.15f;
        }

        return 0;
    }

    @Override
    public void commonExecute(Operator attacker, EntityLivingBase target)
    {
        SimpleThrowable throwable = new SimpleThrowable(world,attacker,getAttackDamage(), SimpleThrowable.Type.HEALING_BALL);

        if(EntityUtil.atSetIntervals(TICK,attacker, getAttackInterval(), 0, 0) && !((getSelectSkill()==2 || getSelectSkill()==3) && getSkillTime()>0))
            EntityUtil.attackRange(attacker,throwable,null,getDistance()/5,1);
    }

    @Override
    public void potentialUpFunction()
    {
        if(getPotential()>=4)
        {
            AttributeModifier attackDamageModifiedPro = new AttributeModifier(UUID.fromString("e5966aa7-3d7c-4908-8a7d-a311e1bdeb23"),"Potential attack damage",1,0);

            if(attackDamage.hasModifier(attackDamageModifiedPro))
                attackDamage.removeModifier(attackDamageModifiedPro);

            attackDamage.applyModifier(attackDamageModifiedPro);
        }
    }

    @Override
    public boolean shouldKeepDistance()
    {
        return getAttackTarget()!=getMaster() && (getAttackTarget() instanceof Operator && ((Operator) getAttackTarget()).getMaster()!=getMaster());
    }

    @Override
    public int getDeployPoint()
    {
        if(getPotential()>=6)
        {
            if(getElite()==2) return 17;
            else return 15;
        }
        else if(getPotential()>=2)
        {
            if(getElite()==2) return 18;
            else return 16;
        }
        else
        {
            if (getElite()==2) return 19;
            else return 17;
        }
    }

    @Override
    public void playIdleSound()
    {
        int chance = new Random().nextInt(4);

        if(operator.medic.purestream.idleSound)
        {
            if (chance == 0)
                playSound(SoundRegistry.PURESTREAM_IDLE_I, 1, 1);

            else if (chance == 1)
                playSound(SoundRegistry.PURESTREAM_IDLE_II, 1, 1);

            else if (chance == 2)
                playSound(SoundRegistry.PURESTREAM_IDLE_III, 1, 1);

            else
                playSound(SoundRegistry.PURESTREAM_IDLE_IV, 1, 1);
        }
    }

    @Override
    public void playInteractiveSound()
    {
        int chance = new Random().nextInt(4);

        if(operator.medic.purestream.interactiveSound)
        {
            if (chance == 0)
                playSound(SoundRegistry.PURESTREAM_INTERACT_I, 1, 1);

            else if (chance == 1)
                playSound(SoundRegistry.PURESTREAM_INTERACT_II, 1, 1);

            else if (chance == 2)
                playSound(SoundRegistry.PURESTREAM_INTERACT_III, 1, 1);

            else
                playSound(SoundRegistry.PURESTREAM_INTERACT_IV, 1, 1);
        }
    }

    @Override
    public void playFightingSound()
    {
        int chance = new Random().nextInt(4);

        if(operator.medic.purestream.fightSound)
        {
            if (chance == 0)
                playSound(SoundRegistry.PURESTREAM_FIGHT_I, 1, 1);

            else if (chance == 1)
                playSound(SoundRegistry.PURESTREAM_FIGHT_II, 1, 1);

            else if (chance == 2)
                playSound(SoundRegistry.PURESTREAM_FIGHT_III, 1, 1);

            else
                playSound(SoundRegistry.PURESTREAM_FIGHT_IV, 1, 1);
        }
    }

    @Override
    public void attributeLevelUp()
    {
        if(getElite()==0)
            maxHealthModifiedLevel = new AttributeModifier(UUID.fromString("436248ed-9bcf-426b-a2b0-55e4b8045f92"),"Levelup max health",(getLevel()-1)*0.237+getTrust()*0.075,0);
        if(getElite()==1)
            maxHealthModifiedLevel = new AttributeModifier(UUID.fromString("436248ed-9bcf-426b-a2b0-55e4b8045f92"),"Levelup max health",(getLevel()-1)*0.156+11.85+getTrust()*0.075,0);
        if(getElite()==2)
            maxHealthModifiedLevel = new AttributeModifier(UUID.fromString("436248ed-9bcf-426b-a2b0-55e4b8045f92"),"Levelup max health",(getLevel()-1)*0.137+21.25+getTrust()*0.075,0);

        if(maxHealthModifiedLevel != null)
        {
            if(maxHealth.hasModifier(maxHealthModifiedLevel))
                maxHealth.removeModifier(maxHealthModifiedLevel);

            maxHealth.applyModifier(maxHealthModifiedLevel);

            setHealth(getMaxHealth());
        }

        if(getElite()==0)
            attackDamageModifiedLevel = new AttributeModifier(UUID.fromString("2944d37f-84c8-434f-93bb-9dd6702c1dae"),"Levelup attack damage",(getLevel()-1)*0.102+getTrust()*0.0175,0);
        if(getElite()==1)
            attackDamageModifiedLevel = new AttributeModifier(UUID.fromString("2944d37f-84c8-434f-93bb-9dd6702c1dae"),"Levelup attack damage",(getLevel()-1)*0.085+5.1+getTrust()*0.0175,0);
        if(getElite()==2)
            attackDamageModifiedLevel = new AttributeModifier(UUID.fromString("2944d37f-84c8-434f-93bb-9dd6702c1dae"),"Levelup attack damage",(getLevel()-1)*0.065+10.2+getTrust()*0.0175,0);

        if(attackDamageModifiedLevel != null)
        {
            if(attackDamage.hasModifier(attackDamageModifiedLevel))
                attackDamage.removeModifier(attackDamageModifiedLevel);

            attackDamage.applyModifier(attackDamageModifiedLevel);
        }

        if(getElite()==0)
            defencePowerModifiedLevel = new AttributeModifier(UUID.fromString("f673dcd1-de86-4d7c-b398-d3e3821c7a02"),"Levelup defence power",(getLevel()-1)*0.024,0);
        if(getElite()==1)
            defencePowerModifiedLevel = new AttributeModifier(UUID.fromString("f673dcd1-de86-4d7c-b398-d3e3821c7a02"),"Levelup defence power",(getLevel()-1)*0.020+1.2,0);
        if(getElite()==2)
            defencePowerModifiedLevel = new AttributeModifier(UUID.fromString("f673dcd1-de86-4d7c-b398-d3e3821c7a02"),"Levelup defence power",(getLevel()-1)*0.013+2.15,0);

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

        if(entity instanceof Purestream)
        {
            Purestream Purestream = (Purestream) entity;

            if(Purestream.getMaster()!=null)
            {
                ItemStack stack = ArkItemUtil.findItemStackInPlayerSlot(Purestream.getMaster(), PurestreamCard.class);

                if(stack != ItemStack.EMPTY && stack.getItem() instanceof PurestreamCard)
                {
                    if(stack.hasCapability(CapabilityRegistry.capOperatorCard,null))
                    {
                        CapabilityOperatorCard card = stack.getCapability(CapabilityRegistry.capOperatorCard, null);

                        card.setLevel(Purestream.getLevel());

                        card.setExp(Purestream.getExp());

                        card.setMoveMode(Purestream.getMoveMode().getValue());

                        card.setActionMode(Purestream.getActionMode().getValue());

                        card.setTrust(Purestream.getTrust());

                        card.setSkillLevel(Purestream.getSkillLevel());

                        card.setSkillFirRank(Purestream.getSkillFirRank());

                        card.setSkillSecRank(Purestream.getSkillSecRank());

                        card.setSkillThiRank(Purestream.getSkillThiRank());

                        card.setSelectSkill(Purestream.getSelectSkill());

                        card.setElite(Purestream.getElite());

                        card.setPotential(Purestream.getPotential());

                        card.setCountdown(Purestream.getRedeployTime());

                        card.setTimes(Purestream.getTimes());

                        card.setExistedTime(Purestream.getExistedTime());

                        card.setMood(Purestream.getMood());

                        card.setTrainTime(Purestream.getTrainTime());

                        if (Purestream.getMaster() != null)
                            card.setMaster(Purestream.getMaster().getName());

                        card.setClear(false);
                    }
                }
            }
        }
    }
    
    @Override
    public Tag[] getTagsOperator()
    {
        return new Tag[]{Tag.HEALING,Tag.SUPPORT};
    }

    @Override
    public List<ItemStack> getEliteI()
    {
        return new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.MEDIC_CHIP, 3), new ItemStack(ItemRegistry.ORIROCK_CUBE, 1), new ItemStack(ItemRegistry.SUGAR, 1)));
    }

    @Override
    public List<ItemStack> getEliteII()
    {
        return new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.MEDIC_CHIPSET, 5), new ItemStack(ItemRegistry.INTEGRATED_DEVICE, 11), new ItemStack(ItemRegistry.COAGULATING_GEL, 9)));
    }

    @Override
    public List<List<ItemStack>> getSluMaterial()
    {
        List<List<ItemStack>> slu = new ArrayList<>();
        slu.add(0, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_I, 2), ItemStack.EMPTY, ItemStack.EMPTY)));
        slu.add(1, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_I, 2), new ItemStack(ItemRegistry.ORIROCK, 5), ItemStack.EMPTY)));
        slu.add(2, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_II, 3), new ItemStack(ItemRegistry.SUGAR, 3), ItemStack.EMPTY)));
        slu.add(3, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_II, 3), new ItemStack(ItemRegistry.POLYESTER, 3), ItemStack.EMPTY)));
        slu.add(4, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_II, 3), new ItemStack(ItemRegistry.MANGANESE_ORE, 2), ItemStack.EMPTY)));
        slu.add(5, new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III, 4), new ItemStack(ItemRegistry.GRINDSTONE, 2), ItemStack.EMPTY)));
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
            super(1, world.isRemote ? I18n.format("gui.arkworld.operator_amiyas1") : "", RecoveryType.AUTO, TriggerType.MANUAL, Purestream.this, new ArrayList<ArrayList<ItemStack>>()
            {
                {
                    this.add(0,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,2),new ItemStack(ItemRegistry.POLYMERIZED_GEL,1),new ItemStack(ItemRegistry.ORIRON_CLUSTER,6))));
                    this.add(1,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,4),new ItemStack(ItemRegistry.GRINDSTONE_PENTAHYDRATE,2),new ItemStack(ItemRegistry.WHITE_HORSE_KOHL,3))));
                    this.add(2,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,6),new ItemStack(ItemRegistry.DSTEEL,2),new ItemStack(ItemRegistry.OPTIMIZED_DEVICE,2))));
                }
            });
        }

        @Override
        public void skillFunctionPerTick()
        {
            setInitPoint(10);

            if(getSkillFirRank()==0)
            {
                if (operator.getSkillLevel() == 1) setConsumePoint(30);

                if (operator.getSkillLevel() == 2) setConsumePoint(29);

                if (operator.getSkillLevel() == 3) setConsumePoint(28);

                if (operator.getSkillLevel() == 4) setConsumePoint(27);

                if (operator.getSkillLevel() == 5) setConsumePoint(26);

                if (operator.getSkillLevel() == 6) setConsumePoint(25);

                if (operator.getSkillLevel() == 7) setConsumePoint(24);
            }

            if(getSkillFirRank()==1) setConsumePoint(23);

            if(getSkillFirRank()==2) setConsumePoint(22);

            if(getSkillFirRank()==3) setConsumePoint(20);

            if(operator.getSkillStartup()>0 && operator.getSkillPoint()>=getConsumePoint()) setCountdown(25);

            if(getCountdown()==1) skillFunctionInstance();
        }

        @Override
        public void skillFunctionInstance()
        {
            super.skillFunctionInstance();

            operator.setSkillPoint(0);

            float atk = 1;

            if(operator.getSkillFirRank()==0)
            {
                atk = 1.9f + 0.1f*operator.getSkillLevel();
            }

            if(getSkillFirRank()==1) atk = 2.9f;

            if(getSkillFirRank()==2) atk = 3.2f;

            if(getSkillFirRank()==3) atk = 3.5f;

            for(EntityLivingBase entity : world.getEntitiesWithinAABB(EntityLivingBase.class,new MeleeRangeUtil(operator,getAttackRange())))
            {
                if(entity==operator.getMaster() || (entity instanceof Operator && ((Operator) entity).getMaster()==operator.getMaster()))
                {
                    entity.heal(getAttackDamage()*atk);
                }
            }
        }
    }

    public class SkillII extends Skill
    {
        public SkillII()
        {
            super(2, world.isRemote ? I18n.format("gui.arkworld.operator_purestreams2") : "", RecoveryType.AUTO, TriggerType.MANUAL, Purestream.this, new ArrayList<ArrayList<ItemStack>>()
            {
                {
                    this.add(0,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,2),new ItemStack(ItemRegistry.ORIRON_BLOCK,1),new ItemStack(ItemRegistry.INCANDESCENT_ALLOY,3))));
                    this.add(1,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,4),new ItemStack(ItemRegistry.RMAR,2),new ItemStack(ItemRegistry.MANGANESE_TRIHYDRATE,2))));
                    this.add(2,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,6),new ItemStack(ItemRegistry.BIPOLAR_NANOFLAKE,2),new ItemStack(ItemRegistry.KETON_COLLOID,2))));
                }
            });
        }

        @Override
        public void skillFunctionPerTick()
        {
            float atk = 0.3f;

            setTime(500);

            if(getSkillSecRank()==0)
            {
                if (getSkillLevel() <= 3) atk = 0.3f;
                else if (getSkillLevel() <= 6) atk = 0.35f;
                else atk = 0.4f;

                if(getSkillLevel()==1)
                {
                    setInitPoint(10);
                    setConsumePoint(70);
                }
                if(getSkillLevel()==2)
                {
                    setInitPoint(11);
                    setConsumePoint(69);
                }
                if(getSkillLevel()==3)
                {
                    setInitPoint(12);
                    setConsumePoint(68);
                }
                if(getSkillLevel()==4)
                {
                    setInitPoint(15);
                    setConsumePoint(67);
                }
                if(getSkillLevel()==5)
                {
                    setInitPoint(16);
                    setConsumePoint(66);
                }
                if(getSkillLevel()==6)
                {
                    setInitPoint(17);
                    setConsumePoint(65);
                }
                if(getSkillLevel()==7)
                {
                    setInitPoint(20);
                    setConsumePoint(64);
                }
            }
            else
            {
                if(getSkillSecRank()==1)
                {
                    atk=0.43f;
                    setInitPoint(23);
                    setConsumePoint(63);
                }
                if(getSkillSecRank()==2)
                {
                    atk=0.46f;
                    setInitPoint(26);
                    setConsumePoint(62);
                }
                if(getSkillSecRank()==3)
                {
                    atk=0.50f;
                    setInitPoint(30);
                    setConsumePoint(60);
                }
            }

            if(operator.getSkillStartup()>0 && operator.getSkillPoint()>=getConsumePoint())
            {
                operator.setSkillTime(getTime());
                operator.setSkillPoint(0);
                operator.setAI(-0.88f);
                operator.applyAttackIntervalModifier();
            }

            if(operator.getSkillTime()>0 && EntityUtil.atSetIntervals(TICK,operator,getAttackInterval(),0,0))
            {
                List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class,new MeleeRangeUtil(operator,getAttackRange()),entity-> entity.getHealth()<entity.getMaxHealth());

                if(entities.size()>0)
                    entities.get(new Random().nextInt(entities.size())).heal(getAttackDamage()*atk);
            }

            if(operator.getSkillTime()==1)
            {
                operator.removeAttackIntervalModifier();
            }
        }
    }

    public class SkillIII extends Skill
    {
        public SkillIII()
        {
            super(3, world.isRemote ? "null" : "", RecoveryType.AUTO, TriggerType.MANUAL, Purestream.this, new ArrayList<ArrayList<ItemStack>>()
            {
                {
                    this.add(0,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,5),new ItemStack(ItemRegistry.GRINDSTONE_PENTAHYDRATE,3),new ItemStack(ItemRegistry.LOXIC_KOHL,4))));
                    this.add(1,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,6),new ItemStack(ItemRegistry.ORIROCK_CONCENTRATION,3),new ItemStack(ItemRegistry.GRINDSTONE_PENTAHYDRATE,6))));
                    this.add(2,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_III,10),new ItemStack(ItemRegistry.POLYESTER_LUMP,4),new ItemStack(ItemRegistry.BIPOLAR_NANOFLAKE,4))));
                }
            });
        }
    }
}
