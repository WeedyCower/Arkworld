package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.networlk.PacketGuiButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;
import java.util.Arrays;

public class OperatorFirGuiContainer extends GuiContainer
{
    private float oldMouseX;
    private float oldMouseY;
    Operator operator;
    private static final ResourceLocation BG = new ResourceLocation(Arkworld.MODID +":textures/gui/operator_fir.png");

    public OperatorFirGuiContainer(Operator operator, Container inventorySlotsIn)
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
        GuiInventory.drawEntityOnScreen(guiLeft + 66, guiTop + 140, 50, (float)(guiLeft + 66) - this.oldMouseX, (float)(guiTop + 140 - 50) - this.oldMouseY, operator);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.add(0,new NormalButton(0,"operator_button",guiLeft + 185,guiTop + 94,35,17,I18n.format("gui.arkworld.operator_levelup")));
        buttonList.add(1,new NormalButton(1,"operator_button",guiLeft + 221,guiTop + 94,35,17,I18n.format("gui.arkworld.operator_skillup")));
        buttonList.add(2,new NormalButton(2,"operator_button",guiLeft + 257,guiTop + 94,35,17,I18n.format("gui.arkworld.operator_proup")));
        buttonList.add(3,new NormalButton(3,"operator_page",guiLeft + 9,guiTop + 175,54,17,I18n.format("gui.arkworld.operator_back")));
        buttonList.add(4,new NormalButton(4,"operator_page",guiLeft + 69,guiTop + 175,54,17,I18n.format("gui.arkworld.operator_next")));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        Arkworld.getNetwork().sendToServer(new PacketGuiButton(button.id));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        DecimalFormat df =new DecimalFormat("0.00");

        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_name")+operator.getName(),152,12,0x3f3f3f);
        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_pro")+getClassName(operator.getOperatorClass()),152,21,0x3f3f3f);
        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_rare")+getRare(operator.getRarity()),152,30,0x3f3f3f);
        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_tag")+getTags(operator.getTagsOperator()),152,39,0x3f3f3f);
        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_hp")+df.format(operator.getMaxHealth()),152,49,0x3f3f3f);
        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_atk")+df.format(operator.getAttackDamage()),152,58,0x3f3f3f);
        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_df")+df.format(operator.getDefensivePower()),152,67,0x3f3f3f);
        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_sr")+df.format(operator.getSpellResistance()*100),152,76,0x3f3f3f);
        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_lv")+operator.getLevel(),222,12,0x3f3f3f);
        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_exp")+operator.getExp()+"/"+operator.getExpNeed(),222,21,0x3f3f3f);
        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_bl")+operator.getTrust(),222,30,0x3f3f3f);
        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_pt")+operator.getPotential(),222,39,0x3f3f3f);
        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_el")+operator.getElite(),222,49,0x3f3f3f);
        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_sl")+operator.getSkillLevel(),222,58,0x3f3f3f);
        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_dp")+operator.getDeployPoint(),222,67,0x3f3f3f);
        mc.fontRenderer.drawString(I18n.format("gui.arkworld.operator_ti")+operator.getRedeployTime()/20,222,76,0x3f3f3f);
    }

    public String getTags(Operator.Tag[] tags)
    {
        return Arrays.toString(tags).replace("HEALING",I18n.format("gui.arkworld.operator_healing"))
                .replace("SUPPORT",I18n.format("gui.arkworld.operator_support"))
                .replace("DPS",I18n.format("gui.arkworld.operator_dps"))
                .replace("AOE",I18n.format("gui.arkworld.operator_aoe"))
                .replace("SLOW",I18n.format("gui.arkworld.operator_slow"))
                .replace("SURVIVAL",I18n.format("gui.arkworld.operator_survival"))
                .replace("WEAKEN",I18n.format("gui.arkworld.operator_weaken"))
                .replace("SHIFT",I18n.format("gui.arkworld.operator_shift"))
                .replace("CROWD_CONTROL",I18n.format("gui.arkworld.operator_crowd_control"))
                .replace("NUKER",I18n.format("gui.arkworld.operator_nuker"))
                .replace("SUMMON",I18n.format("gui.arkworld.operator_summon"))
                .replace("FAST_REDEPLOY",I18n.format("gui.arkworld.operator_fast_redeploy"))
                .replace("DP_RECOVERY",I18n.format("gui.arkworld.operator_recoveryDp"))
                .replace("ROBOT",I18n.format("gui.arkworld.operator_robot"))
                .replace("MELEE",I18n.format("gui.arkworld.melee"))
                .replace("RANGE",I18n.format("gui.arkworld.range"))
                .replace("[","")
                .replace("]","")
                .replace(",","，")
                .replace(" ","");
    }

    public String getRare(Operator.Rarity rarity)
    {
        String star = I18n.format("gui.arkworld.operator_star");
        if(rarity== Operator.Rarity.I)
            return star;
        if(rarity== Operator.Rarity.II)
            return star+star;
        if(rarity== Operator.Rarity.III)
            return star+star+star;
        if(rarity== Operator.Rarity.IV)
            return star+star+star+star;
        if(rarity== Operator.Rarity.V)
            return star+star+star+star+star;
        if(rarity== Operator.Rarity.VI)
            return star+star+star+star+star+star;
        if(rarity== Operator.Rarity.VII)
            return star+star+star+star+star+star+star;
        return "";
    }

    public String getClassName(Operator.Class c)
    {
        if(c== Operator.Class.CASTER)
            return I18n.format("gui.arkworld.operator_caster");
        if(c== Operator.Class.DEFENDER)
            return I18n.format("gui.arkworld.operator_defender");
        if(c== Operator.Class.GUARD)
            return I18n.format("gui.arkworld.operator_guard");
        if(c== Operator.Class.MEDIC)
            return I18n.format("gui.arkworld.operator_medic");
        if(c== Operator.Class.SNIPER)
            return I18n.format("gui.arkworld.operator_sniper");
        if(c== Operator.Class.SPECIALIST)
            return I18n.format("gui.arkworld.operator_specialist");
        if(c== Operator.Class.SUPPORTER)
            return I18n.format("gui.arkworld.operator_supporter");
        if(c== Operator.Class.VANGUARD)
            return I18n.format("gui.arkworld.operator_vanguard");
        return "";
    }
}
