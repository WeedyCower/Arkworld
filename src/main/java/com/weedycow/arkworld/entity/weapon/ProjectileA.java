package com.weedycow.arkworld.entity.weapon;

import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.WLA;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class ProjectileA extends WLA implements IAnimatable
{
    public String id;
    private final AnimationFactory factory = new AnimationFactory(this);

    public ProjectileA(World worldIn)
    {
        super(worldIn);
    }

    public ProjectileA(World worldIn, String id)
    {
        this(worldIn);
        this.id = id;
    }

    public ProjectileA(World worldIn, String id, EntityLivingBase shooter)
    {
        super(worldIn, shooter);
        this.id = id;
    }

    public ProjectileA(World worldIn, String id, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        this.id = id;
    }

    @Override
    public void registerControllers(AnimationData animationData)
    {

    }

    @Override
    public AnimationFactory getFactory()
    {
        return factory;
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureEntities(getTypeString());
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo(getTypeString());
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation(getTypeString());
    }
}