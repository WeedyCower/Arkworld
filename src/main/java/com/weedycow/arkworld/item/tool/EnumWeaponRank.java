package com.weedycow.arkworld.item.tool;

public enum EnumWeaponRank
{
    LOW(0),
    NORMAL(1),
    HIGH(2),
    SUPER(3),
    BEST(4),
    WTF(5);

    final int rank;

    EnumWeaponRank(int r)
    {
        this.rank = r;
    }
}
