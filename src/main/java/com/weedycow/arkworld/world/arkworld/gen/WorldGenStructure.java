package com.weedycow.arkworld.world.arkworld.gen;

import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WorldGenStructure extends WorldGenerator implements IStructure
{
    protected String structureName;
    protected Block top;

    List<ItemStack> epic = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.WHITE_HORSE_KOHL),
            new ItemStack(ItemRegistry.MANGANESE_TRIHYDRATE),new ItemStack(ItemRegistry.GRINDSTONE_PENTAHYDRATE),
            new ItemStack(ItemRegistry.RMAR),new ItemStack(ItemRegistry.ORIROCK_CONCENTRATION),
            new ItemStack(ItemRegistry.OPTIMIZED_DEVICE),new ItemStack(ItemRegistry.POLYESTER_LUMP),
            new ItemStack(ItemRegistry.SUGAR_LUMP),new ItemStack(ItemRegistry.ORIRON_BLOCK),
            new ItemStack(ItemRegistry.KETON_COLLOID),new ItemStack(ItemRegistry.POLYMERIZED_GEL),
            new ItemStack(ItemRegistry.INCANDESCENT_ALLOY_BLOCK),new ItemStack(ItemRegistry.CRYSTALLINE_CIRCUIT),
            new ItemStack(ItemRegistry.CARBON_PACK),new ItemStack(ItemRegistry.REINFORCED_BUILDING_MATERIAL),
            new ItemStack(ItemRegistry.SKILL_SUMMARY_III),new ItemStack(ItemRegistry.TACTICAL_BATTLE_RECORD),
            new ItemStack(ItemRegistry.LMB_HUNDRED),new ItemStack(ItemRegistry.HEADHUNT),
            new ItemStack(ItemRegistry.TOP_EMERGENCY_SANITY_POTION),
            new ItemStack(ItemRegistry.KETTLE),new ItemStack(ItemRegistry.KEEL),new ItemStack(ItemRegistry.TWO_LOAVES_WITH_CHEESE)));

    List<ItemStack> rare = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.LOXIC_KOHL),
            new ItemStack(ItemRegistry.MANGANESE_ORE),new ItemStack(ItemRegistry.GRINDSTONE),
            new ItemStack(ItemRegistry.RMAY),new ItemStack(ItemRegistry.ORIROCK_CLUSTER),
            new ItemStack(ItemRegistry.INTEGRATED_DEVICE),new ItemStack(ItemRegistry.POLYESTER_PACK),
            new ItemStack(ItemRegistry.SUGAR_PACK),new ItemStack(ItemRegistry.ORIRON_CLUSTER),
            new ItemStack(ItemRegistry.AKETON),new ItemStack(ItemRegistry.COAGULATING_GEL),
            new ItemStack(ItemRegistry.INCANDESCENT_ALLOY),new ItemStack(ItemRegistry.CRYSTALLINE_COMPONENT),
            new ItemStack(ItemRegistry.CARBON_BRICK),new ItemStack(ItemRegistry.CONCRETE_BUILDING_MATERIAL),
            new ItemStack(ItemRegistry.SKILL_SUMMARY_II),new ItemStack(ItemRegistry.STRATEGIC_BATTLE_RECORD),
            new ItemStack(ItemRegistry.ORUNDUM),new ItemStack(ItemRegistry.ORIGINIUM_SHARD),
            new ItemStack(ItemRegistry.CASTER_CHIP),new ItemStack(ItemRegistry.DEFENDER_CHIP),
            new ItemStack(ItemRegistry.GUARD_CHIP),new ItemStack(ItemRegistry.MEDIC_CHIP),
            new ItemStack(ItemRegistry.SNIPER_CHIP),new ItemStack(ItemRegistry.SPECIALIST_CHIP),
            new ItemStack(ItemRegistry.SUPPORTER_CHIP),new ItemStack(ItemRegistry.VANGUARD_CHIP),
            new ItemStack(ItemRegistry.EMERGENCY_SANITY_POTION),new ItemStack(ItemRegistry.CHEESE)));

    List<ItemStack> high = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.ORIROCK_CUBE),
            new ItemStack(ItemRegistry.DEVICE),new ItemStack(ItemRegistry.POLYESTER),
            new ItemStack(ItemRegistry.SUGAR),new ItemStack(ItemRegistry.ORIRON),new ItemStack(ItemRegistry.LMB,2),
            new ItemStack(ItemRegistry.POLYKETON),new ItemStack(ItemRegistry.CARBON),
            new ItemStack(ItemRegistry.LIGHT_BUILDING_MATERIAL),new ItemStack(ItemRegistry.SKILL_SUMMARY_I),
            new ItemStack(ItemRegistry.FRONTLINE_BATTLE_RECORD),new ItemStack(ItemRegistry.COIN,2),
            new ItemStack(ItemRegistry.WEAPON_GEM,2),new ItemStack(ItemRegistry.EMERGENCY_SANITY_SAMPLER)));

    List<ItemStack> normal = new ArrayList<>(Arrays.asList(new ItemStack(ItemRegistry.ORIROCK),
            new ItemStack(ItemRegistry.DAMAGED_DEVICE),new ItemStack(ItemRegistry.ESTER),
            new ItemStack(ItemRegistry.SUGAR_SUBSTITUTE),new ItemStack(ItemRegistry.ORIRON_SHARD),
            new ItemStack(ItemRegistry.DIKETON),new ItemStack(ItemRegistry.DRILL_BATTLE_RECORD),
            new ItemStack(ItemRegistry.LMB),new ItemStack(ItemRegistry.WEAPON_GEM),new ItemStack(ItemRegistry.COIN)));

    public WorldGenStructure(String name, Block top)
    {
        this.structureName = name;
        this.top = top;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        MinecraftServer mcServer = world.getMinecraftServer();
        TemplateManager manager = worldServer.getStructureTemplateManager();
        ResourceLocation location = new ResourceLocation(Arkworld.MODID, structureName);
        Template template = manager.get(mcServer,location);
        if(template != null && (structureName.equals("iberia_tower_ii") || structureName.equals("iberia_tower_iii") || WorldGenCustomStructure.checkAround(world,pos,template.getSize(),top)))
        {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            template.addBlocksToWorldChunk(world, pos, settings);
            addItemToChest(world,template,pos);
            return true;
        }
        else
            return false;
    }

    public void addItemToChest(World world,Template template,BlockPos pos)
    {
        for(TileEntityChest chest : getChestTile(world,template,pos))
        {
            int c1 = new Random().nextInt(5)+1;

            for(int i=0; i<c1; i++)
            {
                int c2 = new Random().nextInt(100)+1;

                if(c2<=5)
                {
                    chest.setInventorySlotContents(i,epic.get(new Random().nextInt(epic.size())));
                }

                if(c2<=15)
                {
                    chest.setInventorySlotContents(i,rare.get(new Random().nextInt(rare.size())));
                }

                if(c2<=35)
                {
                    chest.setInventorySlotContents(i,high.get(new Random().nextInt(high.size())));
                }

                if(c2<=75)
                {
                    chest.setInventorySlotContents(i,normal.get(new Random().nextInt(normal.size())));
                }
            }
        }
    }

    public static List<TileEntityChest> getChestTile(World world,Template template,BlockPos pos)
    {
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagList list = (NBTTagList) template.writeToNBT(compound).getTag("blocks");
        List<BlockPos> ps = getChestPos(list,pos);
        List<TileEntityChest> chests = new ArrayList<>();

        for(BlockPos p : ps)
        {
            if(world.getTileEntity(p) instanceof TileEntityChest)
            {
                chests.add((TileEntityChest) world.getTileEntity(p));
            }
        }

        return chests;
    }

    public static List<BlockPos> getChestPos(NBTTagList list,BlockPos pos)
    {
        List<Integer> integers = getChestsSer(list);
        List<BlockPos> blockPos = new ArrayList<>();

        for (Integer integer : integers)
        {
            NBTTagList tem = list.getCompoundTagAt(integer).getTagList("pos", 3);
            blockPos.add(new BlockPos(tem.getIntAt(0)+pos.getX(), tem.getIntAt(1)+pos.getY(), tem.getIntAt(2)+pos.getZ()));
        }

        return blockPos;
    }

    public static List<Integer> getChestsSer(NBTTagList list)
    {
        List<Integer> integers = new ArrayList<>();

        for(int i=0; i<list.tagCount(); i++)
        {
            if(list.getCompoundTagAt(i).toString().contains("chest"))
            {
                integers.add(i);
            }
        }
        return integers;
    }
}
