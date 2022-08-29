package com.weedycow.arkworld.networlk;

import com.weedycow.arkworld.capability.CapabilityLevel;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketLevel implements IMessage
{
    public int level;
    public int need;
    public int experience;
    public int time;

    public static class Handler implements IMessageHandler<PacketLevel, IMessage>
    {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketLevel message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(() ->
                {
                    EntityPlayer player = Minecraft.getMinecraft().player;

                    if(player!=null && player.hasCapability(CapabilityRegistry.capLevel,null))
                    {
                        CapabilityLevel level = player.getCapability(CapabilityRegistry.capLevel,null);
                        level.setExperience(message.experience);
                        level.setLevel(message.level);
                        level.setNeed(message.need);
                        level.setTime(message.time);
                    }
                });
            }
            return null;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        level = ByteBufUtils.readVarShort(buf);
        need = ByteBufUtils.readVarShort(buf);
        experience = ByteBufUtils.readVarShort(buf);
        time = ByteBufUtils.readVarShort(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeVarShort(buf,level);
        ByteBufUtils.writeVarShort(buf,need);
        ByteBufUtils.writeVarShort(buf,experience);
        ByteBufUtils.writeVarShort(buf,time);
    }
}
