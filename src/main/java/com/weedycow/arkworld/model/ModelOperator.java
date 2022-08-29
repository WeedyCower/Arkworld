package com.weedycow.arkworld.model;

import com.weedycow.arkworld.entity.operator.Operator;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelOperator extends AnimatedGeoModel<Operator>
{
    @Override
    public ResourceLocation getModelLocation(Operator object)
    {
        return object.getModelLocation();
    }

    @Override
    public ResourceLocation getTextureLocation(Operator object)
    {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Operator object)
    {
        return object.getAnimationLocation();
    }
}
