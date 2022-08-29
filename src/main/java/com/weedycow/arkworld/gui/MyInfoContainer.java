package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.capability.CapabilityValue;
import com.weedycow.arkworld.item.normal.ArmsMod;
import com.weedycow.arkworld.item.normal.DefenceMod;
import com.weedycow.arkworld.item.normal.PlayerMod;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class MyInfoContainer extends Container implements IButton
{
    EntityPlayer player;
    public IItemHandlerModifiable handler;
    private static final EntityEquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};

    public MyInfoContainer(InventoryPlayer inventoryPlayer, EntityPlayer player)
    {
        super();
        this.player = player;
        handler = player.getCapability(CapabilityRegistry.capSlotPlayer, null);

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

        for (int k = 0; k < 4; ++k)
        {
            final EntityEquipmentSlot entityequipmentslot = VALID_EQUIPMENT_SLOTS[k];
            this.addSlotToContainer(new Slot(inventoryPlayer, 36 + (3 - k), 9, 16 + k * 18)
            {
                public int getSlotStackLimit()
                {
                    return 1;
                }
                public boolean isItemValid(@Nonnull ItemStack stack)
                {
                    return stack.getItem().isValidArmor(stack, entityequipmentslot, player);
                }
                public boolean canTakeStack(@Nonnull EntityPlayer playerIn)
                {
                    ItemStack itemstack = this.getStack();
                    return (itemstack.isEmpty() || playerIn.isCreative() || !EnchantmentHelper.hasBindingCurse(itemstack)) && super.canTakeStack(playerIn);
                }
                @Nullable
                @SideOnly(Side.CLIENT)
                public String getSlotTexture()
                {
                    return ItemArmor.EMPTY_SLOT_NAMES[entityequipmentslot.getIndex()];
                }
            });
        }

        this.addSlotToContainer(new Slot(inventoryPlayer, 40, 9, 88)
        {
            @Nonnull
            @SideOnly(Side.CLIENT)
            public String getSlotTexture()
            {
                return "minecraft:items/empty_armor_slot_shield";
            }
        });

        this.addSlotToContainer(new SlotItemHandler(handler, 0, 108, 16)
        {
            @Override
            @ParametersAreNonnullByDefault
            public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() instanceof ArmsMod;
            }

            @Override
            public int getSlotStackLimit()
            {
                return 1;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(handler, 1, 108, 34)
        {
            @Override
            @ParametersAreNonnullByDefault
            public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() instanceof DefenceMod;
            }

            @Override
            public int getSlotStackLimit()
            {
                return 1;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(handler, 2, 108, 52)
        {
            @Override
            @ParametersAreNonnullByDefault
            public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() instanceof PlayerMod;
            }

            @Override
            public int getSlotStackLimit()
            {
                return 1;
            }
        });
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn)
    {
        return true;
    }

    @Override
    public void onButtonPress(int buttonID)
    {
        CapabilityValue.Process value = new CapabilityValue.Process(player);

        if(buttonID==3 && value.getLmb()>0)
        {
            if(player.inventory.add(-1,new ItemStack(ItemRegistry.LMB))) value.reduceLmb(1);
        }

        if(buttonID==4 && value.getOriginium()>0)
        {
            if(player.inventory.add(-1,new ItemStack(ItemRegistry.ORIGINITE_PRIME))) value.reduceOriginium(1);
        }

        if(buttonID==5 && value.getOrundum()>0)
        {
            if(player.inventory.add(-1,new ItemStack(ItemRegistry.ORUNDUM))) value.reduceOrundum(1);
        }
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index)
    {
        return ItemStack.EMPTY;
    }
}
