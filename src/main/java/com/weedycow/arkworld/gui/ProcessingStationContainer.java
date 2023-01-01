package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.RecipeTable;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.util.ArkItemUtil;
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

public class ProcessingStationContainer extends Container implements IButton
{
    TileEntity tile;
    EntityPlayer player;
    List<ItemStack> oriStacks;
    List<Integer> linkSer;
    List<ItemStack> adjStacks;

    public ProcessingStationContainer(TileEntity tile, EntityPlayer player)
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

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 0, 82, 59)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 1, 82, 20)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 2, 47, 83)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 3, 117, 83)
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

        this.oriStacks = new ArrayList<>(Arrays.asList(handler.getStackInSlot(1), handler.getStackInSlot(2), handler.getStackInSlot(3)));

        this.adjStacks = new ArrayList<>(Arrays.asList(ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY));

        this.linkSer = new ArrayList<>(Arrays.asList(0,0,0));

        if(buttonID==0)
        {
            for(List<ItemStack> s : RecipeTable.PROCESSING_STATION_RECIPE)
            {
                List<ItemStack> table = s.subList(0,s.size()-3);

                if(ArkItemUtil.isEqualSize(oriStacks,table) && ListUtil.isListEqual(oriStacks.stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList()), table.stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList())))
                {
                    for(int j=0; j<3; j++)
                    {
                        for (int i = 0; i < 3; i++)
                        {
                            if(oriStacks.get(j).getUnlocalizedName().equals(table.get(i).getUnlocalizedName()))
                            {
                                this.adjStacks.set(i, oriStacks.get(j));//合成表序号，放入物品
                                this.linkSer.set(j,i);//放入物序号，合成表序号
                            }
                        }
                    }

                    int n1 = table.get(0).getCount()>0 ? adjStacks.get(0).getCount()/table.get(0).getCount() : 0;

                    int n2 = table.get(1).getCount()>0 ? adjStacks.get(1).getCount()/table.get(1).getCount() : 0;

                    int n3 = table.get(2).getCount()>0 ? adjStacks.get(2).getCount()/table.get(2).getCount() : 0;

                    List<Integer> ns = new ArrayList<>(Arrays.asList(n1,n2,n3));

                    List<Integer> mins = new ArrayList<>();

                    for(int i : ns)
                    {
                        if(i>0)
                            mins.add(i);
                    }

                    int min;

                    if(mins.size()>0)
                        min = ListUtil.getMin(mins.stream().mapToInt(Integer::intValue).toArray());
                    else
                        min=0;

                    if(min>0 && (handler.getStackInSlot(0).isEmpty() || (handler.getStackInSlot(0).getItem()==s.get(s.size()-3).getItem() && handler.getStackInSlot(0).getCount()<64)) && player.getCapability(CapabilityRegistry.capValue,null).getLmb()>=s.get(s.size()-2).getCount()*5*min)
                    {
                        ItemStack stack = new ItemStack(s.get(s.size()-3).getItem(),min*s.get(s.size()-3).getCount()).copy();

                        handler.insertItem(0,stack,false);

                        handler.getStackInSlot(1).shrink(min*table.get(linkSer.get(0)).getCount());

                        handler.getStackInSlot(2).shrink(min*table.get(linkSer.get(1)).getCount());

                        handler.getStackInSlot(3).shrink(min*table.get(linkSer.get(2)).getCount());

                        player.getCapability(CapabilityRegistry.capValue,null).setLmb(player.getCapability(CapabilityRegistry.capValue,null).getLmb()-s.get(s.size()-2).getCount()*5*min);

                        mins.clear();
                    }
                }
            }
        }
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
                if (!this.mergeItemStack(itemstack1, 36, 39, true))
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
}
