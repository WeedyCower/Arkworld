package com.weedycow.arkworld.item.tool.weapon;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityPlayer;
import com.weedycow.arkworld.capability.CapabilityStamina;
import com.weedycow.arkworld.capability.CapabilityWeapon;
import com.weedycow.arkworld.entity.enemy.union.normal.Crossbowman;
import com.weedycow.arkworld.entity.weapon.Arrow;
import com.weedycow.arkworld.item.tool.EnumWeaponRank;
import com.weedycow.arkworld.item.tool.ItemArrow;
import com.weedycow.arkworld.item.tool.RangeWeapon;
import com.weedycow.arkworld.util.ArkItemUtil;
import com.weedycow.weedylib.entity.WLA;
import com.weedycow.weedylib.entity.WLMAttributes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.ParametersAreNonnullByDefault;

public class NormalCrossbow extends RangeWeapon
{
    int countdownShoot = 6;
    boolean isShoot;
    Arrow.Type type;

    public NormalCrossbow()
    {
        super(Crossbowman.ID, EnumWeaponRank.NORMAL);
        this.setUnlocalizedName(Arkworld.MODID + ".normalCrossbow");
        this.setRegistryName("normal_crossbow");
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack,worldIn,entityIn,itemSlot,isSelected);

        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);

        if(isShoot && weapon.getTripleTap() && !worldIn.isRemote && entityIn instanceof EntityPlayer && weapon.getLoss() < weapon.getDurability() && ((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND)==stack)
        {
            if(countdownShoot>0)
                countdownShoot=countdownShoot-1;

            if(countdownShoot==3)
            {
                Arrow entityArrow = new Arrow(worldIn, (EntityLivingBase) entityIn,weapon.getDamage(), type);
                entityArrow.setPickupStatus(WLA.PickupStatus.DISALLOWED);
                entityArrow.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, shootVelocity, 1.0F);
                worldIn.spawnEntity(entityArrow);
            }

            if(countdownShoot==0)
            {
                Arrow entityArrow = new Arrow(worldIn, (EntityLivingBase) entityIn,weapon.getDamage(), type);
                entityArrow.setPickupStatus(WLA.PickupStatus.DISALLOWED);
                entityArrow.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, shootVelocity, 1.0F);
                if(worldIn.spawnEntity(entityArrow))
                {
                    isShoot =false;
                    countdownShoot = 6;
                }
            }
        }

        if(entityIn instanceof EntityPlayer && new CapabilityPlayer.Process((EntityPlayer) entityIn).isSwing() && new CapabilityPlayer.Process((EntityPlayer) entityIn).isAttackable() && ((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND)==stack)
        {
            CapabilityStamina.Process stamina = new CapabilityStamina.Process((EntityPlayer) entityIn);
            ItemStack arrow = ArkItemUtil.findItemArrowInPlayerSlot((EntityPlayer) entityIn,Arrow.normalCrossbow);

            if (worldIn.isRemote)
            {
                controllerShoot.setAnimation(new AnimationBuilder().addAnimation("animation.normal_crossbow.shoot", false));
                controllerShoot.markNeedsReload();
            }
            else if(arrow != ItemStack.EMPTY)
            {
                Arrow entityArrow = new Arrow(worldIn, (EntityLivingBase) entityIn,((ItemArrow)arrow.getItem()).getDamage()+weapon.getDamage(),((ItemArrow)arrow.getItem()).getType());
                entityArrow.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, shootVelocity, 1.0F);
                if (worldIn.spawnEntity(entityArrow))
                {
                    type=((ItemArrow)arrow.getItem()).getType();
                    isShoot = weapon.getTripleTap();
                    arrow.shrink(1);
                    stamina.reduceStamina(weapon.getStamina());
                    if (weapon.getLossReduce() > 0)
                        weapon.addLoss((int) (weapon.getRank() * weapon.getLossReduce()));
                    else
                        weapon.addLoss(weapon.getRank());
                }
            }
        }

        if(entityIn instanceof EntityPlayer)
            onDamage(weapon, (float) (range.normalCrossbow.initAttackDamage * ((EntityPlayer)entityIn).getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).getAttributeValue()), (float) (range.normalCrossbow.surplusAttackDamage * ((EntityPlayer)entityIn).getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).getAttributeValue()));

        onStamina(weapon,range.normalCrossbow.stamina);

        onDurability(weapon,range.normalCrossbow.durability);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        if(entityLiving instanceof EntityPlayer)
        {
            CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);
            CapabilityStamina.Process stamina = new CapabilityStamina.Process((EntityPlayer) entityLiving);
            CapabilityPlayer.Process player = new CapabilityPlayer.Process((EntityPlayer) entityLiving);

            if (stamina.getStamina() >= weapon.getStamina() && weapon.getLoss() < weapon.getDurability() && player.isAttackable())
            {
                player.setTimes(2);
            }

        }
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public int getMaxItemUseDuration(ItemStack stack) {return 0;}

    @Override
    public void registerControllers(AnimationData animationData) {animationData.addAnimationController(controllerShoot);}

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {return PlayState.CONTINUE;}
    AnimationController<NormalCrossbow> controllerShoot = new AnimationController<>(this, "attack", 1, this::predicate);
}
