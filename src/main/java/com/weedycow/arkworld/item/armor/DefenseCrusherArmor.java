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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DefenseCrusherArmor extends ArkArmor
{
    public static final ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("DEFENSE_CRUSHER", Arkworld.MODID+":defense_crusher",armor.defenseCrusherArmor.durability,new int[] {armor.defenseCrusherArmor.armorFeet,armor.defenseCrusherArmor.armorLegs,armor.defenseCrusherArmor.armorChest,0}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0);

    AnimationController controllerIdle = new AnimationController(this, "idle", 1, this::PlayState);

    public DefenseCrusherArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(MATERIAL, equipmentSlotIn,armor.defenseCrusherArmor.defensivePowerChest,armor.defenseCrusherArmor.spellResistanceChest);
        this.setUnlocalizedName(Arkworld.MODID + ".defenseCrusherArmor" + equipmentSlotIn.getName());
        this.setRegistryName("defense_crusher_armor_" + equipmentSlotIn.getName());
        this.setCreativeTab(ArkCreateTab.ARMOR);

        if(equipmentSlotIn==EntityEquipmentSlot.LEGS)
        {
            setSpellResistance(armor.defenseCrusherArmor.spellResistanceLegs);
            setDefensivePower(armor.defenseCrusherArmor.defensivePowerLegs);
        }
        if(equipmentSlotIn==EntityEquipmentSlot.FEET)
        {
            setSpellResistance(armor.defenseCrusherArmor.spellResistanceFeet);
            setDefensivePower(armor.defenseCrusherArmor.defensivePowerFeet);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if(getEquipmentSlot()==EntityEquipmentSlot.CHEST)
            tooltip.add(I18n.format("item.arkworld.info.defenseCrusherArmorChest"));
        if(getEquipmentSlot()==EntityEquipmentSlot.LEGS)
            tooltip.add(I18n.format("item.arkworld.info.defenseCrusherArmorLegs"));
        if(getEquipmentSlot()==EntityEquipmentSlot.FEET)
            tooltip.add(I18n.format("item.arkworld.info.defenseCrusherArmorFeet"));

        tooltip.add(I18n.format("item.arkworld.info.defenseCrusherArmor"));
    }

    private <P extends IAnimatable> PlayState PlayState(AnimationEvent<P> event)
    {
        controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.defense_crusher.idle", true));
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
        return ArkResUtil.textureEntities("defense_crusher");
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("defense_crusher_armor");
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation("defense_crusher_armor");
    }

    public static void onHurt(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        List<Item> equipmentList = new ArrayList<>();
        entity.getArmorInventoryList().forEach((x) -> equipmentList.add(x.getItem()));
        boolean hasChest = equipmentList.contains(ItemRegistry.DEFENSE_CRUSHER_CHEST);
        boolean hasLegs = equipmentList.contains(ItemRegistry.DEFENSE_CRUSHER_LEGS);
        boolean hasFeet = equipmentList.contains(ItemRegistry.DEFENSE_CRUSHER_FEET);
        boolean hasAll = equipmentList.containsAll(new ArrayList<>(Arrays.asList(ItemRegistry.DEFENSE_CRUSHER_FEET,ItemRegistry.DEFENSE_CRUSHER_LEGS,ItemRegistry.DEFENSE_CRUSHER_CHEST)));

        if (entity.hasCapability(CapabilityRegistry.capState, null))
        {
            CapabilityState.Process state = new CapabilityState.Process(entity);
            if (state.getStates().contains(EnumState.VERTIGO))
            {
                if(hasAll && new Random().nextInt(2) == 0)
                    state.addFunctionOnlyTick(EnumState.VERTIGO, state.getLevel( EnumState.VERTIGO)/3);
                else if ((hasChest && new Random().nextInt(3) == 0) || (hasLegs && new Random().nextInt(4) == 0)|| (hasFeet && new Random().nextInt(5) == 0))
                    state.addFunctionOnlyTick(EnumState.VERTIGO, state.getLevel( EnumState.VERTIGO)/2);
            }
        }
    }
}
