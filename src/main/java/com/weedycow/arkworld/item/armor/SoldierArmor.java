package com.weedycow.arkworld.item.armor;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class SoldierArmor extends ArkArmor
{
    public static final ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("SOLDIER", Arkworld.MODID+":soldier",armor.soldierArmor.durability,new int[] {armor.soldierArmor.armorFeet,armor.soldierArmor.armorLegs,armor.soldierArmor.armorChest,armor.soldierArmor.armorHead}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0);

    AnimationController controllerIdle = new AnimationController(this, "idle", 1, this::PlayState);

    public SoldierArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(MATERIAL, equipmentSlotIn,armor.soldierArmor.defensivePowerHead,armor.soldierArmor.spellResistanceHead);
        this.setUnlocalizedName(Arkworld.MODID + ".soldierArmor" + equipmentSlotIn.getName());
        this.setRegistryName("soldier_armor_" + equipmentSlotIn.getName());
        this.setCreativeTab(ArkCreateTab.ARMOR);

        if(equipmentSlotIn==EntityEquipmentSlot.CHEST)
        {
            setDefensivePower(armor.soldierArmor.defensivePowerChest);
            setSpellResistance(armor.soldierArmor.spellResistanceChest);
        }
        if(equipmentSlotIn==EntityEquipmentSlot.LEGS)
        {
            setDefensivePower(armor.soldierArmor.defensivePowerLegs);
            setSpellResistance(armor.soldierArmor.spellResistanceLegs);
        }
        if(equipmentSlotIn==EntityEquipmentSlot.FEET)
        {
            setDefensivePower(armor.soldierArmor.defensivePowerFeet);
            setSpellResistance(armor.soldierArmor.spellResistanceFeet);
        }
    }

    private <P extends IAnimatable> PlayState PlayState(AnimationEvent<P> event)
    {
        controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.soldier.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerIdle);
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureEntities("soldier");
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("soldier_armor");
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation("soldier_armor");
    }
}
