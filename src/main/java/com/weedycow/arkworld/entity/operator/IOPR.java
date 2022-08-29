package com.weedycow.arkworld.entity.operator;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IOPR
{
    void commonExecute(Operator attacker, EntityLivingBase target);

    void closeExecute(Operator attacker, EntityLivingBase target);

    void remoteExecute(Operator attacker, EntityLivingBase target);

    Operator.Tag[] getTagsOperator();

    List<ItemStack> getEliteII();

    List<ItemStack> getEliteI();

    List<List<ItemStack>> getSluMaterial();

    List<Skill> getSkills();
}
