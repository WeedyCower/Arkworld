package com.weedycow.arkworld.item.block.floor;

import com.weedycow.arkworld.item.block.ArkItemBlock;
import com.weedycow.arkworld.registry.BlockRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.util.ResourceLocation;

public class ItemOriginiumFloor extends ArkItemBlock
{
    public ItemOriginiumFloor()
    {
        super(BlockRegistry.ORIGINIUM_FLOOR);
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("normal_block");
    }
}
