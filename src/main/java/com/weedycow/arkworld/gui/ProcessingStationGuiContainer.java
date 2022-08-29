package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.networlk.PacketGuiButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ProcessingStationGuiContainer extends GuiContainer
{
    private float oldMouseX;
    private float oldMouseY;
    private static final ResourceLocation BG = new ResourceLocation(Arkworld.MODID +":textures/gui/processing_station.png");

    public ProcessingStationGuiContainer(Container inventorySlotsIn)
    {
        super(inventorySlotsIn);
        this.xSize = 179;
        this.ySize = 201;
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
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.add(0,new NormalButton(0,"blank",guiLeft+74,guiTop+53,8,30, ""));
        buttonList.add(0,new NormalButton(0,"blank",guiLeft+98,guiTop+53,8,30, ""));
        buttonList.add(0,new NormalButton(0,"blank",guiLeft+82,guiTop+53,16,6, ""));
        buttonList.add(0,new NormalButton(0,"blank",guiLeft+82,guiTop+75,16,6, ""));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        Arkworld.getNetwork().sendToServer(new PacketGuiButton(button.id));
    }
}
