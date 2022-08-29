package com.weedycow.arkworld.item.armor;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class OrirockArmor extends ArkNormalArmor
{
    protected float defensivePower;
    protected float spellResistance;
    public static final ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("ORIROCK", Arkworld.MODID+":orirock",armor.orirockArmor.durability,new int[] {armor.orirockArmor.armorFeet,armor.orirockArmor.armorLegs,armor.orirockArmor.armorChest,armor.orirockArmor.armorHead}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0);

    public OrirockArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(MATERIAL, equipmentSlotIn,armor.orirockArmor.defensivePowerHead,armor.orirockArmor.spellResistanceHead);

        this.setUnlocalizedName(Arkworld.MODID + ".orirockArmor" + equipmentSlotIn.getName());

        this.setRegistryName("orirock_armor_" + equipmentSlotIn.getName());

        this.setCreativeTab(ArkCreateTab.ARMOR);

        if(equipmentSlotIn==EntityEquipmentSlot.CHEST)
        {
            setDefensivePower(armor.orirockArmor.defensivePowerChest);
            setSpellResistance(armor.orirockArmor.spellResistanceChest);
        }

        if(equipmentSlotIn==EntityEquipmentSlot.LEGS)
        {
            setDefensivePower(armor.orirockArmor.defensivePowerLegs);
            setSpellResistance(armor.orirockArmor.spellResistanceLegs);
        }
        if(equipmentSlotIn==EntityEquipmentSlot.FEET)
        {
            setDefensivePower(armor.orirockArmor.defensivePowerFeet);
            setSpellResistance(armor.orirockArmor.spellResistanceFeet);
        }
    }
}
