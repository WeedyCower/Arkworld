package com.weedycow.arkworld.entity.animal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.weedycow.weedylib.util.EntityUtil.distanceToTarget;

public class Lion extends ArkAnimal implements IRangedAttackMob
{
    public static final String ID = "lion";
    public static final String NAME = Arkworld.MODID + ".lion";
    protected static final DataParameter<Integer> TYPE = EntityDataManager.createKey(Lion.class, DataSerializers.VARINT);;

    public Lion(World worldIn)
    {
        super(worldIn,ID);
        setSize(1.5f,1.5f);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackRanged(this,getMoveSpeed(),3600,animal.lion.attackRange-1));
        this.tasks.addTask(2, new EntityAIMate(this, getMoveSpeed()));
        this.tasks.addTask(3, new EntityAITempt(this, getMoveSpeed()*1.25f, Items.BEEF, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, getMoveSpeed()*1.125f));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, getMoveSpeed()));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, getMoveSpeed()));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(animal.lion.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(animal.lion.moveSpeed);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(animal.lion.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(animal.lion.attackDamage);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(animal.lion.attackRange);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),12,0) && getAttackState())
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.lion.attack", false));
            controllerAttack.markNeedsReload();
        }
        if(event.isMoving())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.lion.move", true));
        }
        else
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.lion.idle", true));
        }

        return PlayState.CONTINUE;
    }

    AnimationController<Lion> controllerAttack = new AnimationController<>(this,"controllerAttack",2,this::PlayState);
    AnimationController<Lion> controllerMove = new AnimationController<>(this,"controllerMove",2,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(TYPE, 0);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data)
    {
        setType(rand.nextInt(2));
        return super.onInitialSpawn(difficulty,data);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Type", getType());
    }

    @Override
    @ParametersAreNonnullByDefault
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setType(compound.getInteger("Type"));
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(isNormalGetAttackTarget()) EntityUtil.setTargetState(this,TARGET);

        if(isNormalGetAttackState()) EntityUtil.setAttackState(this,ATTACK,getAttackRange());

        if(!world.isRemote)setDistance((float) distanceToTarget(this));

        if(EntityUtil.atSetIntervals(TICK,this,getAttackInterval(),0,0) && getAttackState())
            EntityUtil.attackMeleeAOE(this,null, DamageSource.causeMobDamage(this),getAttackRange(),getAttackDamage(),true);
    }

    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == Items.BEEF;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(@Nonnull EntityAgeable ageable)
    {
        return new Lion(world);
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureEntities(id);
    }

    public int getType()
    {
        return this.getDataManager().get(TYPE);
    }

    public void setType(int type)
    {
        this.getDataManager().set(TYPE, type);
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        if(getGrowingAge()==-1)
            return ArkResUtil.geo(id+"_baby");
        else
        {
            if(getType()==0)
                return ArkResUtil.geo(id+"_male");
            if(getType()==1)
                return ArkResUtil.geo(id+"_female");
        }
        return null;
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        if(getGrowingAge()==-1)
            return ArkResUtil.animation(id+"_baby");
        else
        {
            if(getType()==0)
                return ArkResUtil.animation(id+"_male");
            if(getType()==1)
                return ArkResUtil.animation(id+"_female");
        }
        return null;
    }

    @Override
    public void attackEntityWithRangedAttack(@Nonnull EntityLivingBase target, float distanceFactor)
    {

    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {

    }
}
