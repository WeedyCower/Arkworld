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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class BlockDormitory extends BlockMachine
{
    public BlockDormitory()
    {
        super(Type.DORMITORY);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("item.arkworld.info.operator_range")+machine.dormitory.operatorRange);

        tooltip.add(I18n.format("item.arkworld.info.operator_note"));
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
            playerIn.openGui(Arkworld.instance, GuiRegistry.DORMITORY, worldIn, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileDormitory();
    }

    public static class TileDormitory extends MachineTile
    {
        int atmosphere;
        int atmosphereLimited;
        float mood;
        float moodAdd;
        protected ItemStackHandler slot = new ItemStackHandler(1);

        public TileDormitory()
        {
            super(Type.DORMITORY.getName(),20,true);
            this.oDis = machine.dormitory.operatorRange;
        }

        @Override
        protected void intervalExecute()
        {
            operators.clear();

            setMoodAdd(0);

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
                for (Operator operator : world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)).size() <= 5 ? world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)) : world.getEntitiesWithinAABB(Operator.class, new AxisAlignedBB(getPos()).grow(oDis)).subList(0, 5))
                {
                    if (operator.getDistance(pos.getX(), pos.getY(), pos.getZ()) <= oDis)
                    {
                        if (operator.getRoom() == null)
                            operator.setRoom(Type.DORMITORY, this);

                        if (operator.getRoom() == this)
                            operators.add(operator);
                    }
                }


                for (Operator operator : operators)
                {
                    setMoodAdd(operator.getLogSkillRatio());
                    if (operator.getMood() < 24)
                        operator.setMood(operator.getMood() + (getMood() + getMoodAdd()) / 3600);
                }

                if (!slot.getStackInSlot(0).isEmpty())
                {
                    if (getLevel() == 0 && slot.getStackInSlot(0).getItem() instanceof LightBuildingMaterial && slot.getStackInSlot(0).getCount() >= 1 && getCenter().getUav()>=10)
                    {
                        setLevel(getLevel() + 1);
                        slot.getStackInSlot(0).shrink(1);
                        getCenter().setUav(getCenter().getUav()-10);
                    }

                    if (getLevel() == 1 && slot.getStackInSlot(0).getItem() instanceof LightBuildingMaterial && slot.getStackInSlot(0).getCount() >= 3 && getCenter().getUav()>=20)
                    {
                        setLevel(getLevel() + 1);
                        slot.getStackInSlot(0).shrink(3);
                        getCenter().setUav(getCenter().getUav()-20);
                    }

                    if (getLevel() == 2 && slot.getStackInSlot(0).getItem() instanceof ConcreteBuildingMaterial && slot.getStackInSlot(0).getCount() >= 3 && getCenter().getUav()>=30)
                    {
                        setLevel(getLevel() + 1);
                        slot.getStackInSlot(0).shrink(3);
                        getCenter().setUav(getCenter().getUav()-30);
                    }

                    if (getLevel() == 3 && slot.getStackInSlot(0).getItem() instanceof ReinforcedBuildingMaterial && slot.getStackInSlot(0).getCount() >= 4 && getCenter().getUav()>=45)
                    {
                        setLevel(getLevel() + 1);
                        slot.getStackInSlot(0).shrink(4);
                        getCenter().setUav(getCenter().getUav()-45);
                    }

                    if (getLevel() == 4 && slot.getStackInSlot(0).getItem() instanceof ReinforcedBuildingMaterial && slot.getStackInSlot(0).getCount() >= 8 && getCenter().getUav()>=65)
                    {
                        setLevel(getLevel() + 1);
                        slot.getStackInSlot(0).shrink(8);
                        getCenter().setUav(getCenter().getUav()-65);
                    }
                }
            }

            if(getLevel()==1)
                setPowerNeed(10);

            if(getLevel()==2)
                setPowerNeed(20);

            if(getLevel()==3)
                setPowerNeed(30);

            if(getLevel()==4)
                setPowerNeed(45);

            if(getLevel()==5)
                setPowerNeed(65);

            setMood(1.5f+0.1f*getLevel());

            setMoodAdd(0.0004f*getAtmosphere());

            setAtmosphereLimited(getLevel() * 1000);
        }

        public void setAtmosphere(int atmosphere)
        {
            this.atmosphere = atmosphere;
        }

        public int getAtmosphere()
        {
            return atmosphere;
        }

        public float getMood()
        {
            return mood;
        }

        public void setMood(float mood)
        {
            this.mood = mood;
        }

        public float getMoodAdd()
        {
            return moodAdd;
        }

        public void setMoodAdd(float moodAdd)
        {
            this.moodAdd = moodAdd;
        }

        public int getAtmosphereLimited()
        {
            return atmosphereLimited;
        }

        public void setAtmosphereLimited(int atmosphereLimited)
        {
            this.atmosphereLimited = atmosphereLimited;
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

            setAtmosphere(compound.getInteger("Atmosphere"));

            setAtmosphereLimited(compound.getInteger("AtmosphereLimited"));

            setMood(compound.getFloat("Mood"));

            setMoodAdd(compound.getFloat("MoodAdd"));

            slot.deserializeNBT(compound.getCompoundTag("Slot"));
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound compound)
        {
            super.writeToNBT(compound);

            compound.setInteger("Atmosphere",getAtmosphere());

            compound.setInteger("AtmosphereLimited",getAtmosphereLimited());

            compound.setFloat("Mood",getMood());

            compound.setFloat("MoodAdd",getMoodAdd());

            compound.setTag("Slot", slot.serializeNBT());

            return compound;
        }

        @Override
        public ResourceLocation getModelLocation()
        {
            return ArkResUtil.geo("normal_block");
        }
    }
}