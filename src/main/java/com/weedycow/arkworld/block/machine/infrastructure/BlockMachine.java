package com.weedycow.arkworld.block.machine.infrastructure;

import com.weedycow.arkworld.ArkConfig;
import com.weedycow.arkworld.ArkCreateTab;
import com.weedycow.arkworld.Arkworld;
import com.weedycow.arkworld.block.ArkMaterial;
import com.weedycow.arkworld.block.ArkTile;
import com.weedycow.arkworld.registry.CapabilityRegistry;
import com.weedycow.weedylib.util.StringUtil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public abstract class BlockMachine extends BlockContainer
{
    static ArkConfig.Block.Infrastructure machine = ArkConfig.block.infrastructure;
    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", BlockMachine.Type.class);

    public BlockMachine(Type type)
    {
        super(ArkMaterial.MACHINE);
        this.setCreativeTab(ArkCreateTab.DEVICE);
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(TYPE,type));
        this.setRegistryName(type.getName());
        this.setUnlocalizedName(Arkworld.MODID + "." + StringUtil.lineToHump(type.getName()));
        this.setHarvestLevel("pickaxe",1);
        this.setHardness(3f);
        this.setResistance(5f);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.005F);

        if (this.canSilkHarvest(worldIn, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0)
        {
            java.util.List<ItemStack> items = new java.util.ArrayList<>();
            ItemStack itemstack = this.getSilkTouchDrop(state);

            if (!itemstack.isEmpty())
            {
                items.add(itemstack);
            }

            net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);

            for (ItemStack item : items)
            {
                spawnAsEntity(worldIn, pos, item, te);
            }
        }
        else
        {
            harvesters.set(player);
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
            this.dropBlockAsItemWithChance(worldIn, pos, state, 1, i, te);
            harvesters.set(null);
        }
    }

    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune, TileEntity tile)
    {
        if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
        {
            List<ItemStack> drops = getDrops(worldIn, pos, state, fortune); // use the old method until it gets removed, for backward compatibility
            chance = net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(drops, worldIn, pos, state, fortune, chance, false, harvesters.get());

            for (ItemStack drop : drops)
            {
                if (worldIn.rand.nextFloat() <= chance)
                {
                    spawnAsEntity(worldIn, pos, drop, tile);
                }
            }
        }
    }

    public static void spawnAsEntity(World worldIn, BlockPos pos, ItemStack stack, TileEntity tile)
    {
        if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean("doTileDrops")&& !worldIn.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
        {
            if (captureDrops.get())
            {
                capturedDrops.get().add(stack);
                return;
            }

            if(stack.hasCapability(CapabilityRegistry.capItem,null) && tile instanceof ArkTile)
                stack.getCapability(CapabilityRegistry.capItem,null).setLevel(((MachineTile) tile).getLevel());

            double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            EntityItem entityitem = new EntityItem(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, stack);
            entityitem.setDefaultPickupDelay();
            worldIn.spawnEntity(entityitem);

            if(tile!=null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null))
            {
                IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
                for(int i=0; i<itemHandler.getSlots(); i++)
                {
                    EntityItem item = new EntityItem(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, itemHandler.getStackInSlot(i));
                    item.setDefaultPickupDelay();
                    worldIn.spawnEntity(item);
                }

            }
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(@Nonnull IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(TYPE).getSerialNumber();
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(TYPE, Type.getType(meta));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE);
    }

    public Type getType()
    {
        return blockState.getBaseState().getValue(TYPE);
    }

    protected void setType(Type type)
    {
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE,type));
    }

    public enum Type implements IStringSerializable
    {
        CONTROL_CENTER(0),
        DORMITORY(1),
        MANUFACTURING_STATION(2),
        TRADING_STATION(3),
        POWER_STATION(4),
        PARLOR(5),
        PROCESSING_STATION(6),
        OFFICE(7),
        TRAINING_ROOM(8);

        final int sn;

        Type(int sn)
        {
            this.sn = sn;
        }

        @Nonnull
        @Override
        public String getName()
        {
            return name().toLowerCase();
        }

        public int getSerialNumber()
        {
            return this.sn;
        }

        public static Type getType(int sn)
        {
            for(Type type : Type.values())
            {
                if(type.getSerialNumber() == sn)
                    return type;
            }
            return null;
        }
    }
}
