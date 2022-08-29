package com.weedycow.arkworld.block.nature.tree.huge;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockHugeWood extends Block
{
    public BlockHugeWood()
    {
        super(Material.WOOD);
        this.setUnlocalizedName(Arkworld.MODID + ".hugeWood");
        this.setCreativeTab(ArkCreateTab.BLOCK);
        this.setRegistryName("huge_wood");
        this.setHarvestLevel("axe",1);
        this.setSoundType(SoundType.WOOD);
        this.setHardness(3.0f);
    }
}
