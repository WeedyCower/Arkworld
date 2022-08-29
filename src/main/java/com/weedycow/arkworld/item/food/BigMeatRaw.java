package com.weedycow.arkworld.item.food;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.ItemFood;

public class BigMeatRaw extends ItemFood
{
    public BigMeatRaw()
    {
        super(1, 0, true);
        this.setAlwaysEdible();
        this.setCreativeTab(ArkCreateTab.FOOD);
        this.setUnlocalizedName(Arkworld.MODID + ".bigMeatRaw");
        this.setRegistryName("big_meat_raw");
        this.setMaxStackSize(64);
    }
}
