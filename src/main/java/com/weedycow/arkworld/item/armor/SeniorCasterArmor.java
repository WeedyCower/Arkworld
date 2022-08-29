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

public class SeniorCasterArmor extends ArkArmor
{
    public static final ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("SENIOR_CASTER", Arkworld.MODID+":senior_caster",armor.seniorCasterArmor.durability,new int[] {0,0,armor.seniorCasterArmor.armorChest,armor.seniorCasterArmor.armorHead}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0);

    AnimationController controllerIdle = new AnimationController(this, "idle", 1, this::PlayState);

    public SeniorCasterArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(MATERIAL, equipmentSlotIn,armor.seniorCasterArmor.defensivePowerHead,armor.seniorCasterArmor.spellResistanceHead);

        if(equipmentSlotIn==EntityEquipmentSlot.CHEST)
        {
            setSpellResistance(armor.seniorCasterArmor.spellResistanceChest);
            setDefensivePower(armor.seniorCasterArmor.defensivePowerChest);
        }

        this.setUnlocalizedName(Arkworld.MODID + ".seniorCasterArmor" + equipmentSlotIn.getName());
        this.setRegistryName("senior_caster_armor_" + equipmentSlotIn.getName());
        this.setCreativeTab(ArkCreateTab.ARMOR);
    }

    private <P extends IAnimatable> PlayState PlayState(AnimationEvent<P> event)
    {
        controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.senior_caster.idle", true));
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
        return ArkResUtil.textureEntities("senior_caster");
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("senior_caster_armor");
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation("senior_caster_armor");
    }
}
