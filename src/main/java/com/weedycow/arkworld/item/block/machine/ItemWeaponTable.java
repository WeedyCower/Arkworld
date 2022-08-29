package com.weedycow.arkworld.item.block.machine;

import com.weedycow.arkworld.item.block.ArkItemBlock;
import com.weedycow.arkworld.registry.BlockRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.util.ResourceLocation;

public class ItemWeaponTable extends ArkItemBlock
{
    public ItemWeaponTable()
    {
        super(BlockRegistry.WEAPON_TABLE);
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("normal_block");
    }
}
