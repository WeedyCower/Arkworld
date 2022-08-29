package com.weedycow.arkworld.renderer;

import com.weedycow.arkworld.model.ModelItem;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import net.minecraft.item.Item;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

import java.util.Objects;

public class RenderItem extends GeoItemRenderer
{
    public RenderItem()
    {
        super(new ModelItem());
    }

    @Override
    public Integer getUniqueID(Item animatable)
    {
        return Objects.hash(this.currentItemStack.getItem(), this.currentItemStack.getCount(),
                    this.currentItemStack.hasCapability(CapabilityRegistry.capWeapon,null)
                        && this.currentItemStack.getCapability(CapabilityRegistry.capWeapon,null).getUuid()!=null ?
                            this.currentItemStack.getCapability(CapabilityRegistry.capWeapon,null).getUuid().toString() : 1);
    }
}
