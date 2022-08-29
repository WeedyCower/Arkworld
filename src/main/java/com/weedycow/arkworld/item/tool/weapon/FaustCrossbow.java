package com.weedycow.arkworld.item.tool.weapon;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityPlayer;
import com.weedycow.arkworld.capability.CapabilityStamina;
import com.weedycow.arkworld.capability.CapabilityWeapon;
import com.weedycow.arkworld.entity.enemy.union.boss.Faust;
import com.weedycow.arkworld.entity.weapon.Arrow;
import com.weedycow.arkworld.item.tool.EnumWeaponRank;
import com.weedycow.arkworld.item.tool.ItemArrow;
import com.weedycow.arkworld.item.tool.RangeWeapon;
import com.weedycow.arkworld.item.tool.ammo.FaustSpecialArrow;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.arkworld.util.ArkItemUtil;
import com.weedycow.weedylib.entity.WLA;
import com.weedycow.weedylib.entity.WLMAttributes;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class FaustCrossbow extends RangeWeapon
{
    boolean isShoot;
    boolean isSkill;
    int countdownShoot = 6;
    int countdownSkill = 6;
    Arrow.Type type;

    public FaustCrossbow()
    {
        super(Faust.ID, EnumWeaponRank.SUPER);
        this.setUnlocalizedName(Arkworld.MODID + ".faustCrossbow");
        this.setRegistryName("faust_crossbow");
    }

    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag)
    {
        super.addInformation(itemstack, world, list, flag);

        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(itemstack);

        String add = I18n.format("item.arkworld.info.faustCrossbowInfo");

        list.add(add);

        list.add(I18n.format("item.arkworld.info.mod")+weapon.getBuffs());
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
                if(worldIn.spawnEntity(entityArrow))
                    worldIn.playSound(null,entityIn.posX,entityIn.posY,entityIn.posZ, SoundRegistry.FAUST_ATTACK,SoundCategory.MASTER,1,1);
            }
            
            if(countdownShoot==0)
            {
                Arrow entityArrow = new Arrow(worldIn, (EntityLivingBase) entityIn,weapon.getDamage(), type);
                entityArrow.setPickupStatus(WLA.PickupStatus.DISALLOWED);
                entityArrow.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, shootVelocity, 1.0F);
                if(worldIn.spawnEntity(entityArrow))
                {
                    worldIn.playSound(null,entityIn.posX,entityIn.posY,entityIn.posZ, SoundRegistry.FAUST_ATTACK,SoundCategory.MASTER,1,1);
                    isShoot =false;
                    countdownShoot = 6;
                }
            }
        }

        if(isSkill && weapon.getTripleTap() && !worldIn.isRemote && entityIn instanceof EntityPlayer && weapon.getLoss() < weapon.getDurability()&& ((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND)==stack)
        {
            if(countdownSkill>0)
                countdownSkill=countdownSkill-1;

            if(countdownSkill==3)
            {
                Arrow entityArrow = new Arrow(worldIn, (EntityLivingBase) entityIn,weapon.getDamage(), Arrow.Type.FAUST_SPECIAL_ARROW);
                entityArrow.setPickupStatus(WLA.PickupStatus.DISALLOWED);
                entityArrow.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, shootVelocity, 1.0F);
                if(worldIn.spawnEntity(entityArrow))
                    worldIn.playSound(null,entityIn.posX,entityIn.posY,entityIn.posZ, SoundRegistry.FAUST_ATTACK,SoundCategory.MASTER,1,1);
            }

            if(countdownSkill==0)
            {
                Arrow entityArrow = new Arrow(worldIn, (EntityLivingBase) entityIn,weapon.getDamage(), Arrow.Type.FAUST_SPECIAL_ARROW);
                entityArrow.setPickupStatus(WLA.PickupStatus.DISALLOWED);
                entityArrow.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, shootVelocity, 1.0F);
                if(worldIn.spawnEntity(entityArrow))
                {
                    isSkill =false;
                    countdownSkill = 6;
                    worldIn.playSound(null,entityIn.posX,entityIn.posY,entityIn.posZ, SoundRegistry.FAUST_SKILL,SoundCategory.MASTER,1,1);
                }
            }
        }

        if(entityIn instanceof EntityPlayer && new CapabilityPlayer.Process((EntityPlayer) entityIn).isSwing() &&
                new CapabilityPlayer.Process((EntityPlayer) entityIn).isAttackable()
                && ((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND)==stack)
        {
            CapabilityStamina.Process stamina = new CapabilityStamina.Process((EntityPlayer) entityIn);
            ItemStack arrow = ArkItemUtil.findItemArrowInPlayerSlot((EntityPlayer) entityIn,null);

            if (worldIn.isRemote)
            {
                entityIn.playSound(SoundRegistry.FAUST_ATTACK, 1, 1);
                controllerShoot.setAnimation(new AnimationBuilder().addAnimation("animation.faust_crossbow.shoot", false));
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
            onDamage(weapon, (float) (range.faustCrossbow.initAttackDamage * ((EntityPlayer)entityIn).getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).getAttributeValue()), (float) (range.faustCrossbow.surplusAttackDamage * ((EntityPlayer)entityIn).getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).getAttributeValue()));

        onStamina(weapon,range.faustCrossbow.stamina);

        onDurability(weapon,range.faustCrossbow.durability);
    }
    
    @Override
    @ParametersAreNonnullByDefault
    public int getMaxItemUseDuration(ItemStack stack) {return 0;}
    
    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn)
    {
        ItemStack faustCrossbow = playerIn.getHeldItem(handIn);
        ItemStack arrow = ArkItemUtil.findItemStackInPlayerSlot(playerIn, FaustSpecialArrow.class);
        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(faustCrossbow);
        CapabilityStamina.Process stamina = new CapabilityStamina.Process(playerIn);

        if (stamina.getStamina() >= weapon.getStamina()*2 && weapon.getLoss() < weapon.getDurability() && new CapabilityPlayer.Process(playerIn).isAttackable() && playerIn.getHeldItem(EnumHand.MAIN_HAND)==faustCrossbow)
        {
            if (!arrow.isEmpty())
            {
                if (worldIn.isRemote)
                {
                    playerIn.playSound(SoundRegistry.FAUST_SKILL, 1, 1);
                    controllerShoot.setAnimation(new AnimationBuilder().addAnimation("animation.faust_crossbow.shoot", false));
                    controllerShoot.markNeedsReload();
                }
                else
                {
                    Arrow entityArrow = new Arrow(worldIn,playerIn,((ItemArrow)arrow.getItem()).getDamage()+weapon.getDamage(),((ItemArrow)arrow.getItem()).getType());
                    entityArrow.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, shootVelocity, 1.0F);
                    if (worldIn.spawnEntity(entityArrow))
                    {
                        isSkill = weapon.getTripleTap();
                        arrow.shrink(1);
                        stamina.reduceStamina(weapon.getStamina()*2);
                        if (weapon.getLossReduce() > 0)
                            weapon.addLoss((int) (weapon.getRank() * weapon.getLossReduce() * 2));
                        else
                            weapon.addLoss(weapon.getRank()*2);
                    }
                }
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, faustCrossbow);
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
    public void registerControllers(AnimationData animationData) {animationData.addAnimationController(controllerShoot);}

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {return PlayState.CONTINUE;}
    AnimationController<FaustCrossbow> controllerShoot = new AnimationController<>(this, "attack", 1, this::predicate);
}