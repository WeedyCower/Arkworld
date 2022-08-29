package com.weedycow.arkworld.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelBossOceanCat extends ModelBase
{
    private final ModelRenderer head;
    private final ModelRenderer head1;
    private final ModelRenderer head2;
    private final ModelRenderer body;
    private final ModelRenderer rightleg;
    private final ModelRenderer leftleg;
    private final ModelRenderer rightarm;
    private final ModelRenderer leftarm;

    public ModelBossOceanCat() {
        textureWidth = 128;
        textureHeight = 128;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.setTextureOffset(0, 0).addBox(-3.0F, -6.0F, -4.0F, 6, 6, 8, false);
        head.setTextureOffset(0, 14).addBox(-3.0F, -8.0F, -4.5F, 6, 2, 9, false);
        head.setTextureOffset(44, 0).addBox(2.8F, -7.0F, -4.0F, 1, 1, 8, false);
        head.setTextureOffset(40, 28).addBox(-3.8F, -7.0F, -4.0F, 1, 1, 8, false);

        head1 = new ModelRenderer(this);
        head1.setRotationPoint(-3.5F, -3.0F, 0.0F);
        head.addChild(head1);
        setRotationAngle(head1, 0.0F, 0.0F, 0.1309F);
        head1.setTextureOffset(18, 41).addBox(-1.0F, -3.0F, -4.0F, 1, 6, 8, false);

        head2 = new ModelRenderer(this);
        head2.setRotationPoint(3.5F, -4.0F, 0.0F);
        head.addChild(head2);
        setRotationAngle(head2, 0.0F, 0.0F, -0.1309F);
        head2.setTextureOffset(0, 41).addBox(-0.2F, -2.0F, -4.0F, 1, 6, 8, false);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 12.0F, 0.0F);
        body.setTextureOffset(0, 25).addBox(-4.0F, -12.0F, -2.0F, 8, 12, 4, false);
        body.setTextureOffset(0, 14).addBox(2.0F, -12.0F, -2.5F, 2, 6, 1, false);
        body.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -2.5F, 2, 6, 1, false);
        body.setTextureOffset(21, 16).addBox(-4.0F, -12.0F, 2.0F, 8, 2, 1, false);
        body.setTextureOffset(21, 19).addBox(-3.0F, -10.0F, 2.0F, 6, 1, 1, false);
        body.setTextureOffset(20, 0).addBox(-2.0F, -9.0F, 2.0F, 4, 3, 1, false);
        body.setTextureOffset(20, 4).addBox(-1.0F, -6.0F, 2.0F, 2, 1, 1, false);

        rightleg = new ModelRenderer(this);
        rightleg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        rightleg.setTextureOffset(40, 12).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, false);

        leftleg = new ModelRenderer(this);
        leftleg.setRotationPoint(2.0F, 12.0F, 0.0F);
        leftleg.setTextureOffset(36, 37).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, false);

        rightarm = new ModelRenderer(this);
        rightarm.setRotationPoint(-4.0F, 2.0F, 0.0F);
        rightarm.setTextureOffset(28, 0).addBox(-4.0F, -2.0F, -2.0F, 4, 12, 4, false);
        rightarm.setTextureOffset(50, 28).addBox(-4.5F, 7.0F, -2.5F, 5, 1, 5, false);

        leftarm = new ModelRenderer(this);
        leftarm.setRotationPoint(4.0F, 2.0F, 0.0F);
        leftarm.setTextureOffset(24, 25).addBox(0.0F, -2.0F, -2.0F, 4, 12, 4, false);
        leftarm.setTextureOffset(47, 48).addBox(-0.5F, 7.0F, -2.5F, 5, 1, 5, false);
    }

    @Override
    public void render(Entity entity, float a, float b, float c, float d, float e, float scale)
    {
        this.body.render(scale);
        this.head.render(scale);
        this.leftarm.render(scale);
        this.rightarm.render(scale);
        this.leftleg.render(scale);
        this.rightleg.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getTicksElytraFlying() > 4;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        if (flag)
        {
            this.head.rotateAngleX = -((float)Math.PI / 4F);
        }
        else
        {
            this.head.rotateAngleX = headPitch * 0.017453292F;
        }

        this.body.rotateAngleY = 0.0F;
        this.rightarm.rotationPointZ = 0.0F;
        this.rightarm.rotationPointX = -5.0F;
        this.leftarm.rotationPointZ = 0.0F;
        this.leftarm.rotationPointX = 5.0F;
        float f = 1.0F;

        if (flag)
        {
            f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F)
        {
            f = 1.0F;
        }
        this.rightarm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
        this.leftarm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
        this.rightarm.rotateAngleZ = 0.0F;
        this.leftarm.rotateAngleZ = 0.0F;
        this.rightleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
        this.leftleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
        this.rightleg.rotateAngleY = 0.0F;
        this.leftleg.rotateAngleY = 0.0F;
        this.rightleg.rotateAngleZ = 0.0F;
        this.leftleg.rotateAngleZ = 0.0F;

        if (this.isRiding)
        {
            this.rightarm.rotateAngleX += -((float) Math.PI / 5F);
            this.leftarm.rotateAngleX += -((float) Math.PI / 5F);
            this.rightleg.rotateAngleX = -1.4137167F;
            this.rightleg.rotateAngleY = ((float) Math.PI / 10F);
            this.rightleg.rotateAngleZ = 0.07853982F;
            this.leftleg.rotateAngleX = -1.4137167F;
            this.leftleg.rotateAngleY = -((float) Math.PI / 10F);
            this.leftleg.rotateAngleZ = -0.07853982F;
        }

        this.rightarm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.leftarm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.rightarm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.leftarm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
