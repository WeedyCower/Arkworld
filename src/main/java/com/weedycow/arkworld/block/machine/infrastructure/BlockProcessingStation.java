package com.weedycow.arkworld.block.machine.infrastructure;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.entity.operator.Operator;
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

public class BlockProcessingStation extends BlockMachine
{
    public BlockProcessingStation()
    {
        super(Type.PROCESSING_STATION);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("item.arkworld.info.operator_range")+machine.processingStation.operatorRange);

        tooltip.add(I18n.format("item.arkworld.info.operator_note"));
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
            playerIn.openGui(Arkworld.instance, GuiRegistry.PROCESSING_STATION, worldIn, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileProcessingStation();
    }

    public static class TileProcessingStation extends MachineTile
    {
        protected ItemStackHandler slot = new ItemStackHandler(4);

        AnimationController<TileProcessingStation> controllerIdle = new AnimationController<>(this, "idle", 1, this::PlayState);

        public TileProcessingStation()
        {
            super(Type.PROCESSING_STATION.getName(),20,true);
            this.oDis=machine.processingStation.operatorRange;
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

            for(Operator operator : world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)).size()<=1 ? world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)) : world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)).subList(0,1))
            {
                if(operator.getDistance(pos.getX(),pos.getY(),pos.getZ())<=oDis)
                {
                    if(operator.getRoom()==null)
                        operator.setRoom(Type.PROCESSING_STATION,this);

                    if(operator.getRoom()==this)
                        operators.add(operator);
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

            this.slot.deserializeNBT(compound.getCompoundTag("Slot"));
        }

        @Nonnull
        @Override
        public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
        {
            super.writeToNBT(compound);

            compound.setTag("Slot", this.slot.serializeNBT());

            return compound;
        }

        @Override
        public ResourceLocation getModelLocation()
        {
            return ArkResUtil.geo("normal_block");
        }
    }
}
