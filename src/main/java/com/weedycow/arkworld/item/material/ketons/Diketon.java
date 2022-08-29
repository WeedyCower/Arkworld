package com.weedycow.arkworld.item.material.ketons;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class Diketon extends Item
{
	public Diketon()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".diketon");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("diketon");
		this.setMaxStackSize(64);
	}
}
