package com.weedycow.arkworld.item.material.orirocks;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;

public class OrirockCube extends Item
{
	public OrirockCube()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".orirockCube");
		this.setCreativeTab(ArkCreateTab.MATERIAL);
		this.setRegistryName("orirock_cube");
		this.setMaxStackSize(64);
	}
}
