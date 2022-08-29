package com.weedycow.arkworld.item.food;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityMana;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class HibiscusHealthyFood extends ItemFood
{
    public HibiscusHealthyFood()
    {
        super(2, 1, true);
        this.setAlwaysEdible();
        this.setCreativeTab(ArkCreateTab.FOOD);
        this.setUnlocalizedName(Arkworld.MODID + ".hibiscusHealthyFood");
        this.setRegistryName("hibiscus_healthy_food");
        this.setMaxStackSize(64);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.arkworld.info.food_addMana")+8);
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        entityLiving.addPotionEffect(new PotionEffect(MobEffects.NAUSEA,600,1));
        if(entityLiving.hasCapability(CapabilityRegistry.capMana,null) && entityLiving instanceof EntityPlayer)
        {
            CapabilityMana.Process process = new CapabilityMana.Process((EntityPlayer) entityLiving);process.addMana(8);
        }
        return super.onItemUseFinish(stack,worldIn,entityLiving);
    }
}
