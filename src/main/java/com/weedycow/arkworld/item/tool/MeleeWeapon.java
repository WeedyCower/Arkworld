package com.weedycow.arkworld.item.tool;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.capability.*;
import com.weedycow.arkworld.gui.MyInfoContainer;
import com.weedycow.arkworld.item.normal.ArmsMod;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.IRES;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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

public abstract class MeleeWeapon extends Item implements IAnimatable, IRES
{
    ItemStack mod;
    public String id;
    protected EnumWeaponRank rank;
    protected CapabilityItem.Process item;
    public static ArkConfig.Item.Weapon.Melee melee = ArkConfig.item.weapon.melee;
    public static ArkConfig.Item.Weapon.RangeMelee rangeMelee = ArkConfig.item.weapon.rangeMelee;

    public MeleeWeapon(String id, EnumWeaponRank rank)
    {
        super();
        this.setMaxStackSize(1);
        this.id = id;
        this.rank = rank;
        this.setCreativeTab(ArkCreateTab.WEAPON);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag)
    {
        super.addInformation(itemstack, world, list, flag);

        CapabilityWeapon.Process process = new CapabilityWeapon.Process(itemstack);

        int rank = process.getRank();

        String q = "";
        if(rank==0)
            q=I18n.format("item.arkworld.info.low");
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
        String level = I18n.format("item.arkworld.info.level") + " : " + process.getLevel() + "/" + process.getMaxLevel();
        String exp = I18n.format("item.arkworld.info.exp") + " : " + process.getExperience() + "/" + process.getExpNeed();

        list.add(quality);
        list.add(level);
        list.add(exp);
    }

    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity)
    {
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack,worldIn,entityIn,itemSlot,isSelected);

        CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);

        weapon.setMaterialExperience((weapon.getRank()+1)*100+weapon.getLevel()*10);

        if(entityIn instanceof EntityPlayer)
        {
            mod = new MyInfoContainer(((EntityPlayer) entityIn).inventory, (EntityPlayer) entityIn).getSlot(41).getStack();

            item = new CapabilityItem.Process(mod);

            Vec3d vec3d1 = new Vec3d(entityIn.posX, entityIn.posY+entityIn.getEyeHeight(), entityIn.posZ);

            Vec3d vec3d = new Vec3d(entityIn.posX + entityIn.getLookVec().x*(3+weapon.getAttackRange()), entityIn.posY + entityIn.getEyeHeight() + entityIn.getLookVec().y*(3+weapon.getAttackRange()), entityIn.posZ + entityIn.getLookVec().z*(3+weapon.getAttackRange()));

            if (!worldIn.isRemote)
            {
                CapabilityPlayer.Process player = new CapabilityPlayer.Process((EntityPlayer) entityIn);

                weapon.setExpNeed((weapon.getLevel() + 1) * weapon.getRank() * 100);

                weapon.setMaxLevel(weapon.getRank() * 10);

                weapon.setRank(rank.rank);

                if (weapon.getExperience() >= weapon.getExpNeed() && weapon.getLevel() < weapon.getMaxLevel())
                {
                    weapon.addLevel(1);
                    weapon.setExperience(0);
                }

                if (weapon.getUuid() == null)
                    weapon.setUuid(UUID.randomUUID());

                if (player.isSwing() && ((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND) == stack)
                    onSprint(weapon, (EntityLivingBase) entityIn);

                if(weapon.getAnimationTick()>0)
                    weapon.setAnimationTick(weapon.getAnimationTick()-1);

                if(weapon.getCountdown()>0)
                    weapon.setCountdown(weapon.getCountdown()-1);

                weapon.setEntityHit(EntityUtil.findEntityLookingAt(vec3d1,vec3d,entityIn,(3+weapon.getAttackRange())));
            }
            else
            {
                AnimationController<?> attack = GeckoLibUtil.getControllerForStack(((MeleeWeapon) stack.getItem()).getFactory(), stack, "attack");
                AnimationController<?> skill = GeckoLibUtil.getControllerForStack(((MeleeWeapon) stack.getItem()).getFactory(), stack, "skill");

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

    public void onStamina(CapabilityWeapon.Process weapon, int v)
    {
        if(mod.getItem() instanceof ArmsMod && mod.hasCapability(CapabilityRegistry.capItem, null) && item.getRank(EnumEntry.LESS_STAMINA) > 0)
        {
            if (weapon.getRank() > 0)
            {
                if (weapon.getStaminaReduce() > 0)
                    weapon.setStamina((int) ((weapon.getRank() * v - weapon.getLevel() / 5f) * (1-weapon.getStaminaReduce()) * (1-item.getRank(EnumEntry.LESS_STAMINA)/10f)));
                else
                    weapon.setStamina((int) ((weapon.getRank() * v - weapon.getLevel() / 5f) * (1-item.getRank(EnumEntry.LESS_STAMINA)/10f)));
            }
            else
                weapon.setStamina((int) (v * (1-item.getRank(EnumEntry.LESS_STAMINA)/10f)));
        }
        else
        {
            if(weapon.getRank()>0)
            {
                if (weapon.getStaminaReduce() > 0)
                    weapon.setStamina((int) ((weapon.getRank() * v - weapon.getLevel() / 5f) * (1-weapon.getStaminaReduce())));
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
                    weapon.setMana((int) ((weapon.getRank() * v - weapon.getLevel() / 5f) * (1-weapon.getManaReduce()) * (1f-item.getRank(EnumEntry.LESS_MANA)/10f)));
                else
                    weapon.setMana((int) ((weapon.getRank() * v - weapon.getLevel() / 5f) * (1f-item.getRank(EnumEntry.LESS_MANA)/10f)));
            }
            else
                weapon.setMana((int) (v * (1-item.getRank(EnumEntry.LESS_MANA)/10f)));
        }
        else
        {
            if(weapon.getRank()>0)
            {
                if (weapon.getManaReduce() > 0)
                    weapon.setMana((int) ((float) (weapon.getRank() * v - weapon.getLevel() / 5) * (1-weapon.getManaReduce())));
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
                    weapon.setSam((int) ((float) (weapon.getRank() * v - weapon.getLevel() / 5) * (1-weapon.getSamReduce()) * (1f-item.getRank(EnumEntry.LESS_SAM)/10f)));
                else
                    weapon.setSam((int) ((weapon.getRank() * v - weapon.getLevel() / 5f) * (1f-item.getRank(EnumEntry.LESS_MANA)/10f)));
            }
            else
                weapon.setSam((int) (v * (1f-item.getRank(EnumEntry.LESS_MANA)/10f)));
        }
        else
        {
            if(weapon.getRank()>0)
            {
                if (weapon.getSamReduce() > 0)
                    weapon.setSam((int) ((float) (weapon.getRank() * v - weapon.getLevel() / 5) * (1-weapon.getSamReduce())));
                else
                    weapon.setSam(weapon.getRank() * v - weapon.getLevel() / 5);
            }
            else
                weapon.setSam(v);
        }
    }

    public void onDurability(CapabilityWeapon.Process weapon, int d)
    {
        if (mod.getItem() instanceof ArmsMod && mod.hasCapability(CapabilityRegistry.capItem, null) && item.getRank(EnumEntry.DURABLE)>0)
        {
            if (weapon.getRank() > 0)
            {
                if (weapon.getDurable() > 0)
                    weapon.setDurability((int) (weapon.getRank() * d * (weapon.getLevel() + 1) * (1+weapon.getDurable()) * item.getRank(EnumEntry.DURABLE)));
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
                    weapon.setDurability((int) (weapon.getRank() * d * (weapon.getLevel() + 1) * (1+weapon.getDurable())));
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
            if (item.getRank(EnumEntry.DAMAGE) > 0 && item.getRank(EnumEntry.MELEE) > 0)
            {
                if (weapon.getRank() > 0)
                {
                    if (weapon.getSharp() > 0)
                        weapon.setDamage((base + total * weapon.getLevel() / (weapon.getRank() * 10)) * (1+weapon.getSharp()) * (1+item.getRank(EnumEntry.DAMAGE)/10f) * (1+item.getRank(EnumEntry.MELEE)/10f));
                    else
                        weapon.setDamage((base + total * weapon.getLevel() / (weapon.getRank() * 10))  * (1+item.getRank(EnumEntry.DAMAGE)/10f) * (1+item.getRank(EnumEntry.MELEE)/10f));
                }
                else
                    weapon.setDamage(base * (1+item.getRank(EnumEntry.DAMAGE)/10f) * (1+item.getRank(EnumEntry.MELEE)/10f));
            }
            else if (item.getRank(EnumEntry.DAMAGE) > 0)
            {
                if (weapon.getRank() > 0)
                {
                    if (weapon.getSharp() > 0)
                        weapon.setDamage((base + total * weapon.getLevel() / (weapon.getRank() * 10)) * (1+weapon.getSharp()) * (1+item.getRank(EnumEntry.DAMAGE)/10f));
                    else
                        weapon.setDamage((base + total * weapon.getLevel() / (weapon.getRank() * 10)) * (1+item.getRank(EnumEntry.DAMAGE)/10f));
                }
                else
                    weapon.setDamage(base * (1+item.getRank(EnumEntry.DAMAGE)/10f));
            }
            else if (item.getRank(EnumEntry.MELEE) > 0)
            {
                if (weapon.getRank() > 0)
                {
                    if (weapon.getSharp() > 0)
                        weapon.setDamage((base + total * weapon.getLevel() / (weapon.getRank() * 10)) * (1+weapon.getSharp()) * (1+item.getRank(EnumEntry.MELEE)/10f));
                    else
                        weapon.setDamage((base + total * weapon.getLevel() / (weapon.getRank() * 10)) * (1+item.getRank(EnumEntry.MELEE)/10f));
                }
                else
                    weapon.setDamage(base * (1+item.getRank(EnumEntry.MELEE)/10f));
            }
        }
        else
        {
            if (weapon.getRank() > 0)
            {
                if (weapon.getSharp() > 0)
                    weapon.setDamage((base + total * weapon.getLevel() / (weapon.getRank() * 10)) * (1+weapon.getSharp()));
                else
                    weapon.setDamage(base + total * weapon.getLevel() / (weapon.getRank() * 10));
            }
            else
                weapon.setDamage(base);
        }
    }

    public static void onVampire(LivingDamageEvent event)
    {
        DamageSource source = event.getSource();
        Entity entity = source.getTrueSource();

        if(entity instanceof EntityPlayer)
        {
            ItemStack main = ((EntityPlayer) entity).getHeldItem(EnumHand.MAIN_HAND);

            if(main.getItem() instanceof MeleeWeapon || main.getItem() instanceof ShieldWeapon || main.getItem() instanceof RangeWeapon)
            {
                ItemStack mod = new MyInfoContainer(((EntityPlayer) source.getTrueSource()).inventory, (EntityPlayer) source.getTrueSource()).getSlot(41).getStack();
                CapabilityItem.Process item = new CapabilityItem.Process(mod);
                CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(main);

                if(item.getRank(EnumEntry.VAMPIRE)>0 && weapon.getVampire()>0)
                {
                    ((EntityPlayer) source.getTrueSource()).heal(event.getAmount() * (item.getRank(EnumEntry.VAMPIRE)/10f+weapon.getVampire()));
                }
                else if(item.getRank(EnumEntry.VAMPIRE)>0)
                {
                    ((EntityPlayer) source.getTrueSource()).heal(event.getAmount() * item.getRank(EnumEntry.VAMPIRE)/10f);
                }
                else if (weapon.getVampire() > 0)
                {
                    ((EntityPlayer) source.getTrueSource()).heal(weapon.getDamage() * weapon.getVampire());
                }
            }
        }
    }
    
    public static void onHitEntity(LivingHurtEvent event)
    {
        EntityLivingBase target = event.getEntityLiving();
        Entity attacker = event.getSource().getTrueSource();
        if(attacker instanceof EntityPlayer)
        {
            ItemStack main = ((EntityPlayer) attacker).getHeldItem(EnumHand.MAIN_HAND);

            if(main.getItem() instanceof MeleeWeapon || main.getItem() instanceof ShieldWeapon || main.getItem() instanceof RangeWeapon)
            {
                ItemStack mod = new MyInfoContainer(((EntityPlayer) attacker).inventory, ((EntityPlayer) attacker)).getSlot(41).getStack();
                CapabilityItem.Process item = new CapabilityItem.Process(mod);
                CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(((EntityPlayer) attacker).getHeldItem(EnumHand.MAIN_HAND));
                CapabilityState.Process targetState = new CapabilityState.Process(target);
                CapabilityState.Process attackerState = new CapabilityState.Process((EntityLivingBase) attacker);

                if (mod.getItem() instanceof ArmsMod && mod.hasCapability(CapabilityRegistry.capItem, null))
                {
                    if(item.getRank(EnumEntry.CRITICAL)>0)
                    {
                        if (Math.random() < weapon.getCritical() + item.getRank(EnumEntry.CRITICAL)/10f)
                            event.setAmount(event.getAmount()*3);
                    }
                    else
                    {
                        if (Math.random() < weapon.getCritical())
                            event.setAmount(event.getAmount()*3);
                    }

                    if (weapon.getFlameSeconds() > 0)
                        target.setFire(weapon.getFlameSeconds());

                    if(item.getRank(EnumEntry.KNOCKBACK)>0)
                    {
                        if (weapon.getKnockbackLevel()+item.getRank(EnumEntry.KNOCKBACK) > 0 && (weapon.getKnockbackLevel()+item.getRank(EnumEntry.KNOCKBACK))*(CapabilityState.Process.getLevel(attackerState.state,EnumState.PUSH)>0 ? 1.5 :1) >= target.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue())
                        {
                            target.knockBack(attacker, (float) ((weapon.getKnockbackLevel() +item.getRank(EnumEntry.KNOCKBACK))*(CapabilityState.Process.getLevel(attackerState.state,EnumState.PUSH)>0 ? 1.5 :1) - target.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue()), MathHelper.sin(attacker.rotationYaw * 0.017453292F), -MathHelper.cos(attacker.rotationYaw * 0.017453292F));
                        }
                    }
                    else
                    {
                        if (weapon.getKnockbackLevel() > 0 && weapon.getKnockbackLevel()*(CapabilityState.Process.getLevel(attackerState.state,EnumState.PUSH)>0 ? 1.5 :1) >= target.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue())
                        {
                            target.knockBack(attacker, (float) (weapon.getKnockbackLevel()*(CapabilityState.Process.getLevel(attackerState.state,EnumState.PUSH)>0 ? 1.5 :1) - target.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue()), MathHelper.sin(attacker.rotationYaw * 0.017453292F), -MathHelper.cos(attacker.rotationYaw * 0.017453292F));
                        }
                    }

                    if (Math.random() < weapon.getDefenceReduce() + item.getRank(EnumEntry.REDUCE_DEFENCE)/10f && target.hasCapability(CapabilityRegistry.capState, null))
                        targetState.addFunctionOnlyTick(EnumState.REDUCE_DEFENSE, (int) ((1 + weapon.getDefenceReduce() + item.getRank(EnumEntry.REDUCE_DEFENCE)/10) * 200));

                    if (Math.random() < weapon.getShackles() && target.hasCapability(CapabilityRegistry.capState, null))
                        targetState.addFunctionOnlyTick(EnumState.SHACKLES, (int) ((1+weapon.getShackles()) * 200));

                    if (Math.random() < weapon.getSilence() + item.getRank(EnumEntry.SILENCE)/10f && target.hasCapability(CapabilityRegistry.capState, null))
                        targetState.addFunctionOnlyTick(EnumState.SILENCE, (int) ((1+weapon.getSilence() + item.getRank(EnumEntry.SILENCE)/10f) * 200));

                    if (Math.random() < item.getRank(EnumEntry.COLD)/10f)
                        targetState.addFunctionOnlyTick(EnumState.COLD, (int) (item.getRank(EnumEntry.COLD)* 20));

                    if (Math.random() < item.getRank(EnumEntry.VERTIGO)/10f)
                        targetState.addFunctionOnlyTick(EnumState.VERTIGO, (int) (item.getRank(EnumEntry.VERTIGO)* 20));

                    if (Math.random() < item.getRank(EnumEntry.BURNING_BREATH)/10f)
                        targetState.addFunctionOnlyTick(EnumState.BURNING_BREATH, (int) (item.getRank(EnumEntry.BURNING_BREATH)* 20));

                    if (Math.random() < item.getRank(EnumEntry.FRAGILE)/10f)
                        targetState.addFunctionOnlyTick(EnumState.FRAGILE, (int) (item.getRank(EnumEntry.FRAGILE)* 20));

                    if (Math.random() < item.getRank(EnumEntry.SHACKLES)/10f && ((EntityPlayer)attacker).getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof MeleeWeapon)
                        targetState.addFunctionOnlyTick(EnumState.SHACKLES, (int) (item.getRank(EnumEntry.SHACKLES)* 20));

                    if (Math.random() < item.getRank(EnumEntry.PAUSE)/10f && ((EntityPlayer)attacker).getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof RangeWeapon)
                        targetState.addFunctionOnlyTick(EnumState.PAUSE, (int) (item.getRank(EnumEntry.PAUSE)* 20));

                    if (Math.random() < item.getRank(EnumEntry.BANEAL)/10f)
                        targetState.addFunctionOnlyTick(EnumState.BANEAL, (int) (item.getRank(EnumEntry.BANEAL)* 20));

                    if (Math.random() < item.getRank(EnumEntry.D12)/10f)
                        targetState.addFunctionOnlyTick(EnumState.D12, 61);
                }
                else
                {
                    if (Math.random() < weapon.getCritical())
                        event.setAmount(event.getAmount()*3);

                    if (weapon.getFlameSeconds() > 0)
                        target.setFire(weapon.getFlameSeconds());

                    if (weapon.getKnockbackLevel() > 0 && weapon.getKnockbackLevel() >= target.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue())
                    {
                        target.motionX = attacker.getLookVec().x * (weapon.getKnockbackLevel() - target.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue());
                        target.motionZ = attacker.getLookVec().z * (weapon.getKnockbackLevel() - target.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue());
                    }

                    if (Math.random() < weapon.getDefenceReduce() && target.hasCapability(CapabilityRegistry.capState, null))
                        targetState.addFunctionOnlyTick(EnumState.REDUCE_DEFENSE, (int) (weapon.getDefenceReduce() * 100));

                    if (Math.random() < weapon.getShackles() && target.hasCapability(CapabilityRegistry.capState, null))
                        targetState.addFunctionOnlyTick(EnumState.SHACKLES, (int) (weapon.getShackles() * 100));

                    if (Math.random() < weapon.getSilence() && target.hasCapability(CapabilityRegistry.capState, null))
                        targetState.addFunctionOnlyTick(EnumState.SILENCE, (int) (weapon.getSilence() * 100));
                }
            }
        }
    }
    
    public void onHitFun(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker, boolean normalAttack)
    {
        if(!attacker.world.isRemote && attacker instanceof EntityPlayer)
        {
            CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(stack);
            CapabilityStamina.Process stamina = new CapabilityStamina.Process((EntityPlayer) attacker);
            CapabilityMana.Process mana = new CapabilityMana.Process((EntityPlayer) attacker);
            
            if (weapon.getLossReduce() > 0 )
                weapon.addLoss((int) (weapon.getRank() * (1-weapon.getLossReduce())));
            else
                weapon.addLoss(weapon.getRank());

            mana.reduceMana(weapon.getMana());

            stamina.reduceStamina(weapon.getStamina());

            if (normalAttack)
                target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), (float) (weapon.getDamage() * attacker.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).getAttributeValue()));
        }
    }

    public void onSprint(CapabilityWeapon.Process weapon, EntityLivingBase attacker)
    {
        if(weapon.getSprint()>0 && !attacker.world.isRemote)
        {
            Vec3d look = attacker.getLookVec();
            attacker.setSprinting(true);
            attacker.motionX = look.x * (1+weapon.getSprint());
            attacker.motionY = look.y * (1+weapon.getSprint());
            attacker.motionZ = look.z * (1+weapon.getSprint());
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

    @Override
    public AnimationFactory getFactory() {return this.factory;}
    public AnimationFactory factory = new AnimationFactory(this);

    @Override
    @ParametersAreNonnullByDefault
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {return true;}

    @Override
    @ParametersAreNonnullByDefault
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {return false;}

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, NBTTagCompound nbt)
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

    @Override
    @ParametersAreNonnullByDefault
    public boolean isDamaged(ItemStack stack)
    {
        return false;
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