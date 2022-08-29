package com.weedycow.arkworld.model;

import com.weedycow.arkworld.entity.weapon.ProjectileA;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelProjectileA extends AnimatedGeoModel<ProjectileA>
{
    @Override
    public ResourceLocation getModelLocation(ProjectileA object)
    {
        return object.getModelLocation();
    }

    @Override
    public ResourceLocation getTextureLocation(ProjectileA object)
    {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ProjectileA object)
    {
        return object.getAnimationLocation();
    }
}
