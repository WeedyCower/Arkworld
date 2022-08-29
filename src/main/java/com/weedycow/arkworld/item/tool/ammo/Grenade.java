package com.weedycow.arkworld.item.tool.ammo;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.item.tool.ItemBomb;

public class Grenade extends ItemBomb
{
    public Grenade()
    {
        super(com.weedycow.arkworld.entity.weapon.Bomb.Type.GRENADE, ArkConfig.item.weapon.ammo.bomb.grenade.damage, ArkConfig.item.weapon.ammo.bomb.grenade.destroyTerrain);
        this.setUnlocalizedName(Arkworld.MODID + ".grenade");
        this.setRegistryName("grenade");
        this.setMaxStackSize(64);
    }
}
