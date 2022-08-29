package com.weedycow.arkworld.renderer;

import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.model.ModelArkMob;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderArkmob extends GeoEntityRenderer<ArkMob>
{
    public RenderArkmob(RenderManager renderManager)
    {
        super(renderManager, new ModelArkMob());
    }
}
