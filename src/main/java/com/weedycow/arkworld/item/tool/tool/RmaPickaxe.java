package com.weedycow.arkworld.item.tool.tool;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.common.util.EnumHelper;

public class RmaPickaxe extends ItemPickaxe
{
    public static final Item.ToolMaterial MATERIAL = EnumHelper.addToolMaterial("RMA", 3, 1500, 12, 4, 8);

    public RmaPickaxe()
    {
        super(MATERIAL);
        this.setUnlocalizedName(Arkworld.MODID + ".rmaPickaxe");
        this.setCreativeTab(ArkCreateTab.TOLL);
        this.setRegistryName("rma_pickaxe");
    }
}
