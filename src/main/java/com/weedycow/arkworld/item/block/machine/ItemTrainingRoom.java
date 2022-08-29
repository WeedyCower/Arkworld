package com.weedycow.arkworld.item.block.machine;

import com.weedycow.arkworld.registry.BlockRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.util.ResourceLocation;

public class ItemTrainingRoom extends ItemMachine
{
    public ItemTrainingRoom()
    {
        super(BlockRegistry.TRAINING_ROOM);
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("normal_block");
    }
}
