package com.weedycow.arkworld.item.tool.ammo;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.weapon.Arrow;
import com.weedycow.arkworld.item.tool.ItemArrow;

public class SignArrow extends ItemArrow
{
    public SignArrow()
    {
        super(Arrow.Type.SIGN_ARROW, 0);
        this.setUnlocalizedName(Arkworld.MODID + ".signArrow");
        this.setRegistryName("sign_arrow");
        this.setMaxStackSize(64);
    }
}
