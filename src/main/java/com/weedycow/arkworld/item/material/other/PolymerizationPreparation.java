package com.weedycow.arkworld.item.material.other;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class PolymerizationPreparation extends Item
{
	public PolymerizationPreparation()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".polymerizationPreparation");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("polymerization_preparation");
		this.setMaxStackSize(64);
	}
}
