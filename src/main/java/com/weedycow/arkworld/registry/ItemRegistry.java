package com.weedycow.arkworld.registry;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.item.armor.*;
import com.weedycow.arkworld.item.block.doll.*;
import com.weedycow.arkworld.item.block.floor.*;
import com.weedycow.arkworld.item.block.machine.*;
import com.weedycow.arkworld.item.block.nature.plant.*;
import com.weedycow.arkworld.item.block.nature.surface.*;
import com.weedycow.arkworld.item.block.nature.tree.huge.ItemHugeLeaf;
import com.weedycow.arkworld.item.block.nature.tree.huge.ItemHugeLog;
import com.weedycow.arkworld.item.block.nature.tree.huge.ItemHugeSapling;
import com.weedycow.arkworld.item.block.nature.tree.huge.ItemHugeWood;
import com.weedycow.arkworld.item.block.nature.tree.willow.ItemWillowLeaf;
import com.weedycow.arkworld.item.block.nature.tree.willow.ItemWillowLog;
import com.weedycow.arkworld.item.block.nature.tree.willow.ItemWillowSapling;
import com.weedycow.arkworld.item.block.nature.tree.willow.ItemWillowWood;
import com.weedycow.arkworld.item.block.ore.*;
import com.weedycow.arkworld.item.block.other.ItemPortalArkworld;
import com.weedycow.arkworld.item.food.*;
import com.weedycow.arkworld.item.material.battle_record.*;
import com.weedycow.arkworld.item.material.chip.*;
import com.weedycow.arkworld.item.material.crystallines.CrystallineCircuit;
import com.weedycow.arkworld.item.material.crystallines.CrystallineElectronicUnit;
import com.weedycow.arkworld.item.material.crystallines.CystallineComponent;
import com.weedycow.arkworld.item.material.devices.DamagedDevice;
import com.weedycow.arkworld.item.material.devices.Device;
import com.weedycow.arkworld.item.material.devices.IntegratedDevice;
import com.weedycow.arkworld.item.material.devices.OptimizedDevice;
import com.weedycow.arkworld.item.material.esters.*;
import com.weedycow.arkworld.item.material.gel.CoagulatingGel;
import com.weedycow.arkworld.item.material.gel.Gel;
import com.weedycow.arkworld.item.material.gel.PolymerizedGel;
import com.weedycow.arkworld.item.material.gold.CrudeGold;
import com.weedycow.arkworld.item.material.gold.MixedGold;
import com.weedycow.arkworld.item.material.gold.PureGold;
import com.weedycow.arkworld.item.material.grindstone.Grindstone;
import com.weedycow.arkworld.item.material.grindstone.GrindstonePentahydrate;
import com.weedycow.arkworld.item.material.grindstone.GrindstoneShard;
import com.weedycow.arkworld.item.material.iberia.DripstoneShard;
import com.weedycow.arkworld.item.material.iberia.IberiaPetal;
import com.weedycow.arkworld.item.material.iberia.IberiaStem;
import com.weedycow.arkworld.item.material.incandescent.IncandescentAlloy;
import com.weedycow.arkworld.item.material.incandescent.IncandescentAlloyBlock;
import com.weedycow.arkworld.item.material.incandescent.IncandescentIngot;
import com.weedycow.arkworld.item.material.infrastructure.*;
import com.weedycow.arkworld.item.material.ketons.*;
import com.weedycow.arkworld.item.material.kohls.LoxicKohl;
import com.weedycow.arkworld.item.material.kohls.OriKohl;
import com.weedycow.arkworld.item.material.kohls.WhiteHorseKohl;
import com.weedycow.arkworld.item.material.manganeses.ManganeseOre;
import com.weedycow.arkworld.item.material.manganeses.ManganeseShard;
import com.weedycow.arkworld.item.material.manganeses.ManganeseTrihydrate;
import com.weedycow.arkworld.item.material.orirocks.Orirock;
import com.weedycow.arkworld.item.material.orirocks.OrirockCluster;
import com.weedycow.arkworld.item.material.orirocks.OrirockConcentration;
import com.weedycow.arkworld.item.material.orirocks.OrirockCube;
import com.weedycow.arkworld.item.material.orirons.Oriron;
import com.weedycow.arkworld.item.material.orirons.OrironBlock;
import com.weedycow.arkworld.item.material.orirons.OrironCluster;
import com.weedycow.arkworld.item.material.orirons.OrironShard;
import com.weedycow.arkworld.item.material.other.BipolarNanoflake;
import com.weedycow.arkworld.item.material.other.ChipCatalyst;
import com.weedycow.arkworld.item.material.other.DSteel;
import com.weedycow.arkworld.item.material.other.PolymerizationPreparation;
import com.weedycow.arkworld.item.material.rmas.RmaShard;
import com.weedycow.arkworld.item.material.rmas.Rmar;
import com.weedycow.arkworld.item.material.rmas.Rmay;
import com.weedycow.arkworld.item.material.skill.SkillSummaryI;
import com.weedycow.arkworld.item.material.skill.SkillSummaryII;
import com.weedycow.arkworld.item.material.skill.SkillSummaryIII;
import com.weedycow.arkworld.item.material.sugars.Sugar;
import com.weedycow.arkworld.item.material.sugars.SugarLump;
import com.weedycow.arkworld.item.material.sugars.SugarPack;
import com.weedycow.arkworld.item.material.sugars.SugarSubstitute;
import com.weedycow.arkworld.item.normal.*;
import com.weedycow.arkworld.item.operator.*;
import com.weedycow.arkworld.item.tool.ammo.*;
import com.weedycow.arkworld.item.tool.tool.*;
import com.weedycow.arkworld.item.tool.weapon.*;
import com.weedycow.arkworld.renderer.RenderItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@EventBusSubscriber
public class ItemRegistry
{
	public static final Orundum ORUNDUM = new Orundum();
	public static final HeadhuntTen HEADHUNT_TEN = new HeadhuntTen();
	public static final OriginiumShard ORIGINIUM_SHARD = new OriginiumShard();
	public static final OriginitePrime ORIGINITE_PRIME = new OriginitePrime();
	public static final OrundumTwoHundred ORUNDUM_TWOHUNDRED = new OrundumTwoHundred();
	public static final Lmb LMB = new Lmb();
	public static final Headhunt HEADHUNT = new Headhunt();
	public static final ExpCard EXP_CARD = new ExpCard();
	public static final MyInfo MY_INFO = new MyInfo();
	public static final ArmsMod ARMS_MOD = new ArmsMod();
	public static final DefenceMod DEFENCE_MOD = new DefenceMod();
	public static final PlayerMod PLAYER_MOD = new PlayerMod();
	public static final SkillSummaryI SKILL_SUMMARY_I = new SkillSummaryI();
	public static final SkillSummaryII SKILL_SUMMARY_II = new SkillSummaryII();
	public static final SkillSummaryIII SKILL_SUMMARY_III = new SkillSummaryIII();
	public static final AmiyaCard AMIYA_CARD = new AmiyaCard();
	public static final AmiyaPro AMIYA_PRO = new AmiyaPro();
	public static final NormalWeaponMod NORMAL_WEAPON_MOD = new NormalWeaponMod();
	public static final HighWeaponMod HIGH_WEAPON_MOD = new HighWeaponMod();
	public static final EpicWeaponMod EPIC_WEAPON_MOD = new EpicWeaponMod();
	public static final ItemGrassWithered GRASS_WITHERED = new ItemGrassWithered();
	public static final ItemNiceMushroom NICE_MUSHROOM = new ItemNiceMushroom();
	public static final ItemIberiaDirt IBERIA_DIRT = new ItemIberiaDirt();
	public static final TwoLoavesWithCheese TWO_LOAVES_WITH_CHEESE = new TwoLoavesWithCheese();
	public static final EmergencySanityPotion EMERGENCY_SANITY_POTION = new EmergencySanityPotion();
	public static final EmergencySanitySampler EMERGENCY_SANITY_SAMPLER = new EmergencySanitySampler();
	public static final TopEmergencySanityPotion TOP_EMERGENCY_SANITY_POTION = new TopEmergencySanityPotion();
	public static final PatriotShield PATRIOT_SHIELD = new PatriotShield();
	public static final FaustCrossbow FAUST_CROSSBOW = new FaustCrossbow();
	public static final SmashingHammer SMASHING_HAMMER = new SmashingHammer();
	public static final FaustCommonArrow FAUST_COMMON_ARROW = new FaustCommonArrow();
	public static final FaustSpecialArrow FAUST_SPECIAL_ARROW = new FaustSpecialArrow();
	public static final GrenadeGun GRENADE_GUN = new GrenadeGun();
	public static final TalulahSword TALULAH_SWORD = new TalulahSword();
	public static final GuerrillaShield GUERRILLA_SHIELD = new GuerrillaShield();
	public static final MagicSword MAGIC_SWORD = new MagicSword();
	public static final GuerrillaAxe GUERRILLA_AXE = new GuerrillaAxe();
	public static final AvengerSword AVENGER_SWORD = new AvengerSword();
	public static final IcecleaverSword ICECLEAVER_SWORD = new IcecleaverSword();
	public static final NormalCrossbow NORMAL_CROSSBOW = new NormalCrossbow();
	public static final UrsusKnife URSUS_KNIFE = new UrsusKnife();
	public static final NormalMachete NORMAL_MACHETE = new NormalMachete();
	public static final NormalShield NORMAL_SHIELD = new NormalShield();
	public static final Grenade GRENADE = new Grenade();
	public static final PatriotJavelin PATRIOT_JAVELIN = new PatriotJavelin();
	public static final NormalArrow NORMAL_ARROW = new NormalArrow();
	public static final OrundumTh ORUNDUM_TH = new OrundumTh();
	public static final Frostmourne FROSTMOURNE = new Frostmourne();
	public static final HealingStaff HEALING_STAFF = new HealingStaff();
	public static final InstantBomb INSTANT_BOMB = new InstantBomb();
	public static final D12 D_12 = new D12();
	public static final HeavyShield HEAVY_SHIELD = new HeavyShield();
	public static final DualSword DUAL_SWORD = new DualSword();
	public static final DSteel DSTEEL = new DSteel();
	public static final Rmar RMAR = new Rmar();
	public static final Rmay RMAY = new Rmay();
	public static final CrystallineCircuit CRYSTALLINE_CIRCUIT = new CrystallineCircuit();
	public static final CrystallineElectronicUnit CRYSTALLINE_ELECTRONIC_UNIT = new CrystallineElectronicUnit();
	public static final CystallineComponent CRYSTALLINE_COMPONENT = new CystallineComponent();
	public static final Orirock ORIROCK = new Orirock();
	public static final OrirockCluster ORIROCK_CLUSTER = new OrirockCluster();
	public static final OrirockConcentration ORIROCK_CONCENTRATION = new OrirockConcentration();
	public static final OrirockCube ORIROCK_CUBE = new OrirockCube();
	public static final Oriron ORIRON = new Oriron();
	public static final OrironBlock ORIRON_BLOCK = new OrironBlock();
	public static final OrironCluster ORIRON_CLUSTER = new OrironCluster();
	public static final OrironShard ORIRON_SHARD = new OrironShard();
	public static final Sugar SUGAR = new Sugar();
	public static final SugarLump SUGAR_LUMP = new SugarLump();
	public static final SugarPack SUGAR_PACK = new SugarPack();
	public static final SugarSubstitute SUGAR_SUBSTITUTE = new SugarSubstitute();
	public static final Ester ESTER = new Ester();
	public static final PolyesterLump POLYESTER_LUMP = new PolyesterLump();
	public static final Polyester POLYESTER = new Polyester();
	public static final PolyesterPack POLYESTER_PACK = new PolyesterPack();
	public static final Aketon AKETON = new Aketon();
	public static final Diketon DIKETON = new Diketon();
	public static final KetonColloid KETON_COLLOID = new KetonColloid();
	public static final Polyketon POLYKETON = new Polyketon();
	public static final DamagedDevice DAMAGED_DEVICE = new DamagedDevice();
	public static final Device DEVICE = new Device();
	public static final IntegratedDevice INTEGRATED_DEVICE = new IntegratedDevice();
	public static final OptimizedDevice OPTIMIZED_DEVICE = new OptimizedDevice();
	public static final LoxicKohl LOXIC_KOHL = new LoxicKohl();
	public static final WhiteHorseKohl WHITE_HORSE_KOHL = new WhiteHorseKohl();
	public static final ManganeseOre MANGANESE_ORE = new ManganeseOre();
	public static final ManganeseTrihydrate MANGANESE_TRIHYDRATE = new ManganeseTrihydrate();
	public static final Grindstone GRINDSTONE = new Grindstone();
	public static final GrindstonePentahydrate GRINDSTONE_PENTAHYDRATE = new GrindstonePentahydrate();
	public static final IncandescentAlloy INCANDESCENT_ALLOY = new IncandescentAlloy();
	public static final IncandescentAlloyBlock INCANDESCENT_ALLOY_BLOCK = new IncandescentAlloyBlock();
	public static final CoagulatingGel COAGULATING_GEL = new CoagulatingGel();
	public static final PolymerizedGel POLYMERIZED_GEL = new PolymerizedGel();
	public static final BipolarNanoflake BIPOLAR_NANOFLAKE = new BipolarNanoflake();
	public static final ChipCatalyst CHIP_CATALYST = new ChipCatalyst();
	public static final PolymerizationPreparation POLYMERIZATION_PREPARATION = new PolymerizationPreparation();
	public static final CasterChip CASTER_CHIP = new CasterChip();
	public static final CasterChipset CASTER_CHIPSET = new CasterChipset();
	public static final CasterDualchip CASTER_DUALCHIP = new CasterDualchip();
	public static final DrillBattleRecord DRILL_BATTLE_RECORD = new DrillBattleRecord();
	public static final FrontlineBattleRecord FRONTLINE_BATTLE_RECORD = new FrontlineBattleRecord();
	public static final StrategicBattleRecord STRATEGIC_BATTLE_RECORD = new StrategicBattleRecord();
	public static final TacticalBattleRecord TACTICAL_BATTLE_RECORD = new TacticalBattleRecord();
	public static final FurniturePart FURNITURE_PART = new FurniturePart();
	public static final CarbonBrick CARBON_BRICK = new CarbonBrick();
	public static final CarbonPack CARBON_PACK = new CarbonPack();
	public static final Carbon CARBON = new Carbon();
	public static final LightBuildingMaterial LIGHT_BUILDING_MATERIAL = new LightBuildingMaterial();
	public static final ConcreteBuildingMaterial CONCRETE_BUILDING_MATERIAL = new ConcreteBuildingMaterial();
	public static final ReinforcedBuildingMaterial REINFORCED_BUILDING_MATERIAL = new ReinforcedBuildingMaterial();
	public static final DefenderChip DEFENDER_CHIP = new DefenderChip();
	public static final DefenderChipset DEFENDER_CHIPSET = new DefenderChipset();
	public static final DefenderDualchip DEFENDER_DUALCHIP = new DefenderDualchip();
	public static final GuardChip GUARD_CHIP = new GuardChip();
	public static final GuardChipset GUARD_CHIPSET = new GuardChipset();
	public static final GuardDualchip GUARD_DUALCHIP = new GuardDualchip();
	public static final MedicChip MEDIC_CHIP = new MedicChip();
	public static final MedicChipset MEDIC_CHIPSET = new MedicChipset();
	public static final MedicDualchip MEDIC_DUALCHIP = new MedicDualchip();
	public static final SniperChip SNIPER_CHIP = new SniperChip();
	public static final SniperChipset SNIPER_CHIPSET = new SniperChipset();
	public static final SniperDualchip SNIPER_DUALCHIP = new SniperDualchip();
	public static final SpecialistChip SPECIALIST_CHIP = new SpecialistChip();
	public static final SpecialistChipset SPECIALIST_CHIPSET = new SpecialistChipset();
	public static final SpecialistDualchip SPECIALIST_DUALCHIP = new SpecialistDualchip();
	public static final SupporterChip SUPPORTER_CHIP = new SupporterChip();
	public static final SupporterChipset SUPPORTER_CHIPSET = new SupporterChipset();
	public static final SupporterDualchip SUPPORTER_DUALCHIP = new SupporterDualchip();
	public static final VanguardChip VANGUARD_CHIP = new VanguardChip();
	public static final VanguardChipset VANGUARD_CHIPSET = new VanguardChipset();
	public static final VanguardDualchip VANGUARD_DUALCHIP = new VanguardDualchip();
	public static final Keel KEEL = new Keel();
	public static final Film FILM = new Film();
	public static final MixedGold MIXED_GOLD = new MixedGold();
	public static final CrudeGold CRUDE_GOLD = new CrudeGold();
	public static final OriEster ORI_ESTER = new OriEster();
	public static final Gel GEL = new Gel();
	public static final PureGold PURE_GOLD = new PureGold();
	public static final LmbHundred LMB_HUNDRED = new LmbHundred();
	public static final ItemPowerStation POWER_STATION = new ItemPowerStation();
	public static final Oricoal ORICOAL = new Oricoal();
	public static final ItemDerelictDirt DERELICT_DIRT = new ItemDerelictDirt();
	public static final ItemGrassVigour GRASS_VIGOUR = new ItemGrassVigour();
	public static final ItemFertileDirt FERTILE_DIRT = new ItemFertileDirt();
	public static final ItemCrudeGoldOre CRUDE_GOLD_ORE = new ItemCrudeGoldOre();
	public static final ItemIncandescentOre INCANDESCENT_ORE = new ItemIncandescentOre();
	public static final ItemMixedGoldOre MIXED_GOLD_ORE = new ItemMixedGoldOre();
	public static final ItemOricoalOre ORICOAL_ORE = new ItemOricoalOre();
	public static final ItemOriginiumOre ORIGINIUM_ORE = new ItemOriginiumOre();
	public static final ItemOrimanganeseOre ORIMANGANESE_ORE = new ItemOrimanganeseOre();
	public static final ItemOrirockOre ORIROCK_ORE = new ItemOrirockOre();
	public static final ItemOrironOre ORIRON_ORE = new ItemOrironOre();
	public static final ItemWeaponGemOre WEAPON_GEM_ORE = new ItemWeaponGemOre();
	public static final IncandescentIngot INCANDESCENT_INGOT = new IncandescentIngot();
	public static final ManganeseShard MANGANESE_SHARD = new ManganeseShard();
	public static final ItemGrindstoneOre GRINDSTONE_ORE = new ItemGrindstoneOre();
	public static final GrindstoneShard GRINDSTONE_SHARD = new GrindstoneShard();
	public static final ItemOriginiumCrystal ORIGINIUM_CRYSTAL = new ItemOriginiumCrystal();
	public static final ItemPortalArkworld PORTAL_ARKWORLD = new ItemPortalArkworld();
	public static final ItemTalaStone TALA_STONE = new ItemTalaStone();
	public static final ItemWitheredGrass WITHERED_GRASS = new ItemWitheredGrass();
	public static final ItemWillowLeaf WILLOW_LEAF = new ItemWillowLeaf();
	public static final ItemWillowLog WILLOW_LOG = new ItemWillowLog();
	public static final ItemWillowSapling WILLOW_SAPLING = new ItemWillowSapling();
	public static final ItemWillowWood WILLOW_WOOD = new ItemWillowWood();
	public static final ItemGrassFrozen GRASS_FROZEN = new ItemGrassFrozen();
	public static final ItemFrozenDirt FROZEN_DIRT = new ItemFrozenDirt();
	public static final ItemFlowerFrost FLOWER_FROST = new ItemFlowerFrost();
	public static final ItemMoss MOSS = new ItemMoss();
	public static final ItemActiveOriginiumBlock ACTIVE_ORIGINIUM_BLOCK = new ItemActiveOriginiumBlock();
	public static final ItemOriginiumBlock ORIGINIUM_BLOCK = new ItemOriginiumBlock();
	public static final ItemInactivationOriginiumBlock INACTIVATION_ORIGINIUM_BLOCK = new ItemInactivationOriginiumBlock();
	public static final Fat FAT = new Fat();
	public static final ItemHugeLeaf HUGE_LEAF = new ItemHugeLeaf();
	public static final ItemHugeSapling HUGE_SAPLING = new ItemHugeSapling();
	public static final ItemHugeWood HUGE_WOOD = new ItemHugeWood();
	public static final ItemHugeLog HUGE_LOG = new ItemHugeLog();
	public static final ItemMyceliumVine MYCELIUM_VINE = new ItemMyceliumVine();
	public static final VenisonRaw VENISON_RAW = new VenisonRaw();
	public static final VenisonCooked VENISON_COOKED = new VenisonCooked();
	public static final ItemIberiaFlower IBERIA_FLOWER = new ItemIberiaFlower();
	public static final ItemIberiaBigFlower IBERIA_BIG_FLOWER = new ItemIberiaBigFlower();
	public static final ItemDripstone DRIPSTONE = new ItemDripstone();
	public static final ItemFungusBlanket FUNGUS_BLANKET = new ItemFungusBlanket();
	public static final ItemIberiaWaterlily IBERIA_WATERLILY = new ItemIberiaWaterlily();
	public static final Firebomb FIREBOMB = new Firebomb();
	public static final ItemCityStone CITY_STONE = new ItemCityStone();
	public static final PocketSeaCrawlerArmor SEA_CRAWLER_ARMOR = new PocketSeaCrawlerArmor(EntityEquipmentSlot.HEAD);
	public static final DeepSeaSliderArmor DEEP_SEA_SLIDER_ARMOR = new DeepSeaSliderArmor(EntityEquipmentSlot.HEAD);
	public static final SkullshattererArmor SKULLSHATTERER_ARMOR = new SkullshattererArmor(EntityEquipmentSlot.HEAD);
	public static final TechnicalScoutArmor TECHNICAL_SCOUT_ARMOR = new TechnicalScoutArmor(EntityEquipmentSlot.HEAD);
	public static final WraithArmor WRAITH_ARMOR = new WraithArmor(EntityEquipmentSlot.HEAD);
	public static final SeniorCasterArmor SENIOR_CASTER_HEAD = new SeniorCasterArmor(EntityEquipmentSlot.HEAD);
	public static final SeniorCasterArmor SENIOR_CASTER_CHEST = new SeniorCasterArmor(EntityEquipmentSlot.CHEST);
	public static final ItemTeaPlant TEA_PLANT = new ItemTeaPlant();
	public static final ItemFrostnovaDoll FROSTNOVA_DOLL = new ItemFrostnovaDoll();
	public static final ItemFaustDoll FAUST_DOLL = new ItemFaustDoll();
	public static final ItemMayfestDoll MAYFEST_DOLL = new ItemMayfestDoll();
	public static final ItemPatriotDoll PATRIOT_DOLL = new ItemPatriotDoll();
	public static final ItemRegicideDoll REGICIDE_DOLL = new ItemRegicideDoll();
	public static final ItemWDoll W_DOLL = new ItemWDoll();
	public static final ItemBlackSnakeDoll BLACK_SNAKE_DOLL = new ItemBlackSnakeDoll();
	public static final ItemSkullshattererDoll SKULLSHATTERER_DOLL = new ItemSkullshattererDoll();
	public static final ItemEvolutionDoll EVOLUTION_DOLL = new ItemEvolutionDoll();
	public static final ItemTradingStation TRADING_STATION = new ItemTradingStation();
	public static final ItemSeagrass SEAGRASS = new ItemSeagrass();
	public static final ItemSealedFloor SEALED_FLOOR = new ItemSealedFloor();
	public static final ItemOriginiumFloor ORIGINIUM_FLOOR = new ItemOriginiumFloor();
	public static final ItemProcessingStation PROCESSING_STATION = new ItemProcessingStation();
	public static final ItemTrainingRoom TRAINING_ROOM = new ItemTrainingRoom();
	public static final ItemControlCenter CONTROL_CENTER = new ItemControlCenter();
	public static final ItemOffice OFFICE = new ItemOffice();
	public static final ItemDormitory DORMITORY = new ItemDormitory();
	public static final ItemManufacturingStation MANUFACTURING_STATION = new ItemManufacturingStation();
	public static final ItemWeaponTable WEAPON_TABLE = new ItemWeaponTable();
	public static final WeaponGem WEAPON_GEM = new WeaponGem();
	public static final FrostnovaArmor FROSTNOVA_HEAD = new FrostnovaArmor(EntityEquipmentSlot.HEAD);
	public static final FrostnovaArmor FROSTNOVA_CHEST = new FrostnovaArmor(EntityEquipmentSlot.CHEST);
	public static final PatriotArmor PATRIOT_CHEST = new PatriotArmor(EntityEquipmentSlot.CHEST);
	public static final PatriotArmor PATRIOT_HEAD = new PatriotArmor(EntityEquipmentSlot.HEAD);
	public static final PatriotArmor PATRIOT_LEGS = new PatriotArmor(EntityEquipmentSlot.LEGS);
	public static final PatriotArmor PATRIOT_FEET = new PatriotArmor(EntityEquipmentSlot.FEET);
	public static final SurtrArmor SURTR_ARMOR = new SurtrArmor(EntityEquipmentSlot.CHEST);
	public static final BasinSeaReaperArmor BASIN_SEA_REAPER_ARMOR = new BasinSeaReaperArmor(EntityEquipmentSlot.CHEST);
	public static final SmilingCorpseMountainArmor SMILING_CORPSE_MOUNTAIN_ARMOR = new SmilingCorpseMountainArmor(EntityEquipmentSlot.HEAD);
	public static final RegicideArmor REGICIDE_ARMOR = new RegicideArmor(EntityEquipmentSlot.HEAD);
	public static final DefenseCrusherArmor DEFENSE_CRUSHER_CHEST = new DefenseCrusherArmor(EntityEquipmentSlot.CHEST);
	public static final DefenseCrusherArmor DEFENSE_CRUSHER_LEGS = new DefenseCrusherArmor(EntityEquipmentSlot.LEGS);
	public static final DefenseCrusherArmor DEFENSE_CRUSHER_FEET = new DefenseCrusherArmor(EntityEquipmentSlot.FEET);
	public static final GuerrillaSiegebreakerArmor GUERRILLA_SIEGEBREAKER_ARMOR = new GuerrillaSiegebreakerArmor(EntityEquipmentSlot.CHEST);
	public static final SoldierArmor SOLDIER_HEAD = new SoldierArmor(EntityEquipmentSlot.HEAD);
	public static final SoldierArmor SOLDIER_CHEST = new SoldierArmor(EntityEquipmentSlot.CHEST);
	public static final SoldierArmor SOLDIER_LEGS = new SoldierArmor(EntityEquipmentSlot.LEGS);
	public static final SoldierArmor SOLDIER_FEET = new SoldierArmor(EntityEquipmentSlot.FEET);
	public static final YetiOperativeArmor YETI_OPERATIVE_HEAD = new YetiOperativeArmor(EntityEquipmentSlot.HEAD);
	public static final YetiOperativeArmor YETI_OPERATIVE_CHEST = new YetiOperativeArmor(EntityEquipmentSlot.CHEST);
	public static final YetiOperativeArmor YETI_OPERATIVE_LEGS = new YetiOperativeArmor(EntityEquipmentSlot.LEGS);
	public static final YetiOperativeArmor YETI_OPERATIVE_FEET = new YetiOperativeArmor(EntityEquipmentSlot.FEET);
	public static final UrsusBread URSUS_BREAD = new UrsusBread();
	public static final DriedCactus DRIED_CACTUS = new DriedCactus();
	public static final ArmyProvisions ARMY_PROVISIONS = new ArmyProvisions();
	public static final Biscuit BISCUIT = new Biscuit();
	public static final BlackTea BLACK_TEA = new BlackTea();
	public static final HibiscusHealthyFood HIBISCUS_HEALTHY_FOOD = new HibiscusHealthyFood();
	public static final SandwormCanned SANDWORM_CANNED = new SandwormCanned();
	public static final AsceticPuff ASCETIC_PUFF = new AsceticPuff();
	public static final Baguette BAGUETTE = new Baguette();
	public static final Chocolates CHOCOLATES = new Chocolates();
	public static final GaulCake GAUL_CAKE = new GaulCake();
	public static final VictoriaCake VICTORIA_CAKE = new VictoriaCake();
	public static final CannedVegetables CANNED_VEGETABLES = new CannedVegetables();
	public static final TherapeuticPowder THERAPEUTIC_POWDER = new TherapeuticPowder();
	public static final HemostaticAgent HEMOSTATIC_AGENT = new HemostaticAgent();
	public static final Kettle KETTLE = new Kettle();
	public static final Coin COIN = new Coin();
	public static final Candy CANDY = new Candy();
	public static final OrironPickaxe ORIRON_PICKAXE = new OrironPickaxe();
	public static final OrironAxe ORIRON_AXE = new OrironAxe();
	public static final OrironShovel ORIRON_SHOVEL = new OrironShovel();
	public static final RmaAxe RMA_AXE = new RmaAxe();
	public static final RmaPickaxe RMA_PICKAXE = new RmaPickaxe();
	public static final RmaShovel RMA_SHOVEL = new RmaShovel();
	public static final IncandescentPickaxe INCANDESCENT_PICKAXE = new IncandescentPickaxe();
	public static final Cheese CHEESE = new Cheese();
	public static final Tea TEA = new Tea();
	public static final Cream CREAM = new Cream();
	public static final IberiaStem IBERIA_STEM = new IberiaStem();
	public static final IberiaPetal IBERIA_PETAL = new IberiaPetal();
	public static final DripstoneShard DRIPSTONE_SHARD = new DripstoneShard();
	public static final RmaShard RMA_SHARD = new RmaShard();
	public static final ItemRmaOre RMA_ORE = new ItemRmaOre();
	public static final FrogMeatRaw FROG_MEAT_RAW = new FrogMeatRaw();
	public static final FrogMeatCooked FROG_MEAT_COOKED = new FrogMeatCooked();
	public static final SmallMeatRaw SMALL_MEAT_RAW = new SmallMeatRaw();
	public static final SmallMeatCooked SMALL_MEAT_COOKED = new SmallMeatCooked();
	public static final BigMeatRaw BIG_MEAT_RAW = new BigMeatRaw();
	public static final BigMeatCooked BIG_MEAT_COOKED = new BigMeatCooked();
	public static final HoneyCake HONEY_CAKE = new HoneyCake();
	public static final OriKetone ORI_KETONE = new OriKetone();
	public static final OriKohl ORI_KOHL = new OriKohl();
	public static final ItemFloorBase FLOOR_BASE = new ItemFloorBase();
	public static final ItemDefenseFloor DEFENSE_FLOOR = new ItemDefenseFloor();
	public static final ItemPushFloor PUSH_FLOOR = new ItemPushFloor();
	public static final ItemHealingFloor HEALING_FLOOR = new ItemHealingFloor();
	public static final ItemHotFloor HOT_FLOOR = new ItemHotFloor();
	public static final ItemReduceDefenseFloor REDUCE_DEFENSE_FLOOR = new ItemReduceDefenseFloor();
	public static final ItemMachineBase MACHINE_BASE = new ItemMachineBase();
	public static final Pipe PIPE = new Pipe();
	public static final OrironArmor ORIRON_HEAD = new OrironArmor(EntityEquipmentSlot.HEAD);
	public static final OrironArmor ORIRON_CHEST = new OrironArmor(EntityEquipmentSlot.CHEST);
	public static final OrironArmor ORIRON_LEGS = new OrironArmor(EntityEquipmentSlot.LEGS);
	public static final OrironArmor ORIRON_FEET = new OrironArmor(EntityEquipmentSlot.FEET);
	public static final OrirockArmor ORIROCK_HEAD = new OrirockArmor(EntityEquipmentSlot.HEAD);
	public static final OrirockArmor ORIROCK_CHEST = new OrirockArmor(EntityEquipmentSlot.CHEST);
	public static final OrirockArmor ORIROCK_LEGS = new OrirockArmor(EntityEquipmentSlot.LEGS);
	public static final OrirockArmor ORIROCK_FEET = new OrirockArmor(EntityEquipmentSlot.FEET);
	public static final ManganeseArmor MANGANESE_HEAD = new ManganeseArmor(EntityEquipmentSlot.HEAD);
	public static final ManganeseArmor MANGANESE_CHEST = new ManganeseArmor(EntityEquipmentSlot.CHEST);
	public static final ManganeseArmor MANGANESE_LEGS = new ManganeseArmor(EntityEquipmentSlot.LEGS);
	public static final ManganeseArmor MANGANESE_FEET = new ManganeseArmor(EntityEquipmentSlot.FEET);
	public static final GrindstoneArmor GRINDSTONE_HEAD = new GrindstoneArmor(EntityEquipmentSlot.HEAD);
	public static final GrindstoneArmor GRINDSTONE_CHEST = new GrindstoneArmor(EntityEquipmentSlot.CHEST);
	public static final GrindstoneArmor GRINDSTONE_LEGS = new GrindstoneArmor(EntityEquipmentSlot.LEGS);
	public static final GrindstoneArmor GRINDSTONE_FEET = new GrindstoneArmor(EntityEquipmentSlot.FEET);
	public static final ItemModBox MOD_BOX = new ItemModBox();
	public static final ItemOperatorBox OPERATOR_BOX = new ItemOperatorBox();
	public static final SignArrow SIGN_ARROW = new SignArrow();
	public static final ItemOriginiumCrystalHuge ORIGINIUM_CRYSTAL_HUGE = new ItemOriginiumCrystalHuge();
	public static final WeaponFixGem WEAPON_FIX_GEN = new WeaponFixGem();
	public static final SurtrCard SURTR_CARD = new SurtrCard();
	public static final SurtrPro SURTR_PRO = new SurtrPro();
	public static final PurestreamCard PURESTREAM_CARD = new PurestreamCard();
	public static final PurestreamPro PURESTREAM_PRO = new PurestreamPro();
	public static final WCard W_CARD = new WCard();
	public static final WPro W_PRO = new WPro();

	public static List<Item> items = new ArrayList<>(Arrays.asList(GRENADE,NORMAL_ARROW,TWO_LOAVES_WITH_CHEESE,SURTR_CARD,SURTR_PRO,
			EMERGENCY_SANITY_POTION,EMERGENCY_SANITY_SAMPLER,TOP_EMERGENCY_SANITY_POTION,PATRIOT_JAVELIN,
			PATRIOT_SHIELD,FAUST_CROSSBOW,SMASHING_HAMMER,FAUST_COMMON_ARROW,FAUST_SPECIAL_ARROW,GRENADE_GUN,
			TALULAH_SWORD,GUERRILLA_SHIELD,MAGIC_SWORD,GUERRILLA_AXE,AVENGER_SWORD,ICECLEAVER_SWORD,NORMAL_CROSSBOW,
			URSUS_KNIFE,NORMAL_MACHETE,NORMAL_SHIELD,FROSTNOVA_DOLL,FAUST_DOLL,MAYFEST_DOLL,PATRIOT_DOLL,REGICIDE_DOLL,
			W_DOLL,BLACK_SNAKE_DOLL,SKULLSHATTERER_DOLL,EVOLUTION_DOLL,ORUNDUM_TH,FROSTNOVA_HEAD,SEALED_FLOOR,MY_INFO,
			ORIGINIUM_FLOOR,EXP_CARD,FROSTMOURNE,HEALING_STAFF, INSTANT_BOMB,D_12,ARMS_MOD,DEFENCE_MOD,PLAYER_MOD,ORUNDUM,
			ORUNDUM_TWOHUNDRED,LMB,HEADHUNT,DSTEEL,RMAR,RMAY,CRYSTALLINE_CIRCUIT,CRYSTALLINE_ELECTRONIC_UNIT, CRYSTALLINE_COMPONENT,
			ORIROCK,ORIROCK_CLUSTER,ORIROCK_CONCENTRATION,ORIROCK_CUBE,ORIRON,ORIRON_BLOCK,ORIRON_CLUSTER,ORIRON_SHARD,SUGAR,
			SUGAR_LUMP,SUGAR_PACK,SUGAR_SUBSTITUTE,ESTER,POLYESTER_LUMP,POLYESTER,POLYESTER_PACK,AKETON,DIKETON,KETON_COLLOID,
			POLYKETON,DAMAGED_DEVICE,DEVICE,INTEGRATED_DEVICE,OPTIMIZED_DEVICE,LOXIC_KOHL,WHITE_HORSE_KOHL,MANGANESE_ORE,
			MANGANESE_TRIHYDRATE,GRINDSTONE,GRINDSTONE_PENTAHYDRATE,INCANDESCENT_ALLOY,INCANDESCENT_ALLOY_BLOCK,COAGULATING_GEL,
			POLYMERIZED_GEL,BIPOLAR_NANOFLAKE,CHIP_CATALYST,POLYMERIZATION_PREPARATION,HEAVY_SHIELD,DUAL_SWORD,AMIYA_CARD,
			SKILL_SUMMARY_I,SKILL_SUMMARY_II,SKILL_SUMMARY_III,CASTER_CHIP,CASTER_CHIPSET,CASTER_DUALCHIP,DRILL_BATTLE_RECORD,
			FRONTLINE_BATTLE_RECORD,STRATEGIC_BATTLE_RECORD,TACTICAL_BATTLE_RECORD,AMIYA_PRO,PROCESSING_STATION,FURNITURE_PART,
			CARBON_BRICK,CARBON_PACK,LIGHT_BUILDING_MATERIAL,CONCRETE_BUILDING_MATERIAL,REINFORCED_BUILDING_MATERIAL,CARBON,
			DEFENDER_CHIP,DEFENDER_CHIPSET,DEFENDER_DUALCHIP,GUARD_CHIP,GUARD_CHIPSET,GUARD_DUALCHIP,MEDIC_CHIP,MEDIC_CHIPSET,
			MEDIC_DUALCHIP,SNIPER_CHIP,SNIPER_CHIPSET,SNIPER_DUALCHIP,SPECIALIST_CHIP,SPECIALIST_CHIPSET,SPECIALIST_DUALCHIP,
			SUPPORTER_CHIP,SUPPORTER_CHIPSET,SUPPORTER_DUALCHIP,VANGUARD_CHIP,VANGUARD_CHIPSET,VANGUARD_DUALCHIP,TRAINING_ROOM,
			CONTROL_CENTER,KEEL,OFFICE,DORMITORY,FILM,MIXED_GOLD,CRUDE_GOLD,ORI_ESTER,GEL,MANUFACTURING_STATION,PURE_GOLD,
			TRADING_STATION,LMB_HUNDRED,POWER_STATION,ORICOAL,WEAPON_TABLE, NORMAL_WEAPON_MOD,HIGH_WEAPON_MOD,EPIC_WEAPON_MOD,WEAPON_GEM,
			GRASS_WITHERED,DERELICT_DIRT,GRASS_VIGOUR,FERTILE_DIRT,CRUDE_GOLD_ORE,INCANDESCENT_ORE,MIXED_GOLD_ORE,ORICOAL_ORE,
			ORIGINIUM_ORE,ORIMANGANESE_ORE,ORIROCK_ORE,ORIRON_ORE,WEAPON_GEM_ORE,INCANDESCENT_INGOT,MANGANESE_SHARD,GRINDSTONE_ORE,
			GRINDSTONE_SHARD,ORIGINIUM_CRYSTAL,PORTAL_ARKWORLD,TALA_STONE,WITHERED_GRASS,WILLOW_LEAF,WILLOW_LOG,WILLOW_SAPLING,
			WILLOW_WOOD,GRASS_FROZEN,FROZEN_DIRT,FLOWER_FROST,MOSS,ACTIVE_ORIGINIUM_BLOCK,ORIGINIUM_BLOCK,INACTIVATION_ORIGINIUM_BLOCK,
			FAT,HUGE_LEAF,HUGE_SAPLING,HUGE_WOOD, HUGE_LOG,NICE_MUSHROOM,MYCELIUM_VINE,VENISON_RAW,VENISON_COOKED,IBERIA_DIRT,
			SEAGRASS,IBERIA_FLOWER,IBERIA_BIG_FLOWER,DRIPSTONE,FUNGUS_BLANKET,IBERIA_WATERLILY,FIREBOMB,CITY_STONE,HEADHUNT_TEN,
			ORIGINIUM_SHARD,ORIGINITE_PRIME,SEA_CRAWLER_ARMOR,DEEP_SEA_SLIDER_ARMOR,PATRIOT_HEAD,REGICIDE_ARMOR,SKULLSHATTERER_ARMOR,
			DEFENSE_CRUSHER_CHEST,GUERRILLA_SIEGEBREAKER_ARMOR,TECHNICAL_SCOUT_ARMOR,WRAITH_ARMOR,PATRIOT_CHEST,DEFENSE_CRUSHER_LEGS,
			PATRIOT_LEGS,PATRIOT_FEET,FROSTNOVA_CHEST,SURTR_ARMOR,BASIN_SEA_REAPER_ARMOR,SMILING_CORPSE_MOUNTAIN_ARMOR,DEFENSE_CRUSHER_FEET,
			SENIOR_CASTER_HEAD,SENIOR_CASTER_CHEST,SOLDIER_HEAD,SOLDIER_CHEST,SOLDIER_LEGS,SOLDIER_FEET,YETI_OPERATIVE_HEAD,
			YETI_OPERATIVE_CHEST,YETI_OPERATIVE_LEGS,YETI_OPERATIVE_FEET,URSUS_BREAD,DRIED_CACTUS,ARMY_PROVISIONS,BISCUIT,BLACK_TEA,
			HIBISCUS_HEALTHY_FOOD,SANDWORM_CANNED,ASCETIC_PUFF,BAGUETTE,CHOCOLATES,GAUL_CAKE,VICTORIA_CAKE,CANNED_VEGETABLES,
			THERAPEUTIC_POWDER,HEMOSTATIC_AGENT,KETTLE,COIN,CANDY,ORIRON_PICKAXE,ORIRON_AXE,ORIRON_SHOVEL,RMA_AXE,RMA_PICKAXE,
			RMA_SHOVEL,INCANDESCENT_PICKAXE,CHEESE,TEA_PLANT,TEA,CREAM,IBERIA_STEM,IBERIA_PETAL,DRIPSTONE_SHARD,RMA_SHARD,RMA_ORE,
			FROG_MEAT_RAW,FROG_MEAT_COOKED,SMALL_MEAT_RAW,SMALL_MEAT_COOKED,BIG_MEAT_RAW,BIG_MEAT_COOKED,HONEY_CAKE,ORI_KETONE,
			FLOOR_BASE,ORI_KOHL,DEFENSE_FLOOR,PUSH_FLOOR,HEALING_FLOOR,HOT_FLOOR,REDUCE_DEFENSE_FLOOR,MACHINE_BASE,PIPE,ORIRON_HEAD,
			ORIRON_CHEST,ORIRON_LEGS,ORIRON_FEET,ORIROCK_HEAD,ORIROCK_CHEST,ORIROCK_LEGS,ORIROCK_FEET, MANGANESE_HEAD,MOD_BOX,
			MANGANESE_CHEST, MANGANESE_LEGS, MANGANESE_FEET,GRINDSTONE_HEAD,GRINDSTONE_CHEST,GRINDSTONE_LEGS,GRINDSTONE_FEET,
			OPERATOR_BOX,SIGN_ARROW,ORIGINIUM_CRYSTAL_HUGE,WEAPON_FIX_GEN,PURESTREAM_CARD,PURESTREAM_PRO,W_CARD,W_PRO));

	public static List<Item> geo = new ArrayList<>(Arrays.asList(HEALING_STAFF,FROSTMOURNE,GRENADE,FAUST_COMMON_ARROW,FAUST_SPECIAL_ARROW,NORMAL_ARROW,
			SEALED_FLOOR,ORIGINIUM_FLOOR,EVOLUTION_DOLL,FAUST_CROSSBOW,PATRIOT_JAVELIN,PATRIOT_SHIELD,FROSTNOVA_DOLL,
			FAUST_DOLL,MAYFEST_DOLL,PATRIOT_DOLL,REGICIDE_DOLL,W_DOLL,BLACK_SNAKE_DOLL,SKULLSHATTERER_DOLL,SMASHING_HAMMER,
			GRENADE_GUN,TALULAH_SWORD,GUERRILLA_SHIELD,MAGIC_SWORD,GUERRILLA_AXE,AVENGER_SWORD,ICECLEAVER_SWORD,NORMAL_CROSSBOW,
			URSUS_KNIFE,NORMAL_MACHETE,NORMAL_SHIELD,INSTANT_BOMB,D_12,HEAVY_SHIELD,DUAL_SWORD,PROCESSING_STATION,TRAINING_ROOM,
			CONTROL_CENTER,OFFICE,DORMITORY,MANUFACTURING_STATION,TRADING_STATION,POWER_STATION,WEAPON_TABLE,SEAGRASS,IBERIA_FLOWER,
			IBERIA_BIG_FLOWER,DRIPSTONE,FIREBOMB,SEA_CRAWLER_ARMOR,DEEP_SEA_SLIDER_ARMOR,REGICIDE_ARMOR,SKULLSHATTERER_ARMOR,
			GUERRILLA_SIEGEBREAKER_ARMOR,TECHNICAL_SCOUT_ARMOR,WRAITH_ARMOR,SURTR_ARMOR,BASIN_SEA_REAPER_ARMOR,REDUCE_DEFENSE_FLOOR,
			SMILING_CORPSE_MOUNTAIN_ARMOR,FLOOR_BASE,DEFENSE_FLOOR,PUSH_FLOOR,HEALING_FLOOR,HOT_FLOOR,PIPE,MOD_BOX,OPERATOR_BOX,
			SIGN_ARROW));

	@SubscribeEvent
	public static void onRegistry(Register<Item> event)
	{
		IForgeRegistry<Item> registry = event.getRegistry();

		for(Item item : items)
		{
			registry.register(item);
		}

		GameRegistry.addSmelting(new ItemStack(ORIRON_ORE), new ItemStack(ORIRON_SHARD), new Random().nextInt(9));

		GameRegistry.addSmelting(new ItemStack(ORIGINIUM_ORE), new ItemStack(ORIGINIUM_SHARD), new Random().nextInt(9));

		GameRegistry.addSmelting(new ItemStack(INCANDESCENT_ORE), new ItemStack(INCANDESCENT_INGOT), new Random().nextInt(9));

		GameRegistry.addSmelting(new ItemStack(CRUDE_GOLD_ORE), new ItemStack(CRUDE_GOLD), new Random().nextInt(9));

		GameRegistry.addSmelting(new ItemStack(MIXED_GOLD_ORE), new ItemStack(MIXED_GOLD), new Random().nextInt(9));

		GameRegistry.addSmelting(new ItemStack(ORIMANGANESE_ORE), new ItemStack(MANGANESE_SHARD), new Random().nextInt(9));

		GameRegistry.addSmelting(new ItemStack(FROG_MEAT_RAW), new ItemStack(FROG_MEAT_COOKED), new Random().nextInt(9));

		GameRegistry.addSmelting(new ItemStack(VENISON_RAW), new ItemStack(VENISON_COOKED), new Random().nextInt(9));

		GameRegistry.addSmelting(new ItemStack(SMALL_MEAT_RAW), new ItemStack(SMALL_MEAT_COOKED), new Random().nextInt(9));

		GameRegistry.addSmelting(new ItemStack(BIG_MEAT_RAW), new ItemStack(BIG_MEAT_COOKED), new Random().nextInt(9));

		GameRegistry.addSmelting(new ItemStack(WILLOW_WOOD), new ItemStack(Items.COAL,1), new Random().nextInt(9));

		GameRegistry.addSmelting(new ItemStack(HUGE_WOOD), new ItemStack(Items.COAL,1), new Random().nextInt(9));

		OreDictionary.registerOre("oreCrudeGold",new ItemStack(CRUDE_GOLD_ORE));

		OreDictionary.registerOre("oreIncandescent",new ItemStack(INCANDESCENT_ORE));

		OreDictionary.registerOre("oreMixedGold",new ItemStack(MIXED_GOLD_ORE));

		OreDictionary.registerOre("oreOricoal",new ItemStack(ORICOAL_ORE));

		OreDictionary.registerOre("oreOriginium",new ItemStack(ORIGINIUM_ORE));

		OreDictionary.registerOre("oreOrimanganese",new ItemStack(ORIMANGANESE_ORE));

		OreDictionary.registerOre("oreOrirock",new ItemStack(ORIROCK_ORE));

		OreDictionary.registerOre("oreOriron",new ItemStack(ORIRON_ORE));

		OreDictionary.registerOre("oreWeaponGem",new ItemStack(WEAPON_GEM_ORE));

		OreDictionary.registerOre("oreGrindstone",new ItemStack(GRINDSTONE_ORE));

		OreDictionary.registerOre("oreRma",new ItemStack(RMA_ORE));
	}

	@SideOnly(Side.CLIENT)
	private static void commonRegisterModel(Item item)
	{
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(item.getRegistryName(),"inventory");
		ModelLoader.setCustomModelResourceLocation(item, 0, modelResourceLocation);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onModelRegistry(ModelRegistryEvent event)
	{
		OBJLoader.INSTANCE.addDomain(Arkworld.MODID);

		for (Item item : items) {commonRegisterModel(item);}

		for(Item item : geo) {item.setTileEntityItemStackRenderer(new RenderItem());}

		ModelLoader.setCustomStateMapper(FluidRegistry.ORIGINIUM_CONTAMINATED_WATER.getBlock(), new StateMapperBase()
		{
			@Nonnull
			@Override
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state)
			{
				return new ModelResourceLocation(new ResourceLocation("arkworld", "fluid"), "originium_contaminated_water");
			}
		});

		ModelLoader.setCustomStateMapper(FluidRegistry.IBERIA_WATER.getBlock(), new StateMapperBase()
		{
			@Nonnull
			@Override
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state)
			{
				return new ModelResourceLocation(new ResourceLocation("arkworld", "fluid"), "iberia_water");
			}
		});

	}
}
