package com.weedycow.arkworld.item.tool.weapon;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityPlayer;
import com.weedycow.arkworld.capability.CapabilityStamina;
import com.weedycow.arkworld.capability.CapabilityWeapon;
import com.weedycow.arkworld.capability.EnumEntry;
import com.weedycow.arkworld.entity.enemy.ursus.normal.InfectedPatrolCaptain;
import com.weedycow.arkworld.item.tool.EnumWeaponRank;
import com.weedycow.arkworld.item.tool.MeleeWeapon;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
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
import java.util.List;

public class UrsusKnife extends MeleeWeapon
{
    public UrsusKnife()
    {
        super(InfectedPatrolCaptain.ID,EnumWeaponRank.LOW);
        this.setUnlocalizedName(Arkworld.MODID + ".ursusKnife");
        this.setRegistryName("ursus_knife");
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack,worldIn,entityIn,itemSlot,isSelected);

        if(entityIn instanceof EntityPlayer && !entityIn.world.isRemote)
        {
            CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);

            CapabilityPlayer.Process player  = new CapabilityPlayer.Process((EntityPlayer) entityIn);

            CapabilityStamina.Process stamina = new CapabilityStamina.Process((EntityPlayer) entityIn);

            onDamage(weapon,melee.ursusKnife.initAttackDamage,1);

            onDurability(weapon,melee.ursusKnife.durability);

            if(((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND)==stack)
            {
                float per = 1-(weapon.getAttackSpeed()+item.getRank(EnumEntry.ATTACK_SPEED)/10f);
                if(per<0.25)per=0.25f;
                if(player.isSwing() && player.isAttackable() && stamina.getStamina()>=weapon.getStamina() && weapon.getLoss()<weapon.getDurability())weapon.setAnimationTick((int) (12*per));

                if(weapon.getEntityHit() instanceof EntityLivingBase && weapon.getAnimationTick()==(int)(6*per))
                {
                    onHitFun(stack, (EntityLivingBase) weapon.getEntityHit(), (EntityLivingBase) entityIn, true);
                }
            }
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag)
    {
        super.addInformation(itemstack, world, list, flag);

        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(itemstack);

        String damage = I18n.format("item.arkworld.info.damage") + " : " + weapon.getDamage();
        String durability = I18n.format("item.arkworld.info.durability") + " : " + weapon.getLoss() + "/" + weapon.getDurability();

        list.add(damage);
        list.add(durability);
        list.add(I18n.format("item.arkworld.info.mod")+weapon.getBuffs());
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
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.ursus_knife.attack", false));
                controllerAttack.markNeedsReload();
                player.setTimes(2);
            }
        }
        return true;
    }

    @Override
    public void registerControllers(AnimationData animationData) {animationData.addAnimationController(controllerAttack);}
    
    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event)
    {
        return PlayState.CONTINUE;
    }
    AnimationController<UrsusKnife> controllerAttack = new AnimationController<>(this, "attack", 1, this::predicate);
}
