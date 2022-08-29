package com.weedycow.arkworld.networlk;

import com.weedycow.arkworld.capability.CapabilityAttribute;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketAttribute implements IMessage
{
    public int id;
    public float defencePower;
    public float spellResistance;

    public static class Handler implements IMessageHandler<PacketAttribute, IMessage>
    {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketAttribute message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(() ->
                {
                    World world = Minecraft.getMinecraft().world;
                    EntityLivingBase entity = (EntityLivingBase) world.getEntityByID(message.id);

                    if(entity!=null && entity.hasCapability(CapabilityRegistry.capAttribute,null))
                    {
                        CapabilityAttribute attribute = entity.getCapability(CapabilityRegistry.capAttribute,null);
                        attribute.setDefencePower(message.defencePower);
                        attribute.setSpellResistance(message.spellResistance);
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
        defencePower = buf.readFloat();
        spellResistance = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeVarShort(buf,id);
        buf.writeFloat(defencePower);
        buf.writeFloat(spellResistance);
    }
}
