package com.weedycow.arkworld.item.food;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.ItemFood;

public class SmallMeatCooked extends ItemFood
{
    public SmallMeatCooked()
    {
        super(5, 0, true);
        this.setAlwaysEdible();
        this.setCreativeTab(ArkCreateTab.FOOD);
        this.setUnlocalizedName(Arkworld.MODID + ".smallMeatCooked");
        this.setRegistryName("small_meat_cooked");
        this.setMaxStackSize(64);
    }
}
