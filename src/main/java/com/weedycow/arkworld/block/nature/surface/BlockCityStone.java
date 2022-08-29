package com.weedycow.arkworld.block.nature.surface;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCityStone extends Block
{
    public BlockCityStone()
    {
        super(Material.ROCK);
        this.setHardness(2f);
        this.setResistance(3f);
        this.setUnlocalizedName(Arkworld.MODID + ".cityStone");
        this.setCreativeTab(ArkCreateTab.BLOCK);
        this.setHarvestLevel("pickaxe",1);
        this.setRegistryName("city_stone");
    }
}
