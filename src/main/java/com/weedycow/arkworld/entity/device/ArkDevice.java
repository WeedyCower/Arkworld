package com.weedycow.arkworld.entity.device;

import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.IRES;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.ParametersAreNonnullByDefault;

public abstract class ArkDevice extends EntityLiving implements IAnimatable, IRES
{
    String id;
    AnimationFactory factory = new AnimationFactory(this);
    protected static DataParameter<Integer> COUNTDOWN = EntityDataManager.createKey(ArkDevice.class, DataSerializers.VARINT);

    public ArkDevice(World worldIn)
    {
        super(worldIn);
    }

    public ArkDevice(World worldIn, String id)
    {
        super(worldIn);
        this.id = id;
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(getCountdown()>0)
            setCountdown(getCountdown()-1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        setCountdown(compound.getInteger("Countdown"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        compound.setInteger("Countdown",getCountdown());
    }

    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(COUNTDOWN, 0);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void move(MoverType type, double x, double y, double z)
    {

    }

    @Override
    public void registerControllers(AnimationData animationData)
    {

    }

    public int getCountdown() {
        return this.getDataManager().get(COUNTDOWN);
    }

    public void setCountdown(int countdown) {
        this.getDataManager().set(COUNTDOWN, countdown);
    }

    @Override
    public AnimationFactory getFactory()
    {
        return factory;
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureEntities(id);
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
}
