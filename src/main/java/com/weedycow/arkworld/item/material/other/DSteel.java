package com.weedycow.arkworld.item.material.other;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class DSteel extends Item
{
	public DSteel()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".dSteel");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("dsteel");
		this.setMaxStackSize(64);
	}
}
