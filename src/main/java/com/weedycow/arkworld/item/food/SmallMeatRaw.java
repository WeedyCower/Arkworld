package com.weedycow.arkworld.item.food;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.ItemFood;

public class SmallMeatRaw extends ItemFood
{
    public SmallMeatRaw()
    {
        super(1, 0, true);
        this.setAlwaysEdible();
        this.setCreativeTab(ArkCreateTab.FOOD);
        this.setUnlocalizedName(Arkworld.MODID + ".smallMeatRaw");
        this.setRegistryName("small_meat_raw");
        this.setMaxStackSize(64);
    }
}
