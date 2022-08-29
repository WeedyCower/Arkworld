package com.weedycow.arkworld.renderer;

import com.weedycow.arkworld.entity.device.ArkDevice;
import com.weedycow.arkworld.model.ModelArkDevice;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderArkDevice extends GeoEntityRenderer<ArkDevice>
{
    public RenderArkDevice(RenderManager renderManager)
    {
        super(renderManager, new ModelArkDevice());
    }
}