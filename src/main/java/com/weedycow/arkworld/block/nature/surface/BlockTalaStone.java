package com.weedycow.arkworld.block.nature.surface;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockTalaStone extends Block
{
    public BlockTalaStone()
    {
        super(Material.GROUND);
        this.setUnlocalizedName(Arkworld.MODID + ".talaStone");
        this.setCreativeTab(ArkCreateTab.BLOCK);
        this.setHarvestLevel("pickaxe",0);
        this.setRegistryName("tala_stone");
        this.setHardness(1f);
        this.setResistance(2f);
    }
}
