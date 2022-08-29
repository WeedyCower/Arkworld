package com.weedycow.arkworld.renderer;

import com.weedycow.arkworld.entity.animal.ArkAnimal;
import com.weedycow.arkworld.model.ModelArkAnimal;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderArkAnimal extends GeoEntityRenderer<ArkAnimal>
{
    public RenderArkAnimal(RenderManager renderManager)
    {
        super(renderManager, new ModelArkAnimal());
    }
}
