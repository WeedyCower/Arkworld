package com.weedycow.arkworld.ai;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.weedycow.arkworld.capability.CapabilityValue;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.weedylib.entity.WLM;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

public class OperatorAIAttackTarget extends EntityAITarget
{
    private final int targetChance;
    protected final EntityAINearestAttackableTarget.Sorter sorter;
    protected EntityLivingBase targetEntity;
    private final int taskOwnerRevengeTimerOld;
    private final int masterRevengeTimerOld;

    public OperatorAIAttackTarget(Operator creature, boolean checkSight)
    {
        this(creature, checkSight, false);
    }

    public OperatorAIAttackTarget(Operator creature, boolean checkSight, boolean onlyNearby)
    {
        this(creature, 10, checkSight, onlyNearby);
    }

    public OperatorAIAttackTarget(Operator creature, int chance, boolean checkSight, boolean onlyNearby)
    {
        super(creature, checkSight, onlyNearby);
        this.targetChance = chance;
        this.sorter = new EntityAINearestAttackableTarget.Sorter(creature);
        this.taskOwnerRevengeTimerOld = taskOwner.getRevengeTimer();
        if(((Operator) taskOwner).getMaster()!=null)
            this.masterRevengeTimerOld = ((Operator) taskOwner).getMaster().getRevengeTimer();
        else
            this.masterRevengeTimerOld = 0;
        this.setMutexBits(1);
    }

    public boolean shouldExecute()
    {
        if(((Operator) taskOwner).getActionMode() == Operator.ActionMode.HEALING && ((Operator) taskOwner).getOperatorClass()== Operator.Class.MEDIC)
        {
            List<EntityLivingBase> entities = this.taskOwner.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getTargetableArea(this.getTargetDistance()),entity -> ((entity instanceof Operator && ((Operator) entity).getMaster()==((Operator) taskOwner).getMaster()) || entity==((Operator) taskOwner).getMaster()) && entity.getHealth()<entity.getMaxHealth());

            if (entities.isEmpty())
            {
                return false;
            }
            else
            {
                EntityLivingBase minHp = entities.get(0);

                for (EntityLivingBase entity : entities)
                {
                    if (entity.getHealth()/entity.getMaxHealth() < minHp.getHealth()/minHp.getMaxHealth())
                    {
                        minHp = entity;
                    }
                }

                this.targetEntity = minHp;
                return true;
            }
        }
        if (((Operator) taskOwner).getActionMode() == Operator.ActionMode.NONE || ((Operator) taskOwner).getActionMode() == Operator.ActionMode.WORK || (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0))
        {
            return false;
        }
        if (((Operator) taskOwner).getActionMode() == Operator.ActionMode.FIGHTBACK)
        {
            int i = this.taskOwner.getRevengeTimer();
            EntityLivingBase target = this.taskOwner.getRevengeTarget();
            if (i != this.taskOwnerRevengeTimerOld && target != null && this.isSuitableTarget(target, false))
            {
                if(this.targetEntity != ((Operator) taskOwner).getMaster())
                {
                    this.targetEntity = target;
                    return true;
                }
            }
        }
        if (((Operator) taskOwner).getActionMode() == Operator.ActionMode.PROTECTIVE)
        {
            EntityPlayer player = ((Operator) taskOwner).getMaster();
            if (player != null)
            {
                int i = player.getRevengeTimer();
                EntityLivingBase target = player.getRevengeTarget();
                if (i != this.masterRevengeTimerOld && target != null && this.isSuitableTarget(target, false))
                {
                    if(this.targetEntity != ((Operator) taskOwner).getMaster())
                    {
                        this.targetEntity = target;
                        return true;
                    }
                }
            }
        }
        if (((Operator) taskOwner).getActionMode() == Operator.ActionMode.SIGN)
        {
            if(((Operator) taskOwner).getMaster()!=null)
            {
                CapabilityValue.Process value = new CapabilityValue.Process(((Operator) taskOwner).getMaster());

                Entity t = taskOwner.world.getEntityByID(value.getSign());

                if(t instanceof EntityLivingBase && taskOwner.getDistance(t)<32)
                {
                    if(this.target != ((Operator) taskOwner).getMaster())
                    {
                        this.targetEntity = (EntityLivingBase) t;
                        return true;
                    }
                }

            }
        }
        if (((Operator) taskOwner).getActionMode() == Operator.ActionMode.ENEMY)
        {
            List<WLM> list = this.taskOwner.world.getEntitiesWithinAABB(WLM.class, this.getTargetableArea(this.getTargetDistance()));
            if (list.isEmpty())
            {
                return false;
            }
            else
            {
                list.sort(this.sorter);
                this.targetEntity = list.get(0);
                return true;
            }
        }
        if (((Operator) taskOwner).getActionMode() == Operator.ActionMode.MOB)
        {
            List<EntityMob> list = this.taskOwner.world.getEntitiesWithinAABB(EntityMob.class, this.getTargetableArea(this.getTargetDistance()));
            if (list.isEmpty())
            {
                return false;
            }
            else
            {
                list.sort(this.sorter);
                this.targetEntity = list.get(0);
                return true;
            }
        }
        if (((Operator) taskOwner).getActionMode() == Operator.ActionMode.ALL)
        {
            List<EntityLivingBase> list = this.taskOwner.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getTargetableArea(this.getTargetDistance()), new Predicate<EntityLivingBase>()
            {
                @Override
                public boolean apply(@Nullable EntityLivingBase input)
                {
                    return input != ((Operator) taskOwner).getMaster();
                }
            });

            if(isContainPlayer(list))
            {
                this.targetEntity = this.taskOwner.world.getNearestAttackablePlayer(this.taskOwner.posX, this.taskOwner.posY + (double) this.taskOwner.getEyeHeight(), this.taskOwner.posZ, this.getTargetDistance(), this.getTargetDistance(), new Function<EntityPlayer, Double>()
                {
                    @Nonnull
                    public Double apply(@Nullable EntityPlayer p_apply_1_)
                    {
                        return 1.0D;
                    }
                }, null);
                return this.targetEntity != null;
            }
            else
            {
                if (list.isEmpty())
                {
                    return false;
                }
                else
                {
                    list.sort(this.sorter);
                    this.targetEntity = list.get(0);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean shouldContinueExecuting()
    {
        if(((Operator) taskOwner).getOperatorClass() == Operator.Class.MEDIC)
        {
            EntityLivingBase entitylivingbase = this.taskOwner.getAttackTarget();

            if (entitylivingbase == null)
            {
                entitylivingbase = this.target;
            }

            if (entitylivingbase == null)
            {
                return false;
            }
            else if (entitylivingbase.getHealth()>=entitylivingbase.getMaxHealth())
            {
                return false;
            }
            else if (!entitylivingbase.isEntityAlive())
            {
                return false;
            }
            else
            {
                Team team = this.taskOwner.getTeam();
                Team team1 = entitylivingbase.getTeam();

                List<EntityLivingBase> entities = this.taskOwner.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getTargetableArea(this.getTargetDistance()),entity -> ((entity instanceof Operator && ((Operator) entity).getMaster()==((Operator) taskOwner).getMaster()) || entity==((Operator) taskOwner).getMaster()) && entity.getHealth()<entity.getMaxHealth());

                if (entities.isEmpty())
                {
                    return false;
                }
                else
                {
                    EntityLivingBase minHp = entities.get(0);

                    for (EntityLivingBase entity : entities)
                    {
                        if (entity.getHealth()/entity.getMaxHealth() < minHp.getHealth()/minHp.getMaxHealth())
                        {
                            minHp = entity;
                        }
                    }

                    if(entitylivingbase.getHealth()/entitylivingbase.getMaxHealth()>minHp.getHealth()/minHp.getMaxHealth()) return false;
                }

                if (team != null && team1 == team)
                {
                    return false;
                }
                else
                {
                    double d0 = this.getTargetDistance();

                    if (this.taskOwner.getDistanceSq(entitylivingbase) > d0 * d0)
                    {
                        return false;
                    }
                    else
                    {
                        if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage)
                        {
                            return false;
                        }
                        else
                        {
                            this.taskOwner.setAttackTarget(entitylivingbase);
                            return true;
                        }
                    }
                }
            }
        }
        else
        {
            EntityLivingBase entitylivingbase = this.taskOwner.getAttackTarget();

            if (entitylivingbase == null)
            {
                entitylivingbase = this.target;
            }

            if (entitylivingbase == null)
            {
                return false;
            }
            else if (!entitylivingbase.isEntityAlive())
            {
                return false;
            }
            else
            {
                Team team = this.taskOwner.getTeam();
                Team team1 = entitylivingbase.getTeam();

                if (team != null && team1 == team)
                {
                    return false;
                }
                else
                {
                    double d0 = this.getTargetDistance();

                    if (this.taskOwner.getDistanceSq(entitylivingbase) > d0 * d0)
                    {
                        return false;
                    }
                    else
                    {
                        if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage)
                        {
                            return false;
                        }
                        else
                        {
                            this.taskOwner.setAttackTarget(entitylivingbase);
                            return true;
                        }
                    }
                }
            }
        }
    }

    public boolean isContainPlayer(List<? extends EntityLivingBase> list)
    {
        for(EntityLivingBase base : list)
        {
            if(base instanceof EntityPlayer)
            {
                return true;
            }
        }
        return false;
    }

    protected AxisAlignedBB getTargetableArea(double targetDistance)
    {
        return this.taskOwner.getEntityBoundingBox().grow(targetDistance, 4.0D, targetDistance);
    }

    public void startExecuting()
    {
        if(targetEntity!=null && targetEntity.isEntityAlive())
            this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }

    public static class Sorter implements Comparator<Entity>
    {
        private final Entity entity;

        public Sorter(Entity entityIn)
        {
            this.entity = entityIn;
        }

        public int compare(Entity p_compare_1_, Entity p_compare_2_)
        {
            double d0 = this.entity.getDistanceSq(p_compare_1_);
            double d1 = this.entity.getDistanceSq(p_compare_2_);

            if (d0 < d1)
            {
                return -1;
            }
            else
            {
                return d0 > d1 ? 1 : 0;
            }
        }
    }
}
