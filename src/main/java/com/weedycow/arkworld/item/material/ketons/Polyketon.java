package com.weedycow.arkworld.item.material.ketons;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class Polyketon extends Item
{
	public Polyketon()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".polyketon");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("polyketon");
		this.setMaxStackSize(64);
	}
}
