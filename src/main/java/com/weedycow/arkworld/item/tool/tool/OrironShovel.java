package com.weedycow.arkworld.item.tool.tool;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.ItemSpade;

public class OrironShovel extends ItemSpade
{
    public OrironShovel()
    {
        super(OrironPickaxe.MATERIAL);
        this.setUnlocalizedName(Arkworld.MODID + ".orironShovel");
        this.setCreativeTab(ArkCreateTab.TOLL);
        this.setRegistryName("oriron_shovel");
    }
}
