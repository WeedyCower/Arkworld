package com.weedycow.arkworld.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidRegistry
{
    public static final Fluid ORIGINIUM_CONTAMINATED_WATER = new Fluid("originium_contaminated_water",
            new ResourceLocation("arkworld","blocks/originium_contaminated_water"), new ResourceLocation("arkworld","blocks/originium_contaminated_water"))
            .setLuminosity(0).setDensity(1500).setViscosity(500).setGaseous(false);

    public static final Fluid IBERIA_WATER = new Fluid("iberia_water",
            new ResourceLocation("arkworld","blocks/iberia_water"), new ResourceLocation("arkworld","blocks/iberia_water"))
            .setLuminosity(0).setDensity(1500).setViscosity(500).setGaseous(false);

    public static void onRegistry()
    {
        net.minecraftforge.fluids.FluidRegistry.registerFluid(ORIGINIUM_CONTAMINATED_WATER);
        net.minecraftforge.fluids.FluidRegistry.addBucketForFluid(ORIGINIUM_CONTAMINATED_WATER);
        net.minecraftforge.fluids.FluidRegistry.registerFluid(IBERIA_WATER);
        net.minecraftforge.fluids.FluidRegistry.addBucketForFluid(IBERIA_WATER);
    }
}
