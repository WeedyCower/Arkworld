package com.weedycow.arkworld.item.operator;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.capability.CapabilityOperatorCard;
import com.weedycow.arkworld.capability.CapabilitySam;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.entity.operator.sniper.OW;
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

public class WCard extends OperatorCard
{
    public WCard()
    {
        super("w_card", ArkCreateTab.OPERATOR, 1);
    }

    public static void omRightClick(PlayerInteractEvent event)
    {
        if(event instanceof PlayerInteractEvent.RightClickItem)
        {
            ItemStack stack = event.getItemStack();
            EntityPlayer playerIn = event.getEntityPlayer();
            World worldIn = event.getWorld();
            CapabilitySam.Process sam = new CapabilitySam.Process(playerIn);

            if(stack.getItem() instanceof WCard && stack.hasCapability(CapabilityRegistry.capOperatorCard,null))
            {
                CapabilityOperatorCard card = stack.getCapability(CapabilityRegistry.capOperatorCard,null);

                if(card.getUuid()!=null && !worldIn.isRemote)
                {
                    Entity entity = ArkEntityUtil.getOperator(worldIn,card.getUuid());
                    if(entity instanceof OW && card.isClear() && playerIn==((OW) entity).getMaster())
                    {
                        ((OW) entity).setSkillStartup(2);
                        ((OW) entity).playFightingSound();
                    }
                }

                if(card.getTimes()==0 && !card.isClear() && !worldIn.isRemote)
                {
                    Operator ow = new OW(worldIn).getOperator(1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 24, 0, playerIn.getName());
                    ow.setLocationAndAngles(event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
                    ow.rotationYawHead = ow.rotationYaw;
                    ow.renderYawOffset = ow.rotationYaw;

                    if (worldIn.spawnEntity(ow))
                    {
                        ow.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(ow)), null);

                        ow.setUuid(UUID.randomUUID().toString());

                        card.setClear(true);

                        card.setUuid(ow.getUuid());

                        sam.reduceSam(ow.getDeployPoint());
                    }
                }

                if(card.getTimes()>0 && card.getMaster()!=null && !card.isClear() && card.getCountdown()==0)
                {
                    Operator ow = new OW(worldIn).getOperator(card.getLevel(), card.getExp(), card.getMoveMode(), card.getActionMode(), card.getTrust(), card.getSkillLevel(), card.getSkillFirRank(), card.getSkillSecRank(), card.getSkillThiRank(), card.getSelectSkill(), card.getElite(), card.getPotential(), card.getTimes(), card.getExistedTime(), card.getMood(), card.getTrainTime(), card.getMaster());
                    ow.setLocationAndAngles(event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
                    ow.rotationYawHead = ow.rotationYaw;
                    ow.renderYawOffset = ow.rotationYaw;

                    if(sam.getSam()>=ow.getDeployPoint())
                    {
                        if (!worldIn.isRemote && worldIn.spawnEntity(ow))
                        {
                            ow.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(ow)), null);

                            card.setClear(true);

                            ow.setUuid(card.getUuid());

                            sam.reduceSam(ow.getDeployPoint());
                        }
                    }
                    else if(worldIn.isRemote)
                    {
                        TextComponentString text = new TextComponentString(I18n.format("item.arkworld.info.deployPoint")+ow.getDeployPoint());
                        playerIn.sendMessage(text);
                    }
                }
            }
        }
    }
}
