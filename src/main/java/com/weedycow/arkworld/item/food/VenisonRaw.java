package com.weedycow.arkworld.item.food;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.ItemFood;

public class VenisonRaw extends ItemFood
{
    public VenisonRaw()
    {
        super(1, 0, true);
        this.setAlwaysEdible();
        this.setCreativeTab(ArkCreateTab.FOOD);
        this.setUnlocalizedName(Arkworld.MODID + ".venisonRaw");
        this.setRegistryName("venison_raw");
        this.setMaxStackSize(64);
    }
}
