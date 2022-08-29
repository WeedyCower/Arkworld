package com.weedycow.arkworld.block.nature.surface;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDerelictDirt extends Block
{
    public BlockDerelictDirt()
    {
        super(Material.GROUND);
        this.setHardness(1f);
        this.setResistance(0.5f);
        this.setUnlocalizedName(Arkworld.MODID + ".derelictDirt");
        this.setCreativeTab(ArkCreateTab.BLOCK);
        this.setHarvestLevel("shovel",1);
        this.setRegistryName("derelict_dirt");
    }
}
