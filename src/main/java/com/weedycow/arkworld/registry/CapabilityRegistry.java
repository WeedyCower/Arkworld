package com.weedycow.arkworld.registry;

import com.weedycow.arkworld.capability.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class CapabilityRegistry
{
    @CapabilityInject(CapabilityState.class)
    public static Capability<CapabilityState> capState;

    @CapabilityInject(CapabilitySam.class)
    public static Capability<CapabilitySam> capSam;

    @CapabilityInject(CapabilityLevel.class)
    public static Capability<CapabilityLevel> capLevel;

    @CapabilityInject(CapabilityMana.class)
    public static Capability<CapabilityMana> capMana;

    @CapabilityInject(CapabilityStamina.class)
    public static Capability<CapabilityStamina> capStamina;

    @CapabilityInject(CapabilityValue.class)
    public static Capability<CapabilityValue> capValue;

    @CapabilityInject(CapabilityWeapon.class)
    public static Capability<CapabilityWeapon> capWeapon;

    @CapabilityInject(CapabilityItem.class)
    public static Capability<CapabilityItem> capItem;

    @CapabilityInject(CapabilityPlayer.class)
    public static Capability<CapabilityPlayer> capPlayer;

    @CapabilityInject(IItemHandlerModifiable.class)
    public static Capability<IItemHandlerModifiable> capSlotPlayer;

    @CapabilityInject(CapabilityAttribute.class)
    public static Capability<CapabilityAttribute> capAttribute;

    @CapabilityInject(CapabilityOperatorCard.class)
    public static Capability<CapabilityOperatorCard> capOperatorCard;

    public static void onRegistry()
    {
        CapabilityManager.INSTANCE.register(CapabilityState.class, new CapabilityState.Storage(), CapabilityState::new);

        CapabilityManager.INSTANCE.register(CapabilitySam.class, new CapabilitySam.Storage(), CapabilitySam::new);

        CapabilityManager.INSTANCE.register(CapabilityLevel.class, new CapabilityLevel.Storage(), CapabilityLevel::new);

        CapabilityManager.INSTANCE.register(CapabilityMana.class, new CapabilityMana.Storage(), CapabilityMana::new);

        CapabilityManager.INSTANCE.register(CapabilityStamina.class, new CapabilityStamina.Storage(), CapabilityStamina::new);

        CapabilityManager.INSTANCE.register(CapabilityValue.class, new CapabilityValue.Storage(), CapabilityValue::new);

        CapabilityManager.INSTANCE.register(CapabilityWeapon.class, new CapabilityWeapon.Storage(), CapabilityWeapon::new);

        CapabilityManager.INSTANCE.register(CapabilityItem.class, new CapabilityItem.Storage(), CapabilityItem::new);

        CapabilityManager.INSTANCE.register(CapabilityPlayer.class, new CapabilityPlayer.Storage(), CapabilityPlayer::new);

        CapabilityManager.INSTANCE.register(IItemHandlerModifiable.class, new CapabilitySlotPlayer.Storage<>(), ItemStackHandler.class);

        CapabilityManager.INSTANCE.register(CapabilityAttribute.class, new CapabilityAttribute.Storage(), CapabilityAttribute::new);

        CapabilityManager.INSTANCE.register(CapabilityOperatorCard.class, new CapabilityOperatorCard.Storage(), CapabilityOperatorCard::new);

    }
}
