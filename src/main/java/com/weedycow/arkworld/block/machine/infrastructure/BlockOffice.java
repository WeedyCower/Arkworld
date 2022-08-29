package com.weedycow.arkworld.block.machine.infrastructure;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.item.material.infrastructure.ConcreteBuildingMaterial;
import com.weedycow.arkworld.item.material.infrastructure.LightBuildingMaterial;
import com.weedycow.arkworld.item.material.infrastructure.ReinforcedBuildingMaterial;
import com.weedycow.arkworld.registry.GuiRegistry;
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
import net.minecraftforge.items.ItemStackHandler;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class BlockOffice extends BlockMachine
{
    public BlockOffice()
    {
        super(Type.OFFICE);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("item.arkworld.info.operator_range")+machine.office.operatorRange);

        tooltip.add(I18n.format("item.arkworld.info.operator_note"));
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
            playerIn.openGui(Arkworld.instance, GuiRegistry.OFFICE, worldIn, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileOffice();
    }

    public static class TileOffice extends MachineTile
    {
        int totalTime;
        int totalTimeReduce;
        int countdown;
        int point;
        float reduction;
        protected ItemStackHandler slot = new ItemStackHandler(1);
        AnimationController<TileOffice> controllerIdle = new AnimationController<>(this, "idle", 1, this::PlayState);

        public TileOffice()
        {
            super(Type.OFFICE.getName(),20,true);
            this.oDis = machine.office.operatorRange;
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

            if (!slot.getStackInSlot(0).isEmpty())
            {
                if (getCountdown() < getTotalTime() && getPoint() < 3)
                {
                    setCountdown(getCountdown() + 20);
                } else
                {
                    setCountdown(0);
                    setPoint(getPoint() + 1);
                }

                for (Operator operator : world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)).size() <= 1 ? world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)) : world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)).subList(0, 1))
                {
                    if (operator.getDistance(pos.getX(), pos.getY(), pos.getZ()) <= oDis)
                    {
                        if (operator.getRoom() == null)
                            operator.setRoom(Type.OFFICE, this);

                        if (operator.getRoom() == this)
                            operators.add(operator);
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
            }

            if(getLevel()==1)
                setPowerNeed(10);

            if(getLevel()==2)
                setPowerNeed(30);

            if(getLevel()==3)
                setPowerNeed(60);

            setTotalTime(36000-getTotalTimeReduce());

            setReduction(getLevel()*0.1f);
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

        public void setTotalTimeReduce(int totalTimeReduce)
        {
            this.totalTimeReduce = totalTimeReduce;
        }

        public int getTotalTimeReduce()
        {
            return totalTimeReduce;
        }

        public void setCountdown(int countdown)
        {
            this.countdown = countdown;
        }

        public int getCountdown()
        {
            return countdown;
        }

        public void setTotalTime(int totalTime)
        {
            this.totalTime = totalTime;
        }

        public int getTotalTime()
        {
            return totalTime;
        }

        public int getPoint()
        {
            return point;
        }

        public void setPoint(int point)
        {
            this.point = point;
        }

        public float getReduction()
        {
            return reduction;
        }

        public void setReduction(float reduction)
        {
            this.reduction = reduction;
        }

        @Override
        public void readFromNBT(NBTTagCompound compound)
        {
            super.readFromNBT(compound);

            slot.deserializeNBT(compound.getCompoundTag("Slot"));

            setCountdown(compound.getInteger("Countdown"));

            setPoint(compound.getInteger("Point"));

            setTotalTime(compound.getInteger("TotalTime"));

            setTotalTimeReduce(compound.getInteger("TotalTimeReduce"));

            setReduction(compound.getFloat("Reduction"));
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound compound)
        {
            super.writeToNBT(compound);

            compound.setTag("Slot", slot.serializeNBT());

            compound.setInteger("Countdown",getCountdown());

            compound.setInteger("Point",getPoint());

            compound.setInteger("TotalTime",getTotalTime());

            compound.setInteger("TotalTimeReduce",getTotalTimeReduce());

            compound.setFloat("Reduction",getReduction());

            return compound;
        }

        @Override
        public ResourceLocation getModelLocation()
        {
            return ArkResUtil.geo("normal_block");
        }
    }
}
