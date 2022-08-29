package com.weedycow.arkworld.model;

import com.weedycow.arkworld.entity.device.ArkDevice;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelArkDevice extends AnimatedGeoModel<ArkDevice>
{
    @Override
    public ResourceLocation getModelLocation(ArkDevice object)
    {
        return object.getModelLocation();
    }

    @Override
    public ResourceLocation getTextureLocation(ArkDevice object)
    {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ArkDevice object)
    {
        return object.getAnimationLocation();
    }
}
