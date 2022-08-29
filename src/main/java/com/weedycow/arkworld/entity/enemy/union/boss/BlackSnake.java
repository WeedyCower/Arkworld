package com.weedycow.arkworld.entity.enemy.union.boss;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.MovingSound;
import com.weedycow.arkworld.ai.ArkEntityAiBlackSnakeMeteorite;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.entity.device.EnergyPolymer;
import com.weedycow.arkworld.entity.enemy.ArkBoss;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.arkworld.util.ArkworldUtil;
import com.weedycow.arkworld.util.ParticleList;
import com.weedycow.weedylib.ai.WLEntityAIAttackBase;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.AoeRangeUtil;
import com.weedycow.weedylib.util.EntityUtil;
import com.weedycow.weedylib.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

public class BlackSnake extends ArkBoss
{
    public static final String ID = "black_snake";
    public static final String NAME = Arkworld.MODID + ".blackSnake";
    private static final UUID HEATH_BOOST = UUID.fromString("b561603e-1bd6-4d72-946d-7f14294d02e2");
    private static final UUID DAMAGE_BOOST = UUID.fromString("b581546e-7090-4842-8b5b-2adc5d1c6c3b");
    AoeRangeUtil igniteRange;
    AoeRangeUtil detonateRange;

    public BlackSnake(World worldIn)
    {
        super(worldIn, ID, NAME, BossInfo.Color.RED, EnumTypes.OTHER, EnumCamps.UNION, EnumAttackMethod.RANGE_MELEE, EnumDamageTypes.PHYSPELL);
        this.setSize(0.8f, 2f);
        this.isImmuneToFire = true;
        setCountdown(20);
        experienceValue = union.blackSnake.experienceValue;
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new ArkEntityAiBlackSnakeMeteorite(this));
        this.tasks.addTask(2, new WLEntityAIAttackBase(this));
        this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, getMoveSpeed()));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 32.0f));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(union.blackSnake.maxHealth);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(union.blackSnake.movementSpeed);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(union.blackSnake.knockbackResistance);
        this.getEntityAttribute(WLMAttributes.DEFENSIVE_POWER).setBaseValue(union.blackSnake.defensivePower);
        this.getEntityAttribute(WLMAttributes.SPELL_RESISTANCE).setBaseValue(union.blackSnake.spellResistance);
        this.getEntityAttribute(WLMAttributes.ATTACK_RANGE).setBaseValue(union.blackSnake.attackRange);
        this.getEntityAttribute(WLMAttributes.ATTACK_INTERVAL).setBaseValue(union.blackSnake.attackInterval);
        this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE).setBaseValue(union.blackSnake.attackDamage);
    }

    public <E extends IAnimatable> PlayState PlayState(AnimationEvent<E> event)
    {
        if(getStage() ==0)
        {
            if (EntityUtil.atSetIntervals(TICK,this, union.blackSnake.detonateInterval*getAttackInterval()/union.blackSnake.attackInterval, 20*getAttackInterval()/union.blackSnake.attackInterval, 0) && getDistance()<=union.blackSnake.detonateRange)
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake.detonate", false));
                controllerAttack.markNeedsReload();
            }
            else if (EntityUtil.atSetIntervals(TICK,this, union.blackSnake.igniteInterval*getAttackInterval()/union.blackSnake.attackInterval, 16*getAttackInterval()/union.blackSnake.attackInterval, 0) && getDistance()<=union.blackSnake.igniteRange)
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake.ignite", false));
                controllerAttack.markNeedsReload();
            }
            else if (EntityUtil.atSetIntervals(TICK,this, getAttackInterval(), 20*getAttackInterval()/union.blackSnake.attackInterval, 0) && getAttackState())
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake.attack", false));
                controllerAttack.markNeedsReload();
            }
            if (event.isMoving())
            {
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake.move", true));
            }
            else
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake.idle", true));
        }

        if(getStage()==1 || getStage()==3)
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake.transformation", false));
        }

        if(getStage()==2 || getStage()==5)
        {
            controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake_meteorite.rotate", true));
        }

        if(getStage() ==4)
        {
            if (EntityUtil.atSetIntervals(TICK,this, union.blackSnake.energyPolymerInterval*getAttackInterval()/union.blackSnake.attackInterval, 20*getAttackInterval()/union.blackSnake.attackInterval, 0) && getDistance()<=union.blackSnake.energyPolymerRange)
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake.energy_polymer", false));
                controllerAttack.markNeedsReload();
            }
            else if (EntityUtil.atSetIntervals(TICK,this, union.blackSnake.detonateInterval*getAttackInterval()/union.blackSnake.attackInterval, 20*getAttackInterval()/union.blackSnake.attackInterval, 0) && getDistance()<=union.blackSnake.detonateRange)
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake.detonate", false));
                controllerAttack.markNeedsReload();
            }
            else if (EntityUtil.atSetIntervals(TICK,this, union.blackSnake.igniteInterval*getAttackInterval()/union.blackSnake.attackInterval, 16*getAttackInterval()/union.blackSnake.attackInterval, 0) && getDistance()<=union.blackSnake.igniteRange)
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake.ignite", false));
                controllerAttack.markNeedsReload();
            }
            else if (EntityUtil.atSetIntervals(TICK,this, getAttackInterval(), 20*getAttackInterval()/union.blackSnake.attackInterval, 0) && getAttackState())
            {
                controllerAttack.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake.attack", false));
                controllerAttack.markNeedsReload();
            }
            if (event.isMoving())
            {
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake.move", true));
            }
            else
                controllerMove.setAnimation(new AnimationBuilder().addAnimation("animation.black_snake.idle", true));
        }

        controllerAttack.setAnimationSpeed((float)union.blackSnake.attackInterval/getAttackInterval());

        controllerMove.setAnimationSpeed(getMoveSpeed()/union.blackSnake.movementSpeed);

        return PlayState.CONTINUE;
    }

    AnimationController<BlackSnake> controllerAttack = new AnimationController<>(this, "controllerAttack", 1, this::PlayState);
    AnimationController<BlackSnake> controllerMove = new AnimationController<>(this, "controllerMove", 1, this::PlayState);

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(controllerAttack);
        data.addAnimationController(controllerMove);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        igniteRange = new AoeRangeUtil(this,union.blackSnake.igniteRange);

        detonateRange = new AoeRangeUtil(this,union.blackSnake.detonateRange);

        if(getCountdown() > 0 && getStage() == 1)
        {
            setCountdown(getCountdown()-1);
            motionX=0;motionZ=0;
        }

        if(getCountdown() <= 0 && getStage() == 1)
        {
            setCountdown(union.blackSnake.endlessFireTime);
            if(world.isRemote)secondBgm();
            setStage(2);
        }

        if(getCountdown() > 0 && getStage() == 2)
        {
            motionX=0;motionZ=0;
            setCountdown(getCountdown()-1);
            ParticleList.blackSnakeMeteoriteParticle(this);
        }

        if(getCountdown() <= 0 && getStage() == 2)
        {
            setCountdown(30);
            setStage(3);
        }

        if(getCountdown() > 0 && getStage() == 3)
        {
            setCountdown(getCountdown()-1);
            motionX=0;motionZ=0;
        }

        if(getCountdown() <= 0 && getStage() == 3)
        {
            setCountdown(union.blackSnake.endlessFireTime);
            setStage(4);
            IAttributeInstance heath = this.getEntityAttribute(WLMAttributes.MAX_HEALTH);
            heath.applyModifier(new AttributeModifier(HEATH_BOOST,"Heath boost",getMaxHealth()*0.5,0).setSaved(true));
            IAttributeInstance damage = this.getEntityAttribute(WLMAttributes.ATTACK_DAMAGE);
            damage.applyModifier(new AttributeModifier(DAMAGE_BOOST,"Damage boost",getAttackDamage()*0.5,0).setSaved(true));
            setHealth(getMaxHealth());
        }

        if(getCountdown() > 0 && getStage() == 5)
        {
            motionX=0;motionZ=0;
            setCountdown(getCountdown()-1);
            ParticleList.blackSnakeMeteoriteParticle(this);
        }

        if(getCountdown() <= 0 && getStage() == 5)
        {
            this.setDead();
            this.onDeath(DamageSource.GENERIC);
        }

        if(getStage()==2){this.bossInfo.setPercent(1 - (this.getCountdown()/(float)union.blackSnake.endlessFireTime));}
        else if(getStage()==5){this.bossInfo.setPercent(this.getCountdown()/(float)union.blackSnake.endlessFireTime);}
        else this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void commonExecute(WLM wlm, EntityLivingBase target)
    {
        if(getStage()==0 && getAttackState())
        {
            if (EntityUtil.atSetIntervals(TICK,this, union.blackSnake.detonateInterval * getAttackInterval()/union.blackSnake.attackInterval, 0, 0) && getDistance()<=union.blackSnake.detonateRange) detonate();
            else if (EntityUtil.atSetIntervals(TICK,this, union.blackSnake.igniteInterval * getAttackInterval()/union.blackSnake.attackInterval, 0, 0) && getDistance()<=union.blackSnake.igniteRange) ignite();
            else if (EntityUtil.atSetIntervals(TICK,this, this.getAttackInterval(), 0, 0) && getAttackState()) attack();
        }

        if(getStage()==4 && getAttackState())
        {
            if(EntityUtil.atSetIntervals(TICK,this, union.blackSnake.energyPolymerInterval * getAttackInterval()/union.blackSnake.attackInterval, 0, 0) && getDistance()<=union.blackSnake.energyPolymerRange) energyPolymer();
            else if (EntityUtil.atSetIntervals(TICK,this, union.blackSnake.detonateInterval * getAttackInterval()/union.blackSnake.attackInterval, 0, 0) && getDistance()<=union.blackSnake.detonateRange) detonate();
            else if (EntityUtil.atSetIntervals(TICK,this, union.blackSnake.igniteInterval * getAttackInterval()/union.blackSnake.attackInterval, 0, 0) && getDistance()<=union.blackSnake.igniteRange) ignite();
            else if (EntityUtil.atSetIntervals(TICK,this, this.getAttackInterval(), 0, 0) && getAttackState()) attack();
        }
    }

    private void energyPolymer()
    {
        for(int j = 1;j<=union.blackSnake.energyPolymerNumbers;j++)
        {
            EnergyPolymer polymer = new EnergyPolymer(world, this);
            double x = posX + j*MathUtil.getRandomDouble()*MathUtil.getRandomDouble() + MathUtil.getRandomDouble()*union.blackSnake.energyPolymerRange;
            double z = posZ + j*MathUtil.getRandomDouble()*MathUtil.getRandomDouble() + MathUtil.getRandomDouble()*union.blackSnake.energyPolymerRange;
            double y = ArkworldUtil.findSuitableY(world,x,z);
            polymer.setPosition(x,y,z);
            world.spawnEntity(polymer);
        }
    }

    private void detonate()
    {
        for (EntityLivingBase target : world.getEntitiesWithinAABB(EntityLivingBase.class, detonateRange))
        {
            if ((!(target instanceof ArkMob) || ((ArkMob) target).getCamp() != EnumCamps.UNION) && target.hasCapability(CapabilityRegistry.capState,null)
                    && target.getCapability(CapabilityRegistry.capState,null).getStates().contains(EnumState.BURNING_BREATH))
            {
                CapabilityState.Process stateProcess = new CapabilityState.Process(target);
                stateProcess.removeState(EnumState.BURNING_BREATH);
                world.createExplosion(this, target.posX, target.posY, target.posZ, 4, union.blackSnake.detonateDestroyTerrain);
                attackEntityFrom(DamageSource.causeIndirectMagicDamage(this,null), this.getAttackDamage());

                for (EntityLivingBase ar : world.getEntitiesWithinAABB(EntityLivingBase.class, new AoeRangeUtil(target,2)))
                {
                    if(ar!=target && ar!=null)
                    {
                        stateProcess.addFunctionOnlyTick(EnumState.BURNING_BREATH,union.blackSnake.burningBreathTime);
                    }
                }
            }
        }
    }

    private void ignite()
    {
        for (EntityLivingBase target : world.getEntitiesWithinAABB(EntityLivingBase.class, igniteRange))
        {
            if ((!(target instanceof ArkMob) || ((ArkMob) target).getCamp() != EnumCamps.UNION) && target.hasCapability(CapabilityRegistry.capState,null)
                    && !target.getCapability(CapabilityRegistry.capState,null).getStates().contains(EnumState.BURNING_BREATH))
            {
                CapabilityState.Process stateProcess = new CapabilityState.Process(target);
                stateProcess.addFunctionOnlyTick(EnumState.BURNING_BREATH,union.blackSnake.burningBreathTime);
                playSound(SoundRegistry.BLACK_SNAKE_SKILL, 2, 1);
            }
        }
    }

    private void attack()
    {
        EntityUtil.attackMelee(this, this.getAttackRange(), this.getAttackDamage(), DamageSource.causeMobDamage(this));
        playSound(SoundRegistry.BLACK_SNAKE_ATTACK, 2, 1);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
            if (source == DamageSource.causeExplosionDamage(this))
                return false;
        return super.attackEntityFrom(source, amount);
    }

    @SideOnly(Side.CLIENT)
    public void secondBgm()
    {
        if (ArkConfig.common.bgm)
        {
            MovingSound BGM = new MovingSound(this,SoundRegistry.BLACK_SNAKE_SECOND_BGM);
            Minecraft.getMinecraft().getSoundHandler().playSound(BGM);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onBgm(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();

        if (entity instanceof BlackSnake && ((BlackSnake) entity).getStage()==0 && ArkConfig.common.bgm && entity.world.isRemote)
        {
            MovingSound BGM = new MovingSound((EntityLivingBase) entity,SoundRegistry.BLACK_SNAKE_FIRST_BGM);
            Minecraft.getMinecraft().getSoundHandler().playSound(BGM);
        }
        if (entity instanceof BlackSnake && ((BlackSnake) entity).getStage()!=0 && ArkConfig.common.bgm && entity.world.isRemote)
        {
            MovingSound BGM = new MovingSound((EntityLivingBase) entity,SoundRegistry.BLACK_SNAKE_SECOND_BGM);
            Minecraft.getMinecraft().getSoundHandler().playSound(BGM);
        }
    }

    public static void onTrans(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        event.setCanceled(entity instanceof BlackSnake && (((BlackSnake) entity).getStage()!=0 && (((BlackSnake) entity).getStage()!=4)));
    }

    public static void onDeath(LivingDeathEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        if(entity instanceof BlackSnake && ((BlackSnake) entity).getStage()==0)
        {
            event.setCanceled(true);
            entity.setHealth(1);
            ((BlackSnake) entity).setStage(1);
        }
        if(entity instanceof BlackSnake && ((BlackSnake) entity).getStage()==4)
        {
            if(event.getSource().getTrueSource() instanceof EntityPlayer)
                {((EntityPlayer) event.getSource().getTrueSource()).addExperience(((BlackSnake) entity).getExperienceValue());}
            event.setCanceled(true);
            entity.setHealth(1);
            ((BlackSnake) entity).setStage(5);
        }
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        if(getStage()==0||getStage()==1)
            return ArkResUtil.textureEntities(id+"_first");
        if(getStage()==2||getStage()==5)
            return ArkResUtil.textureEntities(id+"_meteorite");
        if(getStage()==3||getStage()==4)
            return ArkResUtil.textureEntities(id+"_second");
        return ArkResUtil.textureEntities(id);
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        if(getStage()==0||getStage()==1)
            return ArkResUtil.geo(id+"_first");
        if(getStage()==2||getStage()==5)
            return ArkResUtil.geo(id+"_meteorite");
        if(getStage()==3||getStage()==4)
            return ArkResUtil.geo(id+"_second");
        return ArkResUtil.geo(id);
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        if(getStage()==0||getStage()==1)
            return ArkResUtil.animation(id+"_first");
        if(getStage()==2||getStage()==5)
            return ArkResUtil.animation(id+"_meteorite");
        if(getStage()==3||getStage()==4)
            return ArkResUtil.animation(id+"_second");
        return ArkResUtil.animation(id);
    }
}