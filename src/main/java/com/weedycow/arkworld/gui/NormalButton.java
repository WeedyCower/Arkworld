package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.operator.Operator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.ParametersAreNonnullByDefault;

public class NormalButton extends GuiButton
{
    String res;
    String text;
    ResourceLocation location;
    Operator operator;

    public NormalButton(int buttonId, String res, int x, int y, int widthIn, int heightIn, String text)
    {
        super(buttonId, x, y, widthIn, heightIn, text);
        this.res = res;
        this.text = text;
        this.location = new ResourceLocation(Arkworld.MODID+":textures/gui/"+res+".png");
        this.visible = true;
    }

    public NormalButton(int buttonId, String res, int x, int y, int widthIn, int heightIn, String text, Operator operator)
    {
        super(buttonId, x, y, widthIn, heightIn, text);
        this.res = res;
        this.text = text;
        this.location = new ResourceLocation(Arkworld.MODID+":textures/gui/"+res+".png");
        this.visible = true;
        this.operator = operator;
    }

    public boolean isOver()
    {
        if(operator!=null)
        {
            if(id==5 && res.equals("operator_move") && operator.getMoveMode()==Operator.MoveMode.IDLE)
                return true;
            if(id==6 && res.equals("operator_move") && operator.getMoveMode()==Operator.MoveMode.WANDER)
                return true;
            if(id==7 && res.equals("operator_move") && operator.getMoveMode()==Operator.MoveMode.FOLLOW)
                return true;
            if(id==8 && res.equals("operator_action") && operator.getActionMode()==Operator.ActionMode.NONE)
                return true;
            if(id==9 && res.equals("operator_action") && operator.getActionMode()==Operator.ActionMode.FIGHTBACK)
                return true;
            if(id==10 && res.equals("operator_action") && operator.getActionMode()==Operator.ActionMode.PROTECTIVE)
                return true;
            if(id==11 && res.equals("operator_action") && operator.getActionMode()==Operator.ActionMode.SIGN)
                return true;
            if(id==12 && res.equals("operator_action") && operator.getActionMode()==Operator.ActionMode.ENEMY)
                return true;
            if(id==13 && res.equals("operator_action") && operator.getActionMode()==Operator.ActionMode.MOB)
                return true;
            if(id==14 && res.equals("operator_action") && operator.getActionMode()==Operator.ActionMode.ALL)
                return true;
            if(id==15 && res.equals("operator_action") && operator.getActionMode()==Operator.ActionMode.WORK)
                return true;
            if(id==16 && res.equals("operator_sel") && operator.getSelectSkill()==1)
                return true;
            if(id==17 && res.equals("operator_sel") && operator.getSelectSkill()==2)
                return true;
            if(id==18 && res.equals("operator_sel") && operator.getSelectSkill()==3)
                return true;
            if(id==19 && res.equals("operator_action") && operator.getActionMode()==Operator.ActionMode.HEALING)
                return true;
        }
        return false;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if(this.visible)
        {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            mc.getTextureManager().bindTexture(location);
            drawModalRectWithCustomSizedTexture(x,y,0,0,width,height,width,height);
            if(isMouseOver() || isOver())
            {
                mc.getTextureManager().bindTexture(new ResourceLocation(Arkworld.MODID+":textures/gui/over.png"));
                drawModalRectWithCustomSizedTexture(x,y,0,0,width,height,width,height);
            }
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
            this.drawCenteredString(mc.fontRenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, 14737632);
        }
    }
}
