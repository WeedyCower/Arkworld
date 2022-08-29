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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BlockControlCenter extends BlockMachine
{
    public BlockControlCenter()
    {
        super(Type.CONTROL_CENTER);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("item.arkworld.info.center_range")+machine.controlCenter.centerRange);

        tooltip.add(I18n.format("item.arkworld.info.center_note"));

        tooltip.add(I18n.format("item.arkworld.info.operator_range")+machine.controlCenter.operatorRange);

        tooltip.add(I18n.format("item.arkworld.info.operator_note"));
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
            playerIn.openGui(Arkworld.instance, GuiRegistry.CONTROL_CENTER, worldIn, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileControlCenter();
    }

    public static class TileControlCenter extends MachineTile
    {
        int uav;
        int uavLimit;
        int machineLimited;
        int power;
        int powerConsume;
        EntityPlayer master;
        protected List<MachineTile> tiles = new ArrayList<>();
        protected ItemStackHandler slot = new ItemStackHandler(3);
        AnimationController<TileControlCenter> controllerIdle = new AnimationController<>(this, "idle", 1, this::PlayState);

        public TileControlCenter()
        {
            super(Type.CONTROL_CENTER.getName(),20,true);
            if(getLevel()==0) setLevel(1);
            this.oDis=machine.controlCenter.operatorRange;
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
            if(!world.isRemote)
                tiles.clear();

            operators.clear();

            setPower(0);

            setPowerConsume(0);

            if(getLevel()==0) setLevel(1);

            setUavLimit(getLevel() * 36 + 20);

            if (getUav() < getUavLimit() && getTickExisted() % 180 == 0)
                setUav(getUav() + 1);

            setMachineLimited(getLevel() * 3);

            for (Operator operator : world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)).size() <= 5 ? world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)) : world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)).subList(0, 5))
            {
                if(operator.getDistance(pos.getX(),pos.getY(),pos.getZ())<=oDis)
                {
                    if(operator.getRoom()==null)
                        operator.setRoom(Type.CONTROL_CENTER,this);

                    if(operator.getRoom()==this)
                        operators.add(operator);
                }
            }

            Iterator<BlockPos> b = MachineWorldSavedData.get(world).pos.listIterator();

            while (b.hasNext())
            {
                TileEntity tile = world.getTileEntity(b.next());

                if((tile instanceof MachineTile && tile!=this && !world.isRemote && Math.sqrt(tile.getDistanceSq(pos.getX(),pos.getY(),pos.getZ()))<((MachineTile) tile).getoDis()+getoDis())
                        || (tile instanceof BlockControlCenter.TileControlCenter && tile!=this && !world.isRemote && Math.sqrt(tile.getDistanceSq(pos.getX(),pos.getY(),pos.getZ()))<64))
                {
                    if(getTickExisted()>((MachineTile) tile).getTickExisted())
                    {
                        spawnAsEntity(world, tile.getPos(), new ItemStack(tile.getBlockType()), tile);
                        world.setBlockState(tile.getPos(), Blocks.AIR.getDefaultState());
                        b.remove();
                    }
                }

                if (tile instanceof MachineTile && !(tile instanceof BlockControlCenter.TileControlCenter) && Math.sqrt(tile.getDistanceSq(pos.getX(),pos.getY(),pos.getZ()))<=machine.controlCenter.centerRange) tiles.add((MachineTile) tile);
            }

            for (MachineTile tile : tiles)
            {

                if(tile.getCenter()==null)
                    tile.setCenter(this);

                if(tile instanceof BlockPowerStation.TilePowerStation)
                {
                    setPower(getPower() + ((BlockPowerStation.TilePowerStation) tile).getPowerProduction());
                }

                else setPowerConsume(getPowerNeed()+ tile.getPowerNeed());
            }
        }

        public int getMachineAmount(BlockMachine.Type type)
        {
            return (int) tiles.stream().filter(next -> next.getId().equals(type.getName())).count();
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

            setUav(compound.getInteger("Uav"));

            setUavLimit(compound.getInteger("UavLimit"));

            slot.deserializeNBT(compound.getCompoundTag("Slot"));

            setMachineLimited(compound.getInteger("MachineLimited"));

            setPower(compound.getInteger("Power"));

            setPowerConsume(compound.getInteger("PowerConsume"));

            if(hasWorld() && compound.getInteger("size")>0)
            {
                tiles.clear();

                for(int i=0; i<compound.getInteger("size"); i++)
                {
                    if(world.getTileEntity(new BlockPos(compound.getIntArray(String.valueOf(i))[0],compound.getIntArray(String.valueOf(i))[1],compound.getIntArray(String.valueOf(i))[2])) instanceof MachineTile && !(world.getTileEntity(new BlockPos(compound.getIntArray(String.valueOf(i))[0],compound.getIntArray(String.valueOf(i))[1],compound.getIntArray(String.valueOf(i))[2])) instanceof BlockControlCenter.TileControlCenter))
                    tiles.add(i, (MachineTile) world.getTileEntity(new BlockPos(compound.getIntArray(String.valueOf(i))[0],compound.getIntArray(String.valueOf(i))[1],compound.getIntArray(String.valueOf(i))[2])));
                }
            }

            if(hasWorld() && compound.hasKey("Master") && !compound.getString("Master").equals(""))
                setMaster(world.getPlayerEntityByName(compound.getString("Master")));
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound compound)
        {
            super.writeToNBT(compound);

            if(getMaster()!=null)
                compound.setString("Master",getMaster().getName());

            compound.setTag("Slot", slot.serializeNBT());

            compound.setInteger("Uav",getUav());

            compound.setInteger("UavLimit",getUavLimit());

            compound.setInteger("MachineLimited",getMachineLimited());

            compound.setInteger("Power",getPower());

            compound.setInteger("PowerConsume",getPowerConsume());

            if(getMaster()!=null)
                compound.setUniqueId("Master",getMaster().getUniqueID());

            for(int i=0; i<tiles.size(); i++)
            {
                compound.setIntArray(String.valueOf(i), new int[]{tiles.get(i).getPos().getX(),tiles.get(i).getPos().getY(),tiles.get(i).getPos().getZ()});
            }

            compound.setInteger("size",tiles.size());

            return compound;
        }

        public boolean isEnoughPower()
        {
            return getPower()-getPowerConsume()>=0;
        }

        public void setPower(int power)
        {
            this.power = power;
        }

        public int getPower()
        {
            return power;
        }

        public int getPowerConsume()
        {
            return powerConsume;
        }

        public void setPowerConsume(int powerConsume)
        {
            this.powerConsume = powerConsume;
        }

        public void setMachineLimited(int machineLimited)
        {
            this.machineLimited = machineLimited;
        }

        public int getMachineLimited()
        {
            return machineLimited;
        }

        public void setUavLimit(int uavLimit)
        {
            this.uavLimit = uavLimit;
        }

        public int getUavLimit()
        {
            return uavLimit;
        }

        public void setUav(int uav)
        {
            this.uav = uav;
        }

        public int getUav()
        {
            return uav;
        }

        public List<MachineTile> getTiles()
        {
            return tiles;
        }

        public EntityPlayer getMaster()
        {
            return master;
        }

        public void setMaster(EntityPlayer master)
        {
            this.master = master;
        }

        @Override
        public ResourceLocation getModelLocation()
        {
            return ArkResUtil.geo("normal_block");
        }
    }
}
