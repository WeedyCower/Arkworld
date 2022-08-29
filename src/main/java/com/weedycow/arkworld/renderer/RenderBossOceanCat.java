package com.weedycow.arkworld.renderer;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.hypergryph.BossOceanCat;
import com.weedycow.arkworld.model.ModelBossOceanCat;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBossOceanCat extends RenderLiving
{
    private static final ResourceLocation BOOS_OCEAN_CAT_TEXTURE =
            new ResourceLocation(Arkworld.MODID + ":textures/entities/" + BossOceanCat.ID + ".png");

    public RenderBossOceanCat(RenderManager manager)
    {
        super(manager,new ModelBossOceanCat(),0.5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return BOOS_OCEAN_CAT_TEXTURE;
    }
}
