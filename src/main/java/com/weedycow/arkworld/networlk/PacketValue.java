package com.weedycow.arkworld.networlk;

import com.weedycow.arkworld.capability.CapabilityValue;
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

public class PacketValue implements IMessage
{
    public int lmb;
    public int orundum;
    public int originium;
    public int signId;

    public static class Handler implements IMessageHandler<PacketValue, IMessage>
    {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketValue message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(() ->
                {
                    EntityPlayer player = Minecraft.getMinecraft().player;

                    if(player!=null && player.hasCapability(CapabilityRegistry.capValue,null))
                    {
                        CapabilityValue value = player.getCapability(CapabilityRegistry.capValue,null);
                        value.setLmb(message.lmb);
                        value.setOrundum(message.orundum);
                        value.setOriginium(message.originium);
                        value.setSignId(message.signId);
                    }
                });
            }
            return null;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        lmb = ByteBufUtils.readVarShort(buf);
        orundum = ByteBufUtils.readVarShort(buf);
        originium = ByteBufUtils.readVarShort(buf);
        signId = ByteBufUtils.readVarShort(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeVarShort(buf,lmb);
        ByteBufUtils.writeVarShort(buf,orundum);
        ByteBufUtils.writeVarShort(buf,originium);
        ByteBufUtils.writeVarShort(buf,signId);
    }
}
