package com.weedycow.arkworld.item.operator;

import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityOperatorCard;
import com.weedycow.arkworld.capability.CapabilitySam;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.entity.operator.guard.Surtr;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.util.ArkEntityUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class SurtrCard extends OperatorCard
{
    public SurtrCard()
    {
        super("surtr_card", ArkCreateTab.OPERATOR, 1);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if(!Arkworld.NEWDES)
            tooltip.add(I18n.format("item.arkworld.info.onlyNewdes"));
    }

    public static void omRightClick(PlayerInteractEvent event)
    {
        if(Arkworld.NEWDES)
        {
            if (event instanceof PlayerInteractEvent.RightClickItem)
            {
                ItemStack stack = event.getItemStack();
                EntityPlayer playerIn = event.getEntityPlayer();
                World worldIn = event.getWorld();
                CapabilitySam.Process sam = new CapabilitySam.Process(playerIn);

                if (stack.getItem() instanceof SurtrCard && stack.hasCapability(CapabilityRegistry.capOperatorCard, null))
                {
                    CapabilityOperatorCard card = stack.getCapability(CapabilityRegistry.capOperatorCard, null);

                    if (card.getUuid() != null && !worldIn.isRemote)
                    {
                        Entity entity = ArkEntityUtil.getOperator(worldIn, card.getUuid());
                        if (entity instanceof Surtr && card.isClear() && playerIn == ((Surtr) entity).getMaster())
                        {
                            ((Surtr) entity).setSkillStartup(2);
                            ((Surtr) entity).playFightingSound();
                        }
                    }

                    if (card.getTimes() == 0 && !card.isClear() && !worldIn.isRemote)
                    {
                        Operator surtr = new Surtr(worldIn).getOperator(1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 24, 0, playerIn.getName());
                        surtr.setLocationAndAngles(event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
                        surtr.rotationYawHead = surtr.rotationYaw;
                        surtr.renderYawOffset = surtr.rotationYaw;

                        if (worldIn.spawnEntity(surtr))
                        {
                            surtr.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(surtr)), null);

                            surtr.setUuid(UUID.randomUUID().toString());

                            card.setClear(true);

                            card.setUuid(surtr.getUuid());

                            sam.reduceSam(surtr.getDeployPoint());
                        }
                    }

                    if (card.getTimes() > 0 && card.getMaster() != null && !card.isClear() && card.getCountdown() == 0)
                    {
                        Operator surtr = new Surtr(worldIn).getOperator(card.getLevel(), card.getExp(), card.getMoveMode(), card.getActionMode(), card.getTrust(), card.getSkillLevel(), card.getSkillFirRank(), card.getSkillSecRank(), card.getSkillThiRank(), card.getSelectSkill(), card.getElite(), card.getPotential(), card.getTimes(), card.getExistedTime(), card.getMood(), card.getTrainTime(), card.getMaster());
                        surtr.setLocationAndAngles(event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
                        surtr.rotationYawHead = surtr.rotationYaw;
                        surtr.renderYawOffset = surtr.rotationYaw;

                        if (sam.getSam() >= surtr.getDeployPoint() && !worldIn.isRemote)
                        {
                            if (worldIn.spawnEntity(surtr))
                            {
                                surtr.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(surtr)), null);

                                card.setClear(true);

                                card.setUuid(surtr.getUuid());

                                sam.reduceSam(surtr.getDeployPoint());
                            }
                        } else
                        {
                            TextComponentString text = new TextComponentString(I18n.format("item.arkworld.info.deployPoint") + surtr.getDeployPoint());
                            playerIn.sendMessage(text);
                        }
                    }
                }
            }
        }
    }
}
