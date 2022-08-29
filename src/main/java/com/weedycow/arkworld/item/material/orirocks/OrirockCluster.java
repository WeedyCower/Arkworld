package com.weedycow.arkworld.item.material.orirocks;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class OrirockCluster extends Item
{
	public OrirockCluster()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".orirockCluster");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("orirock_cluster");
		this.setMaxStackSize(64);
	}
}
