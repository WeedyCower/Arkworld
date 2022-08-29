package com.weedycow.arkworld.block.nature.surface;

import com.weedycow.arkworld.Arkworld;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockIberiaWater extends BlockFluidClassic
{
    public BlockIberiaWater(Fluid fluid, Material material)
    {
        super(fluid, material);
        this.setRegistryName("iberia_water");
        this.setUnlocalizedName(Arkworld.MODID + ".iberiaWater");
    }
}
