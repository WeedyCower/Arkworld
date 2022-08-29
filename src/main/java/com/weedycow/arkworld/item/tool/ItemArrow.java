package com.weedycow.arkworld.item.tool;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.entity.weapon.Arrow;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.IRES;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class ItemArrow extends Item implements IAnimatable, IRES
{
    protected float damage;
    protected Arrow.Type type;
    AnimationFactory factory = new AnimationFactory(this);

    public ItemArrow(com.weedycow.arkworld.entity.weapon.Arrow.Type type, float damage)
    {
        this.type = type;
        this.damage = damage;
        this.setCreativeTab(ArkCreateTab.WEAPON);
    }

    public com.weedycow.arkworld.entity.weapon.Arrow createArrow(World worldIn, EntityLivingBase shooter)
    {
        return new com.weedycow.arkworld.entity.weapon.Arrow(worldIn,shooter,damage,type);
    }

    public float getDamage()
    {
        return damage;
    }

    public Arrow.Type getType()
    {
        return this.type;
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureEntities(type.toString());
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo(type.toString());
    }

    @Override
    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation(type.toString());
    }

    @Override
    public void registerControllers(AnimationData animationData)
    {

    }

    @Override
    public AnimationFactory getFactory()
    {
        return factory;
    }
}
