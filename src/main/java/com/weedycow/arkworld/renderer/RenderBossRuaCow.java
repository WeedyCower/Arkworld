package com.weedycow.arkworld.renderer;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.hypergryph.BossRuaCow;
import com.weedycow.arkworld.model.ModelBossRuaCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBossRuaCow extends RenderLiving
{
	private static final ResourceLocation BOOS_RUA_COW_TEXTURE = 
			new ResourceLocation(Arkworld.MODID + ":textures/entities/" + BossRuaCow.ID + ".png");
	
	public RenderBossRuaCow(RenderManager manager)
	{
		super(manager,new ModelBossRuaCow(),0.5f);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return BOOS_RUA_COW_TEXTURE;
	}
}