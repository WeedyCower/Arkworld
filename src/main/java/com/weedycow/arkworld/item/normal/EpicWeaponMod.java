package com.weedycow.arkworld.item.normal;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.capability.CapabilityWeapon;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EpicWeaponMod extends NormalItem
{
    public EpicWeaponMod()
    {
        super("epic_weapon_mod", ArkCreateTab.ITEM,1);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);

        tooltip.add(I18n.format("item.arkworld.info.weaponMod")+(3-weapon.getRank()));

        tooltip.add(I18n.format("item.arkworld.info.mod")+weapon.getBuffs());
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn)
    {
        CapabilityWeapon.Process process = new CapabilityWeapon.Process(playerIn.getHeldItem(handIn));

        if(process.getRank()<3 && !worldIn.isRemote)
        {
            process.clear();

            int level;

            if(Math.random()<0.3)
                level=3;
            else if(Math.random()<0.7)
                level=2;
            else
                level=1;

            int value = ThreadLocalRandom.current().nextInt(0, CapabilityWeapon.Process.buffs.length);

            process.add(CapabilityWeapon.Process.Buffs.getBuff(value),level);

            process.setModBuff(value);

            process.setModLevel(level);

            process.setRank(process.getRank()+1);
        }

        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, NBTTagCompound nbt)
    {
        return new CapabilityWeapon.Provide();
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack)
    {
        if(stack.hasCapability(CapabilityRegistry.capWeapon,null))
            stack.setTagCompound(stack.getCapability(CapabilityRegistry.capWeapon,null).serializeNBT());
        return stack.getTagCompound();
    }

    @Override
    public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        if(stack.hasCapability(CapabilityRegistry.capWeapon,null) && nbt!=null)
            stack.getCapability(CapabilityRegistry.capWeapon,null).deserializeNBT(nbt);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(@Nonnull ItemStack stack)
    {
        return true;
    }
}
