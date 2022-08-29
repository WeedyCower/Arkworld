package com.weedycow.arkworld.item.tool.weapon;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityWeapon;
import com.weedycow.arkworld.entity.enemy.union.boss.Patriot;
import com.weedycow.arkworld.item.tool.EnumWeaponRank;
import com.weedycow.arkworld.item.tool.ShieldWeapon;
import com.weedycow.arkworld.util.ArkEntityUtil;
import com.weedycow.weedylib.util.AoeRangeUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class PatriotShield extends ShieldWeapon
{
    float tantRange;

    public PatriotShield()
    {
        super(Patriot.ID, EnumWeaponRank.SUPER);
        this.setUnlocalizedName(Arkworld.MODID + ".patriotShield");
        this.setRegistryName("patriot_shield");
    }

    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag)
    {
        super.addInformation(itemstack, world, list, flag);

        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(itemstack);

        String stamina = I18n.format("item.arkworld.info.stamina") + " : " + weapon.getStamina();

        String tant = I18n.format("item.arkworld.info.tantRange") + " : " + tantRange;

        String durability = I18n.format("item.arkworld.info.durability") + " : " + weapon.getLoss() + "/" + weapon.getDurability();

        String add = I18n.format("item.arkworld.info.tantInfo");

        list.add(tant);

        list.add(stamina);

        list.add(durability);

        list.add(add);

        list.add(I18n.format("item.arkworld.info.mod")+weapon.getBuffs());
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack,worldIn,entityIn,itemSlot,isSelected);

        if(entityIn instanceof EntityPlayer && ItemStack.areItemStacksEqual(((EntityPlayer) entityIn).getActiveItemStack(),stack))
        {
            AoeRangeUtil aabb = new AoeRangeUtil(entityIn,tantRange);
            for(EntityLivingBase entityLivingBase : worldIn.getEntitiesWithinAABB(EntityLivingBase.class,aabb))
            {
                if(entityLivingBase instanceof EntityPlayer && entityLivingBase != entityIn && shield.patriotShield.canTauntPlayer)
                {
                    double d0 = entityIn.posX - entityLivingBase.posX;
                    double d2 = entityIn.posZ - entityLivingBase.posZ;
                    double d1 = entityIn.posY - 1 - entityLivingBase.posY;
                    double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
                    float f = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
                    float f1 = (float) (-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));
                    entityLivingBase.rotationPitch = ArkEntityUtil.updateRotation(entityLivingBase.rotationPitch, f1, 30F);
                    entityLivingBase.rotationYaw = ArkEntityUtil.updateRotation(entityLivingBase.rotationYaw, f, 30F);
                }
                else if (entityLivingBase instanceof EntityLiving && shield.patriotShield.canTauntEntityLiving)
                {
                    ((EntityLiving)entityLivingBase).setAttackTarget((EntityLivingBase) entityIn);
                }
            }
        }

        if(entityIn instanceof EntityPlayer && !entityIn.world.isRemote)
        {
            CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);

            tantRange = weapon.getLevel() / 2f;

            onStamina(weapon, shield.patriotShield.stamina);

            onDurability(weapon, shield.patriotShield.durability);
        }
    }
}
