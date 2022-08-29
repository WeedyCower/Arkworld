package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.machine.BlockWeaponTable;
import com.weedycow.arkworld.capability.CapabilityWeapon;
import com.weedycow.arkworld.item.normal.WeaponGem;
import com.weedycow.arkworld.item.tool.MeleeWeapon;
import com.weedycow.arkworld.item.tool.RangeWeapon;
import com.weedycow.arkworld.item.tool.ShieldWeapon;
import com.weedycow.arkworld.networlk.PacketGuiButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WeaponTableGuiContainer extends GuiContainer
{
    private float oldMouseX;
    private float oldMouseY;
    private TileEntity tile;
    private static final ResourceLocation BG = new ResourceLocation(Arkworld.MODID +":textures/gui/weapon_table.png");

    public WeaponTableGuiContainer(Container inventorySlotsIn, TileEntity tile)
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
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        if(tile instanceof BlockWeaponTable.TileWeaponTable)
        {
            ItemStack w = ((BlockWeaponTable.TileWeaponTable) tile).getSlot().getStackInSlot(0);
            ItemStack m = ((BlockWeaponTable.TileWeaponTable) tile).getSlot().getStackInSlot(1);

            if(w.getItem() instanceof MeleeWeapon || w.getItem() instanceof RangeWeapon || w.getItem() instanceof ShieldWeapon)
            {
                CapabilityWeapon.Process weapon = new CapabilityWeapon.Process(w);


                String q = "";
                if(weapon.getRank()==0)
                    q=I18n.format("item.arkworld.info.low");
                if(weapon.getRank()==1)
                    q = I18n.format("item.arkworld.info.normal");
                if(weapon.getRank()==2)
                    q = I18n.format("item.arkworld.info.high");
                if(weapon.getRank()==3)
                    q = I18n.format("item.arkworld.info.super");
                if(weapon.getRank()==4)
                    q = I18n.format("item.arkworld.info.best");
                if(weapon.getRank()==5)
                    q = I18n.format("item.arkworld.info.wtf");

                mc.fontRenderer.drawString(I18n.format("gui.arkworld.weapon_name") + w.getDisplayName(), 13, 14, 0x3f3f3f);
                mc.fontRenderer.drawString(I18n.format("gui.arkworld.weapon_rank") + q, 13, 24, 0x3f3f3f);
                mc.fontRenderer.drawString(I18n.format("gui.arkworld.weapon_level") + weapon.getLevel(), 13, 34, 0x3f3f3f);
                mc.fontRenderer.drawString(I18n.format("gui.arkworld.weapon_exp") + weapon.getExperience()+"/"+weapon.getExpNeed(), 13, 44, 0x3f3f3f);
                mc.fontRenderer.drawString(I18n.format("gui.arkworld.weapon_dur") + weapon.getLoss()+"/"+weapon.getDurability(), 13, 54, 0x3f3f3f);
                mc.fontRenderer.drawString(I18n.format("gui.arkworld.weapon_mana") + weapon.getMana(), 13, 64, 0x3f3f3f);
                mc.fontRenderer.drawString(I18n.format("gui.arkworld.weapon_stamina") + weapon.getStamina(), 93, 14, 0x3f3f3f);
                mc.fontRenderer.drawString(I18n.format("gui.arkworld.weapon_sam") + weapon.getSam(), 93, 24, 0x3f3f3f);

                if(w.getItem() instanceof MeleeWeapon || w.getItem() instanceof RangeWeapon || w.getItem() instanceof ShieldWeapon || m.getItem() instanceof WeaponGem)
                {
                    CapabilityWeapon.Process mat = new CapabilityWeapon.Process(((BlockWeaponTable.TileWeaponTable) tile).getSlot().getStackInSlot(1));
                    mc.fontRenderer.drawString(I18n.format("gui.arkworld.weapon_mat") + mat.getMaterialExperience(), 93, 34, 0x3f3f3f);
                }
            }
        }
    }

    @Override
    public void initGui()
    {
        super.initGui();

        buttonList.add(0,new NormalButton(0,"blank",guiLeft+60,guiTop+92,72,3, ""));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        Arkworld.getNetwork().sendToServer(new PacketGuiButton(button.id));
    }
}
