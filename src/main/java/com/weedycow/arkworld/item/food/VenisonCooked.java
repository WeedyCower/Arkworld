package com.weedycow.arkworld.item.food;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.ItemFood;

public class VenisonCooked extends ItemFood
{
    public VenisonCooked()
    {
        super(8, 4, true);
        this.setAlwaysEdible();
        this.setCreativeTab(ArkCreateTab.FOOD);
        this.setUnlocalizedName(Arkworld.MODID + ".venisonCooked");
        this.setRegistryName("venison_cooked");
        this.setMaxStackSize(64);
    }
}
