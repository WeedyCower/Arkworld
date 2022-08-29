package com.weedycow.arkworld.model;

import com.weedycow.arkworld.entity.weapon.ProjectileT;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelProjectileT extends AnimatedGeoModel<ProjectileT>
{
    @Override
    public ResourceLocation getModelLocation(ProjectileT object)
    {
        return object.getModelLocation();
    }

    @Override
    public ResourceLocation getTextureLocation(ProjectileT object)
    {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ProjectileT object)
    {
        return object.getAnimationLocation();
    }
}
