package com.weedycow.arkworld.item.material.orirocks;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class Orirock extends Item
{
	public Orirock()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".orirock");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("orirock");
		this.setMaxStackSize(64);
	}
}
