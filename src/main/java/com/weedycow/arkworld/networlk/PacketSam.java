package com.weedycow.arkworld.networlk;

import com.weedycow.arkworld.capability.CapabilitySam;
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

public class PacketSam implements IMessage
{
    public int sam;
    public int limit;
    public int tick;
    public int variable;

    public static class Handler implements IMessageHandler<PacketSam, IMessage>
    {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketSam message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(() ->
                {
                    EntityPlayer player = Minecraft.getMinecraft().player;

                    if(player!=null && player.hasCapability(CapabilityRegistry.capSam,null))
                    {
                        CapabilitySam sam = player.getCapability(CapabilityRegistry.capSam,null);
                        sam.setSam(message.sam);
                        sam.setLimit(message.limit);
                        sam.setTick(message.tick);
                        sam.setVariable(message.variable);
                    }
                });
            }
            return null;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        sam = ByteBufUtils.readVarShort(buf);
        limit = ByteBufUtils.readVarShort(buf);
        tick = ByteBufUtils.readVarShort(buf);
        variable = ByteBufUtils.readVarShort(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeVarShort(buf,sam);
        ByteBufUtils.writeVarShort(buf,limit);
        ByteBufUtils.writeVarShort(buf,tick);
        ByteBufUtils.writeVarShort(buf,variable);
    }
}
