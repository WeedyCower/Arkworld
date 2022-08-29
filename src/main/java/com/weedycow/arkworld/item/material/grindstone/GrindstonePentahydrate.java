package com.weedycow.arkworld.item.material.grindstone;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class GrindstonePentahydrate extends Item
{
	public GrindstonePentahydrate()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".grindstonePentahydrate");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("grindstone_pentahydrate");
		this.setMaxStackSize(64);
	}
}
