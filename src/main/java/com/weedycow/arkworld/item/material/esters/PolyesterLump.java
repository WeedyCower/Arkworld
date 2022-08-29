package com.weedycow.arkworld.item.material.esters;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class PolyesterLump extends Item
{
		public PolyesterLump()	
		{
			this.setUnlocalizedName(Arkworld.MODID + ".polyesterLump");
			this.setCreativeTab(ArkCreateTab.MATERIAL);
			this.setRegistryName("polyester_lump");
			this.setMaxStackSize(64);
		}
}
