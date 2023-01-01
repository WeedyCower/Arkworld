package com.weedycow.arkworld.item.armor;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.util.MathUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
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
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SurtrArmor extends ArkArmor
{
    public static final ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("SURTR", Arkworld.MODID+":surtr",armor.surtrArmor.durability,new int[] {0,0,armor.surtrArmor.armorChest,0}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0);

    AnimationController controllerIdle = new AnimationController(this, "idle", 1, this::PlayState);

    public SurtrArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(MATERIAL,equipmentSlotIn,armor.surtrArmor.defensivePowerChest,armor.surtrArmor.spellResistanceChest);
        this.setUnlocalizedName(Arkworld.MODID + ".surtrArmor" + equipmentSlotIn.getName());
        this.setRegistryName("surtr_armor_" + equipmentSlotIn.getName());
        this.setCreativeTab(ArkCreateTab.ARMOR);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(I18n.format("item.arkworld.info.surtrArmor"));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        List<Item> equipmentList = new ArrayList<>();
        entityIn.getArmorInventoryList().forEach((x) -> equipmentList.add(x.getItem()));

        if(entityIn instanceof EntityPlayer && equipmentList.contains(ItemRegistry.SURTR_ARMOR))
        {
            if(entityIn.world.isRemote)
            {
                double x = entityIn.posX-entityIn.getLookVec().x*2.5 + MathUtil.getRandomDouble()*2;
                double y = entityIn.posY;
                double z = entityIn.posZ-entityIn.getLookVec().z*2.5 + MathUtil.getRandomDouble()*2;
                for (int l = 0; l < 3; ++l)
                {
                    double x0 = x + MathUtil.getRandomDouble();
                    double y0 = y + MathUtil.getRandomDouble();
                    double z0 = z + MathUtil.getRandomDouble();
                    entityIn.world.spawnParticle(EnumParticleTypes.LAVA, x0, y0, z0, 0, 0, 0);
                }
            }
        }
    }

    private <P extends IAnimatable> PlayState PlayState(AnimationEvent<P> event)
    {
        controllerIdle.setAnimation(new AnimationBuilder().addAnimation("animation.surtr.idle", true));
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
        return ArkResUtil.textureEntities("surtr_armor");
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo("surtr_armor");
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation("surtr_armor");
    }

    public static void onHurt(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        List<Item> equipmentList = new ArrayList<>();
        entity.getEquipmentAndArmor().forEach((x) -> equipmentList.add(x.getItem()));
        List<Item> armorList = equipmentList.subList(2, 6);
        boolean flag = armorList.contains(ItemRegistry.SURTR_ARMOR);
        if (flag && entity.hasCapability(CapabilityRegistry.capState, null))
        {
            CapabilityState.Process state = new CapabilityState.Process(entity);
            if (state.getStates().contains(EnumState.BURNING_BREATH))
            {
                if (new Random().nextInt(3) == 0)
                    state.addFunctionOnlyTick(EnumState.NERVE_INJURY, state.getTick(EnumState.BURNING_BREATH)/2);
            }
            event.setCanceled(event.getSource().isFireDamage());
        }
    }
}
