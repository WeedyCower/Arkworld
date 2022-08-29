package com.weedycow.arkworld.item.material.crystallines;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class CystallineComponent extends Item
{
	public CystallineComponent()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".cystallineComponent");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("cystalline_component");
		this.setMaxStackSize(64);
	}
}
