package com.weedycow.arkworld.item.tool.weapon;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.*;
import com.weedycow.arkworld.entity.enemy.union.boss.Mayfest;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.entity.weapon.SimpleThrowable;
import com.weedycow.arkworld.item.tool.EnumWeaponRank;
import com.weedycow.arkworld.item.tool.RangeWeapon;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.util.AoeRangeUtil;
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
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class HealingStaff extends RangeWeapon
{
    int countdownShoot = 6;
    boolean isShoot;

    public HealingStaff()
    {
        super(Mayfest.ID, EnumWeaponRank.SUPER);
        this.setUnlocalizedName(Arkworld.MODID + ".healingStaff");
        this.setRegistryName("healing_staff");
    }

    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag)
    {
        super.addInformation(itemstack, world, list, flag);

        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(itemstack);

        String i1 = I18n.format("item.arkworld.info.frostmourne1");

        String i2 = I18n.format("item.arkworld.info.frostmourne2");

        String range = I18n.format("item.arkworld.info.attack_range");

        float r = weapon.getLevel()/2f+1;

        list.add(range+r);

        list.add(i1);

        list.add(i2);

        list.add(I18n.format("item.arkworld.info.mod")+weapon.getBuffs());
    }

    @Override
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);
        CapabilityMana.Process mana = new CapabilityMana.Process(playerIn);

        if (mana.getMana() >= weapon.getMana()*2 && weapon.getLoss() < weapon.getDurability() && new CapabilityPlayer.Process(playerIn).isAttackable() && (playerIn).getHeldItem(handIn)==stack)
        {
            if (worldIn.isRemote)
            {
                controllerShoot.setAnimation(new AnimationBuilder().addAnimation("animation.healing_staff.skill", false));
                controllerShoot.markNeedsReload();
            }
            else
            {
                float per = 1-(weapon.getAttackSpeed()+item.getRank(EnumEntry.ATTACK_SPEED)/10f+0.5f*((float) weapon.getLevel()/weapon.getMaxLevel()));
                if(per<0.25)per=0.25f;
                weapon.setCountdown((int) (40*per));
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack,worldIn,entityIn,itemSlot,isSelected);

        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);

        if(entityIn instanceof EntityPlayer)
        {
            CapabilityMana.Process mana = new CapabilityMana.Process((EntityPlayer) entityIn);

            CapabilityPlayer.Process player = new CapabilityPlayer.Process((EntityPlayer) entityIn);

            if (isShoot && weapon.getTripleTap() && !worldIn.isRemote && weapon.getLoss() < weapon.getDurability() && ((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND) == stack)
            {
                --countdownShoot;
                if (countdownShoot == 3)
                {
                    SimpleThrowable throwable = new SimpleThrowable(worldIn, (EntityLivingBase) entityIn, weapon.getDamage(), SimpleThrowable.Type.HEALING_BALL);
                    throwable.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, shootVelocity, 1.0F);
                    worldIn.spawnEntity(throwable);
                }
                if (countdownShoot == 0)
                {
                    SimpleThrowable throwable = new SimpleThrowable(worldIn, (EntityLivingBase) entityIn, weapon.getDamage(), SimpleThrowable.Type.HEALING_BALL);
                    throwable.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, shootVelocity, 1.0F);
                    if (worldIn.spawnEntity(throwable))
                    {
                        isShoot = false;
                        countdownShoot = 6;
                    }
                }
            }

            if (((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND) == stack)
            {
                float per = item.getRank(EnumEntry.ATTACK_SPEED) > 0 ? 1 - weapon.getAttackSpeed() * item.getRank(EnumEntry.ATTACK_SPEED) : 1 - weapon.getAttackSpeed();

                if (player.isSwing() && player.isAttackable() && mana.getMana() >= weapon.getMana() && weapon.getLoss() < weapon.getDurability())
                    weapon.setAnimationTick((int) (40 * per));

                if (weapon.getAnimationTick() == (int) (20 * per)&& !worldIn.isRemote)
                {
                    SimpleThrowable throwable = new SimpleThrowable(worldIn, (EntityLivingBase) entityIn, weapon.getDamage(), SimpleThrowable.Type.HEALING_BALL);
                    throwable.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, shootVelocity, 1.0F);
                    if (worldIn.spawnEntity(throwable))
                    {
                        isShoot = true;
                        mana.reduceMana(weapon.getMana());
                        if (weapon.getLossReduce() > 0)
                            weapon.addLoss((int) (weapon.getRank() * weapon.getLossReduce()));
                        else
                            weapon.addLoss(weapon.getRank());

                    }

                }
            }

            if (weapon.getCountdown() == 20 && ((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND) == stack)
            {
                if (mana.getMana() >= weapon.getMana() * 2 && weapon.getLoss() < weapon.getDurability())
                {
                    for (EntityLivingBase target : entityIn.world.getEntitiesWithinAABB(EntityLivingBase.class, new AoeRangeUtil(entityIn, weapon.getLevel() / 2f + 1)))
                    {
                        if (target instanceof EntityPlayer || target instanceof Operator)
                            target.heal(weapon.getDamage());
                        else if (target.hasCapability(CapabilityRegistry.capState, null))
                            new CapabilityState.Process(target).addFunctionOnlyTick(EnumState.BANEAL, weapon.getLevel() * 2 + 1);
                        worldIn.createExplosion(entityIn, target.posX, target.posY, target.posZ, 0, false);
                    }

                    mana.reduceMana(weapon.getMana() * 2);

                    if (weapon.getLossReduce() > 0)
                        weapon.addLoss((int) (weapon.getRank() * weapon.getLossReduce() * 2));
                    else
                        weapon.addLoss(weapon.getRank() * 2);
                }
            }

            onDamage(weapon, (float) (range.healingStaff.initAttackDamage * ((EntityPlayer)entityIn).getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).getAttributeValue()), (float) (range.healingStaff.surplusAttackDamage * ((EntityPlayer)entityIn).getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).getAttributeValue()));

            onMana(weapon,range.healingStaff.mana);

            onDurability(weapon,range.healingStaff.durability);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        if(entityLiving instanceof EntityPlayer)
        {
            CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);
            CapabilityMana.Process mana = new CapabilityMana.Process((EntityPlayer) entityLiving);
            CapabilityPlayer.Process player = new CapabilityPlayer.Process((EntityPlayer) entityLiving);

            if (mana.getMana() >= weapon.getMana() && weapon.getLoss() < weapon.getDurability() && player.isAttackable())
            {
                controllerShoot.setAnimation(new AnimationBuilder().addAnimation("animation.healing_staff.attack", false));
                controllerShoot.markNeedsReload();
                player.setTimes(2);
            }
        }
        return true;
    }

    @Override
    public boolean isMagic()
    {
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public int getMaxItemUseDuration(ItemStack stack) {return 0;}

    @Override
    public void registerControllers(AnimationData animationData) {animationData.addAnimationController(controllerShoot);}

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {return PlayState.CONTINUE;}
    AnimationController<HealingStaff> controllerShoot = new AnimationController<>(this, "attack", 1, this::predicate);
}
