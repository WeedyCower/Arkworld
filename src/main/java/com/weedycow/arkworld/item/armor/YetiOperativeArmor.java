package com.weedycow.arkworld.item.armor;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class YetiOperativeArmor extends ArkArmor
{
    public static final ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("YETI_OPERATIVE", Arkworld.MODID+":yeti_operative",armor.yetiOperativeArmor.durability,new int[] {armor.yetiOperativeArmor.armorFeet,armor.yetiOperativeArmor.armorLegs,armor.yetiOperativeArmor.armorChest,armor.yetiOperativeArmor.armorHead}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0);

    AnimationController controllerIdle = new AnimationController(this, "idle", 1, this::PlayState);

    public YetiOperativeArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(MATERIAL, equipmentSlotIn,armor.yetiOperativeArmor.defensivePowerHead,armor.yetiOperativeArmor.spellResistanceHead);
        this.setUnlocalizedName(Arkworld.MODID + ".yetiOperativeArmor" + equipmentSlotIn.getName());
        this.setRegistryName("yeti_operative_armor_" + equipmentSlotIn.getName());
        this.setCreativeTab(ArkCreateTab.ARMOR);

        if(equipmentSlotIn==EntityEquipmentSlot.CHEST)
        {
            setDefensivePower(armor.yetiOperativeArmor.defensivePowerChest);
            setSpellResistance(armor.yetiOperativeArmor.spellResistanceChest);
        }
        if(equipmentSlotIn==EntityEquipmentSlot.LEGS)
        {
            setDefensivePower(armor.yetiOperativeArmor.defensivePowerLegs);
            setSpellResistance(armor.yetiOperativeArmor.spellResistanceLegs);
        }
        if(equipmentSlotIn==EntityEquipmentSlot.FEET)
        {
            setDefensivePower(armor.yetiOperativeArmor.defensivePowerFeet);
            setSpellResistance(armor.yetiOperativeArmor.spellResistanceFeet);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(I18n.format("item.arkworld.info.yetiOperativeArmor"));
    }

    private <P extends IAnimatable> PlayState PlayState(AnimationEvent<P> event)
    {
        controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.yeti_operative.idle", true));
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
        return ArkResUtil.textureEntities("yeti_operative");
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("yeti_operative_armor");
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation("yeti_operative_armor");
    }

    public static void onHurt(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        List<Item> equipmentList = new ArrayList<>();
        entity.getArmorInventoryList().forEach((x) -> equipmentList.add(x.getItem()));
        boolean hasHead = equipmentList.contains(ItemRegistry.YETI_OPERATIVE_HEAD);
        boolean hasChest = equipmentList.contains(ItemRegistry.YETI_OPERATIVE_CHEST);
        boolean hasLegs = equipmentList.contains(ItemRegistry.YETI_OPERATIVE_LEGS);
        boolean hasFeet = equipmentList.contains(ItemRegistry.YETI_OPERATIVE_FEET);
        boolean hasAll = hasChest&&hasFeet&&hasHead&&hasLegs;

        if (entity.hasCapability(CapabilityRegistry.capState, null))
        {
            CapabilityState.Process state = new CapabilityState.Process(entity);

            if (state.getStates().contains(EnumState.COLD))
            {
                if (hasAll)
                    state.addFunctionOnlyTick(EnumState.COLD, CapabilityState.Process.getLevel(state.state, EnumState.COLD)/2);
            }

            if (state.getStates().contains(EnumState.FREEZE))
            {
                if (hasAll)
                    state.addFunctionOnlyTick(EnumState.FREEZE, CapabilityState.Process.getLevel(state.state, EnumState.FREEZE)/2);
            }
        }
    }
}
