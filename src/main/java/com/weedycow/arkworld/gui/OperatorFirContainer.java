package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.machine.infrastructure.BlockTrainingRoom;
import com.weedycow.arkworld.capability.CapabilityValue;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.item.material.battle_record.DrillBattleRecord;
import com.weedycow.arkworld.item.material.battle_record.FrontlineBattleRecord;
import com.weedycow.arkworld.item.material.battle_record.StrategicBattleRecord;
import com.weedycow.arkworld.item.material.battle_record.TacticalBattleRecord;
import com.weedycow.arkworld.item.operator.OperatorPro;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.util.ArkEntityUtil;
import com.weedycow.weedylib.util.ListUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OperatorFirContainer extends Container implements IButton
{
    Operator operator;
    EntityPlayer player;
    public IItemHandlerModifiable handler;
    List<ItemStack> stacks;

    public OperatorFirContainer(InventoryPlayer inventoryPlayer, Operator operator, EntityPlayer player)
    {
        super();
        this.operator = operator;
        this.player = player;
        this.handler = player.getCapability(CapabilityRegistry.capSlotPlayer, null);

        for(int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 132 + i * 18, 176));
        }

        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, j1 + (l + 1) * 9, 132 + j1 * 18, 118 + l * 18));
            }
        }

        this.addSlotToContainer(new SlotItemHandler(handler, 3, 132, 95)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(handler, 4, 150, 95)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(handler, 5, 168, 95)
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
        if(buttonID==4)
            player.openGui(Arkworld.instance,2, player.world, operator.getEntityId(),0,0);

        IItemHandlerModifiable cp = player.getCapability(CapabilityRegistry.capSlotPlayer, null);
        CapabilityValue value = player.getCapability(CapabilityRegistry.capValue, null);

        if(player.hasCapability(CapabilityRegistry.capSlotPlayer,null))
            this.stacks = new ArrayList<>(Arrays.asList(player.getCapability(CapabilityRegistry.capSlotPlayer,
                            null).getStackInSlot(3), player.getCapability(CapabilityRegistry.capSlotPlayer, null).getStackInSlot(4),
                    player.getCapability(CapabilityRegistry.capSlotPlayer, null).getStackInSlot(5)));

        if(buttonID==2)
        {
            for(int i = 3; i<=5; i++)
            {
                if (cp.getStackInSlot(i).getItem() instanceof OperatorPro && ((OperatorPro)cp.getStackInSlot(i).getItem()).getOperatorId().equals(operator.id) && operator.getPotential()<6)
                {
                    if(cp.getStackInSlot(i).getCount()>6-operator.getPotential())
                    {
                        cp.getStackInSlot(i).shrink(6-operator.getPotential());
                        operator.setPotential(operator.getPotential() + 6-operator.getPotential());
                        operator.potentialUpFunction();
                    }
                    else
                    {
                        operator.setPotential(operator.getPotential() + cp.getStackInSlot(i).getCount());
                        cp.getStackInSlot(i).shrink(cp.getStackInSlot(i).getCount());
                        operator.potentialUpFunction();
                    }
                }
            }
        }

        if(buttonID==1)
        {
            List<ItemStack> oriStacks = new ArrayList<ItemStack>(Arrays.asList(cp.getStackInSlot(3),cp.getStackInSlot(4),cp.getStackInSlot(5)));

            List<ItemStack> adjStacks = new ArrayList<>(Arrays.asList(ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY));

            List<Integer> linkSer = new ArrayList<>(Arrays.asList(0,0,0));

            if(operator.getSkillLevel()<7)
            {
                if(ListUtil.isListEqual(operator.getSluMaterial().get(operator.getSkillLevel()-1).stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList()),oriStacks.stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList())))
                {
                    for(int j=0; j<3; j++)
                    {
                        for (int i = 0; i < 3; i++)
                        {
                            if(oriStacks.get(j).getUnlocalizedName().equals(operator.getSluMaterial().get(operator.getSkillLevel()-1).get(i).getUnlocalizedName()))
                            {
                                adjStacks.set(i, oriStacks.get(j));
                                linkSer.set(i,j+3);
                            }
                        }
                    }

                    int n1 = operator.getSluMaterial().get(operator.getSkillLevel()-1).get(0).getCount()>0 ? adjStacks.get(0).getCount()/operator.getSluMaterial().get(operator.getSkillLevel()-1).get(0).getCount() : 0;

                    int n2;

                    if(operator.getSluMaterial().get(operator.getSkillLevel()-1).get(1)==ItemStack.EMPTY && adjStacks.get(1)==ItemStack.EMPTY) n2=1;
                    else n2 = operator.getSluMaterial().get(operator.getSkillLevel()-1).get(1).getCount()>0 ? adjStacks.get(1).getCount()/operator.getSluMaterial().get(operator.getSkillLevel()-1).get(1).getCount() : 0;

                    int n3;

                    if(operator.getSluMaterial().get(operator.getSkillLevel()-1).get(2)==ItemStack.EMPTY && adjStacks.get(2)==ItemStack.EMPTY) n3=1;
                    else n3 = operator.getSluMaterial().get(operator.getSkillLevel()-1).get(2).getCount()>0 ? adjStacks.get(2).getCount()/operator.getSluMaterial().get(operator.getSkillLevel()-1).get(2).getCount() : 0;

                    List<Integer> ns = new ArrayList<>(Arrays.asList(n1,n2,n3));

                    int min;

                    if(!ns.contains(0))
                        min = ListUtil.getMin(ns.stream().mapToInt(Integer::intValue).toArray());
                    else
                        min=0;

                    if(min>0)
                    {
                        cp.getStackInSlot(linkSer.get(0)).shrink(operator.getSluMaterial().get(operator.getSkillLevel()-1).get(0).getCount());

                        cp.getStackInSlot(linkSer.get(1)).shrink(operator.getSluMaterial().get(operator.getSkillLevel()-1).get(1).getCount());

                        cp.getStackInSlot(linkSer.get(2)).shrink(operator.getSluMaterial().get(operator.getSkillLevel()-1).get(2).getCount());

                        operator.setSkillLevel(operator.getSkillLevel()+1);
                    }
                }
            }
            else if(operator.getSkillRank()<3)
            {
                if(ListUtil.isListEqual(operator.getSkills().get(operator.getSelectSkill()-1).getBreakMaterial().get(operator.getSkillRank()).stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList()),oriStacks.stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList())))
                {
                    List<ItemStack> stacks = operator.getSkills().get(operator.getSelectSkill()-1).getBreakMaterial().get(operator.getSkillRank());

                    for(int j=0; j<3; j++)
                    {
                        for (int i = 0; i < 3; i++)
                        {
                            if(oriStacks.get(j).getUnlocalizedName().equals(stacks.get(i).getUnlocalizedName()))
                            {
                                adjStacks.set(i, oriStacks.get(j));
                                linkSer.set(i,j+3);
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

                    if(min>0 && operator.getRoom() instanceof BlockTrainingRoom.TileTrainingRoom && operator.getTrainTime()<=0 && operator.getRoom().getLevel()>=operator.getSkillRank()+1)
                    {
                        if(ArkEntityUtil.getOperator(operator.world,((BlockTrainingRoom.TileTrainingRoom) operator.getRoom()).getCoach())!=null)
                            operator.setTrainTime((int) (28800*(operator.getSkillRank()+1) * (1-ArkEntityUtil.getOperator(operator.world,((BlockTrainingRoom.TileTrainingRoom) operator.getRoom()).getCoach()).getLogSkillRatio())));
                        else
                            operator.setTrainTime(28800*(operator.getSkillRank()+1));

                        cp.getStackInSlot(linkSer.get(0)).shrink(stacks.get(0).getCount());

                        cp.getStackInSlot(linkSer.get(1)).shrink(stacks.get(1).getCount());

                        cp.getStackInSlot(linkSer.get(2)).shrink(stacks.get(2).getCount());
                    }
                    
                }
            }
        }

        if(buttonID==0)
        {
            List<ItemStack> oriStacks = new ArrayList<ItemStack>(Arrays.asList(cp.getStackInSlot(3),cp.getStackInSlot(4),cp.getStackInSlot(5)));

            List<ItemStack> adjStacks = new ArrayList<>(Arrays.asList(ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY));

            List<Integer> linkSer = new ArrayList<>(Arrays.asList(0,0,0));

            if((operator.getElite()==0 && operator.getLevel()<50)
                    || (operator.getElite()==1 && operator.getLevel()<60 && operator.getRarity()== Operator.Rarity.IV)
                    || (operator.getElite()==1 && operator.getLevel()<70 && operator.getRarity()== Operator.Rarity.V)
                    || (operator.getElite()==1 && operator.getLevel()<80 && operator.getRarity()== Operator.Rarity.VI)
                    || (operator.getElite()==2 && operator.getLevel()<70 && operator.getRarity()== Operator.Rarity.IV)
                    || (operator.getElite()==2 && operator.getLevel()<80 && operator.getRarity()== Operator.Rarity.V)
                    || (operator.getElite()==2 && operator.getLevel()<90 && operator.getRarity()== Operator.Rarity.VI))
            {
                for(int i = 3; i<=5; i++)
                {
                    if(cp.getStackInSlot(i).getItem() instanceof DrillBattleRecord)
                    {
                        operator.setExp(operator.getExp()+200*cp.getStackInSlot(i).getCount());
                        cp.setStackInSlot(i,ItemStack.EMPTY);
                    }

                    if(cp.getStackInSlot(i).getItem() instanceof FrontlineBattleRecord)
                    {
                        operator.setExp(operator.getExp()+400*cp.getStackInSlot(i).getCount());
                        cp.setStackInSlot(i,ItemStack.EMPTY);
                    }

                    if(cp.getStackInSlot(i).getItem() instanceof TacticalBattleRecord)
                    {
                        operator.setExp(operator.getExp()+1000*cp.getStackInSlot(i).getCount());
                        cp.setStackInSlot(i,ItemStack.EMPTY);
                    }

                    if(cp.getStackInSlot(i).getItem() instanceof StrategicBattleRecord)
                    {
                        operator.setExp(operator.getExp()+2000*cp.getStackInSlot(i).getCount());
                        cp.setStackInSlot(i,ItemStack.EMPTY);
                    }
                }
            }

            if(operator.getElite()==0 && operator.getLevel()==50)
            {
                if(player.hasCapability(CapabilityRegistry.capSlotPlayer,null) && player.hasCapability(CapabilityRegistry.capValue,null))
                {
                    if (operator.getRarity() == Operator.Rarity.III && value.getLmb()>=500)
                    {
                        operator.setElite(1);
                        operator.setLevel(1);
                        value.setLmb(value.getLmb() - 500);
                    }

                    if(ListUtil.isListEqual(operator.getEliteI().stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList()),oriStacks.stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList())))
                    {
                        List<ItemStack> stacks = operator.getEliteI();

                        for(int j=0; j<3; j++)
                        {
                            for (int i = 0; i < 3; i++)
                            {
                                if(oriStacks.get(j).getUnlocalizedName().equals(stacks.get(i).getUnlocalizedName()))
                                {
                                    adjStacks.set(i, oriStacks.get(j));
                                    linkSer.set(i,j+3);
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

                        if(min>0)
                        {
                            if(operator.getRarity() == Operator.Rarity.IV && value.getLmb()>=750)
                            {
                                cp.getStackInSlot(linkSer.get(0)).shrink(stacks.get(0).getCount());

                                cp.getStackInSlot(linkSer.get(1)).shrink(stacks.get(1).getCount());

                                cp.getStackInSlot(linkSer.get(2)).shrink(stacks.get(2).getCount());

                                operator.setElite(1);

                                operator.setLevel(1);

                                value.setLmb(value.getLmb() - 750);
                            }

                            if(operator.getRarity() == Operator.Rarity.V && value.getLmb()>=1000)
                            {
                                cp.getStackInSlot(linkSer.get(0)).shrink(stacks.get(0).getCount());

                                cp.getStackInSlot(linkSer.get(1)).shrink(stacks.get(1).getCount());

                                cp.getStackInSlot(linkSer.get(2)).shrink(stacks.get(2).getCount());

                                operator.setElite(1);

                                operator.setLevel(1);

                                value.setLmb(value.getLmb() - 1000);
                            }

                            if(operator.getRarity() == Operator.Rarity.VI && value.getLmb()>=1500)
                            {
                                cp.getStackInSlot(linkSer.get(0)).shrink(stacks.get(0).getCount());

                                cp.getStackInSlot(linkSer.get(1)).shrink(stacks.get(1).getCount());

                                cp.getStackInSlot(linkSer.get(2)).shrink(stacks.get(2).getCount());

                                operator.setElite(1);

                                operator.setLevel(1);

                                value.setLmb(value.getLmb() - 1500);
                            }
                        }
                    }
                }
            }

            if(operator.getElite()==1)
            {
                if(ListUtil.isListEqual(operator.getEliteII().stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList()),oriStacks.stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList())))
                {
                    List<ItemStack> stacks = operator.getEliteII();

                    for(int j=0; j<3; j++)
                    {
                        for (int i = 0; i < 3; i++)
                        {
                            if(oriStacks.get(j).getUnlocalizedName().equals(stacks.get(i).getUnlocalizedName()))
                            {
                                adjStacks.set(i, oriStacks.get(j));
                                linkSer.set(i,j+3);
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

                    if(min>0)
                    {
                        if(operator.getLevel()==60 && operator.getRarity()== Operator.Rarity.IV)
                        {
                            if(value.getLmb()>=3000)
                            {
                                operator.setElite(2);
                                operator.setLevel(1);
                                value.setLmb(value.getLmb() - 3000);
                                cp.getStackInSlot(linkSer.get(0)).shrink(stacks.get(0).getCount());
                                cp.getStackInSlot(linkSer.get(1)).shrink(stacks.get(1).getCount());
                                cp.getStackInSlot(linkSer.get(2)).shrink(stacks.get(2).getCount());
                            }
                        }

                        if(operator.getLevel()==70 && operator.getRarity()== Operator.Rarity.V)
                        {
                            if(value.getLmb()>=6000)
                            {
                                operator.setElite(2);
                                operator.setLevel(1);
                                value.setLmb(value.getLmb() - 6000);
                                cp.getStackInSlot(linkSer.get(0)).shrink(stacks.get(0).getCount());
                                cp.getStackInSlot(linkSer.get(1)).shrink(stacks.get(1).getCount());
                                cp.getStackInSlot(linkSer.get(2)).shrink(stacks.get(2).getCount());
                            }
                        }

                        if(operator.getLevel()==80 && operator.getRarity()== Operator.Rarity.VI)
                        {
                            if(value.getLmb()>=9000)
                            {
                                operator.setElite(2);
                                operator.setLevel(1);
                                value.setLmb(value.getLmb() - 9000);
                                cp.getStackInSlot(linkSer.get(0)).shrink(stacks.get(0).getCount());
                                cp.getStackInSlot(linkSer.get(1)).shrink(stacks.get(1).getCount());
                                cp.getStackInSlot(linkSer.get(2)).shrink(stacks.get(2).getCount());
                            }
                        }
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
}
