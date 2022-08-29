package com.weedycow.arkworld.block.machine;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMachineBase extends Block
{
    public BlockMachineBase()
    {
        super(Material.IRON);
        this.setHardness(3f);
        this.setResistance(5f);
        this.setUnlocalizedName(Arkworld.MODID + ".machineBase");
        this.setCreativeTab(ArkCreateTab.DEVICE);
        this.setHarvestLevel("pickaxe",1);
        this.setRegistryName("machine_base");
    }
}
