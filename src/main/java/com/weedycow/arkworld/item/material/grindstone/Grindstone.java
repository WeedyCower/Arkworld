package com.weedycow.arkworld.item.material.grindstone;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class Grindstone extends Item
{
	public Grindstone()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".grindstone");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("grindstone");
		this.setMaxStackSize(64);
	}
}
