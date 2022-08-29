package com.weedycow.arkworld.entity.operator;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.ai.OperateMoveBase;
import com.weedycow.arkworld.ai.OperatorAIAttackTarget;
import com.weedycow.arkworld.block.machine.infrastructure.BlockMachine;
import com.weedycow.arkworld.block.machine.infrastructure.BlockTrainingRoom;
import com.weedycow.arkworld.block.machine.infrastructure.MachineTile;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.arkworld.util.ArkEntityUtil;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.IRES;
import com.weedycow.weedylib.entity.WLA;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.entity.WLT;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

import static com.weedycow.weedylib.util.EntityUtil.distanceToTarget;

public abstract class Operator extends EntityCreature implements IAnimatable, IOPR, IRES
{
    int chance = 0;
    public UUID uuid;
    public String id;
    public String name;
    protected int countdown;
    protected Class operatorClass;
    protected Rarity rarity;
    protected Gender gender;
    protected Position position;
    protected Access access;
    protected boolean silenceResistance;
    protected boolean stunResistance;
    protected boolean sleepResistance;
    protected boolean freezeResistance;
    protected String giftFir;
    protected String giftSec;
    protected String logistics;
    protected MachineTile room;
    protected BlockMachine.Type roomType;
    protected AttributeModifier maxHealthModifiedLevel;
    protected AttributeModifier attackDamageModifiedLevel;
    protected AttributeModifier defencePowerModifiedLevel;
    protected AttributeModifier spellResistanceModifiedLevel;
    protected AttributeModifier maxHealthModifiedTemp;
    protected AttributeModifier attackDamageModifiedTemp;
    protected AttributeModifier defencePowerModifiedTemp;
    protected AttributeModifier spellResistanceModifiedTemp;
    protected AttributeModifier moveSpeedModifiedTemp;
    protected AttributeModifier attackIntervalModifiedTemp;
    protected AttributeModifier attackRangeModifiedTemp;
    protected AnimationFactory factory = new AnimationFactory(this);
    protected IAttributeInstance maxHealth = this.getEntityAttribute(WLMAttributes.MAX_HEALTH);
    protected IAttributeInstance moveSpeed = this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED);
    protected IAttributeInstance defensivePower = this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER);
    protected IAttributeInstance spellResistance = this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE);
    protected IAttributeInstance attackDamage = this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE);
    protected IAttributeInstance attackInterval = this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL);
    protected IAttributeInstance attackRange = this.getEntityAttribute(WLMAttributes.ATTACK_RANGE);
    protected static final DataParameter<Float> HP = EntityDataManager.createKey(Operator.class, DataSerializers.FLOAT);
    protected static final DataParameter<Float> MS = EntityDataManager.createKey(Operator.class, DataSerializers.FLOAT);
    protected static final DataParameter<Float> DP = EntityDataManager.createKey(Operator.class, DataSerializers.FLOAT);
    protected static final DataParameter<Float> SR = EntityDataManager.createKey(Operator.class, DataSerializers.FLOAT);
    protected static final DataParameter<Float> AD = EntityDataManager.createKey(Operator.class, DataSerializers.FLOAT);
    protected static final DataParameter<Float> AR = EntityDataManager.createKey(Operator.class, DataSerializers.FLOAT);
    protected static final DataParameter<Integer> AI = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> TICK = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Float> DISTANCE = EntityDataManager.createKey(Operator.class, DataSerializers.FLOAT);
    protected static final DataParameter<Boolean> ATTACK = EntityDataManager.createKey(Operator.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> COUNTDOWN = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> POTENTIAL = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> DEPLOY_POINT = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> REDEPLOY_TIME = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> RESIST_NUMBER = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> EXP = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> EXP_NEED = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> MOVE_MODE = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> ACTION_MODE = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<String> MASTER = EntityDataManager.createKey(Operator.class, DataSerializers.STRING);
    protected static final DataParameter<Integer> TRUST = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> SKILL_LEVEL = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> SKILL_FIR_RANK = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> SKILL_SEC_RANK = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> SKILL_THI_RANK = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> SELECT_SKILL = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> SKILL_TIME = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> SKILL_STARTUP = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> SKILL_POINT = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> ELITE = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Boolean> SKILLING = EntityDataManager.createKey(Operator.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> TARGET = EntityDataManager.createKey(Operator.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> TIMES = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> EXISTED_TIME = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<Float> MOOD = EntityDataManager.createKey(Operator.class, DataSerializers.FLOAT);
    protected static final DataParameter<Integer> TRAIN_TIME = EntityDataManager.createKey(Operator.class, DataSerializers.VARINT);
    protected static final DataParameter<String> ID = EntityDataManager.createKey(Operator.class, DataSerializers.STRING);
    public static ArkConfig.Creature.Operator operator = ArkConfig.creature.operator;

    public Operator(World worldIn)
    {
        super(worldIn);
        this.enablePersistence();
    }

    public Operator(World worldIn, String id, String name, Class operatorClass, Rarity rarity, Gender gender, Access access)
    {
        this(worldIn);
        this.id = id;
        this.name = name;
        this.operatorClass = operatorClass;
        this.rarity = rarity;
        this.gender = gender;
        this.access = access;
    }

    public Operator getOperator(int level, int exp, int moveMode, int actionMode, int trust, int skillLevel, int skillFirRank, int skillSecRank, int skillThiRank, int selectSkill, int elite, int potential, int times, int existed_time, float mood, int trainTime, String master)
    {
        this.setLevel(level);
        this.setExp(exp);
        this.setMoveMode(MoveMode.getMode(moveMode));
        this.setActionMode(ActionMode.getMode(actionMode));
        this.setTrust(trust);
        this.setSkillLevel(skillLevel);
        this.setSkillFirRank(skillFirRank);
        this.setSkillSecRank(skillSecRank);
        this.setSkillThiRank(skillThiRank);
        this.setSelectSkill(selectSkill);
        this.setElite(elite);
        this.setPotential(potential);
        this.setTimes(times);
        this.setExistedTime(existed_time);
        this.setMood(mood);
        this.setTrainTime(trainTime);
        this.setMaster(world.getPlayerEntityByName(master));
        return this;
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new OperateMoveBase(this,getMoveSpeed()));
        this.targetTasks.addTask(0, new OperatorAIAttackTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttributeMap().registerAttribute(WLMAttributes.DEFENSIVE_POWER);
        this.getAttributeMap().registerAttribute(WLMAttributes.SPELL_RESISTANCE);
        this.getAttributeMap().registerAttribute(WLMAttributes.ATTACK_RANGE);
        this.getAttributeMap().registerAttribute(WLMAttributes.ATTACK_INTERVAL);
        this.getEntityAttribute(WLMAttributes.ARMOR).setBaseValue(0);
        this.getEntityAttribute(WLMAttributes.FOLLOW_RANGE).setBaseValue(32);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(AI,0);
        this.getDataManager().register(HP,0F);
        this.getDataManager().register(MS,0F);
        this.getDataManager().register(DP,0F);
        this.getDataManager().register(SR,0F);
        this.getDataManager().register(AD,0F);
        this.getDataManager().register(AR,0F);
        this.getDataManager().register(POTENTIAL,1);
        this.getDataManager().register(DEPLOY_POINT,0);
        this.getDataManager().register(REDEPLOY_TIME,0);
        this.getDataManager().register(RESIST_NUMBER,0);
        this.getDataManager().register(ATTACK,false);
        this.getDataManager().register(DISTANCE,0f);
        this.getDataManager().register(TICK,0);
        this.getDataManager().register(LEVEL,1);
        this.getDataManager().register(EXP,0);
        this.getDataManager().register(EXP_NEED,0);
        this.getDataManager().register(MOVE_MODE,0);
        this.getDataManager().register(ACTION_MODE,0);
        this.getDataManager().register(MASTER,"");
        this.getDataManager().register(ID,"");
        this.getDataManager().register(TRUST,0);
        this.getDataManager().register(SKILL_LEVEL,1);
        this.getDataManager().register(SKILL_FIR_RANK,0);
        this.getDataManager().register(SKILL_SEC_RANK,0);
        this.getDataManager().register(SKILL_THI_RANK,0);
        this.getDataManager().register(SELECT_SKILL,1);
        this.getDataManager().register(ELITE,0);
        this.getDataManager().register(COUNTDOWN,getInitCountdown());
        this.getDataManager().register(SKILLING,false);
        this.getDataManager().register(TARGET,false);
        this.getDataManager().register(SKILL_TIME,0);
        this.getDataManager().register(SKILL_STARTUP,0);
        this.getDataManager().register(SKILL_POINT,0);
        this.getDataManager().register(TIMES,0);
        this.getDataManager().register(EXISTED_TIME,0);
        this.getDataManager().register(MOOD,24f);
        this.getDataManager().register(TRAIN_TIME,0);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        if(getMaster()!=null)
            compound.setString("Master", getMaster().getName());

        if(getUuid()!=null)
            compound.setString("UUID", getUuid());

        compound.setInteger("Countdown", getCountdown());
        compound.setInteger("Potential", getPotential());
        compound.setInteger("MoveMode", getMoveMode().value);
        compound.setInteger("ActionMode", getActionMode().value);
        compound.setInteger("Trust", getTrust());
        compound.setInteger("SkillLevel", getSkillLevel());
        compound.setInteger("SkillFirRank", getSkillFirRank());
        compound.setInteger("SkillSecRank", getSkillSecRank());
        compound.setInteger("SkillThiRank", getSkillThiRank());
        compound.setInteger("SelectSkill", getSelectSkill());
        compound.setInteger("Elite", getElite());
        compound.setInteger("Level", getLevel());
        compound.setInteger("Exp", getExp());
        compound.setInteger("SkillTime", getSkillTime());
        compound.setInteger("SkillPoint", getSkillPoint());
        compound.setBoolean("Skilling",isSkilling());
        compound.setInteger("Times",getTimes());
        compound.setInteger("ExistedTimes",getExistedTime());
        compound.setFloat("Mood",getMood());
        compound.setFloat("TrainTime",getTrainTime());
        compound.setInteger("TickExisted",getTickExisted());
    }

    @Override
    @ParametersAreNonnullByDefault
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if(compound.hasKey("Master") && !compound.getString("Master").equals(""))
            setMasterString(compound.getString("Master"));

        if(compound.hasKey("UUID") && !compound.getString("UUID").equals(""))
            setUuid(compound.getString("UUID"));

        setCountdown(compound.getInteger("Countdown"));
        setPotential(compound.getInteger("Potential"));
        setMoveMode(MoveMode.getMode(compound.getInteger("MoveMode")));
        setActionMode(ActionMode.getMode(compound.getInteger("ActionMode")));
        setTrust(compound.getInteger("Trust"));
        setSkillLevel(compound.getInteger("SkillLevel"));
        setSkillFirRank(compound.getInteger("SkillFirRank"));
        setSkillSecRank(compound.getInteger("SkillSecRank"));
        setSkillThiRank(compound.getInteger("SkillThiRank"));
        setSelectSkill(compound.getInteger("SelectSkill"));
        setElite(compound.getInteger("Elite"));
        setLevel(compound.getInteger("Level"));
        setExp(compound.getInteger("Exp"));
        setSkillTime(compound.getInteger("SkillTime"));
        setSkilling(compound.getBoolean("Skilling"));
        setSkillPoint(compound.getInteger("SkillPoint"));
        setTimes(compound.getInteger("Times"));
        setExistedTime(compound.getInteger("ExistedTimes"));
        setMood(compound.getFloat("Mood"));
        setTrainTime(compound.getInteger("TrainTime"));
        setTickExisted(compound.getInteger("TickExisted"));
    }

    public void playInteractiveSound()
    {

    }

    public void playFightingSound()
    {

    }

    public void playIdleSound()
    {

    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        playInteractiveSound();

        return true;
    }

    @Nonnull
    public SoundCategory getSoundCategory()
    {
        return SoundCategory.HOSTILE;
    }

    @Override
    public void onLivingUpdate()
    {
        this.updateArmSwingProgress();
        float f = this.getBrightness();

        if (f > 0.5F)
        {
            this.idleTime += 2;
        }

        super.onLivingUpdate();
    }

    public boolean isNormalGetAttackState()
    {
        return true;
    }

    public static void onAttackRecovery(LivingHurtEvent event)
    {
        DamageSource source = event.getSource();

        if(source.getTrueSource() instanceof Operator && !source.getTrueSource().world.isRemote)
        {
            Operator operator = (Operator) source.getTrueSource();
            if(operator.getSkills().get(operator.getSelectSkill() - 1).getRecoveryType()== Skill.RecoveryType.ATTACK)
                operator.setSkillPoint(operator.getSkillPoint()+1);
        }
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(getMasterString()!=null && !getMasterString().equals(""))
            setMaster(world.getPlayerEntityByName(getMasterString()));

        if (getTickExisted()!=0 && getTargetState() && EntityUtil.atSetIntervals(TICK, this, 1200 + chance * 120, 0, 0))
        {
            playFightingSound();
            chance = new Random().nextInt(10);
        }

        if (getTickExisted()!=0 && !getTargetState() && EntityUtil.atSetIntervals(TICK, this, 2400 + chance * 240, 0, 0))
        {
            playIdleSound();
            chance = new Random().nextInt(10);
        }

        if (getRoom() != null && (getDistance(getRoom().getPos().getX(), getRoom().getPos().getY(), getRoom().getPos().getZ()) > getRoom().getoDis() || !(world.getTileEntity(new BlockPos(getRoom().getPos().getX(), getRoom().getPos().getY(), getRoom().getPos().getZ())) instanceof MachineTile) || this.getActionMode() != ActionMode.WORK))
            setRoom(null, null);

        if (getRoomType() == BlockMachine.Type.TRAINING_ROOM)
        {
            BlockTrainingRoom.TileTrainingRoom trainingRoom = (BlockTrainingRoom.TileTrainingRoom) getRoom();

            if (trainingRoom != null)
            {
                if (getTrainTime() == 6)
                    setSkillRank(getSkillRank() + 1);

                if (getTrainTime() > 0 && ArkEntityUtil.getOperator(world,trainingRoom.getTrainer()) == this)
                {
                    Operator coach = ArkEntityUtil.getOperator(world,trainingRoom.getCoach());

                    if (coach != null && coach != this)
                    {
                        setTrainTime((int) (getTrainTime() - 1 + coach.getLogSkillRatio()));

                    }
                    else
                    {
                        setTrainTime(getTrainTime() - 1);
                    }
                }
            }
        }

        if (getMood() > 24) setMood(24);

        setExistedTime(getExistedTime() + 1);

        if (getSkills().get(getSelectSkill() - 1) != null && EntityUtil.atSetIntervals(TICK, this, 20, 0, 0) && getSkills().get(getSelectSkill() - 1).getRecoveryType()== Skill.RecoveryType.AUTO)
        {
            if (getSkillPoint() < getSkills().get(getSelectSkill() - 1).getConsumePoint() && getSkillTime() == 0)
                setSkillPoint(getSkillPoint() + 1);
        }

        if (getCountdown() > 0)
            setCountdown(getCountdown() - 1);

        if (getSkillTime() > 0)
            setSkillTime(getSkillTime() - 1);

        if (getSkillStartup() > 0)
            setSkillStartup(getSkillStartup() - 1);

        if (!world.isRemote)
            setTickExisted(getTickExisted() + 1);

        if (getMoveMode() == MoveMode.FOLLOW && getMaster() != null && this.getDistance(getMaster()) > 32 && world == getMaster().world)
            setPosition(getMaster().posX, getMaster().posY, getMaster().posZ);

        if (getMoveMode() == MoveMode.FOLLOW && getMaster() != null && world != getMaster().world)
        {
            changeDimension(getMaster().dimension);
            setPosition(getMaster().posX, getMaster().posY, getMaster().posZ);
        }

        if (isNormalGetAttackTarget())
        {
            if (!world.isRemote)
            {
                getDataManager().set(TARGET, getAttackTarget() != null && getActionMode() != ActionMode.NONE && getActionMode() != ActionMode.WORK);
            }
        }

        if (isNormalGetAttackState())
        {
            if (!world.isRemote)
            {
                getDataManager().set(ATTACK, distanceToTarget(this) <= (double) getAttackRange() && getActionMode() != ActionMode.NONE && getActionMode() != ActionMode.WORK);
            }
        }

        if (!world.isRemote) setDistance((float) distanceToTarget(this));

        if (getPotential() > 6)
            setPotential(6);

        if (getExp() >= getExpNeed() && EntityUtil.atSetIntervals(TICK, this, 2, 0, 0))
        {
            setLevel(getLevel() + 1);
            setExp(getExp() - getExpNeed());
            attributeLevelUp();
        }

        if (getElite() == 0)
        {
            setExpNeed(100 + (getLevel() - 1) * 17);
        }

        if (getElite() == 1)
        {
            setExpNeed(100 + (getLevel() - 1) * 52);
        }

        if (getElite() == 2)
        {
            setExpNeed(100 + (getLevel() - 1) * 112);
        }

        if (getTrust() > 100)
            setTrust(100);

        if ((getElite() == 0 && getLevel() > 50)) setLevel(50);

        if (getElite() == 1 && getLevel() > 60 && getRarity() == Operator.Rarity.IV) setLevel(60);

        if (getElite() == 1 && getLevel() > 70 && getRarity() == Operator.Rarity.V) setLevel(70);

        if (getElite() == 1 && getLevel() < 80 && getRarity() == Operator.Rarity.VI) setLevel(80);

        if (getElite() == 2 && getLevel() > 70 && getRarity() == Operator.Rarity.IV) setLevel(70);

        if (getElite() == 2 && getLevel() > 80 && getRarity() == Operator.Rarity.V) setLevel(80);

        if (getElite() == 2 && getLevel() > 90 && getRarity() == Operator.Rarity.VI) setLevel(90);
    }

    public void applyDefencePowerModifier()
    {
        if (getDP() != 0)
        {
            defencePowerModifiedTemp = new AttributeModifier(UUID.fromString("7dd688ff-703c-4af6-a18f-82164afbb8a7"), "Temp defence power", getDP(), 0);

            if(defensivePower.hasModifier(defencePowerModifiedTemp))
                defensivePower.removeModifier(defencePowerModifiedTemp);

            defensivePower.applyModifier(defencePowerModifiedTemp);
        }
    }

    public void removeDefencePowerModifier()
    {
        defencePowerModifiedTemp = new AttributeModifier(UUID.fromString("7dd688ff-703c-4af6-a18f-82164afbb8a7"), "Temp defence power", getDP(), 0);

        if(defensivePower.hasModifier(defencePowerModifiedTemp))
            defensivePower.removeModifier(defencePowerModifiedTemp);
    }

    public void applySpellResistanceModifier()
    {
        if (getSR() != 0)
        {
            spellResistanceModifiedTemp = new AttributeModifier(UUID.fromString("68cf4de1-9c3c-4b54-9ca2-b80ca5412e2c"), "Temp spell resistance", getSR(), 0);

            if(spellResistance.hasModifier(spellResistanceModifiedTemp))
                spellResistance.removeModifier(spellResistanceModifiedTemp);

            spellResistance.applyModifier(spellResistanceModifiedTemp);
        }
    }

    public void removeSpellResistanceModifier()
    {
        spellResistanceModifiedTemp = new AttributeModifier(UUID.fromString("68cf4de1-9c3c-4b54-9ca2-b80ca5412e2c"), "Temp spell resistance", getSR(), 0);

        if(spellResistance.hasModifier(spellResistanceModifiedTemp))
            spellResistance.removeModifier(spellResistanceModifiedTemp);
    }

    public void applyMaxHealthModifier()
    {
        if (getHP() != 0)
        {
            maxHealthModifiedTemp = new AttributeModifier(UUID.fromString("81446959-8478-43d2-b00a-976c9656166f"), "Temp max health", getHP(), 0);

            if(maxHealth.hasModifier(maxHealthModifiedTemp))
                maxHealth.removeModifier(maxHealthModifiedTemp);

            maxHealth.applyModifier(maxHealthModifiedTemp);
        }
    }

    public void removeMaxHealthModifier()
    {
        maxHealthModifiedTemp = new AttributeModifier(UUID.fromString("81446959-8478-43d2-b00a-976c9656166f"), "Temp max health", getHP(), 0);

        if(maxHealth.hasModifier(maxHealthModifiedTemp))
            maxHealth.removeModifier(maxHealthModifiedTemp);
    }

    public void applyMoveSpeedModifier()
    {
        if (getMS() != 0)
        {
            moveSpeedModifiedTemp = new AttributeModifier(UUID.fromString("10f7e3f3-37d6-4de4-b62b-7f1df4cb1c1a"), "Temp move speed", getMS(), 0);

            if(moveSpeed.hasModifier(moveSpeedModifiedTemp))
                moveSpeed.removeModifier(moveSpeedModifiedTemp);

            moveSpeed.applyModifier(moveSpeedModifiedTemp);
        }
    }

    public void removeMoveSpeedModifier()
    {
        moveSpeedModifiedTemp = new AttributeModifier(UUID.fromString("10f7e3f3-37d6-4de4-b62b-7f1df4cb1c1a"), "Temp move speed", getMS(), 0);

        if(moveSpeed.hasModifier(moveSpeedModifiedTemp))
            moveSpeed.removeModifier(moveSpeedModifiedTemp);
    }

    public void applyAttackDamageModifier()
    {
        if (getAD() != 0)
        {
            attackDamageModifiedTemp = new AttributeModifier(UUID.fromString("13b6bbd2-76b1-4c8a-b65e-f7a6ce069908"), "Temp attack damage", getAD(), 0);

            if(attackDamage.hasModifier(attackDamageModifiedTemp))
                attackDamage.removeModifier(attackDamageModifiedTemp);

            attackDamage.applyModifier(attackDamageModifiedTemp);
        }
    }

    public void removeAttackDamageModifier()
    {
        attackDamageModifiedTemp = new AttributeModifier(UUID.fromString("13b6bbd2-76b1-4c8a-b65e-f7a6ce069908"), "Temp attack damage", getAD(), 0);

        if(attackDamage.hasModifier(attackDamageModifiedTemp))
            attackDamage.removeModifier(attackDamageModifiedTemp);
    }

    public void applyAttackIntervalModifier()
    {
        if (getAI() != 0)
        {
            attackIntervalModifiedTemp = new AttributeModifier(UUID.fromString("97d695bd-7b84-46cc-8b5b-a456f9903c08"), "Temp attack interval", getAI(), 0);

            if(attackInterval.hasModifier(attackIntervalModifiedTemp))
                attackInterval.removeModifier(attackIntervalModifiedTemp);

            attackInterval.applyModifier(attackIntervalModifiedTemp);
        }
    }

    public void removeAttackIntervalModifier()
    {
        attackIntervalModifiedTemp = new AttributeModifier(UUID.fromString("97d695bd-7b84-46cc-8b5b-a456f9903c08"), "Temp attack interval", getAI(), 0);

        if(attackInterval.hasModifier(attackIntervalModifiedTemp))
            attackInterval.removeModifier(attackIntervalModifiedTemp);
    }

    public void applyAttackRangeModifier()
    {
        if (getAR() != 0)
        {
            attackRangeModifiedTemp = new AttributeModifier(UUID.fromString("4d2a613f-bb4e-464d-9054-b69a82d6d921"), "Temp attack range", getAR(), 0);

            if(attackRange.hasModifier(attackRangeModifiedTemp))
                attackRange.removeModifier(attackRangeModifiedTemp);

            attackRange.applyModifier(attackRangeModifiedTemp);
        }
    }

    public void removeAttackRangeModifier()
    {
        attackRangeModifiedTemp = new AttributeModifier(UUID.fromString("4d2a613f-bb4e-464d-9054-b69a82d6d921"), "Temp attack range", getAR(), 0);

        if(attackRange.hasModifier(attackRangeModifiedTemp))
            attackRange.removeModifier(attackRangeModifiedTemp);
    }

    public void attributeLevelUp()
    {

    }

    public boolean isNormalGetAttackTarget()
    {
        return true;
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }

    @Nonnull
    protected SoundEvent getSwimSound()
    {
        return SoundEvents.ENTITY_HOSTILE_SWIM;
    }

    @Nonnull
    protected SoundEvent getSplashSound()
    {
        return SoundEvents.ENTITY_HOSTILE_SPLASH;
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount)
    {
        return !this.isEntityInvulnerable(source) && super.attackEntityFrom(source, amount);
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_HOSTILE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundRegistry.OPERATOR_DEATH;
    }

    @Nonnull
    protected SoundEvent getFallSound(int heightIn)
    {
        return heightIn > 4 ? SoundEvents.ENTITY_HOSTILE_BIG_FALL : SoundEvents.ENTITY_HOSTILE_SMALL_FALL;
    }

    @Override
    public boolean attackEntityAsMob(@Nonnull Entity entityIn)
    {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        int i = 0;

        if (entityIn instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {
            if (i > 0)
            {
                ((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, MathHelper.sin(this.rotationYaw * 0.017453292F), -MathHelper.cos(this.rotationYaw * 0.017453292F));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                entityIn.setFire(j * 4);
            }

            if (entityIn instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entityIn;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this) && itemstack1.getItem().isShield(itemstack1, entityplayer))
                {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1)
                    {
                        entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
                        this.world.setEntityState(entityplayer, (byte)30);
                    }
                }
            }

            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    public float getBlockPathWeight(@Nonnull BlockPos pos)
    {
        return 0.5F - this.world.getLightBrightness(pos);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(@Nonnull DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        if(getSkills().get(getSelectSkill()-1)!=null)
            setSkillPoint(getSkills().get(getSelectSkill()-1).getInitPoint());

        setTimes(getTimes()+1);

        this.playSound(SoundRegistry.OPERATOR_PLACE,1,1);

        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    protected boolean canDropLoot()
    {
        return true;
    }

    @Override
    public void commonExecute(Operator wlm, EntityLivingBase target)
    {

    }

    @Override
    public void closeExecute(Operator wlm, EntityLivingBase target)
    {

    }

    @Override
    public void remoteExecute(Operator wlm, EntityLivingBase target)
    {

    }

    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureEntities(id);
    }

    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo(id);
    }

    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation(id);
    }


    public void setRoom(@Nullable BlockMachine.Type type, @Nullable MachineTile tile)
    {
        if(type==null || tile==null)
        {
            this.roomType = null;
            this.room = null;
        }
        else if(tile.getBlockType().getRegistryName().getResourcePath().equals(type.getName()))
        {
            this.roomType = type;
            this.room = tile;
        }
    }

    public void potentialUpFunction()
    {

    }

    public void setTickExisted(int tick)
    {
        this.getDataManager().set(TICK,tick);
    }

    public int getTickExisted()
    {
        return this.getDataManager().get(TICK);
    }

    public MachineTile getRoom()
    {
        return room;
    }

    public BlockMachine.Type getRoomType()
    {
        return roomType;
    }

    public void setMood(float mood)
    {
        this.getDataManager().set(MOOD,mood);
    }

    public float getMood()
    {
        return this.getDataManager().get(MOOD);
    }

    public void setTimes(int times)
    {
        this.getDataManager().set(TIMES,times);
    }

    public int getTimes()
    {
        return this.getDataManager().get(TIMES);
    }

    public void setExistedTime(int e)
    {
        this.getDataManager().set(EXISTED_TIME,e);
    }

    public int getExistedTime()
    {
        return this.getDataManager().get(EXISTED_TIME);
    }

    public boolean getTargetState()
    {
        return this.getDataManager().get(TARGET);
    }

    public void setTargetState(boolean t)
    {
        this.getDataManager().set(TARGET,t);
    }

    public float getDefensivePower()
    {
        return (float) this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).getAttributeValue();
    }

    public float getSpellResistance()
    {
        return (float) this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).getAttributeValue();
    }

    public float getAttackDamage()
    {
        return (float) this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).getAttributeValue();
    }

    public float getAttackRange()
    {
        return (float) this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).getAttributeValue();
    }

    public float getMoveSpeed()
    {
        return (float) this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).getAttributeValue();
    }

    public int getAttackInterval()
    {
        return (int) this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).getAttributeValue();
    }

    public int getKnockbackResistance()
    {
        return (int) this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).getAttributeValue();
    }

    public void setMaster(@Nullable EntityPlayer master)
    {
        if(master !=null)
            this.dataManager.set(MASTER,master.getName());
    }

    public EntityPlayer getMaster()
    {
        if(!this.getDataManager().get(MASTER).equals(""))
            return world.getPlayerEntityByName(this.getDataManager().get(MASTER));
        else
            return null;
    }

    public void setMasterString(String master)
    {
        this.dataManager.set(MASTER,master);
    }

    public String getMasterString()
    {
        return this.getDataManager().get(MASTER);
    }

    public int getSkillRank()
    {
        if(getSelectSkill()==1)
            return getSkillFirRank();
        if(getSelectSkill()==2)
            return getSkillSecRank();
        if(getSelectSkill()==3)
            return getSkillSecRank();

        return 0;
    }

    public void setSkillRank(int v)
    {
        if(getSelectSkill()==1)
            setSkillFirRank(v);
        if(getSelectSkill()==2)
            setSkillSecRank(v);
        if(getSelectSkill()==3)
            setSkillSecRank(v);
    }

    public boolean shouldKeepDistance()
    {
        return true;
    }

    public float getLogSkillRatio()
    {
        return 0;
    }

    public int getTrainTime()
    {
        return this.getDataManager().get(TRAIN_TIME);
    }

    public void setTrainTime(int time)
    {
        this.getDataManager().set(TRAIN_TIME,time);
    }

    public Access getAccess()
    {
        return access;
    }

    public Position getPositionOperator()
    {
        return position;
    }

    public Gender getGender()
    {
        return gender;
    }

    public Rarity getRarity()
    {
        return rarity;
    }

    public Class getOperatorClass()
    {
        return operatorClass;
    }

    public int getResistNumber()
    {
        return this.getDataManager().get(RESIST_NUMBER);
    }

    public void setResistNumber(int resistNumber)
    {
        this.getDataManager().set(RESIST_NUMBER,resistNumber);
    }

    public int getRedeployTime()
    {
        return this.getDataManager().get(REDEPLOY_TIME);
    }

    public void setRedeployTime(int redeployTime)
    {
        this.getDataManager().set(REDEPLOY_TIME,redeployTime);
    }

    public int getDeployPoint()
    {
        return this.getDataManager().get(DEPLOY_POINT);
    }

    public void setDeployPoint(int deployPoint)
    {
        this.getDataManager().set(DEPLOY_POINT,deployPoint);
    }

    public int getPotential()
    {
        return this.getDataManager().get(POTENTIAL);
    }

    public void setPotential(int potential)
    {
        this.getDataManager().set(POTENTIAL,potential);
    }

    public float getDistance()
    {
        return this.getDataManager().get(DISTANCE);
    }

    protected void setDistance(float d)
    {
        this.getDataManager().set(DISTANCE, d);
    }

    public int getCountdown()
    {
        return this.getDataManager().get(COUNTDOWN);
    }

    public void setCountdown(int countdown)
    {
        this.getDataManager().set(COUNTDOWN, countdown);
    }

    public int getSkillStartup()
    {
        return this.getDataManager().get(SKILL_STARTUP);
    }

    public void setSkillStartup(int startup)
    {
        this.getDataManager().set(SKILL_STARTUP,startup);
    }

    public int getSkillTime()
    {
        return this.getDataManager().get(SKILL_TIME);
    }

    public void setSkillTime(int skillTime)
    {
        this.getDataManager().set(SKILL_TIME,skillTime);
    }

    public boolean getAttackState() {return this.getDataManager().get(ATTACK);}

    public void setAttackState(boolean state) {this.getDataManager().set(ATTACK,state);}

    public void setAR(float percent){this.getDataManager().set(AR,percent*((float)getEntityAttribute(WLMAttributes.ATTACK_RANGE).getAttributeValue()));}

    public void addAR(float amount){this.getDataManager().set(AR,amount);}

    public float getAR(){return this.getDataManager().get(AR);}

    public void setAI(float percent){this.getDataManager().set(AI,(int)(percent*getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).getAttributeValue()));}

    public void addAI(int amount){this.getDataManager().set(AI,amount);}

    public float getAI(){return this.getDataManager().get(AI);}

    public void setAD(float percent){this.getDataManager().set(AD,percent*((float)getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).getAttributeValue()));}

    public void addAD(float amount){this.getDataManager().set(AD,amount);}

    public float getAD(){return this.getDataManager().get(AD);}

    public void setSR(float percent){this.getDataManager().set(SR,percent*((float)getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).getAttributeValue()));}

    public void addSR(float amount){this.getDataManager().set(SR,amount);}

    public float getSR(){return this.getDataManager().get(SR);}

    public void setDP(float percent){this.getDataManager().set(DP,percent*((float)getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).getAttributeValue()));}

    public void addDP(float amount){this.getDataManager().set(DP,amount);}

    public float getDP(){return this.getDataManager().get(DP);}

    public void setMS(float percent){this.getDataManager().set(MS,percent*((float)getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).getAttributeValue()));}

    public void addMS(float amount){this.getDataManager().set(MS,amount);}

    public float getMS(){return this.getDataManager().get(MS);}

    public void setHP(float percent){this.getDataManager().set(HP,percent*((float)getEntityAttribute(WLMAttributes.MAX_HEALTH).getAttributeValue()));}

    public void addHP(float amount){this.getDataManager().set(HP,amount);}

    public float getHP(){return this.getDataManager().get(HP);}

    public void setFreezeResistance(boolean b)
    {
        this.freezeResistance = b;
    }

    public void setSleepResistance(boolean b)
    {
        this.sleepResistance = b;
    }

    public void setStunResistance(boolean b)
    {
        this.stunResistance = b;
    }

    public void setSilenceResistance(boolean b)
    {
        this.silenceResistance = b;
    }

    public boolean getFreezeResistance()
    {
        return this.freezeResistance;
    }

    public boolean getSleepResistance()
    {
        return this.sleepResistance;
    }

    public boolean getStunResistance()
    {
        return this.stunResistance;
    }

    public boolean getSilenceResistance()
    {
        return this.silenceResistance;
    }

    public void setInitCountdown(int n)
    {
        this.countdown = n;
    }

    public int getInitCountdown()
    {
        return this.countdown;
    }

    public boolean shouldExecute()
    {
        return true;
    }

    public boolean isTryMoveToEntityLiving()
    {
        return true;
    }

    public void setExpNeed(int expNeed)
    {
        this.getDataManager().set(EXP_NEED,expNeed);
    }

    public int getExpNeed()
    {
        return this.getDataManager().get(EXP_NEED);
    }

    public void setExp(int exp)
    {
        this.getDataManager().set(EXP,exp);
    }

    public int getExp()
    {
        return this.getDataManager().get(EXP);
    }

    public void setUuid(String uuid)
    {
        this.getDataManager().set(ID, uuid);
    }

    public String getUuid()
    {
        return this.getDataManager().get(ID);
    }

    public int getLevel()
    {
        return this.getDataManager().get(LEVEL);
    }

    public void setLevel(int level)
    {
        this.getDataManager().set(LEVEL,level);
    }

    public MoveMode getMoveMode()
    {
        return MoveMode.getMode(this.getDataManager().get(MOVE_MODE));
    }

    public void setMoveMode(MoveMode moveMode)
    {
        this.getDataManager().set(MOVE_MODE,moveMode.value);
    }

    public ActionMode getActionMode()
    {
        return ActionMode.getMode(this.getDataManager().get(ACTION_MODE));
    }

    public void setActionMode(ActionMode actionMode)
    {
        this.getDataManager().set(ACTION_MODE,actionMode.value);
    }

    public int getTrust()
    {
        return this.getDataManager().get(TRUST);
    }

    public void setTrust(int trust)
    {
        this.getDataManager().set(TRUST,trust);
    }

    public void setSkillLevel(int skillLevel)
    {
        this.getDataManager().set(SKILL_LEVEL,skillLevel);
    }

    public int getSkillLevel()
    {
        return this.getDataManager().get(SKILL_LEVEL);
    }

    public int getSkillThiRank()
    {
        return this.getDataManager().get(SKILL_THI_RANK);
    }

    public void setSkillFirRank(int s)
    {
        this.getDataManager().set(SKILL_FIR_RANK,s);
    }

    public int getSkillSecRank()
    {
        return this.getDataManager().get(SKILL_SEC_RANK);
    }

    public void setSkillSecRank(int s)
    {
        this.getDataManager().set(SKILL_SEC_RANK,s);
    }

    public int getSkillFirRank()
    {
        return this.getDataManager().get(SKILL_FIR_RANK);
    }

    public void setSkillThiRank(int s)
    {
        this.getDataManager().set(SKILL_THI_RANK,s);
    }

    public int getSelectSkill()
    {
        return this.dataManager.get(SELECT_SKILL);
    }

    public void setSelectSkill(int s)
    {
        if(getTrainTime()<=0)
            this.dataManager.set(SELECT_SKILL,s);
    }

    public int getElite()
    {
        return this.getDataManager().get(ELITE);
    }

    public int getSkillPoint()
    {
        return this.getDataManager().get(SKILL_POINT);
    }

    public void setSkillPoint(int point)
    {
        this.getDataManager().set(SKILL_POINT,point);
    }

    public void setElite(int e)
    {
        this.dataManager.set(ELITE,e);
    }

    public boolean isRightRange()
    {
        return this.getDistance() <= this.getAttackRange();
    }

    public boolean isSkilling()
    {
        return this.getDataManager().get(SKILLING);
    }

    public void setSkilling(boolean s)
    {
        this.getDataManager().set(SKILLING,s);
    }

    public void setLogistics(String logistics)
    {
        this.logistics = logistics;
    }

    public String getLogistics()
    {
        return logistics;
    }

    public void setGiftSec(String giftSec)
    {
        this.giftSec = giftSec;
    }

    public String getGiftSec()
    {
        return giftSec;
    }

    public void setGiftFir(String giftFir)
    {
        this.giftFir = giftFir;
    }

    public String getGiftFir()
    {
        return giftFir;
    }

    public enum Class
    {
        CASTER,
        DEFENDER,
        GUARD,
        MEDIC,
        SNIPER,
        SPECIALIST,
        SUPPORTER,
        VANGUARD
    }

    public enum Rarity
    {
        I(1),
        II(2),
        III(3),
        IV(4),
        V(5),
        VI(6),
        VII(7);

        final int value;

        Rarity(int v)
        {
            value = v;
        }
    }

    public enum Position
    {
        MELEE,
        RANGE
    }

    public enum Gender
    {
        MAN,
        WOMAN,
        OTHER
    }

    public enum Access
    {
        RECRUIT,
        HEADHUNT,
        RECHUNT,
        ACTIVITY,
        OTHER
    }

    public enum Tag
    {
        HEALING,
        SUPPORT,
        DPS,
        AOE,
        SLOW,
        SURVIVAL,
        DEFENCE,
        WEAKEN,
        SHIFT,
        CROWD_CONTROL,
        NUKER,
        SUMMON,
        FAST_REDEPLOY,
        DP_RECOVERY,
        ROBOT,
        MELEE,
        RANGE
    }

    public enum MoveMode
    {
        IDLE(0),
        WANDER(1),
        FOLLOW(2);

        final int value;

        MoveMode(int v)
        {
            value = v;
        }

        public static MoveMode getMode(int value)
        {
            MoveMode[] modes = {IDLE,WANDER,FOLLOW};
            for(MoveMode mode : modes)
            {
                if(mode.value==value)
                {
                    return mode;
                }
            }
            return IDLE;
        }

        public int getValue()
        {
            return value;
        }
    }

    public enum ActionMode
    {
        NONE(0),
        FIGHTBACK(1),
        PROTECTIVE(2),
        SIGN(3),
        ENEMY(4),
        MOB(5),
        ALL(6),
        WORK(7),
        HEALING(8);

        final int value;

        ActionMode(int v)
        {
            value = v;
        }


        public static ActionMode getMode(int value)
        {
            ActionMode[] modes = {NONE,FIGHTBACK,PROTECTIVE,SIGN,ENEMY,MOB,ALL,WORK,HEALING};
            for(ActionMode mode : modes)
            {
                if(mode.value==value)
                {
                    return mode;
                }
            }
            return NONE;
        }

        public int getValue()
        {
            return value;
        }
    }

    public static class Bar
    {
        ArkConfig.Common.Bar bar = ArkConfig.common.bar;

        @SubscribeEvent
        public void onRenderBar(RenderWorldLastEvent event)
        {
            Minecraft mc = Minecraft.getMinecraft();
            if ((bar.renderInF1 || Minecraft.isGuiEnabled()) && bar.draw)
            {
                Entity cameraEntity = mc.getRenderViewEntity();
                Frustum frustum = new Frustum();
                float partialTicks = event.getPartialTicks();
                double viewX = cameraEntity.lastTickPosX + (cameraEntity.posX - cameraEntity.lastTickPosX) * (double) partialTicks;
                double viewY = cameraEntity.lastTickPosY + (cameraEntity.posY - cameraEntity.lastTickPosY) * (double) partialTicks;
                double viewZ = cameraEntity.lastTickPosZ + (cameraEntity.posZ - cameraEntity.lastTickPosZ) * (double) partialTicks;
                frustum.setPosition(viewX, viewY, viewZ);
                if (bar.showOnlyFocused)
                {
                    Entity focused = getEntityLookedAt(mc.player);
                    if (focused instanceof EntityLivingBase && focused.isEntityAlive() && !(focused instanceof WLA || focused instanceof WLT))
                    {
                        renderCommonBar((EntityLivingBase) focused, partialTicks, cameraEntity);

                        if(focused instanceof Operator)
                        {
                            if(((Operator) focused).getActionMode()==ActionMode.WORK)
                                renderBelowBar((Operator) focused, partialTicks, cameraEntity,255,255,255,((Operator) focused).getSkills().get(((Operator) focused).getSelectSkill()-1).getConsumePoint(),((Operator) focused).getSkillPoint());
                            else
                                renderBelowBar((Operator) focused, partialTicks, cameraEntity,178,200,78,((Operator) focused).getSkills().get(((Operator) focused).getSelectSkill()-1).getConsumePoint(),((Operator) focused).getSkillPoint());
                        }
                    }
                }
                else
                {
                    WorldClient client = mc.world;
                    Set<Entity> entities = ReflectionHelper.getPrivateValue(WorldClient.class, client, "entityList","field_73032_d");
                    for (Entity entity : entities)
                    {
                        if (entity instanceof EntityLivingBase && entity.isEntityAlive() && entity.getRecursivePassengers().isEmpty()
                                && !entity.ignoreFrustumCheck && !(entity instanceof WLA || entity instanceof WLT))
                        {
                            if(((!bar.notShowAnimal && entity instanceof EntityAnimal) ||
                                    (!bar.notShowPlayer && entity instanceof EntityPlayer) ||
                                    (!bar.notShowBoss && entity instanceof EntityMob && !entity.isNonBoss()) ||
                                    (!bar.notShowMob && entity instanceof EntityMob) ||
                                    (!bar.notShowOperator && entity instanceof Operator)) && !entity.isInvisible())
                            renderCommonBar((EntityLivingBase) entity, partialTicks, cameraEntity);

                            if(entity instanceof Operator)
                            {
                                if(((Operator) entity).getActionMode()==ActionMode.WORK)
                                    renderBelowBar((Operator) entity, partialTicks, cameraEntity,255,255,255,((Operator) entity).getMood(),24);
                                else
                                    renderBelowBar((Operator) entity, partialTicks, cameraEntity,178,200,78,((Operator) entity).getSkills().get(((Operator) entity).getSelectSkill()-1).getConsumePoint(),((Operator) entity).getSkillPoint());
                            }
                        }
                    }
                }
            }
        }

        public void renderBelowBar(Operator passedEntity, float partialTicks, Entity viewPoint, int r, int g, int b, float all, float value)
        {
            Stack<Operator> ridingStack = new Stack();
            Operator entity = passedEntity;
            ridingStack.push(passedEntity);

            while (entity.getRidingEntity() != null && entity.getRidingEntity() instanceof Operator)
            {
                entity = (Operator) entity.getRidingEntity();
                ridingStack.push(entity);
            }

            float pastTranslate = 0.0F;

            while (true)
            {
                double x;
                double y;
                double z;
                float scale;
                float t;
                float v;
                do
                {

                    do
                    {
                        float distance;
                        do
                        {
                            do
                            {
                                if (ridingStack.isEmpty())
                                {
                                    return;
                                }
                                entity = ridingStack.pop();
                                distance = passedEntity.getDistance(viewPoint);
                            }
                            while (distance > (float) bar.maxDistance);
                        }
                        while (!passedEntity.canEntityBeSeen(viewPoint));
                    }
                    while (entity.isInvisible());

                    x = passedEntity.lastTickPosX + (passedEntity.posX - passedEntity.lastTickPosX) * (double) partialTicks;
                    y = passedEntity.lastTickPosY + (passedEntity.posY - passedEntity.lastTickPosY) * (double) partialTicks;
                    z = passedEntity.lastTickPosZ + (passedEntity.posZ - passedEntity.lastTickPosZ) * (double) partialTicks;
                    scale = 0.026666673F;
                    t = all;
                    v = Math.min(t, value);
                }
                while (t <= 0.0F);

                RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
                GlStateManager.pushMatrix();
                GlStateManager.translate((float) (x - renderManager.viewerPosX), (float) (y - renderManager.viewerPosY + (double) passedEntity.height + bar.heightAbove-0.05), (float) (z - renderManager.viewerPosZ));
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                GlStateManager.scale(-scale, -scale, scale);
                boolean lighting = GL11.glGetBoolean(2896);
                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();
                int barHeight = bar.barHeight;
                float size = (float) bar.barLength;
                float s = 0.5F;
                GlStateManager.translate(0.0F, pastTranslate, 0.0F);
                float skillSize = size * (v / t);
                buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                buffer.pos(-size, 0.0D, 0.0D).color(127, 127, 127, 127).endVertex();
                buffer.pos(-size, barHeight, 0.0D).color(127, 127, 127, 127).endVertex();
                buffer.pos(size, barHeight, 0.0D).color(127, 127, 127, 127).endVertex();
                buffer.pos(size, 0.0D, 0.0D).color(127, 127, 127, 127).endVertex();
                tessellator.draw();
                buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                buffer.pos(-size, 0.0D, 0.0D).color(r, g, b, 127).endVertex();
                buffer.pos(-size, barHeight, 0.0D).color(r, g, b, 127).endVertex();
                buffer.pos(skillSize * 2.0F - size, barHeight, 0.0D).color(r, g, b, 127).endVertex();
                buffer.pos(skillSize * 2.0F - size, 0.0D, 0.0D).color(r, g, b, 127).endVertex();
                tessellator.draw();
                GlStateManager.enableTexture2D();
                GlStateManager.pushMatrix();
                GlStateManager.translate(-size, -4.5F, 0.0F);
                GlStateManager.scale(s, s, s);
                GlStateManager.pushMatrix();
                GlStateManager.popMatrix();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                float s1 = 0.5F;
                GlStateManager.scale(s1, s1, s1);
                GlStateManager.translate(size / (s * s1) * 2.0F - 16.0F, 0.0F, 0.0F);
                GlStateManager.popMatrix();
                GlStateManager.disableBlend();
                GlStateManager.enableDepth();
                GlStateManager.depthMask(true);
                if (lighting)
                {
                    GlStateManager.enableLighting();
                }
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.popMatrix();
                pastTranslate -= (float) barHeight;
            }
        }

        public void renderCommonBar(EntityLivingBase passedEntity, float partialTicks, Entity viewPoint)
        {
            Stack<EntityLivingBase> ridingStack = new Stack();
            EntityLivingBase entity = passedEntity;
            ridingStack.push(passedEntity);

            while (entity.getRidingEntity() != null && entity.getRidingEntity() instanceof EntityLivingBase)
            {
                entity = (EntityLivingBase) entity.getRidingEntity();
                ridingStack.push(entity);
            }

            float pastTranslate = 0.0F;

            while (true)
            {
                double x;
                double y;
                double z;
                float scale;
                float maxHealth;
                float health;
                do
                {
                    do
                    {
                        float distance;
                        do
                        {
                            do
                            {
                                if (ridingStack.isEmpty())
                                {
                                    return;
                                }
                                entity = ridingStack.pop();
                                distance = passedEntity.getDistance(viewPoint);
                            }
                            while (distance > (float) bar.maxDistance);
                        }
                        while (!passedEntity.canEntityBeSeen(viewPoint));
                    }
                    while (entity.isInvisible());

                    x = passedEntity.lastTickPosX + (passedEntity.posX - passedEntity.lastTickPosX) * (double) partialTicks;
                    y = passedEntity.lastTickPosY + (passedEntity.posY - passedEntity.lastTickPosY) * (double) partialTicks;
                    z = passedEntity.lastTickPosZ + (passedEntity.posZ - passedEntity.lastTickPosZ) * (double) partialTicks;
                    scale = 0.026666673F;
                    maxHealth = entity.getMaxHealth();
                    health = Math.min(maxHealth, entity.getHealth());
                }
                while (maxHealth <= 0.0F);

                RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
                GlStateManager.pushMatrix();
                GlStateManager.translate((float) (x - renderManager.viewerPosX), (float) (y - renderManager.viewerPosY + (double) passedEntity.height + bar.heightAbove), (float) (z - renderManager.viewerPosZ));
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                GlStateManager.scale(-scale, -scale, scale);
                boolean lighting = GL11.glGetBoolean(2896);
                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();
                int barHeight = bar.barHeight;
                float size = (float) bar.barLength;
                float s = 0.5F;
                int r = 0;
                int g = 255;
                int b = 0;

                if (entity instanceof IMob)
                {
                    r = 255;
                    g = 0;
                }

                if (entity instanceof Operator)
                {
                    r = 115;
                    g = 188;
                    b = 216;
                }

                GlStateManager.translate(0.0F, pastTranslate, 0.0F);

                float healthSize = size * (health / maxHealth);

                buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                buffer.pos(-size, 0.0D, 0.0D).color(127, 127, 127, 127).endVertex();
                buffer.pos(-size, barHeight, 0.0D).color(127, 127, 127, 127).endVertex();
                buffer.pos(size, barHeight, 0.0D).color(127, 127, 127, 127).endVertex();
                buffer.pos(size, 0.0D, 0.0D).color(127, 127, 127, 127).endVertex();
                tessellator.draw();
                buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                buffer.pos(-size, 0.0D, 0.0D).color(r, g, b, 127).endVertex();
                buffer.pos(-size, barHeight, 0.0D).color(r, g, b, 127).endVertex();
                buffer.pos(healthSize * 2.0F - size, barHeight, 0.0D).color(r, g, b, 127).endVertex();
                buffer.pos(healthSize * 2.0F - size, 0.0D, 0.0D).color(r, g, b, 127).endVertex();
                tessellator.draw();
                GlStateManager.enableTexture2D();
                GlStateManager.pushMatrix();
                GlStateManager.translate(-size, -4.5F, 0.0F);
                GlStateManager.scale(s, s, s);
                GlStateManager.pushMatrix();
                GlStateManager.popMatrix();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                float s1 = 0.5F;
                GlStateManager.scale(s1, s1, s1);
                GlStateManager.translate(size / (s * s1) * 2.0F - 16.0F, 0.0F, 0.0F);
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/" + "cold" + ".png"));
                if(entity.hasCapability(CapabilityRegistry.capState,null))
                {
                    if(entity.hasCapability(CapabilityRegistry.capState,null))
                    {
                        CapabilityState state = entity.getCapability(CapabilityRegistry.capState,null);
                        List<EnumState> states = state.getStates();
                        for(int i = 0; i<=states.size()-1&&states.size()>0; i++)
                        {
                            if(states.get(i).getTick()>0 && states.get(i)!=EnumState.DEFAULT)
                            {
                                renderStateIcon((int) (-size*7.4+i*16),0,16,16,states.get(i).toString());
                            }
                        }
                    }
                }

                GlStateManager.popMatrix();
                GlStateManager.disableBlend();
                GlStateManager.enableDepth();
                GlStateManager.depthMask(true);
                if (lighting)
                {
                    GlStateManager.enableLighting();
                }

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.popMatrix();
                pastTranslate -= (float) barHeight;
            }
        }

        private static void renderStateIcon(int vertexX, int vertexY, int intU, int intV, String id)
        {
            try
            {
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/" + id + ".png"));
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();
                buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                buffer.pos(vertexX, vertexY + intV, 0.0D).tex(1,0).endVertex();
                buffer.pos(vertexX + intU, vertexY + intV, 0.0D).tex(0, 0).endVertex();
                buffer.pos(vertexX + intU, vertexY, 0.0D).tex(0, 1).endVertex();
                buffer.pos(vertexX, vertexY, 0.0D).tex(1, 1).endVertex();
                tessellator.draw();
            }
            catch (Exception ignored)
            {
            }
        }

        public static Entity getEntityLookedAt(Entity e)
        {
            Entity foundEntity = null;
            double distance = 32.0D;
            RayTraceResult pos = raycast(e, 32.0D);
            Vec3d positionVector = e.getPositionVector();
            if (e instanceof EntityPlayer)
            {
                positionVector = positionVector.addVector(0.0D, e.getEyeHeight(), 0.0D);
            }

            if (pos != null)
            {
                distance = pos.hitVec.distanceTo(positionVector);
            }

            Vec3d lookVector = e.getLookVec();
            Vec3d reachVector = positionVector.addVector(lookVector.x * 32.0D, lookVector.y * 32.0D, lookVector.z * 32.0D);
            Entity lookedEntity = null;
            List<Entity> entitiesInBoundingBox = e.getEntityWorld().getEntitiesWithinAABBExcludingEntity(e, e.getEntityBoundingBox().grow(lookVector.x * 32.0D, lookVector.y * 32.0D, lookVector.z * 32.0D).expand(1.0D, 1.0D, 1.0D));
            double minDistance = distance;
            Iterator<Entity> var14 = entitiesInBoundingBox.iterator();

            while (true)
            {
                do
                {
                    do
                    {
                        if (!var14.hasNext())
                        {
                            return foundEntity;
                        }

                        Entity entity = var14.next();
                        if (entity.canBeCollidedWith())
                        {
                            float collisionBorderSize = entity.getCollisionBorderSize();
                            AxisAlignedBB hitbox = entity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                            RayTraceResult interceptPosition = hitbox.calculateIntercept(positionVector, reachVector);
                            if (hitbox.contains(positionVector))
                            {
                                if (0.0D < minDistance || minDistance == 0.0D)
                                {
                                    lookedEntity = entity;
                                    minDistance = 0.0D;
                                }
                            } else if (interceptPosition != null)
                            {
                                double distanceToEntity = positionVector.distanceTo(interceptPosition.hitVec);
                                if (distanceToEntity < minDistance || minDistance == 0.0D)
                                {
                                    lookedEntity = entity;
                                    minDistance = distanceToEntity;
                                }
                            }
                        }
                    } while (lookedEntity == null);
                } while (!(minDistance < distance) && pos != null);

                foundEntity = lookedEntity;
            }
        }

        public static RayTraceResult raycast(Entity e, double len)
        {
            Vec3d vec = new Vec3d(e.posX, e.posY, e.posZ);
            if (e instanceof EntityPlayer)
            {
                vec = vec.add(new Vec3d(0.0D, e.getEyeHeight(), 0.0D));
            }

            Vec3d look = e.getLookVec();
            return raycast(e.getEntityWorld(), vec, look, len);
        }

        public static RayTraceResult raycast(World world, Vec3d origin, Vec3d ray, double len)
        {
            Vec3d end = origin.add(ray.normalize().scale(len));
            return world.rayTraceBlocks(origin, end);
        }
    }
}
