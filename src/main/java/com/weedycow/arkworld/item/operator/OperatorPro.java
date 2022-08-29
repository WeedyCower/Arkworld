package com.weedycow.arkworld.item.operator;

import com.weedycow.arkworld.item.normal.NormalItem;
import net.minecraft.creativetab.CreativeTabs;

public abstract class OperatorPro extends NormalItem
{
    String operatorId;

    public OperatorPro(String id, String operatorId, CreativeTabs tab, int maxStackSize)
    {
        super(id, tab, maxStackSize);
        this.operatorId = operatorId;
    }

    public String getOperatorId()
    {
        return operatorId;
    }
}
