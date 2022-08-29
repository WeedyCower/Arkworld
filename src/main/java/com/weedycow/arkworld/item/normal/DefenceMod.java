package com.weedycow.arkworld.item.normal;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.capability.CapabilityItem;
import com.weedycow.arkworld.capability.EnumEntry;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.weedylib.util.ListUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefenceMod extends NormalItem
{
    public DefenceMod()
    {
        super("defence_mod", ArkCreateTab.ITEM,1);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        CapabilityItem.Process item = new CapabilityItem.Process(stack);
        if(item.getType()!=1)item.setType(1);
        if(ListUtil.getDuplicate(item.getEntries()).size()>0)
        {
            List<EnumEntry> entries = item.getEntries();
            List<EnumEntry> entriesDuplicate = ListUtil.getDuplicate(entries);
            for(EnumEntry entry : entries)
            {
                if(entriesDuplicate.contains(entry) || !Arrays.stream(CapabilityItem.defence).collect(Collectors.toList()).contains(entry))
                {
                    entries.remove(entry);
                    break;
                }
            }
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag)
    {
        super.addInformation(itemstack, world, list, flag);

        CapabilityItem.Process item = new CapabilityItem.Process(itemstack);

        list.add(item.getEntriesString());
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, NBTTagCompound nbt)
    {
        return new CapabilityItem.Provide();
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack)
    {
        if(stack.hasCapability(CapabilityRegistry.capItem,null))
            stack.setTagCompound(stack.getCapability(CapabilityRegistry.capItem,null).serializeNBT());
        return stack.getTagCompound();
    }

    @Override
    public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        if(stack.hasCapability(CapabilityRegistry.capItem,null) && nbt!=null)
            stack.getCapability(CapabilityRegistry.capItem,null).deserializeNBT(nbt);
    }
}
