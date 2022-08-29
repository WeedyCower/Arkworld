package com.weedycow.arkworld.item.tool.weapon;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityWeapon;
import com.weedycow.arkworld.entity.enemy.union.normal.ShieldSoldier;
import com.weedycow.arkworld.item.tool.EnumWeaponRank;
import com.weedycow.arkworld.item.tool.ShieldWeapon;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class NormalShield extends ShieldWeapon
{
    public NormalShield()
    {
        super(ShieldSoldier.ID, EnumWeaponRank.NORMAL);
        this.setUnlocalizedName(Arkworld.MODID + ".normalShield");
        this.setRegistryName("normal_shield");
    }

    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag)
    {
        super.addInformation(itemstack, world, list, flag);

        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(itemstack);

        String stamina = I18n.format("item.arkworld.info.stamina") + " : " + weapon.getStamina();

        String durability = I18n.format("item.arkworld.info.durability") + " : " + weapon.getLoss() + "/" + weapon.getDurability();

        list.add(stamina);

        list.add(durability);

        list.add(I18n.format("item.arkworld.info.mod")+weapon.getBuffs());
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack,worldIn,entityIn,itemSlot,isSelected);

        if(entityIn instanceof EntityLivingBase && !entityIn.world.isRemote)
        {
            CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);

            onStamina(weapon,shield.normalShield.stamina);

            onDurability(weapon,shield.normalShield.durability);
        }
    }
}
