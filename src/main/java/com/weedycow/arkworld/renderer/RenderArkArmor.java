package com.weedycow.arkworld.renderer;

import com.weedycow.arkworld.item.armor.ArkArmor;
import com.weedycow.arkworld.model.ModelArkArmor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class RenderArkArmor extends GeoArmorRenderer<ArkArmor>
{
    public RenderArkArmor()
    {
        super(new ModelArkArmor());
        this.headBone = "head";
        this.bodyBone = "body";
        this.rightArmBone = "right_arm";
        this.leftArmBone = "left_arm";
        this.rightLegBone = "right_leg";
        this.leftLegBone = "left_leg";
        this.rightBootBone = "right_boot";
        this.leftBootBone = "left_boot";
    }
}
