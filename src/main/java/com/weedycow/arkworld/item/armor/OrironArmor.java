package com.weedycow.arkworld.item.armor;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class OrironArmor extends ArkNormalArmor
{
    protected float defensivePower;
    protected float spellResistance;
    public static final ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("ORIRON", Arkworld.MODID+":oriron",armor.orironArmor.durability,new int[] {armor.orironArmor.armorFeet,armor.orironArmor.armorLegs,armor.orironArmor.armorChest,armor.orironArmor.armorHead}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0);

    public OrironArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(MATERIAL, equipmentSlotIn,armor.orironArmor.defensivePowerHead,armor.orironArmor.spellResistanceHead);

        this.setUnlocalizedName(Arkworld.MODID + ".orironArmor" + equipmentSlotIn.getName());

        this.setRegistryName("oriron_armor_" + equipmentSlotIn.getName());

        this.setCreativeTab(ArkCreateTab.ARMOR);

        if(equipmentSlotIn==EntityEquipmentSlot.CHEST)
        {
            setDefensivePower(armor.orironArmor.defensivePowerChest);
            setSpellResistance(armor.orironArmor.spellResistanceChest);
        }

        if(equipmentSlotIn==EntityEquipmentSlot.LEGS)
        {
            setDefensivePower(armor.orironArmor.defensivePowerLegs);
            setSpellResistance(armor.orironArmor.spellResistanceLegs);
        }

        if(equipmentSlotIn==EntityEquipmentSlot.FEET)
        {
            setDefensivePower(armor.orironArmor.defensivePowerFeet);
            setSpellResistance(armor.orironArmor.spellResistanceFeet);
        }
    }
}
