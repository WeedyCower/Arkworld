package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.machine.infrastructure.BlockTrainingRoom;
import com.weedycow.arkworld.networlk.PacketGuiButton;
import com.weedycow.arkworld.util.ArkEntityUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TrainingRoomGuiContainer extends GuiContainer
{
    private float oldMouseX;
    private float oldMouseY;
    private TileEntity tile;
    private static final ResourceLocation BG = new ResourceLocation(Arkworld.MODID +":textures/gui/office.png");

    public TrainingRoomGuiContainer(Container inventorySlotsIn, TileEntity tile)
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
        if(tile instanceof BlockTrainingRoom.TileTrainingRoom)
        {
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_lv") + ((BlockTrainingRoom.TileTrainingRoom) tile).getLevel(), 13, 15, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.mood") + "1-" + ((BlockTrainingRoom.TileTrainingRoom) tile).getMoodConsumeReduce(), 13, 25, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operatorAmount") + ((BlockTrainingRoom.TileTrainingRoom) tile).getOperatorAmount(), 13, 35, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_trainer") + (ArkEntityUtil.getOperator(mc.world,((BlockTrainingRoom.TileTrainingRoom) tile).getTrainer()) != null ? ArkEntityUtil.getOperator(mc.world,((BlockTrainingRoom.TileTrainingRoom) tile).getTrainer()).getName() : "null"), 101, 15, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_coach") + (ArkEntityUtil.getOperator(mc.world,((BlockTrainingRoom.TileTrainingRoom) tile).getCoach())!=null ? ArkEntityUtil.getOperator(mc.world,((BlockTrainingRoom.TileTrainingRoom) tile).getCoach()).getName() : "null"), 101, 25, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_hasCenter") + (((BlockTrainingRoom.TileTrainingRoom) tile).getCenter()!=null ? ((BlockTrainingRoom.TileTrainingRoom) tile).getCenter().getPos().getX()+","+((BlockTrainingRoom.TileTrainingRoom) tile).getCenter().getPos().getY()+","+((BlockTrainingRoom.TileTrainingRoom) tile).getCenter().getPos().getZ() : "false"), 101, 35, 0x3f3f3f);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        Arkworld.getNetwork().sendToServer(new PacketGuiButton(button.id));
    }
}
