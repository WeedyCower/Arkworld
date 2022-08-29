package com.weedycow.arkworld.item.material.sugars;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class SugarPack extends Item
{
	public SugarPack()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".sugarPack");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("sugar_pack");
		this.setMaxStackSize(64);
	}
}
