package com.weedycow.arkworld.item.material.other;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class BipolarNanoflake extends Item
{
	public BipolarNanoflake()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".bipolarNanoflake");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("bipolar_nanoflake");
		this.setMaxStackSize(64);
	}
}
