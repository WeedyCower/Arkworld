package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.entity.operator.Skill;
import com.weedycow.arkworld.networlk.PacketGuiButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

public class OperatorSecGuiContainer extends GuiContainer
{
    private float oldMouseX;
    private float oldMouseY;
    Operator operator;
    private static final ResourceLocation BG = new ResourceLocation(Arkworld.MODID +":textures/gui/operator_sec.png");

    public OperatorSecGuiContainer(Operator operator, Container inventorySlotsIn)
    {
        super(inventorySlotsIn);
        this.operator = operator;
        this.xSize = 301;
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
        buttonList.add(0,new NormalButton(0,"operator_button",guiLeft + 185,guiTop + 94,35,17, I18n.format("gui.arkworld.operator_levelup")));
        buttonList.add(1,new NormalButton(1,"operator_button",guiLeft + 221,guiTop + 94,35,17,I18n.format("gui.arkworld.operator_skillup")));
        buttonList.add(2,new NormalButton(2,"operator_button",guiLeft + 257,guiTop + 94,35,17,I18n.format("gui.arkworld.operator_proup")));
        buttonList.add(3,new NormalButton(3,"operator_page",guiLeft + 9,guiTop + 175,54,17,I18n.format("gui.arkworld.operator_back")));
        buttonList.add(4,new NormalButton(4,"operator_page",guiLeft + 69,guiTop + 175,54,17,I18n.format("gui.arkworld.operator_next")));
        buttonList.add(5,new NormalButton(5,"operator_move",guiLeft + 38,guiTop + 89,18,12,I18n.format("gui.arkworld.operator_idle"),operator));
        buttonList.add(6,new NormalButton(6,"operator_move",guiLeft + 59,guiTop + 89,18,12,I18n.format("gui.arkworld.operator_wander"),operator));
        buttonList.add(7,new NormalButton(7,"operator_move",guiLeft + 80,guiTop + 89,18,12,I18n.format("gui.arkworld.operator_follow"),operator));
        buttonList.add(8,new NormalButton(8,"operator_action",guiLeft + 38,guiTop + 122,18,13,I18n.format("gui.arkworld.operator_none"),operator));
        buttonList.add(9,new NormalButton(9,"operator_action",guiLeft + 59,guiTop + 122,18,13,I18n.format("gui.arkworld.operator_fightback"),operator));
        buttonList.add(10,new NormalButton(10,"operator_action",guiLeft + 80,guiTop + 122,18,13,I18n.format("gui.arkworld.operator_protective"),operator));
        buttonList.add(11,new NormalButton(11,"operator_action",guiLeft + 101,guiTop + 122,18,13,I18n.format("gui.arkworld.operator_sign"),operator));
        buttonList.add(12,new NormalButton(12,"operator_action",guiLeft + 38,guiTop + 137,18,13,I18n.format("gui.arkworld.operator_enemy"),operator));
        buttonList.add(13,new NormalButton(13,"operator_action",guiLeft + 59,guiTop + 137,18,13,I18n.format("gui.arkworld.operator_mob"),operator));
        buttonList.add(14,new NormalButton(14,"operator_action",guiLeft + 80,guiTop + 137,18,13,I18n.format("gui.arkworld.operator_all"),operator));
        buttonList.add(15,new NormalButton(15,"operator_action",guiLeft + 101,guiTop + 137,18,13,I18n.format("gui.arkworld.operator_work"),operator));
        buttonList.add(16,new NormalButton(16,"operator_sel",guiLeft + 269,guiTop + 15,17,16,"",operator));
        buttonList.add(17,new NormalButton(17,"operator_sel",guiLeft + 269,guiTop + 40,17,16,"",operator));
        buttonList.add(18,new NormalButton(18,"operator_sel",guiLeft + 269,guiTop + 65,17,16,"",operator));
        buttonList.add(19,new NormalButton(19,"operator_action",guiLeft + 38,guiTop + 152,18,12,I18n.format("gui.arkworld.operator_healing"),operator));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        Arkworld.getNetwork().sendToServer(new PacketGuiButton(button.id));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        if(operator.getGiftFir()!=null && !Objects.equals(operator.getGiftFir(), ""))
        {
            mc.fontRenderer.drawString(operator.getGiftFir().substring(0,9), 36, 11, 0x3f3f3f);

            if(operator.getGiftFir().length()>9 && operator.getGiftFir().length()<=18)
            {
                mc.fontRenderer.drawString(operator.getGiftFir().substring(9), 36, 19, 0x3f3f3f);
            }

            if(operator.getGiftFir().length()>18)
            {
                mc.fontRenderer.drawString(operator.getGiftFir().substring(9,18), 36, 19, 0x3f3f3f);
                mc.fontRenderer.drawString(operator.getGiftFir().substring(19), 36, 27, 0x3f3f3f);
            }
        }

        if(operator.getGiftSec()!=null && !Objects.equals(operator.getGiftSec(), ""))
        {
            mc.fontRenderer.drawString(operator.getGiftSec().substring(0,9), 36, 36, 0x3f3f3f);

            if(operator.getGiftSec().length()>9 && operator.getGiftSec().length()<=18)
            {
                mc.fontRenderer.drawString(operator.getGiftSec().substring(9), 36, 44, 0x3f3f3f);
            }

            if(operator.getGiftSec().length()>18)
            {
                mc.fontRenderer.drawString(operator.getGiftSec().substring(9,18), 36, 52, 0x3f3f3f);
                mc.fontRenderer.drawString(operator.getGiftSec().substring(19), 36, 27, 0x3f3f3f);
            }
        }

        if(operator.getLogistics()!=null && !Objects.equals(operator.getLogistics(), ""))
        {
            mc.fontRenderer.drawString(operator.getLogistics().substring(0,9), 36, 61, 0x3f3f3f);

            if(operator.getLogistics().length()>9 && operator.getLogistics().length()<=18)
            {
                mc.fontRenderer.drawString(operator.getLogistics().substring(9), 36, 69, 0x3f3f3f);
            }

            if(operator.getLogistics().length()>18)
            {
                mc.fontRenderer.drawString(operator.getLogistics().substring(9,18), 36, 69, 0x3f3f3f);
                mc.fontRenderer.drawString(operator.getLogistics().substring(18), 36, 77, 0x3f3f3f);
            }
        }

        if(operator.getSkills().size()>=1)
        {
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_skillName") + operator.getSkills().get(0).getName(), 158, 11, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_skillRe") + getRecoveryType(operator.getSkills().get(0).getRecoveryType()), 158, 19, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_skillTi") + getTriggerType(operator.getSkills().get(0).getTriggerType()), 213, 19, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_initPoint") + operator.getSkills().get(0).getInitPoint(), 158, 27, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_consumePoint") + operator.getSkills().get(0).getConsumePoint(), 195, 27, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_skillTime") + operator.getSkills().get(0).getTime()/20, 232, 27, 0x3f3f3f);
        }

        if(operator.getSkills().size()>=2)
        {
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_skillName") + operator.getSkills().get(1).getName(), 158, 11+25, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_skillRe") + getRecoveryType(operator.getSkills().get(1).getRecoveryType()), 158, 19+25, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_skillTi") + getTriggerType(operator.getSkills().get(1).getTriggerType()), 213, 19+25, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_initPoint") + operator.getSkills().get(1).getInitPoint(), 158, 27+25, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_consumePoint") + operator.getSkills().get(1).getConsumePoint(), 195, 27+25, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_skillTime") + operator.getSkills().get(1).getTime()/20, 232, 27+25, 0x3f3f3f);
        }

        if(operator.getSkills().size()>=3)
        {
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_skillName") + operator.getSkills().get(2).getName(), 158, 11+50, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_skillRe") + getRecoveryType(operator.getSkills().get(2).getRecoveryType()), 158, 19+50, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_skillTi") + getTriggerType(operator.getSkills().get(2).getTriggerType()), 213, 19+50, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_initPoint") + operator.getSkills().get(2).getInitPoint(), 158, 27+50, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_consumePoint") + operator.getSkills().get(2).getConsumePoint(), 195, 27+50, 0x3f3f3f);
            mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_skillTime") + operator.getSkills().get(2).getTime()/20, 232, 27+50, 0x3f3f3f);
        }
    }

    public String getTriggerType(Skill.TriggerType type)
    {
        if(type== Skill.TriggerType.AUTO)
            return I18n.format("gui.arkworld.operator_reAuto");
        if(type== Skill.TriggerType.MANUAL)
            return I18n.format("gui.arkworld.operator_manual");
        return "";
    }

    public String getRecoveryType(Skill.RecoveryType type)
    {
        if(type== Skill.RecoveryType.AUTO)
            return I18n.format("gui.arkworld.operator_reAuto");
        if(type== Skill.RecoveryType.ATTACK)
            return I18n.format("gui.arkworld.operator_reAtk");
        if(type== Skill.RecoveryType.INJURED)
            return I18n.format("gui.arkworld.operator_reInj");
        return "";
    }
}
