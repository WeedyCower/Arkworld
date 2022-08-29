package com.weedycow.arkworld.model;

import com.weedycow.arkworld.block.ArkTile;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelArkTile extends AnimatedGeoModel<ArkTile>
{
    @Override
    public ResourceLocation getModelLocation(ArkTile object)
    {
        return object.getModelLocation();
    }

    @Override
    public ResourceLocation getTextureLocation(ArkTile object)
    {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ArkTile object)
    {
        return object.getAnimationLocation();
    }
}
