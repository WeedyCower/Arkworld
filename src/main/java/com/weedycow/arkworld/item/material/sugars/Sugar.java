package com.weedycow.arkworld.item.material.sugars;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class Sugar extends Item
{
	public Sugar()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".sugar");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("sugar");
		this.setMaxStackSize(64);
	}
}
