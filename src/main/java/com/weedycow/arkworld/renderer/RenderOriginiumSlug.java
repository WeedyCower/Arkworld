package com.weedycow.arkworld.renderer;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.union.normal.OriginiumSlug;
import com.weedycow.arkworld.model.ModelOriginiumSlug;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderOriginiumSlug extends RenderLiving
{
    private static final ResourceLocation ORIGINIUM_SLUG_A_TEXTURE =
            new ResourceLocation(Arkworld.MODID + ":textures/entities/" + "originium_slug_a" + ".png");
    private static final ResourceLocation ORIGINIUM_SLUG_B_TEXTURE =
            new ResourceLocation(Arkworld.MODID + ":textures/entities/" + "originium_slug_b" + ".png");
    private static final ResourceLocation ORIGINIUM_SLUG_C_TEXTURE =
            new ResourceLocation(Arkworld.MODID + ":textures/entities/" + "originium_slug_c" + ".png");

    public RenderOriginiumSlug(RenderManager manager)
    {
        super(manager,new ModelOriginiumSlug(),0.1f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        int type = ((OriginiumSlug)entity).getType();
        if(type == 1)
        {
            return ORIGINIUM_SLUG_B_TEXTURE;
        }
        if(type == 2)
        {
            return ORIGINIUM_SLUG_C_TEXTURE;
        }
        return ORIGINIUM_SLUG_A_TEXTURE;
    }
}
