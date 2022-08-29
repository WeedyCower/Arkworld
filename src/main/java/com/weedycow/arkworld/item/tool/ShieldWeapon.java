package com.weedycow.arkworld.item.tool;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.capability.*;
import com.weedycow.arkworld.gui.MyInfoContainer;
import com.weedycow.arkworld.item.normal.ArmsMod;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.IRES;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.UUID;

public abstract class ShieldWeapon  extends Item implements IAnimatable, IRES
{
    public String id;
    EnumWeaponRank rank;
    public static ArkConfig.Item.Weapon.Shield shield = ArkConfig.item.weapon.shield;
    ItemStack mod;
    CapabilityItem.Process item;
    
    public ShieldWeapon(String id, EnumWeaponRank rank)
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
            if(!worldIn.isRemote)
            {
                weapon.setExpNeed((weapon.getLevel()+1)*weapon.getRank()*100);

                weapon.setMaxLevel(weapon.getRank()*10);

                weapon.setRank(rank.rank);

                if(weapon.getExperience()>=weapon.getExpNeed() && weapon.getLevel()<weapon.getMaxLevel())
                {
                    weapon.addLevel(1);
                    weapon.setExperience(0);
                }

                if(ItemStack.areItemStacksEqual(((EntityPlayer) entityIn).getActiveItemStack(),stack))
                {
                    CapabilityStamina.Process stamina = new CapabilityStamina.Process((EntityPlayer) entityIn);
                    if(weapon.getLoss() >= weapon.getDurability() || stamina.getStamina()<weapon.getStamina())
                        ((EntityPlayer) entityIn).stopActiveHand();

                    if(stamina.getStamina()>=weapon.getStamina() && EntityUtil.atSetIntervals((EntityLivingBase) entityIn,20,0,0))
                        stamina.reduceStamina(weapon.getStamina());

                    if(weapon.getShelter()>0)
                        new CapabilityState.Process((EntityLivingBase) entityIn).addFunction(EnumState.SHELTER, (int) weapon.getShelter(), (int) (21*(1+weapon.getShelter())));

                    if(weapon.getResistance()>0)
                        new CapabilityState.Process((EntityLivingBase) entityIn).addFunctionOnlyTick(EnumState.RESISTANCE, (int) (21*(1+weapon.getResistance())));
                }

                if(weapon.getUuid()==null)
                    weapon.setUuid(UUID.randomUUID());
            }

            mod = new MyInfoContainer(((EntityPlayer) entityIn).inventory, (EntityPlayer) entityIn).getSlot(41).getStack();

            item = new CapabilityItem.Process(mod);
        }
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(itemstack);
        CapabilityStamina.Process stamina = new CapabilityStamina.Process(playerIn);

        if(weapon.getLoss() < weapon.getDurability() && stamina.getStamina()>=weapon.getStamina())
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        else
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
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

        list.add(quality);
        list.add(level);
        list.add(exp);
    }

    public void onStamina(CapabilityWeapon.Process weapon, int v)
    {
        if(mod != null && mod.getItem() instanceof ArmsMod && mod.hasCapability(CapabilityRegistry.capItem, null) && item.getRank(EnumEntry.LESS_STAMINA) > 0)
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

    public void onDamage(CapabilityWeapon.Process weapon, float base, float total)
    {
        if(mod.getItem() instanceof ArmsMod && mod.hasCapability(CapabilityRegistry.capItem, null))
        {
            if (item.getRank(EnumEntry.DAMAGE) > 0 && item.getRank(EnumEntry.SHIELD) > 0)
            {
                if (weapon.getRank() > 0)
                {
                    if (weapon.getSharp() > 0)
                        weapon.setDamage((base + total * weapon.getLevel() / (weapon.getRank() * 10)) * weapon.getSharp() * (1+item.getRank(EnumEntry.DAMAGE)/10f) * (1+item.getRank(EnumEntry.SHIELD)/10f));
                    else
                        weapon.setDamage(base + total * weapon.getLevel() / (weapon.getRank() * 10)  * (1+item.getRank(EnumEntry.DAMAGE)/10f) * (1+item.getRank(EnumEntry.SHIELD)/10f));
                }
                else
                    weapon.setDamage(base * (1+item.getRank(EnumEntry.DAMAGE)/10f) * (1+item.getRank(EnumEntry.SHIELD)/10f));
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
            else if (item.getRank(EnumEntry.SHIELD) > 0)
            {
                if (weapon.getRank() > 0)
                {
                    if (weapon.getSharp() > 0)
                        weapon.setDamage((base + total * weapon.getLevel() / (weapon.getRank() * 10)) * weapon.getSharp() * (1+item.getRank(EnumEntry.SHIELD)/10f));
                    else
                        weapon.setDamage(base + total * weapon.getLevel() / (weapon.getRank() * 10) * (1+item.getRank(EnumEntry.SHIELD)/10f));
                }
                else
                    weapon.setDamage(base * (1+item.getRank(EnumEntry.SHIELD)/10f));
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

    public void onHitFun(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker, boolean normalAttack)
    {
        if(!attacker.world.isRemote && attacker instanceof EntityPlayer)
        {
            CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);
            CapabilityStamina.Process stamina = new CapabilityStamina.Process((EntityPlayer) attacker);
            CapabilityMana.Process mana = new CapabilityMana.Process((EntityPlayer) attacker);

            if (weapon.getLossReduce() > 0)
                weapon.addLoss((int) (weapon.getRank() * weapon.getLossReduce()));
            else
                weapon.addLoss(weapon.getRank());

            mana.reduceMana(weapon.getMana());

            stamina.reduceStamina(weapon.getStamina());

            if (normalAttack)
                target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), weapon.getDamage());
        }
    }

    public void onSprint(CapabilityWeapon.Process weapon, EntityLivingBase attacker)
    {
        if(weapon.getSprint()>0)
        {
            Vec3d look = attacker.getLookVec();
            attacker.setSprinting(true);
            attacker.motionX = look.x * weapon.getSprint();
            attacker.motionY = look.y * weapon.getSprint();
            attacker.motionZ = look.z * weapon.getSprint();
            if (attacker instanceof EntityPlayerMP)
            {
                attacker.velocityChanged = true;
                ((EntityPlayerMP) attacker).connection.sendPacket(new SPacketEntityVelocity(attacker));
                attacker.velocityChanged = false;
                if(attacker.hasCapability(CapabilityRegistry.capStamina,null))
                {
                    new CapabilityStamina.Process((EntityPlayer) attacker).reduceStamina(weapon.getStamina()/2);
                }
            }
        }
    }

    public static void onAttackShield(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource source = event.getSource();

        if(entity instanceof EntityPlayer && event.getAmount()>0 && canBlockDamageSource(source, (EntityPlayer) entity) && entity.getActiveItemStack().getItem() instanceof ShieldWeapon)
        {
            CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(entity.getActiveItemStack());

            if(weapon.getLoss() < weapon.getDurability())
            {
                if (weapon.getLossReduce() > 0)
                    weapon.addLoss((int) ((event.getAmount() / 2) * weapon.getLossReduce()));
                else
                    weapon.addLoss((int) (event.getAmount() / 2));
            }

            if (!source.isProjectile())
            {
                Entity target = source.getImmediateSource();

                if (target instanceof EntityLivingBase)
                {
                    blockUsingShield((EntityPlayer) entity, (EntityLivingBase) target);
                }
            }

            if(weapon.getReflex()>0 && source.getTrueSource() instanceof EntityLivingBase)
                source.getTrueSource().attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) entity),event.getAmount()*weapon.getReflex());

            event.setCanceled(true);
        }
    }

    private static boolean canBlockDamageSource(DamageSource damageSourceIn, EntityPlayer player)
    {
        if (!damageSourceIn.isUnblockable() && isActiveItemStackBlocking(player))
        {
            Vec3d vec3d = damageSourceIn.getDamageLocation();

            if (vec3d != null)
            {
                Vec3d vec3d1 = player.getLook(1.0F);
                Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(player.posX, player.posY, player.posZ)).normalize();
                vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);

                return vec3d2.dotProduct(vec3d1) < 0.0D;
            }
        }

        return false;
    }

    public static boolean isActiveItemStackBlocking(EntityPlayer player)
    {
        if (player.isHandActive() && !player.getActiveItemStack().isEmpty())
        {
            Item item = player.getActiveItemStack().getItem();

            if (item.getItemUseAction(player.getActiveItemStack()) != EnumAction.NONE)
            {
                return false;
            }
            else
            {
                return item.getMaxItemUseDuration(player.getActiveItemStack()) - player.getItemInUseCount() >= 5;
            }
        }
        else
        {
            return false;
        }
    }

    protected static void blockUsingShield(EntityPlayer player, EntityLivingBase target)
    {
        target.knockBack(player, 0.5F, player.posX - target.posX, player.posZ - target.posZ);
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

    @Override
    public AnimationFactory getFactory() {return this.factory;}
    public AnimationFactory factory = new AnimationFactory(this);

    @Override
    public void registerControllers(AnimationData animationData) {}

    @Override
    @ParametersAreNonnullByDefault
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {return false;}

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, NBTTagCompound nbt)
    {
        return new CapabilityWeapon.Provide();
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.NONE;
    }

    @Override
    @ParametersAreNonnullByDefault
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
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
