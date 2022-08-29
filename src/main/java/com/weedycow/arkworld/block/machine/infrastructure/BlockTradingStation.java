package com.weedycow.arkworld.block.machine.infrastructure;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.item.material.gold.PureGold;
import com.weedycow.arkworld.item.material.infrastructure.ConcreteBuildingMaterial;
import com.weedycow.arkworld.item.material.infrastructure.LightBuildingMaterial;
import com.weedycow.arkworld.item.material.infrastructure.ReinforcedBuildingMaterial;
import com.weedycow.arkworld.item.normal.OriginiumShard;
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

public class BlockTradingStation extends BlockMachine
{
    public BlockTradingStation()
    {
        super(Type.TRADING_STATION);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("item.arkworld.info.operator_range")+machine.tradingStation.operatorRange);

        tooltip.add(I18n.format("item.arkworld.info.operator_note"));
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
            playerIn.openGui(Arkworld.instance, GuiRegistry.TRADING_STATION, worldIn, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileTradingStation();
    }

    public static class TileTradingStation extends MachineTile
    {
        int powerNeed;
        int countdown;
        int totalTime;
        int stack;
        float efficiencyAdd;
        float moodConsumeReduce;
        protected ItemStackHandler slot = new ItemStackHandler(2);
        List<ItemStack> stacks = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.LMB,50),new ItemStack(ItemRegistry.LMB,64),new ItemStack(ItemRegistry.LMB_HUNDRED),new ItemStack(ItemRegistry.ORUNDUM)));
        AnimationController<TileTradingStation> controllerIdle = new AnimationController<>(this, "idle", 1, this::PlayState);

        public TileTradingStation()
        {
            super(Type.TRADING_STATION.getName(),20,true);
            this.oDis = machine.tradingStation.operatorRange;
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

            setEfficiencyAdd(0);

            //中枢基建的技能加成
            if(getCenter()!=null)
            {
                for(Operator operator : getCenter().getOperators())
                {
                    setEfficiencyAdd(getEfficiencyAdd()+operator.getLogSkillRatio());
                }
            }

            //基建的技能加成
            if(getOperators().size()>0)
            {
                for (Operator operator : getOperators())
                {
                    setEfficiencyAdd(getEfficiencyAdd() + operator.getLogSkillRatio());
                }
            }

            if(isHasCenter() && getCenter().isEnoughPower())
            {
                if (getCountdown() > 0)
                    setCountdown(getCountdown() - 20);
                else
                    setCountdown(0);

                for (Operator operator : world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)).size() <= 3 ? world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)) : world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)).subList(0, 3))
                {
                    if (operator.getDistance(pos.getX(), pos.getY(), pos.getZ()) <= oDis)
                    {
                        if (operator.getRoom() == null)
                            operator.setRoom(Type.TRADING_STATION, this);

                        if (operator.getRoom() == this)
                            operators.add(operator);
                    }
                }

                ItemStack input = slot.getStackInSlot(0);

                if (getCountdown() == 0 && stack != 0)
                {
                    slot.insertItem(1, stacks.get(stack - 1), false);
                    setStack(0);
                }

                if (input.getItem() instanceof OriginiumShard && getCountdown() == 0)
                {
                    if (input.getCount()>=1 && getLevel() == 3)
                    {
                        setStack(4);
                        setTotalTime((int) (3600 * (1 - getEfficiencyAdd())));
                        setCountdown(getTotalTime());
                        input.shrink(1);
                    }
                }

                if (input.getItem() instanceof PureGold && getCountdown() == 0)
                {
                    if (input.getCount() == 2)
                    {
                        if (getLevel() != 0)
                        {
                            setStack(1);
                            setTotalTime((int) (2160*(1-getEfficiencyAdd())));
                            setCountdown(getTotalTime());
                            input.shrink(2);
                        }
                    }

                    if (input.getCount() == 3)
                    {
                        if (getLevel() == 1)
                        {
                            setStack(1);
                            setTotalTime((int) (2160*(1-getEfficiencyAdd())));
                            setCountdown(getTotalTime());
                            input.shrink(3);
                        }

                        if (getLevel() == 2)
                        {
                            if (Math.random() > 0.7)
                            {
                                setStack(3);
                                setTotalTime((int) (6000*(1-getEfficiencyAdd())));
                                setCountdown(getTotalTime());
                                input.shrink(3);
                            }
                            else
                            {
                                setStack(2);
                                setTotalTime((int) (4320*(1-getEfficiencyAdd())));
                                setCountdown(getTotalTime());
                                input.shrink(3);
                            }
                        }

                        if (getLevel() == 3)
                        {
                            if (Math.random() > 0.5)
                            {
                                setStack(3);
                                setTotalTime((int) (6000*(1-getEfficiencyAdd())));
                                setCountdown(getTotalTime());
                                input.shrink(3);
                            }
                            else
                            {
                                setStack(2);
                                setTotalTime((int) (4320*(1-getEfficiencyAdd())));
                                setCountdown(getTotalTime());
                                input.shrink(3);
                            }
                        }
                    }

                    if (input.getCount() >= 4)
                    {
                        if (getLevel() == 1)
                        {
                            setStack(1);
                            setTotalTime((int) (2160*(1-getEfficiencyAdd())));
                            setCountdown(getTotalTime());
                            input.shrink(4);
                        }

                        if (getLevel() == 2)
                        {
                            if (Math.random() > 0.7)
                            {
                                setStack(3);
                                setTotalTime((int) (6000*(1-getEfficiencyAdd())));
                                setCountdown(getTotalTime());
                                input.shrink(4);
                            }
                            else
                            {
                                setStack(2);
                                setTotalTime((int) (4320*(1-getEfficiencyAdd())));
                                setCountdown(getTotalTime());
                                input.shrink(4);
                            }
                        }

                        if (getLevel() == 3)
                        {
                            double r = Math.random();

                            if (r > 0.8)
                            {
                                setStack(3);
                                setTotalTime((int) (6000*(1-getEfficiencyAdd())));
                                setCountdown(getTotalTime());
                                input.shrink(4);
                            }
                            else if (r > 0.3)
                            {
                                setStack(2);
                                setTotalTime((int) (4320*(1-getEfficiencyAdd())));
                                setCountdown(getTotalTime());
                                input.shrink(4);
                            }
                            else
                            {
                                setStack(1);
                                setTotalTime((int) (2160*(1-getEfficiencyAdd())));
                                setCountdown(getTotalTime());
                                input.shrink(4);
                            }
                        }
                    }
                }

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

            if(getLevel()==1) setPowerNeed(10);

            if(getLevel()==2) setPowerNeed(30);

            if(getLevel()==3) setPowerNeed(60);
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

            setPowerNeed(compound.getInteger("PowerNeed"));

            setMoodConsumeReduce(compound.getFloat("MoodConsumeReduce"));

            setEfficiencyAdd(compound.getFloat("EfficiencyAdd"));
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound compound)
        {
            super.writeToNBT(compound);

            compound.setTag("Slot", slot.serializeNBT());

            compound.setInteger("Countdown",getCountdown());

            compound.setInteger("TotalTime",getTotalTime());

            compound.setInteger("Stack",getStack());

            compound.setInteger("PowerNeed",getPowerNeed());

            compound.setFloat("MoodConsumeReduce",getMoodConsumeReduce());

            compound.setFloat("EfficiencyAdd",getEfficiencyAdd());

            return compound;
        }

        public float getRate()
        {
            if(getCountdown()>0 && getTotalTime()>0)
                return (float) getCountdown()/getTotalTime();
            else return 1;
        }

        public void setPowerNeed(int powerNeed)
        {
            this.powerNeed = powerNeed;
        }

        public int getPowerNeed()
        {
            return powerNeed;
        }

        public void setMoodConsumeReduce(float moodConsumeReduce)
        {
            this.moodConsumeReduce = moodConsumeReduce;
        }

        public float getMoodConsumeReduce()
        {
            return moodConsumeReduce;
        }

        public void setEfficiencyAdd(float efficiencyAdd)
        {
            this.efficiencyAdd = efficiencyAdd;
        }

        public float getEfficiencyAdd()
        {
            return efficiencyAdd;
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

        @Override
        public ResourceLocation getModelLocation()
        {
            return ArkResUtil.geo("normal_block");
        }
    }
}
