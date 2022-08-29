package com.weedycow.arkworld.item.food;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.ItemFood;

public class Cream extends ItemFood
{
    public Cream()
    {
        super(2, 1, false);
        this.setCreativeTab(ArkCreateTab.FOOD);
        this.setUnlocalizedName(Arkworld.MODID + ".cream");
        this.setRegistryName("cream");
        this.setMaxStackSize(64);
    }
}
