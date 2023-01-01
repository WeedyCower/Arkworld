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
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
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

public class PocketSeaCrawlerArmor extends ArkArmor
{
    public static final ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("POCKET_SEA_CRAWLER", Arkworld.MODID+":pocket_sea_crawler",armor.pocketSeaCrawlerArmor.durability,new int[] {0,0,0,armor.pocketSeaCrawlerArmor.armorHead}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0);

    AnimationController controllerIdle = new AnimationController(this, "idle", 1, this::PlayState);

    public PocketSeaCrawlerArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(MATERIAL, equipmentSlotIn,armor.pocketSeaCrawlerArmor.defensivePowerHead,armor.pocketSeaCrawlerArmor.spellResistanceHead);
        this.setUnlocalizedName(Arkworld.MODID + ".pocketSeaCrawlerArmor" + equipmentSlotIn.getName());
        this.setRegistryName("pocket_sea_crawler_armor_" + equipmentSlotIn.getName());
        this.setCreativeTab(ArkCreateTab.ARMOR);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(I18n.format("item.arkworld.info.pocketSeaCrawlerArmor"));
    }

    private <P extends IAnimatable> PlayState PlayState(AnimationEvent<P> event)
    {
        controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.pocket_sea_crawler.idle", true));
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
        return ArkResUtil.textureEntities("pocket_sea_crawler");
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("pocket_sea_crawler_armor");
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation("pocket_sea_crawler_armor");
    }

    public static void onUpdating(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        List<Item> equipmentList = new ArrayList<>();
        entity.getArmorInventoryList().forEach((x) -> equipmentList.add(x.getItem()));
        boolean flag = equipmentList.contains(ItemRegistry.SEA_CRAWLER_ARMOR);
        if (flag && entity.hasCapability(CapabilityRegistry.capState, null))
        {
            CapabilityState.Process state = new CapabilityState.Process(entity);
            if (state.getStates().contains(EnumState.NERVE_INJURY) && state.getLevel( EnumState.NERVE_INJURY) < 50 + 10)
            {
                if (entity.ticksExisted % 20 == 0 && new Random().nextInt(10) == 0)
                    state.addFunctionOnlyLevel(EnumState.NERVE_INJURY, state.getLevel( EnumState.NERVE_INJURY) - 1);
                if (entity.ticksExisted % 20 == 0)
                    entity.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 20));
            }
        }
    }
}
