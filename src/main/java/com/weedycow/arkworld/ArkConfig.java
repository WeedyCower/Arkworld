package com.weedycow.arkworld;

import net.minecraftforge.common.config.Config;

@Config(modid = Arkworld.MODID, type = Config.Type.INSTANCE, name = "Arkworld", category = "")
@Config.LangKey("config.arkworld")
public class ArkConfig
{
    @Config.LangKey("config.arkworld.common")
    public static Common common = new Common();

    @Config.LangKey("config.arkworld.creature")
    public static Creature creature = new Creature();

    @Config.LangKey("config.arkworld.item")
    public static Item item = new Item();

    @Config.LangKey("config.arkworld.block")
    public static Block block = new Block();

    @Config.LangKey("config.arkworld.world")
    public static World world = new World();

    public static class Common
    {
        @Config.LangKey("config.arkworld.bgm")
        @Config.RequiresWorldRestart
        public boolean bgm = true;

        @Config.LangKey("config.arkworld.particle")
        @Config.RequiresWorldRestart
        public boolean particle = true;

        @Config.LangKey("config.arkworld.bar")
        public Bar bar = new Bar();

        public static class Bar
        {
            @Config.LangKey("config.arkworld.draw")
            public boolean draw = true;

            @Config.LangKey("config.arkworld.notShowAnimal")
            public boolean notShowAnimal = true;

            @Config.LangKey("config.arkworld.notShowPlayer")
            public boolean notShowPlayer = false;

            @Config.LangKey("config.arkworld.notShowBoss")
            public boolean notShowBoss = false;

            @Config.LangKey("config.arkworld.notShowMob")
            public boolean notShowMob = false;

            @Config.LangKey("config.arkworld.notShowOperator")
            public boolean notShowOperator = false;

            @Config.LangKey("config.arkworld.maxDistance")
            public int maxDistance = 24;

            @Config.LangKey("config.arkworld.renderInF1")
            public boolean renderInF1 = false;

            @Config.LangKey("config.arkworld.heightAbove")
            public double heightAbove = 0.3D;

            @Config.LangKey("config.arkworld.barHeight")
            public int barHeight = 2;

            @Config.LangKey("config.arkworld.barLength")
            public int barLength = 25;

            @Config.LangKey("config.arkworld.showOnlyFocused")
            public boolean showOnlyFocused = false;
        }
    }

    public static class Creature
    {
        @Config.LangKey("config.arkworld.mob")
        public Mob mob = new Mob();

        @Config.LangKey("config.arkworld.animal")
        public Animal animal = new Animal();

        @Config.LangKey("config.arkworld.operator")
        public Operator operator = new Operator();

        public static class Mob
        {
            @Config.LangKey("config.arkworld.union")
            public Union union = new Union();

            @Config.LangKey("config.arkworld.ursus")
            public Ursus ursus = new Ursus();

            @Config.LangKey("config.arkworld.split")
            public Split split = new Split();

            @Config.LangKey("config.arkworld.kazdel")
            public Kazdel kazdel = new Kazdel();

            @Config.LangKey("config.arkworld.cthulhu")
            public Cthulhu cthulhu = new Cthulhu();

            @Config.LangKey("config.arkworld.other")
            public Other other = new Other();

            public static class Other
            {
                @Config.LangKey("entity.arkworld.springworm.name")
                public Springworm springworm = new Springworm();

                @Config.LangKey("entity.arkworld.hydralisk.name")
                public Hydralisk hydralisk = new Hydralisk();

                @Config.LangKey("entity.arkworld.queen.name")
                public Queen queen = new Queen();

                @Config.LangKey("entity.arkworld.smilingCorpseMountain.name")
                public SmilingCorpseMountain smilingCorpseMountain = new SmilingCorpseMountain();

                public static class SmilingCorpseMountain
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 200;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.5;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 14;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 200;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 40;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;
                }

                public static class Queen
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 1000;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.45;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 10;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.3;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 40;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 5;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 1000;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 60;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 4;
                }

                public static class Hydralisk
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 250;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.45;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 10;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.1;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 16;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 2;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 150;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 40;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 8;
                }

                public static class Springworm
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 125;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.7;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.1;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 125;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 20;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 3;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 14;
                }
            }

            public static class Cthulhu
            {
                @Config.LangKey("entity.arkworld.deepSeaSlider.name")
                public DeepSeaSlider deepSeaSlider = new DeepSeaSlider();

                @Config.LangKey("entity.arkworld.basinSeaReaper.name")
                public BasinSeaReaper basinSeaReaper = new BasinSeaReaper();

                @Config.LangKey("entity.arkworld.pocketSeaCrawler.name")
                public PocketSeaCrawler pocketSeaCrawler = new PocketSeaCrawler();

                @Config.LangKey("entity.arkworld.shellSeaRunner.name")
                public ShellSeaRunner shellSeaRunner = new ShellSeaRunner();

                public static class ShellSeaRunner
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 150;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.5;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 0;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.2;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 0;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 150;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 30;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 14;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 50;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 3;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 1;
                }

                public static class PocketSeaCrawler
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 1250;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.45;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 0;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 4;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 125;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 60;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 3;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 15;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 500;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 5;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 0;
                }

                public static class BasinSeaReaper
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 1000;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.45;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 40;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.75;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 2;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 100;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 60;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 3;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 20;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 250;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 5;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 0;
                }

                public static class DeepSeaSlider
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 140;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.55;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 6.5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.1;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 140;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 40;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 14;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 40;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 14;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 1;
                }
            }

            public static class Union
            {
                @Config.LangKey("entity.arkworld.crossbowman.name")
                public Crossbowman crossbowman = new Crossbowman();

                @Config.LangKey("entity.arkworld.regicide.name")
                public Regicide regicide = new Regicide();

                @Config.LangKey("entity.arkworld.shieldSoldier.name")
                public ShieldSoldier shieldSoldier = new ShieldSoldier();

                @Config.LangKey("entity.arkworld.soldier.name")
                public Soldier soldier = new Soldier();

                @Config.LangKey("entity.arkworld.enragedPossessedSoldier.name")
                public EnragedPossessedSoldier enragedPossessedSoldier = new EnragedPossessedSoldier();

                @Config.LangKey("entity.arkworld.guerrillaShieldGuard.name")
                public GuerrillaShieldGuard guerrillaShieldGuard = new GuerrillaShieldGuard();

                @Config.LangKey("entity.arkworld.defenseCrusher.name")
                public DefenseCrusher defenseCrusher = new DefenseCrusher();

                @Config.LangKey("entity.arkworld.defenseCrusher.name")
                public GuerrillaSiegebreaker guerrillaSiegebreaker = new GuerrillaSiegebreaker();

                @Config.LangKey("entity.arkworld.hatefulAvenger.name")
                public HatefulAvenger hatefulAvenger = new HatefulAvenger();

                @Config.LangKey("entity.arkworld.seniorCaster.name")
                public SeniorCaster seniorCaster = new SeniorCaster();

                @Config.LangKey("entity.arkworld.yetiIcecleaver.name")
                public YetiIcecleaver yetiIcecleaver = new YetiIcecleaver();

                @Config.LangKey("entity.arkworld.avenger.name")
                public Avenger avenger = new Avenger();

                @Config.LangKey("entity.arkworld.originiumSlug.name")
                public OriginiumSlug originiumSlug = new OriginiumSlug();

                @Config.LangKey("entity.arkworld.spiderGlacialOriginiumSlug.name")
                public SpiderGlacialOriginiumSlug spiderGlacialOriginiumSlug = new SpiderGlacialOriginiumSlug();

                @Config.LangKey("entity.arkworld.spiderOriginiumSlug.name")
                public SpiderOriginiumSlug spiderOriginiumSlug = new SpiderOriginiumSlug();

                @Config.LangKey("entity.arkworld.yetiOperative.name")
                public YetiOperative yetiOperative = new YetiOperative();

                @Config.LangKey("entity.arkworld.blackSnake.name")
                public BlackSnake blackSnake = new BlackSnake();

                @Config.LangKey("entity.arkworld.faust.name")
                public Faust faust = new Faust();

                @Config.LangKey("entity.arkworld.frostnova.name")
                public Frostnova frostnova = new Frostnova();

                @Config.LangKey("entity.arkworld.mayfest.name")
                public Mayfest mayfest = new Mayfest();

                @Config.LangKey("entity.arkworld.patriot.name")
                public Patriot patriot = new Patriot();

                @Config.LangKey("entity.arkworld.skullshatterer.name")
                public Skullshatterer skullshatterer = new Skullshatterer();

                @Config.LangKey("entity.arkworld.w.name")
                public W w = new W();

                @Config.LangKey("entity.arkworld.yetiSniper.name")
                public YetiSniper yetiSniper = new YetiSniper();

                @Config.LangKey("entity.arkworld.mortarGunner.name")
                public MortarGunner mortarGunner = new MortarGunner();

                @Config.LangKey("entity.arkworld.caster.name")
                public Caster caster = new Caster();

                @Config.LangKey("entity.arkworld.heavyDefender.name")
                public HeavyDefender heavyDefender = new HeavyDefender();

                @Config.LangKey("entity.arkworld.dualSwordsman.name")
                public DualSwordsman dualSwordsman = new DualSwordsman();

                @Config.LangKey("entity.arkworld.cocktailThrower.name")
                public CocktailThrower cocktailThrower = new CocktailThrower();

                @Config.LangKey("entity.arkworld.wraith.name")
                public Wraith wraith = new Wraith();

                @Config.LangKey("entity.arkworld.technicalScout.name")
                public TechnicalScout technicalScout = new TechnicalScout();

                @Config.LangKey("entity.arkworld.sarkazCenturion.name")
                public SarkazCenturion sarkazCenturion = new SarkazCenturion();

                @Config.LangKey("entity.arkworld.rioter.name")
                public Rioter rioter = new Rioter();

                public static class Rioter
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 85;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.45;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 2.5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 0;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 85;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 40;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 12.5f;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 45;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 5;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 2;
                }

                public static class SarkazCenturion
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 750;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.45;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 12.5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.35;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 5;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 750;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 100;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 8;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 35;
                }

                public static class TechnicalScout
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 350;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.45;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.2;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 350;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 50;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 13.5f;
                }

                public static class Wraith
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 115;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.6;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 6;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 35;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 115;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 20;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 1;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 5;
                }

                public static class CocktailThrower
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 77.5;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.45;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 2.5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 9;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 0;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 77;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 60;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 9;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 7.5f;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 1.75f;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 3.5f;
                }

                public static class W
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 500;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.6;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.5;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 23.5f;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 5;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 500;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 80;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 8;

                    @Config.LangKey("config.arkworld.explosionDestroyTerrain")
                    public boolean explosionDestroyTerrain = false;

                    @Config.LangKey("config.arkworld.shootingStrength")
                    public float shootingStrength = 1.5f;

                    @Config.LangKey("config.arkworld.shootingInaccuracy")
                    public float shootingInaccuracy = 1;

                    @Config.LangKey("config.arkworld.c4Interval")
                    public int c4Interval = 420;
                }

                public static class Skullshatterer
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 525;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.45;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 7.5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.3;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 50;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 5;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 525;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 60;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 4;

                    @Config.LangKey("config.arkworld.explosionDestroyTerrain")
                    public boolean explosionDestroyTerrain = false;
                }

                public static class Patriot
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 2250;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.4;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 75;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.9;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 120;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 7;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 2250;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 80;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 3;

                    @Config.LangKey("config.arkworld.javelinInterval")
                    public int javelinInterval = 400;

                    @Config.LangKey("config.arkworld.javelinRange")
                    public float javelinRange = 32;

                    @Config.LangKey("config.arkworld.tauntRange")
                    public float tauntRange = 8;

                    @Config.LangKey("config.arkworld.canTaunt")
                    public boolean canTaunt = true;

                    @Config.LangKey("config.arkworld.spawnGuerrillaSiegebreaker")
                    public boolean spawnGuerrillaSiegebreaker  = true;
                }

                public static class Mayfest
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 1400;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.45;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 10;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.6;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 25;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 3;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 1400;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 120;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 16;
                }

                public static class Frostnova
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 1250;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.4;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 12.5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.5;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 21;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 5;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 1250;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 74;

                    @Config.LangKey("config.arkworld.iceRingInterval")
                    public int iceRingInterval = 170;

                    @Config.LangKey("config.arkworld.iceRingRange")
                    public float iceRingRange = 10;

                    @Config.LangKey("config.arkworld.freezeInterval")
                    public int freezeInterval = 600;

                    @Config.LangKey("config.arkworld.coldTime")
                    public int coldTime = 160;

                    @Config.LangKey("config.arkworld.freezeRange")
                    public float freezeRange = 16;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 8;
                }

                public static class Faust
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 1850;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.4;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 17.5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.35;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 50;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 1850;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 100;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 16;
                }

                public static class BlackSnake
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 2500;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.4;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 40;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.5;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 38.5f;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 6;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 2500;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 90;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 6;

                    @Config.LangKey("config.arkworld.igniteInterval")
                    public int igniteInterval = 380;

                    @Config.LangKey("config.arkworld.igniteRange")
                    public float igniteRange = 16;

                    @Config.LangKey("config.arkworld.detonateInterval")
                    public int detonateInterval = 700;

                    @Config.LangKey("config.arkworld.detonateRange")
                    public float detonateRange = 16;

                    @Config.LangKey("config.arkworld.energyPolymerInterval")
                    public int energyPolymerInterval = 1000;

                    @Config.LangKey("config.arkworld.energyPolymerRange")
                    public float energyPolymerRange = 16;

                    @Config.LangKey("config.arkworld.energyPolymerNumbers")
                    public int energyPolymerNumbers = 5;

                    @Config.LangKey("config.arkworld.energyPolymerDestroyTerrain")
                    public boolean energyPolymerDestroyTerrain = false;

                    @Config.LangKey("config.arkworld.endlessFireTime")
                    public int endlessFireTime = 600;

                    @Config.LangKey("config.arkworld.endlessFireRange")
                    public float endlessFireRange = 128;

                    @Config.LangKey("config.arkworld.burningBreathTime")
                    public int burningBreathTime = 610;

                    @Config.LangKey("config.arkworld.endlessFireDestroyTerrain")
                    public boolean endlessFireDestroyTerrain = false;

                    @Config.LangKey("config.arkworld.detonateDestroyTerrain")
                    public boolean detonateDestroyTerrain = false;
                }

                public static class YetiSniper
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 125;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.45;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 4;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 1;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 125;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 48;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 8;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 50;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 1;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 1;
                }

                public static class YetiOperative
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 170;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.55;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 170;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 40;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 18;
                }

                public static class SpiderOriginiumSlug
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 123;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.5;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 0;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 0;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 123;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 34;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 13;

                    @Config.LangKey("config.arkworld.explosionDestroyTerrain")
                    public boolean explosionDestroyTerrain = false;

                    @Config.LangKey("config.arkworld.alphaMaxHealth")
                    public float alphaMaxHealth = 62;

                    @Config.LangKey("config.arkworld.alphaAttackDamage")
                    public float alphaAttackDamage = 4.5f;
                }

                public static class SpiderGlacialOriginiumSlug
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 162.5;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.5;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 0;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 0;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 162;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 34;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 15;

                    @Config.LangKey("config.arkworld.explosionDestroyTerrain")
                    public boolean explosionDestroyTerrain = false;

                    @Config.LangKey("config.arkworld.alphaMaxHealth")
                    public float alphaMaxHealth = 80;

                    @Config.LangKey("config.arkworld.alphaAttackDamage")
                    public float alphaAttackDamage = 3.5f;
                }

                public static class OriginiumSlug
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 27.5;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.5;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 0;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 0;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 27;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 34;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 6.5f;

                    @Config.LangKey("config.arkworld.alphaMaxHealth")
                    public float alphaMaxHealth = 25;

                    @Config.LangKey("config.arkworld.alphaAttackDamage")
                    public float alphaAttackDamage = 2.75f;

                    @Config.LangKey("config.arkworld.betaMaxHealth")
                    public float betaMaxHealth = 25;

                    @Config.LangKey("config.arkworld.betaAttackDamage")
                    public float betaAttackDamage = 2.75f;
                }

                public static class Avenger
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 250;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.425;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 10;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.5;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 2;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 250;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 46;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 15;
                }

                public static class YetiIcecleaver
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 800;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.45;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 25;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.2;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 3;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 800;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 60;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 41.5f;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 450;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 13.5f;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 5;
                }

                public static class SeniorCaster
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 350;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.45;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 6;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.5;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 350;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 76;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 12;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 12;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 325;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 7;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 2;
                }

                public static class HatefulAvenger
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 900;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.425;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 11.5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.5;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 2;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 900;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 46;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 24;
                }

                public static class GuerrillaSiegebreaker
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 190;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.5;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 27.5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.2;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 2;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 190;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 38;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 18;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 60;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 3.5f;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 0;
                }

                public static class DefenseCrusher
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 500;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.4;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 50;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 4;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 500;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 70;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 4;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 50;

                    @Config.LangKey("config.arkworld.vertigoTime")
                    public int vertigoTime = 140;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 500;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 25;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 50;
                }

                public static class GuerrillaShieldGuard
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 750;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.35;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 65;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.6;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 4;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 750;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 78;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 35;

                    @Config.LangKey("config.arkworld.tauntRange")
                    public float tauntRange = 8;

                    @Config.LangKey("config.arkworld.canTaunt")
                    public boolean canTaunt = true;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 500;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 5;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 10;
                }

                public static class EnragedPossessedSoldier
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 1000;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.6;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 10;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.3;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 2;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 1000;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 27;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 50;

                    @Config.LangKey("config.arkworld.takeDamagePerSecond")
                    public float takeDamagePerSecond  = 16.5f;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 500;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 27.5f;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 1.5f;

                    @Config.LangKey("config.arkworld.leaderTakeDamagePerSecond")
                    public float leaderTakeDamagePerSecond = 8.5f;
                }

                public static class Soldier
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 80;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.55;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 80;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 40;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 12;
                }

                public static class DualSwordsman
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 100;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.5;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 100;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 24;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 1;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 8.75f;
                }

                public static class ShieldSoldier
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 100;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.5;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 12.5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 100;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 40;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 12;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 50;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 2;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 2.5f;
                }

                public static class HeavyDefender
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 300;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.425;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 40;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 3;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 300;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 50;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 15;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 200;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 0;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 0;
                }

                public static class Regicide
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 300;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.7;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 6;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.5;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 300;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 60;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 1;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 20;
                }

                public static class Crossbowman
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 70;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.45;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 7.75f;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 70;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 60;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 8;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 25;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 2.5f;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 1;
                }

                public static class MortarGunner
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 165;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.4;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 7.5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 10f;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 2;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 165;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 100;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 16;

                    @Config.LangKey("item.arkworld.info.destroyTerrain")
                    public boolean destroyTerrain = false;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 85;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 0;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 3.75f;
                }

                public static class Caster
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 80;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.4;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 2.5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.5;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 5f;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 80;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 80;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 16;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 40;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 2.5f;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 3.75f;
                }
            }

            public static class Ursus
            {
                @Config.LangKey("entity.arkworld.infectedPatrolCaptain.name")
                public InfectedPatrolCaptain infectedPatrolCaptain = new InfectedPatrolCaptain();

                @Config.LangKey("entity.arkworld.ursusArmoredCaster.name")
                public UrsusArmoredCaster ursusArmoredCaster = new UrsusArmoredCaster();

                public static class UrsusArmoredCaster
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 250;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.45;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 25;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.5;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 250;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 56;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 12;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 17;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 75;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 4;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 0;
                }

                public static class InfectedPatrolCaptain
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 165;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.55;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 7.5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.2;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 165;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 40;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 1;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 16.5f;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 50;

                    @Config.LangKey("config.arkworld.leaderAttackDamage")
                    public float leaderAttackDamage = 3.5f;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 0;
                }
            }

            public static class Split
            {
                @Config.LangKey("entity.arkworld.mutantRockSpider.name")
                public MutantRockSpider mutantRockSpider = new MutantRockSpider();

                @Config.LangKey("entity.arkworld.originiutantTumor.name")
                public OriginiutantTumor originiutantTumor = new OriginiutantTumor();

                @Config.LangKey("entity.arkworld.originiutantExcrescence.name")
                public OriginiutantExcrescence originiutantExcrescence = new OriginiutantExcrescence();

                @Config.LangKey("entity.arkworld.evolution.name")
                public Evolution evolution = new Evolution();

                public static class Evolution
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 2500;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 25;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.5;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 6;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 2500;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 100;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 32;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 35;

                    @Config.LangKey("config.arkworld.firstStageChargedStrikeDamage")
                    public float firstStageChargedStrikeDamage = 30;

                    @Config.LangKey("config.arkworld.firstStageChargedStrikeInterval")
                    public int firstStageChargedStrikeInterval = 640;

                    @Config.LangKey("config.arkworld.firstStageSplitOriginiutantExcrescenceInterval")
                    public int firstStageSplitOriginiutantExcrescenceInterval = 100;

                    @Config.LangKey("config.arkworld.firstStageSplitOriginiutantExcrescenceNumber")
                    public int firstStageSplitOriginiutantExcrescenceNumber = 1;

                    @Config.LangKey("config.arkworld.secondStageChargedStrikeDamage")
                    public float secondStageChargedStrikeDamage = 35;

                    @Config.LangKey("config.arkworld.secondStageChargedStrikeInterval")
                    public int secondStageChargedStrikeInterval = 300;

                    @Config.LangKey("config.arkworld.secondStageSplitOriginiutantTumorsInterval")
                    public int secondStageSplitOriginiutantTumorsInterval = 60;

                    @Config.LangKey("config.arkworld.secondStageSplitOriginiutantTumorsNumber")
                    public int secondStageSplitOriginiutantTumorsNumber = 2;

                    @Config.LangKey("config.arkworld.thirdStageChargedStrikeDamage")
                    public float thirdStageChargedStrikeDamage = 40;

                    @Config.LangKey("config.arkworld.thirdStageChargedStrikeInterval")
                    public int thirdStageChargedStrikeInterval = 160;

                    @Config.LangKey("config.arkworld.thirdStageSplitOriginiutantTumorsInterval")
                    public int thirdStageSplitOriginiutantTumorsInterval = 40;

                    @Config.LangKey("config.arkworld.thirdStageSplitOriginiutantTumorsNumber")
                    public int thirdStageSplitOriginiutantTumorsNumber = 3;

                    @Config.LangKey("config.arkworld.alarmTime")
                    public int alarmTime = 140;
                }

                public static class OriginiutantExcrescence
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 50;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.75;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 0;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 0;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 50;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 40;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 1;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 7.5f;
                }

                public static class OriginiutantTumor
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 75;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.75;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 0;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 0;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 75;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 40;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 1;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 10;
                }

                public static class MutantRockSpider
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 125;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.55;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 10;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 125;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 30;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 1;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 18;

                    @Config.LangKey("config.arkworld.deadSplitOriginiutantExcrescence")
                    public int deadSplitOriginiutantExcrescence = 3;

                    @Config.LangKey("config.arkworld.alphaMaxHealth")
                    public float alphaMaxHealth = 75;

                    @Config.LangKey("config.arkworld.alphaAttackDamage")
                    public float alphaAttackDamage = 2.5f;
                }
            }

            public static class Kazdel
            {
                @Config.LangKey("entity.arkworld.sarkazSentinel.name")
                public SarkazSentinel sarkazSentinel = new SarkazSentinel();

                @Config.LangKey("entity.arkworld.sarkazBladeweaver.name")
                public SarkazBladeweaver sarkazBladeweaver = new SarkazBladeweaver();

                @Config.LangKey("entity.arkworld.sarkazGrudgebearer.name")
                public SarkazGrudgebearer sarkazGrudgebearer = new SarkazGrudgebearer();

                public static class SarkazSentinel
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 200;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.4;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.3;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 200;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 20;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 32;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 1;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 50;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 3;
                }

                public static class SarkazBladeweaver
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 325;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.4;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 10;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.5;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 2;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 325;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 50;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 10;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 150;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 2.5f;
                }

                public static class SarkazGrudgebearer
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 750;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.4;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 12.5;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.5;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 3;

                    @Config.LangKey("config.arkworld.experienceValue")
                    public int experienceValue = 750;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 100;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 12;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 17.5f;

                    @Config.LangKey("config.arkworld.leaderMaxHealth")
                    public float leaderMaxHealth = 350;

                    @Config.LangKey("config.arkworld.leaderDefensivePower")
                    public float leaderDefensivePower = 2.5f;
                }
            }

        }

        public static class Animal
        {
            @Config.LangKey("entity.arkworld.bullfrog.name")
            public Bullfrog bullfrog = new Bullfrog();

            @Config.LangKey("entity.arkworld.cheetah.name")
            public Cheetah cheetah = new Cheetah();

            @Config.LangKey("entity.arkworld.elk.name")
            public Elk elk = new Elk();

            @Config.LangKey("entity.arkworld.frog.name")
            public Frog frog = new Frog();

            @Config.LangKey("entity.arkworld.hedgehog.name")
            public Hedgehog hedgehog = new Hedgehog();

            @Config.LangKey("entity.arkworld.hyena.name")
            public Hyena hyena = new Hyena();

            @Config.LangKey("entity.arkworld.lion.name")
            public Lion lion = new Lion();

            @Config.LangKey("entity.arkworld.owl.name")
            public Owl owl = new Owl();

            @Config.LangKey("entity.arkworld.raccoon.name")
            public Raccoon raccoon = new Raccoon();

            @Config.LangKey("entity.arkworld.rhino.name")
            public Rhino rhino = new Rhino();

            @Config.LangKey("entity.arkworld.skunk.name")
            public Skunk skunk = new Skunk();

            @Config.LangKey("entity.arkworld.squirrel.name")
            public Squirrel squirrel = new Squirrel();

            @Config.LangKey("entity.arkworld.tibetanAntelope.name")
            public TibetanAntelope tibetanAntelope = new TibetanAntelope();

            public static class TibetanAntelope
            {
                @Config.LangKey("config.arkworld.maxHealth")
                public double maxHealth = 14;

                @Config.LangKey("config.arkworld.movementSpeed")
                public double moveSpeed = 0.5;
            }

            public static class Squirrel
            {
                @Config.LangKey("config.arkworld.maxHealth")
                public double maxHealth = 4;

                @Config.LangKey("config.arkworld.movementSpeed")
                public double moveSpeed = 0.6;
            }

            public static class Skunk
            {
                @Config.LangKey("config.arkworld.maxHealth")
                public double maxHealth = 6;

                @Config.LangKey("config.arkworld.movementSpeed")
                public double moveSpeed = 0.6;
            }

            public static class Rhino
            {
                @Config.LangKey("config.arkworld.maxHealth")
                public double maxHealth = 40;

                @Config.LangKey("config.arkworld.movementSpeed")
                public double moveSpeed = 0.5;

                @Config.LangKey("config.arkworld.attackInterval")
                public double attackInterval = 60;

                @Config.LangKey("config.arkworld.attackDamage")
                public float attackDamage = 12;

                @Config.LangKey("config.arkworld.attackRange")
                public float attackRange = 4;
            }

            public static class Raccoon
            {
                @Config.LangKey("config.arkworld.maxHealth")
                public double maxHealth = 6;

                @Config.LangKey("config.arkworld.movementSpeed")
                public double moveSpeed = 0.6;
            }

            public static class Owl
            {
                @Config.LangKey("config.arkworld.maxHealth")
                public double maxHealth = 8;

                @Config.LangKey("config.arkworld.movementSpeed")
                public double moveSpeed = 0.6;
            }

            public static class Lion
            {
                @Config.LangKey("config.arkworld.maxHealth")
                public double maxHealth = 20;

                @Config.LangKey("config.arkworld.movementSpeed")
                public double moveSpeed = 0.5;

                @Config.LangKey("config.arkworld.attackInterval")
                public double attackInterval = 60;

                @Config.LangKey("config.arkworld.attackDamage")
                public float attackDamage = 12;

                @Config.LangKey("config.arkworld.attackRange")
                public float attackRange = 3;
            }

            public static class Hyena
            {
                @Config.LangKey("config.arkworld.maxHealth")
                public double maxHealth = 10;

                @Config.LangKey("config.arkworld.movementSpeed")
                public double moveSpeed = 0.5;

                @Config.LangKey("config.arkworld.attackInterval")
                public double attackInterval = 60;

                @Config.LangKey("config.arkworld.attackDamage")
                public float attackDamage = 6;

                @Config.LangKey("config.arkworld.attackRange")
                public float attackRange = 2;
            }

            public static class Hedgehog
            {
                @Config.LangKey("config.arkworld.maxHealth")
                public double maxHealth = 8;

                @Config.LangKey("config.arkworld.movementSpeed")
                public double moveSpeed = 0.6;
            }

            public static class Frog
            {
                @Config.LangKey("config.arkworld.maxHealth")
                public double maxHealth = 4;

                @Config.LangKey("config.arkworld.movementSpeed")
                public double moveSpeed = 0.2;
            }

            public static class Elk
            {
                @Config.LangKey("config.arkworld.maxHealth")
                public double maxHealth = 14;

                @Config.LangKey("config.arkworld.movementSpeed")
                public double moveSpeed = 0.4;
            }

            public static class Bullfrog
            {
                @Config.LangKey("config.arkworld.maxHealth")
                public double maxHealth = 6;

                @Config.LangKey("config.arkworld.movementSpeed")
                public double moveSpeed = 0.4;
            }

            public static class Cheetah
            {
                @Config.LangKey("config.arkworld.maxHealth")
                public double maxHealth = 16;

                @Config.LangKey("config.arkworld.movementSpeed")
                public double moveSpeed = 0.6;

                @Config.LangKey("config.arkworld.attackInterval")
                public double attackInterval = 60;

                @Config.LangKey("config.arkworld.attackDamage")
                public float attackDamage = 9;

                @Config.LangKey("config.arkworld.attackRange")
                public float attackRange = 2;
            }
        }

        public static class Operator
        {
            @Config.LangKey("config.arkworld.caster")
            public Caster caster = new Caster();

            @Config.LangKey("config.arkworld.guard")
            public Guard guard = new Guard();

            @Config.LangKey("config.arkworld.medic")
            public Medic medic = new Medic();

            @Config.LangKey("config.arkworld.sniper")
            public Sniper sniper = new Sniper();

            public static class Sniper
            {
                @Config.LangKey("entity.arkworld.ow.name")
                public OW ow = new OW();

                public static class OW
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 41;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.5;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 3.4;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.1;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 0;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 56;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 16;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 19.85f;

                    @Config.LangKey("config.arkworld.idleSound")
                    public boolean idleSound = true;

                    @Config.LangKey("config.arkworld.interactiveSound")
                    public boolean interactiveSound = true;

                    @Config.LangKey("config.arkworld.fightSound")
                    public boolean fightSound = true;

                    @Config.LangKey("config.arkworld.mineDestroyTerrain")
                    public boolean mineDestroyTerrain = false;

                    @Config.LangKey("config.arkworld.d12DestroyTerrain")
                    public boolean d12DestroyTerrain = false;
                }
            }

            public static class Medic
            {
                @Config.LangKey("entity.arkworld.purestream.name")
                public Purestream purestream = new Purestream();

                public static class Purestream
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 37.4;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.5;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 2.55;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.1;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 0;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 57;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 16;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 7.95f;

                    @Config.LangKey("config.arkworld.idleSound")
                    public boolean idleSound = true;

                    @Config.LangKey("config.arkworld.interactiveSound")
                    public boolean interactiveSound = true;

                    @Config.LangKey("config.arkworld.fightSound")
                    public boolean fightSound = true;
                }
            }

            public static class Guard
            {
                @Config.LangKey("entity.arkworld.surtr.name")
                public Surtr surtr = new Surtr();

                public static class Surtr
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 66.5;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.5;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 9.3;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.1;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 1;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 34;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 2;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 14.4f;

                    @Config.LangKey("config.arkworld.idleSound")
                    public boolean idleSound = true;

                    @Config.LangKey("config.arkworld.interactiveSound")
                    public boolean interactiveSound = true;

                    @Config.LangKey("config.arkworld.fightSound")
                    public boolean fightSound = true;
                }
            }

            public static class Caster
            {
                @Config.LangKey("entity.arkworld.amiya.name")
                public Amiya amiya = new Amiya();

                public static class Amiya
                {
                    @Config.LangKey("config.arkworld.maxHealth")
                    public double maxHealth = 35;

                    @Config.LangKey("config.arkworld.movementSpeed")
                    public double movementSpeed = 0.5;

                    @Config.LangKey("config.arkworld.defensivePower")
                    public double defensivePower = 2.4;

                    @Config.LangKey("config.arkworld.spellResistance")
                    public double spellResistance = 0.1;

                    @Config.LangKey("config.arkworld.knockbackResistance")
                    public double knockbackResistance = 0;

                    @Config.LangKey("config.arkworld.attackInterval")
                    public int attackInterval = 32;

                    @Config.LangKey("config.arkworld.attackRange")
                    public float attackRange = 16;

                    @Config.LangKey("config.arkworld.attackDamage")
                    public float attackDamage = 13.8f;

                    @Config.LangKey("config.arkworld.idleSound")
                    public boolean idleSound = true;

                    @Config.LangKey("config.arkworld.interactiveSound")
                    public boolean interactiveSound = true;

                    @Config.LangKey("config.arkworld.fightSound")
                    public boolean fightSound = true;
                }
            }
        }
    }

    public static class Item
    {
        @Config.LangKey("config.arkworld.weapon")
        public Weapon weapon = new Weapon();

        public static class Weapon
        {
            @Config.LangKey("config.arkworld.melee")
            public Melee melee = new Melee();

            @Config.LangKey("config.arkworld.range")
            public Range range = new Range();

            @Config.LangKey("config.arkworld.rangeMelee")
            public RangeMelee rangeMelee = new RangeMelee();

            @Config.LangKey("config.arkworld.shield")
            public Shield shield = new Shield();

            @Config.LangKey("config.arkworld.ammo")
            public Ammo ammo = new Ammo();

            @Config.LangKey("config.arkworld.armor")
            public Armor armor = new Armor();

            public static class Melee
            {
                @Config.LangKey("item.arkworld.avengerSword.name")
                public AvengerSword avengerSword = new AvengerSword();

                @Config.LangKey("item.arkworld.guerrillaAxe.name")
                public GuerrillaAxe guerrillaAxe = new GuerrillaAxe();

                @Config.LangKey("item.arkworld.icecleaverSword.name")
                public IcecleaverSword icecleaverSword = new IcecleaverSword();

                @Config.LangKey("item.arkworld.magicSword.name")
                public MagicSword magicSword = new MagicSword();

                @Config.LangKey("item.arkworld.normalMachete.name")
                public NormalMachete normalMachete = new NormalMachete();

                @Config.LangKey("item.arkworld.smashingHammer.name")
                public SmashingHammer smashingHammer = new SmashingHammer();

                @Config.LangKey("item.arkworld.talulahSword.name")
                public TalulahSword talulahSword = new TalulahSword();

                @Config.LangKey("item.arkworld.ursusKnife.name")
                public UrsusKnife ursusKnife = new UrsusKnife();

                @Config.LangKey("item.arkworld.dualSword.name")
                public DualSword dualSword = new DualSword();

                @Config.LangKey("item.arkworld.pipe.name")
                public Pipe pipe = new Pipe();

                public static class Pipe
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 12.5f;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 85;
                }

                public static class AvengerSword
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 5;

                    @Config.LangKey("item.arkworld.info.surplusAttackDamage")
                    public float surplusAttackDamage = 15;

                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 4;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 125;
                }

                public static class GuerrillaAxe
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 6;

                    @Config.LangKey("item.arkworld.info.surplusAttackDamage")
                    public float surplusAttackDamage = 12;

                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 3;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 95;
                }

                public static class DualSword
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 5;

                    @Config.LangKey("item.arkworld.info.surplusAttackDamage")
                    public float surplusAttackDamage = 18;

                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 3;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 100;
                }

                public static class IcecleaverSword
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 12;

                    @Config.LangKey("item.arkworld.info.surplusAttackDamage")
                    public float surplusAttackDamage = 28;

                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 4;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 400;
                }

                public static class MagicSword
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 7;

                    @Config.LangKey("item.arkworld.info.surplusAttackDamage")
                    public float surplusAttackDamage = 13;

                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 2;

                    @Config.LangKey("item.arkworld.info.manaConsume")
                    public int mana = 2;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 162;
                }

                public static class NormalMachete
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 12;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 100;
                }

                public static class SmashingHammer
                {
                    @Config.LangKey("item.arkworld.info.baseSkillFlySpeed")
                    public double baseSkillFlySpeed = 1.1;

                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 20;

                    @Config.LangKey("item.arkworld.info.surplusAttackDamage")
                    public float surplusAttackDamage = 30;

                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 4;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 250;
                }

                public static class TalulahSword
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 15;

                    @Config.LangKey("item.arkworld.info.surplusAttackDamage")
                    public float surplusAttackDamage = 25;

                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 4;

                    @Config.LangKey("item.arkworld.info.manaConsume")
                    public int mana = 4;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 1250;

                    @Config.LangKey("item.arkworld.info.destroyTerrain")
                    public boolean destroyTerrain = false;

                    @Config.LangKey("config.arkworld.onlyMob")
                    public boolean onlyMob = true;
                }

                public static class UrsusKnife
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 16.5f;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 82;
                }
            }

            public static class Range
            {
                @Config.LangKey("item.arkworld.faustCrossbow.name")
                public FaustCrossbow faustCrossbow = new FaustCrossbow();

                @Config.LangKey("item.arkworld.normalCrossbow.name")
                public NormalCrossbow normalCrossbow = new NormalCrossbow();

                @Config.LangKey("item.arkworld.frostmourne.name")
                public Frostmourne frostmourne = new Frostmourne();

                @Config.LangKey("item.arkworld.healingStaff.name")
                public HealingStaff healingStaff = new HealingStaff();

                @Config.LangKey("item.arkworld.d12.name")
                public D12 d12 = new D12();

                public static class FaustCrossbow
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 7;

                    @Config.LangKey("item.arkworld.info.surplusAttackDamage")
                    public float surplusAttackDamage = 25;

                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 3;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 925;
                }

                public static class NormalCrossbow
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 1;

                    @Config.LangKey("item.arkworld.info.surplusAttackDamage")
                    public float surplusAttackDamage = 7;

                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 1;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 46;
                }

                public static class Frostmourne
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 7;

                    @Config.LangKey("item.arkworld.info.surplusAttackDamage")
                    public float surplusAttackDamage = 21;

                    @Config.LangKey("item.arkworld.info.manaConsume")
                    public int mana = 6;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 625;
                }

                public static class HealingStaff
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 8;

                    @Config.LangKey("item.arkworld.info.surplusAttackDamage")
                    public float surplusAttackDamage = 25;

                    @Config.LangKey("item.arkworld.info.manaConsume")
                    public int mana = 6;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 700;
                }

                public static class D12
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 8;

                    @Config.LangKey("item.arkworld.info.surplusAttackDamage")
                    public float surplusAttackDamage = 24;

                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 2;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 250;
                }
            }

            public static class RangeMelee
            {
                @Config.LangKey("item.arkworld.grenadeGun.name")
                public GrenadeGun grenadeGun = new GrenadeGun();

                @Config.LangKey("item.arkworld.patriotJavelin.name")
                public PatriotJavelin patriotJavelin = new PatriotJavelin();

                public static class GrenadeGun
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 20;

                    @Config.LangKey("item.arkworld.info.surplusAttackDamage")
                    public float surplusAttackDamage = 30;

                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 5;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 250;
                }

                public static class PatriotJavelin
                {
                    @Config.LangKey("item.arkworld.info.initAttackDamage")
                    public float initAttackDamage = 30;

                    @Config.LangKey("item.arkworld.info.surplusAttackDamage")
                    public float surplusAttackDamage = 80;

                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 5;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 1125;
                }
            }

            public static class Shield
            {
                @Config.LangKey("item.arkworld.guerrillaShield.name")
                public GuerrillaShield guerrillaShield = new GuerrillaShield();

                @Config.LangKey("item.arkworld.normalShield.name")
                public NormalShield normalShield = new NormalShield();

                @Config.LangKey("item.arkworld.patriotShield.name")
                public PatriotShield patriotShield = new PatriotShield();

                @Config.LangKey("item.arkworld.heavyShield.name")
                public HeavyShield heavyShield = new HeavyShield();

                public static class GuerrillaShield
                {
                    @Config.LangKey("item.arkworld.info.canTauntEntityLiving")
                    public boolean canTauntEntityLiving = true;

                    @Config.LangKey("item.arkworld.info.canTauntPlayer")
                    public boolean canTauntPlayer = true;

                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 2;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 750;
                }

                public static class NormalShield
                {
                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 1;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 200;
                }

                public static class HeavyShield
                {
                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 2;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 400;
                }

                public static class PatriotShield
                {
                    @Config.LangKey("item.arkworld.info.canTauntEntityLiving")
                    public boolean canTauntEntityLiving = true;

                    @Config.LangKey("item.arkworld.info.canTauntPlayer")
                    public boolean canTauntPlayer = true;

                    @Config.LangKey("item.arkworld.info.staminaConsume")
                    public int stamina = 3;

                    @Config.LangKey("item.arkworld.info.durabilityCoefficient")
                    public int durability = 2250;
                }
            }

            public static class Ammo
            {
                @Config.LangKey("config.arkworld.bomb")
                public Bomb bomb = new Bomb();

                @Config.LangKey("config.arkworld.arrow")
                public Arrow arrow = new Arrow();

                public static class Arrow
                {
                    @Config.LangKey("item.arkworld.faustCommonArrow.name")
                    public FaustCommonArrow faustCommonArrow = new FaustCommonArrow();

                    @Config.LangKey("item.arkworld.faustSpecialArrow.name")
                    public FaustSpecialArrow faustSpecialArrow = new FaustSpecialArrow();

                    @Config.LangKey("item.arkworld.normalArrow.name")
                    public NormalArrow normalArrow = new NormalArrow();

                    public static class NormalArrow
                    {
                        @Config.LangKey("config.arkworld.attackDamage")
                        public float attackDamage = 7;
                    }

                    public static class FaustCommonArrow
                    {
                        @Config.LangKey("config.arkworld.attackDamage")
                        public float attackDamage = 25;
                    }

                    public static class FaustSpecialArrow
                    {
                        @Config.LangKey("config.arkworld.attackDamage")
                        public float attackDamage = 50;
                    }
                }

                public static class Bomb
                {
                    @Config.LangKey("item.arkworld.grenade.name")
                    public Grenade grenade = new Grenade();

                    @Config.LangKey("item.arkworld.instantBomb.name")
                    public InstantBomb instantBomb = new InstantBomb();

                    @Config.LangKey("item.arkworld.fireBomb.name")
                    public FireBomb fireBomb = new FireBomb();

                    public static class Grenade
                    {
                        @Config.LangKey("item.arkworld.info.damage")
                        public float damage = 12f;

                        @Config.LangKey("item.arkworld.destroyTerrain")
                        public boolean destroyTerrain = false;
                    }

                    public static class InstantBomb
                    {
                        @Config.LangKey("item.arkworld.info.damage")
                        public float damage = 24f;

                        @Config.LangKey("item.arkworld.destroyTerrain")
                        public boolean destroyTerrain = false;

                        @Config.LangKey("item.arkworld.thrownVelocity")
                        public float thrownVelocity = 1.1f;
                    }

                    public static class FireBomb
                    {
                        @Config.LangKey("item.arkworld.info.damage")
                        public float damage = 2f;

                        @Config.LangKey("item.arkworld.destroyTerrain")
                        public boolean destroyTerrain = false;

                        @Config.LangKey("item.arkworld.thrownVelocity")
                        public float thrownVelocity = 1.1f;
                    }
                }
            }

            public static class Armor
            {
                @Config.LangKey("config.arkworld.basinSeaReaperArmor")
                public BasinSeaReaperArmor basinSeaReaperArmor = new BasinSeaReaperArmor();

                @Config.LangKey("config.arkworld.deepSeaSliderArmor")
                public DeepSeaSliderArmor deepSeaSliderArmor = new DeepSeaSliderArmor();

                @Config.LangKey("config.arkworld.defenseCrusherArmor")
                public DefenseCrusherArmor defenseCrusherArmor = new DefenseCrusherArmor();

                @Config.LangKey("config.arkworld.frostnovaArmor")
                public FrostnovaArmor frostnovaArmor = new FrostnovaArmor();

                @Config.LangKey("config.arkworld.grindstoneArmor")
                public GrindstoneArmor grindstoneArmor = new GrindstoneArmor();

                @Config.LangKey("config.arkworld.guerrillaSiegebreakerArmor")
                public GuerrillaSiegebreakerArmor guerrillaSiegebreakerArmor = new GuerrillaSiegebreakerArmor();

                @Config.LangKey("config.arkworld.manganeseArmor")
                public ManganeseArmor manganeseArmor = new ManganeseArmor();

                @Config.LangKey("config.arkworld.orirockArmor")
                public OrirockArmor orirockArmor = new OrirockArmor();

                @Config.LangKey("config.arkworld.orironArmor")
                public OrironArmor orironArmor = new OrironArmor();

                @Config.LangKey("config.arkworld.patriotArmor")
                public PatriotArmor patriotArmor = new PatriotArmor();

                @Config.LangKey("config.arkworld.pocketSeaCrawlerArmor")
                public PocketSeaCrawlerArmor pocketSeaCrawlerArmor = new PocketSeaCrawlerArmor();

                @Config.LangKey("config.arkworld.regicideArmor")
                public RegicideArmor regicideArmor = new RegicideArmor();

                @Config.LangKey("config.arkworld.seniorCasterArmor")
                public SeniorCasterArmor seniorCasterArmor = new SeniorCasterArmor();

                @Config.LangKey("config.arkworld.skullshattererArmor")
                public SkullshattererArmor skullshattererArmor = new SkullshattererArmor();

                @Config.LangKey("config.arkworld.smilingCorpseMountainArmor")
                public SmilingCorpseMountainArmor smilingCorpseMountainArmor = new SmilingCorpseMountainArmor();

                @Config.LangKey("config.arkworld.soldierArmor")
                public SoldierArmor soldierArmor = new SoldierArmor();

                @Config.LangKey("config.arkworld.surtrArmor")
                public SurtrArmor surtrArmor = new SurtrArmor();

                @Config.LangKey("config.arkworld.technicalScoutArmor")
                public TechnicalScoutArmor technicalScoutArmor = new TechnicalScoutArmor();

                @Config.LangKey("config.arkworld.wraithArmor")
                public WraithArmor wraithArmor = new WraithArmor();

                @Config.LangKey("config.arkworld.yetiOperativeArmor")
                public YetiOperativeArmor yetiOperativeArmor = new YetiOperativeArmor();

                public static class YetiOperativeArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 200;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 4;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 2;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0.01f;

                    @Config.LangKey("config.arkworld.armorChest")
                    public int armorChest = 12;

                    @Config.LangKey("config.arkworld.defensivePowerChest")
                    public float defensivePowerChest = 6;

                    @Config.LangKey("config.arkworld.spellResistanceChest")
                    public float spellResistanceChest = 0.03f;

                    @Config.LangKey("config.arkworld.armorLegs")
                    public int armorLegs = 8;

                    @Config.LangKey("config.arkworld.defensivePowerLegs")
                    public float defensivePowerLegs = 4;

                    @Config.LangKey("config.arkworld.spellResistanceLegs")
                    public float spellResistanceLegs = 0.02f;

                    @Config.LangKey("config.arkworld.armorFeet")
                    public int armorFeet = 4;

                    @Config.LangKey("config.arkworld.defensivePowerFeet")
                    public float defensivePowerFeet = 2;

                    @Config.LangKey("config.arkworld.spellResistanceFeet")
                    public float spellResistanceFeet = 0.01f;
                }

                public static class WraithArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 300;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 2;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 1;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0.01f;
                }

                public static class TechnicalScoutArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 150;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 2;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 1;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0;
                }

                public static class SurtrArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 800;

                    @Config.LangKey("config.arkworld.armorChest")
                    public int armorChest = 20;

                    @Config.LangKey("config.arkworld.defensivePowerChest")
                    public float defensivePowerChest = 20;

                    @Config.LangKey("config.arkworld.spellResistanceChest")
                    public float spellResistanceChest = 0.2f;
                }

                public static class SoldierArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 200;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 5;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 2;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0;

                    @Config.LangKey("config.arkworld.armorChest")
                    public int armorChest = 15;

                    @Config.LangKey("config.arkworld.defensivePowerChest")
                    public float defensivePowerChest = 7;

                    @Config.LangKey("config.arkworld.spellResistanceChest")
                    public float spellResistanceChest = 0;

                    @Config.LangKey("config.arkworld.armorLegs")
                    public int armorLegs = 10;

                    @Config.LangKey("config.arkworld.defensivePowerLegs")
                    public float defensivePowerLegs = 5;

                    @Config.LangKey("config.arkworld.spellResistanceLegs")
                    public float spellResistanceLegs = 0;

                    @Config.LangKey("config.arkworld.armorFeet")
                    public int armorFeet = 5;

                    @Config.LangKey("config.arkworld.defensivePowerFeet")
                    public float defensivePowerFeet = 2;

                    @Config.LangKey("config.arkworld.spellResistanceFeet")
                    public float spellResistanceFeet = 0;
                }

                public static class SmilingCorpseMountainArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 250;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 8;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 4;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0;
                }

                public static class SkullshattererArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 350;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 4;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 2;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0;
                }

                public static class SeniorCasterArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 300;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 6;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 3;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0.1f;

                    @Config.LangKey("config.arkworld.armorChest")
                    public int armorChest = 10;

                    @Config.LangKey("config.arkworld.defensivePowerChest")
                    public float defensivePowerChest = 5;

                    @Config.LangKey("config.arkworld.spellResistanceChest")
                    public float spellResistanceChest = 0.2f;
                }

                public static class RegicideArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 200;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 2;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 2;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0;
                }

                public static class BasinSeaReaperArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 350;

                    @Config.LangKey("config.arkworld.armorChest")
                    public int armorChest = 10;

                    @Config.LangKey("config.arkworld.defensivePowerChest")
                    public float defensivePowerChest = 4;

                    @Config.LangKey("config.arkworld.spellResistanceChest")
                    public float spellResistanceChest = 0;
                }

                public static class PocketSeaCrawlerArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 400;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 6;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 6;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0;
                }

                public static class DeepSeaSliderArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 300;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 3;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 3;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0;
                }

                public static class DefenseCrusherArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 600;

                    @Config.LangKey("config.arkworld.armorChest")
                    public int armorChest = 20;

                    @Config.LangKey("config.arkworld.defensivePowerChest")
                    public float defensivePowerChest = 15;

                    @Config.LangKey("config.arkworld.spellResistanceChest")
                    public float spellResistanceChest = 0;

                    @Config.LangKey("config.arkworld.armorLegs")
                    public int armorLegs = 15;

                    @Config.LangKey("config.arkworld.defensivePowerLegs")
                    public float defensivePowerLegs = 10;

                    @Config.LangKey("config.arkworld.spellResistanceLegs")
                    public float spellResistanceLegs = 0;

                    @Config.LangKey("config.arkworld.armorFeet")
                    public int armorFeet = 10;

                    @Config.LangKey("config.arkworld.defensivePowerFeet")
                    public float defensivePowerFeet = 5;

                    @Config.LangKey("config.arkworld.spellResistanceFeet")
                    public float spellResistanceFeet = 0;
                }

                public static class FrostnovaArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 150;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 2;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 0;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0.01f;

                    @Config.LangKey("config.arkworld.armorChest")
                    public int armorChest = 6;

                    @Config.LangKey("config.arkworld.defensivePowerChest")
                    public float defensivePowerChest = 4;

                    @Config.LangKey("config.arkworld.spellResistanceChest")
                    public float spellResistanceChest = 0.2f;
                }

                public static class GrindstoneArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 300;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 2;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 1;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0;

                    @Config.LangKey("config.arkworld.armorChest")
                    public int armorChest = 6;

                    @Config.LangKey("config.arkworld.defensivePowerChest")
                    public float defensivePowerChest = 3;

                    @Config.LangKey("config.arkworld.spellResistanceChest")
                    public float spellResistanceChest = 0;

                    @Config.LangKey("config.arkworld.armorLegs")
                    public int armorLegs = 4;

                    @Config.LangKey("config.arkworld.defensivePowerLegs")
                    public float defensivePowerLegs = 2;

                    @Config.LangKey("config.arkworld.spellResistanceLegs")
                    public float spellResistanceLegs = 0;

                    @Config.LangKey("config.arkworld.armorFeet")
                    public int armorFeet = 2;

                    @Config.LangKey("config.arkworld.defensivePowerFeet")
                    public float defensivePowerFeet = 1;

                    @Config.LangKey("config.arkworld.spellResistanceFeet")
                    public float spellResistanceFeet = 0;
                }

                public static class GuerrillaSiegebreakerArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 200;

                    @Config.LangKey("config.arkworld.armorChest")
                    public int armorChest = 4;

                    @Config.LangKey("config.arkworld.defensivePowerChest")
                    public float defensivePowerChest = 6;

                    @Config.LangKey("config.arkworld.spellResistanceChest")
                    public float spellResistanceChest = 0;
                }

                public static class ManganeseArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 250;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 1;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 0;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0.02f;

                    @Config.LangKey("config.arkworld.armorChest")
                    public int armorChest = 4;

                    @Config.LangKey("config.arkworld.defensivePowerChest")
                    public float defensivePowerChest = 2;

                    @Config.LangKey("config.arkworld.spellResistanceChest")
                    public float spellResistanceChest = 0.06f;

                    @Config.LangKey("config.arkworld.armorLegs")
                    public int armorLegs = 2;

                    @Config.LangKey("config.arkworld.defensivePowerLegs")
                    public float defensivePowerLegs = 1;

                    @Config.LangKey("config.arkworld.spellResistanceLegs")
                    public float spellResistanceLegs = 0.04f;

                    @Config.LangKey("config.arkworld.armorFeet")
                    public int armorFeet = 1;

                    @Config.LangKey("config.arkworld.defensivePowerFeet")
                    public float defensivePowerFeet = 0;

                    @Config.LangKey("config.arkworld.spellResistanceFeet")
                    public float spellResistanceFeet = 0.02f;
                }

                public static class OrirockArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 300;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 3;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 1;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0.01f;

                    @Config.LangKey("config.arkworld.armorChest")
                    public int armorChest = 6;

                    @Config.LangKey("config.arkworld.defensivePowerChest")
                    public float defensivePowerChest = 3;

                    @Config.LangKey("config.arkworld.spellResistanceChest")
                    public float spellResistanceChest = 0.03f;

                    @Config.LangKey("config.arkworld.armorLegs")
                    public int armorLegs = 4;

                    @Config.LangKey("config.arkworld.defensivePowerLegs")
                    public float defensivePowerLegs = 2;

                    @Config.LangKey("config.arkworld.spellResistanceLegs")
                    public float spellResistanceLegs = 0.02f;

                    @Config.LangKey("config.arkworld.armorFeet")
                    public int armorFeet = 3;

                    @Config.LangKey("config.arkworld.defensivePowerFeet")
                    public float defensivePowerFeet = 1;

                    @Config.LangKey("config.arkworld.spellResistanceFeet")
                    public float spellResistanceFeet = 0.01f;
                }

                public static class OrironArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 500;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 6;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 3;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0;

                    @Config.LangKey("config.arkworld.armorChest")
                    public int armorChest = 10;

                    @Config.LangKey("config.arkworld.defensivePowerChest")
                    public float defensivePowerChest = 5;

                    @Config.LangKey("config.arkworld.spellResistanceChest")
                    public float spellResistanceChest = 0;

                    @Config.LangKey("config.arkworld.armorLegs")
                    public int armorLegs = 8;

                    @Config.LangKey("config.arkworld.defensivePowerLegs")
                    public float defensivePowerLegs = 4;

                    @Config.LangKey("config.arkworld.spellResistanceLegs")
                    public float spellResistanceLegs = 0;

                    @Config.LangKey("config.arkworld.armorFeet")
                    public int armorFeet = 6;

                    @Config.LangKey("config.arkworld.defensivePowerFeet")
                    public float defensivePowerFeet = 3;

                    @Config.LangKey("config.arkworld.spellResistanceFeet")
                    public float spellResistanceFeet = 0;
                }

                public static class PatriotArmor
                {
                    @Config.LangKey("item.arkworld.info.durability")
                    public int durability = 1000;

                    @Config.LangKey("config.arkworld.armorHead")
                    public int armorHead = 10;

                    @Config.LangKey("config.arkworld.defensivePowerHead")
                    public float defensivePowerHead = 10;

                    @Config.LangKey("config.arkworld.spellResistanceHead")
                    public float spellResistanceHead = 0.1f;

                    @Config.LangKey("config.arkworld.armorChest")
                    public int armorChest = 20;

                    @Config.LangKey("config.arkworld.defensivePowerChest")
                    public float defensivePowerChest = 20;

                    @Config.LangKey("config.arkworld.spellResistanceChest")
                    public float spellResistanceChest = 0.3f;

                    @Config.LangKey("config.arkworld.armorLegs")
                    public int armorLegs = 15;

                    @Config.LangKey("config.arkworld.defensivePowerLegs")
                    public float defensivePowerLegs = 15;

                    @Config.LangKey("config.arkworld.spellResistanceLegs")
                    public float spellResistanceLegs = 0.2f;

                    @Config.LangKey("config.arkworld.armorFeet")
                    public int armorFeet = 10;

                    @Config.LangKey("config.arkworld.defensivePowerFeet")
                    public float defensivePowerFeet = 10;

                    @Config.LangKey("config.arkworld.spellResistanceFeet")
                    public float spellResistanceFeet = 0.1f;
                }
            }
        }
    }

    public static class Block
    {
        @Config.LangKey("config.arkworld.infrastructure")
        public Infrastructure infrastructure = new Infrastructure();

        public static class Infrastructure
        {
            @Config.LangKey("tile.arkworld.controlCenter.name")
            public ControlCenter controlCenter = new ControlCenter();

            @Config.LangKey("tile.arkworld.dormitory.name")
            public Dormitory dormitory = new Dormitory();

            @Config.LangKey("tile.arkworld.manufacturingStation.name")
            public ManufacturingStation manufacturingStation = new ManufacturingStation();

            @Config.LangKey("tile.arkworld.office.name")
            public Office office = new Office();

            @Config.LangKey("tile.arkworld.powerStation.name")
            public PowerStation powerStation = new PowerStation();

            @Config.LangKey("tile.arkworld.processingStation.name")
            public ProcessingStation processingStation = new ProcessingStation();

            @Config.LangKey("tile.arkworld.tradingStation.name")
            public TradingStation tradingStation = new TradingStation();

            @Config.LangKey("tile.arkworld.trainingRoom.name")
            public TrainingRoom trainingRoom = new TrainingRoom();

            public static class ControlCenter
            {
                @Config.LangKey("config.arkworld.operatorRange")
                public int operatorRange = 4;

                @Config.LangKey("config.arkworld.centerRange")
                public int centerRange = 32;
            }

            public static class Dormitory
            {
                @Config.LangKey("config.arkworld.operatorRange")
                public int operatorRange = 4;
            }

            public static class ManufacturingStation
            {
                @Config.LangKey("config.arkworld.operatorRange")
                public int operatorRange = 3;
            }

            public static class Office
            {
                @Config.LangKey("config.arkworld.operatorRange")
                public int operatorRange = 2;
            }

            public static class PowerStation
            {
                @Config.LangKey("config.arkworld.operatorRange")
                public int operatorRange = 2;
            }

            public static class ProcessingStation
            {
                @Config.LangKey("config.arkworld.operatorRange")
                public int operatorRange = 2;
            }

            public static class TradingStation
            {
                @Config.LangKey("config.arkworld.operatorRange")
                public int operatorRange = 3;
            }

            public static class TrainingRoom
            {
                @Config.LangKey("config.arkworld.operatorRange")
                public int operatorRange = 2;
            }
        }
    }

    public static class World
    {
        @Config.LangKey("config.arkworld.usePortal")
        public boolean usePortal = true;

        @Config.LangKey("config.arkworld.graySky")
        public boolean graySky = true;

        @Config.LangKey("config.arkworld.fog")
        public boolean fog = true;
    }
}
