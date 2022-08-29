package com.weedycow.arkworld.item.normal;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class HeadhuntTen extends Item
{
	public HeadhuntTen()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".headhuntTen");
		this.setCreativeTab(ArkCreateTab.ITEM);
		this.setRegistryName("headhunt_ten");
		this.setMaxStackSize(30);
	}
}
