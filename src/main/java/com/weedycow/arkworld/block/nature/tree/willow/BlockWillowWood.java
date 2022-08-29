package com.weedycow.arkworld.block.nature.tree.willow;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockWillowWood extends Block
{
    public BlockWillowWood()
    {
        super(Material.WOOD);
        this.setUnlocalizedName(Arkworld.MODID + ".willowWood");
        this.setCreativeTab(ArkCreateTab.BLOCK);
        this.setRegistryName("willow_wood");
        this.setHarvestLevel("axe",1);
        this.setSoundType(SoundType.WOOD);
        this.setHardness(3.0f);
    }
}
