package com.weedycow.arkworld.block.nature.surface;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockIberiaDirt extends Block
{
    public BlockIberiaDirt()
    {
        super(Material.GROUND);
        this.setUnlocalizedName(Arkworld.MODID + ".iberiaDirt");
        this.setCreativeTab(ArkCreateTab.BLOCK);
        this.setHarvestLevel("shovel",0);
        this.setRegistryName("iberia_dirt");
        this.setHardness(0.5f);
    }
}
