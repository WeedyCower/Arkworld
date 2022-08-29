package com.weedycow.arkworld.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBossRuaCow extends ModelBase
{
	private ModelRenderer body;
	
	public ModelBossRuaCow()
	{
		textureWidth = 256;
		textureHeight = 256;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, -3.0F);
		body.setTextureOffset(0, 72).addBox(-6.0F, -17.5F, -6.0F, 12, 3, 3, false);
		body.setTextureOffset(34, 47).addBox(-7.0F, -28.0F, -5.0F, 2, 14, 2, false);
		body.setTextureOffset(12, 11).addBox(-8.0F, -21.0F, -5.0F, 1, 5, 2, false);
		body.setTextureOffset(42, 52).addBox(5.0F, -24.0F, -5.0F, 1, 7, 2, false);
		body.setTextureOffset(47, 0).addBox(3.0F, -28.0F, -5.0F, 2, 15, 2, false);
		body.setTextureOffset(36, 85).addBox(-5.0F, -29.0F, -6.0F, 9, 2, 3, false);
		body.setTextureOffset(0, 47).addBox(-3.0F, -30.0F, -5.0F, 6, 1, 5, false);
		body.setTextureOffset(0, 0).addBox(2.0F, -13.0F, -7.0F, 3, 9, 4, false);
		body.setTextureOffset(44, 27).addBox(0.0F, -7.0F, -8.0F, 3, 5, 5, false);
		body.setTextureOffset(44, 1).addBox(2.0F, -3.0F, -9.0F, 6, 3, 23, false);
		body.setTextureOffset(79, 0).addBox(8.0F, -3.0F, -8.0F, 5, 3, 21, false);
		body.setTextureOffset(33, 52).addBox(-2.0F, -3.0F, -9.0F, 4, 3, 23, false);
		body.setTextureOffset(36, 78).addBox(-9.0F, -3.0F, 14.0F, 12, 3, 4, false);
		body.setTextureOffset(0, 47).addBox(-13.0F, -3.0F, -8.0F, 6, 3, 22, false);
		body.setTextureOffset(65, 56).addBox(-7.0F, -3.0F, -8.0F, 5, 3, 22, false);
		body.setTextureOffset(42, 27).addBox(-12.0F, -8.0F, -7.0F, 10, 5, 20, false);
		body.setTextureOffset(0, 78).addBox(-11.0F, -12.0F, -6.0F, 9, 4, 18, false);
		body.setTextureOffset(97, 46).addBox(-10.0F, -14.0F, -5, 8, 2, 16, false);
		body.setTextureOffset(92, 87).addBox(-11.0F, -20.0F, -4.0F, 9, 6, 14, false);
		body.setTextureOffset(0, 100).addBox(-9.0F, -28.0F, -4.0F, 7, 8, 13, false);
		body.setTextureOffset(97, 64).addBox(-7.0F, -29.0F, -3.0F, 12, 1, 10, false);
		body.setTextureOffset(88, 24).addBox(-2.0F, -22.0F, -4.0F, 9, 8, 14, false);
		body.setTextureOffset(40, 101).addBox(-2.0F, -28.0F, -4.0F, 8, 6, 12, false);
		body.setTextureOffset(54, 81).addBox(-2.0F, -16.0F, -5.0F, 10, 4, 16, false);
		body.setTextureOffset(0, 24).addBox(-2.0F, -13.0F, -6.0F, 13, 5, 18, false);
		body.setTextureOffset(0, 0).addBox(-2.0F, -8.0F, -6.0F, 14, 5, 19, false);
		body.setTextureOffset(0, 24).addBox(-5.0F, -27.0F, -5.0F, 8, 10, 0, false);
	}
	
	@Override
	public void render(Entity entity, float a, float b, float c, float d, float e, float scale)
	{
		this.body.render(scale);
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
		this.body.rotateAngleY = (float) (Math.PI/180)*netHeadYaw;
    }
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) 
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}