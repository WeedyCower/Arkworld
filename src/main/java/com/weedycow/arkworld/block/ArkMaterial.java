package com.weedycow.arkworld.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class ArkMaterial extends Material
{
    public static final ArkMaterial TRAP = (ArkMaterial) (new ArkMaterial(MapColor.IRON)).setNoPushMobility();
    public static final ArkMaterial MACHINE = (ArkMaterial) (new ArkMaterial(MapColor.IRON)).setNoPushMobility();
    public static final ArkMaterial DOLL = (ArkMaterial) (new ArkMaterial(MapColor.IRON)).setRequiresTool();
    public static final ArkMaterial SEA_PLANT = (ArkMaterial) (new ArkMaterial(MapColor.GRASS)).setRequiresTool();

    public ArkMaterial(MapColor color)
    {
        super(color);
    }
}
