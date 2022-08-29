package com.weedycow.arkworld.renderer;

import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.model.ModelOperator;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderOperator extends GeoEntityRenderer<Operator>
{
    public RenderOperator(RenderManager renderManager)
    {
        super(renderManager, new ModelOperator());
    }

}
