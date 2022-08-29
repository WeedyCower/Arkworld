package com.weedycow.arkworld.entity.operator;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class Skill
{
    protected int ser;
    protected int initPoint;
    protected int consumePoint;
    protected int time;
    protected String name;
    protected RecoveryType recoveryType;
    protected TriggerType triggerType;
    protected Operator operator;
    protected ArrayList<ArrayList<ItemStack>> breakMaterial;

    public Skill(int ser, String name, RecoveryType recoveryType, TriggerType triggerType, Operator operator, ArrayList<ArrayList<ItemStack>> breakMaterial)
    {
        this.ser = ser;
        this.recoveryType = recoveryType;
        this.triggerType = triggerType;
        this.operator = operator;
        this.breakMaterial = breakMaterial;
        this.name = name;
    }

    public void skillFunctionPerTick()
    {

    }

    public void skillFunctionInstance()
    {

    }

    public int getSer()
    {
        return ser;
    }

    public ArrayList<ArrayList<ItemStack>> getBreakMaterial()
    {
        return breakMaterial;
    }

    public TriggerType getTriggerType()
    {
        return triggerType;
    }

    public RecoveryType getRecoveryType()
    {
        return recoveryType;
    }

    public String getName()
    {
        return name;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public int getTime()
    {
        return time;
    }

    public void setConsumePoint(int consumePoint)
    {
        this.consumePoint = consumePoint;
    }

    public int getConsumePoint()
    {
        return consumePoint;
    }

    public void setInitPoint(int initPoint)
    {
        this.initPoint = initPoint;
    }

    public int getInitPoint()
    {
        return initPoint;
    }

    public enum TriggerType
    {
        MANUAL,
        AUTO
    }

    public enum RecoveryType
    {
        AUTO,
        ATTACK,
        INJURED
    }
}
