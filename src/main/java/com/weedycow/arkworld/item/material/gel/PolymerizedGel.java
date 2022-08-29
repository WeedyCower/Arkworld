package com.weedycow.arkworld.item.material.gel;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class PolymerizedGel extends Item
{
	public PolymerizedGel()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".polymerizedGel");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("polymerized_gel");
		this.setMaxStackSize(64);
	}
}
