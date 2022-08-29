package com.weedycow.arkworld.item.material.esters;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class Ester extends Item
{
		public Ester()	
		{
			this.setUnlocalizedName(Arkworld.MODID + ".ester");
			this.setCreativeTab(ArkCreateTab.MATERIAL);
			this.setRegistryName("ester");
			this.setMaxStackSize(64);
		}
}
