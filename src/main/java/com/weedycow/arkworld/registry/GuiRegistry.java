package com.weedycow.arkworld.registry;

import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.gui.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiRegistry implements IGuiHandler
{
    public static final int MY_INFO = 0;
    public static final int OPERATOR_FIR = 1;
    public static final int OPERATOR_SEC = 2;
    public static final int PROCESSING_STATION = 3;
    public static final int CONTROL_CENTER = 4;
    public static final int MANUFACTURING_STATION = 5;
    public static final int TRADING_STATION = 6;
    public static final int POWER_STATION = 7;
    public static final int OFFICE = 8;
    public static final int DORMITORY = 9;
    public static final int TRAINING_ROOM = 10;
    public static final int WEAPON_TABLE = 11;

    @Nullable
    @Override
    public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (guiId)
        {
            case MY_INFO:
                return new MyInfoContainer(player.inventory, player);
            case OPERATOR_FIR:
                return new OperatorFirContainer(player.inventory, (Operator) world.getEntityByID(x), player);
            case OPERATOR_SEC:
                return new OperatorSecContainer(player.inventory, (Operator) world.getEntityByID(x), player);
            case PROCESSING_STATION:
                return new ProcessingStationContainer(world.getTileEntity(new BlockPos(x,y,z)),player);
            case CONTROL_CENTER:
                return new ControlCenterContainer(world.getTileEntity(new BlockPos(x,y,z)),player);
            case MANUFACTURING_STATION:
                return new ManufacturingStationContainer(world.getTileEntity(new BlockPos(x,y,z)),player);
            case TRADING_STATION:
                return new TradingStationContainer(world.getTileEntity(new BlockPos(x,y,z)),player);
            case POWER_STATION:
                return new PowerStationContainer(world.getTileEntity(new BlockPos(x,y,z)),player);
            case OFFICE:
                return new OfficeContainer(world.getTileEntity(new BlockPos(x,y,z)),player);
            case DORMITORY:
                return new DormitoryContainer(world.getTileEntity(new BlockPos(x,y,z)),player);
            case TRAINING_ROOM:
                return new TrainingRoomContainer(world.getTileEntity(new BlockPos(x,y,z)),player);
            case WEAPON_TABLE:
                return new WeaponTableContainer(world.getTileEntity(new BlockPos(x,y,z)),player);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (guiId)
        {
            case MY_INFO:
                return new MyInfoGuiContainer(player, new MyInfoContainer(player.inventory, player));
            case OPERATOR_FIR:
                return new OperatorFirGuiContainer((Operator) world.getEntityByID(x), new OperatorFirContainer(player.inventory, (Operator) world.getEntityByID(x), player));
            case OPERATOR_SEC:
                return new OperatorSecGuiContainer((Operator) world.getEntityByID(x), new OperatorSecContainer(player.inventory, (Operator) world.getEntityByID(x), player));
            case PROCESSING_STATION:
                return new ProcessingStationGuiContainer(new ProcessingStationContainer(world.getTileEntity(new BlockPos(x,y,z)),player));
            case CONTROL_CENTER:
                return new ControlCenterGuiContainer(new ControlCenterContainer(world.getTileEntity(new BlockPos(x,y,z)),player),world.getTileEntity(new BlockPos(x,y,z)));
            case MANUFACTURING_STATION:
                return new ManufacturingStationGuiContainer(new ManufacturingStationContainer(world.getTileEntity(new BlockPos(x,y,z)),player),world.getTileEntity(new BlockPos(x,y,z)));
            case TRADING_STATION:
                return new TradingStationGuiContainer(new TradingStationContainer(world.getTileEntity(new BlockPos(x,y,z)),player),world.getTileEntity(new BlockPos(x,y,z)));
            case POWER_STATION:
                return new PowerStationGuiContainer(new PowerStationContainer(world.getTileEntity(new BlockPos(x,y,z)),player),world.getTileEntity(new BlockPos(x,y,z)));
            case OFFICE:
                return new OfficeGuiContainer(new OfficeContainer(world.getTileEntity(new BlockPos(x,y,z)),player),world.getTileEntity(new BlockPos(x,y,z)));
            case DORMITORY:
                return new DormitoryGuiContainer(new DormitoryContainer(world.getTileEntity(new BlockPos(x,y,z)),player),world.getTileEntity(new BlockPos(x,y,z)));
            case TRAINING_ROOM:
                return new TrainingRoomGuiContainer(new TrainingRoomContainer(world.getTileEntity(new BlockPos(x,y,z)),player),world.getTileEntity(new BlockPos(x,y,z)));
            case WEAPON_TABLE:
                return new WeaponTableGuiContainer(new WeaponTableContainer(world.getTileEntity(new BlockPos(x,y,z)),player),world.getTileEntity(new BlockPos(x,y,z)));
            default:
                return null;
        }
    }
}
