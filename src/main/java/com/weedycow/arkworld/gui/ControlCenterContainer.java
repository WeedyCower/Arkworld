package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.block.machine.infrastructure.BlockControlCenter;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.weedylib.util.ListUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ControlCenterContainer extends Container implements IButton
{
    TileEntity tile;
    EntityPlayer player;
    List<ItemStack> oriStacks;
    List<Integer> linkSer;
    List<ItemStack> adjStacks;
    ArrayList<ArrayList<ItemStack>> stacks = new ArrayList<ArrayList<ItemStack>>()
    {
        {
            this.add(0,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.KEEL,1),new ItemStack(ItemRegistry.LIGHT_BUILDING_MATERIAL,1), ItemStack.EMPTY, new ItemStack(ItemRegistry.LMB,2))));
            this.add(1,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.KEEL,1),new ItemStack(ItemRegistry.LIGHT_BUILDING_MATERIAL,3), ItemStack.EMPTY, new ItemStack(ItemRegistry.LMB,4))));
            this.add(2,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.KEEL,1),new ItemStack(ItemRegistry.CONCRETE_BUILDING_MATERIAL,4), ItemStack.EMPTY, new ItemStack(ItemRegistry.LMB,8))));
            this.add(3,new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.KEEL,1),new ItemStack(ItemRegistry.REINFORCED_BUILDING_MATERIAL,8), ItemStack.EMPTY, new ItemStack(ItemRegistry.LMB,12))));
        }
    };

    public ControlCenterContainer(TileEntity tile, EntityPlayer player)
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

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 0, 10, 95)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 1, 28, 95)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 2, 46, 95)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });
    }

    @Override
    public void onButtonPress(int buttonID)
    {
        IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);

        this.oriStacks = new ArrayList<>(Arrays.asList(handler.getStackInSlot(0), handler.getStackInSlot(1), handler.getStackInSlot(2)));

        this.adjStacks = new ArrayList<>(Arrays.asList(ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY));

        this.linkSer = new ArrayList<>(Arrays.asList(0,0,0));

        if(buttonID==0 && tile instanceof BlockControlCenter.TileControlCenter)
        {
            if(((BlockControlCenter.TileControlCenter) tile).getMaster()==null)
                ((BlockControlCenter.TileControlCenter) tile).setMaster(player);

            List<ItemStack> stacks = this.stacks.get(((BlockControlCenter.TileControlCenter) tile).getLevel()-1).subList(0,3);

            if(ListUtil.isListEqual(stacks.stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList()),oriStacks.stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList())))
            {
                for(int j=0; j<3; j++)
                {
                    for (int i = 0; i < 3; i++)
                    {
                        if(oriStacks.get(j).getUnlocalizedName().equals(stacks.get(i).getUnlocalizedName()))
                        {
                            this.adjStacks.set(i, oriStacks.get(j));
                            this.linkSer.set(j,i);
                        }
                    }
                }

                int n1 = stacks.get(0).getCount()>0 ? adjStacks.get(0).getCount()/stacks.get(0).getCount() : 0;

                int n2;

                if(stacks.get(1)==ItemStack.EMPTY && adjStacks.get(1)==ItemStack.EMPTY) n2=1;
                else n2 = stacks.get(1).getCount()>0 ? adjStacks.get(1).getCount()/stacks.get(1).getCount() : 0;

                int n3;

                if(stacks.get(2)==ItemStack.EMPTY && adjStacks.get(2)==ItemStack.EMPTY) n3=1;
                else n3 = stacks.get(2).getCount()>0 ? adjStacks.get(2).getCount()/stacks.get(2).getCount() : 0;

                List<Integer> ns = new ArrayList<>(Arrays.asList(n1,n2,n3));

                int min;

                if(!ns.contains(0))
                    min = ListUtil.getMin(ns.stream().mapToInt(Integer::intValue).toArray());
                else
                    min=0;

                if(min>0 && ((BlockControlCenter.TileControlCenter) tile).getUav()>=this.stacks.get(((BlockControlCenter.TileControlCenter) tile).getLevel()-1).get(3).getCount()*10 && ((BlockControlCenter.TileControlCenter) tile).getLevel()<5)
                {
                    ((BlockControlCenter.TileControlCenter) tile).setLevel(((BlockControlCenter.TileControlCenter) tile).getLevel()+1);

                    handler.getStackInSlot(0).shrink(min*stacks.get(linkSer.get(0)).getCount());

                    handler.getStackInSlot(1).shrink(min*stacks.get(linkSer.get(1)).getCount());

                    handler.getStackInSlot(2).shrink(min*stacks.get(linkSer.get(2)).getCount());
                }
            }
        }

        if(buttonID==1 && tile instanceof BlockControlCenter.TileControlCenter)
        {
            if(((BlockControlCenter.TileControlCenter) tile).getMaster()==null)
                ((BlockControlCenter.TileControlCenter) tile).setMaster(player);

            if(((BlockControlCenter.TileControlCenter) tile).getUav()<((BlockControlCenter.TileControlCenter) tile).getUavLimit()
            && player.getCapability(CapabilityRegistry.capSam,null).getSam()>0)
            {
                player.getCapability(CapabilityRegistry.capSam,null).setSam(player.getCapability(CapabilityRegistry.capSam,null).getSam()-1);

                if(((BlockControlCenter.TileControlCenter) tile).getUavLimit()-((BlockControlCenter.TileControlCenter) tile).getUav()<=1)
                    ((BlockControlCenter.TileControlCenter) tile).setUav(((BlockControlCenter.TileControlCenter) tile).getUav()+1);
                else
                    ((BlockControlCenter.TileControlCenter) tile).setUav(((BlockControlCenter.TileControlCenter) tile).getUav()+2);
            }
        }
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
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }
}
