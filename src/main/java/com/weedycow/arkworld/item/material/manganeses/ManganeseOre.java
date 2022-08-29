package com.weedycow.arkworld.item.material.manganeses;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class ManganeseOre extends Item
{
	public ManganeseOre()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".manganeseOre");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("manganese_ore");
		this.setMaxStackSize(64);
	}
}
