package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.block.machine.BlockWeaponTable;
import com.weedycow.arkworld.capability.CapabilityWeapon;
import com.weedycow.arkworld.item.material.devices.Device;
import com.weedycow.arkworld.item.normal.*;
import com.weedycow.arkworld.item.tool.MeleeWeapon;
import com.weedycow.arkworld.item.tool.RangeWeapon;
import com.weedycow.arkworld.item.tool.ShieldWeapon;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class WeaponTableContainer extends Container implements IButton
{
    TileEntity tile;
    EntityPlayer player;

    public WeaponTableContainer(TileEntity tile, EntityPlayer player)
    {
        this.player = player;
        this.tile =  tile;

        for(int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 10 + i * 18, 176));
        }

        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.addSlotToContainer(new Slot(player.inventory, j1 + (l + 1) * 9, 10 + j1 * 18, 118 + l * 18));
            }
        }

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 0, 23, 86)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 1;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 1, 142, 86)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn)
    {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 36)
            {
                if (!this.mergeItemStack(itemstack1, 36, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 36, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public void onButtonPress(int buttonID)
    {
        if (buttonID==0 && tile instanceof BlockWeaponTable.TileWeaponTable)
        {
            ItemStackHandler slot = ((BlockWeaponTable.TileWeaponTable) tile).getSlot();

            ItemStack weapon = slot.getStackInSlot(0);

            ItemStack material = slot.getStackInSlot(1);

            if((weapon.getItem() instanceof MeleeWeapon || weapon.getItem() instanceof RangeWeapon || weapon.getItem() instanceof ShieldWeapon) && weapon.getCount()==1 && weapon.hasCapability(CapabilityRegistry.capWeapon,null))
            {
                CapabilityWeapon.Process wp = new CapabilityWeapon.Process(weapon);
                CapabilityWeapon.Process mp = new CapabilityWeapon.Process(material);

                if(mp.getRank()>=1 && (material.getItem() instanceof NormalWeaponMod || material.getItem() instanceof HighWeaponMod || material.getItem() instanceof EpicWeaponMod) && wp.getRank()==0 ? wp.getMods()<1 : wp.getMods()<wp.getRank()*2)
                {
                    wp.add(CapabilityWeapon.Process.Buffs.getBuff(mp.getModBuff()), mp.getModLevel());
                    wp.addMods(1);
                    material.shrink(1);
                }

                if(material.getItem() instanceof WeaponGem && wp.getLevel()<wp.getMaxLevel())
                {
                    if(material.getCount()*100>wp.getExpNeed()-wp.getExperience())
                    {
                        material.shrink((wp.getExpNeed()-wp.getExperience())/100);
                        wp.addExperience(wp.getExpNeed()-wp.getExperience());
                    }
                    else
                    {
                        wp.addExperience(material.getCount()*100);
                        material.shrink(material.getCount());
                    }
                }

                if((material.getItem() instanceof MeleeWeapon || material.getItem() instanceof RangeWeapon || material.getItem() instanceof ShieldWeapon) && material.hasCapability(CapabilityRegistry.capWeapon,null) && wp.getLevel()<wp.getMaxLevel())
                {
                    wp.addExperience(mp.getMaterialExperience());
                    material.shrink(1);
                }

                if(material.getItem() instanceof WeaponFixGem && wp.getLoss()<wp.getDurability())
                {
                    if(material.getCount()*10>wp.getDurability()-wp.getLoss())
                    {
                        material.shrink((wp.getDurability()-wp.getLoss())/10);
                        wp.reduceLoss(wp.getDurability()-wp.getLoss());
                    }
                    else
                    {
                        wp.reduceLoss(material.getCount()*10);
                        material.shrink(material.getCount());
                    }
                }

                if(material.getItem() instanceof Device)
                {
                    wp.clear();
                    wp.setMods(0);
                    material.shrink(1);
                }

                weapon.updateAnimation(player.world,player,0,false);
            }

            tile.markDirty();
        }
    }
}
