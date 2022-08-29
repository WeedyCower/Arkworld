package com.weedycow.arkworld.item.material.sugars;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class SugarSubstitute extends Item
{
	public SugarSubstitute()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".sugarSubstitute");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("sugar_substitute");
		this.setMaxStackSize(64);
	}
}
