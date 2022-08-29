package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.capability.EnumState;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import java.util.List;


public class StateGui extends Gui
{
    private final Minecraft mc;
    private final Entity entity;

    public StateGui()
    {
        this.mc = Minecraft.getMinecraft();
        this.entity = mc.player;
    }

    public void render()
    {
        if(entity.hasCapability(CapabilityRegistry.capState,null))
        {
            CapabilityState state = entity.getCapability(CapabilityRegistry.capState,null);
            List<EnumState> states = state.getStates();
            ScaledResolution resolution = new ScaledResolution(this.mc);
            int height = resolution.getScaledHeight();
            GlStateManager.enableBlend();

            for(int i = 0; i<=states.size()-1&&states.size()>0; i++)
            {
                if(states.get(i).getTick()>0 && states.get(i)!=EnumState.DEFAULT)
                {
                    mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/" + states.get(i) + ".png"));
                    drawModalRectWithCustomSizedTexture(i*16+1, height-17, 0, 0, 16, 16, 16, 16);
                    if(states.get(i)==EnumState.NERVE_INJURY)
                        drawString(mc.fontRenderer,String.valueOf(states.get(i).getLevel()-10),i*16+1, height-17,0XFFFFFF);
                    else
                        drawString(mc.fontRenderer,String.valueOf(states.get(i).getSeconds()),i*16+1, height-17,0XFFFFFF);
                }
            }
        }
    }
}
