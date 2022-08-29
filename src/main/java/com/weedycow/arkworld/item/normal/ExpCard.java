package com.weedycow.arkworld.item.normal;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.capability.CapabilityItem;
import com.weedycow.arkworld.capability.CapabilityLevel;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class ExpCard extends NormalItem
{
    public ExpCard()
    {
        super("exp_card", ArkCreateTab.ITEM, 64);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag)
    {
        super.addInformation(itemstack, world, list, flag);

        CapabilityItem.Process item = new CapabilityItem.Process(itemstack);

        String exp = I18n.format("item.arkworld.info.expCardValue") + ":" + item.getExperience();

        list.add(exp);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if(!worldIn.isRemote && playerIn.hasCapability(CapabilityRegistry.capLevel,null) && stack.getItem() == this
                && stack.hasCapability(CapabilityRegistry.capItem,null) && stack.getCapability(CapabilityRegistry.capItem,null).getExperience()>0)
        {
            new CapabilityLevel.Process(playerIn).addExperience(new CapabilityItem.Process(stack).getExperience());
            stack.shrink(1);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
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
