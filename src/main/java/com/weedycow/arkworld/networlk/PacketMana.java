package com.weedycow.arkworld.networlk;

import com.weedycow.arkworld.capability.CapabilityMana;
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

public class PacketMana implements IMessage
{
    public int mana;
    public int limit;
    public int tick;
    public int variable;

    public static class Handler implements IMessageHandler<PacketMana, IMessage>
    {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketMana message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(() ->
                {
                    EntityPlayer player = Minecraft.getMinecraft().player;

                    if(player!=null && player.hasCapability(CapabilityRegistry.capMana,null))
                    {
                        CapabilityMana m = player.getCapability(CapabilityRegistry.capMana,null);
                        m.setMana(message.mana);
                        m.setLimit(message.limit);
                        m.setTick(message.tick);
                        m.setVariable(message.variable);
                    }
                });
            }
            return null;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        mana = ByteBufUtils.readVarShort(buf);
        limit = ByteBufUtils.readVarShort(buf);
        tick = ByteBufUtils.readVarShort(buf);
        variable = ByteBufUtils.readVarShort(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeVarShort(buf,mana);
        ByteBufUtils.writeVarShort(buf,limit);
        ByteBufUtils.writeVarShort(buf,tick);
        ByteBufUtils.writeVarShort(buf,variable);
    }
}
