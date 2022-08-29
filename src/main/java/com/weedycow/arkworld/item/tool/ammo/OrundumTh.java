package com.weedycow.arkworld.item.tool.ammo;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.weapon.EntityOrundumTh;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class OrundumTh extends Item
{
    public OrundumTh()
    {
        this.setUnlocalizedName(Arkworld.MODID + ".orundumTh");
        this.setRegistryName("orundum_th");
        this.setMaxStackSize(64);
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if(playerIn.getHeldItem(handIn).getItem() instanceof OrundumTh && playerIn.getHeldItem(handIn).getCount()>=1)
        {
            EntityOrundumTh orundum200 = new EntityOrundumTh(worldIn,playerIn,88);
            orundum200.shoot(playerIn,playerIn.rotationPitch,playerIn.prevRotationYaw,0,4,1);
            worldIn.spawnEntity(orundum200);
            playerIn.getHeldItem(handIn).shrink(1);
            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        } else
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
