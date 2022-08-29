package com.weedycow.arkworld.registry;

import com.weedycow.arkworld.entity.animal.*;
import com.weedycow.arkworld.entity.device.EnergyPolymer;
import com.weedycow.arkworld.entity.device.EntityMine;
import com.weedycow.arkworld.entity.enemy.cthulhu.elite.BasinSeaReaper;
import com.weedycow.arkworld.entity.enemy.cthulhu.normal.DeepSeaSlider;
import com.weedycow.arkworld.entity.enemy.cthulhu.normal.PocketSeaCrawler;
import com.weedycow.arkworld.entity.enemy.cthulhu.normal.ShellSeaRunner;
import com.weedycow.arkworld.entity.enemy.hypergryph.BossOceanCat;
import com.weedycow.arkworld.entity.enemy.hypergryph.BossRuaCow;
import com.weedycow.arkworld.entity.enemy.kazdel.elite.SarkazBladeweaver;
import com.weedycow.arkworld.entity.enemy.kazdel.elite.SarkazGrudgebearer;
import com.weedycow.arkworld.entity.enemy.kazdel.normal.SarkazSentinel;
import com.weedycow.arkworld.entity.enemy.other.boss.Queen;
import com.weedycow.arkworld.entity.enemy.other.elite.Hydralisk;
import com.weedycow.arkworld.entity.enemy.other.elite.SmilingCorpseMountain;
import com.weedycow.arkworld.entity.enemy.other.normal.Zergling;
import com.weedycow.arkworld.entity.enemy.split.boss.Evolution;
import com.weedycow.arkworld.entity.enemy.split.normal.MutantRockSpider;
import com.weedycow.arkworld.entity.enemy.split.normal.OriginiutantExcrescence;
import com.weedycow.arkworld.entity.enemy.split.normal.OriginiutantTumor;
import com.weedycow.arkworld.entity.enemy.union.boss.*;
import com.weedycow.arkworld.entity.enemy.union.elite.*;
import com.weedycow.arkworld.entity.enemy.union.normal.*;
import com.weedycow.arkworld.entity.enemy.ursus.normal.InfectedPatrolCaptain;
import com.weedycow.arkworld.entity.enemy.ursus.normal.UrsusArmoredCaster;
import com.weedycow.arkworld.entity.operator.caster.Amiya;
import com.weedycow.arkworld.entity.operator.guard.Surtr;
import com.weedycow.arkworld.entity.operator.medic.Purestream;
import com.weedycow.arkworld.entity.weapon.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class EntityRegistry
{
	public static final EntityEntry ORIGINIUTANT_TUMOR = EntityEntryBuilder.create().entity(OriginiutantTumor.class).id(OriginiutantTumor.ID,0).name(OriginiutantTumor.NAME).tracker(64, 3, true).egg(853253, 16743788).build();
	public static final EntityEntry BOSS_RUA_COW = EntityEntryBuilder.create().entity(BossRuaCow.class).id(BossRuaCow.ID,1).name(BossRuaCow.NAME).tracker(64, 3, true).egg(5928063, 15657449).build();
	public static final EntityEntry BOSS_OCEAN_CAT = EntityEntryBuilder.create().entity(BossOceanCat.class).id(BossOceanCat.ID,2).name(BossOceanCat.NAME).tracker(64, 3, true).egg(15047978, 0).build();
	public static final EntityEntry PATRIOT = EntityEntryBuilder.create().entity(Patriot.class).id(Patriot.ID,3).name(Patriot.NAME).tracker(64, 3, true).egg(2105376, 10987431).build();
	public static final EntityEntry ORIGINIUTANT_EXCRESCENCE = EntityEntryBuilder.create().entity(OriginiutantExcrescence.class).id(OriginiutantExcrescence.ID,4).name(OriginiutantExcrescence.NAME).tracker(64, 3, true).egg(853253, 16743788).build();
	public static final EntityEntry ENTITY_FROST_CRYSTAL = EntityEntryBuilder.create().entity(SimpleThrowable.class).id(SimpleThrowable.ID,5).name(SimpleThrowable.NAME).tracker(64, 3, true).build();
	public static final EntityEntry SARKAZ_BLADEWEAVER = EntityEntryBuilder.create().entity(SarkazBladeweaver.class).id(SarkazBladeweaver.ID,6).name(SarkazBladeweaver.NAME).tracker(64, 3, true).egg(2368809, 3117135).build();
	public static final EntityEntry FAUST = EntityEntryBuilder.create().entity(Faust.class).id(Faust.ID,7).name(Faust.NAME).tracker(64, 3, true).egg(3093105, 3093046).build();
	public static final EntityEntry MAYFEST = EntityEntryBuilder.create().entity(Mayfest.class).id(Mayfest.ID,8).name(Mayfest.NAME).tracker(64, 3, true).egg(16777215, 16448250).build();
	public static final EntityEntry BLACK_SNAKE = EntityEntryBuilder.create().entity(BlackSnake.class).id(BlackSnake.ID,9).name(BlackSnake.NAME).tracker(64, 3, true).egg(13315328, 10104064).build();
	public static final EntityEntry AVENGER = EntityEntryBuilder.create().entity(Avenger.class).id(Avenger.ID,10).name(Avenger.NAME).tracker(64, 3, true).egg(2499364, 12427300).build();
	public static final EntityEntry CROSSBOWMAN = EntityEntryBuilder.create().entity(Crossbowman.class).id(Crossbowman.ID,11).name(Crossbowman.NAME).tracker(64, 3, true).egg(16766873, 6710887).build();
	public static final EntityEntry REGICIDE = EntityEntryBuilder.create().entity(Regicide.class).id(Regicide.ID,12).name(Regicide.NAME).tracker(64, 3, true).egg(4277049, 11420473).build();
	public static final EntityEntry ENTITY_BOMB = EntityEntryBuilder.create().entity(Bomb.class).id(Bomb.ID,13).name(Bomb.NAME).tracker(64, 3, true).build();
	public static final EntityEntry QWQ = EntityEntryBuilder.create().entity(W.class).id(W.ID,14).name(W.NAME).tracker(64, 3, true).egg(11547183, 13814482).build();
	public static final EntityEntry EVOLUTION = EntityEntryBuilder.create().entity(Evolution.class).id(Evolution.ID,15).name(Evolution.NAME).tracker(64, 3, true).egg(13779738, 13814482).build();
	public static final EntityEntry GUERRILLA_SHIELD_GUARD = EntityEntryBuilder.create().entity(GuerrillaShieldGuard.class).id(GuerrillaShieldGuard.ID,16).name(GuerrillaShieldGuard.NAME).tracker(64, 3, true).egg(2697256, 3356122).build();
	public static final EntityEntry ORIGINIUM_SLUG = EntityEntryBuilder.create().entity(OriginiumSlug.class).id(OriginiumSlug.ID,17).name(OriginiumSlug.NAME).tracker(64, 3, true).egg(2829609, 13813052).build();
	public static final EntityEntry SENIOR_CASTER = EntityEntryBuilder.create().entity(SeniorCaster.class).id(SeniorCaster.ID,18).name(SeniorCaster.NAME).tracker(64, 3, true).egg(2300956, 16129052).build();
	public static final EntityEntry SHIELD_SOLDIER = EntityEntryBuilder.create().entity(ShieldSoldier.class).id(ShieldSoldier.ID,19).name(ShieldSoldier.NAME).tracker(64, 3, true).egg(15264745, 13410074).build();
	public static final EntityEntry SOLDIER = EntityEntryBuilder.create().entity(Soldier.class).id(Soldier.ID,20).name(Soldier.NAME).tracker(64, 3, true).egg(15264745, 13410074).build();
	public static final EntityEntry YETI_ICECLEAVER = EntityEntryBuilder.create().entity(YetiIcecleaver.class).id(YetiIcecleaver.ID,21).name(YetiIcecleaver.NAME).tracker(64, 3, true).egg(5130824, 2706089).build();
	public static final EntityEntry YETI_SNIPER = EntityEntryBuilder.create().entity(YetiSniper.class).id(YetiSniper.ID,22).name(YetiSniper.NAME).tracker(64, 3, true).egg(15987958, 16777215).build();
	public static final EntityEntry SKULLSHATTERER = EntityEntryBuilder.create().entity(Skullshatterer.class).id(Skullshatterer.ID,23).name(Skullshatterer.NAME).tracker(64, 3, true).egg(4736582, 12148806).build();
	public static final EntityEntry DEFENSE_CRUSHER = EntityEntryBuilder.create().entity(DefenseCrusher.class).id(DefenseCrusher.ID,25).name(DefenseCrusher.NAME).tracker(64, 3, true).egg(15658734,5000012).build();
	public static final EntityEntry INFECTED_PATROL_CAPTAIN = EntityEntryBuilder.create().entity(InfectedPatrolCaptain.class).id(InfectedPatrolCaptain.ID,26).name(InfectedPatrolCaptain.NAME).tracker(64, 3, true).egg(3223599,6711397).build();
	public static final EntityEntry URSUS_ARMORED_CASTER = EntityEntryBuilder.create().entity(UrsusArmoredCaster.class).id(UrsusArmoredCaster.ID,27).name(UrsusArmoredCaster.NAME).tracker(64, 3, true).egg(3223599,6711397).build();
	public static final EntityEntry YETI_OPERATIVE = EntityEntryBuilder.create().entity(YetiOperative.class).id(YetiOperative.ID,28).name(YetiOperative.NAME).tracker(64, 3, true).egg(15262951,16777215).build();
	public static final EntityEntry FIREBALL = EntityEntryBuilder.create().entity(FireBall.class).id(FireBall.ID,29).name(FireBall.NAME).tracker(64, 3, true).build();
	public static final EntityEntry HATEFUL_AVENGER = EntityEntryBuilder.create().entity(HatefulAvenger.class).id(HatefulAvenger.ID,30).name(HatefulAvenger.NAME).tracker(64, 3, true).egg(1253651, 13377811).build();
	public static final EntityEntry GUERRILLA_SIGEGEBREAKER = EntityEntryBuilder.create().entity(GuerrillaSiegebreaker.class).id(GuerrillaSiegebreaker.ID,31).name(GuerrillaSiegebreaker.NAME).tracker(64, 3, true).egg(3223599,6711397).build();
	public static final EntityEntry ENRAGED_POSSESSED_SOLDIER= EntityEntryBuilder.create().entity(EnragedPossessedSoldier.class).id(EnragedPossessedSoldier.ID,32).name(EnragedPossessedSoldier.NAME).tracker(64, 3, true).egg(14212317,5692509).build();
	public static final EntityEntry SPIDER_ORIGINIUM_SLUG= EntityEntryBuilder.create().entity(SpiderOriginiumSlug.class).id(SpiderOriginiumSlug.ID,33).name(SpiderOriginiumSlug.NAME).tracker(64, 3, true).egg(2631208,16771584).build();
	public static final EntityEntry MUTANT_ROCK_SPIDER = EntityEntryBuilder.create().entity(MutantRockSpider.class).id(MutantRockSpider.ID,34).name(MutantRockSpider.NAME).tracker(64, 3, true).egg(2631208,14921773).build();
	public static final EntityEntry SPIDER_GLACIAL_ORIGINIUM_SLUG = EntityEntryBuilder.create().entity(SpiderGlacialOriginiumSlug.class).id(SpiderGlacialOriginiumSlug.ID,35).name(SpiderGlacialOriginiumSlug.NAME).tracker(64, 3, true).egg(15066344,4949224).build();
	public static final EntityEntry FROSTNOVA = EntityEntryBuilder.create().entity(Frostnova.class).id(Frostnova.ID,36).name(Frostnova.NAME).tracker(64, 3, true).egg(16777215, 15725556).build();
	public static final EntityEntry ENTITY_ORUNDUM_TH = EntityEntryBuilder.create().entity(EntityOrundumTh.class).id(EntityOrundumTh.ID,37).name(EntityOrundumTh.NAME).tracker(64, 3, true).build();
	public static final EntityEntry SARKAZ_SENTINEL = EntityEntryBuilder.create().entity(SarkazSentinel.class).id(SarkazSentinel.ID,38).name(SarkazSentinel.NAME).tracker(64, 3, true).egg(2368809, 3117135).build();
	public static final EntityEntry ENERGY_POLYMER = EntityEntryBuilder.create().entity(EnergyPolymer.class).id(EnergyPolymer.ID,39).name(EnergyPolymer.NAME).tracker(64, 3, true).build();
	public static final EntityEntry ARROW = EntityEntryBuilder.create().entity(Arrow.class).id(Arrow.ID,40).name(Arrow.NAME).tracker(64, 1, true).build();
	public static final EntityEntry SARKAZ_GRUDGEBEARER = EntityEntryBuilder.create().entity(SarkazGrudgebearer.class).id(SarkazGrudgebearer.ID,41).name(SarkazGrudgebearer.NAME).tracker(64, 3, true).egg(2368809, 3117135).build();
	public static final EntityEntry MORTAR_GUNNER = EntityEntryBuilder.create().entity(MortarGunner.class).id(MortarGunner.ID,42).name(MortarGunner.NAME).tracker(64, 3, true).egg(15264745, 13410074).build();
	public static final EntityEntry CASTER = EntityEntryBuilder.create().entity(Caster.class).id(Caster.ID,43).name(Caster.NAME).tracker(64, 3, true).egg(15264745, 13410074).build();
	public static final EntityEntry HEAVY_DEFENDER = EntityEntryBuilder.create().entity(HeavyDefender.class).id(HeavyDefender.ID,44).name(HeavyDefender.NAME).tracker(64, 3, true).egg(1842206, 3487032).build();
	public static final EntityEntry DUAL_SWORDSMAN = EntityEntryBuilder.create().entity(DualSwordsman.class).id(DualSwordsman.ID,45).name(DualSwordsman.NAME).tracker(64, 3, true).egg(15264745, 13410074).build();
	public static final EntityEntry AMIYA = EntityEntryBuilder.create().entity(Amiya.class).id(Amiya.ID,46).name(Amiya.NAME).tracker(64, 3, true).build();
	public static final EntityEntry TIBETAN_ANTELOPE = EntityEntryBuilder.create().entity(TibetanAntelope.class).id(TibetanAntelope.ID,47).name(TibetanAntelope.NAME).tracker(64, 3, true).egg(8350803, 10526878).build();
	public static final EntityEntry ELK = EntityEntryBuilder.create().entity(Elk.class).id(Elk.ID,48).name(Elk.NAME).tracker(64, 3, true).egg(13151118, 3416087).build();
	public static final EntityEntry SKUNK = EntityEntryBuilder.create().entity(Skunk.class).id(Skunk.ID,49).name(Skunk.NAME).tracker(64, 3, true).egg(2303527, 11515577).build();
	public static final EntityEntry DEEP_SEA_SLIDER = EntityEntryBuilder.create().entity(DeepSeaSlider.class).id(DeepSeaSlider.ID,50).name(DeepSeaSlider.NAME).tracker(64, 3, true).egg(6528, 13948116).build();
	public static final EntityEntry BASIN_SEA_REAPER = EntityEntryBuilder.create().entity(BasinSeaReaper.class).id(BasinSeaReaper.ID,51).name(BasinSeaReaper.NAME).tracker(64, 3, true).egg(6528, 13948116).build();
	public static final EntityEntry POCKET_SEA_CRAWLER = EntityEntryBuilder.create().entity(PocketSeaCrawler.class).id(PocketSeaCrawler.ID,52).name(PocketSeaCrawler.NAME).tracker(64, 3, true).egg(6528, 13948116).build();
	public static final EntityEntry SHELL_SEA_RUNNER = EntityEntryBuilder.create().entity(ShellSeaRunner.class).id(ShellSeaRunner.ID,53).name(ShellSeaRunner.NAME).tracker(64, 3, true).egg(6528, 13948116).build();
	public static final EntityEntry COCKTAIL_THROWER = EntityEntryBuilder.create().entity(CocktailThrower.class).id(CocktailThrower.ID,54).name(CocktailThrower.NAME).tracker(64, 3, true).egg(1776407, 4605510).build();
	public static final EntityEntry WRAITH = EntityEntryBuilder.create().entity(Wraith.class).id(Wraith.ID,55).name(Wraith.NAME).tracker(64, 3, true).egg(1118481, 15332702).build();
	public static final EntityEntry TECHNICAL_SCOUT = EntityEntryBuilder.create().entity(TechnicalScout.class).id(TechnicalScout.ID,56).name(TechnicalScout.NAME).tracker(64, 3, true).egg(1907997, 4085078).build();
	public static final EntityEntry SARKAZ_CENTURION = EntityEntryBuilder.create().entity(SarkazCenturion.class).id(SarkazCenturion.ID,57).name(SarkazCenturion.NAME).tracker(64, 3, true).egg(1579032, 7564390).build();
	public static final EntityEntry ZERGLING = EntityEntryBuilder.create().entity(Zergling.class).id(Zergling.ID,58).name(Zergling.NAME).tracker(64, 3, true).egg(5915958, 8734832).build();
	public static final EntityEntry HYDRALISK = EntityEntryBuilder.create().entity(Hydralisk.class).id(Hydralisk.ID,59).name(Hydralisk.NAME).tracker(64, 3, true).egg(5915958, 8734832).build();
	public static final EntityEntry QUEEN = EntityEntryBuilder.create().entity(Queen.class).id(Queen.ID,60).name(Queen.NAME).tracker(64, 3, true).egg(5915958, 8734832).build();
	public static final EntityEntry HEDGEHOG = EntityEntryBuilder.create().entity(Hedgehog.class).id(Hedgehog.ID,61).name(Hedgehog.NAME).tracker(64, 3, true).egg(2301724, 9932681).build();
	public static final EntityEntry FROG = EntityEntryBuilder.create().entity(Frog.class).id(Frog.ID,62).name(Frog.NAME).tracker(64, 3, true).egg(6786347, 4875038).build();
	public static final EntityEntry BULL_FROG = EntityEntryBuilder.create().entity(Bullfrog.class).id(Bullfrog.ID,63).name(Bullfrog.NAME).tracker(64, 3, true).egg(6786347, 4875038).build();
	public static final EntityEntry OWL = EntityEntryBuilder.create().entity(Owl.class).id(Owl.ID,64).name(Owl.NAME).tracker(64, 3, true).egg(6179379, 14144987).build();
	public static final EntityEntry RACCOON = EntityEntryBuilder.create().entity(Raccoon.class).id(Raccoon.ID,65).name(Raccoon.NAME).tracker(64, 3, true).egg(4670788, 10001311).build();
	public static final EntityEntry SQUIRREL = EntityEntryBuilder.create().entity(Squirrel.class).id(Squirrel.ID,66).name(Squirrel.NAME).tracker(64, 3, true).egg(8283705, 1709842).build();
	public static final EntityEntry RHINO = EntityEntryBuilder.create().entity(Rhino.class).id(Rhino.ID,67).name(Rhino.NAME).tracker(64, 3, true).egg(11905441, 11053224).build();
	public static final EntityEntry LION = EntityEntryBuilder.create().entity(Lion.class).id(Lion.ID,68).name(Lion.NAME).tracker(64, 3, true).egg(8876626, 16313819).build();
	public static final EntityEntry CHEETAH = EntityEntryBuilder.create().entity(Cheetah.class).id(Cheetah.ID,69).name(Cheetah.NAME).tracker(64, 3, true).egg(9731671, 393985).build();
	public static final EntityEntry HYENA = EntityEntryBuilder.create().entity(Hyena.class).id(Hyena.ID,70).name(Hyena.NAME).tracker(64, 3, true).egg(8808229, 1513240).build();
	public static final EntityEntry SMILING_CORPSE_MOUNTAIN = EntityEntryBuilder.create().entity(SmilingCorpseMountain.class).id(SmilingCorpseMountain.ID,71).name(SmilingCorpseMountain.NAME).tracker(64, 3, true).egg(986641, 6160641).build();
	public static final EntityEntry RIOTER = EntityEntryBuilder.create().entity(Rioter.class).id(Rioter.ID,72).name(Rioter.NAME).tracker(64, 3, true).egg(2565932, 9802644).build();
	public static final EntityEntry SURTR = EntityEntryBuilder.create().entity(Surtr.class).id(Surtr.ID,73).name(Surtr.NAME).tracker(64, 3, true).build();
	public static final EntityEntry PURESTREAM = EntityEntryBuilder.create().entity(Purestream.class).id(Purestream.ID,74).name(Purestream.NAME).tracker(64, 3, true).build();
	public static final EntityEntry MINE = EntityEntryBuilder.create().entity(EntityMine.class).id(EntityMine.ID,75).name(EntityMine.NAME).tracker(64, 3, true).build();
	public static final EntityEntry OWO = EntityEntryBuilder.create().entity(com.weedycow.arkworld.entity.operator.sniper.OW.class).id(com.weedycow.arkworld.entity.operator.sniper.OW.ID,76).name(com.weedycow.arkworld.entity.operator.sniper.OW.NAME).tracker(64, 3, true).build();

	public static EntityEntry[] entityEntries = {ORIGINIUTANT_TUMOR,BOSS_RUA_COW,BOSS_OCEAN_CAT,PATRIOT,ENTITY_FROST_CRYSTAL
			,FAUST,MAYFEST,BLACK_SNAKE,AVENGER,CROSSBOWMAN,REGICIDE,ENTITY_BOMB,QWQ,EVOLUTION,ORIGINIUTANT_EXCRESCENCE
			,GUERRILLA_SHIELD_GUARD,ORIGINIUM_SLUG,SENIOR_CASTER,SHIELD_SOLDIER,SOLDIER,YETI_ICECLEAVER,YETI_SNIPER
			,SKULLSHATTERER,DEFENSE_CRUSHER,INFECTED_PATROL_CAPTAIN,URSUS_ARMORED_CASTER,SARKAZ_BLADEWEAVER,WRAITH
			,YETI_OPERATIVE,FIREBALL,HATEFUL_AVENGER,GUERRILLA_SIGEGEBREAKER,ENRAGED_POSSESSED_SOLDIER,COCKTAIL_THROWER
			,SPIDER_ORIGINIUM_SLUG,MUTANT_ROCK_SPIDER,SPIDER_GLACIAL_ORIGINIUM_SLUG,FROSTNOVA,ENTITY_ORUNDUM_TH,RHINO
			,SARKAZ_SENTINEL,ENERGY_POLYMER,ARROW,SARKAZ_GRUDGEBEARER,MORTAR_GUNNER,CASTER,HEAVY_DEFENDER,DUAL_SWORDSMAN
			,AMIYA,TIBETAN_ANTELOPE,ELK,SKUNK,DEEP_SEA_SLIDER,BASIN_SEA_REAPER,POCKET_SEA_CRAWLER,SHELL_SEA_RUNNER
			,TECHNICAL_SCOUT,SARKAZ_CENTURION,ZERGLING,HYDRALISK,QUEEN,HEDGEHOG,FROG,BULL_FROG,OWL,RACCOON,SQUIRREL
			,LION,CHEETAH,HYENA,SMILING_CORPSE_MOUNTAIN,RIOTER,SURTR,PURESTREAM,MINE,OWO};

	@SubscribeEvent
	public static void onRegistry(RegistryEvent.Register<EntityEntry> event)
	{
		IForgeRegistry<EntityEntry> registry = event.getRegistry();
		registry.registerAll(entityEntries);
	}
}
