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
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BasinSeaReaperArmor extends ArkArmor
{
    public static final ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("BASIN_SEA_REAPER", Arkworld.MODID+":basin_sea_reaper",armor.basinSeaReaperArmor.durability,new int[] {0,0,armor.basinSeaReaperArmor.armorChest,0}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0);

    public BasinSeaReaperArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(MATERIAL, equipmentSlotIn,armor.basinSeaReaperArmor.defensivePowerChest,armor.basinSeaReaperArmor.spellResistanceChest);
        this.setUnlocalizedName(Arkworld.MODID + ".basinSeaReaperArmor" + equipmentSlotIn.getName());
        this.setRegistryName("basin_sea_reaper_armor_" + equipmentSlotIn.getName());
        this.setCreativeTab(ArkCreateTab.ARMOR);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.arkworld.info.basinSeaReaperArmor1"));
        tooltip.add(I18n.format("item.arkworld.info.basinSeaReaperArmor2"));
    }

    @Override
    public void registerControllers(AnimationData data)
    {

    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureEntities("basin_sea_reaper");
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("basin_sea_reaper_armor");
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation("basin_sea_reaper_armor");
    }

    public static void onHurt(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        List<Item> equipmentList = new ArrayList<>();
        entity.getArmorInventoryList().forEach((x) -> equipmentList.add(x.getItem()));
        boolean flag = equipmentList.contains(ItemRegistry.BASIN_SEA_REAPER_ARMOR);
        if (flag && entity.hasCapability(CapabilityRegistry.capState, null))
        {
            CapabilityState.Process state = new CapabilityState.Process(entity);
            if (state.getStates().contains(EnumState.NERVE_INJURY) && state.getLevel( EnumState.NERVE_INJURY) < 50 + 10)
            {
                if (new Random().nextInt(3) == 0)
                    state.addFunctionOnlyLevel(EnumState.NERVE_INJURY, state.getLevel( EnumState.NERVE_INJURY) - 1);
                if (new Random().nextInt(100) < 5)
                    state.removeState(EnumState.NERVE_INJURY);
            }
        }

    }
}
