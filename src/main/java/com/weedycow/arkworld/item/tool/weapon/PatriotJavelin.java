package com.weedycow.arkworld.item.tool.weapon;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityPlayer;
import com.weedycow.arkworld.capability.CapabilityStamina;
import com.weedycow.arkworld.capability.CapabilityWeapon;
import com.weedycow.arkworld.capability.EnumEntry;
import com.weedycow.arkworld.entity.enemy.union.boss.Patriot;
import com.weedycow.arkworld.entity.weapon.Arrow;
import com.weedycow.arkworld.item.tool.EnumWeaponRank;
import com.weedycow.arkworld.item.tool.MeleeWeapon;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
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
import net.minecraft.util.ResourceLocation;
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

public class PatriotJavelin extends MeleeWeapon
{
    boolean rightClick;

    public PatriotJavelin()
    {
        super(Patriot.ID,EnumWeaponRank.SUPER);
        this.setUnlocalizedName(Arkworld.MODID + ".patriotJavelin");
        this.setRegistryName("patriot_javelin");
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack,worldIn,entityIn,itemSlot,isSelected);

        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);

        if(entityIn instanceof EntityPlayer && !entityIn.world.isRemote)
        {
            onStamina(weapon,rangeMelee.patriotJavelin.stamina);

            onDamage(weapon,rangeMelee.patriotJavelin.initAttackDamage,rangeMelee.patriotJavelin.surplusAttackDamage);

            onDurability(weapon,rangeMelee.patriotJavelin.durability);

            if(((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND)==stack)
            {
                CapabilityPlayer.Process player  = new CapabilityPlayer.Process((EntityPlayer) entityIn);

                CapabilityStamina.Process stamina = new CapabilityStamina.Process((EntityPlayer) entityIn);

                float per = item.getRank(EnumEntry.ATTACK_SPEED) > 0 ? 1 - weapon.getAttackSpeed() * item.getRank(EnumEntry.ATTACK_SPEED) : 1 - weapon.getAttackSpeed();

                if (player.isSwing() && player.isAttackable() && stamina.getStamina() >= weapon.getStamina() && weapon.getLoss() < weapon.getDurability())
                    weapon.setAnimationTick((int) (per * 12));

                if (weapon.getEntityHit() instanceof EntityLivingBase && weapon.getAnimationTick() == (int) (6 * per))
                {
                    EntityLivingBase entity = (EntityLivingBase) weapon.getEntityHit();

                    onHitFun(stack, entity, (EntityLivingBase) entityIn, true);

                    weapon.setRightClick(false);
                }
            }
        }

        if(entityIn instanceof EntityPlayer)
            onShoot(stack, entityIn.world, (EntityPlayer) entityIn);

        rightClick=weapon.getRightClick();
    }

    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag)
    {
        super.addInformation(itemstack, world, list, flag);

        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(itemstack);

        String damage = I18n.format("item.arkworld.info.damage") + " : " + weapon.getDamage();
        String stamina = I18n.format("item.arkworld.info.stamina") + " : " + weapon.getStamina();
        String durability = I18n.format("item.arkworld.info.durability") + " : " + weapon.getLoss() + "/" + weapon.getDurability();
        String add = I18n.format("item.arkworld.info.patriot");

        list.add(damage);
        list.add(stamina);
        list.add(durability);
        list.add(add.substring(0,7));
        list.add(add.substring(7,17));
        list.add(add.substring(17,29));
        list.add(add.substring(29));
        list.add(I18n.format("item.arkworld.info.mod")+weapon.getBuffs());
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(itemstack);
        CapabilityStamina.Process stamina = new CapabilityStamina.Process(playerIn);
        if(stamina.getStamina()>=weapon.getStamina()*2 && weapon.getLoss()<weapon.getDurability() && new CapabilityPlayer.Process(playerIn).isAttackable())
        {
            weapon.setRightClick(true);
            playerIn.setActiveHand(handIn);
            controllerAttack.markNeedsReload();
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.patriot_javelin.throw", false));
        }

        return new ActionResult<>(EnumActionResult.PASS,itemstack);
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

            if (stamina.getStamina() >= weapon.getStamina() && weapon.getLoss() < weapon.getDurability() && (player.isAttackable()||weapon.getRightClick()))
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.patriot_javelin.attack", false));
                player.setTimes(2);
                controllerAttack.markNeedsReload();
            }
        }
        return true;
    }

    public void onShoot(ItemStack stack, World worldIn, EntityPlayer entityLiving)
    {
        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);
        CapabilityStamina.Process stamina = new CapabilityStamina.Process(entityLiving);

        if (new CapabilityPlayer.Process(entityLiving).isSwing() && weapon.getRightClick() && entityLiving.getHeldItem(EnumHand.MAIN_HAND)==stack)
        {
            if(!entityLiving.world.isRemote)
            {
                Arrow arrow = new Arrow(worldIn, entityLiving, weapon.getDamage(), Arrow.Type.PATRIOT_JAVELIN);
                arrow.tickPick = 300 / (weapon.getLevel() + 1);
                arrow.rangePick = weapon.getLevel() / 2f + 1;
                arrow.tag = stack.getCapability(CapabilityRegistry.capWeapon, null).serializeNBT();

                if (weapon.getShootVelocity() > 0)
                    arrow.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.1f + ((float) weapon.getLevel() / 15) * weapon.getShootVelocity(), 1.0F);
                else
                    arrow.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.1f + ((float) weapon.getLevel() / 15), 1.0F);

                if(worldIn.spawnEntity(arrow))
                {
                    weapon.setRightClick(false);

                    stamina.reduceStamina(weapon.getStamina() * 2);

                    if (weapon.getLossReduce() > 0)
                        weapon.addLoss((int) (weapon.getRank() * weapon.getLossReduce() * 2));
                    else
                        weapon.addLoss(weapon.getRank() * 2);

                    stack.shrink(1);
                }
            }
        }
    }

    public boolean isRightClick()
    {
        return rightClick;
    }

    @Override
    public void registerControllers(AnimationData animationData) {animationData.addAnimationController(controllerAttack);}

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event)
    {
        return PlayState.CONTINUE;
    }

    AnimationController<PatriotJavelin> controllerAttack = new AnimationController<>(this, "attack", 1, this::predicate);

    @Override
    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureItems(getRegistryName().getResourcePath());
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        if(isRightClick())
            return ArkResUtil.geo("patriot_javelin_throw");
        else
            return ArkResUtil.geo("patriot_javelin");
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation(getRegistryName().getResourcePath());
    }
}
