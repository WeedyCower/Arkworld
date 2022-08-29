package com.weedycow.arkworld.item.tool.ammo;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.item.tool.ItemArrow;

public class NormalArrow extends ItemArrow
{
    public NormalArrow()
    {
        super(com.weedycow.arkworld.entity.weapon.Arrow.Type.NORMAL_ARROW, ArkConfig.item.weapon.ammo.arrow.normalArrow.attackDamage);
        this.setUnlocalizedName(Arkworld.MODID + ".normalArrow");
        this.setRegistryName("normal_arrow");
        this.setMaxStackSize(64);
    }
}
