package com.weedycow.arkworld.world.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MachineWorldSavedData extends WorldSavedData
{
    public List<BlockPos> pos = new ArrayList<>();
    public static final String DATA_NAME = "arkworld_machine_amount";

    public MachineWorldSavedData()
    {
        super(DATA_NAME);
    }

    public MachineWorldSavedData(String name)
    {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        for(int i=0; i<nbt.getInteger("size"); i++)
        {
            pos.add(i,new BlockPos(nbt.getIntArray(String.valueOf(i))[0],nbt.getIntArray(String.valueOf(i))[1],nbt.getIntArray(String.valueOf(i))[2]));
        }
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        for(int i = 0; i< pos.size(); i++)
            compound.setIntArray(String.valueOf(i),new int[] {pos.get(i).getX(), pos.get(i).getY(), pos.get(i).getZ()});

        compound.setInteger("size", pos.size());

        return compound;
    }

    public void setPos(List<BlockPos> pos)
    {
        this.pos = pos;
    }

    public List<BlockPos> getPos()
    {
        return pos;
    }

    public static MachineWorldSavedData get(World world)
    {
        MapStorage storage = world.getPerWorldStorage();
        MachineWorldSavedData instance = (MachineWorldSavedData) storage.getOrLoadData(MachineWorldSavedData.class, DATA_NAME);

        if (instance == null)
        {
            instance = new MachineWorldSavedData();
            storage.setData(DATA_NAME, instance);
        }

        return instance;
    }
}
