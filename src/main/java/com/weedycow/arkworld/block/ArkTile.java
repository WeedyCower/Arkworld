package com.weedycow.arkworld.block;

import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.block.WLT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;

public abstract class ArkTile extends WLT implements IAnimatable
{
    public String id;
    private final AnimationFactory factory = new AnimationFactory(this);

    public ArkTile(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    public void registerControllers(AnimationData animationData)
    {

    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(pos, 1, writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(@Nonnull NetworkManager manager, SPacketUpdateTileEntity packet)
    {
        NBTTagCompound data = packet.getNbtCompound(); readFromNBT(data);
    }

    @Override
    public AnimationFactory getFactory()
    {
        return factory;
    }

    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureBlocks(id);
    }

    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo(id);
    }

    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation(id);
    }
}
