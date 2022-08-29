package com.weedycow.arkworld.item.normal;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.capability.CapabilityValue;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class LmbHundred extends NormalItem
{
    public LmbHundred()
    {
        super("lmb_hundred", ArkCreateTab.ITEM,64);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);

        CapabilityValue.Process value = new CapabilityValue.Process(playerIn);

        if(stack.getItem()==this)
        {
            value.addLmb(100);

            stack.shrink(1);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
