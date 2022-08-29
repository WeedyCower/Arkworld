package com.weedycow.arkworld.item.material.orirons;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class OrironShard extends Item
{
	public OrironShard()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".orironShard");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("oriron_shard");
		this.setMaxStackSize(64);
	}
}
