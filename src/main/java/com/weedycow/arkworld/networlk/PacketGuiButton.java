package com.weedycow.arkworld.networlk;

import com.weedycow.arkworld.gui.IButton;
import com.weedycow.arkworld.util.ArkNetworkUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGuiButton implements IMessage
{
    private int buttonID;

    public PacketGuiButton()
    {
    }

    public PacketGuiButton(int buttonID)
    {
        this.buttonID = buttonID;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        buttonID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(buttonID);
    }

    public static class Handler implements IMessageHandler<PacketGuiButton, IMessage>
    {

        @Override
        public IMessage onMessage(PacketGuiButton message, MessageContext ctx)
        {
            EntityPlayer player = ArkNetworkUtil.getPlayer(ctx);
            if(player != null)
            {
                ArkNetworkUtil.getThreadListener(ctx).addScheduledTask(() ->
                {
                    if(player.openContainer instanceof IButton)
                    {
                        ((IButton) player.openContainer).onButtonPress(message.buttonID);
                    }
                });
            }
            return null;
        }
    }
}
