package com.weedycow.arkworld.item.block;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.weedylib.entity.IRES;
import com.weedycow.weedylib.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class ArkItemBlock extends ItemBlock implements IAnimatable, IRES
{
    public String id;
    protected final Block block;
    public final AnimationFactory factory = new AnimationFactory(this);

    public ArkItemBlock(Block block)
    {
        super(block);
        this.block = block;
        this.setId();
        this.setRegistryNames(id);
        this.setUnlocalizedName(Arkworld.MODID + "." + StringUtil.lineToHump(getRegistryName().getResourcePath()));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (!block.isReplaceable(worldIn, pos))
        {
            pos = pos.offset(facing);
        }

        ItemStack itemstack = player.getHeldItem(hand);

        if (!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(this.block, pos, false, facing, player))
        {
            int i = this.getMetadata(itemstack.getMetadata());
            IBlockState iblockstate1 = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);

            if (placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1))
            {
                iblockstate1 = worldIn.getBlockState(pos);
                SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
            }

            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }

    public void setId()
    {
        this.id = block.getRegistryName().getResourcePath();
    }

    public void setRegistryNames(String id)
    {
        this.setRegistryName(id);
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimationData animationData)
    {

    }

    public ResourceLocation getTextureLocation()
    {
        return ArkResUtil.textureBlocks(id);
    }

    public ResourceLocation getModelLocation()
    {
        return ArkResUtil.geo(id);
    }

    public ResourceLocation getAnimationLocation()
    {
        return ArkResUtil.animation(id);
    }
}
