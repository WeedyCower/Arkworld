package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityMana;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.weedylib.util.StringUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ManaGui extends SimpleGui
{
    @Override
    public void render(int x)
    {
        if(entity.hasCapability(CapabilityRegistry.capMana,null))
        {
            CapabilityMana mana = entity.getCapability(CapabilityRegistry.capMana,null);
            GlStateManager.enableBlend();

            int m = mana.getMana();
            int l = mana.getLimit();
            String t = StringUtil.addZero(m) + "/" + StringUtil.addZero(l);

            mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/bar_bg.png"));
            drawModalRectWithCustomSizedTexture(x, 64, 0, 0, 12, 100, 12, 100);

            mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/mana.png"));
            drawModalRectWithCustomSizedTexture(x, 164, 0, 0, 12, 12, 12, 12);

            if(m>0)
            {
                mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/mana_bar.png"));
                drawScaledCustomSizeModalRect(x, 64 + 100 - (int)(((float)m/(float)mana.getLimit())*100), 0, 100 - ((float)m/(float)mana.getLimit())*100, 12, (int)(((float)m/(float)mana.getLimit())*100), 12, (int)(((float)m/(float)mana.getLimit())*100), 12, 100);
            }

            for(int i = 0; i<=t.length()-1; i++)
            {
                mc.fontRenderer.drawSplitString(t.substring(i, i + 1), x+3, 75 + i * 12, 116, 0Xe9cd64);
            }
        }
    }
}
