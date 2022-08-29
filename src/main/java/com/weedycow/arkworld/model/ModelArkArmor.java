package com.weedycow.arkworld.model;

import com.weedycow.arkworld.item.armor.ArkArmor;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelArkArmor extends AnimatedGeoModel<ArkArmor>
{
    @Override
    public ResourceLocation getModelLocation(ArkArmor object)
    {
        return  object.getModelLocation();
    }

    @Override
    public ResourceLocation getTextureLocation(ArkArmor object)
    {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ArkArmor object)
    {
        return object.getAnimationLocation();
    }
}
