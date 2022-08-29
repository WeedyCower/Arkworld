package com.weedycow.arkworld.registry;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.world.arkworld.biome.*;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(Arkworld.MODID)
public class BiomesRegistry
{
    public final static BiomeWasteland WASTELAND = new BiomeWasteland();
    public final static BiomeFrostland FROSTLAND = new BiomeFrostland();
    public final static BiomeFungimist FUNGIMIST = new BiomeFungimist();
    public final static BiomeIberia IBERIA = new BiomeIberia();
    public final static BiomeChernoberg CHERNOBERG = new BiomeChernoberg();
    public final static BiomePlant PLANT = new BiomePlant();
    public final static BiomeHill HILL = new BiomeHill();
    public final static BiomeJungle JUNGLE = new BiomeJungle();
    public final static BiomeWilderness WILDERNESS = new BiomeWilderness();

    @Mod.EventBusSubscriber(modid = Arkworld.MODID)
    public static class RegistrationHandler
    {
        @SubscribeEvent
        public static void onEvent(RegistryEvent.Register<Biome> event)
        {
            IForgeRegistry<Biome> registry = event.getRegistry();
            registry.register(new BiomeWasteland().setRegistryName(Arkworld.MODID,"wasteland"));
            registry.register(new BiomeFrostland().setRegistryName(Arkworld.MODID,"frostland"));
            registry.register(new BiomeFungimist().setRegistryName(Arkworld.MODID,"fungimist"));
            registry.register(new BiomeIberia().setRegistryName(Arkworld.MODID,"iberia"));
            registry.register(new BiomeChernoberg().setRegistryName(Arkworld.MODID,"chernoberg"));
            registry.register(new BiomePlant().setRegistryName(Arkworld.MODID,"plant"));
            registry.register(new BiomeHill().setRegistryName(Arkworld.MODID,"hill"));
            registry.register(new BiomeJungle().setRegistryName(Arkworld.MODID,"jungle"));
            registry.register(new BiomeWilderness().setRegistryName(Arkworld.MODID,"wilderness"));

        }
    }

    public static void initBiomeManagerAndDictionary()
    {
        BiomeDictionary.addTypes(WASTELAND, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.HOT);
        BiomeDictionary.addTypes(FROSTLAND, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.PLAINS);
        BiomeDictionary.addTypes(FUNGIMIST, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.LUSH, BiomeDictionary.Type.WET);
        BiomeDictionary.addTypes(IBERIA, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.WET);
        BiomeDictionary.addTypes(PLANT, BiomeDictionary.Type.PLAINS);
        BiomeDictionary.addTypes(HILL, BiomeDictionary.Type.HILLS);
        BiomeDictionary.addTypes(JUNGLE, BiomeDictionary.Type.JUNGLE);
    }
}