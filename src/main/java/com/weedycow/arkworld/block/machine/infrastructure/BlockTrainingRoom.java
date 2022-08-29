package com.weedycow.arkworld.block.machine.infrastructure;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.item.material.infrastructure.ConcreteBuildingMaterial;
import com.weedycow.arkworld.item.material.infrastructure.LightBuildingMaterial;
import com.weedycow.arkworld.item.material.infrastructure.ReinforcedBuildingMaterial;
import com.weedycow.arkworld.registry.GuiRegistry;
import com.weedycow.arkworld.util.ArkEntityUtil;
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

public class BlockTrainingRoom extends BlockMachine
{
    public BlockTrainingRoom()
    {
        super(Type.TRAINING_ROOM);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("item.arkworld.info.operator_range")+machine.trainingRoom.operatorRange);

        tooltip.add(I18n.format("item.arkworld.info.operator_note"));
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
            playerIn.openGui(Arkworld.instance, GuiRegistry.TRAINING_ROOM, worldIn, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileTrainingRoom();
    }

    public static class TileTrainingRoom extends MachineTile
    {
        String trainer = "";
        String coach = "";
        protected ItemStackHandler slot = new ItemStackHandler(1);
        AnimationController<TileTrainingRoom> controllerIdle = new AnimationController<>(this, "idle", 1, this::PlayState);

        public TileTrainingRoom()
        {
            super(Type.TRAINING_ROOM.getName(),20,true);
            this.oDis = machine.trainingRoom.operatorRange;
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

            if(getLevel()==1)
                setPowerNeed(10);

            if(getLevel()==2)
                setPowerNeed(30);

            if(getLevel()==3)
                setPowerNeed(60);

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

            if(isHasCenter() && getCenter().isEnoughPower())
            {
                for (Operator operator : world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)).size() <= 2 ? world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)) : world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)).subList(0, 2))
                {
                    if (operator.getDistance(pos.getX(), pos.getY(), pos.getZ()) <= oDis)
                        operator.setRoom(Type.TRAINING_ROOM, this);

                    if (operator.getTrainTime() <= 0 && getCoach().equals("") && operator.getRoom() == this)
                        setCoach(operator.getUuid());

                    if (operator.getTrainTime() > 0 && getTrainer().equals("") && operator.getRoom() == this)
                        setTrainer(operator.getUuid());

                    Operator tra = ArkEntityUtil.getOperator(world,trainer);

                    Operator coa = ArkEntityUtil.getOperator(world,coach);

                    if (tra == null || tra.getTrainTime() <= 0 || tra.getRoom() != this) setTrainer("");

                    if (coa ==null || coa.getTrainTime() > 0 || coa.getRoom() != this) setCoach("");

                    if (tra != null)
                        operators.add(tra);

                    if (coa != null)
                        operators.add(coa);
                }

                if (!slot.getStackInSlot(0).isEmpty())
                {
                    if (getLevel() == 0 && slot.getStackInSlot(0).getItem() instanceof LightBuildingMaterial && slot.getStackInSlot(0).getCount() >= 2 && getCenter().getUav() >= 10)
                    {
                        setLevel(getLevel() + 1);
                        slot.getStackInSlot(0).shrink(2);
                        getCenter().setUav(getCenter().getUav() - 10);
                    }

                    if (getLevel() == 1 && slot.getStackInSlot(0).getItem() instanceof ConcreteBuildingMaterial && slot.getStackInSlot(0).getCount() >= 6 && getCenter().getUav() >= 60)
                    {
                        setLevel(getLevel() + 1);
                        slot.getStackInSlot(0).shrink(6);
                        getCenter().setUav(getCenter().getUav() - 60);
                    }

                    if (getLevel() == 2 && slot.getStackInSlot(0).getItem() instanceof ReinforcedBuildingMaterial && slot.getStackInSlot(0).getCount() >= 10 && getCenter().getUav() >= 120)
                    {
                        setLevel(getLevel() + 1);
                        slot.getStackInSlot(0).shrink(10);
                        getCenter().setUav(getCenter().getUav() - 120);
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
        public void readFromNBT(@Nonnull NBTTagCompound compound)
        {
            super.readFromNBT(compound);

            setTrainer(compound.getString("Trainer"));

            setCoach(compound.getString("Coach"));

            slot.deserializeNBT(compound.getCompoundTag("Slot"));
        }

        @Nonnull
        @Override
        public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
        {
            super.writeToNBT(compound);

            compound.setString("Trainer",getTrainer());

            compound.setString("Coach",getCoach());

            compound.setTag("Slot", slot.serializeNBT());

            return compound;
        }

        public void setCoach(String coach)
        {
            this.coach = coach;
        }

        public String getCoach()
        {
            return coach;
        }

        public void setTrainer(String trainer)
        {
            this.trainer = trainer;
        }

        public String getTrainer()
        {
            return trainer;
        }

        @Override
        public ResourceLocation getModelLocation()
        {
            return ArkResUtil.geo("normal_block");
        }
    }
}
