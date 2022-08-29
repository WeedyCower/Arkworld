package com.weedycow.arkworld.networlk;

import com.weedycow.arkworld.capability.CapabilityPlayer;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.util.ArkNetworkUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSwing implements IMessage
{
    public boolean attackable;
    public String name;

    public static class Handler implements IMessageHandler<PacketSwing, IMessage>
    {
        @Override
        public IMessage onMessage(PacketSwing message, MessageContext ctx)
        {
            EntityPlayer player = ctx.getServerHandler().player;

            if(player != null && player.getName().equals(message.name))
            {
                ArkNetworkUtil.getThreadListener(ctx).addScheduledTask(() ->
                {
                    if(player.hasCapability(CapabilityRegistry.capPlayer,null))
                    {
                        CapabilityPlayer.Process p = new CapabilityPlayer.Process(player);
                        p.setAttackable(message.attackable);
                    }
                });
            }
            return null;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        attackable = buf.readBoolean();
        name = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(attackable);
        ByteBufUtils.writeUTF8String(buf,name);
    }
}
