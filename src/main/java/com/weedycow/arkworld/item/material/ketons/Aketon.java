package com.weedycow.arkworld.item.material.ketons;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class Aketon extends Item
{
	public Aketon()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".aketon");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("aketon");
		this.setMaxStackSize(64);
	}
}
