package com.weedycow.arkworld.item.material.esters;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class PolyesterPack extends Item
{
		public PolyesterPack()	
		{
			this.setUnlocalizedName(Arkworld.MODID + ".polyesterPack");
			this.setCreativeTab(ArkCreateTab.MATERIAL);
			this.setRegistryName("polyester_pack");
			this.setMaxStackSize(64);
		}
}
