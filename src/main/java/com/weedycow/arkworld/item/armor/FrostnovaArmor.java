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
import java.util.Random;

public class FrostnovaArmor extends ArkArmor
{
    public static final ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("FROSTNOVA", Arkworld.MODID+":frostnova",armor.frostnovaArmor.durability,new int[] {0,0,armor.frostnovaArmor.armorChest,armor.frostnovaArmor.armorHead}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0);

    AnimationController controllerIdle = new AnimationController(this, "idle", 1, this::PlayState);

    public FrostnovaArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(MATERIAL, equipmentSlotIn,armor.frostnovaArmor.defensivePowerHead,armor.frostnovaArmor.spellResistanceHead);
        if(equipmentSlotIn==EntityEquipmentSlot.CHEST)
        {
            setSpellResistance(armor.frostnovaArmor.spellResistanceChest);
            setDefensivePower(armor.frostnovaArmor.defensivePowerChest);
        }
        this.setUnlocalizedName(Arkworld.MODID + ".frostnovaArmor" + equipmentSlotIn.getName());
        this.setRegistryName("frostnova_armor_" + equipmentSlotIn.getName());
        this.setCreativeTab(ArkCreateTab.ARMOR);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if(getEquipmentSlot()==EntityEquipmentSlot.HEAD)
            tooltip.add(I18n.format("item.arkworld.info.frostnovaArmorHead"));

        if(getEquipmentSlot()==EntityEquipmentSlot.CHEST)
        {
            tooltip.add(I18n.format("item.arkworld.info.frostnovaArmorChest1"));
            tooltip.add(I18n.format("item.arkworld.info.frostnovaArmorChest2"));
        }
    }

    private <P extends IAnimatable> PlayState PlayState(AnimationEvent<P> event)
    {
        controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.frostnova.idle", true));
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
        return ArkResUtil.textureEntities("frostnova");
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("frostnova_armor");
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation("frostnova_armor");
    }

    public static void onHurt(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        List<Item> equipmentList = new ArrayList<>();
        entity.getArmorInventoryList().forEach((x) -> equipmentList.add(x.getItem()));
        boolean hasHead = equipmentList.contains(ItemRegistry.FROSTNOVA_HEAD);
        boolean hasChest = equipmentList.contains(ItemRegistry.FROSTNOVA_CHEST);

        if (entity.hasCapability(CapabilityRegistry.capState, null))
        {
            CapabilityState.Process state = new CapabilityState.Process(entity);
            if (state.getStates().contains(EnumState.COLD))
            {
                if (hasHead && new Random().nextInt(4) == 0)
                    state.addFunctionOnlyTick(EnumState.COLD, (int) (CapabilityState.Process.getLevel(state.state, EnumState.COLD)*0.8));
                if(hasChest && new Random().nextInt(2) == 0)
                    state.addFunctionOnlyTick(EnumState.COLD, CapabilityState.Process.getLevel(state.state, EnumState.COLD)/2);
            }
            if(state.getStates().contains(EnumState.FREEZE))
            {
                if(hasChest && new Random().nextInt(4) == 0)
                    state.addFunctionOnlyTick(EnumState.FREEZE, CapabilityState.Process.getLevel(state.state, EnumState.FREEZE)/2);
            }
        }
    }
}