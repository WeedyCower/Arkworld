package com.weedycow.arkworld.item.tool.weapon;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.*;
import com.weedycow.arkworld.entity.enemy.union.boss.BlackSnake;
import com.weedycow.arkworld.entity.weapon.FireBall;
import com.weedycow.arkworld.item.tool.EnumWeaponRank;
import com.weedycow.arkworld.item.tool.MeleeWeapon;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.util.MeleeRangeUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
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

public class TalulahSword extends MeleeWeapon
{
    public TalulahSword()
    {
        super(BlackSnake.ID,EnumWeaponRank.SUPER);
        this.setUnlocalizedName(Arkworld.MODID + ".talulahSword");
        this.setRegistryName("talulah_sword");
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

            CapabilityMana.Process mana = new CapabilityMana.Process((EntityPlayer) entityIn);

            onStamina(weapon,melee.talulahSword.stamina);

            onMana(weapon,melee.talulahSword.mana);

            onDamage(weapon,melee.talulahSword.initAttackDamage,melee.talulahSword.surplusAttackDamage);

            onDurability(weapon,melee.talulahSword.durability);

            if(((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND)==stack)
            {
                float per = 1-(weapon.getAttackSpeed()+item.getRank(EnumEntry.ATTACK_SPEED)/10f+0.5f*((float) weapon.getLevel()/weapon.getMaxLevel()));
                if(per<0.25)per=0.25f;

                if(player.isSwing() && player.isAttackable() && stamina.getStamina()>=weapon.getStamina() && weapon.getLoss()<weapon.getDurability())weapon.setAnimationTick((int) (12*per));

                if(weapon.getEntityHit() instanceof EntityLivingBase && weapon.getAnimationTick()==(int)(6*per))
                {
                    onHitFun(stack, (EntityLivingBase) weapon.getEntityHit(), (EntityLivingBase) entityIn, true);
                }

                if(mana.getMana()>=weapon.getMana() && weapon.getCountdown() == (int)(8*per))
                {
                    MeleeRangeUtil skill = new MeleeRangeUtil(entityIn,weapon.getLevel()/3f);
                    for (EntityLivingBase livingBase : worldIn.getEntitiesWithinAABB(EntityLivingBase.class, skill))
                    {
                        if ((melee.talulahSword.onlyMob && livingBase instanceof EntityMob)||(!melee.talulahSword.onlyMob && livingBase != null && livingBase != entityIn))
                        {
                            FireBall fireball = new FireBall(worldIn, (EntityLivingBase) entityIn, weapon.getDamage(), melee.talulahSword.destroyTerrain, FireBall.Type.ENDLESS_FIRE);
                            fireball.setPosition(livingBase.posX, livingBase.posY + 32, livingBase.posZ);
                            fireball.shoot(0, -0.1, 0, 0.1f, 1);
                            worldIn.spawnEntity(fireball);
                            mana.reduceMana(weapon.getMana());
                        }
                    }
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
        String stamina = I18n.format("item.arkworld.info.stamina") + " : " + weapon.getStamina();
        String mana = I18n.format("item.arkworld.info.mana") + " : " + weapon.getMana();
        String durability = I18n.format("item.arkworld.info.durability") + " : " + weapon.getLoss() + "/" + weapon.getDurability();
        String add = I18n.format("item.arkworld.info.talula");
        String range = I18n.format("item.arkworld.info.attack_range");

        float r = weapon.getLevel()/3f;

        list.add(damage);
        list.add(stamina);
        list.add(mana);
        list.add(range+r);
        list.add(durability);
        list.add(add.substring(0,7));
        list.add(add.substring(7));
        list.add(I18n.format("item.arkworld.info.mod")+weapon.getBuffs());
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);
        CapabilityMana.Process mana = new CapabilityMana.Process(playerIn);

        if(mana.getMana()>=weapon.getMana() && new CapabilityPlayer.Process(playerIn).isAttackable())
        {
            controllerSkill.setAnimation(new AnimationBuilder().addAnimation("animation.talulah_sword.skill", false));
            controllerSkill.markNeedsReload();

            float per = 1-(weapon.getAttackSpeed()+item.getRank(EnumEntry.ATTACK_SPEED)/10f+0.5f*((float) weapon.getLevel()/weapon.getMaxLevel()));
             if(per<0.25)per=0.25f;
            weapon.setCountdown((int) (20*per));
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
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
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.talulah_sword.attack", false));
                controllerAttack.markNeedsReload();
                player.setTimes(2);
            }
        }
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public int getMaxItemUseDuration(ItemStack stack) {return 0;}

    @Override
    public void registerControllers(AnimationData animationData)
    {
        animationData.addAnimationController(controllerAttack);
        animationData.addAnimationController(controllerSkill);
    }

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {return PlayState.CONTINUE;}
    AnimationController<TalulahSword> controllerAttack = new AnimationController<>(this, "attack", 1, this::predicate);
    AnimationController<TalulahSword> controllerSkill = new AnimationController<>(this, "skill", 1, this::predicate);

    @Override
    @ParametersAreNonnullByDefault
    public boolean isDamaged(ItemStack stack) {return false;}

    @Override
    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureItems(getRegistryName().getResourcePath());
    }
}
