package com.weedycow.arkworld.block.nature.plant;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.block.BlockVine;
import net.minecraft.block.SoundType;

public class BlockMyceliumVine extends BlockVine
{
    public BlockMyceliumVine()
    {
        this.setDefaultState(this.blockState.getBaseState().withProperty(UP, Boolean.valueOf(false)).withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)));
        this.setUnlocalizedName(Arkworld.MODID + ".myceliumVine");
        this.setCreativeTab(ArkCreateTab.BLOCK);
        this.setSoundType(SoundType.PLANT);
        this.setRegistryName("mycelium_vine");
        this.setTickRandomly(true);
        this.setHardness(0);
    }
}
