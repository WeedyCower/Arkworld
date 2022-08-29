package com.weedycow.arkworld.item.material.kohls;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class WhiteHorseKohl extends Item
{
	public WhiteHorseKohl()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".whiteHorseKohl");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("white_horse_kohl");
		this.setMaxStackSize(64);
	}
}
