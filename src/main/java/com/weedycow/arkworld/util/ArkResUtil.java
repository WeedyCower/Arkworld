package com.weedycow.arkworld.util;

import com.weedycow.arkworld.Arkworld;
import net.minecraft.util.ResourceLocation;

public class ArkResUtil
{
    public static ResourceLocation geo(String id)
    {
        return new ResourceLocation(Arkworld.MODID, "geo/"+id+".geo.json");
    }

    public static ResourceLocation animation(String id)
    {
        return new ResourceLocation(Arkworld.MODID, "animations/"+id+".animation.json");
    }

    public static ResourceLocation textureEntities(String id)
    {
        return new ResourceLocation(Arkworld.MODID, "textures/entities/"+id+".png");
    }

    public static ResourceLocation textureBlocks(String id)
    {
        return new ResourceLocation(Arkworld.MODID, "textures/blocks/"+id+".png");
    }

    public static ResourceLocation textureItems(String id)
    {
        return new ResourceLocation(Arkworld.MODID, "textures/items/"+id+".png");
    }

    public static ResourceLocation textureGui(String id)
    {
        return new ResourceLocation(Arkworld.MODID, "textures/gui/"+id+".png");
    }
}
