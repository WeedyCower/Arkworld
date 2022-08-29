package com.weedycow.arkworld.renderer;

import com.weedycow.arkworld.block.ArkTile;
import com.weedycow.arkworld.model.ModelArkTile;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderArkTile extends GeoBlockRenderer<ArkTile>
{
    public RenderArkTile()
    {
        super(new ModelArkTile());
    }
}
