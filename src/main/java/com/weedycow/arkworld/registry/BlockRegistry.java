package com.weedycow.arkworld.registry;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.doll.*;
import com.weedycow.arkworld.block.floor.*;
import com.weedycow.arkworld.block.machine.BlockMachineBase;
import com.weedycow.arkworld.block.machine.BlockModBox;
import com.weedycow.arkworld.block.machine.BlockOperatorBox;
import com.weedycow.arkworld.block.machine.BlockWeaponTable;
import com.weedycow.arkworld.block.machine.infrastructure.*;
import com.weedycow.arkworld.block.nature.plant.*;
import com.weedycow.arkworld.block.nature.surface.*;
import com.weedycow.arkworld.block.nature.tree.huge.BlockHugeLeaf;
import com.weedycow.arkworld.block.nature.tree.huge.BlockHugeLog;
import com.weedycow.arkworld.block.nature.tree.huge.BlockHugeSapling;
import com.weedycow.arkworld.block.nature.tree.huge.BlockHugeWood;
import com.weedycow.arkworld.block.nature.tree.willow.BlockWillowLeaf;
import com.weedycow.arkworld.block.nature.tree.willow.BlockWillowLog;
import com.weedycow.arkworld.block.nature.tree.willow.BlockWillowSapling;
import com.weedycow.arkworld.block.nature.tree.willow.BlockWillowWood;
import com.weedycow.arkworld.block.ore.*;
import com.weedycow.arkworld.block.other.BlockPortalArkworld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class BlockRegistry
{
	public static final BlockFrostnovaDoll FROSTNOVA_DOLL = new BlockFrostnovaDoll();
	public static final BlockPatriotDoll PATRIOT_DOLLL = new BlockPatriotDoll();
	public static final BlockMayfestDoll MAYFEST_DOLL = new BlockMayfestDoll();
	public static final BlockFaustDoll FAUST_DOLLL = new BlockFaustDoll();
	public static final BlockRegicideDoll REGICIDE_DOLL = new BlockRegicideDoll();
	public static final BlockWDoll W_DOLL = new BlockWDoll();
	public static final BlockBlackSnakeDoll TALULAH_DOLL = new BlockBlackSnakeDoll();
	public static final BlockSkullshattererDoll SKULLSHATTERER_DOLL = new BlockSkullshattererDoll();
	public static final BlockEvolutionDoll EVOLUTION_DOLL = new BlockEvolutionDoll();
	public static final BlockSealedFloor SEALED_FLOOR = new BlockSealedFloor();
	public static final BlockOriginiumFloor ORIGINIUM_FLOOR = new BlockOriginiumFloor();
	public static final BlockProcessingStation PROCESSING_STATION = new BlockProcessingStation();
	public static final BlockTrainingRoom TRAINING_ROOM = new BlockTrainingRoom();
	public static final BlockControlCenter CONTROL_CENTER = new BlockControlCenter();
	public static final BlockOffice OFFICE = new BlockOffice();
	public static final BlockDormitory DORMITORY = new BlockDormitory();
	public static final BlockManufacturingStation MANUFACTURING_STATION = new BlockManufacturingStation();
	public static final BlockTradingStation TRADING_STATION = new BlockTradingStation();
	public static final BlockPowerStation POWER_STATION = new BlockPowerStation();
	public static final BlockWeaponTable WEAPON_TABLE = new BlockWeaponTable();
	public static final BlockGrassWithered GRASS_WITHERED = new BlockGrassWithered();
	public static final BlockDerelictDirt DERELICT_DIRT = new BlockDerelictDirt();
	public static final BlockGrassVigour GRASS_VIGOUR = new BlockGrassVigour();
	public static final BlockFertileDirt FERTILE_DIRT = new BlockFertileDirt();
	public static final BlockCrudeGoldOre CRUDE_GOLD_ORE = new BlockCrudeGoldOre();
	public static final BlockIncandescentOre INCANDESCENT_ORE = new BlockIncandescentOre();
	public static final BlockMixedGoldOre MIXED_GOLD_ORE = new BlockMixedGoldOre();
	public static final BlockOricoalOre ORICOAL_ORE = new BlockOricoalOre();
	public static final BlockOriginiumOre ORIGINIUM_ORE = new BlockOriginiumOre();
	public static final BlockOrimanganeseOre ORIMANGANESE_ORE = new BlockOrimanganeseOre();
	public static final BlockOrirockOre ORIROCK_ORE = new BlockOrirockOre();
	public static final BlockOrironOre ORIRON_ORE = new BlockOrironOre();
	public static final BlockWeaponGemOre WEAPON_GEM_ORE = new BlockWeaponGemOre();
	public static final BlockGrindstoneOre GRINDSTONE_ORE = new BlockGrindstoneOre();
	public static final BlockOriginiumCrystal ORIGINIUM_CRYSTAL = new BlockOriginiumCrystal();
	public static final BlockPortalArkworld PORTAL_ARKWORLD = new BlockPortalArkworld();
	public static final BlockTalaStone TALA_STONE = new BlockTalaStone();
	public static final BlockWitheredGrass WITHERED_GRASS = new BlockWitheredGrass();
	public static final BlockWillowLeaf WILLOW_LEAF = new BlockWillowLeaf();
	public static final BlockWillowLog WILLOW_LOG = new BlockWillowLog();
	public static final BlockWillowSapling WILLOW_SAPLING = new BlockWillowSapling();
	public static final BlockWillowWood WILLOW_WOOD = new BlockWillowWood();
	public static final BlockFrozenDirt FROZEN_DIRT = new BlockFrozenDirt();
	public static final BlockGrassFrozen GRASS_FROZEN = new BlockGrassFrozen();
	public static final BlockFlowerFrost FLOWER_FROST = new BlockFlowerFrost();
	public static final BlockMoss MOSS = new BlockMoss();
	public static final BlockActiveOriginiumBlock ACTIVE_ORIGINIUM_BLOCK = new BlockActiveOriginiumBlock();
	public static final BlockOriginiumBlock ORIGINIUM_BLOCK = new BlockOriginiumBlock();
	public static final BlockHugeLeaf HUGE_LEAF = new BlockHugeLeaf();
	public static final BlockHugeSapling HUGE_SAPLING = new BlockHugeSapling();
	public static final BlockHugeWood HUGE_WOOD = new BlockHugeWood();
	public static final BlockHugeLog HUGE_LOG = new BlockHugeLog();
	public static final BlockNiceMushroom NICE_MUSHROOM = new BlockNiceMushroom();
	public static final BlockMyceliumVine MYCELIUM_VINE = new BlockMyceliumVine();
	public static final BlockInactivationOriginiumBlock INACTIVATION_ORIGINIUM_BLOCK = new BlockInactivationOriginiumBlock();
	public static final BlockIberiaDirt IBERIA_DIRT = new BlockIberiaDirt();
	public static final BlockSeagrass SEAGRASS = new BlockSeagrass();
	public static final BlockIberiaFlower IBERIA_FLOWER = new BlockIberiaFlower();
	public static final BlockIberiaBigFlower IBERIA_BIG_FLOWER = new BlockIberiaBigFlower();
	public static final BlockDripstone DRIPSTONE = new BlockDripstone();
	public static final BlockFungusBlanket FUNGUS_BLANKET = new BlockFungusBlanket();
	public static final BlockIberiaWaterlily IBERIA_WATERLILY = new BlockIberiaWaterlily();
	public static final BlockCityStone CITY_STONE = new BlockCityStone();
	public static final BlockTeaPlant TEA_PLANT = new BlockTeaPlant();
	public static final BlockRmaOre RMA_ORE = new BlockRmaOre();
	public static final BlockFloorBase FLOOR_BASE = new BlockFloorBase();
	public static final BlockDefenseFloor DEFENSE_FLOOR = new BlockDefenseFloor();
	public static final BlockPushFloor PUSH_FLOOR = new BlockPushFloor();
	public static final BlockHealingFloor HEALING_FLOOR = new BlockHealingFloor();
	public static final BlockHotFloor HOT_FLOOR = new BlockHotFloor();
	public static final BlockReduceDefenseFloor REDUCE_DEFENSE_FLOOR = new BlockReduceDefenseFloor();
	public static final BlockMachineBase MACHINE_BASE = new BlockMachineBase();
	public static final BlockModBox MOD_BOX = new BlockModBox();
	public static final BlockOperatorBox OPERATOR_BOX = new BlockOperatorBox();
	public static final BlockOriginiumCrystalHuge ORIGINIUM_CRYSTAL_HUGE = new BlockOriginiumCrystalHuge();

	public static final BlockOriginiumContaminatedWater ORIGINIUM_CONTAMINATED_WATER
			= new BlockOriginiumContaminatedWater(FluidRegistry.ORIGINIUM_CONTAMINATED_WATER, Material.WATER);
	public static final BlockIberiaWater IBERIA_WATER
			= new BlockIberiaWater(FluidRegistry.IBERIA_WATER, Material.WATER);

	static Block[] blocks = {FROSTNOVA_DOLL,PATRIOT_DOLLL,MAYFEST_DOLL,FAUST_DOLLL,REGICIDE_DOLL,W_DOLL,TALULAH_DOLL,SKULLSHATTERER_DOLL,
			EVOLUTION_DOLL,SEALED_FLOOR,ORIGINIUM_FLOOR,PROCESSING_STATION,TRAINING_ROOM,CONTROL_CENTER,OFFICE,DORMITORY,MANUFACTURING_STATION,
			TRADING_STATION,POWER_STATION,WEAPON_TABLE,GRASS_WITHERED,DERELICT_DIRT,GRASS_VIGOUR,FERTILE_DIRT,CRUDE_GOLD_ORE,INCANDESCENT_ORE,
			MIXED_GOLD_ORE,ORICOAL_ORE,ORIGINIUM_ORE,ORIMANGANESE_ORE,ORIROCK_ORE,ORIRON_ORE,WEAPON_GEM_ORE,GRINDSTONE_ORE,ORIGINIUM_CRYSTAL,
			PORTAL_ARKWORLD,TALA_STONE,WITHERED_GRASS,WILLOW_LEAF,WILLOW_LOG,WILLOW_SAPLING,WILLOW_WOOD,ORIGINIUM_CONTAMINATED_WATER,FROZEN_DIRT,
			GRASS_FROZEN,FLOWER_FROST,MOSS,ACTIVE_ORIGINIUM_BLOCK,ORIGINIUM_BLOCK,INACTIVATION_ORIGINIUM_BLOCK,HUGE_LEAF,HUGE_SAPLING,HUGE_WOOD,
			HUGE_LOG,NICE_MUSHROOM,MYCELIUM_VINE,IBERIA_WATER,IBERIA_DIRT,SEAGRASS,IBERIA_FLOWER,IBERIA_BIG_FLOWER,DRIPSTONE,FUNGUS_BLANKET,
			IBERIA_WATERLILY,CITY_STONE,TEA_PLANT,RMA_ORE,FLOOR_BASE,DEFENSE_FLOOR,PUSH_FLOOR,HEALING_FLOOR,HOT_FLOOR,REDUCE_DEFENSE_FLOOR,
			MACHINE_BASE,MOD_BOX,OPERATOR_BOX,ORIGINIUM_CRYSTAL_HUGE};

	public static Class[] tileEntity = {BlockEvolutionDoll.TileEvolutionDoll.class, BlockFaustDoll.TileFaustDoll.class,
			BlockFrostnovaDoll.TileFrostnovaDoll.class, BlockMayfestDoll.TileMayfestDoll.class, BlockPatriotDoll.TilePatriotDoll.class,
			BlockRegicideDoll.TileRegicideDoll.class, BlockSkullshattererDoll.TileSkullshattererDoll.class, BlockBlackSnakeDoll.TileBlackSnakeDoll.class,
			BlockWDoll.TileWDoll.class, BlockSealedFloor.TileSealedFloor.class,BlockOriginiumFloor.TileOriginiumFloor.class,
			BlockProcessingStation.TileProcessingStation.class,BlockTrainingRoom.TileTrainingRoom.class,BlockControlCenter.TileControlCenter.class,
			BlockOffice.TileOffice.class,BlockDormitory.TileDormitory.class,BlockManufacturingStation.TileManufacturingStation.class,
			BlockTradingStation.TileTradingStation.class, BlockPowerStation.TilePowerStation.class, BlockWeaponTable.TileWeaponTable.class,
			BlockSeagrass.TileSeagrass.class, BlockIberiaFlower.TileIberiaFlower.class, BlockIberiaBigFlower.TileIberiaBigFlower.class,
			BlockDripstone.TileDripstone.class, BlockFloorBase.TileFloorBase.class,	BlockDefenseFloor.TileDefenseFloor.class,
			BlockPushFloor.TilePushFloor.class, BlockHealingFloor.TileHealingFloor.class, BlockHotFloor.TileHotFloor.class,
			BlockReduceDefenseFloor.TileReduceDefenseFloor.class,BlockModBox.TileModBox.class,BlockOperatorBox.TileOperatorBox.class};

	@SubscribeEvent
	public static void onRegistry(Register<Block> event)
	{
		IForgeRegistry<Block> registry = event.getRegistry();
		registry.registerAll(blocks);
		for (Class cl:tileEntity){GameRegistry.registerTileEntity(cl, new ResourceLocation(Arkworld.MODID, cl.getSimpleName()));}
	}
}
