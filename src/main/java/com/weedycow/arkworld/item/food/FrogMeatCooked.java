package com.weedycow.arkworld.item.food;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.ItemFood;

public class FrogMeatCooked extends ItemFood
{
    public FrogMeatCooked()
    {
        super(3, 1, true);
        this.setAlwaysEdible();
        this.setCreativeTab(ArkCreateTab.FOOD);
        this.setUnlocalizedName(Arkworld.MODID + ".frogMeatCooked");
        this.setRegistryName("frog_meat_cooked");
        this.setMaxStackSize(64);
    }
}
