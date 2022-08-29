package com.weedycow.arkworld.item.material.kohls;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class LoxicKohl extends Item
{
	public LoxicKohl()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".loxicKohl");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("loxic_kohl");
		this.setMaxStackSize(64);
	}
}
