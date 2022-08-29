package com.weedycow.arkworld.item.armor;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.registry.ItemRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GrindstoneArmor extends ArkNormalArmor
{
    protected float defensivePower;
    protected float spellResistance;
    public static final ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("GRINDSTONE", Arkworld.MODID+":grindstone",armor.grindstoneArmor.durability,new int[] {armor.grindstoneArmor.armorFeet,armor.grindstoneArmor.armorLegs,armor.grindstoneArmor.armorChest,armor.grindstoneArmor.armorHead}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0);

    public GrindstoneArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(MATERIAL, equipmentSlotIn,armor.grindstoneArmor.defensivePowerHead,armor.grindstoneArmor.spellResistanceHead);

        this.setUnlocalizedName(Arkworld.MODID + ".grindstoneArmor" + equipmentSlotIn.getName());

        this.setRegistryName("grindstone_armor_" + equipmentSlotIn.getName());

        this.setCreativeTab(ArkCreateTab.ARMOR);

        if(equipmentSlotIn==EntityEquipmentSlot.CHEST)
        {
            setDefensivePower(armor.grindstoneArmor.defensivePowerChest);
            setSpellResistance(armor.grindstoneArmor.spellResistanceChest);
        }

        if(equipmentSlotIn==EntityEquipmentSlot.LEGS)
        {
            setDefensivePower(armor.grindstoneArmor.defensivePowerLegs);
            setSpellResistance(armor.grindstoneArmor.spellResistanceLegs);
        }

        if(equipmentSlotIn==EntityEquipmentSlot.FEET)
        {
            setDefensivePower(armor.grindstoneArmor.defensivePowerFeet);
            setSpellResistance(armor.grindstoneArmor.spellResistanceFeet);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.arkworld.info.grindstoneArmor"));
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack item)
    {
        List<Item> equipmentList = new ArrayList<>();
        player.getArmorInventoryList().forEach((x) -> equipmentList.add(x.getItem()));
        boolean f = equipmentList.containsAll(new ArrayList<>(Arrays.asList(ItemRegistry.GRINDSTONE_HEAD,ItemRegistry.GRINDSTONE_CHEST,ItemRegistry.GRINDSTONE_LEGS,ItemRegistry.GRINDSTONE_FEET)));
        if(f) player.addPotionEffect(new PotionEffect(MobEffects.SPEED,220,0));
    }
}
