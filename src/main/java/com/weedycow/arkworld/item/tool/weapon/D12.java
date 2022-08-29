package com.weedycow.arkworld.item.tool.weapon;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.*;
import com.weedycow.arkworld.entity.enemy.union.boss.W;
import com.weedycow.arkworld.entity.weapon.Bomb;
import com.weedycow.arkworld.item.tool.EnumWeaponRank;
import com.weedycow.arkworld.item.tool.ItemBomb;
import com.weedycow.arkworld.item.tool.RangeWeapon;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.util.ArkItemUtil;
import com.weedycow.arkworld.util.ArkResUtil;
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

public class D12 extends RangeWeapon
{
    public D12()
    {
        super(W.ID, EnumWeaponRank.HIGH);
        this.setUnlocalizedName(Arkworld.MODID + ".d12");
        this.setRegistryName("d12");
    }

    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag)
    {
        super.addInformation(itemstack, world, list, flag);

        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(itemstack);

        String range = I18n.format("item.arkworld.info.attack_range");

        float r = weapon.getLevel()/2f+1;

        list.add(range+r);

        list.add(I18n.format("item.arkworld.info.d12Bomb"));

        list.add(I18n.format("item.arkworld.info.d12"));

        list.add(I18n.format("item.arkworld.info.mod")+weapon.getBuffs());
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);

        if(entityIn instanceof EntityPlayer && new CapabilityPlayer.Process((EntityPlayer) entityIn).isSwing() && ((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND)==stack && new CapabilityPlayer.Process((EntityPlayer) entityIn).isAttackable())
        {
            CapabilityStamina.Process stamina = new CapabilityStamina.Process((EntityPlayer) entityIn);

            if(stamina.getStamina() >= weapon.getStamina() && weapon.getLoss() < weapon.getDurability())
            {
                if (!worldIn.isRemote)
                {
                    ItemStack ammon = ArkItemUtil.findItemBombInPlayerSlot((EntityPlayer) entityIn, new Bomb.Type[]{Bomb.Type.INSTANT_BOMB});
                    if(ammon!=ItemStack.EMPTY)
                    {
                        Bomb bomb = new Bomb(worldIn, (EntityLivingBase) entityIn,((ItemBomb)ammon.getItem()).getDamage()+weapon.getDamage(),((ItemBomb)ammon.getItem()).isDamagesTerrain(),((ItemBomb)ammon.getItem()).getType());
                        bomb.shoot(entityIn,entityIn.rotationPitch,entityIn.rotationYaw,weapon.getShootVelocity(),1);
                        if(worldIn.spawnEntity(bomb))
                        {
                            ammon.shrink(1);

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

        if(entityIn instanceof EntityPlayer && weapon.getCountdown()==1 && ((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND)==stack)
        {
            CapabilityStamina.Process stamina = new CapabilityStamina.Process((EntityPlayer) entityIn);

            if (stamina.getStamina() >= weapon.getStamina()*2 && weapon.getLoss() < weapon.getDurability())
            {
                for (EntityLivingBase target : entityIn.world.getEntitiesWithinAABB(EntityLivingBase.class, new AoeRangeUtil(entityIn,weapon.getLevel()/2f+1)))
                {
                    if(target != entityIn && target != null && target.hasCapability(CapabilityRegistry.capState,null))
                    {
                        new CapabilityState.Process(target).addFunction(EnumState.D12,weapon.getLevel()+1,61);
                    }
                }
                if (weapon.getLossReduce() > 0)
                    weapon.addLoss((int) (weapon.getRank() * weapon.getLossReduce()*2));
                else
                    weapon.addLoss(weapon.getRank()*2);

                stamina.reduceStamina(weapon.getStamina()*2);
            }
        }

        if(entityIn instanceof EntityPlayer)
                onDamage(weapon, (float) (range.d12.initAttackDamage * ((EntityPlayer)entityIn).getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).getAttributeValue()), (float) (range.d12.surplusAttackDamage * ((EntityPlayer)entityIn).getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).getAttributeValue()));

        onStamina(weapon,range.d12.stamina);

        onDurability(weapon,range.d12.durability);
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);
        CapabilityStamina.Process stamina = new CapabilityStamina.Process(playerIn);

        if (stamina.getStamina() >= weapon.getStamina()*2 && weapon.getLoss() < weapon.getDurability() && new CapabilityPlayer.Process(playerIn).isAttackable() && playerIn.getHeldItem(EnumHand.MAIN_HAND)==stack)
        {
            if (worldIn.isRemote)
            {
                controllerShoot.setAnimation(new AnimationBuilder().addAnimation("animation.d12.skill", false));
                controllerShoot.markNeedsReload();
            }
            else
            {
                float per = 1-(weapon.getAttackSpeed()+item.getRank(EnumEntry.ATTACK_SPEED)/10f+0.5f*((float) weapon.getLevel()/weapon.getMaxLevel()));
                if(per<0.25)per=0.25f;
                weapon.setCountdown((int) (1+10*per));
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureItems(getRegistryName().getResourcePath());
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
    AnimationController<D12> controllerShoot = new AnimationController<>(this, "attack", 1, this::predicate);
}
