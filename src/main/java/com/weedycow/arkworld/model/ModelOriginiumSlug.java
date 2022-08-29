package com.weedycow.arkworld.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelOriginiumSlug extends ModelBase
{
    private final ModelRenderer body;
    private final ModelRenderer body_r1;
    private final ModelRenderer body_r2;
    private final ModelRenderer body_r3;
    private final ModelRenderer body_r4;

    public ModelOriginiumSlug() {
        textureWidth = 32;
        textureHeight = 32;
        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 24.0F, 0.0F);
        setRotationAngle(body, 0.0F, 3.1416F, 0.0F);
        body.setTextureOffset(13, 14).addBox(-2.0F, -2.6F, 2.8F, 3, 1, 1,false);
        body.setTextureOffset(0, 0).addBox(-3.0F, -2.0F, -2.0F, 5, 2, 6,true);
        body.setTextureOffset(8, 9).addBox(-2.5F, -3.0F, -1.0F, 2, 1, 4,false);
        body.setTextureOffset(0, 8).addBox(-0.5F, -3.0F, -1.0F, 2, 1, 4,false);
        body.setTextureOffset(0, 0).addBox(-1.0F, -1.8F, -3.0F, 1, 1, 2,false);

        body_r1 = new ModelRenderer(this);
        body_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(body_r1);
        setRotationAngle(body_r1, -0.3491F, 0.0F, 0.0F);
        body_r1.setTextureOffset(0, 13).addBox(-1.0F, -2.8F, -4.0F, 1, 1, 3,false);

        body_r2 = new ModelRenderer(this);
        body_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(body_r2);
        setRotationAngle(body_r2, -0.6981F, 0.0F, 0.0F);
        body_r2.setTextureOffset(7, 16).addBox(-1.0F, -6.5F, 0.0F, 1, 4, 1,false);

        body_r3 = new ModelRenderer(this);
        body_r3.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(body_r3);
        setRotationAngle(body_r3, -1.4835F, 0.0F, 0.0F);
        body_r3.setTextureOffset(0, 3).addBox(-1.0F, -5.5F, -1.5F, 1, 2, 1,false);

        body_r4 = new ModelRenderer(this);
        body_r4.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(body_r4);
        setRotationAngle(body_r4, 0.1745F, 0.0F, 0.0F);
        body_r4.setTextureOffset(5, 14).addBox(-2.0F, -3.0F, -1.4F, 3, 1, 1,false);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void render(Entity entity, float a, float b, float c, float d, float e, float scale)
    {
        this.body.render(scale);
    }

}
