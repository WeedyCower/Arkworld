package com.weedycow.arkworld.item.tool.weapon;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityPlayer;
import com.weedycow.arkworld.capability.CapabilityStamina;
import com.weedycow.arkworld.capability.CapabilityWeapon;
import com.weedycow.arkworld.capability.EnumEntry;
import com.weedycow.arkworld.entity.enemy.union.boss.Skullshatterer;
import com.weedycow.arkworld.entity.weapon.Bomb;
import com.weedycow.arkworld.item.tool.EnumWeaponRank;
import com.weedycow.arkworld.item.tool.ItemBomb;
import com.weedycow.arkworld.item.tool.MeleeWeapon;
import com.weedycow.arkworld.item.tool.ammo.Grenade;
import com.weedycow.arkworld.util.ArkItemUtil;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.arkworld.util.ParticleList;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
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

public class GrenadeGun extends MeleeWeapon
{
    boolean rightClick;

    public GrenadeGun()
    {
        super(Skullshatterer.ID,EnumWeaponRank.SUPER);
        this.setUnlocalizedName(Arkworld.MODID + ".grenadeGun");
        this.setRegistryName("grenade_gun");
        this.rightClick=true;
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
        String add = I18n.format("item.arkworld.info.grenadeGunInfo");

        list.add(damage);
        list.add(stamina);
        list.add(durability);
        list.add(add.substring(0,7));
        list.add(add.substring(7));
        list.add(I18n.format("item.arkworld.info.mod")+weapon.getBuffs());
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack,worldIn,entityIn,itemSlot,isSelected);

        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);

        if(entityIn instanceof EntityPlayer && !entityIn.world.isRemote)
        {
            CapabilityPlayer.Process player  = new CapabilityPlayer.Process((EntityPlayer) entityIn);

            CapabilityStamina.Process stamina = new CapabilityStamina.Process((EntityPlayer) entityIn);

            onStamina(weapon,rangeMelee.grenadeGun.stamina);

            onDamage(weapon,rangeMelee.grenadeGun.initAttackDamage,rangeMelee.grenadeGun.surplusAttackDamage);

            onDurability(weapon,rangeMelee.grenadeGun.durability);

            ParticleList.skullshattererParticle((EntityPlayer) entityIn);

            if(((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND)==stack)
            {
                float per = 1-(weapon.getAttackSpeed()+item.getRank(EnumEntry.ATTACK_SPEED)/10f+0.5f*((float) weapon.getLevel()/weapon.getMaxLevel()));
                if(per<0.25)per=0.25f;
                if(player.isSwing() && player.isAttackable() && stamina.getStamina()>=weapon.getStamina() && weapon.getLoss()<weapon.getDurability())weapon.setAnimationTick((int) (per*20));

                if(weapon.getEntityHit() instanceof EntityLivingBase && weapon.getAnimationTick()==(int)(10*per))
                {
                    EntityLivingBase entity = (EntityLivingBase) weapon.getEntityHit();

                    onHitFun(stack, entity, (EntityLivingBase) entityIn, true);

                    weapon.setRightClick(false);
                }

                ItemStack itemStack = ArkItemUtil.findItemBombInPlayerSlot((EntityPlayer) entityIn,Bomb.grenadeGun);

                if(stamina.getStamina() >= weapon.getStamina() && weapon.getLoss() < weapon.getDurability() && !itemStack.isEmpty() && itemStack.getCount() >=2)
                {
                    Bomb entityBomb1 = ((ItemBomb) itemStack.getItem()).createArrow(worldIn, (EntityLivingBase) entityIn);
                    Bomb entityBomb2 = ((ItemBomb) itemStack.getItem()).createArrow(worldIn, (EntityLivingBase) entityIn);

                    if (weapon.getShootVelocity() > 0)
                    {
                        entityBomb1.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, ((float) weapon.getLevel() / 15 + 0.1f) * weapon.getShootVelocity(), 1);
                        entityBomb2.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, ((float) weapon.getLevel() / 15 + 0.1f) * weapon.getShootVelocity(), 1);
                    }
                    else
                    {
                        entityBomb1.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, (float) weapon.getLevel() / 15 + 0.1f, 1);
                        entityBomb2.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, (float) weapon.getLevel() / 15 + 0.1f, 1);
                    }

                    if(weapon.getCountdown()==8)
                    {
                        if (worldIn.spawnEntity(entityBomb1))
                            itemStack.shrink(1);
                    }

                    if(weapon.getCountdown()==13)
                    {
                        if (worldIn.spawnEntity(entityBomb2))
                        {
                            itemStack.shrink(1);

                            if (weapon.getLossReduce() > 0)
                                weapon.addLoss((int) (weapon.getRank() * weapon.getLossReduce()));
                            else
                                weapon.addLoss(weapon.getRank());

                            stamina.reduceStamina(weapon.getStamina());
                        }
                    }
                }
            }
        }

        rightClick=weapon.getRightClick();
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
            weapon.setRightClick(false);
            if (stamina.getStamina() >= weapon.getStamina() && weapon.getLoss() < weapon.getDurability() && player.isAttackable())
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.skullshatterer_melee.attack", false));
                controllerAttack.markNeedsReload();
                player.setTimes(2);
            }
        }
        return true;
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn)
    {
        ItemStack itemStack = ArkItemUtil.findItemBombInPlayerSlot(playerIn,Bomb.grenadeGun);
        ItemStack grenadeGun = playerIn.getHeldItem(handIn);
        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(grenadeGun);
        CapabilityStamina.Process stamina = new CapabilityStamina.Process(playerIn);
        if(stamina.getStamina() >= weapon.getStamina() && weapon.getLoss() < weapon.getDurability() && !itemStack.isEmpty() && itemStack.getCount() >=2 && new CapabilityPlayer.Process(playerIn).isAttackable())
        {
            weapon.setRightClick(true);

            if(worldIn.isRemote)
            {
                controllerShoot.setAnimation(new AnimationBuilder().addAnimation("animation.skullshatterer_range.attack", false));
                controllerShoot.markNeedsReload();
            }
            else
            {
                weapon.setCountdown(17);
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void registerControllers(AnimationData animationData) {animationData.addAnimationController(controllerAttack); animationData.addAnimationController(controllerShoot);}

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {return PlayState.CONTINUE;}
    AnimationController<GrenadeGun> controllerAttack = new AnimationController<>(this, "attack", 1, this::predicate);
    AnimationController<GrenadeGun> controllerShoot = new AnimationController<>(this, "skill", 1, this::predicate);

    @Override
    @ParametersAreNonnullByDefault
    public boolean isDamaged(ItemStack stack) {return false;}

    public boolean getRightClick()
    {
        return rightClick;
    }

    public boolean isAmmo(ItemStack stack)
    {
        return stack.getItem() instanceof Grenade;
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureEntities(id);
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        if(getRightClick())
            return ArkResUtil.geo("grenade_gun");
        else
            return ArkResUtil.geo("grenade_gun_melee");
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        if(getRightClick())
            return ArkResUtil.animation("skullshatterer_range");
        else
            return ArkResUtil.animation("grenade_gun_melee");
    }
}
