package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.machine.infrastructure.BlockPowerStation;
import com.weedycow.arkworld.networlk.PacketGuiButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class PowerStationGuiContainer extends GuiContainer
{
    private float oldMouseX;
    private float oldMouseY;
    private TileEntity tile;
    private static final ResourceLocation BG = new ResourceLocation(Arkworld.MODID +":textures/gui/trading_station.png");

    public PowerStationGuiContainer(Container inventorySlotsIn, TileEntity tile)
    {
        super(inventorySlotsIn);
        this.xSize = 179;
        this.ySize = 201;
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
        mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID + ":textures/gui/progress_bar.png"));
        if(tile instanceof BlockPowerStation.TilePowerStation)
            drawScaledCustomSizeModalRect(guiLeft+48, guiTop+35, 0, 0, (int) (84*(1-((BlockPowerStation.TilePowerStation) tile).getRate())), 11, (int) (84*(1-((BlockPowerStation.TilePowerStation) tile).getRate())), 11,84,11);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        if(tile instanceof BlockPowerStation.TilePowerStation)
        {
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_lv") + ((BlockPowerStation.TilePowerStation) tile).getLevel(), 13, 77, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.power") + ((BlockPowerStation.TilePowerStation) tile).getPowerProduction(), 13, 87, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.mood") + "1-" + ((BlockPowerStation.TilePowerStation) tile).getMoodConsumeReduce(), 13, 97, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operatorAmount") + ((BlockPowerStation.TilePowerStation) tile).getOperatorAmount(), 93, 77, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_totalPower") + (((BlockPowerStation.TilePowerStation) tile).getCenter()!=null ? ((BlockPowerStation.TilePowerStation) tile).getCenter().getPowerConsume()+"/"+((BlockPowerStation.TilePowerStation) tile).getCenter().getPower() : "0/0"), 93, 87, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_hasCenter") + (((BlockPowerStation.TilePowerStation) tile).getCenter()!=null ? ((BlockPowerStation.TilePowerStation) tile).getCenter().getPos().getX()+","+((BlockPowerStation.TilePowerStation) tile).getCenter().getPos().getY()+","+((BlockPowerStation.TilePowerStation) tile).getCenter().getPos().getZ() : "false"), 93, 97, 0x3f3f3f);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        Arkworld.getNetwork().sendToServer(new PacketGuiButton(button.id));
    }
}
