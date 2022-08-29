package com.weedycow.arkworld.item.material.devices;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class IntegratedDevice extends Item
{
	public IntegratedDevice()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".integratedDevice");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("integrated_device");
		this.setMaxStackSize(64);
	}
}
