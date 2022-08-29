package com.weedycow.arkworld.item.material.gel;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class CoagulatingGel extends Item
{
	public CoagulatingGel()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".coagulatingGel");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("coagulating_gel");
		this.setMaxStackSize(64);
	}
}
