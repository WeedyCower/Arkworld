package com.weedycow.arkworld.item.material.orirons;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class OrironCluster extends Item
{
	public OrironCluster()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".orironCluster");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("oriron_cluster");
		this.setMaxStackSize(64);
	}
}
