package com.weedycow.arkworld.registry;

import com.weedycow.arkworld.world.arkworld.world.ArkWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class WorldGenRegistry
{
    public static final String ARKWWORLD_NAME = "arkworld";
    public static final int ARKWORLD_DIM_ID = 36888;
    public static final DimensionType ARKWORLD_DIM_TYPE = DimensionType.register(ARKWWORLD_NAME, "_"+ARKWWORLD_NAME, ARKWORLD_DIM_ID, ArkWorldProvider.class, true);

    public static void registerDimensions()
    {
        DimensionManager.registerDimension(ARKWORLD_DIM_ID, ARKWORLD_DIM_TYPE);
    }
}
