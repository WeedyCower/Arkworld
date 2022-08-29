package com.weedycow.arkworld.item.material.devices;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class DamagedDevice extends Item
{
	public DamagedDevice()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".damagedDevice");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("damaged_device");
		this.setMaxStackSize(64);
	}
}
