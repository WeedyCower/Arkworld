package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.block.machine.infrastructure.BlockManufacturingStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ManufacturingStationContainer extends Container implements IButton
{
    TileEntity tile;
    EntityPlayer player;

    public ManufacturingStationContainer(TileEntity tile, EntityPlayer player)
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

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 0, 23, 23)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 1, 23, 43)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 2, 142, 33)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
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
        if(buttonID==0)
        {
            if(tile instanceof BlockManufacturingStation.TileManufacturingStation && ((BlockManufacturingStation.TileManufacturingStation) tile).getCenter()!=null)
            {
                if(((BlockManufacturingStation.TileManufacturingStation) tile).getCenter().getUav()>=1 && ((BlockManufacturingStation.TileManufacturingStation) tile).getCountdown()>0)
                {
                    if(((BlockManufacturingStation.TileManufacturingStation) tile).getCountdown()<180)
                        ((BlockManufacturingStation.TileManufacturingStation) tile).setCountdown(0);
                    else
                        ((BlockManufacturingStation.TileManufacturingStation) tile).setCountdown(((BlockManufacturingStation.TileManufacturingStation) tile).getCountdown()-180);

                    ((BlockManufacturingStation.TileManufacturingStation) tile).getCenter().setUav(((BlockManufacturingStation.TileManufacturingStation) tile).getCenter().getUav()-1);
                }
            }
        }
    }
}
