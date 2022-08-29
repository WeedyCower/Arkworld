package com.weedycow.arkworld.item.block.machine;

import com.weedycow.arkworld.registry.BlockRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.util.ResourceLocation;

public class ItemControlCenter extends ItemMachine
{
    public ItemControlCenter()
    {
        super(BlockRegistry.CONTROL_CENTER);
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("normal_block");
    }
}
