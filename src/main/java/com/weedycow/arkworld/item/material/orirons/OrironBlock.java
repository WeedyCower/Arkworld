package com.weedycow.arkworld.item.material.orirons;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class OrironBlock extends Item
{
	public OrironBlock()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".orironBlock");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("oriron_block");
		this.setMaxStackSize(64);
	}
}
