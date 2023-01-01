package com.weedycow.arkworld.item.operator;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.capability.CapabilityOperatorCard;
import com.weedycow.arkworld.capability.CapabilitySam;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.entity.operator.caster.Amiya;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.util.ArkEntityUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.UUID;


public class AmiyaCard extends OperatorCard
{
    public AmiyaCard()
    {
        super("amiya_card", ArkCreateTab.OPERATOR, 1);
    }

    public static void omRightClick(PlayerInteractEvent event)
    {
        if(event instanceof PlayerInteractEvent.RightClickItem)
        {
            ItemStack stack = event.getItemStack();
            EntityPlayer playerIn = event.getEntityPlayer();
            World worldIn = event.getWorld();
            CapabilitySam.Process sam = new CapabilitySam.Process(playerIn);

            if(stack.getItem() instanceof AmiyaCard && stack.hasCapability(CapabilityRegistry.capOperatorCard,null))
            {
                CapabilityOperatorCard card = stack.getCapability(CapabilityRegistry.capOperatorCard,null);

                if(card.getUuid()!=null && !worldIn.isRemote)
                {
                    Entity entity = ArkEntityUtil.getOperator(worldIn,card.getUuid());
                    if(entity instanceof Amiya && card.isClear() && playerIn==((Amiya) entity).getMaster())
                    {
                        ((Amiya) entity).setSkillStartup(2);
                        ((Amiya) entity).playFightingSound();
                    }
                }

                if(card.getTimes()==0 && !card.isClear() && !worldIn.isRemote)
                {
                    Operator amiya = new Amiya(worldIn).getOperator(1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 24, 0, playerIn.getName());
                    amiya.setLocationAndAngles(event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
                    amiya.rotationYawHead = amiya.rotationYaw;
                    amiya.renderYawOffset = amiya.rotationYaw;

                    if (worldIn.spawnEntity(amiya))
                    {
                        amiya.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(amiya)), null);

                        amiya.setUuid(UUID.randomUUID().toString());

                        card.setClear(true);

                        card.setUuid(amiya.getUuid());

                        sam.reduceSam(amiya.getDeployPoint());
                    }
                }

                if(card.getTimes()>0 && card.getMaster()!=null && !card.isClear() && card.getCountdown()==0)
                {
                    Operator amiya = new Amiya(worldIn).getOperator(card.getLevel(), card.getExp(), card.getMoveMode(), card.getActionMode(), card.getTrust(), card.getSkillLevel(), card.getSkillFirRank(), card.getSkillSecRank(), card.getSkillThiRank(), card.getSelectSkill(), card.getElite(), card.getPotential(), card.getTimes(), card.getExistedTime(), card.getMood(), card.getTrainTime(), card.getMaster());
                    amiya.setLocationAndAngles(event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
                    amiya.rotationYawHead = amiya.rotationYaw;
                    amiya.renderYawOffset = amiya.rotationYaw;

                    if(sam.getSam()>=amiya.getDeployPoint())
                    {
                        if (!worldIn.isRemote && worldIn.spawnEntity(amiya))
                        {
                            amiya.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(amiya)), null);

                            card.setClear(true);

                            amiya.setUuid(card.getUuid());

                            sam.reduceSam(amiya.getDeployPoint());
                        }
                    }
                    else if(worldIn.isRemote)
                    {
                        TextComponentString text = new TextComponentString(I18n.format("item.arkworld.info.deployPoint")+amiya.getDeployPoint());
                        playerIn.sendMessage(text);
                    }
                }
            }
        }
    }
}
