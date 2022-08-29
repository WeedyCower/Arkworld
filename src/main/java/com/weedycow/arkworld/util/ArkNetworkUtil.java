package com.weedycow.arkworld.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ArkNetworkUtil
{
    public static IThreadListener getThreadListener(MessageContext context)
    {
        if (context.side.isServer())
            return context.getServerHandler().player.mcServer;
        else
            return null;
    }

    public static EntityPlayer getPlayer(MessageContext context)
    {
        if (context.side.isServer())
            return context.getServerHandler().player;
        else
            return null;
    }
}
