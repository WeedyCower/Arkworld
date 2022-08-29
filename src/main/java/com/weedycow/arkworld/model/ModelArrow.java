package com.weedycow.arkworld.model;

import com.weedycow.arkworld.item.tool.ItemArrow;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelArrow extends AnimatedGeoModel<ItemArrow>
{
    @Override
    public ResourceLocation getModelLocation(ItemArrow object)
    {
        return object.getModelLocation();
    }

    @Override
    public ResourceLocation getTextureLocation(ItemArrow object)
    {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ItemArrow object)
    {
        return object.getAnimationLocation();
    }
}
