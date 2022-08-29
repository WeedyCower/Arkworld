package com.weedycow.arkworld.gui;

import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.arkworld.util.ArkItemUtil;
import com.weedycow.weedylib.util.ListUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProcessingStationContainer extends Container implements IButton
{
    TileEntity tile;
    EntityPlayer player;
    List<ItemStack> oriStacks;
    List<Integer> linkSer;
    List<ItemStack> adjStacks;
    public static final List<ItemStack> CRYSTALLINE_ELECTRONIC_UNIT = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.CRYSTALLINE_CIRCUIT),new ItemStack(ItemRegistry.POLYMERIZED_GEL,2),new ItemStack(ItemRegistry.INCANDESCENT_ALLOY_BLOCK),new ItemStack(ItemRegistry.CRYSTALLINE_ELECTRONIC_UNIT),new ItemStack(ItemRegistry.LMB,4),new ItemStack(ItemRegistry.LMB,8)));
    public static final List<ItemStack> D32_STEEL = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.MANGANESE_TRIHYDRATE),new ItemStack(ItemRegistry.GRINDSTONE_PENTAHYDRATE),new ItemStack(ItemRegistry.RMAR),new ItemStack(ItemRegistry.DSTEEL),new ItemStack(ItemRegistry.LMB,4),new ItemStack(ItemRegistry.LMB,8)));
    public static final List<ItemStack> BIPOLAR_NANOFLAKE = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.OPTIMIZED_DEVICE),new ItemStack(ItemRegistry.WHITE_HORSE_KOHL,2),ItemStack.EMPTY,new ItemStack(ItemRegistry.BIPOLAR_NANOFLAKE),new ItemStack(ItemRegistry.LMB,4),new ItemStack(ItemRegistry.LMB,8)));
    public static final List<ItemStack> POLYMERIZATION_PREPARATION = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.ORIROCK_CONCENTRATION),new ItemStack(ItemRegistry.ORIRON_BLOCK),new ItemStack(ItemRegistry.KETON_COLLOID),new ItemStack(ItemRegistry.POLYMERIZATION_PREPARATION),new ItemStack(ItemRegistry.LMB,4),new ItemStack(ItemRegistry.LMB,8)));
    public static final List<ItemStack> CRYSTALLINE_CIRCUIT = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.CRYSTALLINE_COMPONENT,2),new ItemStack(ItemRegistry.COAGULATING_GEL),new ItemStack(ItemRegistry.INCANDESCENT_ALLOY),new ItemStack(ItemRegistry.CRYSTALLINE_CIRCUIT),new ItemStack(ItemRegistry.LMB,3),new ItemStack(ItemRegistry.LMB,4)));
    public static final List<ItemStack> INCANDESCENT_ALLOY_BLOCK = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.INTEGRATED_DEVICE),new ItemStack(ItemRegistry.GRINDSTONE),new ItemStack(ItemRegistry.INCANDESCENT_ALLOY),new ItemStack(ItemRegistry.INCANDESCENT_ALLOY_BLOCK),new ItemStack(ItemRegistry.LMB,3),new ItemStack(ItemRegistry.LMB,4)));
    public static final List<ItemStack> POLYMERIZED_GEL = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.ORIRON_CLUSTER),new ItemStack(ItemRegistry.COAGULATING_GEL),new ItemStack(ItemRegistry.INCANDESCENT_ALLOY),new ItemStack(ItemRegistry.POLYMERIZED_GEL),new ItemStack(ItemRegistry.LMB,3),new ItemStack(ItemRegistry.LMB,4)));
    public static final List<ItemStack> RMAR = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.RMAY),new ItemStack(ItemRegistry.ORIROCK_CLUSTER,2),new ItemStack(ItemRegistry.AKETON),new ItemStack(ItemRegistry.RMAR),new ItemStack(ItemRegistry.LMB,3),new ItemStack(ItemRegistry.LMB,4)));
    public static final List<ItemStack> GRINDSTONE_PENTAHYDRATE = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.GRINDSTONE),new ItemStack(ItemRegistry.ORIRON_CLUSTER),new ItemStack(ItemRegistry.INTEGRATED_DEVICE),new ItemStack(ItemRegistry.GRINDSTONE_PENTAHYDRATE),new ItemStack(ItemRegistry.LMB,3),new ItemStack(ItemRegistry.LMB,4)));
    public static final List<ItemStack> MANGANESE_TRIHYDRATE = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.MANGANESE_ORE,2),new ItemStack(ItemRegistry.POLYESTER_PACK),new ItemStack(ItemRegistry.LOXIC_KOHL),new ItemStack(ItemRegistry.MANGANESE_TRIHYDRATE),new ItemStack(ItemRegistry.LMB,3),new ItemStack(ItemRegistry.LMB,4)));
    public static final List<ItemStack> WHITE_HORSE_KOHL = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.LOXIC_KOHL),new ItemStack(ItemRegistry.SUGAR_PACK),new ItemStack(ItemRegistry.RMAY),new ItemStack(ItemRegistry.WHITE_HORSE_KOHL),new ItemStack(ItemRegistry.LMB,3),new ItemStack(ItemRegistry.LMB,4)));
    public static final List<ItemStack> OPTIMIZED_DEVICE = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.INTEGRATED_DEVICE),new ItemStack(ItemRegistry.ORIRON_CLUSTER,2),new ItemStack(ItemRegistry.GRINDSTONE),new ItemStack(ItemRegistry.OPTIMIZED_DEVICE),new ItemStack(ItemRegistry.LMB,3),new ItemStack(ItemRegistry.LMB,4)));
    public static final List<ItemStack> KETON_COLLOID = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.AKETON,2),new ItemStack(ItemRegistry.SUGAR_PACK),new ItemStack(ItemRegistry.MANGANESE_ORE),new ItemStack(ItemRegistry.KETON_COLLOID),new ItemStack(ItemRegistry.LMB,3),new ItemStack(ItemRegistry.LMB,4)));
    public static final List<ItemStack> ORIRON_BLOCK = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.ORIRON_CLUSTER,2),new ItemStack(ItemRegistry.INTEGRATED_DEVICE),new ItemStack(ItemRegistry.POLYESTER_PACK),new ItemStack(ItemRegistry.ORIRON_BLOCK),new ItemStack(ItemRegistry.LMB,3),new ItemStack(ItemRegistry.LMB,4)));
    public static final List<ItemStack> POLYESTER_LUMP = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.POLYESTER_PACK,2),new ItemStack(ItemRegistry.AKETON),new ItemStack(ItemRegistry.LOXIC_KOHL),new ItemStack(ItemRegistry.POLYESTER_LUMP),new ItemStack(ItemRegistry.LMB,3),new ItemStack(ItemRegistry.LMB,4)));
    public static final List<ItemStack> SUGAR_LUMP = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.SUGAR_PACK,2),new ItemStack(ItemRegistry.ORIRON_CLUSTER),new ItemStack(ItemRegistry.MANGANESE_ORE),new ItemStack(ItemRegistry.SUGAR_LUMP),new ItemStack(ItemRegistry.LMB,3),new ItemStack(ItemRegistry.LMB,4)));
    public static final List<ItemStack> INTEGRATED_DEVICE = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.DEVICE,4),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.INTEGRATED_DEVICE),new ItemStack(ItemRegistry.LMB,2),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> ORIROCK_CONCENTRATION = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.ORIROCK_CLUSTER,4),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.ORIROCK_CONCENTRATION),new ItemStack(ItemRegistry.LMB,3),new ItemStack(ItemRegistry.LMB,4)));
    public static final List<ItemStack> DEVICE = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.DAMAGED_DEVICE,3),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.DEVICE),new ItemStack(ItemRegistry.LMB,1),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> POLYKETON = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.DIKETON,3),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.POLYKETON),new ItemStack(ItemRegistry.LMB,1),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> AKETON = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.POLYKETON,4),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.AKETON),new ItemStack(ItemRegistry.LMB,2),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> ORIRON = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.ORIRON_SHARD,3),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.ORIRON),new ItemStack(ItemRegistry.LMB,1),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> ORIRON_CLUSTER = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.ORIRON,4),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.ORIRON_CLUSTER),new ItemStack(ItemRegistry.LMB,2),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> POLYESTER = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.ESTER,3),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.POLYESTER),new ItemStack(ItemRegistry.LMB,1),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> POLYESTER_PACK = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.POLYESTER,4),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.POLYESTER_PACK),new ItemStack(ItemRegistry.LMB,2),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> SUGAR = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.SUGAR_SUBSTITUTE,3),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.SUGAR),new ItemStack(ItemRegistry.LMB,1),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> SUGAR_PACK = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.SUGAR,4),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.SUGAR_PACK),new ItemStack(ItemRegistry.LMB,2),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> ORIROCK_CUBE = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.ORIROCK,3),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.ORIROCK_CUBE),new ItemStack(ItemRegistry.LMB,1),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> ORIROCK_CLUSTER = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.ORIROCK_CUBE,5),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.ORIROCK_CLUSTER),new ItemStack(ItemRegistry.LMB,2),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> LIGHT_BUILDING_MATERIAL = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.CARBON,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.LIGHT_BUILDING_MATERIAL),new ItemStack(ItemRegistry.LMB,8),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> CONCRETE_BUILDING_MATERIAL = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.CARBON_BRICK,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.CONCRETE_BUILDING_MATERIAL),new ItemStack(ItemRegistry.LMB,24),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> REINFORCED_BUILDING_MATERIAL = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.CARBON_PACK,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.REINFORCED_BUILDING_MATERIAL),new ItemStack(ItemRegistry.LMB,64),new ItemStack(ItemRegistry.LMB,4)));
    public static final List<ItemStack> CARBON_BRICK = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.CARBON,3),new ItemStack(Items.SUGAR),ItemStack.EMPTY,new ItemStack(ItemRegistry.CARBON_BRICK),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> CARBON_PACK = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.CARBON_BRICK,3),new ItemStack(Items.SUGAR),ItemStack.EMPTY,new ItemStack(ItemRegistry.CARBON_PACK),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,4)));
    public static final List<ItemStack> SKILL_SUMMARY_II = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_I,3),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.SKILL_SUMMARY_II),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> SKILL_SUMMARY_III = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.SKILL_SUMMARY_II,3),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.SKILL_SUMMARY_III),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> DEFENDER_CHIP = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.MEDIC_CHIP,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.DEFENDER_CHIP),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> MEDIC_CHIP = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.DEFENDER_CHIP,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.MEDIC_CHIP),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> SNIPER_CHIP = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.CASTER_CHIP,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.SNIPER_CHIP),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> CASTER_CHIP = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.SNIPER_CHIP,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.CASTER_CHIP),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> GUARD_CHIP = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.SPECIALIST_CHIP,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.GUARD_CHIP),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> SPECIALIST_CHIP = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.GUARD_CHIP,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.SPECIALIST_CHIP),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> VANGUARD_CHIP = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.SUPPORTER_CHIP,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.VANGUARD_CHIP),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> SUPPORTER_CHIP = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.VANGUARD_CHIP,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.SUPPORTER_CHIP),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> DEFENDER_CHIPSET = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.MEDIC_CHIPSET,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.DEFENDER_CHIPSET),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> MEDIC_CHIPSET = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.DEFENDER_CHIPSET,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.MEDIC_CHIPSET),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> SNIPER_CHIPSET = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.CASTER_CHIPSET,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.SNIPER_CHIPSET),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> CASTER_CHIPSET = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.SNIPER_CHIPSET,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.CASTER_CHIPSET),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> GUARD_CHIPSET = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.SPECIALIST_CHIPSET,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.GUARD_CHIPSET),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> SPECIALIST_CHIPSET = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.GUARD_CHIPSET,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.SPECIALIST_CHIPSET),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> VANGUARD_CHIPSET = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.SUPPORTER_CHIPSET,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.VANGUARD_CHIPSET),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> SUPPORTER_CHIPSET = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemRegistry.VANGUARD_CHIPSET,2),ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(ItemRegistry.SUPPORTER_CHIPSET),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,2)));
    public static final List<ItemStack> SUGAR_SUBSTITUTE = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Items.SUGAR,2),new ItemStack(Items.PAPER),ItemStack.EMPTY,new ItemStack(ItemRegistry.SUGAR_SUBSTITUTE),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> TWO_LOAVES_WITH_CHEESE = new ArrayList<>(Arrays.asList(new ItemStack(Items.BREAD), new ItemStack(Items.BREAD), new ItemStack(ItemRegistry.CHEESE),new ItemStack(ItemRegistry.TWO_LOAVES_WITH_CHEESE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> URSUS_BREAD = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Items.WHEAT),new ItemStack(Items.WHEAT),new ItemStack(ItemRegistry.FAT),new ItemStack(ItemRegistry.URSUS_BREAD),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> DRIED_CACTUS = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Blocks.CACTUS),new ItemStack(Blocks.CACTUS),new ItemStack(ItemRegistry.ESTER),new ItemStack(ItemRegistry.DRIED_CACTUS),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> BISCUIT = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Items.COOKIE),new ItemStack(ItemRegistry.DAMAGED_DEVICE),ItemStack.EMPTY,new ItemStack(ItemRegistry.BISCUIT),new ItemStack(ItemRegistry.LMB,0),new ItemStack(ItemRegistry.LMB,1)));
    public static final List<ItemStack> BLACK_TEA = new ArrayList<>(Arrays.asList(new ItemStack(Items.WATER_BUCKET), new ItemStack(ItemRegistry.TEA,3), ItemStack.EMPTY, new ItemStack(ItemRegistry.BLACK_TEA), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> HIBISCUS_HEALTHY_FOOD = new ArrayList<>(Arrays.asList(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.SPIDER_EYE), new ItemStack(ItemRegistry.NICE_MUSHROOM), new ItemStack(ItemRegistry.HIBISCUS_HEALTHY_FOOD), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> ASCETIC_PUFF = new ArrayList<>(Arrays.asList(new ItemStack(Items.COOKIE), new ItemStack(ItemRegistry.CREAM), new ItemStack(Items.COOKIE), new ItemStack(ItemRegistry.ASCETIC_PUFF), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> BAGUETTE = new ArrayList<>(Arrays.asList(new ItemStack(Items.BREAD), new ItemStack(Items.BREAD), new ItemStack(Items.BREAD), new ItemStack(ItemRegistry.BAGUETTE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> CHOCOLATES = new ArrayList<>(Arrays.asList(new ItemStack(Items.DYE,9,3), new ItemStack(ItemRegistry.SUGAR,3), new ItemStack(Items.MILK_BUCKET), new ItemStack(ItemRegistry.CHOCOLATES), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> GAUL_CAKE = new ArrayList<>(Arrays.asList(new ItemStack(Items.COOKIE), new ItemStack(ItemRegistry.CREAM), new ItemStack(Items.BEETROOT), new ItemStack(ItemRegistry.GAUL_CAKE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> VICTORIA_CAKE = new ArrayList<>(Arrays.asList(new ItemStack(Items.CAKE), new ItemStack(ItemRegistry.FAT), new ItemStack(Items.DYE,3,3), new ItemStack(ItemRegistry.VICTORIA_CAKE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> CANNED_VEGETABLES = new ArrayList<>(Arrays.asList(new ItemStack(Items.CARROT,9), new ItemStack(Items.POTATO,6), new ItemStack(Items.WHEAT,3), new ItemStack(ItemRegistry.CANNED_VEGETABLES), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> SANDWORM_CANNED = new ArrayList<>(Arrays.asList(new ItemStack(Items.SPIDER_EYE,3), new ItemStack(Items.ROTTEN_FLESH,3), new ItemStack(Items.GOLDEN_APPLE), new ItemStack(ItemRegistry.SANDWORM_CANNED), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> CANDY = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.FLOWER_FROST), new ItemStack(Items.SUGAR), new ItemStack(Items.PAPER), new ItemStack(ItemRegistry.CANDY), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> HONEY_CAKE = new ArrayList<>(Arrays.asList(new ItemStack(Items.WHEAT), new ItemStack(Items.WHEAT), new ItemStack(Items.SUGAR), new ItemStack(ItemRegistry.HONEY_CAKE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> CITY_STONE = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.TALA_STONE), new ItemStack(Blocks.STONEBRICK), new ItemStack(Blocks.STONEBRICK), new ItemStack(ItemRegistry.CITY_STONE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> FLOOR_BASE = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.CITY_STONE), new ItemStack(ItemRegistry.CARBON_PACK), new ItemStack(Blocks.IRON_BLOCK), new ItemStack(ItemRegistry.FLOOR_BASE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> ORIGINIUM_FLOOR = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.FLOOR_BASE), new ItemStack(ItemRegistry.ACTIVE_ORIGINIUM_BLOCK), ItemStack.EMPTY, new ItemStack(ItemRegistry.ORIGINIUM_FLOOR), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> DEFENSE_FLOOR = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.FLOOR_BASE), new ItemStack(Items.IRON_CHESTPLATE), ItemStack.EMPTY, new ItemStack(ItemRegistry.DEFENSE_FLOOR), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> PUSH_FLOOR = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.FLOOR_BASE), new ItemStack(Blocks.PISTON), ItemStack.EMPTY, new ItemStack(ItemRegistry.PUSH_FLOOR), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> HEALING_FLOOR = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.FLOOR_BASE), new ItemStack(ItemRegistry.THERAPEUTIC_POWDER,3), ItemStack.EMPTY, new ItemStack(ItemRegistry.HEALING_FLOOR), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> REDUCE_DEFENSE_FLOOR = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.FLOOR_BASE), new ItemStack(Items.IRON_AXE), ItemStack.EMPTY, new ItemStack(ItemRegistry.REDUCE_DEFENSE_FLOOR), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> HOT_FLOOR = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.TALA_STONE,6), new ItemStack(Blocks.MAGMA,3), new ItemStack(Blocks.OBSIDIAN), new ItemStack(ItemRegistry.HOT_FLOOR), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> MACHINE_BASE = new ArrayList<>(Arrays.asList(new ItemStack(Blocks.IRON_BLOCK), new ItemStack(ItemRegistry.DEVICE), new ItemStack(ItemRegistry.CRYSTALLINE_COMPONENT), new ItemStack(ItemRegistry.MACHINE_BASE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> CONTROL_CENTER = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.MACHINE_BASE), new ItemStack(ItemRegistry.KEEL), ItemStack.EMPTY, new ItemStack(ItemRegistry.CONTROL_CENTER), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> DORMITORY = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.MACHINE_BASE), new ItemStack(Items.BED), ItemStack.EMPTY, new ItemStack(ItemRegistry.DORMITORY), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> MANUFACTURING_STATION = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.MACHINE_BASE), new ItemStack(Blocks.ANVIL), ItemStack.EMPTY, new ItemStack(ItemRegistry.MANUFACTURING_STATION), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> OFFICE = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.MACHINE_BASE), new ItemStack(Items.BOOK), ItemStack.EMPTY, new ItemStack(ItemRegistry.OFFICE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> POWER_STATION = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.MACHINE_BASE), new ItemStack(Blocks.PISTON), ItemStack.EMPTY, new ItemStack(ItemRegistry.POWER_STATION), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> TRADING_STATION = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.MACHINE_BASE), new ItemStack(ItemRegistry.LMB), ItemStack.EMPTY, new ItemStack(ItemRegistry.TRADING_STATION), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> TRAINING_ROOM = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.MACHINE_BASE), new ItemStack(Items.WOODEN_SWORD), ItemStack.EMPTY, new ItemStack(ItemRegistry.TRAINING_ROOM), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> WEAPON_TABLE = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.MACHINE_BASE), new ItemStack(Items.IRON_SWORD), ItemStack.EMPTY, new ItemStack(ItemRegistry.WEAPON_TABLE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> ORIRON_PICKAXE = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.ORIRON,3), new ItemStack(Items.STICK), new ItemStack(Items.STICK), new ItemStack(ItemRegistry.ORIRON_PICKAXE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> ORIRON_AXE = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.ORIRON_CLUSTER), new ItemStack(Items.STICK), new ItemStack(Items.STICK), new ItemStack(ItemRegistry.ORIRON_AXE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> ORIRON_SHOVEL = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.ORIRON), new ItemStack(Items.STICK,3), ItemStack.EMPTY, new ItemStack(ItemRegistry.ORIRON_SHOVEL), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> RMA_PICKAXE = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.RMA_SHARD,3), new ItemStack(Items.STICK), new ItemStack(Items.STICK), new ItemStack(ItemRegistry.RMA_PICKAXE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> RMA_AXE = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.RMAY), new ItemStack(Items.STICK), new ItemStack(Items.STICK), new ItemStack(ItemRegistry.RMA_AXE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> RMA_SHOVEL = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.RMA_SHARD,1), new ItemStack(Items.STICK,3), ItemStack.EMPTY, new ItemStack(ItemRegistry.RMA_SHOVEL), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> INCANDESCENT_PICKAXE = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.INCANDESCENT_ALLOY_BLOCK,1), new ItemStack(Items.STICK), new ItemStack(Items.STICK), new ItemStack(ItemRegistry.INCANDESCENT_PICKAXE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> GRENADE = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SUGAR_SUBSTITUTE), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.GUNPOWDER), new ItemStack(ItemRegistry.GRENADE,2), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> INSTANT_BOMB = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.GRENADE), new ItemStack(ItemRegistry.ORI_KOHL), ItemStack.EMPTY, new ItemStack(ItemRegistry.INSTANT_BOMB), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> SEA_CRAWLER_ARMOR = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.IBERIA_STEM,4), new ItemStack(ItemRegistry.IBERIA_PETAL,8), new ItemStack(Items.IRON_INGOT,3), new ItemStack(ItemRegistry.SEA_CRAWLER_ARMOR), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> DEEP_SEA_SLIDER_ARMOR = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.IBERIA_STEM,9), new ItemStack(ItemRegistry.IBERIA_PETAL,3), new ItemStack(Items.STICK,8), new ItemStack(ItemRegistry.DEEP_SEA_SLIDER_ARMOR), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> BASIN_SEA_REAPER_ARMOR = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.IBERIA_PETAL,9), new ItemStack(Blocks.QUARTZ_BLOCK), ItemStack.EMPTY, new ItemStack(ItemRegistry.BASIN_SEA_REAPER_ARMOR), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> REGICIDE_ARMOR = new ArrayList<>(Arrays.asList(new ItemStack(Items.LEATHER_HELMET), new ItemStack(ItemRegistry.CARBON,3), new ItemStack(Items.STRING,3), new ItemStack(ItemRegistry.REGICIDE_ARMOR), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> DEFENSE_CRUSHER_CHEST = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_CHESTPLATE), new ItemStack(ItemRegistry.INCANDESCENT_ALLOY_BLOCK,4), new ItemStack(ItemRegistry.CARBON_BRICK,4), new ItemStack(ItemRegistry.DEFENSE_CRUSHER_CHEST), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> DEFENSE_CRUSHER_LEGS = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_LEGGINGS), new ItemStack(ItemRegistry.INCANDESCENT_ALLOY_BLOCK,2), new ItemStack(ItemRegistry.CARBON_BRICK,3), new ItemStack(ItemRegistry.DEFENSE_CRUSHER_LEGS), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> DEFENSE_CRUSHER_FEET = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_BOOTS), new ItemStack(ItemRegistry.INCANDESCENT_ALLOY_BLOCK,1), new ItemStack(ItemRegistry.CARBON_BRICK,2), new ItemStack(ItemRegistry.DEFENSE_CRUSHER_FEET), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> SENIOR_CASTER_HEAD = new ArrayList<>(Arrays.asList(new ItemStack(Items.LEATHER_HELMET), new ItemStack(ItemRegistry.ORIGINIUM_SHARD,1), new ItemStack(ItemRegistry.CARBON_PACK,1), new ItemStack(ItemRegistry.SENIOR_CASTER_HEAD), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> SENIOR_CASTER_CHEST = new ArrayList<>(Arrays.asList(new ItemStack(Items.LEATHER_CHESTPLATE), new ItemStack(ItemRegistry.ORIGINIUM_SHARD,2), new ItemStack(ItemRegistry.CARBON_PACK,2), new ItemStack(ItemRegistry.SENIOR_CASTER_CHEST), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> SOLDIER_HEAD = new ArrayList<>(Arrays.asList(new ItemStack(Items.LEATHER_HELMET), new ItemStack(ItemRegistry.CARBON,3), new ItemStack(ItemRegistry.DIKETON,2), new ItemStack(ItemRegistry.SOLDIER_HEAD), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> SOLDIER_CHEST = new ArrayList<>(Arrays.asList(new ItemStack(Items.LEATHER_CHESTPLATE), new ItemStack(ItemRegistry.CARBON,9), new ItemStack(ItemRegistry.DIKETON,6), new ItemStack(ItemRegistry.SOLDIER_CHEST), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> SOLDIER_LEGS = new ArrayList<>(Arrays.asList(new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(ItemRegistry.CARBON,6), new ItemStack(ItemRegistry.DIKETON,4), new ItemStack(ItemRegistry.SOLDIER_LEGS), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> SOLDIER_FEET = new ArrayList<>(Arrays.asList(new ItemStack(Items.LEATHER_BOOTS), new ItemStack(ItemRegistry.CARBON,3), new ItemStack(ItemRegistry.DIKETON,2), new ItemStack(ItemRegistry.SOLDIER_FEET), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> YETI_OPERATIVE_HEAD = new ArrayList<>(Arrays.asList(new ItemStack(Items.LEATHER_HELMET), new ItemStack(ItemRegistry.CARBON,3), new ItemStack(ItemRegistry.FLOWER_FROST,4), new ItemStack(ItemRegistry.YETI_OPERATIVE_HEAD), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> YETI_OPERATIVE_CHEST = new ArrayList<>(Arrays.asList(new ItemStack(Items.LEATHER_CHESTPLATE), new ItemStack(ItemRegistry.CARBON,9), new ItemStack(ItemRegistry.FLOWER_FROST,12), new ItemStack(ItemRegistry.YETI_OPERATIVE_CHEST), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> YETI_OPERATIVE_LEGS = new ArrayList<>(Arrays.asList(new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(ItemRegistry.CARBON,6), new ItemStack(ItemRegistry.FLOWER_FROST,8), new ItemStack(ItemRegistry.YETI_OPERATIVE_LEGS), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> YETI_OPERATIVE_FEET = new ArrayList<>(Arrays.asList(new ItemStack(Items.LEATHER_BOOTS), new ItemStack(ItemRegistry.CARBON,3), new ItemStack(ItemRegistry.FLOWER_FROST,4), new ItemStack(ItemRegistry.YETI_OPERATIVE_FEET), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> NORMAL_ARROW = new ArrayList<>(Arrays.asList(new ItemStack(Items.ARROW), new ItemStack(ItemRegistry.CARBON), ItemStack.EMPTY, new ItemStack(ItemRegistry.NORMAL_ARROW,2), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> FAUST_COMMON_ARROW = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.NORMAL_ARROW), new ItemStack(ItemRegistry.GRINDSTONE_SHARD), ItemStack.EMPTY, new ItemStack(ItemRegistry.FAUST_COMMON_ARROW), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> FAUST_SPECIAL_ARROW = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.FAUST_COMMON_ARROW), new ItemStack(ItemRegistry.MANGANESE_SHARD), ItemStack.EMPTY, new ItemStack(ItemRegistry.FAUST_SPECIAL_ARROW), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> GUERRILLA_AXE = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_AXE), new ItemStack(ItemRegistry.CARBON,4), new ItemStack(Items.IRON_INGOT,2), new ItemStack(ItemRegistry.GUERRILLA_AXE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> NORMAL_CROSSBOW = new ArrayList<>(Arrays.asList(new ItemStack(Items.BOW), new ItemStack(ItemRegistry.CARBON,3), new ItemStack(Items.IRON_INGOT,3), new ItemStack(ItemRegistry.NORMAL_CROSSBOW), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> URSUS_KNIFE = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_SWORD), new ItemStack(ItemRegistry.CARBON,1), new ItemStack(ItemRegistry.TALA_STONE), new ItemStack(ItemRegistry.URSUS_KNIFE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> NORMAL_MACHETE = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_SWORD), new ItemStack(ItemRegistry.CARBON,1), new ItemStack(ItemRegistry.INCANDESCENT_INGOT,1), new ItemStack(ItemRegistry.NORMAL_MACHETE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> NORMAL_SHIELD = new ArrayList<>(Arrays.asList(new ItemStack(Items.SHIELD), new ItemStack(ItemRegistry.CARBON,2), new ItemStack(ItemRegistry.ORI_KETONE,3), new ItemStack(ItemRegistry.NORMAL_SHIELD), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> DUAL_SWORD = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_SWORD), new ItemStack(Items.SHEARS), new ItemStack(ItemRegistry.CARBON,2), new ItemStack(ItemRegistry.DUAL_SWORD), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> FIREBOMB = new ArrayList<>(Arrays.asList(new ItemStack(Blocks.GLASS), new ItemStack(Items.PAPER), new ItemStack(ItemRegistry.ORI_KOHL,1), new ItemStack(ItemRegistry.FIREBOMB), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> PIPE = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_INGOT,3), new ItemStack(ItemRegistry.CARBON,3), ItemStack.EMPTY, new ItemStack(ItemRegistry.PIPE), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> STICK_HUGE = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.HUGE_WOOD), new ItemStack(ItemRegistry.HUGE_WOOD), new ItemStack(ItemRegistry.HUGE_WOOD), new ItemStack(Items.STICK,3), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> STICK_WILLOW = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.WILLOW_WOOD), new ItemStack(ItemRegistry.WILLOW_WOOD), new ItemStack(ItemRegistry.WILLOW_WOOD), new ItemStack(Items.STICK,3), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> WILLOW_WOOD = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.WILLOW_LOG), ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(ItemRegistry.WILLOW_WOOD,4), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> HUGE_WOOD = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.HUGE_LOG), ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(ItemRegistry.HUGE_WOOD,4), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> ORIRON_HEAD = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_HELMET), new ItemStack(ItemRegistry.ORIRON,5), ItemStack.EMPTY, new ItemStack(ItemRegistry.ORIRON_HEAD), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> ORIRON_CHEST = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_CHESTPLATE), new ItemStack(ItemRegistry.ORIRON,8), ItemStack.EMPTY, new ItemStack(ItemRegistry.ORIRON_CHEST), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> ORIRON_LEGS = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_LEGGINGS), new ItemStack(ItemRegistry.ORIRON,7), ItemStack.EMPTY, new ItemStack(ItemRegistry.ORIRON_LEGS), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> ORIRON_FEET = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_BOOTS), new ItemStack(ItemRegistry.ORIRON,4), ItemStack.EMPTY, new ItemStack(ItemRegistry.ORIRON_FEET), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> ORIROCK_HEAD = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_HELMET), new ItemStack(ItemRegistry.ORIROCK_CLUSTER,5), ItemStack.EMPTY, new ItemStack(ItemRegistry.ORIROCK_HEAD), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> ORIROCK_CHEST = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_CHESTPLATE), new ItemStack(ItemRegistry.ORIROCK_CLUSTER,8), ItemStack.EMPTY, new ItemStack(ItemRegistry.ORIROCK_CHEST), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> ORIROCK_LEGS = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_LEGGINGS), new ItemStack(ItemRegistry.ORIROCK_CLUSTER,7), ItemStack.EMPTY, new ItemStack(ItemRegistry.ORIROCK_LEGS), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> ORIROCK_FEET = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_BOOTS), new ItemStack(ItemRegistry.ORIROCK_CLUSTER,4), ItemStack.EMPTY, new ItemStack(ItemRegistry.ORIROCK_FEET), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> MANGANESE_HEAD = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_HELMET), new ItemStack(ItemRegistry.MANGANESE_SHARD,5), ItemStack.EMPTY, new ItemStack(ItemRegistry.MANGANESE_HEAD), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> MANGANESE_CHEST = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_CHESTPLATE), new ItemStack(ItemRegistry.MANGANESE_SHARD,8), ItemStack.EMPTY, new ItemStack(ItemRegistry.MANGANESE_CHEST), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> MANGANESE_LEGS = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_LEGGINGS), new ItemStack(ItemRegistry.MANGANESE_SHARD,7), ItemStack.EMPTY, new ItemStack(ItemRegistry.MANGANESE_LEGS), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> MANGANESE_FEET = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_BOOTS), new ItemStack(ItemRegistry.MANGANESE_SHARD,4), ItemStack.EMPTY, new ItemStack(ItemRegistry.MANGANESE_FEET), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> GRINDSTONE_HEAD = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_HELMET), new ItemStack(ItemRegistry.GRINDSTONE_SHARD,5), ItemStack.EMPTY, new ItemStack(ItemRegistry.GRINDSTONE_HEAD), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> GRINDSTONE_CHEST = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_CHESTPLATE), new ItemStack(ItemRegistry.GRINDSTONE_SHARD,8), ItemStack.EMPTY, new ItemStack(ItemRegistry.GRINDSTONE_CHEST), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> GRINDSTONE_LEGS = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_LEGGINGS), new ItemStack(ItemRegistry.GRINDSTONE_SHARD,7), ItemStack.EMPTY, new ItemStack(ItemRegistry.GRINDSTONE_LEGS), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> GRINDSTONE_FEET = new ArrayList<>(Arrays.asList(new ItemStack(Items.IRON_BOOTS), new ItemStack(ItemRegistry.GRINDSTONE_SHARD,4), ItemStack.EMPTY, new ItemStack(ItemRegistry.GRINDSTONE_FEET), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> MY_INFO = new ArrayList<>(Arrays.asList(new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(ItemRegistry.MY_INFO), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> MOD_BOX = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.MACHINE_BASE), new ItemStack(Blocks.GOLD_BLOCK), ItemStack.EMPTY, new ItemStack(ItemRegistry.MOD_BOX), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> OPERATOR_BOX = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.MACHINE_BASE), new ItemStack(Items.DIAMOND), ItemStack.EMPTY, new ItemStack(ItemRegistry.OPERATOR_BOX), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> ORIGINITE_PRIME = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.ORIGINIUM_SHARD,12), ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(ItemRegistry.ORIGINITE_PRIME), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> LMB_HUNDRED = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.LMB,50), new ItemStack(ItemRegistry.LMB,50), ItemStack.EMPTY, new ItemStack(ItemRegistry.LMB_HUNDRED), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> ORUNDUM = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.ORIGINITE_PRIME), ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(ItemRegistry.ORUNDUM,9), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> HEADHUNT = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.ORUNDUM,30), ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(ItemRegistry.HEADHUNT), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> HEADHUNT_TEN = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.HEADHUNT,10), ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(ItemRegistry.HEADHUNT_TEN), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> SIGN_ARROW = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.NORMAL_ARROW), new ItemStack(Items.REDSTONE), ItemStack.EMPTY, new ItemStack(ItemRegistry.SIGN_ARROW,2), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> AMIYA_PRO = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.AMIYA_CARD), ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(ItemRegistry.AMIYA_PRO), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));
    public static final List<ItemStack> SURTR_PRO = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.SURTR_CARD), ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(ItemRegistry.SURTR_PRO), new ItemStack(ItemRegistry.LMB, 0), new ItemStack(ItemRegistry.LMB, 1)));

    //lmb5，motion
    public static List<List<ItemStack>> CRAFTING_TABLE = new ArrayList<>(Arrays.asList(CRYSTALLINE_ELECTRONIC_UNIT,
            D32_STEEL,BIPOLAR_NANOFLAKE,POLYMERIZATION_PREPARATION,CRYSTALLINE_CIRCUIT,INCANDESCENT_ALLOY_BLOCK,POLYMERIZED_GEL,
            RMAR,GRINDSTONE_PENTAHYDRATE,MANGANESE_TRIHYDRATE,WHITE_HORSE_KOHL,OPTIMIZED_DEVICE,KETON_COLLOID,ORIRON_BLOCK,
            POLYESTER_LUMP,SUGAR_LUMP,DEVICE,INTEGRATED_DEVICE,POLYKETON,AKETON,ORIRON,ORIRON_CLUSTER,POLYESTER,POLYESTER_PACK,
            SUGAR,SUGAR_PACK,ORIROCK_CUBE,ORIROCK_CLUSTER,ORIROCK_CONCENTRATION,LIGHT_BUILDING_MATERIAL,CONCRETE_BUILDING_MATERIAL,
            REINFORCED_BUILDING_MATERIAL,CARBON_BRICK,CARBON_PACK,SKILL_SUMMARY_II,SKILL_SUMMARY_III,DEFENDER_CHIP,MEDIC_CHIP,
            SNIPER_CHIP,CASTER_CHIP,GUARD_CHIP,SPECIALIST_CHIP,VANGUARD_CHIP,SUPPORTER_CHIP,DEFENDER_CHIPSET,MEDIC_CHIPSET,
            SNIPER_CHIPSET,CASTER_CHIPSET,GUARD_CHIPSET,SPECIALIST_CHIPSET,VANGUARD_CHIPSET,SUPPORTER_CHIPSET,SUGAR_SUBSTITUTE,
            TWO_LOAVES_WITH_CHEESE,URSUS_BREAD,DRIED_CACTUS,BISCUIT,BLACK_TEA,HIBISCUS_HEALTHY_FOOD,ASCETIC_PUFF,BAGUETTE,
            CHOCOLATES,GAUL_CAKE,VICTORIA_CAKE,CANNED_VEGETABLES,HONEY_CAKE,CANDY,CITY_STONE,FLOOR_BASE,ORIGINIUM_FLOOR,
            DEFENSE_FLOOR,PUSH_FLOOR,HEALING_FLOOR,REDUCE_DEFENSE_FLOOR,HOT_FLOOR,MACHINE_BASE,CONTROL_CENTER,DORMITORY,
            MANUFACTURING_STATION,OFFICE,POWER_STATION,TRADING_STATION,TRAINING_ROOM,WEAPON_TABLE,ORIRON_PICKAXE,ORIRON_AXE,
            ORIRON_SHOVEL,RMA_PICKAXE,RMA_AXE,RMA_SHOVEL,INCANDESCENT_PICKAXE,GRENADE,INSTANT_BOMB,SEA_CRAWLER_ARMOR,
            DEEP_SEA_SLIDER_ARMOR,BASIN_SEA_REAPER_ARMOR,REGICIDE_ARMOR,DEFENSE_CRUSHER_CHEST,DEFENSE_CRUSHER_LEGS,
            DEFENSE_CRUSHER_FEET,SENIOR_CASTER_HEAD,SENIOR_CASTER_CHEST,SOLDIER_HEAD,SOLDIER_CHEST,SOLDIER_LEGS,DUAL_SWORD,
            SOLDIER_FEET,YETI_OPERATIVE_HEAD,YETI_OPERATIVE_CHEST,YETI_OPERATIVE_LEGS,YETI_OPERATIVE_FEET,NORMAL_ARROW,
            FAUST_COMMON_ARROW,FAUST_SPECIAL_ARROW,GUERRILLA_AXE,NORMAL_CROSSBOW,URSUS_KNIFE,NORMAL_MACHETE,NORMAL_SHIELD,
            FIREBOMB,PIPE,STICK_HUGE,STICK_WILLOW,WILLOW_WOOD,HUGE_WOOD,ORIRON_HEAD,ORIRON_CHEST,ORIRON_LEGS,ORIRON_FEET,
            ORIROCK_HEAD,ORIROCK_CHEST,ORIROCK_LEGS,ORIROCK_FEET,MANGANESE_HEAD,MANGANESE_CHEST,MANGANESE_LEGS,MANGANESE_FEET,
            GRINDSTONE_HEAD,GRINDSTONE_CHEST,GRINDSTONE_LEGS,GRINDSTONE_FEET,SANDWORM_CANNED,MY_INFO,MOD_BOX,OPERATOR_BOX,
            ORIGINITE_PRIME,LMB_HUNDRED,ORUNDUM,HEADHUNT,HEADHUNT_TEN,SIGN_ARROW,AMIYA_PRO,SURTR_PRO));

    public static void addCraftingTable(List<ItemStack> list)
    {
        CRAFTING_TABLE.add(list);
    }

    public ProcessingStationContainer(TileEntity tile, EntityPlayer player)
    {
        this.player = player;
        this.tile =  tile;

        for(int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 10 + i * 18, 176));
        }

        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.addSlotToContainer(new Slot(player.inventory, j1 + (l + 1) * 9, 10 + j1 * 18, 118 + l * 18));
            }
        }

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 0, 82, 59)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 1, 82, 20)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 2, 47, 83)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), 3, 117, 83)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 64;
            }
        });
    }

    @Override
    public void onButtonPress(int buttonID)
    {
        IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);

        this.oriStacks = new ArrayList<>(Arrays.asList(handler.getStackInSlot(1), handler.getStackInSlot(2), handler.getStackInSlot(3)));

        this.adjStacks = new ArrayList<>(Arrays.asList(ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY));

        this.linkSer = new ArrayList<>(Arrays.asList(0,0,0));

        if(buttonID==0)
        {
            for(List<ItemStack> s : CRAFTING_TABLE)
            {
                List<ItemStack> table = s.subList(0,s.size()-3);

                if(ArkItemUtil.isEqualSize(oriStacks,table) && ListUtil.isListEqual(oriStacks.stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList()), table.stream().map(ItemStack::getUnlocalizedName).collect(Collectors.toList())))
                {
                    for(int j=0; j<3; j++)
                    {
                        for (int i = 0; i < 3; i++)
                        {
                            if(oriStacks.get(j).getUnlocalizedName().equals(table.get(i).getUnlocalizedName()))
                            {
                                this.adjStacks.set(i, oriStacks.get(j));//合成表序号，放入物品
                                this.linkSer.set(j,i);//放入物序号，合成表序号
                            }
                        }
                    }

                    int n1 = table.get(0).getCount()>0 ? adjStacks.get(0).getCount()/table.get(0).getCount() : 0;

                    int n2 = table.get(1).getCount()>0 ? adjStacks.get(1).getCount()/table.get(1).getCount() : 0;

                    int n3 = table.get(2).getCount()>0 ? adjStacks.get(2).getCount()/table.get(2).getCount() : 0;

                    List<Integer> ns = new ArrayList<>(Arrays.asList(n1,n2,n3));

                    List<Integer> mins = new ArrayList<>();

                    for(int i : ns)
                    {
                        if(i>0)
                            mins.add(i);
                    }

                    int min;

                    if(mins.size()>0)
                        min = ListUtil.getMin(mins.stream().mapToInt(Integer::intValue).toArray());
                    else
                        min=0;

                    if(min>0 && (handler.getStackInSlot(0).isEmpty() || (handler.getStackInSlot(0).getItem()==s.get(s.size()-3).getItem() && handler.getStackInSlot(0).getCount()<64)) && player.getCapability(CapabilityRegistry.capValue,null).getLmb()>=s.get(s.size()-2).getCount()*5*min)
                    {
                        handler.insertItem(0,new ItemStack(s.get(s.size()-3).getItem(),min*s.get(s.size()-3).getCount()),false);

                        handler.getStackInSlot(1).shrink(min*table.get(linkSer.get(0)).getCount());

                        handler.getStackInSlot(2).shrink(min*table.get(linkSer.get(1)).getCount());

                        handler.getStackInSlot(3).shrink(min*table.get(linkSer.get(2)).getCount());

                        player.getCapability(CapabilityRegistry.capValue,null).setLmb(player.getCapability(CapabilityRegistry.capValue,null).getLmb()-s.get(s.size()-2).getCount()*5*min);

                        mins.clear();
                    }
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn)
    {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 36)
            {
                if (!this.mergeItemStack(itemstack1, 36, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 36, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }
}
