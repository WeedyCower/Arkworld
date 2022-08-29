package com.weedycow.arkworld.item.normal;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.weedylib.util.StringUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public abstract class NormalItem extends Item
{
    public String id;
    public CreativeTabs tab;
    public int maxStackSize;

    public NormalItem(String id, CreativeTabs tab, int maxStackSize)
    {
        super();

        this.setCreativeTab(tab);
        this.setUnlocalizedName(Arkworld.MODID + "." + StringUtil.lineToHump(id));
        this.setRegistryName(id);
        this.setMaxStackSize(maxStackSize);

        this.id = id;
        this.tab = tab;
        this.maxStackSize = maxStackSize;
    }
}
