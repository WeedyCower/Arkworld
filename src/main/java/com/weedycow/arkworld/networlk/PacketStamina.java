package com.weedycow.arkworld.networlk;

import com.weedycow.arkworld.capability.CapabilityStamina;
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

public class PacketStamina implements IMessage
{
    public int stamina;
    public int limit;
    public int tick;
    public int variable;

    public static class Handler implements IMessageHandler<PacketStamina, IMessage>
    {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketStamina message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(() ->
                {
                    EntityPlayer player = Minecraft.getMinecraft().player;

                    if(player!=null && player.hasCapability(CapabilityRegistry.capStamina,null))
                    {
                        CapabilityStamina s = player.getCapability(CapabilityRegistry.capStamina,null);
                        s.setStamina(message.stamina);
                        s.setLimit(message.limit);
                        s.setTick(message.tick);
                        s.setVariable(message.variable);
                    }
                });
            }
            return null;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        stamina = ByteBufUtils.readVarShort(buf);
        limit = ByteBufUtils.readVarShort(buf);
        tick = ByteBufUtils.readVarShort(buf);
        variable = ByteBufUtils.readVarShort(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeVarShort(buf, stamina);
        ByteBufUtils.writeVarShort(buf,limit);
        ByteBufUtils.writeVarShort(buf,tick);
        ByteBufUtils.writeVarShort(buf,variable);
    }
}
