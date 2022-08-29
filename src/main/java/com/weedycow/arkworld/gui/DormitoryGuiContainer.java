package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.machine.infrastructure.BlockDormitory;
import com.weedycow.arkworld.networlk.PacketGuiButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class DormitoryGuiContainer extends GuiContainer
{
    private float oldMouseX;
    private float oldMouseY;
    private TileEntity tile;
    private static final ResourceLocation BG = new ResourceLocation(Arkworld.MODID +":textures/gui/office.png");

    public DormitoryGuiContainer(Container inventorySlotsIn, TileEntity tile)
    {
        super(inventorySlotsIn);
        this.xSize = 179;
        this.ySize = 139;
        this.tile = tile;
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
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        if(tile instanceof BlockDormitory.TileDormitory)
        {
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_lv") + ((BlockDormitory.TileDormitory) tile).getLevel(), 13, 15, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operatorAmount") + ((BlockDormitory.TileDormitory) tile).getOperatorAmount(), 13, 25, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_moodRecover") + ((BlockDormitory.TileDormitory) tile).getMood() + "+" + ((BlockDormitory.TileDormitory) tile).getMoodAdd(), 13, 35, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_atmosphere") + ((BlockDormitory.TileDormitory) tile).getAtmosphere(), 101, 15, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_totalPower") + (((BlockDormitory.TileDormitory) tile).getCenter()!=null ? ((BlockDormitory.TileDormitory) tile).getCenter().getPowerConsume()+"/"+((BlockDormitory.TileDormitory) tile).getCenter().getPower() : "0/0"), 101, 25, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_hasCenter") + (((BlockDormitory.TileDormitory) tile).getCenter()!=null ? ((BlockDormitory.TileDormitory) tile).getCenter().getPos().getX()+","+((BlockDormitory.TileDormitory) tile).getCenter().getPos().getY()+","+((BlockDormitory.TileDormitory) tile).getCenter().getPos().getZ() : "false"), 101, 35, 0x3f3f3f);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        Arkworld.getNetwork().sendToServer(new PacketGuiButton(button.id));
    }
}
