package com.weedycow.arkworld.block.machine.infrastructure;

import com.weedycow.arkworld.block.ArkTileTickable;
import com.weedycow.arkworld.entity.operator.Operator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public abstract class MachineTile extends ArkTileTickable
{
    protected int oDis;
    protected int level;
    protected int powerNeed;
    protected int operatorAmount;
    protected float moodConsumeReduce;
    protected boolean hasCenter;
    protected BlockControlCenter.TileControlCenter center;
    protected List<Operator> operators = new ArrayList<>();

    public MachineTile(String id, int interval, boolean sync)
    {
        super(id, interval, sync);
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getLevel()
    {
        return level;
    }

    public List<Operator> getOperators()
    {
        return operators;
    }

    public void setOperatorAmount(int operatorAmount)
    {
        this.operatorAmount = operatorAmount;
    }

    public int getOperatorAmount()
    {
        return operatorAmount;
    }

    public int getoDis()
    {
        return oDis;
    }

    public void setCenter(BlockControlCenter.TileControlCenter center)
    {
        this.center = center;
    }

    public BlockControlCenter.TileControlCenter getCenter()
    {
        return center;
    }

    public void setPowerNeed(int powerNeed)
    {
        this.powerNeed = powerNeed;
    }

    public int getPowerNeed()
    {
        return powerNeed;
    }

    public void setMoodConsumeReduce(float moodConsumeReduce)
    {
        this.moodConsumeReduce = moodConsumeReduce;
    }

    public float getMoodConsumeReduce()
    {
        return moodConsumeReduce;
    }

    public boolean isHasCenter()
    {
        return hasCenter;
    }

    public void setHasCenter(boolean hasCenter)
    {
        this.hasCenter = hasCenter;
    }

    @Override
    public void update()
    {
        super.update();

        if(tickExisted%40==0) markDirty();

        setHasCenter(getCenter()!=null);

        if(!world.isRemote)
            setOperatorAmount(operators.size());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        setLevel(compound.getInteger("Level"));

        setOperatorAmount(compound.getInteger("OperatorAmount"));

        if(compound.hasKey("cx") && compound.hasKey("cy") && compound.hasKey("cz"))
        {
            if(hasWorld() && world.getTileEntity(new BlockPos(compound.getInteger("cx"),compound.getInteger("cy"),compound.getInteger("cz"))) instanceof BlockControlCenter.TileControlCenter)
                setCenter((BlockControlCenter.TileControlCenter) world.getTileEntity(new BlockPos(compound.getInteger("cx"),compound.getInteger("cy"),compound.getInteger("cz"))));
        }

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Level",getLevel());

        compound.setInteger("OperatorAmount",getOperatorAmount());

        if(getCenter()!=null)
        {
            compound.setInteger("cx", getCenter().getPos().getX());
            compound.setInteger("cy", getCenter().getPos().getY());
            compound.setInteger("cz", getCenter().getPos().getZ());
        }

        return compound;
    }
}
