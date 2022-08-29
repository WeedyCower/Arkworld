package com.weedycow.arkworld.entity.enemy.union.boss;

import com.weedycow.weedylib.entity.WLM;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;

public abstract class UnionBoss extends WLM implements IAnimatable
{
    public UnionBoss(World worldIn)
    {
        super(worldIn);
        this.enablePersistence();
    }
    @Override
    public boolean isNonBoss()
    {
        return false;
    }
}