package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.machine.infrastructure.BlockControlCenter;
import com.weedycow.arkworld.block.machine.infrastructure.BlockMachine;
import com.weedycow.arkworld.networlk.PacketGuiButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ControlCenterGuiContainer extends GuiContainer
{
    private float oldMouseX;
    private float oldMouseY;
    private TileEntity tile;
    private static final ResourceLocation BG = new ResourceLocation(Arkworld.MODID +":textures/gui/center_container.png");

    public ControlCenterGuiContainer(Container inventorySlotsIn, TileEntity tile)
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
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        if(tile instanceof BlockControlCenter.TileControlCenter)
        {
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_master") + (((BlockControlCenter.TileControlCenter) tile).getMaster() !=null ? ((BlockControlCenter.TileControlCenter) tile).getMaster().getName() : "null"), 13, 14, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_lv") + ((BlockControlCenter.TileControlCenter) tile).getLevel(), 13, 24, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.machine_uav") + ((BlockControlCenter.TileControlCenter) tile).getUav()+"/"+((BlockControlCenter.TileControlCenter) tile).getUavLimit(), 13, 34, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.machine_man") + ((BlockControlCenter.TileControlCenter) tile).getMachineAmount(BlockMachine.Type.MANUFACTURING_STATION), 13, 44, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.machine_tra") + ((BlockControlCenter.TileControlCenter) tile).getMachineAmount(BlockMachine.Type.TRADING_STATION), 13, 54, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.machine_pow") + ((BlockControlCenter.TileControlCenter) tile).getMachineAmount(BlockMachine.Type.POWER_STATION), 13, 64, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.machine_pro") + ((BlockControlCenter.TileControlCenter) tile).getMachineAmount(BlockMachine.Type.PROCESSING_STATION), 13, 74, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.machine_max") + ((BlockControlCenter.TileControlCenter) tile).getMachineLimited(), 93, 14, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.machine_dom") + ((BlockControlCenter.TileControlCenter) tile).getMachineAmount(BlockMachine.Type.DORMITORY), 93, 24, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.machine_off") + ((BlockControlCenter.TileControlCenter) tile).getMachineAmount(BlockMachine.Type.OFFICE), 93, 34, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.machine_train") + ((BlockControlCenter.TileControlCenter) tile).getMachineAmount(BlockMachine.Type.TRAINING_ROOM), 93, 44, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.machine_par") + ((BlockControlCenter.TileControlCenter) tile).getMachineAmount(BlockMachine.Type.PARLOR), 93, 54, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_totalPowerValue") + ((BlockControlCenter.TileControlCenter) tile).getPowerConsume()+"/"+((BlockControlCenter.TileControlCenter) tile).getPower(), 93, 64, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operatorAmount2") + ((BlockControlCenter.TileControlCenter) tile).getOperatorAmount(), 93, 74, 0x3f3f3f);
        }
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.add(0,new NormalButton(0,"center_container_button",guiLeft+63,guiTop+94,53,17, I18n.format("gui.arkworld.machine_levelup")));
        buttonList.add(1,new NormalButton(1,"center_container_button",guiLeft+117,guiTop+94,53,17, I18n.format("gui.arkworld.machine_uavAdd")));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        Arkworld.getNetwork().sendToServer(new PacketGuiButton(button.id));
    }
}
