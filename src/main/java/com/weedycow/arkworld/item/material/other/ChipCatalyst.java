package com.weedycow.arkworld.item.material.other;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class ChipCatalyst extends Item
{
	public ChipCatalyst()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".chipCatalyst");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("chip_catalyst");
		this.setMaxStackSize(64);
	}
}
