package com.weedycow.arkworld.item.tool.tool;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.ItemAxe;

public class RmaAxe extends ItemAxe
{
    public RmaAxe()
    {
        super(OrironPickaxe.MATERIAL,6.0F,-3.0F);
        this.setUnlocalizedName(Arkworld.MODID + ".rmaAxe");
        this.setCreativeTab(ArkCreateTab.TOLL);
        this.setRegistryName("rma_axe");
    }
}
