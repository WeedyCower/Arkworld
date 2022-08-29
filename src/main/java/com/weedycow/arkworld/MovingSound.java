package com.weedycow.arkworld;

import com.weedycow.arkworld.entity.enemy.union.boss.BlackSnake;
import com.weedycow.arkworld.entity.enemy.union.boss.Frostnova;
import com.weedycow.arkworld.entity.enemy.union.boss.Patriot;
import com.weedycow.arkworld.registry.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MovingSound extends PositionedSound implements ITickableSound
{
    private boolean donePlaying;
    private final EntityLivingBase entity;
    SoundEvent sound;

    public MovingSound(EntityLivingBase entity, SoundEvent soundId)
    {
        super(soundId, SoundCategory.MUSIC);
        this.xPosF = (float) entity.posX;
        this.yPosF = (float) entity.posY;
        this.zPosF = (float) entity.posZ;
        this.volume = 3;
        this.pitch = 1;
        this.repeat = true;
        this.repeatDelay = 0;
        this.entity = entity;
    }

    @Override
    public boolean isDonePlaying()
    {
        return donePlaying;
    }

    public void setDonePlaying(boolean donePlaying)
    {
        this.donePlaying = donePlaying;
    }

    @Override
    public void update()
    {
        boolean flag = false;
        
        if(entity instanceof BlackSnake && sound== SoundRegistry.BLACK_SNAKE_FIRST_BGM) flag = ((BlackSnake)entity).getStage() != 0;

        if(entity instanceof Frostnova && sound== SoundRegistry.FROSTNOVA_FIRST_BGM) flag = ((Frostnova)entity).getStage() != 0;

        if(entity instanceof Patriot && sound== SoundRegistry.PATRIOT_FIRST_BGM) flag = ((Patriot)entity).getStage() != 0;

        setDonePlaying(flag || Minecraft.getMinecraft().isGamePaused() || !entity.isEntityAlive());
    }
}
