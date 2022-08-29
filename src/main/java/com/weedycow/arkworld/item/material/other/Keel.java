package com.weedycow.arkworld.item.material.other;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class Keel extends Item
{
	public Keel()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".keel");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("keel");
		this.setMaxStackSize(64);
	}
}
