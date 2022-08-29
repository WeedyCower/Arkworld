package com.weedycow.arkworld.item.tool.tool;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.ItemSpade;

public class RmaShovel extends ItemSpade
{
    public RmaShovel()
    {
        super(OrironPickaxe.MATERIAL);
        this.setUnlocalizedName(Arkworld.MODID + ".rmaShovel");
        this.setCreativeTab(ArkCreateTab.TOLL);
        this.setRegistryName("rma_shovel");
    }
}
