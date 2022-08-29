package com.weedycow.arkworld.entity.device;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.registry.SoundRegistry;
import com.weedycow.weedylib.entity.WLMAttributes;
import com.weedycow.weedylib.util.AoeRangeUtil;
import com.weedycow.weedylib.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

import java.util.List;

public class EntityMine extends ArkDevice
{
    Operator attacker;
    float damage;
    int vertigo;
    public static final String ID = "mine";
    public static final String NAME = Arkworld.MODID + ".mine";

    public EntityMine(World worldIn)
    {
        super(worldIn,ID);
        setSize(1f,0.5f);
        setCountdown(2400);
    }

    public EntityMine(World worldIn, Operator attacker, float damage, int vertigo)
    {
        super(worldIn,ID);
        setSize(1f,0.5f);
        this.attacker = attacker;
        this.damage = damage;
        this.vertigo = vertigo;
        setCountdown(2400);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(WLMAttributes.MAX_HEALTH).setBaseValue(1000);
        this.getEntityAttribute(WLMAttributes.MOVEMENT_SPEED).setBaseValue(0);
        this.getEntityAttribute(WLMAttributes.KNOCKBACK_RESISTANCE).setBaseValue(10);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(getCountdown() > 0)
        {
            setCountdown(getCountdown()-1);
        }

        if((getCountdown() <= 0 || attacker==null) && !world.isRemote) this.setDead();

        if(getCountdown()>0 && EntityUtil.atSetIntervals(this,20,0,0) && !world.isRemote)
        {
            List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class,new AoeRangeUtil(this,1),entity-> entity instanceof EntityMob);

            if(entities.size()>0)
            {
                world.createExplosion(attacker, posX, posY, posZ, damage, ArkConfig.creature.operator.sniper.ow.mineDestroyTerrain);

                playSound(SoundRegistry.W_EXPLODE,1,1);

                for(EntityLivingBase entity : entities)
                {
                    new CapabilityState.Process(entity).addFunctionOnlyTick(EnumState.VERTIGO,vertigo);
                }

                this.setDead();
            }
        }
    }
}
