package com.weedycow.arkworld.item.tool;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.capability.CapabilityItem;
import com.weedycow.arkworld.capability.CapabilityPlayer;
import com.weedycow.arkworld.capability.CapabilityWeapon;
import com.weedycow.arkworld.capability.EnumEntry;
import com.weedycow.arkworld.gui.MyInfoContainer;
import com.weedycow.arkworld.item.normal.ArmsMod;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.IRES;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.UUID;

public abstract class RangeWeapon extends ItemBow implements IAnimatable, IRES
{
    public String id;
    protected EnumWeaponRank rank;
    protected float shootVelocity;
    public static ArkConfig.Item.Weapon.Range range = ArkConfig.item.weapon.range;
    protected ItemStack mod;
    protected CapabilityItem.Process item;
    
    public RangeWeapon(String id, EnumWeaponRank rank)
    {
        this.setMaxStackSize(1);
        this.id = id;
        this.rank = rank;
        this.setCreativeTab(ArkCreateTab.WEAPON);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack,worldIn,entityIn,itemSlot,isSelected);

        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);

        weapon.setMaterialExperience((weapon.getRank()+1)*50+weapon.getLevel()*2);

        if(entityIn instanceof EntityPlayer)
        {
            mod = new MyInfoContainer(((EntityPlayer) entityIn).inventory, (EntityPlayer) entityIn).getSlot(41).getStack();

            item = new CapabilityItem.Process(mod);

            if (!worldIn.isRemote)
            {
                if (weapon.getCountdown() > 0)
                    weapon.setCountdown(weapon.getCountdown() - 1);

                weapon.setExpNeed((weapon.getLevel() + 1) * weapon.getRank() * 100);

                weapon.setMaxLevel(weapon.getRank() * 10);

                weapon.setRank(rank.rank);

                if (weapon.getExperience() >= weapon.getExpNeed() && weapon.getLevel() < weapon.getMaxLevel())
                {
                    weapon.addLevel(1);
                    weapon.setExperience(0);
                }

                if(item.getRank(EnumEntry.SHOOT)>0)
                {
                    if (weapon.getShootVelocity() > 0)
                        shootVelocity = ((0.5f + (float) weapon.getLevel() / 10) * weapon.getShootVelocity() * (1+item.getRank(EnumEntry.SHOOT)/5f));
                    else
                        shootVelocity = 0.5f + (float) weapon.getLevel() / 10 * (1+item.getRank(EnumEntry.SHOOT)/5f);
                }
                else
                {
                    if (weapon.getShootVelocity() > 0)
                        shootVelocity = ((0.5f + (float) weapon.getLevel() / 10) * weapon.getShootVelocity());
                    else
                        shootVelocity = 0.5f + (float) weapon.getLevel() / 10;
                }
                
                if (weapon.getUuid() == null)
                    weapon.setUuid(UUID.randomUUID());

                if(weapon.getAnimationTick()>0)
                    weapon.setAnimationTick(weapon.getAnimationTick()-1);
            }
            else
            {
                AnimationController<?> attack = GeckoLibUtil.getControllerForStack(((RangeWeapon) stack.getItem()).getFactory(), stack, "attack");
                AnimationController<?> skill = GeckoLibUtil.getControllerForStack(((RangeWeapon) stack.getItem()).getFactory(), stack, "skill");

                float per;

                if(weapon.getRank()>0)
                    per = 1+(weapon.getAttackSpeed()+item.getRank(EnumEntry.ATTACK_SPEED)/10f+0.5f*((float) weapon.getLevel()/weapon.getMaxLevel()));
                else
                    per = 1+(weapon.getAttackSpeed()+item.getRank(EnumEntry.ATTACK_SPEED)/10f);

                if(per>1.75) per=1.75f;

                attack.setAnimationSpeed(per);

                if (skill != null)
                    skill.setAnimationSpeed(per);

                if (((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND) == stack)
                {
                    CapabilityPlayer.Process p = new CapabilityPlayer.Process((EntityPlayer) entityIn);
                    p.setAttackable(attack.getAnimationState() != AnimationState.Running);
                    if (skill != null)
                        p.setAttackable(attack.getAnimationState() != AnimationState.Running || skill.getAnimationState() != AnimationState.Running);
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

        int rank = weapon.getRank();

        String q = "";
        if(rank==0)
            q= I18n.format("item.arkworld.info.low");
        if(rank==1)
            q = I18n.format("item.arkworld.info.normal");
        if(rank==2)
            q = I18n.format("item.arkworld.info.high");
        if(rank==3)
            q = I18n.format("item.arkworld.info.super");
        if(rank==4)
            q = I18n.format("item.arkworld.info.best");
        if(rank==5)
            q = I18n.format("item.arkworld.info.wtf");

        String quality = I18n.format("item.arkworld.info.quality") + " : " + q;
        String level = I18n.format("item.arkworld.info.level") + " : " + weapon.getLevel() + "/" + weapon.getMaxLevel();
        String exp = I18n.format("item.arkworld.info.exp") + " : " + weapon.getExperience() + "/" + weapon.getExpNeed();
        String stamina = I18n.format("item.arkworld.info.stamina") + " : " + weapon.getStamina();
        String mana = I18n.format("item.arkworld.info.mana") + " : " + weapon.getMana();
        String damage = I18n.format("item.arkworld.info.damage") + " : " + weapon.getDamage();
        String shoot = I18n.format("item.arkworld.info.shootVelocity") + " : " + shootVelocity;
        String durability = I18n.format("item.arkworld.info.durability") + " : " + weapon.getLoss() + "/" + weapon.getDurability();

        list.add(quality);
        list.add(level);
        list.add(exp);
        list.add(damage);
        if(isMagic())
            list.add(mana);
        else
            list.add(stamina);
        list.add(shoot);
        list.add(durability);
    }

    public boolean isMagic()
    {
        return false;
    }

    public void onDamage(CapabilityWeapon.Process weapon, float base, float total)
    {
        if(mod.getItem() instanceof ArmsMod && mod.hasCapability(CapabilityRegistry.capItem, null))
        {
            if (item.getRank(EnumEntry.DAMAGE) > 0 && item.getRank(EnumEntry.RANGE) > 0)
            {
                if (weapon.getRank() > 0)
                {
                    if (weapon.getSharp() > 0)
                        weapon.setDamage((base + total * weapon.getLevel() / (weapon.getRank() * 10)) * weapon.getSharp() * (1+item.getRank(EnumEntry.DAMAGE)/10f) * (1+item.getRank(EnumEntry.RANGE)/10f));
                    else
                        weapon.setDamage(base + total * weapon.getLevel() / (weapon.getRank() * 10)  * (1+item.getRank(EnumEntry.DAMAGE)/10f) * (1+item.getRank(EnumEntry.RANGE)/10f));
                }
                else
                    weapon.setDamage(base * (1+item.getRank(EnumEntry.DAMAGE)/10f) * (1+item.getRank(EnumEntry.RANGE)/10f));
            }
            else if (item.getRank(EnumEntry.DAMAGE) > 0)
            {
                if (weapon.getRank() > 0)
                {
                    if (weapon.getSharp() > 0)
                        weapon.setDamage((base + total * weapon.getLevel() / (weapon.getRank() * 10)) * weapon.getSharp() * (1+item.getRank(EnumEntry.DAMAGE)/10f));
                    else
                        weapon.setDamage(base + total * weapon.getLevel() / (weapon.getRank() * 10) * (1+item.getRank(EnumEntry.DAMAGE)/10f));
                }
                else
                    weapon.setDamage(base * (1+item.getRank(EnumEntry.DAMAGE)/10f));
            }
            else if (item.getRank(EnumEntry.RANGE) > 0)
            {
                if (weapon.getRank() > 0)
                {
                    if (weapon.getSharp() > 0)
                        weapon.setDamage((base + total * weapon.getLevel() / (weapon.getRank() * 10)) * weapon.getSharp() * (1+item.getRank(EnumEntry.RANGE)/10f));
                    else
                        weapon.setDamage(base + total * weapon.getLevel() / (weapon.getRank() * 10) * (1+item.getRank(EnumEntry.RANGE)/10f));
                }
                else
                    weapon.setDamage(base * (1+item.getRank(EnumEntry.RANGE)/10f));
            }
        }
        else
        {
            if (weapon.getRank() > 0)
            {
                if (weapon.getSharp() > 0)
                    weapon.setDamage((base + total * weapon.getLevel() / (weapon.getRank() * 10)) * weapon.getSharp());
                else
                    weapon.setDamage(base + total * weapon.getLevel() / (weapon.getRank() * 10));
            }
            else
                weapon.setDamage(base);
        }

    }
    
    public void onStamina(CapabilityWeapon.Process weapon, int v)
    {
        if(mod.getItem() instanceof ArmsMod && mod.hasCapability(CapabilityRegistry.capItem, null) && item.getRank(EnumEntry.LESS_STAMINA) > 0)
        {
            if (weapon.getRank() > 0)
            {
                if (weapon.getStaminaReduce() > 0)
                    weapon.setStamina((int) ((float) (weapon.getRank() * v - weapon.getLevel() / 5) * weapon.getStaminaReduce() * (1-item.getRank(EnumEntry.LESS_STAMINA)/10f)));
                else
                    weapon.setStamina((int) (weapon.getRank() * v - weapon.getLevel() / 5f * (1-item.getRank(EnumEntry.LESS_STAMINA)/10f)));
            } else
                weapon.setStamina((int) (v * (1-item.getRank(EnumEntry.LESS_STAMINA)/10f)));
        }
        else
        {
            if(weapon.getRank()>0)
            {
                if (weapon.getStaminaReduce() > 0)
                    weapon.setStamina((int) ((weapon.getRank() * v - weapon.getLevel() / 5f) * weapon.getStaminaReduce()));
                else
                    weapon.setStamina((int) (weapon.getRank() * v - weapon.getLevel() / 5f));
            }
            else
                weapon.setStamina(v);
        }
    }

    public void onMana(CapabilityWeapon.Process weapon, int v)
    {
        if(mod.getItem() instanceof ArmsMod && mod.hasCapability(CapabilityRegistry.capItem, null) && item.getRank(EnumEntry.LESS_MANA) > 0)
        {
            if(weapon.getRank()>0)
            {
                if (weapon.getManaReduce() > 0)
                    weapon.setMana((int) ((weapon.getRank() * v - weapon.getLevel() / 5f) * weapon.getManaReduce() * (1f-item.getRank(EnumEntry.LESS_MANA)/10f)));
                else
                    weapon.setMana((int) (weapon.getRank() * v - weapon.getLevel() / 5f * (1f-item.getRank(EnumEntry.LESS_MANA)/10f)));
            }
            else
                weapon.setMana((int) (v * (1-item.getRank(EnumEntry.LESS_MANA)/10f)));
        }
        else
        {
            if(weapon.getRank()>0)
            {
                if (weapon.getManaReduce() > 0)
                    weapon.setMana((int) ((float) (weapon.getRank() * v - weapon.getLevel() / 5) * weapon.getManaReduce()));
                else
                    weapon.setMana(weapon.getRank() * v - weapon.getLevel() / 5);
            }
            else
                weapon.setMana(v);
        }
    }

    public void onSam(CapabilityWeapon.Process weapon, int v)
    {
        if(mod.getItem() instanceof ArmsMod && mod.hasCapability(CapabilityRegistry.capItem, null) && item.getRank(EnumEntry.LESS_SAM) > 0)
        {
            if(weapon.getRank()>0)
            {
                if (weapon.getSamReduce() > 0)
                    weapon.setSam((int) ((float) (weapon.getRank() * v - weapon.getLevel() / 5) * weapon.getSamReduce() * (1f-item.getRank(EnumEntry.LESS_SAM)/10f)));
                else
                    weapon.setSam((int) (weapon.getRank() * v - weapon.getLevel() / 5f * (1f-item.getRank(EnumEntry.LESS_MANA)/10f)));
            }
            else
                weapon.setSam((int) (v * (1f-item.getRank(EnumEntry.LESS_MANA)/10f)));
        }
        else
        {
            if(weapon.getRank()>0)
            {
                if (weapon.getSamReduce() > 0)
                    weapon.setSam((int) ((float) (weapon.getRank() * v - weapon.getLevel() / 5) * weapon.getSamReduce()));
                else
                    weapon.setSam(weapon.getRank() * v - weapon.getLevel() / 5);
            }
            else
                weapon.setSam(v);
        }
    }

    public void onDurability(CapabilityWeapon.Process weapon, int d)
    {
        if (mod.getItem() instanceof ArmsMod && mod.hasCapability(CapabilityRegistry.capItem, null) && item.getEntries().contains(EnumEntry.DURABLE) && item.getRank(EnumEntry.DURABLE)>0)
        {
            if (weapon.getRank() > 0)
            {
                if (weapon.getDurable() > 0)
                    weapon.setDurability((int) (weapon.getRank() * d * (weapon.getLevel() + 1) * weapon.getDurable() * item.getRank(EnumEntry.DURABLE)));
                else
                    weapon.setDurability(weapon.getRank() * d * (weapon.getLevel() + 1) * item.getRank(EnumEntry.DURABLE));
            }
            else
                weapon.setDurability(d * item.getRank(EnumEntry.DURABLE));
        }
        else
        {
            if (weapon.getRank() > 0)
            {
                if (weapon.getDurable() > 0)
                    weapon.setDurability((int) (weapon.getRank() * d * (weapon.getLevel() + 1) * weapon.getDurable()));
                else
                    weapon.setDurability(weapon.getRank() * d * (weapon.getLevel() + 1));
            }
            else
                weapon.setDurability(d);
        }
    }

    @Override
    public AnimationFactory getFactory() {return this.factory;}
    public AnimationFactory factory = new AnimationFactory(this);

    @Override
    @ParametersAreNonnullByDefault
    @Nonnull
    public EnumAction getItemUseAction(ItemStack stack) {return EnumAction.NONE;}

    @Override
    @ParametersAreNonnullByDefault
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {return true;}

    @Override
    @ParametersAreNonnullByDefault
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {return false;}

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
    {
        return new CapabilityWeapon.Provide();
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack)
    {
        if(stack.hasCapability(CapabilityRegistry.capWeapon,null))
            stack.setTagCompound(stack.getCapability(CapabilityRegistry.capWeapon,null).serializeNBT());
        return stack.getTagCompound();
    }

    @Override
    public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        if(stack.hasCapability(CapabilityRegistry.capWeapon,null) && nbt!=null)
            stack.getCapability(CapabilityRegistry.capWeapon,null).deserializeNBT(nbt);
    }

    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity)
    {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureEntities(id);
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
}
