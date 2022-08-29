package com.weedycow.arkworld.item.material.orirons;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class Oriron extends Item
{
	public Oriron()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".oriron");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("oriron");
		this.setMaxStackSize(64);
	}
}
