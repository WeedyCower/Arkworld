package com.weedycow.arkworld.block.nature.surface;

import com.weedycow.arkworld.Arkworld;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockOriginiumContaminatedWater extends BlockFluidClassic
{
    public BlockOriginiumContaminatedWater(Fluid fluid, Material material)
    {
        super(fluid, material);
        this.setRegistryName("originium_contaminated_water");
        this.setUnlocalizedName(Arkworld.MODID + ".originiumContaminatedWater");
    }
}
