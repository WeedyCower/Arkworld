package com.weedycow.arkworld.item.food;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilitySam;
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

public class TopEmergencySanityPotion extends ItemFood
{
	public TopEmergencySanityPotion()
    {
        super(3, 0, false);
        this.setAlwaysEdible();
        this.setCreativeTab(ArkCreateTab.FOOD);
        this.setUnlocalizedName(Arkworld.MODID + ".topEmergencySanityPotion");
        this.setRegistryName("top_emergency_sanity_potion");
        this.setMaxStackSize(64);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.arkworld.info.topEmergencySanityPotion"));
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        if(entityLiving.hasCapability(CapabilityRegistry.capSam,null) && entityLiving instanceof EntityPlayer)
        {
            CapabilitySam.Process process = new CapabilitySam.Process((EntityPlayer) entityLiving);
            process.addSam(100);
        }
        return super.onItemUseFinish(stack,worldIn,entityLiving);
    }
}
