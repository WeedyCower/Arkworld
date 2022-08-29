package com.weedycow.arkworld.item.tool.weapon;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.*;
import com.weedycow.arkworld.entity.enemy.union.elite.DefenseCrusher;
import com.weedycow.arkworld.item.tool.EnumWeaponRank;
import com.weedycow.arkworld.item.tool.MeleeWeapon;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.util.AoeRangeUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class SmashingHammer extends MeleeWeapon
{
    boolean rightClick;

    public SmashingHammer()
    {
        super(DefenseCrusher.ID,EnumWeaponRank.HIGH);
        this.setUnlocalizedName(Arkworld.MODID + ".smashingHammer");
        this.setRegistryName("smashing_hammer");
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
        String add = I18n.format("item.arkworld.info.smashing");

        list.add(damage);
        list.add(stamina);
        list.add(durability);
        list.add(add.substring(0,8));
        list.add(add.substring(8));

        list.add(I18n.format("item.arkworld.info.mod")+weapon.getBuffs());
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack,worldIn,entityIn,itemSlot,isSelected);

        BlockPos blockPos = new BlockPos(entityIn.posX,entityIn.posY,entityIn.posZ);
        if(isRightClick() && entityIn.fallDistance >= 1 && worldIn.getBlockState(blockPos.down()).getBlock() != Blocks.AIR
                && worldIn.getBlockState(blockPos.down().east()).getBlock() != Blocks.AIR
                && worldIn.getBlockState(blockPos.down().east().south()).getBlock() != Blocks.AIR
                && worldIn.getBlockState(blockPos.down().east().north()).getBlock() != Blocks.AIR
                && worldIn.getBlockState(blockPos.down().west()).getBlock() != Blocks.AIR
                && worldIn.getBlockState(blockPos.down().west().south()).getBlock() != Blocks.AIR
                && worldIn.getBlockState(blockPos.down().west().north()).getBlock() != Blocks.AIR
                && worldIn.getBlockState(blockPos.down().north()).getBlock() != Blocks.AIR
                && worldIn.getBlockState(blockPos.down().south()).getBlock() != Blocks.AIR)
        {
            onPlayerStoppedUsing(stack, worldIn, (EntityLivingBase) entityIn, 0);
        }

        if(entityIn instanceof EntityPlayer && !entityIn.world.isRemote)
        {
            CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);

            CapabilityPlayer.Process player  = new CapabilityPlayer.Process((EntityPlayer) entityIn);

            CapabilityStamina.Process stamina = new CapabilityStamina.Process((EntityPlayer) entityIn);

            onStamina(weapon,melee.smashingHammer.stamina);

            onDamage(weapon,melee.smashingHammer.initAttackDamage,melee.smashingHammer.surplusAttackDamage);

            onDurability(weapon,melee.smashingHammer.durability);

            if(((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND)==stack)
            {
                float per = 1-(weapon.getAttackSpeed()+item.getRank(EnumEntry.ATTACK_SPEED)/10f+0.5f*((float) weapon.getLevel()/weapon.getMaxLevel()));
                if(per<0.25)per=0.25f;
                if(player.isSwing() && player.isAttackable() && stamina.getStamina()>=weapon.getStamina() && weapon.getLoss()<weapon.getDurability())weapon.setAnimationTick((int) (30*per));

                if(weapon.getEntityHit() instanceof EntityLivingBase && weapon.getAnimationTick()==(int)(10*per))
                {
                    weapon.setRightClick(false);

                    onHitFun(stack, (EntityLivingBase) weapon.getEntityHit(), (EntityLivingBase) entityIn, true);
                }
            }
        }

        rightClick=new CapabilityWeapon.Process(stack).getRightClick();
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
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.hammer.attack", false));
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
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(itemstack);
        CapabilityStamina.Process stamina = new CapabilityStamina.Process(playerIn);
        if(stamina.getStamina()>=weapon.getStamina() && weapon.getLoss()<weapon.getDurability() && new CapabilityPlayer.Process(playerIn).isAttackable())
        {
            if (controllerSkill.getAnimationState() != AnimationState.Running)
            {
                controllerSkill.setAnimation(new AnimationBuilder().addAnimation("animation.hammer.skill_start", false));
                controllerSkill.markNeedsReload();
            }
            playerIn.setActiveHand(handIn);
            weapon.setRightClick(true);
            playerIn.motionY = melee.smashingHammer.baseSkillFlySpeed * weapon.getLevel()/20;
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    @ParametersAreNonnullByDefault
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 0;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if(entityLiving instanceof EntityPlayer)
        {
            CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);
            CapabilityStamina.Process stamina = new CapabilityStamina.Process((EntityPlayer) entityLiving);
            AoeRangeUtil skill = new AoeRangeUtil(entityLiving, (float) weapon.getLevel() / 2 + 2);
            for (EntityLivingBase livingBase : worldIn.getEntitiesWithinAABB(EntityLivingBase.class, skill))
            {
                if (livingBase != null && livingBase != entityLiving)
                {
                    livingBase.attackEntityFrom(DamageSource.ANVIL, weapon.getDamage() * entityLiving.fallDistance / 2);
                    new CapabilityState.Process(livingBase).addFunctionOnlyTick(EnumState.VERTIGO, (weapon.getLevel() / 5) * 20 + 20);
                }
            }

            if (controllerSkill.getAnimationState() == AnimationState.Running)
            {
                controllerSkill.setAnimation(new AnimationBuilder().addAnimation("animation.hammer.skill_end", false));
                controllerSkill.markNeedsReload();
            }

            weapon.setRightClick(false);

            stamina.reduceStamina(weapon.getStamina()*2);

            if (weapon.getLossReduce() > 0)
                weapon.addLoss((int) (weapon.getRank() * weapon.getLossReduce()*2));
            else
                weapon.addLoss(weapon.getRank()*2);
        }
    }

    @Override
    public void registerControllers(AnimationData animationData)
    {
        animationData.addAnimationController(controllerAttack);
        animationData.addAnimationController(controllerSkill);
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }
    public AnimationFactory factory = new AnimationFactory(this);
    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event)
    {
        return PlayState.CONTINUE;
    }
    AnimationController<SmashingHammer> controllerAttack = new AnimationController<>(this, "attack", 1, this::predicate);
    AnimationController<SmashingHammer> controllerSkill = new AnimationController<>(this, "skill", 1, this::predicate);

    @Override
    @ParametersAreNonnullByDefault
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {return true;}

    @Override
    @ParametersAreNonnullByDefault
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {return false;}

    public boolean isRightClick()
    {
        return rightClick;
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureItems(getRegistryName().getResourcePath());
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo(getRegistryName().getResourcePath());
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation(getRegistryName().getResourcePath());
    }

    public static void onDrops(LivingHurtEvent event)
    {
        EntityLivingBase victim = event.getEntityLiving();
        DamageSource source = event.getSource();
        if (source == DamageSource.FALL && victim != null)
        {
            ItemStack mainHand = victim.getHeldItemMainhand();
            ItemStack offHand = victim.getHeldItemOffhand();
            if(mainHand.getItem() == ItemRegistry.SMASHING_HAMMER || offHand.getItem() == ItemRegistry.SMASHING_HAMMER)
            {
                event.setCanceled(true);
            }
        }
    }
}
