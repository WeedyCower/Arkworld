package com.weedycow.arkworld.capability;

public enum EnumEntry
{
    DAMAGE(10),
    SHOOT(11),
    DURABLE(12),
    MELEE(13),
    RANGE(14),
    SHIELD(15),
    CRITICAL(16),
    LESS_STAMINA(17),
    LESS_MANA(18),
    LESS_SAM(19),
    KNOCKBACK(20),
    VAMPIRE(21),
    COLD(22),
    REDUCE_DEFENCE(23),
    SILENCE(24),
    VERTIGO(25),
    BURNING_BREATH(26),
    FRAGILE(27),
    PAUSE(28),
    SHACKLES(29),
    BANEAL(30),
    D12(31),
    ANTI_HIDE(32),
    ANTI_PHYSICAL(33),
    ANTI_SPELL(34),
    ANTI_DAMAGE(35),
    ANTI_EXPLOSION(36),
    ANTI_FIRE(37),
    ANTI_FALL(38),
    ANTI_WITHER(39),
    ANTI_DROWN(40),
    REFLEX(41),
    RESURRECTION(42),
    MAX_HEALTH(43),
    HEALING_STRENGTH(44),
    HEALTH_RECOVER(45),
    MOVE_SPEED(46),
    ATTACK_SPEED(47),
    LUCK(48),
    ADD_STAMINA(49),
    ADD_MAMA(50),
    ADD_SAM(51);

    private final int value;
    private int rank;

    EnumEntry(int value)
    {
        this.value = value;
    }

    public static EnumEntry getEntryFromValue(int value)
    {
        for(EnumEntry state : EnumEntry.values())
        {
            if(state.getValue() == value)
                return state;
        }
        return SHOOT;
    }

    public EnumEntry getEntry( int rank)
    {
        this.setRank(rank);
        return this;
    }

    public static EnumEntry getEntry(int value, int rank)
    {
        return getEntryFromValue(value).getEntryWithRank(rank);
    }

    public EnumEntry getEntryWithRank(int rank)
    {
        this.setRank(rank);
        return this;
    }

    public static EnumEntry getEntryFormId(String id)
    {
        String v = id.substring(0,2);
        String l = id.substring(2,3);
        return EnumEntry.getEntry(Integer.parseInt(v),Integer.parseInt(l));
    }

    public String getEntryId()
    {
        return value + String.valueOf(rank);
    }

    public int getValue()
    {
        return value;
    }

    public void setRank(int rank)
    {
        this.rank = rank;
    }

    public int getRank()
    {
        return rank;
    }

    @Override
    public String toString()
    {
        return super.toString().toLowerCase();
    }
}
