package com.weedycow.arkworld.item.block.doll;

import com.weedycow.arkworld.item.block.ArkItemBlock;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public abstract class ArkItemDoll extends ArkItemBlock
{
    public ArkItemDoll(Block block)
    {
        super(block);
        this.setMaxStackSize(1);
    }

    @Override
    public void setId()
    {
        this.id = block.getRegistryName().getResourcePath().replace("_doll","");
    }

    @Override
    public void setRegistryNames(String id)
    {
        this.setRegistryName(id+"_doll");
    }

    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag)
    {
        super.addInformation(itemstack, world, list, flag);
        list.add(I18n.format("item.arkworld.info.item_doll"));
        list.add(I18n.format("item.arkworld.info.doll_block"));
    }
}
