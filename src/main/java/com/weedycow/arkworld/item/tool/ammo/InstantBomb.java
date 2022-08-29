package com.weedycow.arkworld.item.tool.ammo;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityPlayer;
import com.weedycow.arkworld.entity.weapon.Bomb;
import com.weedycow.arkworld.item.tool.ItemBomb;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

public class InstantBomb extends ItemBomb
{
    public InstantBomb()
    {
        super(Bomb.Type.INSTANT_BOMB, ArkConfig.item.weapon.ammo.bomb.instantBomb.damage, ArkConfig.item.weapon.ammo.bomb.instantBomb.destroyTerrain);
        this.setUnlocalizedName(Arkworld.MODID + ".instantBomb");
        this.setRegistryName("instant_bomb");
        this.setMaxStackSize(64);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(entityIn instanceof EntityPlayer && new CapabilityPlayer.Process((EntityPlayer) entityIn).isSwing() && ((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND)==stack && !worldIn.isRemote)
        {
            Bomb bomb = createArrow(worldIn,((EntityPlayer)entityIn));
            bomb.shoot(entityIn,entityIn.rotationPitch,entityIn.rotationYaw,ArkConfig.item.weapon.ammo.bomb.instantBomb.thrownVelocity,1);
            worldIn.spawnEntity(bomb);
            if(!((EntityPlayer) entityIn).isCreative()) stack.shrink(1);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        if(entityLiving instanceof EntityPlayer)
        {
            if (entityLiving.hasCapability(CapabilityRegistry.capPlayer,null) && entityLiving.world.isRemote && !entityLiving.getCapability(CapabilityRegistry.capPlayer,null).isSwing())
            {
                CapabilityPlayer.Process player = new CapabilityPlayer.Process((EntityPlayer) entityLiving);
                player.setTimes(player.getTimes()+2);
                return true;
            }
        }
        return false;
    }
}
