package com.weedycow.arkworld.capability;

public enum EnumState
{
    //友方debuff
    DEFAULT(10),//默认
    COLD(11),//寒冷
    FREEZE(12),//冻结
    WATER_EROSION(13),//水蚀 (敌方debuff)//TODO
    VERTIGO(14),//眩晕 (敌方debuff)
    REDUCE_DEFENSE(15),//降防
    ORIGINIUM_STIMULATION(16),//源石刺激
    BURNING_BREATH(17),//烈焚灼息

    //友方buff
    RESISTANCE(18),//抵抗
    SHELTER(19),//庇护
    INSPIRE(20),//鼓舞
    ENERGETIC(21),//精力充沛
    HIDE(22),//隐匿 (敌方buff)
    WE_UNITE(23),//我们联合

    //敌方buff
    INVINCIBLE(24),//无敌
    CAMOUFLAGE(25),//迷彩
    TACTICAL_ORDER(26),//战术命令

    //敌方debuff
    PAUSE(27),//停顿
    SHACKLES(28),//束缚
    FRAGILE(29),//脆弱
    SLEEP(30),//沉睡
    SILENCE(31),//沉默
    NERVE_INJURY(32),//神经损伤
    EROSION_DAMAGE(33),
    BANEAL(34),
    D12(35),
    ADD_DEFENSE(36),
    PUSH(37);

    private final int value;
    private int tick;
    private int level;

    EnumState(int value)
    {
        this.value = value;
    }

    public static EnumState getNormalState(int value, int tick)
    {
        return getStateFromValue(value).getLevelTickState(10,tick);
    }

    public static EnumState getState(int value, int level, int tick)
    {
        return getStateFromValue(value).getLevelTickState(level,tick);
    }

    public static EnumState getStateFromValue(int value)
    {
        for(EnumState state : EnumState.values())
        {
            if(state.value==value)
                return state;
        }
        return DEFAULT;
    }

    @Override
    public String toString()
    {
        return name().toLowerCase();
    }

    private void setTick(int tick)
    {
        this.tick = tick;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void addLevel(int l)
    {
        this.level += l;
    }

    public void delLevel(int l)
    {
        this.level -= l;
    }

    public int getTick()
    {
        return this.tick;
    }

    public int getLevel()
    {
        return this.level;
    }

    public int getValue()
    {
        return this.value;
    }

    public EnumState getTickState(int tick)
    {
        this.setTick(tick);
        return this;
    }

    public EnumState getLevelState(int level)
    {
        this.setLevel(level);
        return this;
    }

    public EnumState getLevelTickState(int level, int tick)
    {
        this.setTick(tick);
        this.setLevel(level);
        return this;
    }

    public int getSeconds()
    {
        return getTick()/20;
    }

    public void tickTok()
    {
        if(getTick()>0)
            setTick(getTick()-1);
    }

    public String getStateId()
    {
        return value + String.valueOf(level) + tick;
    }

    public static EnumState getStateFormId(String id)
    {
        String v = id.substring(0,2);
        String l = id.substring(2,4);
        String t = id.substring(4);
        return getStateFromValue(Integer.parseInt(v)).getLevelTickState(Integer.parseInt(l),Integer.parseInt(t));
    }
}
