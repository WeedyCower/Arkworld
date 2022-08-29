package com.weedycow.arkworld.event;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.*;
import com.weedycow.arkworld.entity.animal.ArkAnimal;
import com.weedycow.arkworld.entity.weapon.ProjectileA;
import com.weedycow.arkworld.entity.weapon.ProjectileT;
import com.weedycow.arkworld.gui.*;
import com.weedycow.arkworld.item.block.machine.ItemMachine;
import com.weedycow.arkworld.item.normal.ArmsMod;
import com.weedycow.arkworld.item.normal.ExpCard;
import com.weedycow.arkworld.item.operator.OperatorCard;
import com.weedycow.arkworld.item.tool.MeleeWeapon;
import com.weedycow.arkworld.item.tool.RangeWeapon;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.registry.ItemRegistry;
import com.weedycow.weedylib.entity.WLM;
import com.weedycow.weedylib.util.EntityUtil;
import com.weedycow.weedylib.util.ServerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class CapabilityEventHandler
{
    @SubscribeEvent
    public static void onCapability(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();

        if(!(entity instanceof ProjectileT) && !(entity instanceof ProjectileA) && entity != null)
        {
            CapabilityState.Process stateProcess = new CapabilityState.Process(entity);
            stateProcess.processMain();
            stateProcess.processOther();
            stateProcess.removeFunction();
            stateProcess.processPerTick();
            if(EntityUtil.atSetIntervals(entity,20,0,0))
                stateProcess.sendPacketToClient();
        }

        if(entity instanceof EntityPlayer)
        {
            CapabilitySam.Process processSam = new CapabilitySam.Process((EntityPlayer) entity);
            CapabilityLevel.Process processLevel = new CapabilityLevel.Process((EntityPlayer) entity);
            CapabilityMana.Process processMana = new CapabilityMana.Process((EntityPlayer) entity);
            CapabilityStamina.Process processStamina = new CapabilityStamina.Process((EntityPlayer) entity);
            CapabilityValue.Process processValue = new CapabilityValue.Process((EntityPlayer) entity);
            CapabilityPlayer.Process player = new CapabilityPlayer.Process((EntityPlayer) entity);
            CapabilityState.Process state = new CapabilityState.Process(entity);
            ItemStack mod = new MyInfoContainer(((EntityPlayer) entity).inventory, (EntityPlayer) entity).getSlot(43).getStack();
            CapabilityItem.Process item = new CapabilityItem.Process(mod);
            processLevel.setTime(processLevel.getTime()+1);

            if(((EntityPlayer) entity).capabilities.isCreativeMode)
            {
                processSam.setSam(processSam.getLimit());

                processMana.setMana(processMana.getLimit());

                processStamina.setStamina(processStamina.getLimit());
            }

            if((state.getStates().contains(EnumState.INSPIRE) || state.getStates().contains(EnumState.WE_UNITE)) && !entity.world.isRemote)
            {
                processStamina.setStamina(processStamina.getLimit());
                processMana.setMana(processMana.getLimit());
            }

            player.setSwing(player.getTimes()>0);

            if(player.getTimes()>0)
                player.setTimes(player.getTimes()-1);

            if(entity.world.isRemote)
            {
                player.setName(entity.getName());

                player.sendPacketToServer();
            }

            if(processSam.getTick()>0 && processSam.getVariable()!=0)
            {
                if(EntityUtil.atSetIntervals(entity, (int) (processSam.getTick()*(1f-item.getRank(EnumEntry.ADD_SAM)/10f)),0,0) && processSam.getSam()<processSam.getLimit())
                {
                    if(processSam.getVariable()>0)
                        processSam.addSam((int) (processSam.getVariable()*(1f+item.getRank(EnumEntry.ADD_SAM)/10f)));
                    else
                        processSam.reduceSam(processSam.getVariable());
                }
            }

            if(processMana.getTick()>0 && processMana.getVariable()!=0)
            {
                if(EntityUtil.atSetIntervals(entity, (int) (processMana.getTick()*(1f-item.getRank(EnumEntry.ADD_MAMA)/10f)),0,0) && processMana.getMana()<processMana.getLimit())
                {
                    if(processMana.getVariable()>0)
                        processMana.addMana((int) (processMana.getVariable()*(1f+item.getRank(EnumEntry.ADD_MAMA)/10f)));
                    else
                        processMana.reduceMana(processMana.getVariable());
                }
            }

            if(processStamina.getTick()>0 && processStamina.getVariable()!=0)
            {
                if(EntityUtil.atSetIntervals(entity, (int) (processStamina.getTick()*(1f-item.getRank(EnumEntry.ADD_STAMINA)/10f)),0,0) && processStamina.getStamina()<processStamina.getLimit())
                {
                    if(processStamina.getVariable()>0)
                        processStamina.addStamina((int) (processStamina.getVariable()*(1f+item.getRank(EnumEntry.ADD_STAMINA)/10f)));
                    else
                        processStamina.reduceStamina(processStamina.getVariable());
                }
            }

            if(processLevel.getExperience() >= processLevel.getNeed() && processLevel.getLevel()<100)
            {
                processLevel.addLevel(1);

                processLevel.setExperience(0);

                processSam.changeVariety(-processLevel.getLevel(),0);//max 10s

                processStamina.changeVariety(-(processLevel.getLevel()%10==0?processLevel.getLevel():0),processLevel.getLevel()%10==0?processLevel.getLevel()/10:0);//max 1s

                processMana.changeVariety(-(processLevel.getLevel()%4==0?processLevel.getLevel():0),processLevel.getLevel()%20==0?processLevel.getLevel()/20:0);//max 2s

                processSam.setLimited(processLevel.getLevel()+60);

                processMana.setLimited(processLevel.getLevel()+20);

                processStamina.setLimited(processLevel.getLevel()*2+40);

                processLevel.setNeed((processLevel.getLevel()+1)*500);

                if(Arkworld.NEWDES && !entity.world.isRemote)
                    ServerUtil.consoleCommand("hxl setlevel " + processLevel.getLevel() + " " + entity.getName());
            }

            if(EntityUtil.atSetIntervals(entity,20,0,0))
            {
                processSam.sendPacketToClient();
                processLevel.sendPacketToClient();
                processMana.sendPacketToClient();
                processStamina.sendPacketToClient();
                processValue.sendPacketToClient();
            }
        }
    }

    @SubscribeEvent
    public static void onFirstLogging(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event)
    {
        EntityPlayer entity= event.player;

        CapabilitySam.Process processSam = new CapabilitySam.Process(entity);
        CapabilityLevel.Process processLevel = new CapabilityLevel.Process(entity);
        CapabilityMana.Process processMana = new CapabilityMana.Process(entity);
        CapabilityStamina.Process processStamina = new CapabilityStamina.Process(entity);

        if(processLevel.getLevel()==0)
        {
            processSam.setVariety(5250,1);

            processStamina.setVariety(570,1);

            processMana.setVariety(1340,1);

            processSam.setLimited(35);

            processMana.setLimited(10);

            processStamina.setLimited(40);

            processLevel.setNeed(500);
        }

        if(processLevel.getTime()<=20)
        {
            entity.inventory.add(-1, new ItemStack(ItemRegistry.KEEL));
            entity.inventory.add(-1, new ItemStack(ItemRegistry.MY_INFO));
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event)
    {
        if(event.getObject() instanceof EntityLivingBase && !(event.getObject() instanceof ProjectileA) && !(event.getObject() instanceof ProjectileT))
        {
            CapabilityState.Provide state = new CapabilityState.Provide();
            event.addCapability(new ResourceLocation(Arkworld.MODID , "state"), state);

            if(!(event.getObject() instanceof WLM) && !(event.getObject() instanceof ArkAnimal) && !(event.getObject() instanceof EntityAnimal))
            {
                CapabilityAttribute.Provide attribute = new CapabilityAttribute.Provide();
                event.addCapability(new ResourceLocation(Arkworld.MODID, "attribute"), attribute);
            }
        }

        if(event.getObject() instanceof EntityPlayer)
        {
            CapabilitySam.Provide sam = new CapabilitySam.Provide();
            event.addCapability(new ResourceLocation(Arkworld.MODID,"sam"), sam);
            CapabilityLevel.Provide level = new CapabilityLevel.Provide();
            event.addCapability(new ResourceLocation(Arkworld.MODID,"level"), level);
            CapabilityMana.Provide mana = new CapabilityMana.Provide();
            event.addCapability(new ResourceLocation(Arkworld.MODID,"mana"), mana);
            CapabilityStamina.Provide stamina = new CapabilityStamina.Provide();
            event.addCapability(new ResourceLocation(Arkworld.MODID,"stamina"), stamina);
            CapabilityValue.Provide value = new CapabilityValue.Provide();
            event.addCapability(new ResourceLocation(Arkworld.MODID,"value"), value);
            CapabilityPlayer.Provide player = new CapabilityPlayer.Provide();
            event.addCapability(new ResourceLocation(Arkworld.MODID,"player"), player);
            event.addCapability(new ResourceLocation(Arkworld.MODID,"slot_player"), new CapabilitySlotPlayer.Provider(new ItemStackHandler(6)));
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesItem(AttachCapabilitiesEvent<ItemStack> event)
    {
        if(event.getObject().getItem() instanceof MeleeWeapon || event.getObject().getItem() instanceof RangeWeapon || event.getObject().getItem() instanceof MeleeWeapon)
        {
            CapabilityWeapon.Provide weapon = new CapabilityWeapon.Provide();
            event.addCapability(new ResourceLocation(Arkworld.MODID,"weapon"),weapon);
        }

        if(event.getObject().getItem() instanceof ExpCard || event.getObject().getItem() instanceof ArmsMod)
        {
            CapabilityItem.Provide item = new CapabilityItem.Provide();
            event.addCapability(new ResourceLocation(Arkworld.MODID,"weapon_mod"),item);
        }

        if(event.getObject().getItem() instanceof OperatorCard)
        {
            CapabilityOperatorCard.Provide item = new CapabilityOperatorCard.Provide();
            event.addCapability(new ResourceLocation(Arkworld.MODID,"operator_card"),item);
        }

        if(event.getObject().getItem() instanceof ItemMachine)
        {
            CapabilityOperatorCard.Provide item = new CapabilityOperatorCard.Provide();
            event.addCapability(new ResourceLocation(Arkworld.MODID,"machine"),item);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        CapabilitySam sam = event.getEntityPlayer().getCapability(CapabilityRegistry.capSam,null);
        CapabilitySam sam_original = event.getOriginal().getCapability(CapabilityRegistry.capSam,null);
        sam.setSam(sam_original.getSam());
        sam.setLimit(sam_original.getLimit());

        CapabilityLevel level = event.getEntityPlayer().getCapability(CapabilityRegistry.capLevel,null);
        CapabilityLevel level_original = event.getOriginal().getCapability(CapabilityRegistry.capLevel,null);
        level.setExperience(level_original.getExperience());
        level.setNeed(level_original.getNeed());
        level.setLevel(level_original.getLevel());
        level.setTime(level_original.getTime());

        CapabilityMana mana = event.getEntityPlayer().getCapability(CapabilityRegistry.capMana,null);
        CapabilityMana mana_original = event.getOriginal().getCapability(CapabilityRegistry.capMana,null);
        mana.setMana(mana_original.getMana());
        mana.setLimit(mana_original.getLimit());

        CapabilityStamina stamina = event.getEntityPlayer().getCapability(CapabilityRegistry.capStamina,null);
        CapabilityStamina stamina_original = event.getOriginal().getCapability(CapabilityRegistry.capStamina,null);
        stamina.setStamina(stamina_original.getStamina());
        stamina.setLimit(stamina_original.getLimit());

        CapabilityValue value = event.getEntityPlayer().getCapability(CapabilityRegistry.capValue,null);
        CapabilityValue value_original = event.getOriginal().getCapability(CapabilityRegistry.capValue,null);
        value.setLmb(value_original.getLmb());
        value.setOrundum(value_original.getOrundum());
        value.setOriginium(value_original.getOriginium());

        ItemStackHandler handler = (ItemStackHandler) event.getEntityPlayer().getCapability(CapabilityRegistry.capSlotPlayer,null);
        handler.deserializeNBT(((ItemStackHandler) event.getOriginal().getCapability(CapabilityRegistry.capSlotPlayer,null)).serializeNBT());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRenderCapabilities(RenderGameOverlayEvent event)
    {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        new StateGui().render();

        List<SimpleGui> guiList = new ArrayList<>();
        SamGui samGui = new SamGui();
        ManaGui manaGui = new ManaGui();
        StaminaGui staminaGui = new StaminaGui();

        guiList.add(samGui);
        guiList.add(manaGui);
        guiList.add(staminaGui);

        guiList.removeIf(next->next==samGui && MyInfoGuiContainer.samTimes%2==0);
        guiList.removeIf(next->next==manaGui && MyInfoGuiContainer.manaTimes%2==0);
        guiList.removeIf(next->next==staminaGui && MyInfoGuiContainer.staminaTimes%2==0);

        for(int i=0; i<=guiList.size()-1; i++)
        {
            guiList.get(i).render(1+i*13);
        }
    }
}
