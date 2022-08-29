package com.weedycow.arkworld.item.material.incandescent;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class IncandescentAlloyBlock extends Item
{
	public IncandescentAlloyBlock()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".incandescentAlloyBlock");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("incandescent_alloy_block");
		this.setMaxStackSize(64);
	}
}
