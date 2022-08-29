package com.weedycow.arkworld.item.material.esters;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class Polyester extends Item
{
	
		public Polyester()	
		{
			this.setUnlocalizedName(Arkworld.MODID + ".polyester");
			this.setCreativeTab(ArkCreateTab.MATERIAL);
			this.setRegistryName("polyester");
			this.setMaxStackSize(64);
		}
	
}
