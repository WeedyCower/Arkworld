package com.weedycow.arkworld.networlk;

import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PacketState implements IMessage
{
    public int id;
    public List<String> ids;

    public static class Handler implements IMessageHandler<PacketState, IMessage>
    {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketState message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(() ->
                {
                    World world = Minecraft.getMinecraft().world;
                    if(message.ids!=null && message.id!=0)
                    {
                        EntityLivingBase entity = (EntityLivingBase) world.getEntityByID(message.id);

                        if (entity != null && entity.hasCapability(CapabilityRegistry.capState, null))
                        {
                            CapabilityState state = entity.getCapability(CapabilityRegistry.capState, null);

                            state.setStates(message.ids.stream().map(EnumState::getStateFormId).collect(Collectors.toList()));
                        }
                    }
                });
            }
            return null;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        id = ByteBufUtils.readVarShort(buf);
        ids = Arrays.stream(new PacketBuffer(buf).readVarIntArray()).boxed().collect(Collectors.toList()).stream().map(String::valueOf).collect(Collectors.toList());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeVarShort(buf,id);
        new PacketBuffer(buf).writeVarIntArray(ids.stream().map(Integer::valueOf).collect(Collectors.toList()).stream().mapToInt(Integer::intValue).toArray());
    }
}
