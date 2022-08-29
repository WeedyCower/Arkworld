package com.weedycow.arkworld.item.armor;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.List;

public class PatriotArmor extends ArkArmor
{
    public static final ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("PATRIOT", Arkworld.MODID+":patriot",armor.patriotArmor.durability,new int[] {armor.patriotArmor.armorFeet,armor.patriotArmor.armorLegs,armor.patriotArmor.armorChest,armor.patriotArmor.armorHead}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0);

    AnimationController controllerIdle = new AnimationController(this, "idle", 1, this::PlayState);

    public PatriotArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(MATERIAL, equipmentSlotIn,armor.patriotArmor.defensivePowerHead,armor.patriotArmor.spellResistanceHead);
        this.setUnlocalizedName(Arkworld.MODID + ".patriotArmor" + equipmentSlotIn.getName());
        this.setRegistryName("patriot_armor_" + equipmentSlotIn.getName());
        this.setCreativeTab(ArkCreateTab.ARMOR);

        if(equipmentSlotIn==EntityEquipmentSlot.CHEST)
        {
            setSpellResistance(armor.patriotArmor.spellResistanceChest);
            setDefensivePower(armor.patriotArmor.defensivePowerChest);
        }
        if(equipmentSlotIn==EntityEquipmentSlot.LEGS)
        {
            setSpellResistance(armor.patriotArmor.spellResistanceLegs);
            setDefensivePower(armor.patriotArmor.defensivePowerLegs);
        }
        if(equipmentSlotIn==EntityEquipmentSlot.FEET)
        {
            setSpellResistance(armor.patriotArmor.spellResistanceFeet);
            setDefensivePower(armor.patriotArmor.defensivePowerFeet);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if(getEquipmentSlot()==EntityEquipmentSlot.HEAD)
            tooltip.add(I18n.format("item.arkworld.info.patriotArmorHead"));
    }

    private <P extends IAnimatable> PlayState PlayState(AnimationEvent<P> event)
    {
        controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.patriot.idle", true));
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
        return ArkResUtil.textureEntities("patriot");
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("patriot_armor");
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation("patriot_armor");
    }
}
