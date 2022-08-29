package com.weedycow.arkworld.item.tool.tool;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.common.util.EnumHelper;

public class OrironPickaxe extends ItemPickaxe
{
    public static final Item.ToolMaterial MATERIAL = EnumHelper.addToolMaterial("ORIRON", 3, 3000, 8, 4, 10);

    public OrironPickaxe()
    {
        super(MATERIAL);
        this.setUnlocalizedName(Arkworld.MODID + ".orironPickaxe");
        this.setCreativeTab(ArkCreateTab.TOLL);
        this.setRegistryName("oriron_pickaxe");
    }
}
