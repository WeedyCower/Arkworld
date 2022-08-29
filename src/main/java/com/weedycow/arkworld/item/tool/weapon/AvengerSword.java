package com.weedycow.arkworld.item.tool.weapon;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityPlayer;
import com.weedycow.arkworld.capability.CapabilityStamina;
import com.weedycow.arkworld.capability.CapabilityWeapon;
import com.weedycow.arkworld.capability.EnumEntry;
import com.weedycow.arkworld.entity.enemy.union.normal.Avenger;
import com.weedycow.arkworld.item.tool.EnumWeaponRank;
import com.weedycow.arkworld.item.tool.MeleeWeapon;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.arkworld.util.ParticleList;
import com.weedycow.weedylib.entity.WLMAttributes;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class AvengerSword extends MeleeWeapon
{
    boolean red;

    public AvengerSword()
    {
        super(Avenger.ID,EnumWeaponRank.HIGH);
        this.setUnlocalizedName(Arkworld.MODID + ".avengerSword");
        this.setRegistryName("avenger_sword");
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack,worldIn,entityIn,itemSlot,isSelected);

        if(entityIn instanceof EntityPlayer && !entityIn.world.isRemote)
        {
            red = ((EntityPlayer) entityIn).getHealth() / ((EntityPlayer) entityIn).getMaxHealth() <= 0.5 && ((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof AvengerSword;

            CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);

            CapabilityPlayer.Process player  = new CapabilityPlayer.Process((EntityPlayer) entityIn);

            CapabilityStamina.Process stamina = new CapabilityStamina.Process((EntityPlayer) entityIn);

            onStamina(weapon, melee.avengerSword.stamina);

            onDamage(weapon, melee.avengerSword.initAttackDamage,melee.avengerSword.surplusAttackDamage);

            onDurability(weapon,melee.avengerSword.durability);

            if(red) ParticleList.skullshattererParticle((EntityPlayer) entityIn);

            if(((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND)==stack)
            {
                float per = 1-(weapon.getAttackSpeed()+item.getRank(EnumEntry.ATTACK_SPEED)/10f+0.5f*((float) weapon.getLevel()/weapon.getMaxLevel()));
                
                if(per<0.25)per=0.25f;
                if(player.isSwing() && player.isAttackable() && stamina.getStamina()>=weapon.getStamina() && weapon.getLoss()<weapon.getDurability())weapon.setAnimationTick((int) (16*per));

                if(weapon.getEntityHit() instanceof EntityLivingBase && weapon.getAnimationTick()==(int)(8*per))
                {
                    EntityLivingBase entity = (EntityLivingBase) weapon.getEntityHit();

                    if (isRed())
                        entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) entityIn), (float) (weapon.getDamage() * 2 * ((EntityPlayer)entityIn).getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).getAttributeValue()));
                    else
                        entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) entityIn), (float) (weapon.getDamage() * ((EntityPlayer)entityIn).getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).getAttributeValue()));

                    onHitFun(stack, entity, (EntityLivingBase) entityIn, false);

                    if (entityIn.world instanceof WorldServer)
                    {
                        double d0 = -MathHelper.sin(entityIn.rotationYaw * 0.017453292F);
                        double d1 = MathHelper.cos(entityIn.rotationYaw * 0.017453292F);
                        ((WorldServer) entityIn.world).spawnParticle(EnumParticleTypes.SWEEP_ATTACK, entityIn.posX + d0, entityIn.posY + (double) entityIn.height * 0.5D, entityIn.posZ + d1, 0, d0, 0.0D, d1, 0.0D);
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
        String durability = I18n.format("item.arkworld.info.durability") + " : " + weapon.getLoss() + "/" + weapon.getDurability();
        String add = I18n.format("item.arkworld.info.avenger");

        list.add(damage);
        list.add(stamina);
        list.add(durability);
        list.add(add.substring(0,9));
        list.add(add.substring(9));
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
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.avenger_sword.attack", false));
                controllerAttack.markNeedsReload();
                player.setTimes(2);
            }
        }
        return true;
    }

    public boolean isRed() {return red;}

    @Override
    public void registerControllers(AnimationData animationData) {animationData.addAnimationController(controllerAttack);}

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {return PlayState.CONTINUE;}
    AnimationController<AvengerSword> controllerAttack = new AnimationController<>(this, "attack", 1, this::predicate);

    @Override
    public ResourceLocation getTextureLocation()
    {
        if(isRed())
            return ArkResUtil.textureItems("hateful_avenger_sword");
        else
            return ArkResUtil.textureItems("avenger_sword");
    }
}
