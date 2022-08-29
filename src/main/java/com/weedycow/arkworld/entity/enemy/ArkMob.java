package com.weedycow.arkworld.entity.enemy;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.enemy.cthulhu.elite.BasinSeaReaper;
import com.weedycow.arkworld.entity.enemy.cthulhu.normal.DeepSeaSlider;
import com.weedycow.arkworld.entity.enemy.cthulhu.normal.PocketSeaCrawler;
import com.weedycow.arkworld.entity.enemy.cthulhu.normal.ShellSeaRunner;
import com.weedycow.arkworld.entity.enemy.other.elite.SmilingCorpseMountain;
import com.weedycow.arkworld.entity.enemy.split.boss.Evolution;
import com.weedycow.arkworld.entity.enemy.union.boss.*;
import com.weedycow.arkworld.entity.enemy.union.elite.*;
import com.weedycow.arkworld.entity.enemy.union.normal.*;
import com.weedycow.arkworld.entity.enemy.ursus.normal.InfectedPatrolCaptain;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.ai.WLEntityAIAttackBase;
import com.weedycow.weedylib.entity.*;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class ArkMob extends WLM implements IAnimatable
{
    private final AnimationFactory factory = new AnimationFactory(this);
    public static ArkConfig.Creature.Mob.Union union = ArkConfig.creature.mob.union;
    public static ArkConfig.Creature.Mob.Split split = ArkConfig.creature.mob.split;
    public static ArkConfig.Creature.Mob.Kazdel kazdel = ArkConfig.creature.mob.kazdel;
    public static ArkConfig.Creature.Mob.Ursus ursus = ArkConfig.creature.mob.ursus;
    public static ArkConfig.Creature.Mob.Cthulhu cthulhu = ArkConfig.creature.mob.cthulhu;
    public static ArkConfig.Creature.Mob.Other other = ArkConfig.creature.mob.other;

    public ArkMob(World worldIn)
    {
        super(worldIn);
    }

    public ArkMob(World worldIn, String id, String name, EnumTypes type, EnumCamps camp, EnumStatus status, EnumAttackMethod attackMethod, EnumDamageTypes damageTypes)
    {
        super(worldIn, id, name, EnumMods.ARKWORLD,camp,type,status,attackMethod,damageTypes);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new WLEntityAIAttackBase(this));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, getMoveSpeed()));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 32.0f));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Type", getType());
        compound.setInteger("Countdown", getCountdown());
    }

    @Override
    @ParametersAreNonnullByDefault
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setType(compound.getInteger("Type"));
        setCountdown(compound.getInteger("Countdown"));
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }

    public boolean isNormalGetAttackTarget()
    {
        return true;
    }

    public boolean isNormalGetAttackState()
    {
        return true;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(isNormalGetAttackTarget()) EntityUtil.setTargetState(this,TARGET);

        if(isNormalGetAttackState()) EntityUtil.setAttackState(this,ATTACK,getAttackRange());

        if(!world.isRemote)setDistance((float) EntityUtil.distanceToTarget(this));
    }

    public int getExperienceValue()
    {
        return this.experienceValue;
    }

    public ResourceLocation getTextureLocation()
    {
        if(getType()==1)
            return ArkResUtil.textureEntities(id+"_leader");
        else
            return ArkResUtil.textureEntities(id);
    }

    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo(id);
    }

    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation(id);
    }

    public static void onDrop(LivingDropsEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();

        if(entity instanceof ArkMob)
        {
            ItemStack exp_card = new ItemStack(ItemRegistry.EXP_CARD);
            exp_card.getCapability(CapabilityRegistry.capItem, null).setExperience(((ArkMob) entity).getExperienceValue());
            event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, exp_card));

            List<ItemStack> epic = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.WHITE_HORSE_KOHL),
                    new ItemStack(ItemRegistry.MANGANESE_TRIHYDRATE),new ItemStack(ItemRegistry.GRINDSTONE_PENTAHYDRATE),
                    new ItemStack(ItemRegistry.RMAR),new ItemStack(ItemRegistry.ORIROCK_CONCENTRATION),
                    new ItemStack(ItemRegistry.OPTIMIZED_DEVICE),new ItemStack(ItemRegistry.POLYESTER_LUMP),
                    new ItemStack(ItemRegistry.SUGAR_LUMP),new ItemStack(ItemRegistry.ORIRON_BLOCK),
                    new ItemStack(ItemRegistry.KETON_COLLOID),new ItemStack(ItemRegistry.POLYMERIZED_GEL),
                    new ItemStack(ItemRegistry.INCANDESCENT_ALLOY_BLOCK),new ItemStack(ItemRegistry.CRYSTALLINE_CIRCUIT),
                    new ItemStack(ItemRegistry.CARBON_PACK),new ItemStack(ItemRegistry.REINFORCED_BUILDING_MATERIAL),
                    new ItemStack(ItemRegistry.SKILL_SUMMARY_III),new ItemStack(ItemRegistry.TACTICAL_BATTLE_RECORD),
                    new ItemStack(ItemRegistry.LMB_HUNDRED),new ItemStack(ItemRegistry.HEADHUNT),
                    new ItemStack(ItemRegistry.TOP_EMERGENCY_SANITY_POTION),
                    new ItemStack(ItemRegistry.KETTLE),new ItemStack(ItemRegistry.KEEL),new ItemStack(ItemRegistry.TWO_LOAVES_WITH_CHEESE)));

            List<ItemStack> rare = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.LOXIC_KOHL),
                    new ItemStack(ItemRegistry.MANGANESE_ORE),new ItemStack(ItemRegistry.GRINDSTONE),
                    new ItemStack(ItemRegistry.RMAY),new ItemStack(ItemRegistry.ORIROCK_CLUSTER),
                    new ItemStack(ItemRegistry.INTEGRATED_DEVICE),new ItemStack(ItemRegistry.POLYESTER_PACK),
                    new ItemStack(ItemRegistry.SUGAR_PACK),new ItemStack(ItemRegistry.ORIRON_CLUSTER),
                    new ItemStack(ItemRegistry.AKETON),new ItemStack(ItemRegistry.COAGULATING_GEL),
                    new ItemStack(ItemRegistry.INCANDESCENT_ALLOY),new ItemStack(ItemRegistry.CRYSTALLINE_COMPONENT),
                    new ItemStack(ItemRegistry.CARBON_BRICK),new ItemStack(ItemRegistry.CONCRETE_BUILDING_MATERIAL),
                    new ItemStack(ItemRegistry.SKILL_SUMMARY_II),new ItemStack(ItemRegistry.STRATEGIC_BATTLE_RECORD),
                    new ItemStack(ItemRegistry.ORUNDUM),new ItemStack(ItemRegistry.ORIGINIUM_SHARD),
                    new ItemStack(ItemRegistry.CASTER_CHIP),new ItemStack(ItemRegistry.DEFENDER_CHIP),
                    new ItemStack(ItemRegistry.GUARD_CHIP),new ItemStack(ItemRegistry.MEDIC_CHIP),
                    new ItemStack(ItemRegistry.SNIPER_CHIP),new ItemStack(ItemRegistry.SPECIALIST_CHIP),
                    new ItemStack(ItemRegistry.SUPPORTER_CHIP),new ItemStack(ItemRegistry.VANGUARD_CHIP),
                    new ItemStack(ItemRegistry.EMERGENCY_SANITY_POTION),new ItemStack(ItemRegistry.CHEESE)));

            List<ItemStack> high = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.ORIROCK_CUBE),
                    new ItemStack(ItemRegistry.DEVICE),new ItemStack(ItemRegistry.POLYESTER),
                    new ItemStack(ItemRegistry.SUGAR),new ItemStack(ItemRegistry.ORIRON),new ItemStack(ItemRegistry.LMB,2),
                    new ItemStack(ItemRegistry.POLYKETON),new ItemStack(ItemRegistry.CARBON),new ItemStack(ItemRegistry.WEAPON_FIX_GEN,2),
                    new ItemStack(ItemRegistry.LIGHT_BUILDING_MATERIAL),new ItemStack(ItemRegistry.SKILL_SUMMARY_I),
                    new ItemStack(ItemRegistry.FRONTLINE_BATTLE_RECORD),new ItemStack(ItemRegistry.COIN,Arkworld.NEWDES ? 1 :2),
                    new ItemStack(ItemRegistry.WEAPON_GEM,2),new ItemStack(ItemRegistry.EMERGENCY_SANITY_SAMPLER)));

            List<ItemStack> normal = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.ORIROCK),
                    new ItemStack(ItemRegistry.DAMAGED_DEVICE),new ItemStack(ItemRegistry.ESTER),new ItemStack(ItemRegistry.WEAPON_FIX_GEN),
                    new ItemStack(ItemRegistry.SUGAR_SUBSTITUTE),new ItemStack(ItemRegistry.ORIRON_SHARD),
                    new ItemStack(ItemRegistry.DIKETON),new ItemStack(ItemRegistry.DRILL_BATTLE_RECORD),
                    new ItemStack(ItemRegistry.LMB),new ItemStack(ItemRegistry.WEAPON_GEM),Arkworld.NEWDES ? new ItemStack(ItemRegistry.WEAPON_GEM) : new ItemStack(ItemRegistry.COIN)));

            int chance = new Random().nextInt(100)+1;

            if(((ArkMob)entity).getStatus()==EnumStatus.NORMAL && new Random().nextInt(Arkworld.NEWDES?3:1)==0)
            {
                ItemStack film = new ItemStack(ItemRegistry.FILM);

                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, film));

                if(Arkworld.NEWDES)
                {
                    if(chance<=3)
                    {
                        ItemStack m = epic.get(new Random().nextInt(epic.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    }
                    else if(chance<=8)
                    {
                        ItemStack m = rare.get(new Random().nextInt(rare.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    }
                    else if(chance<=18)
                    {
                        ItemStack m = high.get(new Random().nextInt(high.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    }
                    else
                    {
                        ItemStack m = normal.get(new Random().nextInt(normal.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    }
                }
                else
                {
                    if(chance<=5)
                    {
                        ItemStack m = epic.get(new Random().nextInt(epic.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    }
                    else if(chance<=15)
                    {
                        ItemStack m = rare.get(new Random().nextInt(rare.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    }
                    else if(chance<=35)
                    {
                        ItemStack m = high.get(new Random().nextInt(high.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    }
                    else if(chance<=75)
                    {
                        ItemStack m = normal.get(new Random().nextInt(normal.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    }
                }
            }

            if(((ArkMob)entity).getStatus()==EnumStatus.ELITE && new Random().nextInt(Arkworld.NEWDES?2:1)==0)
            {
                ItemStack film = new ItemStack(ItemRegistry.FILM,new Random().nextInt(3)+2);

                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, film));

                if(Arkworld.NEWDES)
                {
                    if (chance <= 5)
                    {
                        ItemStack m = epic.get(new Random().nextInt(epic.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    }
                    else if (chance <= 15)
                    {
                        ItemStack m = rare.get(new Random().nextInt(rare.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    } else if (chance <= 35)
                    {
                        ItemStack m = high.get(new Random().nextInt(high.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    } else
                    {
                        ItemStack m = normal.get(new Random().nextInt(normal.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    }
                }
                else
                {
                    if (chance <= 10)
                    {
                        ItemStack m = epic.get(new Random().nextInt(epic.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    } else if (chance <= 30)
                    {
                        ItemStack m = rare.get(new Random().nextInt(rare.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    } else if (chance <= 70)
                    {
                        ItemStack m = high.get(new Random().nextInt(high.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    } else
                    {
                        ItemStack m = normal.get(new Random().nextInt(normal.size()));
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                    }
                }
            }

            if(((ArkMob)entity).getStatus()==EnumStatus.LEADER)
            {
                ItemStack film = new ItemStack(ItemRegistry.FILM, new Random().nextInt(3) + 6);

                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, film));

                if (chance <= 20)
                {
                    ItemStack m = epic.get(new Random().nextInt(epic.size()));
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                } else if (chance <= 60)
                {
                    ItemStack m = rare.get(new Random().nextInt(rare.size()));
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                } else
                {
                    ItemStack m = high.get(new Random().nextInt(high.size()));
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                }
            }

            if(entity instanceof BlackSnake)
            {
                ItemStack w = new ItemStack(ItemRegistry.TALULAH_SWORD);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));

                if(!Arkworld.NEWDES)
                {
                    ItemStack d = new ItemStack(ItemRegistry.BLACK_SNAKE_DOLL);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, d));
                }
            }

            if(entity instanceof Faust)
            {
                if(!Arkworld.NEWDES)
                {
                    ItemStack w = new ItemStack(ItemRegistry.FAUST_CROSSBOW);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }

                ItemStack d = new ItemStack(ItemRegistry.FAUST_DOLL);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, d));
            }

            if(entity instanceof Frostnova)
            {
                int c = new Random().nextInt(2);

                if(!Arkworld.NEWDES)
                {
                    if (c == 0)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.FROSTNOVA_CHEST);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    } else
                    {
                        ItemStack w = new ItemStack(ItemRegistry.FROSTMOURNE);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }
                }

                ItemStack w = new ItemStack(ItemRegistry.FROSTNOVA_HEAD);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));

                ItemStack d = new ItemStack(ItemRegistry.FROSTNOVA_DOLL);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, d));
            }

            if(entity instanceof Mayfest)
            {
                if(!Arkworld.NEWDES)
                {
                    ItemStack w = new ItemStack(ItemRegistry.HEALING_STAFF);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }

                ItemStack d = new ItemStack(ItemRegistry.MAYFEST_DOLL);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, d));
            }

            if(entity instanceof Evolution)
            {
                ItemStack d = new ItemStack(ItemRegistry.EVOLUTION_DOLL);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, d));
            }

            if(entity instanceof Patriot)
            {
                int c = new Random().nextInt(6);

                if(!Arkworld.NEWDES)
                {
                    if (c == 0)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.PATRIOT_JAVELIN);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 1)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.PATRIOT_SHIELD);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 2)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.PATRIOT_HEAD);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 3)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.PATRIOT_CHEST);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 4)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.PATRIOT_LEGS);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 5)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.PATRIOT_FEET);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }
                }

                ItemStack d = new ItemStack(ItemRegistry.PATRIOT_DOLL);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, d));
            }

            if(entity instanceof Regicide)
            {
                ItemStack w = new ItemStack(ItemRegistry.REGICIDE_ARMOR);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));

                ItemStack d = new ItemStack(ItemRegistry.REGICIDE_DOLL);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, d));
            }

            if(entity instanceof Skullshatterer)
            {
                if(!Arkworld.NEWDES)
                {
                    ItemStack g = new ItemStack(ItemRegistry.GRENADE_GUN);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, g));
                }

                ItemStack w = new ItemStack(ItemRegistry.SKULLSHATTERER_ARMOR);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));

                ItemStack d = new ItemStack(ItemRegistry.SKULLSHATTERER_DOLL);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, d));
            }

            if(entity instanceof W)
            {
                if(!Arkworld.NEWDES)
                {
                    ItemStack w = new ItemStack(ItemRegistry.D_12);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }

                ItemStack d = new ItemStack(ItemRegistry.W_DOLL);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, d));
            }

            if(entity instanceof DefenseCrusher)
            {
                int c = new Random().nextInt(3);

                if(new Random().nextInt(4)==0)
                {
                    if (c == 0)
                    {
                        if(!Arkworld.NEWDES)
                        {
                            ItemStack w = new ItemStack(ItemRegistry.DEFENSE_CRUSHER_CHEST);
                            event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                        }
                    }

                    if (c == 1)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.DEFENSE_CRUSHER_LEGS);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 2)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.DEFENSE_CRUSHER_FEET);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }
                }
            }

            if(entity instanceof GuerrillaShieldGuard)
            {
                if(new Random().nextInt(4)==0)
                {
                    ItemStack w = new ItemStack(ItemRegistry.GUERRILLA_SHIELD);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }
            }

            if(entity instanceof BasinSeaReaper)
            {
                ItemStack w = new ItemStack(ItemRegistry.IBERIA_STEM, new Random().nextInt(3) + 1);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                ItemStack m = new ItemStack(ItemRegistry.IBERIA_PETAL, new Random().nextInt(3) + 1);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));

            }

            if(entity instanceof PocketSeaCrawler)
            {

                ItemStack w = new ItemStack(ItemRegistry.IBERIA_STEM, new Random().nextInt(2) + 1);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                ItemStack m = new ItemStack(ItemRegistry.IBERIA_PETAL, new Random().nextInt(2) + 1);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
            }

            if(entity instanceof DeepSeaSlider)
            {
                ItemStack w = new ItemStack(ItemRegistry.IBERIA_STEM);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                ItemStack m = new ItemStack(ItemRegistry.IBERIA_PETAL);
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
            }

            if(entity instanceof ShellSeaRunner)
            {
                if(new Random().nextInt(4)==0)
                {
                    ItemStack w = new ItemStack(ItemRegistry.IBERIA_STEM);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    ItemStack m = new ItemStack(ItemRegistry.IBERIA_PETAL);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, m));
                }
            }

            if(entity instanceof SmilingCorpseMountain)
            {
                if(new Random().nextInt(8)==0)
                {
                    ItemStack w = new ItemStack(ItemRegistry.SMILING_CORPSE_MOUNTAIN_ARMOR);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }
            }

            if(entity instanceof GuerrillaSiegebreaker)
            {
                if(new Random().nextInt(4)==0)
                {
                    ItemStack w = new ItemStack(ItemRegistry.GUERRILLA_SIEGEBREAKER_ARMOR);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }
            }

            if(entity instanceof HatefulAvenger)
            {
                if(new Random().nextInt(4)==0)
                {
                    ItemStack w = new ItemStack(ItemRegistry.AVENGER_SWORD);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }
            }

            if(entity instanceof HeavyDefender)
            {
                if(new Random().nextInt(4)==0)
                {
                    ItemStack w = new ItemStack(ItemRegistry.HEAVY_SHIELD);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }
            }

            if(entity instanceof SeniorCaster)
            {
                int c = new Random().nextInt(2);

                if(new Random().nextInt(4)==0)
                {
                    if (c == 0)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.SENIOR_CASTER_HEAD);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    } else
                    {
                        ItemStack w = new ItemStack(ItemRegistry.SENIOR_CASTER_CHEST);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }
                }
            }

            if(entity instanceof YetiIcecleaver)
            {
                if(new Random().nextInt(Arkworld.NEWDES?8:4)==0)
                {
                    ItemStack w = new ItemStack(ItemRegistry.ICECLEAVER_SWORD);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }
            }

            if(entity instanceof Avenger)
            {
                if(new Random().nextInt(8)==0)
                {
                    ItemStack w = new ItemStack(ItemRegistry.AVENGER_SWORD);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }
            }

            if(entity instanceof CocktailThrower)
            {
                if(new Random().nextInt(8)==0)
                {
                    ItemStack w = new ItemStack(ItemRegistry.FIREBOMB);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }
            }

            if(entity instanceof Crossbowman)
            {
                if(new Random().nextInt(8)==0)
                {
                    ItemStack w = new ItemStack(ItemRegistry.NORMAL_CROSSBOW);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }
            }

            if(entity instanceof DualSwordsman)
            {
                if(new Random().nextInt(8)==0)
                {
                    ItemStack w = new ItemStack(ItemRegistry.DUAL_SWORD);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }
            }

            if(entity instanceof Rioter)
            {
                if(new Random().nextInt(8)==0)
                {
                    ItemStack w = new ItemStack(ItemRegistry.PIPE);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }
            }

            if(entity instanceof ShieldSoldier)
            {
                int c = new Random().nextInt(6);

                if(new Random().nextInt(8)==0)
                {
                    if (c == 0)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.SOLDIER_HEAD);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 1)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.SOLDIER_CHEST);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 2)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.SOLDIER_LEGS);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 3)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.SOLDIER_FEET);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 4)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.NORMAL_MACHETE);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 5)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.NORMAL_SHIELD);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }
                }
            }

            if(entity instanceof Soldier)
            {
                int c = new Random().nextInt(5);

                if(new Random().nextInt(8)==0)
                {
                    if (c == 0)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.SOLDIER_HEAD);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 1)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.SOLDIER_CHEST);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 2)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.SOLDIER_LEGS);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 3)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.SOLDIER_FEET);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 4)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.NORMAL_MACHETE);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }
                }
            }

            if(entity instanceof TechnicalScout)
            {
                if(new Random().nextInt(8)==0)
                {
                    ItemStack w = new ItemStack(ItemRegistry.TECHNICAL_SCOUT_ARMOR);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }
            }

            if(entity instanceof Wraith)
            {
                if(new Random().nextInt(8)==0)
                {
                    ItemStack w = new ItemStack(ItemRegistry.WRAITH_ARMOR);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }
            }

            if(entity instanceof YetiOperative)
            {
                int c = new Random().nextInt(5);

                if(new Random().nextInt(8)==0)
                {

                    if (c == 0)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.YETI_OPERATIVE_HEAD);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 1)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.YETI_OPERATIVE_CHEST);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 2)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.YETI_OPERATIVE_LEGS);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 3)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.YETI_OPERATIVE_FEET);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 4)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.URSUS_KNIFE);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }
                }
            }

            if(entity instanceof YetiSniper)
            {
                int c = new Random().nextInt(5);

                if(new Random().nextInt(8)==0)
                {
                    if (c == 0)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.YETI_OPERATIVE_HEAD);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 1)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.YETI_OPERATIVE_CHEST);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 2)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.YETI_OPERATIVE_LEGS);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 3)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.YETI_OPERATIVE_FEET);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }

                    if (c == 4)
                    {
                        ItemStack w = new ItemStack(ItemRegistry.NORMAL_CROSSBOW);
                        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                    }
                }
            }

            if(entity instanceof InfectedPatrolCaptain)
            {
                if(new Random().nextInt(8)==0)
                {
                    ItemStack w = new ItemStack(ItemRegistry.URSUS_KNIFE);
                    event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, w));
                }
            }
        }
    }
}
