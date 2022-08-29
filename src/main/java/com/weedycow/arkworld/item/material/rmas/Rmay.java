package com.weedycow.arkworld.item.material.rmas;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class Rmay extends Item
{
	public Rmay()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".rmay");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("rmay");
		this.setMaxStackSize(64);
	}
}
