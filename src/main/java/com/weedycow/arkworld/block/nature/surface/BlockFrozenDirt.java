package com.weedycow.arkworld.block.nature.surface;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockFrozenDirt extends Block
{
    public BlockFrozenDirt()
    {
        super(Material.GROUND);
        this.setHardness(2f);
        this.setResistance(2f);
        this.setUnlocalizedName(Arkworld.MODID + ".frozenDirt");
        this.setCreativeTab(ArkCreateTab.BLOCK);
        this.setHarvestLevel("shovel",2);
        this.setRegistryName("frozen_dirt");
    }
}
