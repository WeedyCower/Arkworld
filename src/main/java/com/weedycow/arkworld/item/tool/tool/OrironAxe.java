package com.weedycow.arkworld.item.tool.tool;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.ItemAxe;

public class OrironAxe extends ItemAxe
{
    public OrironAxe()
    {
        super(OrironPickaxe.MATERIAL,8.0F,-3.0F);
        this.setUnlocalizedName(Arkworld.MODID + ".orironAxe");
        this.setCreativeTab(ArkCreateTab.TOLL);
        this.setRegistryName("oriron_axe");
    }
}
