package com.weedycow.arkworld.item.food;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.ItemFood;

public class BigMeatCooked extends ItemFood
{
    public BigMeatCooked()
    {
        super(8, 4, true);
        this.setAlwaysEdible();
        this.setCreativeTab(ArkCreateTab.FOOD);
        this.setUnlocalizedName(Arkworld.MODID + ".bigMeatCooked");
        this.setRegistryName("big_meat_cooked");
        this.setMaxStackSize(64);
    }
}
