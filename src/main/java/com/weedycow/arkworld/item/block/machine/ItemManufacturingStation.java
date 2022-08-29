package com.weedycow.arkworld.item.block.machine;

import com.weedycow.arkworld.registry.BlockRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.util.ResourceLocation;

public class ItemManufacturingStation extends ItemMachine
{
    public ItemManufacturingStation()
    {
        super(BlockRegistry.MANUFACTURING_STATION);
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("normal_block");
    }
}
