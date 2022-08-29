package com.weedycow.arkworld.item.armor;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityAttribute;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ArkNormalArmor extends ItemArmor
{
    protected float defensivePower;
    protected float spellResistance;
    public static ArkConfig.Item.Weapon.Armor armor = ArkConfig.item.weapon.armor;

    public ArkNormalArmor(ItemArmor.ArmorMaterial materialIn, EntityEquipmentSlot slot, float defensivePower, float spellResistance)
    {
        super(materialIn, 0, slot);
        this.defensivePower = defensivePower;
        this.spellResistance=spellResistance;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return Arkworld.MODID + ":textures/items/" + getRegistryName().getResourcePath()+".png";
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("config.arkworld.spellResistance")+":+"+getSpellResistance());
        tooltip.add(I18n.format("config.arkworld.defensivePower")+":+"+getDefensivePower());
    }

    public static void onWear(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();

        if(EntityUtil.atSetIntervals(entity,20,0,0))
        {
            float defensivePower = 0;
            float spellResistance = 0;
            List<Item> equipmentList = new ArrayList<>();
            entity.getArmorInventoryList().forEach((x) -> equipmentList.add(x.getItem()));

            for (Item armor : equipmentList)
            {
                if (armor instanceof ArkNormalArmor)
                {
                    defensivePower += ((ArkNormalArmor) armor).getDefensivePower();
                    spellResistance += ((ArkNormalArmor) armor).getSpellResistance();
                }
            }

            if (entity.hasCapability(CapabilityRegistry.capAttribute, null))
            {
                CapabilityAttribute.Process attribute = new CapabilityAttribute.Process(entity);
                attribute.setDefencePower(defensivePower);
                attribute.setSpellResistance(spellResistance);
            }
        }
    }

    public float getSpellResistance()
    {
        return spellResistance;
    }

    public float getDefensivePower()
    {
        return defensivePower;
    }

    protected void setSpellResistance(float spellResistance)
    {
        this.spellResistance = spellResistance;
    }

    protected void setDefensivePower(float defensivePower)
    {
        this.defensivePower = defensivePower;
    }
}
