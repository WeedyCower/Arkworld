package com.weedycow.arkworld.item.food;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class Cheese extends ItemFood
{
    public Cheese()
    {
        super(2, 1, true);
        this.setAlwaysEdible();
        this.setCreativeTab(ArkCreateTab.FOOD);
        this.setUnlocalizedName(Arkworld.MODID + ".cheese");
        this.setRegistryName("cheese");
        this.setMaxStackSize(64);
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}
