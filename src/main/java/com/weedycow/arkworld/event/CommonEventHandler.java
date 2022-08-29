package com.weedycow.arkworld.event;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.doll.BlockDoll;
import com.weedycow.arkworld.block.machine.infrastructure.BlockMachine;
import com.weedycow.arkworld.capability.CapabilityAttribute;
import com.weedycow.arkworld.capability.CapabilityItem;
import com.weedycow.arkworld.capability.CapabilityState;
import com.weedycow.arkworld.entity.animal.ArkAnimal;
import com.weedycow.arkworld.entity.enemy.ArkMob;
import com.weedycow.arkworld.entity.enemy.kazdel.normal.SarkazSentinel;
import com.weedycow.arkworld.entity.enemy.split.boss.Evolution;
import com.weedycow.arkworld.entity.enemy.split.normal.MutantRockSpider;
import com.weedycow.arkworld.entity.enemy.split.normal.OriginiutantTumor;
import com.weedycow.arkworld.entity.enemy.union.boss.*;
import com.weedycow.arkworld.entity.enemy.union.normal.SpiderGlacialOriginiumSlug;
import com.weedycow.arkworld.entity.enemy.union.normal.SpiderOriginiumSlug;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.entity.operator.caster.Amiya;
import com.weedycow.arkworld.entity.operator.guard.Surtr;
import com.weedycow.arkworld.entity.operator.sniper.OW;
import com.weedycow.arkworld.item.armor.*;
import com.weedycow.arkworld.item.block.nature.tree.huge.ItemHugeLog;
import com.weedycow.arkworld.item.block.nature.tree.huge.ItemHugeSapling;
import com.weedycow.arkworld.item.block.nature.tree.huge.ItemHugeWood;
import com.weedycow.arkworld.item.block.nature.tree.willow.ItemWillowLog;
import com.weedycow.arkworld.item.block.nature.tree.willow.ItemWillowSapling;
import com.weedycow.arkworld.item.block.nature.tree.willow.ItemWillowWood;
import com.weedycow.arkworld.item.normal.Oricoal;
import com.weedycow.arkworld.item.operator.*;
import com.weedycow.arkworld.item.tool.MeleeWeapon;
import com.weedycow.arkworld.item.tool.ShieldWeapon;
import com.weedycow.arkworld.item.tool.tool.IncandescentPickaxe;
import com.weedycow.arkworld.item.tool.weapon.SmashingHammer;
import com.weedycow.arkworld.world.arkworld.world.ArkTeleporter;
import com.weedycow.arkworld.world.data.MachineWorldSavedData;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.weedycow.weedylib.util.ServerUtil.getServerIpAddress;

@EventBusSubscriber
public class CommonEventHandler
{
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void JustNewdes(FMLNetworkEvent.ClientConnectedToServerEvent event) throws IOException
    {
        if(!getServerIpAddress().equals("null") && !getServerIpAddress().equals("mc.newdes.games")
                && !getServerIpAddress().equals("newdes.games:36888") && !getServerIpAddress().equals(getNewdesIp()))
        {
            if(Arkworld.NEWDES)
            {
                int FuckYou = 1 / 0;
                System.out.println(FuckYou);
            }
        }
    }

    private static String getNewdesIp() throws IOException
    {
        InputStream fin = getNetworkFileInputStream("https://newdes-1304909052.cos.ap-nanjing.myqcloud.com/res/.minecraft/ip.txt");
        if(fin!=null)
        {
            InputStreamReader reader = new InputStreamReader(fin);
            BufferedReader buffReader = new BufferedReader(reader);
            return buffReader.readLine();
        }
        else return null;
    }

    private static InputStream getNetworkFileInputStream(String path)
    {
        URL url;
        try
        {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(3*1000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            return conn.getInputStream();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onPlayerFirstText(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event)
    {
        EntityPlayer entity= event.player;

        TextComponentString s = new TextComponentString(I18n.format("item.arkworld.info.welcome"));

        entity.sendMessage(s);
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(Arkworld.MODID))
        {
            ConfigManager.sync(Arkworld.MODID, Config.Type.INSTANCE);
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
    {
        ArkArmor.onWear(event);

        ArkNormalArmor.onWear(event);

        PocketSeaCrawlerArmor.onUpdating(event);

        GuerrillaSiegebreakerArmor.onUpdating(event);

        RegicideArmor.onUpdating(event);

        SmilingCorpseMountainArmor.onUpdating(event);

        TechnicalScoutArmor.onUpdating(event);

        WraithArmor.onUpdating(event);
    }

    @SubscribeEvent
    public static void onPlayerUpdate(TickEvent.PlayerTickEvent event)
    {
        CapabilityItem.Process.onUpdate(event);

        ArkTeleporter.checkPorter(event);
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event)
    {
        CapabilityItem.Process.onDeath(event);

        Amiya.onDeath(event);

        SpiderOriginiumSlug.onDeath(event);

        SpiderGlacialOriginiumSlug.onDeath(event);

        MutantRockSpider.onDeath(event);

        BlackSnake.onDeath(event);

        Frostnova.onDeath(event);

        Patriot.onDeath(event);

        Amiya.targetDeath(event);

        Surtr.onDeath(event);

        OW.onDeath(event);
    }

    @SubscribeEvent
    public static void onLivingHealing(LivingHealEvent event)
    {
        CapabilityState.Process.banHealing(event);

        CapabilityItem.Process.onHeal(event);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event)
    {
        MeleeWeapon.onVampire(event);

        CapabilityAttribute.Process.onAttribute(event);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
        Evolution.onEvolutionThird(event);

        SarkazSentinel.onActive(event);

        CapabilityState.Process.onCapAttack(event);

        MeleeWeapon.onHitEntity(event);

        CapabilityItem.Process.onHurt(event);

        BasinSeaReaperArmor.onHurt(event);

        DeepSeaSliderArmor.onHurt(event);

        DefenseCrusherArmor.onHurt(event);

        FrostnovaArmor.onHurt(event);

        SkullshattererArmor.onHurt(event);

        SurtrArmor.onHurt(event);

        YetiOperativeArmor.onHurt(event);

        Amiya.targetHurt(event);

        Operator.onAttackRecovery(event);

        Surtr.onHurt(event);

        Frostnova.onTrans(event);

        BlackSnake.onTrans(event);

        Patriot.onTrans(event);

        Faust.onTrans(event);

        SmashingHammer.onDrops(event);

        MutantRockSpider.onAttack(event);

        SpiderGlacialOriginiumSlug.onAttack(event);

        SpiderOriginiumSlug.onAttack(event);

        ShieldWeapon.onAttackShield(event);

        CapabilityState.Process.onCapAttack(event);
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event)
    {
        if(event.getSource().getDamageType().equals("player"))
            event.getEntityLiving().maxHurtResistantTime=2;
        else
            event.getEntityLiving().maxHurtResistantTime=20;

        Regicide.onAttacked(event);

        OriginiutantTumor.onMutatedTumorAttack(event);
    }

    @SubscribeEvent
    public static void onLivingDrop(LivingDropsEvent event)
    {
        ArkMob.onDrop(event);

        ArkAnimal.onDrop(event);
    }

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent event)
    {
        OperatorCard.recovery(event);

        AmiyaCard.omRightClick(event);

        SurtrCard.omRightClick(event);

        PurestreamCard.omRightClick(event);

        WCard.omRightClick(event);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onBgm(EntityJoinWorldEvent event)
    {
        BlackSnake.onBgm(event);

        Frostnova.onBgm(event);

        Patriot.onBgm(event);

        Faust.onBgm(event);

        Skullshatterer.onBgm(event);
    }

    @SubscribeEvent
    public static void getVanillaFurnaceFuelValue(FurnaceFuelBurnTimeEvent event)
    {
        if (event.getItemStack().getItem() instanceof Oricoal)
        {
            event.setBurnTime(1600);
        }
        if (event.getItemStack().getItem() instanceof ItemWillowWood || event.getItemStack().getItem() instanceof ItemHugeWood
                || event.getItemStack().getItem() instanceof ItemWillowLog || event.getItemStack().getItem() instanceof ItemHugeLog)
        {
            event.setBurnTime(300);
        }
        if(event.getItemStack().getItem() instanceof ItemWillowSapling || event.getItemStack().getItem() instanceof ItemHugeSapling)
        {
            event.setBurnTime(100);
        }
    }

    @SubscribeEvent
    public static void blockPlace(BlockEvent.PlaceEvent event)
    {
        Block block = event.getPlacedBlock().getBlock();
        BlockPos pos = event.getPos();
        World world = event.getWorld();

        if(block instanceof BlockMachine)
        {
            MachineWorldSavedData.get(world).pos.add(pos);
            MachineWorldSavedData.get(world).markDirty();
        }
    }

    @SubscribeEvent
    public static void blockBreak(BlockEvent.BreakEvent event)
    {
        Block block = event.getState().getBlock();
        BlockPos pos = event.getPos();
        World world = event.getWorld();

        if(block instanceof BlockMachine)
        {
            MachineWorldSavedData.get(world).pos.remove(pos);
            MachineWorldSavedData.get(world).markDirty();
        }

        IncandescentPickaxe.onUse(event);
    }

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickEmpty event)
    {
        BlockDoll.onRightClick(event);
    }
}
