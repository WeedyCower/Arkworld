package com.weedycow.arkworld.item.food;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class Kettle extends ItemFood
{
    public Kettle()
    {
        super(2, 1, true);
        this.setAlwaysEdible();
        this.setCreativeTab(ArkCreateTab.FOOD);
        this.setUnlocalizedName(Arkworld.MODID + ".kettle");
        this.setRegistryName("kettle");
        this.setMaxStackSize(64);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.arkworld.info.kettle"));
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        if(entityLiving.hasCapability(CapabilityRegistry.capState,null))
        {
            CapabilityState.Process process = new CapabilityState.Process(entityLiving);
            process.addFunctionOnlyTick(EnumState.INSPIRE,1200);
        }
        return super.onItemUseFinish(stack,worldIn,entityLiving);
    }
}
