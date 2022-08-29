package com.weedycow.arkworld.entity.animal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class Owl extends ArkAnimal
{
    public static final String ID = "owl";
    public static final String NAME = Arkworld.MODID + ".owl";
    private static final DataParameter<Boolean> FLYING = EntityDataManager.createKey(Owl.class, DataSerializers.BOOLEAN);

    private BlockPos spawnPosition;

    public Owl(World worldIn)
    {
        super(worldIn,ID);
        setSize(0.7f,1.1f);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, getMoveSpeed()));
        this.tasks.addTask(2, new EntityAIMate(this, getMoveSpeed()));
        this.tasks.addTask(3, new EntityAITempt(this, getMoveSpeed(), ItemRegistry.SMALL_MEAT_RAW, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, getMoveSpeed()));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, getMoveSpeed()));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, getMoveSpeed()));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(animal.owl.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(animal.owl.moveSpeed);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(FLYING, false);
    }

    public  <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(isFlying())
        {
            controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.owl.move", true));
        }
        else
        {
            if(EntityUtil.atSetIntervals(TICK,this,200,0,0) && new Random().nextInt(10)==0)
            {
                controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.owl.idle2", false));
                controllerIdle.markNeedsReload();
            }
            else if(controllerIdle.getAnimationState()!= AnimationState.Running)
            {
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.owl.idle", true));
            }
        }

        if(this.isChild())
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.owl.scale", true));

        return PlayState.CONTINUE;
    }

    AnimationController<Owl> controllerAttack = new AnimationController<>(this,"controllerAttack",2,this::PlayState);
    AnimationController<Owl> controllerMove = new AnimationController<>(this,"controllerMove",2,this::PlayState);
    AnimationController<Owl> controllerIdle = new AnimationController<>(this,"controllerIdle",2,this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
        data.addAnimationController(controllerIdle);
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.dataManager.set(FLYING, compound.getBoolean("Flying"));
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Flying", this.dataManager.get(FLYING));
    }

    public boolean isFlying()
    {
        return this.dataManager.get(FLYING);
    }

    public void setFlying(boolean b)
    {
        this.getDataManager().set(FLYING,b);
    }

    protected void updateAITasks()
    {
        super.updateAITasks();
        BlockPos blockpos = new BlockPos(this);
        BlockPos blockpos1 = blockpos.down();

        if(isFlying())
        {
            if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1))
            {
                this.spawnPosition = null;
            }

            if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((int) this.posX, (double) ((int) this.posY), (double) ((int) this.posZ)) < 4.0D)
            {
                this.spawnPosition = new BlockPos((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int) this.posY + this.rand.nextInt(6) - 2, (int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
            }

            double d0 = (double) this.spawnPosition.getX() + 0.5D - this.posX;
            double d1 = (double) this.spawnPosition.getY() + 0.2D - this.posY;
            double d2 = (double) this.spawnPosition.getZ() + 0.5D - this.posZ;
            this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
            this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
            this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
            float f = (float) (MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
            float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
            this.moveForward = 0.5F;
            this.rotationYaw += f1;

//            && this.world.getBlockState(blockpos1).isNormalCube()

            if (this.rand.nextInt(1000) == 0)
            {
                this.setFlying(false);
            }
        }
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(!world.isRemote)
        {
            if (this.getRevengeTarget() != null || this.isBurning())
            {
                setFlying(true);
            }
        }

        if (!this.isFlying())
        {
            setFlying((motionX>0 || motionZ>0) && onGround);
        }
        else
        {
            this.motionY *= 0.6000000238418579D;
        }
    }

    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == ItemRegistry.SMALL_MEAT_RAW;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(@Nonnull EntityAgeable ageable)
    {
        return new Owl(world);
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo(id);
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation(id);
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source) || source==DamageSource.FALL)
        {
            return false;
        }
        else
        {
            return super.attackEntityFrom(source, amount);
        }
    }
}
