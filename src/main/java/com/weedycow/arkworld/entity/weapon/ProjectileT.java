package com.weedycow.arkworld.entity.weapon;

import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.WLT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class ProjectileT extends WLT implements IAnimatable
{
    public String id;
    private final AnimationFactory factory = new AnimationFactory(this);

    public ProjectileT(World worldIn)
    {
        super(worldIn);
    }

    public ProjectileT(World worldIn, String id)
    {
        this(worldIn);
        this.id = id;
    }

    public ProjectileT(World worldIn, String id, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        this.id = id;
    }

    public ProjectileT(World worldIn, String id, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
        this.id = id;
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

    @Override
    public void registerControllers(AnimationData animationData)
    {

    }

    @Override
    public AnimationFactory getFactory()
    {
        return factory;
    }
}
