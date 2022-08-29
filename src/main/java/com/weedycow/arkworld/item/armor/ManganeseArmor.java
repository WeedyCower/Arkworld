package com.weedycow.arkworld.item.armor;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class ManganeseArmor extends ArkNormalArmor
{
    protected float defensivePower;
    protected float spellResistance;
    public static final ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("MANGANESE", Arkworld.MODID+":manganese",armor.manganeseArmor.durability,new int[] {armor.manganeseArmor.armorFeet,armor.manganeseArmor.armorLegs,armor.manganeseArmor.armorChest,armor.manganeseArmor.armorHead}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0);

    public ManganeseArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(MATERIAL, equipmentSlotIn,armor.manganeseArmor.defensivePowerHead,armor.manganeseArmor.spellResistanceHead);

        this.setUnlocalizedName(Arkworld.MODID + ".manganeseArmor" + equipmentSlotIn.getName());

        this.setRegistryName("manganese_armor_" + equipmentSlotIn.getName());

        this.setCreativeTab(ArkCreateTab.ARMOR);

        if(equipmentSlotIn==EntityEquipmentSlot.CHEST)
        {
            setDefensivePower(armor.manganeseArmor.defensivePowerChest);
            setSpellResistance(armor.manganeseArmor.spellResistanceChest);
        }

        if(equipmentSlotIn==EntityEquipmentSlot.LEGS)
        {
            setDefensivePower(armor.manganeseArmor.defensivePowerLegs);
            setSpellResistance(armor.manganeseArmor.spellResistanceLegs);
        }
        if(equipmentSlotIn==EntityEquipmentSlot.FEET)
        {
            setDefensivePower(armor.manganeseArmor.defensivePowerFeet);
            setSpellResistance(armor.manganeseArmor.spellResistanceFeet);
        }
    }
}
