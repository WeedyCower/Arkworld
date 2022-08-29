package com.weedycow.arkworld.item.material.orirocks;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class OrirockConcentration extends Item
{
	public OrirockConcentration()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".orirockConcentration");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("orirock_concentration");
		this.setMaxStackSize(64);
	}
}
