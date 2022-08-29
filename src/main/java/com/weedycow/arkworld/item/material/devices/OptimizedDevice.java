package com.weedycow.arkworld.item.material.devices;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class OptimizedDevice extends Item
{
	public OptimizedDevice()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".optimizedDevice");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("optimized_device");
		this.setMaxStackSize(64);
	}
}
