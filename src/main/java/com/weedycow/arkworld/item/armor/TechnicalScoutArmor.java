package com.weedycow.arkworld.item.armor;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.util.AoeRangeUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.ArrayList;
import java.util.List;

public class TechnicalScoutArmor extends ArkArmor
{
    public static final ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("TECHNICAL_SCOUT", Arkworld.MODID+":technical_scout",armor.technicalScoutArmor.durability,new int[] {0,0,0,armor.technicalScoutArmor.armorHead}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0);

    AnimationController controllerIdle = new AnimationController(this, "idle", 1, this::PlayState);

    public TechnicalScoutArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(MATERIAL, equipmentSlotIn,armor.technicalScoutArmor.defensivePowerHead,armor.technicalScoutArmor.spellResistanceHead);
        this.setUnlocalizedName(Arkworld.MODID + ".technicalScoutArmor" + equipmentSlotIn.getName());
        this.setRegistryName("technical_scout_armor_" + equipmentSlotIn.getName());
        this.setCreativeTab(ArkCreateTab.ARMOR);
    }

    private <P extends IAnimatable> PlayState PlayState(AnimationEvent<P> event)
    {
        controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.technical_scout.idle", true));
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
        return ArkResUtil.textureEntities("technical_scout");
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("technical_scout_armor");
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation("technical_scout_armor");
    }

    public static void onUpdating(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();

        List<Item> equipmentList = new ArrayList<>();
        entity.getArmorInventoryList().forEach((x) -> equipmentList.add(x.getItem()));
        boolean flag = equipmentList.contains(ItemRegistry.TECHNICAL_SCOUT_ARMOR);
        if (flag && entity.ticksExisted%40==0)
        {
            for(EntityLivingBase e : entity.world.getEntitiesWithinAABB(EntityLivingBase.class,new AoeRangeUtil(entity,8)))
            {
                if(e.hasCapability(CapabilityRegistry.capState,null))
                {
                    CapabilityState.Process state = new CapabilityState.Process(e);
                    if(state.getStates().contains(EnumState.HIDE) || state.getStates().contains(EnumState.CAMOUFLAGE))
                    {
                        state.removeState(EnumState.HIDE);
                        state.removeState(EnumState.CAMOUFLAGE);
                    }
                }
            }
        }
    }
}
