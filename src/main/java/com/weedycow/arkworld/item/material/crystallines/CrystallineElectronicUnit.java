package com.weedycow.arkworld.item.material.crystallines;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class CrystallineElectronicUnit extends Item
{
	public CrystallineElectronicUnit()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".crystallineElectronicUnit");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("crystalline_electronic_unit");
		this.setMaxStackSize(64);
	}
}
