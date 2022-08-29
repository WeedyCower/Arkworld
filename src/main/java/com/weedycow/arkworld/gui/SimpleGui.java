package com.weedycow.arkworld.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;

public class SimpleGui extends Gui
{
    protected Minecraft mc;
    protected Entity entity;

    public SimpleGui()
    {
        this.mc = Minecraft.getMinecraft();
        this.entity = mc.player;
    }

    public void render(int x)
    {

    }
}
