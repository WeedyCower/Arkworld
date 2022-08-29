package com.weedycow.arkworld.item.armor;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
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
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GuerrillaSiegebreakerArmor extends ArkArmor
{
    public static final ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("GUERRILLA_SIEGEBREAKER", Arkworld.MODID+":guerrilla_siegebreaker",armor.guerrillaSiegebreakerArmor.durability,new int[] {0,0,armor.guerrillaSiegebreakerArmor.armorChest,0}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0);

    AnimationController controllerIdle = new AnimationController(this, "idle", 1, this::PlayState);

    public GuerrillaSiegebreakerArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(MATERIAL, equipmentSlotIn,armor.guerrillaSiegebreakerArmor.defensivePowerChest,armor.guerrillaSiegebreakerArmor.spellResistanceChest);
        this.setUnlocalizedName(Arkworld.MODID + ".guerrillaSiegebreakerArmor" + equipmentSlotIn.getName());
        this.setRegistryName("guerrilla_siegebreaker_armor_" + equipmentSlotIn.getName());
        this.setCreativeTab(ArkCreateTab.ARMOR);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.arkworld.info.guerrillaSiegebreakerArmor"));
    }

    private <P extends IAnimatable> PlayState PlayState(AnimationEvent<P> event)
    {
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
        return ArkResUtil.textureEntities("guerrilla_siegebreaker");
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("guerrilla_siegebreaker_armor");
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation("guerrilla_siegebreaker_armor");
    }

    public static void onUpdating(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        List<Item> equipmentList = new ArrayList<>();
        entity.getArmorInventoryList().forEach((x) -> equipmentList.add(x.getItem()));
        boolean flag = equipmentList.contains(ItemRegistry.GUERRILLA_SIEGEBREAKER_ARMOR);
        if (flag) entity.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 220, 2));
    }
}
