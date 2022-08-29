package com.weedycow.arkworld.capability;

import com.weedycow.arkworld.registry.CapabilityRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class CapabilitySlotPlayer
{
	public static class Storage<T extends IItemHandlerModifiable> implements IStorage<IItemHandlerModifiable>
	{
		@Override
		public NBTBase writeNBT(Capability<IItemHandlerModifiable> capability, IItemHandlerModifiable instance, EnumFacing side)
		{
			NBTTagList nbtTagList = new NBTTagList();
			int size = instance.getSlots();
			for (int i = 0; i < size; i++)
			{
				ItemStack stack = instance.getStackInSlot(i);
				if (!stack.isEmpty())
				{
					NBTTagCompound itemTag = new NBTTagCompound();
					itemTag.setInteger("Slot", i);
					stack.writeToNBT(itemTag);
					nbtTagList.appendTag(itemTag);
				}
			}
			return nbtTagList;
		}

		@Override
		public void readNBT(Capability<IItemHandlerModifiable> capability, IItemHandlerModifiable instance, EnumFacing side, NBTBase base)
		{
			if (instance == null)
				throw new RuntimeException("IItemHandler instance does not implement IItemHandlerModifiable");
			NBTTagList tagList = (NBTTagList) base;
			for (int i = 0; i < tagList.tagCount(); i++)
			{
				NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
				int j = itemTags.getInteger("Slot");

				if (j >= 0 && j < instance.getSlots())
				{
					instance.setStackInSlot(j, new ItemStack(itemTags));
				}
			}
		}
	}

	public static class Provider implements INBTSerializable<NBTTagCompound>, ICapabilityProvider
	{
		private final ItemStackHandler handler;

		public Provider(ItemStackHandler container)
		{
			this.handler = container;
		}

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing)
		{
			return capability == CapabilityRegistry.capSlotPlayer;
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> T getCapability(Capability<T> capability, EnumFacing facing)
		{
			if (capability == CapabilityRegistry.capSlotPlayer)
				return (T) this.handler;
			else
				return null;
		}

		@Override
		public NBTTagCompound serializeNBT()
		{
			return this.handler.serializeNBT();
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt)
		{
			this.handler.deserializeNBT(nbt);
		}
	}
}
