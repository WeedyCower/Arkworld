package com.weedycow.arkworld.ai;

import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class OperateMoveBase extends EntityAIBase
{
    protected final Operator entity;
    protected double x;
    protected double y;
    protected double z;
    protected final double speed;
    protected int executionChance;
    protected boolean mustUpdate;
    protected final float probability = 0.001F;
    private int timeToRecalcPath;
    private float oldWaterCost;
    private double lookX;
    private double lookZ;
    private int idleTime;
    private int seeTime;
    
    public OperateMoveBase(Operator creatureIn, double speedIn)
    {
        this(creatureIn, speedIn, 120);
    }

    public OperateMoveBase(Operator creatureIn, double speedIn, int chance)
    {
        this.entity = creatureIn;
        this.speed = speedIn;
        this.executionChance = chance;
        this.setMutexBits(3);
    }

    public boolean shouldExecute()
    {
        if(entity.getAttackTarget() == null || entity.getActionMode() == Operator.ActionMode.NONE || entity.getActionMode() == Operator.ActionMode.WORK)
        {
            if (entity.getMoveMode() == Operator.MoveMode.WANDER)
            {
                if (!this.mustUpdate)
                {
                    if (this.entity.getIdleTime() >= 100)
                    {
                        return false;
                    }

                    if (this.entity.getRNG().nextInt(this.executionChance) != 0)
                    {
                        return false;
                    }
                }

                Vec3d vec3d = this.getPosition();

                if (vec3d == null)
                {
                    return false;
                } else
                {
                    this.x = vec3d.x;
                    this.y = vec3d.y;
                    this.z = vec3d.z;
                    this.mustUpdate = false;
                    return true;
                }
            }

            if (entity.getMoveMode() == Operator.MoveMode.FOLLOW)
            {
                return entity.getMaster() != null && entity.getDistance(entity.getMaster())<entity.getEntityAttribute(WLMAttributes.FOLLOW_RANGE).getAttributeValue();
            }

            if (entity.getMoveMode() == Operator.MoveMode.IDLE)
            {
                return this.entity.getRNG().nextFloat() < 0.02F;
            }
        }
        else
        {
            EntityLivingBase entitylivingbase = this.entity.getAttackTarget();

            if(entity.shouldExecute())
            {
                return entitylivingbase != null;
            }
            else
                return false;
        }
        
        return false;
    }

    @Nullable
    protected Vec3d getPosition()
    {
        if (this.entity.isInWater())
        {
            Vec3d vec3d = RandomPositionGenerator.getLandPos(this.entity, 15, 7);
            return vec3d == null ? RandomPositionGenerator.findRandomTarget(this.entity, 10, 7) : vec3d;
        }
        else
        {
            return this.entity.getRNG().nextFloat() >= this.probability ? RandomPositionGenerator.getLandPos(this.entity, 10, 7) : RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
        }
    }

    public boolean shouldContinueExecuting()
    {
        if(entity.getMoveMode() == Operator.MoveMode.FOLLOW && entity.getMaster()!=null)
            return entity.getDistance(entity.getMaster())<entity.getEntityAttribute(WLMAttributes.FOLLOW_RANGE).getAttributeValue() || !this.entity.getNavigator().noPath();
        else if(entity.getMoveMode()== Operator.MoveMode.IDLE)
            return this.idleTime >= 0;
        else if(entity.getActionMode() != Operator.ActionMode.NONE && entity.getAttackTarget() != null)
            return this.shouldExecute() || !this.entity.getNavigator().noPath();
        else return !this.entity.getNavigator().noPath();
    }

    public void startExecuting()
    {
        if(entity.getMoveMode() == Operator.MoveMode.FOLLOW)
        {
            this.timeToRecalcPath = 0;
            this.oldWaterCost = this.entity.getPathPriority(PathNodeType.WATER);
            this.entity.setPathPriority(PathNodeType.WATER, 0.0F);
        }
        else if(entity.getMoveMode() == Operator.MoveMode.IDLE)
        {
            double d0 = (Math.PI * 2D) * entity.getRNG().nextDouble();
            this.lookX = Math.cos(d0);
            this.lookZ = Math.sin(d0);
            this.idleTime = 20 + entity.getRNG().nextInt(20);
        }
        else 
            this.entity.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);
    }

    public void resetTask()
    {
        if(entity.getActionMode() != Operator.ActionMode.NONE && entity.getAttackTarget() != null)
        {
            this.seeTime = 0;
        }
        else if(entity.getMoveMode() == Operator.MoveMode.FOLLOW && entity.getMaster() != null)
        {
            entity.getNavigator().clearPath();
            entity.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
        }
    }

    public void updateTask()
    {
        if (entity.getAttackTarget() != null && entity.getActionMode()!= Operator.ActionMode.NONE && entity.getActionMode()!= Operator.ActionMode.WORK)
        {
            boolean flag = this.entity.getEntitySenses().canSee(entity.getAttackTarget());

            if (flag)
            {
                ++this.seeTime;
            } else
            {
                this.seeTime = 0;
            }

            if (EntityUtil.distanceToTarget(entity) <= entity.getAttackRange())
            {
                if (seeTime >= 20)
                    entity.getNavigator().clearPath();

                entity.closeExecute(entity, entity.getAttackTarget());

                Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, 16, 7, new Vec3d(this.entity.getAttackTarget().posX, this.entity.getAttackTarget().posY, this.entity.getAttackTarget().posZ));

                if(EntityUtil.distanceToTarget(entity)<=entity.getAttackRange()/2 && vec3d!=null && entity.shouldKeepDistance())
                {
                    entity.getNavigator().setPath(entity.getNavigator().getPathToXYZ(vec3d.x, vec3d.y,vec3d.z), entity.getMoveSpeed());
                    this.entity.getNavigator().setSpeed(entity.getMoveSpeed());
                }
            }
            else
            {
                if (entity.isTryMoveToEntityLiving())
                    entity.getNavigator().tryMoveToEntityLiving(entity.getAttackTarget(), entity.getMoveSpeed());

                entity.remoteExecute(entity, entity.getAttackTarget());
            }

            entity.commonExecute(entity, entity.getAttackTarget());

            this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 30.0F, 30.0F);
        }
        else if (entity.getMoveMode() == Operator.MoveMode.IDLE)
        {
            --this.idleTime;
            entity.getLookHelper().setLookPosition(entity.posX + this.lookX, entity.posY + (double) entity.getEyeHeight(), entity.posZ + this.lookZ, (float) entity.getHorizontalFaceSpeed(), (float) entity.getVerticalFaceSpeed());
        }
        else if (entity.getMoveMode() == Operator.MoveMode.FOLLOW)
        {
            if (entity.getMaster() != null && !this.entity.getLeashed())
            {
                this.entity.getLookHelper().setLookPositionWithEntity(entity.getMaster(), 10.0F, (float)this.entity.getVerticalFaceSpeed());

                if (--this.timeToRecalcPath <= 0)
                {
                    this.timeToRecalcPath = 10;
                    double d0 = this.entity.posX - entity.getMaster().posX;
                    double d1 = this.entity.posY - entity.getMaster().posY;
                    double d2 = this.entity.posZ - entity.getMaster().posZ;
                    double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                    if (d3 > 9)
                    {
                        entity.getNavigator().tryMoveToEntityLiving(entity.getMaster(), entity.getMoveSpeed());
                    }
                    else
                    {
                        entity.getNavigator().clearPath();
                    }
                }
            }
        }

        if (this.entity.getRNG().nextFloat() < 0.8F && this.entity.isInWater() || this.entity.isInLava())
        {
            this.entity.getJumpHelper().setJumping();
        }
    }
}
