package com.weedycow.arkworld;

import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.networlk.*;
import com.weedycow.arkworld.registry.*;
import com.weedycow.arkworld.world.arkworld.gen.WorldGenCustomStructure;
import com.weedycow.arkworld.world.arkworld.gen.WorldGeneratorTalaStone;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.GeckoLib;

@Mod(modid = Arkworld.MODID, name = Arkworld.NAME, version = Arkworld.VERSION, acceptedMinecraftVersions = "[1.12.2]", dependencies = "required-after:weedylib@[0.3,);")
public class Arkworld
{
    public static final String MODID = "arkworld";
    public static final String NAME = "Arkworld";
    public static final String VERSION = "0.2.3";
    public static final boolean NEWDES = false;
    private static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    public Arkworld()
    {
        GeckoLib.initialize();
        net.minecraftforge.fluids.FluidRegistry.enableUniversalBucket();
    }

    @Mod.Instance
    public static Arkworld instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        CapabilityRegistry.onRegistry();
        FluidRegistry.onRegistry();
        WorldGenRegistry.registerDimensions();
        MinecraftForge.ORE_GEN_BUS.register(new WorldGeneratorTalaStone());
        GameRegistry.registerWorldGenerator(new WorldGenCustomStructure(), 0);
        NETWORK.registerMessage(new PacketState.Handler(),PacketState.class,1,Side.CLIENT);
        NETWORK.registerMessage(new PacketSam.Handler(),PacketSam.class,2,Side.CLIENT);
        NETWORK.registerMessage(new PacketLevel.Handler(),PacketLevel.class,3,Side.CLIENT);
        NETWORK.registerMessage(new PacketMana.Handler(), PacketMana.class,4,Side.CLIENT);
        NETWORK.registerMessage(new PacketStamina.Handler(), PacketStamina.class,5,Side.CLIENT);
        NETWORK.registerMessage(new PacketValue.Handler(), PacketValue.class,6,Side.CLIENT);
        NETWORK.registerMessage(new PacketSwing.Handler(), PacketSwing.class,7,Side.SERVER);
        NETWORK.registerMessage(new PacketAttribute.Handler(), PacketAttribute.class,8,Side.CLIENT);
        NETWORK.registerMessage(new PacketGuiButton.Handler(), PacketGuiButton.class,9,Side.SERVER);
    }

    @EventHandler
    @SideOnly(Side.CLIENT)
    public void  preInitClient(FMLPreInitializationEvent event)
    {
        RenderRegistry.register();
        MinecraftForge.EVENT_BUS.register(new Operator.Bar());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiRegistry());
        BiomesRegistry.initBiomeManagerAndDictionary();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
    
    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {}

    public static SimpleNetworkWrapper getNetwork()
    {
        return NETWORK;
    }
}
