package com.weedycow.arkworld.item.material.rmas;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class Rmar extends Item
{
	public Rmar()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".rmar");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("rmar");
		this.setMaxStackSize(64);
	}
}
