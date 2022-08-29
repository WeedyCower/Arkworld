package com.weedycow.arkworld.renderer;

import com.weedycow.arkworld.entity.weapon.ProjectileA;
import com.weedycow.arkworld.model.ModelProjectileA;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderProjectileA extends GeoEntityRenderer<ProjectileA>
{
    public RenderProjectileA(RenderManager renderManager)
    {
        super(renderManager, new ModelProjectileA());
    }

    @Override
    protected void applyRotations(ProjectileA entityLiving, float ageInTicks, float rotationYaw, float partialTicks)
    {
        GlStateManager.rotate(entityLiving.prevRotationYaw + (entityLiving.rotationYaw - entityLiving.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entityLiving.prevRotationPitch + (entityLiving.rotationPitch - entityLiving.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
    }
}
