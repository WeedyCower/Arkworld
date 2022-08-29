package com.weedycow.arkworld.item.normal;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityValue;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class OrundumTwoHundred extends Item
{
	public OrundumTwoHundred()	
	{
		this.setUnlocalizedName(Arkworld.MODID + ".orundumTwohundred");
		this.setCreativeTab(ArkCreateTab.ITEM);
		this.setRegistryName("orundum_twohundred");
		this.setMaxStackSize(64);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);

		CapabilityValue.Process value = new CapabilityValue.Process(playerIn);

		if(stack.getItem()==this)
		{
			value.addOrundum(200);

			stack.shrink(1);
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
