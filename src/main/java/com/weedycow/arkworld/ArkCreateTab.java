package com.weedycow.arkworld;

import com.weedycow.arkworld.registry.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArkCreateTab
{
    public static final CreativeTabs TOLL = new CreativeTabs("arkworld_tool")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ItemRegistry.INCANDESCENT_PICKAXE);
        }
    };

    public static final CreativeTabs OPERATOR = new CreativeTabs("arkworld_operator")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ItemRegistry.AMIYA_CARD);
        }
    };

    public static final CreativeTabs ITEM = new CreativeTabs("arkworld_item")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ItemRegistry.MY_INFO);
        }
    };

    public static final CreativeTabs BLOCK = new CreativeTabs("arkworld_block")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ItemRegistry.GRASS_FROZEN);
        }
    };

    public static final CreativeTabs CHIP = new CreativeTabs("arkworld_chip")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ItemRegistry.CASTER_CHIP);
        }
    };

    public static final CreativeTabs ARMOR = new CreativeTabs("arkworld_armor")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ItemRegistry.SKULLSHATTERER_ARMOR);
        }
    };

    public static final CreativeTabs DOLL = new CreativeTabs("arkworld_doll")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ItemRegistry.EVOLUTION_DOLL);
        }
    };

    public static final CreativeTabs WEAPON = new CreativeTabs("arkworld_weapon")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ItemRegistry.FAUST_CROSSBOW);
        }
    };

    public static final CreativeTabs MATERIAL = new CreativeTabs("arkworld_material")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ItemRegistry.SUGAR);
        }
    };

    public static final CreativeTabs DEVICE = new CreativeTabs("arkworld_device")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ItemRegistry.TRAINING_ROOM);
        }
    };

    public static final CreativeTabs FOOD = new CreativeTabs("arkworld_food")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ItemRegistry.TWO_LOAVES_WITH_CHEESE);
        }
    };
}
