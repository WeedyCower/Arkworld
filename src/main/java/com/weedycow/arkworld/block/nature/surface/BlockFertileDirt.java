package com.weedycow.arkworld.block.nature.surface;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockFertileDirt extends Block
{
    public BlockFertileDirt()
    {
        super(Material.GROUND);
        this.setHardness(0.5f);
        this.setUnlocalizedName(Arkworld.MODID + ".fertileDirt");
        this.setCreativeTab(ArkCreateTab.BLOCK);
        this.setHarvestLevel("shovel",0);
        this.setRegistryName("fertile_dirt");
    }
}
