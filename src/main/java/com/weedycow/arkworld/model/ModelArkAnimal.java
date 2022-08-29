package com.weedycow.arkworld.model;

import com.weedycow.arkworld.entity.animal.ArkAnimal;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelArkAnimal extends AnimatedGeoModel<ArkAnimal>
{
    @Override
    public ResourceLocation getModelLocation(ArkAnimal object)
    {
        return object.getModelLocation();
    }

    @Override
    public ResourceLocation getTextureLocation(ArkAnimal object)
    {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ArkAnimal object)
    {
        return object.getAnimationLocation();
    }
}
