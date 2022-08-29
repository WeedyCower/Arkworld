package com.weedycow.arkworld.item.block.machine;

import com.weedycow.arkworld.registry.BlockRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.util.ResourceLocation;

public class ItemPowerStation extends ItemMachine
{
    public ItemPowerStation()
    {
        super(BlockRegistry.POWER_STATION);
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("normal_block");
    }
}
