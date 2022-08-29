package com.weedycow.arkworld.model;

import com.weedycow.arkworld.entity.enemy.ArkMob;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelArkMob extends AnimatedGeoModel<ArkMob>
{
    @Override
    public ResourceLocation getModelLocation(ArkMob object)
    {
        return object.getModelLocation();
    }

    @Override
    public ResourceLocation getTextureLocation(ArkMob object)
    {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ArkMob object)
    {
        return object.getAnimationLocation();
    }
}
