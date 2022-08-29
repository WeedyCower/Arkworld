package com.weedycow.arkworld.item.tool.ammo;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.weapon.Arrow;
import com.weedycow.arkworld.item.tool.ItemArrow;
import com.weedycow.arkworld.item.tool.weapon.FaustCrossbow;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class FaustCommonArrow extends ItemArrow
{
    public FaustCommonArrow()
    {
        super(Arrow.Type.FAUST_COMMON_ARROW, ArkConfig.item.weapon.ammo.arrow.faustCommonArrow.attackDamage);
        this.setUnlocalizedName(Arkworld.MODID + ".faustCommonArrow");
        this.setRegistryName("faust_common_arrow");
        this.setMaxStackSize(64);
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("faust_arrow");
    }

    @Override
    public Arrow createArrow(World worldIn, EntityLivingBase shooter)
    {
        if(shooter.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof FaustCrossbow)
            return new Arrow(worldIn,shooter,damage,type);
        else
            return new Arrow(worldIn,shooter,damage/2,type);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag)
    {
        super.addInformation(itemstack, world, list, flag);

        String add = I18n.format("item.arkworld.info.onlyFaust");

        list.add(add);
    }
}
