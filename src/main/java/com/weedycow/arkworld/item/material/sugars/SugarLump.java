package com.weedycow.arkworld.item.material.sugars;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class SugarLump extends Item
{
	public SugarLump()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".sugarLump");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("sugar_lump");
		this.setMaxStackSize(64);
	}
}
