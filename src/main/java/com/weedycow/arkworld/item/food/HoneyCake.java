package com.weedycow.arkworld.item.food;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityMana;
import com.weedycow.arkworld.capability.CapabilityStamina;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class HoneyCake extends ItemFood
{
    public HoneyCake()
    {
        super(4, 1, true);
        this.setAlwaysEdible();
        this.setCreativeTab(ArkCreateTab.FOOD);
        this.setUnlocalizedName(Arkworld.MODID + ".honeyCake");
        this.setRegistryName("honey_cake");
        this.setMaxStackSize(64);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.arkworld.info.food_addStamina")+6);
        tooltip.add(I18n.format("item.arkworld.info.food_addMana")+4);
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        if(entityLiving.hasCapability(CapabilityRegistry.capStamina,null) && entityLiving instanceof EntityPlayer)
        {
            CapabilityStamina.Process process = new CapabilityStamina.Process((EntityPlayer) entityLiving);process.addStamina(6);
        }
        if(entityLiving.hasCapability(CapabilityRegistry.capMana,null) && entityLiving instanceof EntityPlayer)
        {
            CapabilityMana.Process process = new CapabilityMana.Process((EntityPlayer) entityLiving);process.addMana(4);
        }
        return super.onItemUseFinish(stack,worldIn,entityLiving);
    }
}
