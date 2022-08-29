package com.weedycow.arkworld.renderer;

import com.weedycow.arkworld.item.tool.ItemArrow;
import com.weedycow.arkworld.model.ModelArrow;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class RenderArrow extends GeoItemRenderer<ItemArrow>
{
    public RenderArrow()
    {
        super(new ModelArrow());
    }
}
