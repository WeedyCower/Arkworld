package com.weedycow.arkworld.block.machine.infrastructure;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.item.material.infrastructure.ConcreteBuildingMaterial;
import com.weedycow.arkworld.item.material.infrastructure.LightBuildingMaterial;
import com.weedycow.arkworld.item.material.infrastructure.ReinforcedBuildingMaterial;
import com.weedycow.arkworld.item.normal.Oricoal;
import com.weedycow.arkworld.registry.GuiRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.arkworld.world.data.MachineWorldSavedData;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BlockPowerStation extends BlockMachine
{
    public BlockPowerStation()
    {
        super(Type.POWER_STATION);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("item.arkworld.info.operator_range")+machine.powerStation.operatorRange);

        tooltip.add(I18n.format("item.arkworld.info.operator_note"));
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
            playerIn.openGui(Arkworld.instance, GuiRegistry.POWER_STATION, worldIn, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TilePowerStation();
    }

    public static class TilePowerStation extends MachineTile
    {
        int powerProduction;
        int countdown;
        int totalTime;
        int stack;
        float chargingAdd;
        float timeAdd;
        protected ItemStackHandler slot = new ItemStackHandler(2);
        List<ItemStack> stacks = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.ORIROCK,1)));
        AnimationController<TilePowerStation> controllerIdle = new AnimationController<>(this, "idle", 1, this::PlayState);

        public TilePowerStation()
        {
            super(Type.POWER_STATION.getName(),20,true);
            this.oDis = machine.powerStation.operatorRange;
        }

        private <E extends TileEntity & IAnimatable> PlayState PlayState(AnimationEvent<E> event)
        {
            return PlayState.CONTINUE;
        }

        @Override
        public void registerControllers(AnimationData data)
        {
            data.addAnimationController(controllerIdle);
        }

        @Override
        protected void intervalExecute()
        {
            operators.clear();

            setTimeAdd(1);

            if(isHasCenter())
            {
                if (getCountdown() > 0)
                    setCountdown(getCountdown() - 20);
                else
                    setCountdown(0);

                for (Operator operator : world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)).size() <= 1 ? world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)) : world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)).subList(0, 1))
                {
                    if (operator.getDistance(pos.getX(), pos.getY(), pos.getZ()) <= oDis)
                    {
                        if (operator.getRoom() == null)
                            operator.setRoom(Type.POWER_STATION, this);

                        if (operator.getRoom() == this)
                            operators.add(operator);
                    }
                }

                for(Operator operator : operators)
                {
                    setTimeAdd(1+(getTimeAdd()+operator.getLogSkillRatio()));
                }

                ItemStack input = slot.getStackInSlot(0);

                if (getCountdown() == 0 && stack != 0)
                {
                    slot.insertItem(1, stacks.get(stack - 1), false);
                    setStack(0);
                }

                if (input.getItem() instanceof Oricoal && getCountdown() == 0)
                {
                    if (input.getCount() >= 1)
                    {
                        setStack(1);

                        if (getLevel() == 1)
                        {
                            setTotalTime((int) (1200*getTimeAdd()));
                            setCountdown(getTotalTime());
                        }

                        if (getLevel() == 2)
                        {
                            setTotalTime((int) (1800*getTimeAdd()));
                            setCountdown(getTotalTime());
                        }

                        if (getLevel() == 3)
                        {
                            setTotalTime((int) (2400*getTimeAdd()));
                            setCountdown(getTotalTime());
                        }

                        input.shrink(1);
                    }
                }

                if (!slot.getStackInSlot(0).isEmpty())
                {
                    if (getLevel() == 0 && slot.getStackInSlot(0).getItem() instanceof LightBuildingMaterial && slot.getStackInSlot(0).getCount() >= 1 && getCenter().getUav()>=10)
                    {
                        setLevel(getLevel() + 1);
                        slot.getStackInSlot(0).shrink(1);
                        getCenter().setUav(getCenter().getUav()-10);
                    }

                    if (getLevel() == 1 && slot.getStackInSlot(0).getItem() instanceof ConcreteBuildingMaterial && slot.getStackInSlot(0).getCount() >= 3 && getCenter().getUav()>=60)
                    {
                        setLevel(getLevel() + 1);
                        slot.getStackInSlot(0).shrink(3);
                        getCenter().setUav(getCenter().getUav()-60);
                    }

                    if (getLevel() == 2 && slot.getStackInSlot(0).getItem() instanceof ReinforcedBuildingMaterial && slot.getStackInSlot(0).getCount() >= 5 && getCenter().getUav()>=120)
                    {
                        setLevel(getLevel() + 1);
                        slot.getStackInSlot(0).shrink(5);
                        getCenter().setUav(getCenter().getUav()-120);
                    }
                }
            }

            Iterator<BlockPos> b = MachineWorldSavedData.get(world).pos.listIterator();

            while (b.hasNext())
            {
                TileEntity tile = world.getTileEntity(b.next());

                if(tile instanceof MachineTile && tile!=this && !world.isRemote && Math.sqrt(tile.getDistanceSq(pos.getX(),pos.getY(),pos.getZ()))<((MachineTile) tile).getoDis()+getoDis())
                {
                    if(getTickExisted()>((MachineTile) tile).getTickExisted())
                    {
                        spawnAsEntity(world, tile.getPos(), new ItemStack(tile.getBlockType()), tile);
                        world.setBlockState(tile.getPos(), Blocks.AIR.getDefaultState());
                        b.remove();
                    }
                }
            }

            if(world.getTileEntity(pos.up())!=null && world.getTileEntity(pos.up()).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null))
            {
                IItemHandler handler = world.getTileEntity(pos.up()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

                for (int i = 0; i < handler.getSlots(); i++)
                {
                    if (!handler.getStackInSlot(i).isEmpty() && (slot.getStackInSlot(0) == ItemStack.EMPTY || (handler.getStackInSlot(i).getItem() == slot.getStackInSlot(0).getItem() && handler.getStackInSlot(i).getMetadata() == slot.getStackInSlot(0).getMetadata() && slot.getStackInSlot(0).getCount() < 64)))
                    {
                        ItemStack stack = handler.getStackInSlot(i).copy();
                        handler.getStackInSlot(i).shrink(64 - slot.getStackInSlot(i).getCount());
                        slot.insertItem(0, stack, false);
                    }
                }
            }

            if(world.getTileEntity(pos.down())!=null && world.getTileEntity(pos.down()).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null))
            {
                IItemHandler handler = world.getTileEntity(pos.down()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

                for (int i = 0; i < handler.getSlots(); i++)
                {
                    if (!slot.getStackInSlot(1).isEmpty() && (handler.getStackInSlot(i) == ItemStack.EMPTY  || (handler.getStackInSlot(i).getItem() == slot.getStackInSlot(1).getItem() && handler.getStackInSlot(i).getMetadata() == slot.getStackInSlot(1).getMetadata() && handler.getStackInSlot(i).getCount() < 64)))
                    {
                        ItemStack stack = slot.getStackInSlot(1).copy();
                        slot.getStackInSlot(1).shrink(64-handler.getStackInSlot(i).getCount());
                        handler.insertItem(i, stack, false);
                    }
                }
            }

            if(getCountdown()>0)
            {
                if(getLevel()==1)
                    setPowerProduction(60);
                if(getLevel()==2)
                    setPowerProduction(130);
                if(getLevel()==3)
                    setPowerProduction(270);
            }
            else
                setPowerProduction(0);
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
        {
            if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
            {
                return true;
            }
            return super.hasCapability(capability, facing);
        }

        @Nullable
        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
            {
                @SuppressWarnings("unchecked")
                T result = (T) slot;
                return result;
            }
            return super.getCapability(capability, facing);
        }

        @Override
        public void readFromNBT(NBTTagCompound compound)
        {
            super.readFromNBT(compound);

            slot.deserializeNBT(compound.getCompoundTag("Slot"));

            setCountdown(compound.getInteger("Countdown"));

            setTotalTime(compound.getInteger("TotalTime"));

            setStack(compound.getInteger("Stack"));

            setPowerProduction(compound.getInteger("PowerProduction"));

            setChargingAdd(compound.getInteger("ChargingAdd"));

            setTimeAdd(compound.getFloat("TimeAdd"));
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound compound)
        {
            super.writeToNBT(compound);

            compound.setTag("Slot", slot.serializeNBT());

            compound.setInteger("Countdown",getCountdown());

            compound.setInteger("TotalTime",getTotalTime());

            compound.setInteger("Stack",getStack());

            compound.setInteger("PowerProduction",getPowerProduction());

            compound.setFloat("ChargingAdd",getChargingAdd());

            compound.setFloat("TimeAdd",getTimeAdd());

            return compound;
        }

        public float getRate()
        {
            if(getCountdown()>0 && getTotalTime()>0)
                return (float) getCountdown()/getTotalTime();
            else return 1;
        }

        public void setPowerProduction(int powerProduction)
        {
            this.powerProduction = powerProduction;
        }

        public int getPowerProduction()
        {
            return powerProduction;
        }

        public void setMoodConsumeReduce(float moodConsumeReduce)
        {
            this.moodConsumeReduce = moodConsumeReduce;
        }

        public float getMoodConsumeReduce()
        {
            return moodConsumeReduce;
        }

        public void setChargingAdd(float chargingAdd)
        {
            this.chargingAdd = chargingAdd;
        }

        public float getChargingAdd()
        {
            return chargingAdd;
        }

        public int getCountdown()
        {
            return countdown;
        }

        public void setCountdown(int countdown)
        {
            this.countdown = countdown;
        }

        public void setTotalTime(int totalTime)
        {
            this.totalTime = totalTime;
        }

        public int getTotalTime()
        {
            return totalTime;
        }

        public void setStack(int stack)
        {
            this.stack = stack;
        }

        public int getStack()
        {
            return stack;
        }

        public void setTimeAdd(float timeAdd)
        {
            this.timeAdd = timeAdd;
        }

        public float getTimeAdd()
        {
            return timeAdd;
        }

        @Override
        public ResourceLocation getModelLocation()
        {
            return ArkResUtil.geo("normal_block");
        }
    }
}
