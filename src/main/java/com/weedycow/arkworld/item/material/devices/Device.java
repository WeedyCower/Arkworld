package com.weedycow.arkworld.item.material.devices;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class Device extends Item
{
	public Device()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".device");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("device");
		this.setMaxStackSize(64);
	}
}
