package com.weedycow.arkworld.block.machine.infrastructure;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.RecipeTable;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.item.normal.Lmb;
import com.weedycow.arkworld.registry.GuiRegistry;
import com.weedycow.arkworld.util.ArkItemUtil;
import com.weedycow.arkworld.util.ArkResUtil;
import com.weedycow.arkworld.world.data.MachineWorldSavedData;
import com.weedycow.weedylib.util.ListUtil;
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
import java.util.stream.Collectors;

public class BlockManufacturingStation extends BlockMachine
{
    public BlockManufacturingStation()
    {
        super(Type.MANUFACTURING_STATION);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("item.arkworld.info.operator_range")+machine.manufacturingStation.operatorRange);

        tooltip.add(I18n.format("item.arkworld.info.operator_note"));
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
            playerIn.openGui(Arkworld.instance, GuiRegistry.MANUFACTURING_STATION, worldIn, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileManufacturingStation();
    }

    public static class TileManufacturingStation extends MachineTile
    {
        int stack;
        int countdown;
        int totalTime;
        float productivityAdd;
        float moodConsumeReduce;
        List<ItemStack> oriStacks;
        List<Integer> linkSer;
        List<ItemStack> adjStacks;
        protected ItemStackHandler slot = new ItemStackHandler(3);
        AnimationController<TileManufacturingStation> controllerIdle = new AnimationController<>(this, "idle", 1, this::PlayState);

        public TileManufacturingStation()
        {
            super(Type.MANUFACTURING_STATION.getName(),20,true);
            this.oDis = machine.manufacturingStation.operatorRange;
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

            if(getLevel()==1) setPowerNeed(10);

            if(getLevel()==2) setPowerNeed(30);

            if(getLevel()==3) setPowerNeed(60);

            setProductivityAdd(1);

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

            for(Operator operator : operators)
            {
                setProductivityAdd((1-(getProductivityAdd()+operator.getLogSkillRatio())));
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
                            operator.setRoom(Type.MANUFACTURING_STATION, this);

                        if (operator.getRoom() == this)
                            operators.add(operator);
                    }
                }

                if (getCountdown() == 0 && stack != 0)
                {
                    ItemStack sta = RecipeTable.MANUFACTURING_RECIPE.get(stack - 1).get(2).copy();
                    slot.insertItem(2, sta, false);
                    setStack(0);
                }

                this.oriStacks = new ArrayList<>(Arrays.asList(slot.getStackInSlot(0), slot.getStackInSlot(1)));

                this.adjStacks = new ArrayList<>(Arrays.asList(ItemStack.EMPTY, ItemStack.EMPTY));

                this.linkSer = new ArrayList<>(Arrays.asList(0, 0));

                if ((slot.getStackInSlot(0) != ItemStack.EMPTY || slot.getStackInSlot(1) != ItemStack.EMPTY) && getCountdown() == 0)
                {
                    for (List<ItemStack> s : RecipeTable.MANUFACTURING_RECIPE)
                    {
                        List<ItemStack> table = s.subList(0, s.size() - 2);

                        if (ArkItemUtil.isEqualSize(oriStacks,table) && ListUtil.isListEqual(table.stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList()), oriStacks.stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList())))
                        {
                            for (int j = 0; j < 2; j++)
                            {
                                for (int i = 0; i < 2; i++)
                                {
                                    if (oriStacks.get(j).getUnlocalizedName().equals(table.get(i).getUnlocalizedName()))
                                    {
                                        this.adjStacks.set(i, oriStacks.get(j));
                                        this.linkSer.set(j, i);
                                    }
                                }

                                int n1 = table.get(0).getCount() > 0 ? adjStacks.get(0).getCount() / table.get(0).getCount() : 0;

                                int n2;

                                if (table.get(1) == ItemStack.EMPTY && adjStacks.get(1) == ItemStack.EMPTY) n2 = 1;
                                else n2 = table.get(1).getCount() > 0 ? adjStacks.get(1).getCount() / table.get(1).getCount() : 0;

                                List<Integer> ns = new ArrayList<>(Arrays.asList(n1, n2));

                                int min;

                                if (!ns.contains(0))
                                    min = ListUtil.getMin(ns.stream().mapToInt(Integer::intValue).toArray());
                                else
                                    min = 0;

                                if (min > 0)
                                {
                                    if (s.get(2).getItem() instanceof Lmb)
                                    {
                                        if (getLevel() == s.get(2).getCount() - 1 && getCenter().getUav()>=s.get(3).getCount())
                                        {
                                            setLevel(getLevel() + 1);

                                            getCenter().setUav(getCenter().getUav()-s.get(3).getCount());

                                            slot.getStackInSlot(0).shrink(table.get(linkSer.get(0)).getCount());

                                            slot.getStackInSlot(1).shrink(table.get(linkSer.get(1)).getCount());

                                            break;
                                        }
                                    }
                                    else
                                    {
                                        slot.getStackInSlot(0).shrink(table.get(linkSer.get(0)).getCount());

                                        slot.getStackInSlot(1).shrink(table.get(linkSer.get(1)).getCount());

                                        setTotalTime((int) (s.get(3).getCount() * 1200 * getProductivityAdd()));

                                        setCountdown(getTotalTime());

                                        stack = RecipeTable.MANUFACTURING_RECIPE.indexOf(s) + 1;

                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if(world.getTileEntity(pos.up())!=null && world.getTileEntity(pos.up()).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null))
            {
                IItemHandler handler = world.getTileEntity(pos.up()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);

                for (int j = 0; j < 2; j++)
                {
                    for (int i = 0; i < handler.getSlots(); i++)
                    {
                        if (!handler.getStackInSlot(i).isEmpty() && (slot.getStackInSlot(j) == ItemStack.EMPTY || (handler.getStackInSlot(i).getItem() == slot.getStackInSlot(j).getItem() && handler.getStackInSlot(i).getMetadata() == slot.getStackInSlot(j).getMetadata() && slot.getStackInSlot(j).getCount() < 64)))
                        {
                            ItemStack stack = handler.getStackInSlot(i).copy();
                            handler.getStackInSlot(i).shrink(64-slot.getStackInSlot(i).getCount());
                            slot.insertItem(j, stack, false);
                        }
                    }
                }
            }

            if(world.getTileEntity(pos.down())!=null && world.getTileEntity(pos.down()).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null))
            {
                IItemHandler handler = world.getTileEntity(pos.down()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

                for (int i = 0; i < handler.getSlots(); i++)
                {
                    if (!slot.getStackInSlot(2).isEmpty() && (handler.getStackInSlot(i) == ItemStack.EMPTY  || (handler.getStackInSlot(i).getItem() == slot.getStackInSlot(2).getItem() && handler.getStackInSlot(i).getMetadata() == slot.getStackInSlot(2).getMetadata() && handler.getStackInSlot(i).getCount() < 64)))
                    {
                        ItemStack stack = slot.getStackInSlot(2).copy();
                        slot.getStackInSlot(2).shrink(64-handler.getStackInSlot(i).getCount());
                        handler.insertItem(i, stack, false);
                    }
                }
            }
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

            setProductivityAdd(compound.getFloat("ProductivityAdd"));

            setMoodConsumeReduce(compound.getFloat("MoodConsumeReduce"));
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound compound)
        {
            super.writeToNBT(compound);

            compound.setTag("Slot", slot.serializeNBT());

            compound.setInteger("Countdown",getCountdown());

            compound.setInteger("TotalTime",getTotalTime());

            compound.setInteger("Stack",getStack());

            compound.setFloat("ProductivityAdd",getProductivityAdd());

            compound.setFloat("MoodConsumeReduce",getMoodConsumeReduce());

            return compound;
        }

        public float getRate()
        {
            if(getCountdown()>0 && getTotalTime()>0)
                return (float) getCountdown()/getTotalTime();
            else return 1;
        }

        public void setMoodConsumeReduce(float moodConsumeReduce)
        {
            this.moodConsumeReduce = moodConsumeReduce;
        }

        public float getMoodConsumeReduce()
        {
            return moodConsumeReduce;
        }

        public void setProductivityAdd(float productivityAdd)
        {
            this.productivityAdd = productivityAdd;
        }

        public float getProductivityAdd()
        {
            return productivityAdd;
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
