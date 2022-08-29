package com.weedycow.arkworld.model;

import com.weedycow.weedylib.entity.IRES;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelItem extends AnimatedGeoModel
{
    @Override
    public ResourceLocation getModelLocation(Object object)
    {
        if(object instanceof Item && object instanceof IAnimatable && object instanceof IRES)
            return ((IRES) object).getModelLocation();
        return null;
    }

    @Override
    public ResourceLocation getTextureLocation(Object object)
    {
        if(object instanceof Item && object instanceof IAnimatable && object instanceof IRES)
            return ((IRES) object).getTextureLocation();
        return null;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Object object)
    {
        if(object instanceof Item && object instanceof IAnimatable && object instanceof IRES)
            return ((IRES) object).getAnimationLocation();
        return null;
    }
}
