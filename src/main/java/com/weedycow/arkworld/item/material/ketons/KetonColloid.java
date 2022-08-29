package com.weedycow.arkworld.item.material.ketons;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class KetonColloid extends Item
{
	public KetonColloid()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".ketonColloid");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("keton_colloid");
		this.setMaxStackSize(64);
	}
}
