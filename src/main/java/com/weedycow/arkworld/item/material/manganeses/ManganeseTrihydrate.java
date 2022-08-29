package com.weedycow.arkworld.item.material.manganeses;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class ManganeseTrihydrate extends Item
{
	public ManganeseTrihydrate()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".manganeseTrihydrate");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("manganese_trihydrate");
		this.setMaxStackSize(64);
	}
}
