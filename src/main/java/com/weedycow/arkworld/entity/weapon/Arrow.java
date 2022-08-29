package com.weedycow.arkworld.entity.weapon;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityValue;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.item.tool.MeleeWeapon;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.WLA;
import com.weedycow.weedylib.entity.WLDamageSource;
import com.weedycow.weedylib.util.AoeRangeUtil;
import com.weedycow.weedylib.util.EntityUtil;
import com.weedycow.weedylib.util.MathUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class Arrow extends ProjectileA
{
    public int tickPick;
    public float rangePick;
    public NBTTagCompound tag;
    public static final String ID = "arrow";
    public static final String NAME = Arkworld.MODID + ".arrow";
    public static Type[] normalCrossbow = {Type.NORMAL_ARROW,Type.SIGN_ARROW};

    public Arrow(World worldIn)
    {
        super(worldIn);
    }

    public Arrow(World worldIn, double x, double y, double z, float damage, Type type)
    {
        super(worldIn,ID,x,y,z);
        setTypeString(type.toString());
        setDamage(damage);
    }

    public Arrow(World worldIn, EntityLivingBase shooter, float damage, Type type)
    {
        super(worldIn,ID,shooter);
        setTypeString(type.toString());
        setDamage(damage);
    }

    public void addParticle(EnumParticleTypes type)
    {
        if(ArkConfig.common.particle && world.isRemote && EntityUtil.atSetIntervals(this,2,0,0))
            world.spawnParticle(type,posX + MathUtil.getRandomDouble()/2,posY + MathUtil.getRandomDouble()/2, posZ + MathUtil.getRandomDouble()/2,0,0,0);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (getTypeString().equals(Type.FAUST_SPECIAL_ARROW.toString()))
            addParticle(EnumParticleTypes.SPELL_WITCH);
        if (getTypeString().equals(Type.FAUST_COMMON_ARROW.toString()))
            addParticle(EnumParticleTypes.CLOUD);

        if(Objects.equals(getTypeString(), Type.PATRIOT_JAVELIN.toString()) && pickupStatus==PickupStatus.ALLOWED && !this.isDead && !world.isRemote)
        {
            AoeRangeUtil rangeUtil = new AoeRangeUtil(this, 1+rangePick);
            for (EntityPlayer player : world.getEntitiesWithinAABB(EntityPlayer.class, rangeUtil))
            {
                if((player==shootingEntity && ticksInGround>=10+tickPick) || (player.getDistance(this)<=1 && inGround))
                {
                    ItemStack stack = this.getArrowStack();
                    stack.getCapability(CapabilityRegistry.capWeapon, null).deserializeNBT(tag);
                    stack.getCapability(CapabilityRegistry.capWeapon, null).setRightClick(false);
                    AnimationController<?> attack = GeckoLibUtil.getControllerForStack(((MeleeWeapon) stack.getItem()).getFactory(), stack, "attack");
                    attack.setAnimation(new AnimationBuilder().addAnimation("animation.patriot_javelin.attack", false));
                    player.inventory.add(-1, stack);
                    this.setDead();
                }
            }
        }
    }

    @ParametersAreNonnullByDefault
    public void onCollideWithPlayer(EntityPlayer entityIn)
    {
        if (!this.world.isRemote && this.inGround && this.arrowShake <= 0 && !Objects.equals(getTypeString(), Type.PATRIOT_JAVELIN.toString()))
        {
            boolean flag = this.pickupStatus == WLA.PickupStatus.ALLOWED || this.pickupStatus == WLA.PickupStatus.CREATIVE_ONLY && entityIn.capabilities.isCreativeMode;
            if (this.pickupStatus == WLA.PickupStatus.ALLOWED && !entityIn.inventory.addItemStackToInventory(this.getArrowStack()))
            {
                flag = false;
            }

            if (flag)
            {
                entityIn.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        if(Objects.equals(getTypeString(), Type.PATRIOT_JAVELIN.toString()) && pickupStatus==PickupStatus.ALLOWED)
        {
            compound.setInteger("tick_pick", tickPick);
            compound.setFloat("range_pick", rangePick);
            compound.setTag("patriot", tag);
            if(shootingEntity instanceof EntityPlayer)
                compound.setString("master",shootingEntity.getName());
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        if(Objects.equals(getTypeString(), Type.PATRIOT_JAVELIN.toString()) && pickupStatus==PickupStatus.ALLOWED)
        {
            if (compound.hasKey("tick_pick"))
                tickPick=compound.getInteger("tick_pick");
            if (compound.hasKey("range_pick"))
                rangePick=compound.getFloat("range_pick");
            if (compound.hasKey("patriot") && compound.getTag("patriot") instanceof NBTTagCompound)
                tag=(NBTTagCompound) compound.getTag("patriot");
            if(compound.hasKey("master"))
                shootingEntity=world.getPlayerEntityByName("master");
        }
    }

    public enum Type
    {
        NORMAL_ARROW,
        FAUST_COMMON_ARROW,
        FAUST_SPECIAL_ARROW,
        PATRIOT_JAVELIN,
        BONE_SPUR,
        SIGN_ARROW;

        @Override
        public String toString()
        {
            return name().toLowerCase();
        }
    }

    @Override
    protected ItemStack getArrowStack()
    {
        if(Objects.equals(getTypeString(), Type.FAUST_COMMON_ARROW.toString()))
            return new ItemStack(ItemRegistry.FAUST_COMMON_ARROW);
        if(Objects.equals(getTypeString(), Type.FAUST_SPECIAL_ARROW.toString()))
            return new ItemStack(ItemRegistry.FAUST_SPECIAL_ARROW);
        if(Objects.equals(getTypeString(), Type.PATRIOT_JAVELIN.toString()))
            return new ItemStack(ItemRegistry.PATRIOT_JAVELIN);
        if(Objects.equals(getTypeString(), Type.NORMAL_ARROW.toString()))
            return new ItemStack(ItemRegistry.NORMAL_ARROW);
        if(Objects.equals(getTypeString(), Type.SIGN_ARROW.toString()))
            return new ItemStack(ItemRegistry.SIGN_ARROW);
        return ItemStack.EMPTY;
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn)
    {
        Entity entity = raytraceResultIn.entityHit;
        float f1;
        if (entity != null)
        {
            DamageSource damagesource;

            if (this.shootingEntity == null)
            {
                damagesource = WLDamageSource.causeArrowDamage(this, this);
            }
            else
            {
                damagesource = WLDamageSource.causeArrowDamage(this, this.shootingEntity);
            }

            if (this.isBurning() && !(entity instanceof EntityEnderman))
            {
                entity.setFire(5);
            }

            if(shootingEntity instanceof EntityPlayer && getTypeString().equals(Type.SIGN_ARROW.toString()))
            {
                CapabilityValue.Process v = new CapabilityValue.Process((EntityPlayer) shootingEntity);
                v.setSign(entity.getEntityId());
            }

            if((shootingEntity instanceof EntityPlayer || (shootingEntity instanceof ArkMob&& (!(entity instanceof ArkMob) || ((ArkMob) entity).getCamp() != ((ArkMob) shootingEntity).getCamp()))) && entity!=shootingEntity)
            {
                if (entity.attackEntityFrom(damagesource, this.getDamage()))
                {
                    if (entity instanceof EntityLivingBase)
                    {
                        EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
                        if (!this.world.isRemote)
                        {
                            entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
                        }

                        if (this.knockbackStrength > 0)
                        {
                            f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                            if (f1 > 0.0F)
                            {
                                entitylivingbase.addVelocity(this.motionX * (double) this.knockbackStrength * 0.6000000238418579D / (double) f1, 0.1D, this.motionZ * (double) this.knockbackStrength * 0.6000000238418579D / (double) f1);
                            }
                        }

                        this.arrowHit(entitylivingbase);

                        if (this.shootingEntity != null  && entitylivingbase instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
                        {
                            ((EntityPlayerMP) this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                        }
                    }

                    this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

                    if(getTypeString().equals(Type.PATRIOT_JAVELIN.toString()))
                        onDrop(raytraceResultIn);
                    else this.setDead();
                }
                else
                {
                    this.motionX *= -0.10000000149011612D;
                    this.motionY *= -0.10000000149011612D;
                    this.motionZ *= -0.10000000149011612D;
                    this.rotationYaw += 180.0F;
                    this.prevRotationYaw += 180.0F;
                    this.ticksInAir = 0;
                    if (!this.world.isRemote && this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ < 0.0010000000474974513D)
                    {
                        if (this.pickupStatus == WLA.PickupStatus.ALLOWED)
                        {
                            this.entityDropItem(this.getArrowStack(), 0.1F);
                        }

                        if(getTypeString().equals(Type.PATRIOT_JAVELIN.toString()))
                            onDrop(raytraceResultIn);
                        else this.setDead();
                    }
                }
            }
        }
        else
        {
            onDrop(raytraceResultIn);
        }
    }

    public void onDrop(RayTraceResult raytraceResultIn)
    {
        if(raytraceResultIn.typeOfHit== RayTraceResult.Type.BLOCK)
        {
            BlockPos blockpos = raytraceResultIn.getBlockPos();
            this.xTile = blockpos.getX();
            this.yTile = blockpos.getY();
            this.zTile = blockpos.getZ();
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            this.inTile = iblockstate.getBlock();
            this.inData = this.inTile.getMetaFromState(iblockstate);
            this.motionX = (float) (raytraceResultIn.hitVec.x - this.posX);
            this.motionY = (float) (raytraceResultIn.hitVec.y - this.posY);
            this.motionZ = (float) (raytraceResultIn.hitVec.z - this.posZ);
            float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            this.posX -= this.motionX / (double) f1 * 0.05000000074505806D;
            this.posY -= this.motionY / (double) f1 * 0.05000000074505806D;
            this.posZ -= this.motionZ / (double) f1 * 0.05000000074505806D;
            this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            this.inGround = true;
            this.arrowShake = 7;

            if (iblockstate.getMaterial() != Material.AIR)
            {
                this.inTile.onEntityCollidedWithBlock(this.world, blockpos, iblockstate, this);
            }
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return source == WLDamageSource.WEAPON_CLEAR;
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        if(getTypeString().equals(Arrow.Type.FAUST_COMMON_ARROW.toString()) || getTypeString().equals(Arrow.Type.FAUST_SPECIAL_ARROW.toString()))
            return ArkResUtil.geo("faust_arrow");

        if(getTypeString().equals(Type.PATRIOT_JAVELIN.toString()))
            return ArkResUtil.geo("patriot_javelin_projectile");

        return ArkResUtil.geo(getTypeString());
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        if(getTypeString().equals(Type.PATRIOT_JAVELIN.toString()))
            return ArkResUtil.textureItems(getTypeString());
        return ArkResUtil.textureEntities(getTypeString());
    }
}
