package com.weedycow.arkworld.item.normal;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class OriginiumShard extends Item
{
	public OriginiumShard()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".originiumShard");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("originium_shard");
		this.setMaxStackSize(64);
	}
}
