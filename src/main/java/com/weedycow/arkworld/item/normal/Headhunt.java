package com.weedycow.arkworld.item.normal;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class Headhunt extends Item
{
	public Headhunt()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".headhunt");
		this.setCreativeTab(ArkCreateTab.ITEM);
		this.setRegistryName("headhunt");
		this.setMaxStackSize(60);
	}
}
