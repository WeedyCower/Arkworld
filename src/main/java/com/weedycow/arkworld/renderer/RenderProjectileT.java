package com.weedycow.arkworld.renderer;

import com.weedycow.arkworld.entity.weapon.ProjectileT;
import com.weedycow.arkworld.model.ModelProjectileT;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderProjectileT extends GeoEntityRenderer<ProjectileT>
{
    public RenderProjectileT(RenderManager renderManager)
    {
        super(renderManager, new ModelProjectileT());
    }
}
