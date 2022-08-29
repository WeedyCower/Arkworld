package com.weedycow.arkworld.item.operator;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.capability.CapabilityOperatorCard;
import com.weedycow.arkworld.entity.operator.Operator;
import com.weedycow.arkworld.gui.MyInfoGuiContainer;
import com.weedycow.arkworld.item.normal.NormalItem;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.arkworld.util.ArkEntityUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public abstract class OperatorCard extends NormalItem
{
    public OperatorCard(String id, CreativeTabs tab, int maxStackSize)
    {
        super(id, tab, maxStackSize);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (stack.hasCapability(CapabilityRegistry.capOperatorCard, null))
        {
            CapabilityOperatorCard card = stack.getCapability(CapabilityRegistry.capOperatorCard, null);
            if(!card.isClear())
            {
                if(card.getMaster()!=null && !Objects.equals(card.getMaster(), "") && worldIn!=null && worldIn.getPlayerEntityByName(card.getMaster())!=null)
                    tooltip.add(I18n.format("gui.arkworld.operator_master")+worldIn.getPlayerEntityByName(card.getMaster()));
                tooltip.add(I18n.format("gui.arkworld.operator_el")+card.getElite());
                tooltip.add(I18n.format("gui.arkworld.operator_lv")+card.getLevel());
                tooltip.add(I18n.format("gui.arkworld.operator_bl")+card.getTrust());
                tooltip.add(I18n.format("gui.arkworld.operator_pt")+card.getPotential());
                tooltip.add(I18n.format("gui.arkworld.operator_selSkill")+card.getSelectSkill());
                tooltip.add(I18n.format("gui.arkworld.operator_times")+card.getTimes());
                tooltip.add(I18n.format("gui.arkworld.operator_clodown")+card.getCountdown()/20);
                tooltip.add(I18n.format("gui.arkworld.operator_time")+ MyInfoGuiContainer.getFormat(card.getExistedTime()/20));
            }
            else if(card.getUuid()!=null && worldIn!=null && ArkEntityUtil.getOperator(worldIn,card.getUuid())!=null)
            {
                Operator operator = ArkEntityUtil.getOperator(worldIn,card.getUuid());
                if(operator!=null && operator.getMaster() != null)
                    tooltip.add(I18n.format("gui.arkworld.operator_master")+operator.getMaster().getName());
                tooltip.add(I18n.format("gui.arkworld.operator_conOpe")+(operator.hasCustomName() ? operator.getCustomNameTag() : operator.getName()) + "," + I18n.format("item.arkworld.info.position") + operator.getPosition().getX() + "," + operator.getPosition().getY() + "," + operator.getPosition().getZ());
                tooltip.add(I18n.format("gui.arkworld.operator_selSkill")+operator.getSelectSkill());
                tooltip.add(I18n.format("gui.arkworld.operator_skillPoint")+operator.getSkillPoint()+"/"+operator.getSkills().get(operator.getSelectSkill()-1).getConsumePoint());
                tooltip.add(I18n.format("gui.arkworld.operator_selSkill")+operator.getSelectSkill());
                tooltip.add(I18n.format("gui.arkworld.operator_mood")+operator.getMood()+"/"+24);
                tooltip.add(I18n.format("gui.arkworld.operator_trainTime")+operator.getTrainTime()+"/"+28800*(operator.getSkillRank()+1));
            }
            else
            {
                tooltip.add(I18n.format("item.arkworld.info.notGetOperator"));
                tooltip.add(I18n.format("item.arkworld.info.positionLast") + I18n.format("item.arkworld.info.diaLast") + card.getDia() + "," + I18n.format("item.arkworld.info.position") + card.getX() + "," + card.getY() + "," + card.getZ());
            }
        }
    }

    public static void recovery(PlayerInteractEvent event)
    {
        if(event instanceof PlayerInteractEvent.EntityInteract)
        {
            EntityLivingBase entity = event.getEntityLiving();
            Entity target = ((PlayerInteractEvent.EntityInteract) event).getTarget();
            ItemStack stack = entity.getHeldItem(EnumHand.MAIN_HAND);

            if(entity instanceof EntityPlayer && target instanceof Operator && ((Operator) target).getMaster()==entity)
            {
                if(!(stack.getItem() instanceof OperatorCard))
                {
                    if(!entity.world.isRemote)
                        ((EntityPlayer) entity).openGui(Arkworld.instance, 1, entity.world, target.getEntityId(), 0, 0);
                }

                if(stack.hasCapability(CapabilityRegistry.capOperatorCard,null))
                {
                    CapabilityOperatorCard card = stack.getCapability(CapabilityRegistry.capOperatorCard,null);

                    if (stack.getItem() instanceof OperatorCard && card.isClear())
                    {
                        card.setLevel(((Operator) target).getLevel());

                        card.setExp(((Operator) target).getExp());

                        card.setMoveMode(((Operator) target).getMoveMode().getValue());

                        card.setActionMode(((Operator) target).getActionMode().getValue());

                        card.setTrust(((Operator) target).getTrust());

                        card.setSkillLevel(((Operator) target).getSkillLevel());

                        card.setSkillFirRank(((Operator) target).getSkillFirRank());

                        card.setSkillSecRank(((Operator) target).getSkillSecRank());

                        card.setSkillThiRank(((Operator) target).getSkillThiRank());

                        card.setSelectSkill(((Operator) target).getSelectSkill());

                        card.setElite(((Operator) target).getElite());

                        card.setPotential(((Operator) target).getPotential());

                        card.setCountdown(((Operator) target).getRedeployTime());

                        card.setTimes(((Operator) target).getTimes());

                        card.setExistedTime(((Operator) target).getExistedTime());

                        card.setMood(((Operator) target).getMood());

                        card.setTrainTime(((Operator) target).getTrainTime());

                        if(((Operator) target).getMaster()!=null)
                            card.setMaster(((Operator) target).getMaster().getName());

                        card.setClear(false);

                        target.setDead();
                    }
                }
            }
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if(stack.getItem() instanceof OperatorCard && stack.hasCapability(CapabilityRegistry.capOperatorCard,null) && !worldIn.isRemote)
        {
            CapabilityOperatorCard card = stack.getCapability(CapabilityRegistry.capOperatorCard, null);

            if(card.getCountdown()>0)
                card.setCountdown(card.getCountdown()-1);

            if(card.getUuid() != null && ArkEntityUtil.getOperator(worldIn, card.getUuid()) != null)
            {
                Operator operator = ArkEntityUtil.getOperator(worldIn, card.getUuid());
                card.setDia(operator.dimension);
                card.setX(operator.getPosition().getX());
                card.setY(operator.getPosition().getY());
                card.setZ(operator.getPosition().getZ());
            }
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, NBTTagCompound nbt)
    {
        return new CapabilityOperatorCard.Provide();
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack)
    {
        if(stack.hasCapability(CapabilityRegistry.capOperatorCard,null))
            stack.setTagCompound(stack.getCapability(CapabilityRegistry.capOperatorCard,null).serializeNBT());
        return stack.getTagCompound();
    }

    @Override
    public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        if(stack.hasCapability(CapabilityRegistry.capOperatorCard,null) && nbt!=null)
            stack.getCapability(CapabilityRegistry.capOperatorCard,null).deserializeNBT(nbt);
    }
}