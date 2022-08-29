package com.weedycow.arkworld.util;

import com.weedycow.arkworld.entity.weapon.Arrow;
import com.weedycow.arkworld.entity.weapon.Bomb;
import com.weedycow.arkworld.item.tool.ItemArrow;
import com.weedycow.arkworld.item.tool.ItemBomb;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import javax.annotation.Nullable;
import java.util.List;

public class ArkItemUtil
{
    public static boolean isEqualSize(List<ItemStack> oriStacks, List<ItemStack> tables)
    {
        int oriStackSize = 0;

        int tableSize = 0;

        for(ItemStack oriStack : oriStacks)
        {
            if(oriStack!=ItemStack.EMPTY && !oriStack.equals(new ItemStack(Items.AIR))) oriStackSize+=1;
        }

        for(ItemStack table : tables)
        {
            if(table!=ItemStack.EMPTY && !table.equals(new ItemStack(Items.AIR))) tableSize+=1;
        }

        return oriStackSize==tableSize;
    }

    public static ItemStack findItemBombInPlayerSlot(EntityPlayer player, @Nullable Bomb.Type[] types)
    {
        if(types==null)
        {
            if ((player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemBomb))
            {
                return player.getHeldItem(EnumHand.OFF_HAND);
            }
            else if ((player.getHeldItem(EnumHand.MAIN_HAND)).getItem() instanceof ItemBomb)
            {
                return player.getHeldItem(EnumHand.MAIN_HAND);
            }
            else
            {
                for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
                {
                    ItemStack itemstack = player.inventory.getStackInSlot(i);

                    if (itemstack.getItem() instanceof ItemBomb)
                    {
                        return itemstack;
                    }
                }
                return ItemStack.EMPTY;
            }
        }
        else
        {
            for (Bomb.Type type : types)
            {
                if ((player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemBomb && ((ItemBomb) player.getHeldItem(EnumHand.OFF_HAND).getItem()).getType() == type))
                {
                    return player.getHeldItem(EnumHand.OFF_HAND);
                }
                else if ((player.getHeldItem(EnumHand.MAIN_HAND)).getItem() instanceof ItemBomb && ((ItemBomb) player.getHeldItem(EnumHand.MAIN_HAND).getItem()).getType() == type)
                {
                    return player.getHeldItem(EnumHand.MAIN_HAND);
                }
                else
                {
                    for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
                    {
                        ItemStack itemstack = player.inventory.getStackInSlot(i);

                        if (itemstack.getItem() instanceof ItemBomb && ((ItemBomb) itemstack.getItem()).getType() == type)
                        {
                            return itemstack;
                        }
                    }
                    return ItemStack.EMPTY;
                }
            }
            return ItemStack.EMPTY;
        }
    }

    public static ItemStack findItemArrowInPlayerSlot(EntityPlayer player, @Nullable Arrow.Type[] types)
    {
        if(types==null)
        {
            if ((player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemArrow))
            {
                return player.getHeldItem(EnumHand.OFF_HAND);
            }
            else if ((player.getHeldItem(EnumHand.MAIN_HAND)).getItem() instanceof ItemArrow)
            {
                return player.getHeldItem(EnumHand.MAIN_HAND);
            }
            else
            {
                for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
                {
                    ItemStack itemstack = player.inventory.getStackInSlot(i);

                    if (itemstack.getItem() instanceof ItemArrow)
                    {
                        return itemstack;
                    }
                }
                return ItemStack.EMPTY;
            }
        }
        else
        {
            for (Arrow.Type type : types)
            {
                if ((player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemArrow && ((ItemArrow) player.getHeldItem(EnumHand.OFF_HAND).getItem()).getType() == type))
                {
                    return player.getHeldItem(EnumHand.OFF_HAND);
                }
                else if ((player.getHeldItem(EnumHand.MAIN_HAND)).getItem() instanceof ItemArrow && ((ItemArrow) player.getHeldItem(EnumHand.MAIN_HAND).getItem()).getType() == type)
                {
                    return player.getHeldItem(EnumHand.MAIN_HAND);
                }
                else
                {
                    for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
                    {
                        ItemStack itemstack = player.inventory.getStackInSlot(i);

                        if (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).getType() == type)
                        {
                            return itemstack;
                        }
                    }
                    return ItemStack.EMPTY;
                }
            }
            return ItemStack.EMPTY;
        }
    }

    public static ItemStack findItemStackInPlayerSlot(EntityPlayer player, Class <? extends Item> item)
    {
        if ((player.getHeldItem(EnumHand.OFF_HAND).getItem().getClass() == item))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if ((player.getHeldItem(EnumHand.MAIN_HAND)).getItem().getClass() == item)
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (itemstack.getItem().getClass() == item)
                {
                    return itemstack;
                }
            }
            return ItemStack.EMPTY;
        }
    }

    public static ItemStack findItemStackInEntityHand(EntityLivingBase player, Class <? extends Item> item)
    {
        if ((player.getHeldItem(EnumHand.OFF_HAND).getItem().getClass() == item))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if ((player.getHeldItem(EnumHand.MAIN_HAND)).getItem().getClass() == item)
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
            return ItemStack.EMPTY;
    }
}
