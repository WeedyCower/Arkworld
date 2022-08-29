package com.weedycow.arkworld.item.material.crystallines;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class CrystallineCircuit extends Item
{
	public CrystallineCircuit()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".crystallineCircuit");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("crystalline_circuit");
		this.setMaxStackSize(64);
	}
}
