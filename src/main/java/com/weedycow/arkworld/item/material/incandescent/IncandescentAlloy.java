package com.weedycow.arkworld.item.material.incandescent;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class IncandescentAlloy extends Item
{
	public IncandescentAlloy()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".incandescentAlloy");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("incandescent_alloy");
		this.setMaxStackSize(64);
	}
}
