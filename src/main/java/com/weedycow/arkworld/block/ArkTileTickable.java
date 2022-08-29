package com.weedycow.arkworld.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.util.Constants;

public abstract class ArkTileTickable extends ArkTile implements ITickable
{
    public int tickExisted;
    public int interval;
    public boolean sync;

    public ArkTileTickable(String id, int interval, boolean sync)
    {
        super(id);
        this.interval = interval;
        this.sync = sync;
    }

    @Override
    public void update()
    {
        tickExisted+=1;

        if(atSetIntervals(interval))intervalExecute();

        if(sync)world.notifyBlockUpdate(pos, getBlockType().getDefaultState(), getBlockType().getDefaultState(), Constants.BlockFlags.SEND_TO_CLIENTS);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        setTickExisted(compound.getInteger("TickExisted"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("TickExisted",getTickExisted());

        return compound;
    }

    public void setTickExisted(int tickExisted)
    {
        this.tickExisted = tickExisted;
    }

    public int getTickExisted()
    {
        return tickExisted;
    }

    public boolean atSetIntervals(int interval)
    {
        return tickExisted%interval==0;
    }

    protected void intervalExecute()
    {

    }
}
