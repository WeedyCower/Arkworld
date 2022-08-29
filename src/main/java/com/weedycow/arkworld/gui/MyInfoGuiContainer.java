package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.*;
import com.weedycow.arkworld.networlk.PacketGuiButton;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.weedylib.util.StringUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class MyInfoGuiContainer extends GuiContainer
{
    private float oldMouseX;
    private float oldMouseY;
    public static int samTimes;
    public static int manaTimes;
    public static int staminaTimes;
    EntityPlayer player;
    private static final ResourceLocation BG = new ResourceLocation(Arkworld.MODID +":textures/gui/my_info.png");

    public MyInfoGuiContainer(EntityPlayer player, Container inventorySlotsIn)
    {
        super(inventorySlotsIn);
        this.xSize = 301;
        this.ySize = 201;
        this.player = player;
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if(button.id==0) samTimes+=1;
        else if(button.id==1) manaTimes+=1;
        else if(button.id==2) staminaTimes+=1;
        else Arkworld.getNetwork().sendToServer(new PacketGuiButton(button.id));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX,mouseY,partialTicks);
        this.oldMouseX = (float)mouseX;
        this.oldMouseY = (float)mouseY;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1,1,1,1);
        mc.getTextureManager().bindTexture(BG);
        drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
        GuiInventory.drawEntityOnScreen(guiLeft + 67, guiTop + 106, 50, (float)(guiLeft + 67) - this.oldMouseX, (float)(guiTop + 106 - 50) - this.oldMouseY, this.mc.player);
        drawInfo(false);drawSamBar(false);drawManaBar(false);drawStaminaBar(false);drawExpBar(false);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        drawInfo(true);drawSamBar(true);drawManaBar(true);drawStaminaBar(true);drawExpBar(true);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.add(0,new NormalButton(0,"sam",guiLeft + 9,guiTop + 120,11,11,""));
        buttonList.add(1,new NormalButton(1,"mana",guiLeft + 9,guiTop + 138,11,11,""));
        buttonList.add(2,new NormalButton(2,"stamina",guiLeft + 9,guiTop + 156,11,11,""));
        buttonList.add(3,new NormalButton(3,"lmb",guiLeft + 136, guiTop + 39,12,12,""));
        buttonList.add(4,new NormalButton(4,"originium",guiLeft + 136, guiTop + 63,12,12,""));
        buttonList.add(5,new NormalButton(5,"orundum",guiLeft + 136, guiTop + 86,12,12,""));
    }

    private void drawSamBar(boolean text)
    {
        if(player.hasCapability(CapabilityRegistry.capSam,null))
        {
            CapabilitySam sam = player.getCapability(CapabilityRegistry.capSam,null);
            int s = sam.getSam();
            int l = sam.getLimit();
            String t = StringUtil.addZero(s) + "/" + StringUtil.addZero(l);

            if(text)
            {
                for (int i = 0; i <= t.length() - 1; i++)
                {
                    mc.fontRenderer.drawSplitString(t.substring(i, i + 1), 37 + i * 12, 122, 116, 0XF0F0F0);
                }
            }
            else if(s>0)
            {
                mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/sam_bar_info.png"));
                drawScaledCustomSizeModalRect(guiLeft + 27, guiTop + 120, 0, 0, (int) (97*(float)s/(float)l), 11, (int) (97*(float)s/(float)l), 11, 97, 11);
            }
        }
    }

    private void drawManaBar(boolean text)
    {
        if(player.hasCapability(CapabilityRegistry.capMana,null))
        {
            CapabilityMana mana = player.getCapability(CapabilityRegistry.capMana,null);
            int m = mana.getMana();
            int l = mana.getLimit();
            String t = StringUtil.addZero(m) + "/" + StringUtil.addZero(l);

            if(text)
            {
                for (int i = 0; i <= t.length() - 1; i++)
                {
                    mc.fontRenderer.drawSplitString(t.substring(i, i + 1), 37 + i * 12, 140, 116, 0Xe9cd64);
                }
            }
            else if(m>0)
            {
                mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/mana_bar_info.png"));
                drawScaledCustomSizeModalRect(guiLeft + 27, guiTop + 138, 0, 0, (int) (97*(float)m/(float)l), 11, (int) (97*(float)m/(float)l), 11, 97, 11);
            }
        }
    }

    private void drawStaminaBar(boolean text)
    {
        if(player.hasCapability(CapabilityRegistry.capStamina,null))
        {
            CapabilityStamina stamina = player.getCapability(CapabilityRegistry.capStamina,null);
            int s = stamina.getStamina();
            int l = stamina.getLimit();
            String t = StringUtil.addZero(s) + "/" + StringUtil.addZero(l);

            if(text)
            {
                for (int i = 0; i <= t.length() - 1; i++)
                {
                    mc.fontRenderer.drawSplitString(t.substring(i, i + 1), 37 + i * 12, 158, 116, 0Xf1813c);
                }
            }
            else if(s>0)
            {
                mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/stamina_bar_info.png"));
                drawScaledCustomSizeModalRect(guiLeft + 27, guiTop + 156, 0, 0, (int) (97*(float)s/(float)l), 11, (int) (97*(float)s/(float)l), 11, 97, 11);
            }
        }
    }

    private void drawExpBar(boolean text)
    {
        if(player.hasCapability(CapabilityRegistry.capLevel,null))
        {
            CapabilityLevel level = player.getCapability(CapabilityRegistry.capLevel,null);
            int e = level.getExperience();
            int n = level.getNeed();
            String t = e + "/" + n;

            if(text)
            {
                for (int i = 0; i <= t.length() - 1; i++)
                {
                    mc.fontRenderer.drawSplitString(t.substring(i, i + 1), 12 + i * 6, 180, 116, 0X6ef335);
                }
            }
            else if(e>0)
            {
                mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/exp_bar.png"));
                drawScaledCustomSizeModalRect(guiLeft + 9, guiTop + 176, 0, 0, (int) (115*(float)e/(float)n), 15, (int) (115*(float)e/(float)n), 15, 155, 15);
            }
        }
    }

    private void drawInfo(boolean text)
    {
        if(player.hasCapability(CapabilityRegistry.capValue,null) && player.hasCapability(CapabilityRegistry.capLevel,null))
        {
            CapabilityValue value = player.getCapability(CapabilityRegistry.capValue,null);
            CapabilityLevel level = player.getCapability(CapabilityRegistry.capLevel,null);

            int lmb = value.getLmb();
            int orundum = value.getOrundum();
            int originium = value.getOriginium();
            int l = level.getLevel();
            String t = getFormat(level.getTime()/20);
            GlStateManager.enableBlend();

            if(text)
            {
                mc.fontRenderer.drawSplitString(String.valueOf(lmb), 150, 42, 116, 0XFFFFFF);
                mc.fontRenderer.drawSplitString(String.valueOf(originium), 150, 66, 116, 0XFFFFFF);
                mc.fontRenderer.drawSplitString(String.valueOf(orundum), 150, 89, 116, 0XFFFFFF);
                mc.fontRenderer.drawSplitString(player.getDisplayNameString(), 229, 42, 116, 0XFFFFFF);
                mc.fontRenderer.drawSplitString(String.valueOf(l), 229, 66, 116, 0XFFFFFF);
                mc.fontRenderer.drawSplitString(t, 229, 90, 116, 0XFFFFFF);
            }
            else
            {
                mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/name.png"));
                drawModalRectWithCustomSizedTexture(guiLeft + 215, guiTop + 39, 0, 0, 12, 12, 12, 12);
                mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/level.png"));
                drawModalRectWithCustomSizedTexture(guiLeft + 215, guiTop + 63, 0, 0, 12, 12, 12, 12);
                mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/time.png"));
                drawModalRectWithCustomSizedTexture(guiLeft + 215, guiTop + 87, 0, 0, 12, 12, 12, 12);
                mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/weapon.png"));
                drawModalRectWithCustomSizedTexture(guiLeft + 108, guiTop + 16, 0, 0, 16, 16, 16, 16);
                mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/defence.png"));
                drawModalRectWithCustomSizedTexture(guiLeft + 108, guiTop + 34, 0, 0, 16, 16, 16, 16);
                mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/player.png"));
                drawModalRectWithCustomSizedTexture(guiLeft + 108, guiTop + 52, 0, 0, 16, 16, 16, 16);
            }
        }
    }

    public static String getFormat(int time)
    {
        int dd = time / 86400;
        int hh = (time % 86400) / 3600;
        int mm = (time % 3600) / 60;
        int ss = (time % 3600) % 60;
        return  (dd < 10 ? ("0" + dd) : dd) + ":" +
                (hh < 10 ? ("0" + hh) : hh) + ":" +
                (mm < 10 ? ("0" + mm) : mm) + ":" +
                (ss < 10 ? ("0" + ss) : ss);
    }
}
