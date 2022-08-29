package com.weedycow.arkworld.registry;

import com.weedycow.arkworld.block.doll.*;
import com.weedycow.arkworld.block.floor.*;
import com.weedycow.arkworld.block.machine.BlockModBox;
import com.weedycow.arkworld.block.machine.BlockOperatorBox;
import com.weedycow.arkworld.block.machine.BlockWeaponTable;
import com.weedycow.arkworld.block.machine.infrastructure.*;
import com.weedycow.arkworld.block.nature.plant.BlockIberiaBigFlower;
import com.weedycow.arkworld.block.nature.plant.BlockIberiaFlower;
import com.weedycow.arkworld.block.nature.plant.BlockSeagrass;
import com.weedycow.arkworld.block.nature.surface.BlockDripstone;
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
import com.weedycow.arkworld.entity.operator.sniper.OW;
import com.weedycow.arkworld.entity.weapon.*;
import com.weedycow.arkworld.item.armor.*;
import com.weedycow.arkworld.renderer.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class RenderRegistry
{
	static Class[] arkTileClass = {BlockSealedFloor.TileSealedFloor.class, BlockOriginiumFloor.TileOriginiumFloor.class, BlockEvolutionDoll.TileEvolutionDoll.class,
			BlockFaustDoll.TileFaustDoll.class, BlockFrostnovaDoll.TileFrostnovaDoll.class, BlockMayfestDoll.TileMayfestDoll.class,
			BlockPatriotDoll.TilePatriotDoll.class,BlockRegicideDoll.TileRegicideDoll.class, BlockSkullshattererDoll.TileSkullshattererDoll.class,
			BlockBlackSnakeDoll.TileBlackSnakeDoll.class,BlockWDoll.TileWDoll.class, BlockProcessingStation.TileProcessingStation.class,
			BlockTrainingRoom.TileTrainingRoom.class, BlockControlCenter.TileControlCenter.class, BlockOffice.TileOffice.class,
			BlockManufacturingStation.TileManufacturingStation.class,BlockTradingStation.TileTradingStation.class,BlockPowerStation.TilePowerStation.class,
			BlockDormitory.TileDormitory.class, BlockWeaponTable.TileWeaponTable.class, BlockSeagrass.TileSeagrass.class, BlockIberiaFlower.TileIberiaFlower.class,
			BlockIberiaBigFlower.TileIberiaBigFlower.class, BlockDripstone.TileDripstone.class, BlockFloorBase.TileFloorBase.class,
			BlockDefenseFloor.TileDefenseFloor.class, BlockPushFloor.TilePushFloor.class, BlockHealingFloor.TileHealingFloor.class,
			BlockHotFloor.TileHotFloor.class, BlockReduceDefenseFloor.TileReduceDefenseFloor.class, BlockModBox.TileModBox.class,
			BlockOperatorBox.TileOperatorBox.class};

	static Class[] arkMobClass = {OriginiutantExcrescence.class,OriginiutantTumor.class,EntityMine.class,
			GuerrillaShieldGuard.class,Faust.class,Mayfest.class,Avenger.class,Crossbowman.class,Regicide.class,
			W.class,Evolution.class,SeniorCaster.class,ShieldSoldier.class,Soldier.class,YetiIcecleaver.class,
			YetiSniper.class,Skullshatterer.class,DefenseCrusher.class,InfectedPatrolCaptain.class,
			UrsusArmoredCaster.class,YetiOperative.class,HatefulAvenger.class,GuerrillaSiegebreaker.class,
			EnragedPossessedSoldier.class,SpiderOriginiumSlug.class,BlackSnake.class,Frostnova.class,
			SpiderGlacialOriginiumSlug.class,Patriot.class,SarkazBladeweaver.class,SarkazGrudgebearer.class,
			SarkazSentinel.class,MortarGunner.class,Caster.class,HeavyDefender.class,DualSwordsman.class,
			DeepSeaSlider.class,BasinSeaReaper.class,PocketSeaCrawler.class,ShellSeaRunner.class,
			CocktailThrower.class,Wraith.class,TechnicalScout.class,SarkazCenturion.class,Zergling.class,
			Hydralisk.class,Queen.class,SmilingCorpseMountain.class,Rioter.class,MutantRockSpider.class};

	static Class[] arkDevice = {EnergyPolymer.class,EntityMine.class};

	static Class[] projectileA = {Arrow.class};

	static Class[] projectileT = {FireBall.class,Bomb.class,SimpleThrowable.class};

	static Class[] arkAnimalClass = {TibetanAntelope.class,Elk.class,Skunk.class,Hedgehog.class,Frog.class,
			Bullfrog.class,Owl.class,Raccoon.class,Squirrel.class,Rhino.class,Lion.class,Cheetah.class,Hyena.class};

	static Class[] operatorClass = {Amiya.class,Surtr.class, Purestream.class, OW.class};

	static Class[] armorClass = {FrostnovaArmor.class,PocketSeaCrawlerArmor.class,DeepSeaSliderArmor.class,PatriotArmor.class,
			RegicideArmor.class,SkullshattererArmor.class,DefenseCrusherArmor.class,GuerrillaSiegebreakerArmor.class,
			TechnicalScoutArmor.class,WraithArmor.class,SurtrArmor.class,BasinSeaReaperArmor.class,SmilingCorpseMountainArmor.class,
			SeniorCasterArmor.class,SoldierArmor.class,YetiOperativeArmor.class};

	public static void register()
	{
		for (Class cl:arkTileClass){ClientRegistry.bindTileEntitySpecialRenderer(cl, new RenderArkTile());}

		for (Class cl:arkMobClass){RenderingRegistry.registerEntityRenderingHandler(cl, RenderArkmob::new);}

		for (Class cl:operatorClass){RenderingRegistry.registerEntityRenderingHandler(cl, RenderOperator::new);}

		for (Class cl:arkAnimalClass){RenderingRegistry.registerEntityRenderingHandler(cl, RenderArkAnimal::new);}

		for (Class cl:projectileA){RenderingRegistry.registerEntityRenderingHandler(cl, RenderProjectileA::new);}

		for (Class cl:projectileT){RenderingRegistry.registerEntityRenderingHandler(cl, RenderProjectileT::new);}

		for (Class cl:arkDevice){RenderingRegistry.registerEntityRenderingHandler(cl, RenderArkDevice::new);}

		for (Class cl:armorClass){GeoArmorRenderer.registerArmorRenderer(cl, new RenderArkArmor());}

		RenderingRegistry.registerEntityRenderingHandler(BossRuaCow.class, RenderBossRuaCow::new);

		RenderingRegistry.registerEntityRenderingHandler(BossOceanCat.class, RenderBossOceanCat::new);

		RenderingRegistry.registerEntityRenderingHandler(OriginiumSlug.class, RenderOriginiumSlug::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityOrundumTh.class, manager ->
		{
			RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
			return new RenderSnowball<EntityOrundumTh>(manager, ItemRegistry.ORUNDUM_TH,renderItem) {
			};
		});
	}
}