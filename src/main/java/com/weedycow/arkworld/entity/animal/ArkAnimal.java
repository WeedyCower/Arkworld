package com.weedycow.arkworld.entity.animal;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.registry.BlockRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.IRES;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;
import java.util.UUID;

public abstract class ArkAnimal extends EntityAnimal implements IAnimatable, IRES
{
    String id;
    protected int countdown;
    protected AttributeModifier maxHealthModifiedTemp;
    protected AttributeModifier attackDamageModifiedTemp;
    protected AttributeModifier moveSpeedModifiedTemp;
    protected AttributeModifier attackIntervalModifiedTemp;
    protected AttributeModifier attackRangeModifiedTemp;
    public static ArkConfig.Creature.Animal animal = ArkConfig.creature.animal;
    protected AnimationFactory factory = new AnimationFactory(this);
    protected IAttributeInstance maxHealth = this.getEntityAttribute(WLMAttributes.MAX_HEALTH);
    protected IAttributeInstance moveSpeed = this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED);
    protected IAttributeInstance attackDamage = this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE);
    protected IAttributeInstance attackInterval = this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL);
    protected IAttributeInstance attackRange = this.getEntityAttribute(WLMAttributes.ATTACK_RANGE);
    protected static final DataParameter<Float> HP = EntityDataManager.createKey(ArkAnimal.class, DataSerializers.FLOAT);
    protected static final DataParameter<Float> MS = EntityDataManager.createKey(ArkAnimal.class, DataSerializers.FLOAT);
    protected static final DataParameter<Float> AD = EntityDataManager.createKey(ArkAnimal.class, DataSerializers.FLOAT);
    protected static final DataParameter<Float> AR = EntityDataManager.createKey(ArkAnimal.class, DataSerializers.FLOAT);
    protected static final DataParameter<Integer> AI = EntityDataManager.createKey(ArkAnimal.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> TICK = EntityDataManager.createKey(ArkAnimal.class, DataSerializers.VARINT);
    protected static final DataParameter<Float> DISTANCE = EntityDataManager.createKey(ArkAnimal.class, DataSerializers.FLOAT);
    protected static final DataParameter<Boolean> ATTACK = EntityDataManager.createKey(ArkAnimal.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> TARGET = EntityDataManager.createKey(ArkAnimal.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> COUNTDOWN = EntityDataManager.createKey(ArkAnimal.class, DataSerializers.VARINT);

    public ArkAnimal(World worldIn)
    {
        super(worldIn);
    }

    public ArkAnimal(World worldIn, String id)
    {
        super(worldIn);
        this.id = id;
        this.spawnableBlock = BlockRegistry.GRASS_FROZEN;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(WLMAttributes.ATTACK_RANGE);
        this.getAttributeMap().registerAttribute(WLMAttributes.ATTACK_INTERVAL);
        this.getAttributeMap().registerAttribute(WLMAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(WLMAttributes.FOLLOW_RANGE).setBaseValue(32);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!world.isRemote)
            setTickExisted(getTickExisted() + 1);

        if (isNormalGetAttackTarget()) EntityUtil.setTargetState(this, TARGET);

        if (isNormalGetAttackState()) EntityUtil.setAttackState(this, ATTACK, getAttackRange());
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

    @Override
    @ParametersAreNonnullByDefault
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("TickExisted",getTickExisted());
    }

    @Override
    @ParametersAreNonnullByDefault
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setTickExisted(compound.getInteger("TickExisted"));
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(AI,0);
        this.getDataManager().register(HP,0F);
        this.getDataManager().register(MS,0F);
        this.getDataManager().register(AD,0F);
        this.getDataManager().register(AR,0F);
        this.getDataManager().register(ATTACK,false);
        this.getDataManager().register(DISTANCE,0f);
        this.getDataManager().register(TICK,0);
        this.getDataManager().register(TARGET,false);
        this.getDataManager().register(COUNTDOWN,getInitCountdown());
    }

    public void setTickExisted(int tick)
    {
        this.getDataManager().set(TICK,tick);
    }

    public int getTickExisted()
    {
        return this.getDataManager().get(TICK);
    }

    public int getInitCountdown()
    {
        return this.countdown;
    }

    public void setInitCountdown(int n)
    {
        this.countdown = n;
    }

    @Override
    public AnimationFactory getFactory()
    {
        return factory;
    }

    public boolean getTargetState()
    {
        return this.getDataManager().get(TARGET);
    }

    public void setTargetState(boolean t)
    {
        this.getDataManager().set(TARGET,t);
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

    public boolean getAttackState() {return this.getDataManager().get(ATTACK);}

    public void setAttackState(boolean state) {this.getDataManager().set(ATTACK,state);}

    public boolean isNormalGetAttackTarget()
    {
        return true;
    }

    public boolean isNormalGetAttackState()
    {
        return true;
    }

    public void setAR(float percent){this.getDataManager().set(AR,percent*((float)getEntityAttribute(WLMAttributes.ATTACK_RANGE).getAttributeValue()));}

    public void addAR(float amount){this.getDataManager().set(AR,amount);}

    public float getAR(){return this.getDataManager().get(AR);}

    public void setAI(float percent){this.getDataManager().set(AI,(int)(percent*getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).getAttributeValue()));}

    public void addAI(int amount){this.getDataManager().set(AI,amount);}

    public float getAI(){return this.getDataManager().get(AI);}

    public void setAD(float percent){this.getDataManager().set(AD,percent*((float)getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).getAttributeValue()));}

    public void addAD(float amount){this.getDataManager().set(AD,amount);}

    public float getAD(){return this.getDataManager().get(AD);}

    public void setMS(float percent){this.getDataManager().set(MS,percent*((float)getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).getAttributeValue()));}

    public void addMS(float amount){this.getDataManager().set(MS,amount);}

    public float getMS(){return this.getDataManager().get(MS);}

    public void setHP(float percent){this.getDataManager().set(HP,percent*((float)getEntityAttribute(WLMAttributes.MAX_HEALTH).getAttributeValue()));}

    public void addHP(float amount){this.getDataManager().set(HP,amount);}

    public float getHP(){return this.getDataManager().get(HP);}

    @Override
    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureEntities(id);
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        if(getGrowingAge()==-1)
            return ArkResUtil.geo(id+"_baby");
        else
            return ArkResUtil.geo(id);
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        if(getGrowingAge()==-1)
            return ArkResUtil.animation(id+"_baby");
        else
            return ArkResUtil.animation(id);
    }

    public static void onDrop(LivingDropsEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();

        if(entity instanceof EntityAnimal)
        {
            event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(ItemRegistry.ORI_KETONE,new Random().nextInt(3)+1)));

            if (entity instanceof TibetanAntelope)
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(Items.MUTTON)));
            if (entity instanceof Elk)
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(ItemRegistry.VENISON_RAW)));
            if (entity instanceof Bullfrog || entity instanceof Frog)
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(ItemRegistry.FROG_MEAT_RAW)));
            if (entity instanceof Cheetah || entity instanceof Lion)
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(ItemRegistry.BIG_MEAT_RAW)));
            if (entity instanceof Hedgehog || entity instanceof Hyena || entity instanceof Raccoon || entity instanceof Skunk || entity instanceof Squirrel)
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(ItemRegistry.SMALL_MEAT_RAW)));
            if (entity instanceof Owl)
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(Items.CHICKEN)));
            if (entity instanceof Rhino)
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(ItemRegistry.BIG_MEAT_RAW,2)));
        }
    }
}
